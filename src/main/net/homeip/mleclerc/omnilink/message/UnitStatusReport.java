package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.UnitControlEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class UnitStatusReport extends MultipleInfoReplyMessage
{
    public class UnitStatusInfo extends Info
    {
        private UnitControlEnum condition;
        private int time;
        private int value;

        public UnitStatusInfo(int unit)
        {
            super(unit);
        }

        public void dataChanged(short[] data) throws CommunicationException
        {
        	if (getNumber() <= MessageHelper.getMaxUnits(getSystemType()))
        	{
        		this.condition = (UnitControlEnum) MessageHelper.valueToEnum(UnitControlEnum.metaInfo, data[0]);
        		this.value = 0;
        	}
        	else
        	{
        		this.condition = (data[0] > 0) ? UnitControlEnum.ON : UnitControlEnum.OFF;
        		this.value = data[0];
        	}
            time = MessageHelper.createWord(data[1], data[2]);
        }

        public int getUnit()
        {
            return getNumber();
        }

        public int getRemainingTime()
        {
            return time;
        }

        public UnitControlEnum getCondition()
        {
            return condition;
        }
        
        public boolean isUnit() throws CommunicationException {
        	return getNumber() <= MessageHelper.getMaxUnits(getSystemType());
        }
        
        public int getValue()
        {
        	return value;
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("CONDITION: " + getCondition() + "\n");
            strbuf.append("TIME REMAINGING: " + getRemainingTime() + "\n");
            strbuf.append("VALUE: " + getValue() + "\n");
            return strbuf.toString();
        }
    }

    public UnitStatusReport() {
    }

    public UnitStatusReport(int firstUnit, int lastUnit) {
    	super(firstUnit, lastUnit);
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
    	if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
    		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 5, ObjectTypeEnum.UNIT);
    	} else {
    		super.initialize(model, protocolType, 0x18, 3);
    	}
	}

	protected Info createInfo(int number, short[] data) {
        return new UnitStatusInfo(number);
    }
}
