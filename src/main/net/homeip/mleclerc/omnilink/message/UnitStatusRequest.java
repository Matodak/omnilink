package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoRequestMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;

@SuppressWarnings("serial")
public class UnitStatusRequest extends MultipleInfoRequestMessage {
    public UnitStatusRequest(int unit) {
        this(unit, unit);
    }

    public UnitStatusRequest(int firstUnit, int lastUnit) {
    	super(firstUnit, lastUnit);
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
			super.initialize(model, protocolType, 0x22, 0x06, ObjectTypeEnum.UNIT);
		} else {
			super.initialize(model, protocolType, 0x17, 0x03);
		}
	}

	protected ReplyMessage createReplyMessage() {
    	if (ProtocolTypeHelper.isOmniLinkII(getProtocolType())) {
    		return new UnitStatusReport();
    	} else {
    		return new UnitStatusReport(firstNumber, lastNumber);
    	}
    }
}
