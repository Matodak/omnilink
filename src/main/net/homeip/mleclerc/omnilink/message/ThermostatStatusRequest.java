package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoRequestMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;

@SuppressWarnings("serial")
public class ThermostatStatusRequest extends MultipleInfoRequestMessage {
    public ThermostatStatusRequest() {
        this(MessageConstants.DEFAULT_TEMP_ZONE);
    }

    public ThermostatStatusRequest(int tempZone) {
        this(tempZone, tempZone);
    }

    public ThermostatStatusRequest(int firstThermo, int lastThermo) {
    	super(firstThermo, lastThermo);
    }
    
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
			super.initialize(model, protocolType, 0x22, 0x06, ObjectTypeEnum.THERMOSTAT);
		} else {
			super.initialize(model, protocolType, 0x1E, 0x03);
		}
	}

	protected ReplyMessage createReplyMessage() {
		if (ProtocolTypeHelper.isOmniLinkII(getProtocolType())) {
			return new ThermostatStatusReport();
		} else {
			return new ThermostatStatusReport(firstNumber, lastNumber);
		}
    }
}
