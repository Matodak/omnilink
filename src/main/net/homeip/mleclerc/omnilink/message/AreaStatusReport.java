package net.homeip.mleclerc.omnilink.message;

import java.util.ArrayList;
import java.util.Collection;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.AlarmTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.Enum;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SecurityModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class AreaStatusReport extends MultipleInfoReplyMessage {
    public class AreaStatusInfo extends Info {
    	private SecurityModeEnum areaMode;
    	private Collection<AlarmTypeEnum> areaAlarms = new ArrayList<AlarmTypeEnum>();
        private int entryTimer;
        private int exitTimer;

        public AreaStatusInfo(int number) {
            super(number);
        }

        public void dataChanged(short[] data) throws CommunicationException {
        	int areaModeVal = data[0];
        	areaMode = (SecurityModeEnum) SecurityModeEnum.metaInfo.getByValue(areaModeVal);
        	int areaAlarmsVal = data[1];
        	for (Enum enumMember : AlarmTypeEnum.metaInfo.members()) {
        		if (MessageHelper.isBitOn(areaAlarmsVal, enumMember.getValue() - 1)) {
        			areaAlarms.add((AlarmTypeEnum) enumMember);
        		}
        	}
            entryTimer = data[2];
            exitTimer = data[3];
        }

        public int getArea() {
            return getNumber();
        }

        public SecurityModeEnum getAreaMode() {
        	return areaMode;
        }
        
        public Collection<AlarmTypeEnum> getAreaAlarms() {
        	return areaAlarms;
        }
        
        public int getEntryTimer() {
            return entryTimer;
        }

        public int getExitTimer() {
            return exitTimer;
        }
        
        public String toString() {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("AREA MODE: " + getAreaMode() + "\n");
            strbuf.append("AREA ALARMS: " + MessageHelper.getEnumItemsString(getAreaAlarms()) + "\n");
            strbuf.append("ENTRY TIMER: " + getEntryTimer() + "\n");
            strbuf.append("EXIT TIMER: " + getExitTimer() + "\n");
            return strbuf.toString();
        }
    }

    public AreaStatusReport() {
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
   		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 6, ObjectTypeEnum.AREA);
	}

	protected Info createInfo(int number, short[] data) {
        return new AreaStatusInfo(number);
    }
}
