package net.homeip.mleclerc.omnilink.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.AlarmTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.Enum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SecurityModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage.Info;

@SuppressWarnings("serial")
public class SystemStatusReport extends ExpectedReplyMessage
{
    public static class ExpansionEnclosureInfo extends Info
    {
        private boolean acPowerOff;
        private boolean batteryLow;
        private boolean commFailure;
        private int batteryReading;

        public ExpansionEnclosureInfo(int number) {
        	super(number);
        }

		protected void dataChanged(short[] data) throws CommunicationException {
			short enclosureStatus = data[0];
			short batteryReading = data[1];
            acPowerOff = MessageHelper.isBitOn(enclosureStatus, 0);
            batteryLow = MessageHelper.isBitOn(enclosureStatus, 1);
            commFailure = MessageHelper.isBitOn(enclosureStatus, 7);
            this.batteryReading = batteryReading;
        }

        public boolean isACPowerOff()
        {
            return acPowerOff;
        }

        public boolean isBatteryLow()
        {
            return batteryLow;
        }

        public boolean isCommunicationFailure()
        {
            return commFailure;
        }
        
        public int getBatteryReading()
        {
        	return batteryReading;
        }

        public String toString() {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append("EXPANSION ENCLOSURE " + getNumber() + ":\n");
            strbuf.append("   AC POWER OFF: " + isACPowerOff() + "\n");
            strbuf.append("   BATTERY LOW: " + isBatteryLow() + "\n");
            strbuf.append("   COMM FAILURE: " + isCommunicationFailure() + "\n");
            strbuf.append("   BATTERY READING: " + getBatteryReading() + "\n");
            return strbuf.toString();
        }
    }
    
    public class AlarmStatusInfo implements Serializable {
    	private List<AlarmTypeEnum> alarms = new ArrayList<AlarmTypeEnum>();

		protected void addAlarm(short alarmStatus) {
        	for (Enum enumMember : AlarmTypeEnum.metaInfo.members()) {
        		if (MessageHelper.isBitOn(alarmStatus, enumMember.getValue() - 1)) {
        			alarms.add((AlarmTypeEnum) enumMember);
        		}
        	}
        }

        public List<AlarmTypeEnum> getAlarms() {
            return alarms;
        }

        public String toString() {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(MessageHelper.getEnumItemsString(alarms));
            return strbuf.toString();
        }
    }

    private Date date;
    private Date sunrise;
    private Date sunset;
    private int batteryReading;
    private boolean dstOn;
    private Map<Integer, SecurityModeEnum> securityModeMap = new TreeMap<Integer, SecurityModeEnum>();
    private Map<Integer, ExpansionEnclosureInfo> expansionEnclosureMap = new TreeMap<Integer, ExpansionEnclosureInfo>();
    private Map<Integer, AlarmStatusInfo> alarmStatusMap = new TreeMap<Integer, AlarmStatusInfo>();
    private int alarmCount;

