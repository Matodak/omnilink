package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ArmingStatusEnum;
import net.homeip.mleclerc.omnilink.enumeration.LatchedAlarmStatusEnum;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ZoneConditionEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ZoneStatusReport extends MultipleInfoReplyMessage
{
    public class ZoneStatusInfo extends Info
    {
        private ZoneConditionEnum condition;
        private LatchedAlarmStatusEnum latchedAlarmStatus;
        private ArmingStatusEnum armingStatus;
        private int analogLoopReading;
        private boolean unacknowledgedTroubleCondition;

        public ZoneStatusInfo(int zone)
        {
            super(zone);
        }

        protected void dataChanged(short[] data) throws CommunicationException
        {
            short status = data[0];
            condition = (ZoneConditionEnum) MessageHelper.valueToEnum(ZoneConditionEnum.metaInfo, MessageHelper.getBits(status, 0, 2));
            latchedAlarmStatus = (LatchedAlarmStatusEnum) MessageHelper.valueToEnum(LatchedAlarmStatusEnum.metaInfo, MessageHelper.getBits(status, 2, 2));
            armingStatus = (ArmingStatusEnum) MessageHelper.valueToEnum(ArmingStatusEnum.metaInfo, MessageHelper.getBits(status, 4, 2));
            unacknowledgedTroubleCondition = MessageHelper.isBitOn(status, 5);
            analogLoopReading = data[1];
        }

        public int getZone()
        {
            return getNumber();
        }

        public ZoneConditionEnum getCondition()
        {
            return condition;
        }

        public LatchedAlarmStatusEnum getLatchedAlarmStatus()
        {
            return latchedAlarmStatus;
        }

        public ArmingStatusEnum getArmingStatus()
        {
            return armingStatus;
        }

        public int getAnalogLoopReading()
        {
            return analogLoopReading;
        }

        public boolean isUnacknowledgedTroubleCondition() {
        	return unacknowledgedTroubleCondition;
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("CONDITION: " + getCondition() + "\n");
            strbuf.append("LATCHED ALARM STATUS: " + getLatchedAlarmStatus() + "\n");
            strbuf.append("ARMING STATUS: " + getArmingStatus() + "\n");
            strbuf.append("UNACKNOWLEDGED TROUBLE CONDITION: " + isUnacknowledgedTroubleCondition() + "\n");
            strbuf.append("ANALOG LOOPBACK READING: " + getAnalogLoopReading() + "\n");
            return strbuf.toString();
        }
    }

    public ZoneStatusReport() {
    }
    
    public ZoneStatusReport(int firstZone, int lastZone) {
    	super(firstZone, lastZone);
    }

    protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
    		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 4, ObjectTypeEnum.ZONE);
    	} else {
    		super.initialize(model, protocolType, 0x16, 2);
    	}
    }

    protected Info createInfo(int number, short[] data)
    {
        return new ZoneStatusInfo(number);
    }
}
