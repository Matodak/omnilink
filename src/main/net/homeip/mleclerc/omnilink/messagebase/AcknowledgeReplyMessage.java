package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public class AcknowledgeReplyMessage extends ExpectedReplyMessage
{
    public AcknowledgeReplyMessage() {
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
    		super.initialize(model, protocolType, 0x01, 0x01);
    	} else {
    		super.initialize(model, protocolType, 0x05, 0x01);    		
    	}
	}

	public String toString()
    {
        return "Command executed successfully.";
    }
}
