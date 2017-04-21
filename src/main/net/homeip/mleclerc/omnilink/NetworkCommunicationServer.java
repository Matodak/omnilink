package net.homeip.mleclerc.omnilink;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Random;

import net.homeip.mleclerc.omnilink.enumeration.NetworkMessageTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.Message;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.NetworkHelper;

public class NetworkCommunicationServer extends BaseSerialCommunication
{
	private final static int VERSION = 1; 
	private final static int PORT = 4369; 
	
	private int port;
    private DatagramSocket socket;
    private byte[] privateKey = new byte[16];

	private SocketAddress clientAddress;
    private byte[] sessionId;
    private byte[] sessionKey;
	
	public NetworkCommunicationServer(String comPortStr, int baudRate, String encryptionKey)
	{
		this(comPortStr, baudRate, null, null, PORT, encryptionKey);
	}
	
	public NetworkCommunicationServer(String comPortStr, int baudRate, String initCmd, String localAccessCmd, int port, String encryptionKey)
	{
		super(comPortStr, baudRate, initCmd, localAccessCmd);
		
		this.port = port;
		
		// Convert the encryption key to a byte array
		String[] encryptionKeyValues = encryptionKey.split("-");
		for (int i = 0; i < encryptionKeyValues.length; i++)
		{
			String encryptionKeyValue = encryptionKeyValues[i];				
			privateKey[i] = (byte) MessageHelper.hex2decimal(encryptionKeyValue);
		}
	}
	
	public void run() throws CommunicationException
	{
		try 
		{
			// Open serial communication with controller
			open();
			
			// Open UDP port
			socket = new DatagramSocket(port);
			
			byte[] bytes = new byte[1024];
			
			while(true)
			{
				// Wait for a UDP packet
				DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
				socket.receive(packet);
				
				// Get the sequence number (16-bits) starting at index 0
				short seqHigh = MessageHelper.getBits(bytes[0], 0, 8); // This is converts from byte to short in order to remove negative numbers
				short seqLow = MessageHelper.getBits(bytes[1], 0, 8);  // This is converts from byte to short in order to remove negative numbers
				int messageSeqNo = MessageHelper.createWord(seqHigh, seqLow);
				
				// Get the message type (8-bits) at index 2
				int messageTypeVal = bytes[2];
				NetworkMessageTypeEnum messageType = (NetworkMessageTypeEnum) NetworkMessageTypeEnum.metaInfo.getByValue(messageTypeVal);

				// Get the reserved section (8-bits) at index 3
				int reservedByte = bytes[3];
				if (reservedByte != 0)
				{
					throw new CommunicationException("Unexpected value for reserved section");
				}
				
				// Get the decrypted application data (starting at index 4)
				byte[] appData = new byte[packet.getLength() - 4];
				System.arraycopy(bytes, 4, appData, 0, packet.getLength() - 4);
				
				// Process the message type
				if (messageType.equals(NetworkMessageTypeEnum.CLIENT_REQUEST_NEW_SESSION))
				{
					processNewSessionRequest(packet.getSocketAddress(), messageSeqNo);
				}
				else if (messageType.equals(NetworkMessageTypeEnum.CLIENT_REQUEST_SECURE_CONNECTION))
				{
					processSecureConnectionRequest(packet.getSocketAddress(), messageSeqNo, appData);
				}
				else if (messageType.equals(NetworkMessageTypeEnum.OMNI_LINK_MESSAGE))
				{
					processOmniLinkRequest(packet.getSocketAddress(), messageSeqNo, appData);
				}
				else if (messageType.equals(NetworkMessageTypeEnum.CLIENT_SESSION_TERMINATED))
				{
					processSessionTerminatedRequest(packet.getSocketAddress(), messageSeqNo);
				}
			}
		} 
		catch (SocketException ex)
		{
			throw new CommunicationException(ex);
		}
		catch (IOException ex)
		{
			throw new CommunicationException(ex);
		}
		finally
		{
			// Close serial communication with controller
			close();
		}
	}
	
	private void processNewSessionRequest(SocketAddress clientAddress, int messageSeqNo) throws CommunicationException, IOException
	{
		if (this.clientAddress != null && !clientAddress.equals(this.clientAddress))
		{
			// There is already another session in progress
			
			// Send an error
			sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.CONTROLLER_CANNOT_START_NEW_SESSION, null, false);
			return;
		}
				
		// Keep the address of the client
		this.clientAddress = clientAddress;
					
		// Generate a session ID
		sessionId = generateSessionID();
		
		// Create the response data					
		byte[] data = new byte[7];
		
		// Put protocol version (16 bits) starting at index 0		
		data[0] = (byte) MessageHelper.highByte(VERSION);
		data[1] = (byte) MessageHelper.lowByte(VERSION);
		
		// Put session ID (40 bits) starting at index 2
		System.arraycopy(sessionId, 0, data, 2, 5);					
		
