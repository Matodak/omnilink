package net.homeip.mleclerc.omnilink.messagebase;

import java.io.IOException;
import java.io.InputStream;

import net.homeip.mleclerc.omnilink.BadStartCharacterException;
import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public abstract class ReplyMessage extends Message
{
    private final static short UNACKNOWLEDGE_TYPE = 0x06;

    protected ReplyMessage()
    {
        // Need to call parse reply to fill in the message attributes
    }

    public boolean execute(SystemTypeEnum model, ProtocolTypeEnum protocolType, MessageHandler listener) throws CommunicationException {
    	// Initialize the message
    	initialize(model, protocolType);
    	
        // Get the input stream
    	InputStream is = listener.onPreHandleReply(this);
        if (parseReply(is)) {            
            // Notify listener
        	listener.onPostHandleReply(this, is);
        	return true;
        }
        
        return false;
    }

    public boolean execute(SystemTypeEnum model, ProtocolTypeEnum protocolType, InputStream is) throws CommunicationException {
    	// Initialize the message
    	initialize(model, protocolType);
    	
    	// Read the reply
    	return parseReply(is);
    }
    
    private boolean parseReply(InputStream is) throws CommunicationException
    {
        try
        {            
            // Check start char
            short startChar = readNextByte(is);
            if (startChar != getStartChar())
                throw new BadStartCharacterException(startChar, getStartChar());

            // Check message length
            short messageLength = readNextByte(is);

            // Check message type
            short messageType = readNextByte(is);
            if (messageType == UNACKNOWLEDGE_TYPE && ProtocolTypeHelper.isOmniLink(getProtocolType()))
                throw new CommunicationException("Unable to perform request. User login may be required.");

            // Validate message type and length
            checkMessageType(messageType);
            checkMessageLength(messageLength);

            // Get data
            short[] data = new short[messageLength - 1];
            for (int i = 0; i < data.length; i++)
                data[i] = readNextByte(is);

            // Set the message object attributes
            setMessageFields(messageType, messageLength, data);

            // Get CRC
            short[] crc = readNextBytes(is, 2);

            // Validate CRC
            short[] message = getMessageBytes();
            short[] expectedCRC = MessageHelper.getCRC(message);
            for (int i = 0; i < crc.length; i++)
                if (crc[i] != expectedCRC[i])
                    throw new CommunicationException("Bad CRC");

            // Validate the message size
            if (message.length + crc.length != getExpectedByteCount())
                throw new CommunicationException("Invalid message size");

            // Notify observer that data has changed
            dataChanged(data);
        }
        catch(IOException ioe)
        {
            throw new CommunicationException(ioe);
        }
        finally
        {
            // The parsing of the reply might have been interrupted
            // Eat the remaining bytes in the input stream
            eatNextBytes(is);
        }

        return true;
    }

    protected void checkMessageType(int messageType) throws CommunicationException
    {
        // Nothing to do by default
    }

    protected void checkMessageLength(int messageLength) throws CommunicationException
    {
        // Nothing to do by default
    }

    protected void dataChanged(short[] data) throws CommunicationException
    {
        // Nothing to do by default
    }

    private short[] readNextBytes(InputStream is, int length) throws CommunicationException, IOException
    {
        short[] message = new short[length];
        for (int i = 0; i < message.length; i++)
            message[i] = readNextByte(is);

        return message;
    }

    private short readNextByte(InputStream is) throws IOException
    {
        short ret;
        while((ret = (short) is.read()) == -1);
        return ret;
    }

    private void eatNextBytes(InputStream is) throws CommunicationException
    {
        try
        {
            int n = 0;
            while((n = is.available()) > 0)
            {
                is.skip(n);
                Thread.sleep(100);
            }
        }
        catch(IOException ioe)
        {
            throw new CommunicationException(ioe);
        }
        catch (InterruptedException ie)
        {
            throw new CommunicationException(ie);
        }
    }
}
