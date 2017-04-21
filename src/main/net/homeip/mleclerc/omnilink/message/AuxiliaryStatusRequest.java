package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoRequestMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;

@SuppressWarnings("serial")
public class AuxiliaryStatusRequest extends MultipleInfoRequestMessage {
    public AuxiliaryStatusRequest(int sensor) {
        this(sensor, sensor);
    }

    public AuxiliaryStatusRequest(int firstSensor, int lastSensor) {
    	super(firstSensor, lastSensor);
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
			super.initialize(model, protocolType, 0x22, 0x06, ObjectTypeEnum.AUXILIARY_SENSOR);
		} else {
			super.initialize(model, protocolType, 0x19, 0x03);
		}
	}

	protected ReplyMessage createReplyMessage() {
		if (ProtocolTypeHelper.isOmniLinkII(getProtocolType())) {
			return new AuxiliaryStatusReport();
		} else {
			return new AuxiliaryStatusReport(firstNumber, lastNumber);
		}
    }
}
