package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class SystemStatusRequest extends RequestMessage {
    public SystemStatusRequest() {
    }
    
    public void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
    		super.initialize(model, protocolType, 0x18, 0x01);
    	} else {
    		super.initialize(model, protocolType, 0x13, 0x01);
    	}
    }

    protected ReplyMessage createReplyMessage() {
        return new SystemStatusReport();
    }
}
