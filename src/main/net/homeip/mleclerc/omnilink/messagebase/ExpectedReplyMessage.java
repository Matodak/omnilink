package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.EndOfDataException;

@SuppressWarnings("serial")
public abstract class ExpectedReplyMessage extends ReplyMessage
{
    private final static int MAX_EXPECTED_MESSAGE_LENGTH = 255;
    private final static int NEGATIVE_ACKNOWLEDGE = 0x02;
    protected final static int END_OF_DATA = 0x03;
    
    protected ExpectedReplyMessage()
    {
        // Internal use only
    }

    protected void checkMessageType(int messageType) throws CommunicationException {
    	if (messageType == NEGATIVE_ACKNOWLEDGE) {
    		throw new CommunicationException("Object not found");
    	} else if (messageType == END_OF_DATA) {
    		throw new EndOfDataException();
    	} else if (messageType != this.getMessageType()) {
            throw new CommunicationException("Bad message type: " + messageType + " should be: " + this.getMessageType());
        }
    }

    protected void checkMessageLength(int messageLength) throws CommunicationException
    {
        if (this.getMessageLength() > MAX_EXPECTED_MESSAGE_LENGTH)
            throw new CommunicationException("The expected reply length is too long. Please provide smaller range");

        if (messageLength != this.getMessageLength())
            throw new CommunicationException("Bad message length: " + messageLength + " should be: " + this.getMessageLength());

    }
}
