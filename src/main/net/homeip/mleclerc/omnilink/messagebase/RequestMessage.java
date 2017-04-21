package net.homeip.mleclerc.omnilink.messagebase;

import java.io.IOException;
import java.io.OutputStream;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public abstract class RequestMessage extends Message
{
    protected RequestMessage()
    {
        // Internal use only
    }
    
    public ReplyMessage execute(SystemTypeEnum model, ProtocolTypeEnum protocolType, MessageHandler handler) throws CommunicationException
    {
        // Send the message
    	sendRequest(model, protocolType, handler);

        // Create the reply object
        ReplyMessage ret = createReplyMessage();

        // Get the reply message
        ret.execute(model, protocolType, handler);

        return ret;
    }

    protected void sendRequest(SystemTypeEnum model, ProtocolTypeEnum protocolType, MessageHandler handler) throws CommunicationException
    {
    	// Get the output stream
		OutputStream os = handler.onPreHandleRequest(this);

    	// Initialize the message
    	initialize(model, protocolType);
    	
		// Send the request
    	sendRequest(os);
    	
    	// Notify handler
        handler.onPostHandleRequest(this, os);
    }
    
    protected void validate(SystemTypeEnum model) throws CommunicationException
    {
        // Allows subclasses to validate params based on the system model
        // Don't perform any validation by default
    }

    protected abstract ReplyMessage createReplyMessage() throws CommunicationException;

    private void sendRequest(OutputStream os) throws CommunicationException
    {
        try
        {        	
            // Send the message
            os.write(getBytes());
            os.flush();
        }
        catch(IOException ioe)
        {
            throw new CommunicationException(ioe);
        }
    }

    private byte[] getBytes() throws CommunicationException
    {
        short[] message = getMessageBytes();
        short[] crc = MessageHelper.getCRC(message);

        short[] fullMessage = new short[message.length + crc.length];
        System.arraycopy(message, 0, fullMessage, 0, message.length);
        System.arraycopy(crc, 0, fullMessage, message.length, crc.length);

        // Final conversion for the write operation
        byte[] ret = new byte[fullMessage.length];
        for (int i = 0; i < fullMessage.length; i++)
            ret[i] = (byte) fullMessage[i];

        // Make sure it's got the expected byte count
        if (ret.length != getExpectedByteCount())
            throw new CommunicationException("Does not have expected byte count");

        return ret;
    }
}
