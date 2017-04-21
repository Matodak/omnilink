package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public class AcknowledgeMessage extends RequestMessage
{
	public AcknowledgeMessage() {
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		super.initialize(model, protocolType, 0x05, 0x01);
	}

	protected ReplyMessage createReplyMessage() throws CommunicationException 
	{
		return null;
	}
}
