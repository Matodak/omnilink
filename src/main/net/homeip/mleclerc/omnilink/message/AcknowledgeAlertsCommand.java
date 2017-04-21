package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.AcknowledgeReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class AcknowledgeAlertsCommand extends RequestMessage {
	private int areaNo;

    public AcknowledgeAlertsCommand() {
    	this(MessageConstants.DEFAULT_AREA);
    }

    public AcknowledgeAlertsCommand(int areaNo) {
    	this.areaNo = areaNo;
    }
    
    public void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
    	short[] data = new short[1];
    	data[0] = (short) areaNo;
		super.initialize(model, protocolType, 0x3C, 0x02, data);
    }

    protected ReplyMessage createReplyMessage() {
        return new AcknowledgeReplyMessage();
    }
}
