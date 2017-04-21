package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.AcknowledgeReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class NotificationsCommand extends RequestMessage {
	private boolean enable;
	
    public NotificationsCommand() {
        this(true);
    }

    public NotificationsCommand(boolean enable) {
    	this.enable = enable;
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
        short[] data = new short[1];
        data[0] = (short) (enable ? 1: 0);
		super.initialize(model, protocolType, 0x15, 0x02, data);
	}

	protected ReplyMessage createReplyMessage(){
        return new AcknowledgeReplyMessage();
    }
}
