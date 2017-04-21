package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.FanModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.HoldModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ThermostatStatusReport extends MultipleInfoReplyMessage
{
    public class ThermostatStatusInfo extends Info
    {
        private boolean commFailure;
        private boolean freezeAlarm;
        private double currentTemp;
        private double lowSetPoint;
        private double highSetPoint;
        private SystemModeEnum systemMode;
        private FanModeEnum fanMode;
        private HoldModeEnum holdStatus;

        public ThermostatStatusInfo(int number)
        {
            super(number);
        }

        protected void dataChanged(short[] data) throws CommunicationException
        {
           // Extract the status byte
           short status = data[0];
           commFailure = MessageHelper.isBitOn(status, 0);
           freezeAlarm = MessageHelper.isBitOn(status, 1);

           // Extract the current temperature
           currentTemp = MessageHelper.omniToTemp(data[1]);

           // Extract the heat set point
           lowSetPoint = MessageHelper.omniToTemp(data[2]);

           // Extract the cool set point
           highSetPoint = MessageHelper.omniToTemp(data[3]);

           // Extract the system mode
           systemMode = (SystemModeEnum) MessageHelper.valueToEnum(SystemModeEnum.metaInfo, data[4]);

           // Extract the fan mode
           fanMode = (FanModeEnum) MessageHelper.valueToEnum(FanModeEnum.metaInfo, data[5]);

           // Extract the hold status
           holdStatus = (HoldModeEnum) MessageHelper.valueToEnum(HoldModeEnum.metaInfo, data[6]);
        }

        public int getThermostat()
        {
            return getNumber();
        }

        public boolean isCommunicationFailure()
        {
            return commFailure;
        }

        public boolean isFreezeAlarm()
        {
            return freezeAlarm;
        }

        public double getCurrentTemperature()
        {
            return currentTemp;
        }

        public double getLowSetPoint()
        {
            return lowSetPoint;
        }

        public double getHighSetPoint()
        {
            return highSetPoint;
        }

        public SystemModeEnum getSystemMode()
        {
            return systemMode;
        }

        public FanModeEnum getFanMode()
        {
            return fanMode;
        }

        public HoldModeEnum getHoldStatus()
        {
            return holdStatus;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("COMM FAILURE: " + isCommunicationFailure() + "\n");
            strbuf.append("FREEZE ALARM: " + isFreezeAlarm() + "\n");
            strbuf.append("LOW SETPOINT: " + getLowSetPoint() + "\n");
            strbuf.append("HIGH SETPOINT: " + getHighSetPoint() + "\n");
            strbuf.append("TEMPERATURE: " + getCurrentTemperature() + "\n");
            strbuf.append("FAN MODE: " + getFanMode() + "\n");
            strbuf.append("SYSTEM MODE: " + getSystemMode() + "\n");
            strbuf.append("HOLD STATUS: " + getHoldStatus() + "\n");
            return strbuf.toString();
        }
    }

    public ThermostatStatusReport() {
    }

    public ThermostatStatusReport(int firstThermo, int lastThermo) {
    	super(firstThermo, lastThermo);
    }

	protected void initialize(SystemTypeEnum model,ProtocolTypeEnum protocolType) throws CommunicationException {
    	if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
    		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 9, ObjectTypeEnum.THERMOSTAT);
    	} else {
    		super.initialize(model, protocolType, 0x1F, 7);
    	}
	}
	
    public Info createInfo(int number, short[] data)
    {
        return new ThermostatStatusInfo(number);
    }
}
