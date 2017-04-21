package net.homeip.mleclerc.omnilink.messagebase;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import net.homeip.mleclerc.omnilink.CommunicationException;

public class NetworkHelper 
{
	public static byte[] encrypt(byte[] data, byte[] sessionKey, int messageSeqNo) throws CommunicationException
	{
		// Validate parameters
		if (data == null)
			throw new CommunicationException("Invalid data");
		if (sessionKey.length != 16)
			throw new CommunicationException("Invalid session key");
	
		try 
		{
			int dataBlockCount = (int) Math.ceil((double) data.length / 16);
			byte[] encryptedData = new byte[dataBlockCount * 16];
			
			for (int i = 0; i < dataBlockCount; i++)
			{
				// Fill the encryption block (128 bits). Right fill with zeros if needed
				byte[] dataBlock = new byte[16];
				Arrays.fill(dataBlock, (byte) 0);
				System.arraycopy(data, i * 16, dataBlock, 0, Math.min(16, data.length - (i * 16)));
				
				// Perform a logical XOR operation between the first two bytes of the encryption block and
				// the first two bytes of the message sequence number
				dataBlock[0] = (byte) (MessageHelper.highByte(messageSeqNo) ^ dataBlock[0]);
				dataBlock[1] = (byte) (MessageHelper.lowByte(messageSeqNo) ^ dataBlock[1]);		
				
				// Encrypt the block
				SecretKeySpec skeySpec = new SecretKeySpec(sessionKey, "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
				byte[] encryptedDataBlock = cipher.doFinal(dataBlock);
				
				// Store the block into the result array
				System.arraycopy(encryptedDataBlock, 0, encryptedData, i * 16, 16);
			}
			
			return encryptedData;
		} 
		catch (InvalidKeyException ex) 
		{
			throw new CommunicationException(ex);
		} 
		catch (NoSuchAlgorithmException ex) 
		{
			throw new CommunicationException(ex);
		}
		catch (NoSuchPaddingException ex) 
		{
			throw new CommunicationException(ex);
		}
		catch (IllegalBlockSizeException ex) 
		{
			throw new CommunicationException(ex);
		}
		catch (BadPaddingException ex) 
		{
			throw new CommunicationException(ex);
		}
	}

	public static byte[] decrypt(byte[] data, byte[] sessionKey, int messageSeqNo) throws CommunicationException
	{
		// Validate parameters
		if (data == null)
			throw new CommunicationException("Invalid data");
		if (data.length % 16 != 0)
			throw new CommunicationException("Data is of invalid length: " + data.length);
		if (sessionKey.length != 16)
			throw new CommunicationException("Invalid session key");
		
		try 
		{
			int dataBlockCount = data.length / 16;
			byte[] decryptedData = new byte[dataBlockCount * 16];
			
			for (int i = 0; i < dataBlockCount; i++)
			{
				// Prepare the block
				byte[] dataBlock = new byte[16];
				System.arraycopy(data, i * 16, dataBlock, 0, 16);
	
				// Decrypt the block
				SecretKeySpec skeySpec = new SecretKeySpec(sessionKey, "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
				byte[] decryptedDataBlock = cipher.doFinal(dataBlock);
				
				// Perform a logical XOR operation between the first two bytes of the encryption block and
				// the first two bytes of the message sequence number
				decryptedDataBlock[0] = (byte) (MessageHelper.highByte(messageSeqNo) ^ decryptedDataBlock[0]);
				decryptedDataBlock[1] = (byte) (MessageHelper.lowByte(messageSeqNo) ^ decryptedDataBlock[1]);		
	
				// Store the block into the result array
				System.arraycopy(decryptedDataBlock, 0, decryptedData, i * 16, 16);
			}
			
			return decryptedData;
		} 
		catch (InvalidKeyException ex) 
		{
			throw new CommunicationException(ex);
		} 
		catch (NoSuchAlgorithmException ex) 
		{
			throw new CommunicationException(ex);
		}
		catch (NoSuchPaddingException ex) 
		{
			throw new CommunicationException(ex);
		}
		catch (IllegalBlockSizeException ex) 
		{
			throw new CommunicationException(ex);
		}
		catch (BadPaddingException ex) 
		{
			throw new CommunicationException(ex);
		}
	}

	public static byte[] createSessionKey(byte[] privateKey, byte[] sessionId) throws CommunicationException
	{
		// Validate parameters
		if (privateKey.length != 16)
			throw new CommunicationException("Invalid private key");
		if (sessionId.length != 5)
			throw new CommunicationException("Invald session ID");
		
		byte[] sessionKey = new byte[16]; // 128 bits
		
		// The 88 most significant bits (11 bytes) are the same as the private key
		System.arraycopy(privateKey, 0, sessionKey, 0, 11);
	
		// The 40 least significant bits (5 bytes) are the result of a logical XOR 
		// of the least significant bits of the private key and the 40 bits of the session ID. 
		for (int i = 0; i < 5; i++)
		{
			sessionKey[i + 11] = (byte) (privateKey[i + 11] ^ sessionId[i]);
		}
	
		return sessionKey;
	}
}