		// Create the session key
		sessionKey = NetworkHelper.createSessionKey(privateKey, sessionId);
		
		// Send a success response
		sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.CONTROLLER_ACKNOWLEDGE_NEW_SESSION, data, false);
	}
	
	private void processSecureConnectionRequest(SocketAddress clientAddress, int messageSeqNo, byte[] data) throws CommunicationException, IOException
	{
		if (!clientAddress.equals(this.clientAddress))
		{
			// There is already another session in progress
			
			// Send an error
			sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.CONTROLLER_SESSION_TERMINATED, null, false);
			return;
		}
		
		// Decrypt the application data
		byte[] decryptedData = NetworkHelper.decrypt(data, sessionKey, messageSeqNo);
		
		// Get the session Id (5 bits) starting at index 0
		byte[] sessionId = Arrays.copyOfRange(decryptedData, 0, 5);
		
		// Compare received session Id with generated one
		if (!Arrays.equals(sessionId, this.sessionId))
		{
			// Send an error
			sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.CONTROLLER_SESSION_TERMINATED, null, false);
			return;
		}
		
		// Session ids are identical
		// Send a success response
		sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.CONTROLLER_ACKNOWLEDGE_SECURE_CONNECTION, this.sessionId, true);
	}

	private void processOmniLinkRequest(SocketAddress clientAddress, int messageSeqNo, byte[] data) throws CommunicationException, IOException
	{
		if (!clientAddress.equals(this.clientAddress))
		{
			// There is already another session in progress
			
			// Send an error
			sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.CONTROLLER_SESSION_TERMINATED, null, false);
			return;
		}
		
		// Decrypt the application data
		byte[] decryptedData = NetworkHelper.decrypt(data, sessionKey, messageSeqNo);

		// Get the Omni-Link message (16 bits) starting at index 0
		int startChar = decryptedData[0];
		if (startChar != Message.START_CHAR)
		{
			throw new BadStartCharacterException(startChar, Message.START_CHAR);
		}
		int messageLength = decryptedData[1];
		byte[] omniLinkMessage = Arrays.copyOfRange(decryptedData, 0, messageLength + 4);

		// Send the Omni-Link message to the controller
		os.write(omniLinkMessage);
		os.flush();
		
		// Read the Omni-Link response from controller
		startChar = readNextByte(is);
		if (startChar != Message.START_CHAR)
		{
			throw new BadStartCharacterException(startChar, Message.START_CHAR);			
		}
		int replyLength = readNextByte(is);
		byte[] omniLinkReply = new byte[replyLength + 4];
		omniLinkReply[0] = (byte) startChar;
		omniLinkReply[1] = (byte) replyLength;	
		for (int i = 0; i < replyLength + 2; i++)
		{
			omniLinkReply[i + 2] = (byte) readNextByte(is);
		}
		
		// Send a success response
		sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.OMNI_LINK_MESSAGE, omniLinkReply, true);
	}

	private void processSessionTerminatedRequest(SocketAddress clientAddress, int messageSeqNo) throws CommunicationException, IOException
	{		
		if (!clientAddress.equals(this.clientAddress))
		{
			// There is already another session in progress

			// Send an error
			sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.CONTROLLER_SESSION_TERMINATED, null, false);
			return;
		}

		// Send a success response
		sendPacket(clientAddress, messageSeqNo, NetworkMessageTypeEnum.CONTROLLER_SESSION_TERMINATED, null, false);
		
		// Remove the session
		this.clientAddress = null;
		this.sessionId = null;
		this.sessionKey = null;
	}

	private void sendPacket(SocketAddress clientAddress, int messageSeqNo, NetworkMessageTypeEnum messageType, byte[] appData, boolean encrypt) throws IOException, CommunicationException
	{
		// Write the sequence number (16 bits)
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(MessageHelper.highByte(messageSeqNo));
		baos.write(MessageHelper.lowByte(messageSeqNo));
		
		// Write the message type (8-bits)
		baos.write(messageType.getValue());

		// Write the reserved section (8-bits)
		baos.write(0);
		
		if (appData != null && appData.length > 0)
		{
			// Write the encrypted application data
			byte[] encryptedAppData = encrypt ? NetworkHelper.encrypt(appData, sessionKey, messageSeqNo) : appData;
			baos.write(encryptedAppData);
		}
		
		// Send the UDP packet
		DatagramPacket packet = new DatagramPacket(baos.toByteArray(), baos.size(), clientAddress);
		socket.send(packet);
	}

    private short readNextByte(InputStream is) throws IOException
    {
        short ret;
        while((ret = (short) is.read()) == -1);
        return ret;
    }

	private byte[] generateSessionID()
	{
		Random random = new Random();
		byte[] sessionId = new byte[5];
		random.nextBytes(sessionId);
		return sessionId;
	}	
}
