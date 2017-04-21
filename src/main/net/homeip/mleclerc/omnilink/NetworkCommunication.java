package net.homeip.mleclerc.omnilink;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.homeip.mleclerc.omnilink.enumeration.NetworkMessageTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.Message;
import net.homeip.mleclerc.omnilink.messagebase.MessageHandler;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.NetworkHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

public class NetworkCommunication implements MessageManager
{
	public final static int DEFAULT_TIMEOUT = 60; // 1 minute
	
    private MessageHandler messageHandler = new MessageHandlerImpl();

    private SystemTypeEnum model;
    private ProtocolTypeEnum protocolType;
    
    private InetAddress ipAddress;
    private int port;
    private Socket tcpSocket;
    private DatagramSocket udpSocket;
    private int timeout;
    private byte[] privateKey = new byte[16];
    
    private int messageSeqNo;
    private int protocolVersion;
    private byte[] sessionKey;
    private boolean open;
    private PendingReplyInfo pendingReplyInfo = new PendingReplyInfo();
    private Set<NotificationListener> listeners = new LinkedHashSet<NotificationListener>();

	public interface NotificationListener {
		void notify(Message notification) throws CommunicationException;
	}

	public NetworkCommunication(SystemTypeEnum model, String ipAddress, int port, String encryptionKey) throws CommunicationException
	{
		this(model, ipAddress, port, DEFAULT_TIMEOUT, encryptionKey, ProtocolTypeEnum.HAI_OMNI_LINK);
	}
	
	public NetworkCommunication(SystemTypeEnum model, String ipAddress, int port, int timeout, String encryptionKey) throws CommunicationException {
		this(model, ipAddress, port, timeout, encryptionKey, ProtocolTypeEnum.HAI_OMNI_LINK);
	}
	
	public NetworkCommunication(SystemTypeEnum model, String ipAddress, int port, int timeout, String encryptionKey, ProtocolTypeEnum protocolType) throws CommunicationException
	{
		// Validate parameters
		if (ipAddress == null)
			throw new CommunicationException("Invalid IP address");
		if (port < 0)
			throw new CommunicationException("Invalid port");
		if (encryptionKey.length() != 47)
			throw new CommunicationException("Private key must contain 16 hex values separated by -");

		try
		{
			this.model = model;
			this.ipAddress = InetAddress.getByName(ipAddress);
			this.port = port;
			this.timeout = timeout * 1000;
			this.protocolType = protocolType;
			this.open = false;
			
			// Convert the encryption key to a byte array
			String[] encryptionKeyValues = encryptionKey.split("-");
			for (int i = 0; i < encryptionKeyValues.length; i++)
			{
				String encryptionKeyValue = encryptionKeyValues[i];				
				privateKey[i] = (byte) MessageHelper.hex2decimal(encryptionKeyValue);
			}
		}
		catch(UnknownHostException ex) 
		{			
			throw new CommunicationException(ex);
		}
	}
	
	public int getProtocolVersion()
	{
		return protocolVersion;
	}
	
