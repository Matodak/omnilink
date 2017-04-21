package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public class UploadMessageSmallReply extends ReplyMessage
{
    private final static int END_OF_DATA_TYPE = 0x03;
    private final static int END_OF_DATA_LENGTH = 0x01;

    private final int dataMessageType;

    public UploadMessageSmallReply(int dataMessageType)
    {
        this.dataMessageType = dataMessageType;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		super.initialize(model, protocolType, 0, 0);
	}

	public boolean execute(SystemTypeEnum model, ProtocolTypeEnum protocolType, MessageHandler handler) throws CommunicationException
    {
        // Parse the reply
        if (super.execute(model, protocolType, handler))
        {
            int type = getMessageType();
            int length = getMessageLength();
            if (type == END_OF_DATA_TYPE && length == END_OF_DATA_LENGTH)
                return false;
            else if (type == dataMessageType)
                return true;
            else
                throw new CommunicationException("Unexpected reply type: " + type);
        }

        return true;
    }
}
