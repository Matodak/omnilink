package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoRequestMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;

@SuppressWarnings("serial")
public class ZoneStatusRequest extends MultipleInfoRequestMessage {
    public ZoneStatusRequest(int zone) {
        this(zone, zone);
    }

    public ZoneStatusRequest(int firstZone, int lastZone) {
    	super(firstZone, lastZone);
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
			super.initialize(model, protocolType, 0x22, 0x06, ObjectTypeEnum.ZONE);
		} else {
	        int maxZones = MessageHelper.getMaxZones(model);
	        if (firstNumber > maxZones || lastNumber > maxZones)
	            throw new CommunicationException("Range exceeds the number of zones supported by this system: " + maxZones);
	        
			super.initialize(model, protocolType, 0x15, 0x03);
		}
	}

    protected ReplyMessage createReplyMessage() {
    	if (ProtocolTypeHelper.isOmniLinkII(getProtocolType())) {
    		return new ZoneStatusReport();
    	} else {
    		return new ZoneStatusReport(firstNumber, lastNumber);
    	}
    }
}
