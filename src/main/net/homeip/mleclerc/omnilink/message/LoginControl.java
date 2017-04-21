package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.AcknowledgeReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class LoginControl extends RequestMessage {
	private String userCode;
	
    public LoginControl(String userCode) {
    	this.userCode = userCode;
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLink(protocolType);
        short[] data = MessageHelper.userCodeToBytes(userCode);
		super.initialize(model, protocolType, 0x20, 0x05, data);
	}

	protected ReplyMessage createReplyMessage() {
        return new AcknowledgeReplyMessage();
    }
}
