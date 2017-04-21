package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.EmergencyTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.AcknowledgeReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class ActivateKeypadEmergencyCommand extends RequestMessage {
	private int areaNo;
	private EmergencyTypeEnum emergencyType;

    public ActivateKeypadEmergencyCommand(EmergencyTypeEnum emergencyType) {
    	this(emergencyType, MessageConstants.DEFAULT_AREA);
    }

    public ActivateKeypadEmergencyCommand(EmergencyTypeEnum emergencyType, int areaNo) {
    	this.areaNo = areaNo;
    	this.emergencyType = emergencyType;
    }
    
    public void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
    	short[] data = new short[2];
    	data[0] = (short) areaNo;
    	data[1] = (short) emergencyType.getValue();
		super.initialize(model, protocolType, 0x2C, 0x03, data);
    }

    protected ReplyMessage createReplyMessage() {
        return new AcknowledgeReplyMessage();
    }
}