    protected SystemStatusReport() {
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
			super.initialize(model, protocolType, 0x19, 0); // Variable message length
		} else {
	        // Set the expected message length based on the model type passed in
	        int expectedMessageLength = MessageHelper.getExpectedSystemStatusReportLength(model, protocolType);
			super.initialize(model, protocolType, 0x14, expectedMessageLength);						
		}
	}

	protected void checkMessageLength(int messageLength) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(ProtocolTypeEnum.HAI_OMNI_LINK_II)) {
			this.alarmCount = (messageLength - 15) / 2;
		} else {
			super.checkMessageLength(messageLength);
		}
	}

	protected void dataChanged(short[] data) throws CommunicationException
    {
        // Make sure the date/time have been set on the system
        if (data[0] > 0)
        {
            // Extract the date
            short year = data[1];
            short month = data[2];
            short day = data[3];
            short dayOfWeek = data[4];
            short hour = data[5];
            short minute = data[6];
            short second = data[7];
            short dst = data[8];
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_WEEK, MessageHelper.getCalendarDayOfWeek(dayOfWeek));
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.YEAR, 2000 + year);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, second);
            cal.set(Calendar.MILLISECOND, 0);
            // The following does not seem to be required
            //cal.set(Calendar.DST_OFFSET, (dst != 0) ? 1 : 0);
            date = cal.getTime();

            // DST indicator
            dstOn = (dst != 0);

            // Extract the sunrise
            short sunriseHour = data[9];
            short sunriseMin = data[10];
            cal.set(Calendar.HOUR_OF_DAY, sunriseHour);
            cal.set(Calendar.MINUTE, sunriseMin);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            sunrise = cal.getTime();

            // Extract the sunset
            short sunsetHour = data[11];
            short sunsetMin = data[12];
            cal.set(Calendar.HOUR_OF_DAY, sunsetHour);
            cal.set(Calendar.MINUTE, sunsetMin);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            sunset = cal.getTime();
        }

        // Extract the battery reading
        batteryReading = data[13];

        if (ProtocolTypeHelper.isOmniLinkII(getProtocolType())) {
	        // Extract alarm status for all reported alarms
	        for (int i = 0; i < alarmCount; i++) {
	            int areaNo = data[14 + (i * 2)];
	            short alarmStatus = data[15 + (i * 2)];
	            AlarmStatusInfo alarmStatusObj = alarmStatusMap.get(new Integer(areaNo));
	            if (alarmStatusObj == null) {
	            	alarmStatusObj = new AlarmStatusInfo();
	            }
	            alarmStatusObj.addAlarm(alarmStatus);
	            alarmStatusMap.put(new Integer(areaNo), alarmStatusObj);
	        }
        } else {
            // Determine the number of areas
            int areaCount = MessageHelper.getAreaCount(getSystemType());

	        // Get security mode for all the available areas
	        for (int i = 0; i < areaCount; i++)
	        {
	            int securityModeVal = data[14 + i];
	            SecurityModeEnum securityMode = (SecurityModeEnum) MessageHelper.valueToEnum(SecurityModeEnum.metaInfo, securityModeVal);
	            securityModeMap.put(new Integer(i + 1), securityMode);
	        }
	
	        // Determine the number of enclosures
	        int enclosureCount = MessageHelper.getExpansionEnclosureCount(getSystemType());
	
	        // Extract enclosure status for all the available areas
	        for (int i = 0; i < enclosureCount; i++)
	        {
	        	int enclosureNo = new Integer(i + 1);
	            short enclosureStatus = data[22 + (i * 2)];
	            short enclosreBatteryReading = data[23 + (i * 2)];
	            short[] newData = { enclosureStatus, enclosreBatteryReading};           
	            ExpansionEnclosureInfo enclosureStatusObj = new ExpansionEnclosureInfo(enclosureNo);
	            enclosureStatusObj.dataChanged(newData);
	            expansionEnclosureMap.put(enclosureNo, enclosureStatusObj);
	        }
        }
    }

    public Date getDate()
    {
        return date;
    }

    public Date getSunrise()
    {
        return sunrise;
    }

    public Date getSunset()
    {
        return sunset;
    }

    public SecurityModeEnum getSecurityMode(int areaNo)
    {
        return (SecurityModeEnum) securityModeMap.get(new Integer(areaNo));
    }

    public ExpansionEnclosureInfo getExpansionEnclosure(int areaNo)
    {
        return (ExpansionEnclosureInfo) expansionEnclosureMap.get(new Integer(areaNo));
    }

    public AlarmStatusInfo getAlarmStatus(int areaNo)
    {
        return (AlarmStatusInfo) alarmStatusMap.get(new Integer(areaNo));
    }

    public boolean isDSTOn()
    {
        return dstOn;
    }

    public int getBatteryReading()
    {
        return batteryReading;
    }

    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();

        strbuf.append("DATE: " + getDate() + "\n");
        strbuf.append("DST ON: " + dstOn + "\n");
        strbuf.append("SUNRISE: " + getSunrise() + "\n");
        strbuf.append("SUNSET: " + getSunset() + "\n");
        strbuf.append("BATTERY READING: " + getBatteryReading() + "\n");

        if (ProtocolTypeHelper.isOmniLinkII(getProtocolType())) {
	        for (Iterator iter = alarmStatusMap.keySet().iterator(); iter.hasNext(); ) {
	            Integer areaNo = (Integer) iter.next();
	            AlarmStatusInfo info = (AlarmStatusInfo) alarmStatusMap.get(areaNo);
	            strbuf.append("ALARM - AREA " + areaNo + ": ");
	            strbuf.append(info);
	            strbuf.append("\n");
	        }        	
        } else {
	        for (Iterator iter = securityModeMap.keySet().iterator(); iter.hasNext(); )
	        {
	            Integer areaNo = (Integer) iter.next();
	            SecurityModeEnum securityMode = (SecurityModeEnum) securityModeMap.get(areaNo);
	            strbuf.append("SECURITY MODE - AREA " + areaNo + ": " + securityMode + "\n");
	        }
	
	        for (Iterator iter = expansionEnclosureMap.keySet().iterator(); iter.hasNext(); )
	        {
	            Integer enclosureNo = (Integer) iter.next();
	            ExpansionEnclosureInfo info = (ExpansionEnclosureInfo) expansionEnclosureMap.get(enclosureNo);
	            strbuf.append(info);
	        }
        }

        return strbuf.toString();
    }
}