	public synchronized void open() throws CommunicationException 
	{
		if (open) {
			throw new CommunicationException("Session already open");
		}
		
		try 
		{
			// Connect to the system
			if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
				tcpSocket = new Socket(ipAddress, port);
				tcpSocket.setSoTimeout(0);
			} else {
				udpSocket = new DatagramSocket();
				udpSocket.setSoTimeout(timeout);
			}
			
			// Request a new session
			sendPacket(NetworkMessageTypeEnum.CLIENT_REQUEST_NEW_SESSION, null, false);
			PacketInfo replyPacketInfo = receivePacket(false, true);
			if (replyPacketInfo.messageType.equals(NetworkMessageTypeEnum.CONTROLLER_ACKNOWLEDGE_NEW_SESSION))
			{
				// Session created successfully				
			}
			else if (replyPacketInfo.messageType.equals(NetworkMessageTypeEnum.CONTROLLER_CANNOT_START_NEW_SESSION))
			{
				// Session creation failure
				throw new CommunicationException("Cannot start new session");
			}
			else 
			{
				// Unexpected response
				throw new CommunicationException("Unexpected message type: " + replyPacketInfo.messageType);
			}
			
			// Get protocol version and session ID (16 bits) starting at index 0
			protocolVersion = MessageHelper.createWord(replyPacketInfo.appData[0], replyPacketInfo.appData[1]);
			
			// Get session id (40 bits) starting at index 2
			byte[] sessionId = new byte[5];
			System.arraycopy(replyPacketInfo.appData, 2, sessionId, 0, 5);

			// Create the session key
			sessionKey = NetworkHelper.createSessionKey(privateKey, sessionId);
			
			// Listen for incoming messages
			listenForIncomingMessages();

			// Request secure connection
			sendPacket(NetworkMessageTypeEnum.CLIENT_REQUEST_SECURE_CONNECTION, sessionId, true);
			replyPacketInfo = receivePacket();
			if (replyPacketInfo.messageType.equals(NetworkMessageTypeEnum.CONTROLLER_ACKNOWLEDGE_SECURE_CONNECTION))
			{
				// Successful response				
			}
			else if (replyPacketInfo.messageType.equals(NetworkMessageTypeEnum.CONTROLLER_SESSION_TERMINATED))
			{
				// Unexpected session id
				throw new CommunicationException("Controller reports that the client session is not active or session IDs are not identical");
			}
			else
			{
				// Unexpected response
				throw new CommunicationException("Unexpected message type: " + replyPacketInfo.messageType);					
			}
			
			// Get session id (40 bits) starting at index 0
			byte[] repliedSessionId = Arrays.copyOfRange(replyPacketInfo.appData, 0, 5);
			
			// Make sure the session id is the same
			if (Arrays.equals(repliedSessionId, sessionId))
			{
				// Open procedure successful
				
				open = true;
			}
			else
			{
				// Unexpected session id
				throw new CommunicationException("Session IDs are not identical");
			}
		} 
		catch(SocketException ex) 
		{
			throw new CommunicationException(ex);
		}
		catch(IOException ex) 
		{
			throw new CommunicationException(ex);
		}		
	}

	public synchronized void close() throws CommunicationException
	{
		close(true);
	}
	
	private void close(boolean terminateSession) throws CommunicationException
	{
		if (!open) {
			throw new CommunicationException("Session not open");
		}
		
		if (tcpSocket == null && udpSocket == null) {
			throw new CommunicationException("Session not open");
		}
		
		try
		{
			if (terminateSession)
			{
				// Close the session
				sendPacket(NetworkMessageTypeEnum.CLIENT_SESSION_TERMINATED, null, false);
				PacketInfo replyPacketInfo = receivePacket();
				if (replyPacketInfo.messageType.equals(NetworkMessageTypeEnum.CONTROLLER_SESSION_TERMINATED))
				{
					// Session close successfully
				}
				else
				{
					throw new CommunicationException("Unexpected message type: " + replyPacketInfo.messageType);
				}
			}
	
			open = false;
			
			// Close the socket
			if (tcpSocket != null) {
				tcpSocket.close();
				tcpSocket = null;
			} else if (udpSocket != null) {
				udpSocket.close();
				udpSocket = null;
			} else {
				throw new CommunicationException("No socket open");
			}
		} catch(SocketTimeoutException ex) {
			// Fail silently
			open = false;
		} catch(IOException ex) {
			throw new CommunicationException(ex);
		}
	}

	public synchronized ReplyMessage execute(RequestMessage request) throws CommunicationException 
	{		
        if (!open) {
        	throw new CommunicationException("Communication closed");
        }
        
		// Validate parameter
        if (request == null) {
            throw new CommunicationException("Null request passed in");
        }
        
        if (tcpSocket != null) {
        	try {
        		return request.execute(model, protocolType, messageHandler);
        	} catch(CommunicationException ex) {
        		if (ex.getCause() != null && ex.getCause() instanceof IOException) {
        			close(false);
        		}
        		throw ex;
        	}
        } else if (udpSocket != null) {
	        // Retry mechanism for requests (UDP only)
	        while(true) {
		        try {
					// Execute the request
			        return request.execute(model, protocolType, messageHandler);
		        } catch(CommunicationException ex) {
		        	// Send request again if receive on socket timed out
		        	if (ex.getCause() != null && ex.getCause() instanceof SocketTimeoutException) {
		        		// Socket timeout. Sending request again
		        	} else {
		        		throw ex;
		        	}
		        }
	        }
        } else {
			throw new CommunicationException("No socket open");
		}
	}

	public synchronized boolean isOpen() throws CommunicationException 
	{
		return open;
	}
	
	private void sendPacket(NetworkMessageTypeEnum messageType, byte[] appData, boolean encrypt) throws IOException, CommunicationException
	{
		// Increment the sequence number
		if (messageSeqNo <= 0 || messageSeqNo >= 65535)
		{
			messageSeqNo = 1;
		}
		else
		{
			messageSeqNo++;
		}
		
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
		
		// Keep the expected sequence number of the reply
		synchronized(pendingReplyInfo) {
			pendingReplyInfo.messageSeqNo = messageSeqNo;
			pendingReplyInfo.packetInfo = null;
		}

		// Send the request
		if (tcpSocket != null) {
			tcpSocket.getOutputStream().write(baos.toByteArray());
		} else if (udpSocket != null){
			DatagramPacket packet = new DatagramPacket(baos.toByteArray(), baos.size(), ipAddress, port);
			udpSocket.send(packet);
		} else {
			throw new CommunicationException("No socket open");
		}
	}
	
	private PacketInfo receivePacket() throws SocketTimeoutException {
		synchronized(pendingReplyInfo) {
			// Wait for reply if yet not received
			if (pendingReplyInfo.packetInfo == null) {
				try { pendingReplyInfo.wait(timeout); } catch(InterruptedException ex) {}
				if (pendingReplyInfo.packetInfo == null) {
					throw new SocketTimeoutException();
				}
			}
			
			// Reset reply info and return received packet
			PacketInfo packetInfo = pendingReplyInfo.packetInfo;
			pendingReplyInfo.messageSeqNo = -1;
			pendingReplyInfo.packetInfo = null;
			return packetInfo;
		}
	}
	
	private PacketInfo receivePacket(boolean decrypt, boolean validateSeqNo) throws IOException, CommunicationException {
		return receivePackets(decrypt, validateSeqNo)[0];
	}
	
	private PacketInfo[] receivePackets(boolean decrypt, boolean validateSeqNo) throws IOException, CommunicationException
	{
		// Get the response
		byte[] bytes = new byte[1024];
		int byteCount;
		if (tcpSocket != null) {
			byteCount = tcpSocket.getInputStream().read(bytes);
		} else if (udpSocket != null) {
			DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
			udpSocket.receive(packet);
			byteCount = packet.getLength();
		} else {
			throw new SocketException("No socket open");
		}
		
		// Make sure the socket is still open
		if (byteCount == -1) {
			throw new SocketException("Socket closed");
		}

		// Extract the packets from the data received
		List<PacketInfo> packetsInfo = new ArrayList<PacketInfo>();
		int startIndex = 0;
		while(startIndex < byteCount) {
			// Get the sequence number (16-bits) starting at index 0
			short seqHigh = MessageHelper.getBits(bytes[startIndex++], 0, 8); // This is converts from byte to short in order to remove negative numbers
			short seqLow = MessageHelper.getBits(bytes[startIndex++], 0, 8);  // This is converts from byte to short in order to remove negative numbers
			int messageSeqNo = MessageHelper.createWord(seqHigh, seqLow);
			if (validateSeqNo && messageSeqNo != this.messageSeqNo)
			{
				throw new CommunicationException("Unexpected sequence number");
			}
			
			// Get the message type (8-bits) at index 2
			int messageTypeVal = bytes[startIndex++];
			NetworkMessageTypeEnum messageType = (NetworkMessageTypeEnum) NetworkMessageTypeEnum.metaInfo.getByValue(messageTypeVal);
			
			// Get the reserved section (8-bits) at index 3
			int reservedByte = bytes[startIndex++];
			if (reservedByte != 0)
			{
				throw new CommunicationException("Unexpected value for reserved section");
			}

			// Now extract the packet's application data
			byte[] appData = (messageType == NetworkMessageTypeEnum.OMNI_LINK_MESSAGE || messageType == NetworkMessageTypeEnum.OMNI_LINK_MESSAGE_II) ? getAppData(startIndex, bytes, decrypt, messageSeqNo) : getAppData(startIndex, byteCount - startIndex, bytes, decrypt, messageSeqNo);
			
			// Fill the return data
			PacketInfo packetInfo = new PacketInfo();
			packetInfo.messageSeqNo = messageSeqNo;
			packetInfo.messageType = messageType;
			packetInfo.appData = appData;
			packetsInfo.add(packetInfo);
			
			// Move to the next packet
			startIndex += appData.length;
		}
		
		return packetsInfo.toArray(new PacketInfo[0]);
	}
	
	private byte[] getAppData(int startIndex, byte[] bytes, boolean decrypt, int messageSeqNo) throws CommunicationException {
		byte[] appData = getAppData(startIndex, 16, bytes, decrypt, messageSeqNo);		
		int messageLength = MessageHelper.byte2short(appData[1]);
		int additionalAppDataBlockCount = (int) Math.ceil((messageLength + 4) / 16.0) - 1;
		for (int i = 0; i < additionalAppDataBlockCount; i++) {
			byte[] additionalAppData = getAppData(startIndex + appData.length, 16, bytes, decrypt, messageSeqNo);
			byte[] previousAppData = appData;
			appData = new byte[previousAppData.length + additionalAppData.length];
			System.arraycopy(previousAppData, 0, appData, 0, previousAppData.length);
			System.arraycopy(additionalAppData, 0, appData, previousAppData.length, additionalAppData.length);
		}
		
		return appData;
	}
	
	private byte[] getAppData(int startIndex, int length, byte[] bytes, boolean decrypt, int messageSeqNo) throws CommunicationException {
		byte[] encryptedAppData = new byte[length];
		System.arraycopy(bytes, startIndex, encryptedAppData, 0, encryptedAppData.length);
		return decrypt ? NetworkHelper.decrypt(encryptedAppData, sessionKey, messageSeqNo) : encryptedAppData;
	}

	private void listenForIncomingMessages() {
		// Run in a separate thread
		Runnable exec = new Runnable() {
			public void run() {			
				while(true){
					try {
						// Receive the next messages (blocking)
						PacketInfo[] packetsInfo = receivePackets(true, false);
						for (PacketInfo packetInfo : packetsInfo) {
							if (packetInfo.messageSeqNo == 0) {
								// Notification received
								Message notification = NotificationFactory.createNotification(model, protocolType, packetInfo);
								if (notification != null) {
									// Notify listeners of incoming notification
									notifyListeners(notification);								
								} else {
									// Unsupported notification
									throw new CommunicationException("Unexpected notification. Data received: " + packetInfo.appData[0] + " " + packetInfo.appData[1] + " " + packetInfo.appData[2] + " " + packetInfo.appData[3]);								
								}
							} else {
								synchronized(pendingReplyInfo) {
									if (packetInfo.messageSeqNo == pendingReplyInfo.messageSeqNo) {
										// Notify blocked request execution of incoming reply
										pendingReplyInfo.packetInfo = packetInfo;
										pendingReplyInfo.notify();
									} else {
										// Unexpected message
										throw new CommunicationException("Unexpected message: message type: " + packetInfo.appData[2]);
									}
								}
							}
						}
					} catch(CommunicationException ex) {
						ex.printStackTrace();	
					} catch(SocketTimeoutException ex) {
						// Timeout reading socket
						// Keep going
					} catch(SocketException ex) {
						// Socket is closed
						// Terminate the thread
						break;
					} catch(IOException ex) {
						// Socket closed
						break;
					}
				}
			}
		};
		new Thread(exec).start();
	}
	
	private void notifyListeners(Message notification) throws CommunicationException {
		synchronized(listeners) {
			for (NotificationListener listener : listeners) {
				listener.notify(notification);
			}
		}
	}
	public void addListener(NotificationListener listener) {
		synchronized(listeners) {
			listeners.add(listener);
		}
	}
	
	public void removeListener(NotificationListener listener) {
		synchronized(listeners) {
			listeners.remove(listener);
		}
	}
	
	class PacketInfo
	{
		public int messageSeqNo;
		public NetworkMessageTypeEnum messageType;
		public byte[] appData;
	}
	
	class PendingReplyInfo {
		int messageSeqNo;
		PacketInfo packetInfo;
	}
	
    private class MessageHandlerImpl implements MessageHandler 
    {
		public OutputStream onPreHandleRequest(RequestMessage request) throws CommunicationException 
		{
			return new ByteArrayOutputStream(); 
		}        	

		public void onPostHandleRequest(RequestMessage request, OutputStream os) throws CommunicationException
		{
			try
			{
				// Send the request 
				ByteArrayOutputStream baos = (ByteArrayOutputStream) os;
				sendPacket(ProtocolTypeHelper.isOmniLinkII(protocolType) ? NetworkMessageTypeEnum.OMNI_LINK_MESSAGE_II : NetworkMessageTypeEnum.OMNI_LINK_MESSAGE, baos.toByteArray(), true);
				
				// Close the output stream
				os.close();
			}
			catch(IOException ex)
			{
				throw new CommunicationException(ex);
			}
		}

		public InputStream onPreHandleReply(ReplyMessage reply) throws CommunicationException
		{
			try
			{
				// Receive the reply
				PacketInfo packetInfo = receivePacket();
				if (ProtocolTypeHelper.isOmniLink(protocolType) && packetInfo.messageType.equals(NetworkMessageTypeEnum.OMNI_LINK_MESSAGE)) 
				{
					// Successful response
				}
				else if (ProtocolTypeHelper.isOmniLinkII(protocolType) && packetInfo.messageType.equals(NetworkMessageTypeEnum.OMNI_LINK_MESSAGE_II))
				{
					// Successful response												
				}
				else if (packetInfo.messageType.equals(NetworkMessageTypeEnum.CONTROLLER_SESSION_TERMINATED))
				{
					throw new CommunicationException("Controller reports that there is no active session");
				}
				else
				{
					throw new CommunicationException("Unexpected message type: " + packetInfo.messageType);						
				}
				
				// Create the input stream containing the reply
				return new ByteArrayInputStream(packetInfo.appData);
			}
			catch(IOException ex)
			{
				throw new CommunicationException(ex);
			}
		}

		public void onPostHandleReply(ReplyMessage reply, InputStream is) throws CommunicationException
		{
			try
			{
				// Close the input stream
				is.close();					
			}
			catch(IOException ex)
			{
				throw new CommunicationException(ex);
			}
		}
	}
}
