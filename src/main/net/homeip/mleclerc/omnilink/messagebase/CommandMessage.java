package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public abstract class CommandMessage extends RequestMessage
{
    protected CommandMessage() {
    }

    protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType, int command, int p1, int p2) throws CommunicationException {
    	short[] data = new short[] {(short) command, (short) p1, MessageHelper.highByte(p2), MessageHelper.lowByte(p2)};
    	if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
    		super.initialize(model, protocolType, 0x14, 0x05, data);
    	} else {
    		super.initialize(model, protocolType, 0x0F, 0x05, data);
    	}
    }

    protected ReplyMessage createReplyMessage() {
        return new AcknowledgeReplyMessage();
    }
}
