package net.homeip.mleclerc.omnilink.messagebase;

import java.io.Serializable;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public abstract class Message implements Serializable
{
    public final static short START_CHAR = 0x5A;
    private final static short START_CHAR_OMNILINK_II = 0x21;

    private short messageType;
    private short messageLength;
    private short[] data;
    private SystemTypeEnum model;
    private ProtocolTypeEnum protocolType;
    
    protected Message()
    {
        // Internal use only
    }

    protected abstract void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException;
    
    protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType, int messageType, int messageLength) throws CommunicationException {
    	initialize(model, protocolType, messageType, messageLength, new short[0]);
    }

    protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType, int messageType, int messageLength, short[] data) throws CommunicationException {
    	this.model = model;
    	this.protocolType = protocolType;
        setMessageFields(messageType, messageLength, data);
    }

    protected short getStartChar() {
    	return ProtocolTypeHelper.isOmniLinkII(protocolType) ? START_CHAR_OMNILINK_II : START_CHAR;
    }
    
    protected void setMessageFields(int messageType, int messageLength, short[] data)
    {
        this.messageType = (short) messageType;
        this.messageLength = (short) messageLength;
        setData(data);
    }

    private void setData(short[] data)
    {
        this.data = data;
    }

    protected ProtocolTypeEnum getProtocolType() {
    	return protocolType;
    }
    
    protected int getMessageType()
    {
        return messageType;
    }

    protected int getMessageLength()
    {
        return messageLength;
    }

    protected short[] getData()
    {
        return data;
    }

    protected SystemTypeEnum getSystemType() {
    	return model;
    }
    
    protected int getExpectedByteCount()
    {
        return messageLength + 4; // Includes start char, message length and CRC bytes
    }
    
    protected short[] getMessageBytes() throws CommunicationException
    {
        // Make sure the message type and length are set
        if (messageType < 0)
            throw new CommunicationException("Message type not set");

        // Make sure the message type and length are set
        if (messageLength < 0)
            throw new CommunicationException("Message length not set");

        // Make sure the total length is ok
        if (data.length != messageLength - 1)
            throw new CommunicationException("Invalid message length");

        short[] ret = new short[3 + data.length];
        ret[0] = getStartChar();
        ret[1] = messageLength;
        ret[2] = messageType;
        System.arraycopy(data, 0, ret, 3, data.length);

        return ret;
    }
}
