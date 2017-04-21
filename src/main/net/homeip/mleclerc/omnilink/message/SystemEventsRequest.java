package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class SystemEventsRequest extends RequestMessage
{
    public SystemEventsRequest() {
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLink(protocolType);
        // Unfortunately, we don't know in advance how many system events
        // we'll get in the reply
		super.initialize(model, protocolType, 0x22, 0x01);
	}

	protected ReplyMessage createReplyMessage() {
        return new SystemEventsReport();
    }
}
