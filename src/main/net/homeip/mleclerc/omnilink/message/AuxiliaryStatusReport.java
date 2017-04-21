package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class AuxiliaryStatusReport extends MultipleInfoReplyMessage
{
    public class AuxiliaryStatusInfo extends Info
    {
        private boolean relayEnergized;
        private double currTemperature;
        private double lowSetpoint;
        private double highSetpoint;

        public AuxiliaryStatusInfo(int number)
        {
            super(number);
        }

        public void dataChanged(short[] data)
        {
            relayEnergized = (data[0] != 0);
            currTemperature = MessageHelper.omniToTemp(data[1]);
            lowSetpoint = MessageHelper.omniToTemp(data[2]);
            highSetpoint = MessageHelper.omniToTemp(data[3]);
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("RELAY ENERGIZED: " + relayEnergized + "\n");
            strbuf.append("CURRENT TEMPERATURE: " + currTemperature + "\n");
            strbuf.append("LOW SETPOINT: " + lowSetpoint + "\n");
            strbuf.append("HIGH SETPOINT: " + highSetpoint + "\n");
            return strbuf.toString();
        }
    }

    public AuxiliaryStatusReport() {
    }
    
    public AuxiliaryStatusReport(int firstSensor, int lastSensor) {
    	super(firstSensor, lastSensor);
    }

	protected void initialize(SystemTypeEnum model,ProtocolTypeEnum protocolType) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
    		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 6, ObjectTypeEnum.AUXILIARY_SENSOR);
		} else {
			super.initialize(model, protocolType, 0x1A, 4);
		}
	}

	protected Info createInfo(int number, short[] data) {
        return new AuxiliaryStatusInfo(number);
    }
}
