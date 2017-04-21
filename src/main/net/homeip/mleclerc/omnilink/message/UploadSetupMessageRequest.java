package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.UploadMessageRequest;
import net.homeip.mleclerc.omnilink.messagebase.UploadMessageSmallReply;

@SuppressWarnings("serial")
public class UploadSetupMessageRequest extends UploadMessageRequest {
    public UploadSetupMessageRequest() {
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLink(protocolType);
		super.initialize(model, protocolType, 0x04, 0x01);
	}

    protected ReplyMessage createSmallReplyMessage() {
        return new UploadMessageSmallReply(0x02);
    }

    protected ReplyMessage createReplyMessage() {
        return new UploadSetupMessageReport();
    }
}
