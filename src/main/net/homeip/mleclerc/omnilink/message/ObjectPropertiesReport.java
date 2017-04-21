package net.homeip.mleclerc.omnilink.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.EndOfDataException;
import net.homeip.mleclerc.omnilink.enumeration.AlarmTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ArmingStatusEnum;
import net.homeip.mleclerc.omnilink.enumeration.Enum;
import net.homeip.mleclerc.omnilink.enumeration.FanModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.HoldStatusEnum;
import net.homeip.mleclerc.omnilink.enumeration.LatchedAlarmStatusEnum;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SecurityModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SensorTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ThermostatHCHDStatusEnum;
import net.homeip.mleclerc.omnilink.enumeration.ThermostatTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.UnitControlEnum;
import net.homeip.mleclerc.omnilink.enumeration.UnitTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.UserSettingTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ZoneConditionEnum;
import net.homeip.mleclerc.omnilink.enumeration.ZoneOptionEnum;
import net.homeip.mleclerc.omnilink.enumeration.ZoneTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ObjectPropertiesReport extends ExpectedReplyMessage {
	public class ObjectProperties implements Serializable {
		private ObjectTypeEnum objectType;
		private int objectNo;
    	protected String name;

		protected ObjectProperties(ObjectTypeEnum objectType, int objectNo) {
			this.objectType = objectType;
			this.objectNo = objectNo;
		}

        public void dataChanged(short[] data) throws CommunicationException {
        	name = MessageHelper.getText(3, data);
        }

		public ObjectTypeEnum getObjectType() {
			return objectType;
		}
		
		public int getObjectNo() {
			return objectNo;
		}
		
        public String getName() {
        	return name;
        }
        
		public String toString() {
			StringBuffer strbuf = new StringBuffer();
            strbuf.append("OBJECT NO: " + getObjectNo() + "\n");
            strbuf.append("OBJECT TYPE: " + getObjectType() + "\n");
            strbuf.append("NAME: " + getName() + "\n");
            return strbuf.toString();
		}
	}
	
    public class ZoneProperties extends ObjectProperties {
        private ZoneConditionEnum condition;
        private LatchedAlarmStatusEnum latchedAlarmStatus;
        private ArmingStatusEnum armingStatus;
        private int loopReading;
        private ZoneTypeEnum type;
        private int area;
        private Collection<ZoneOptionEnum> options;

        public ZoneProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
        
        public void dataChanged(short[] data) throws CommunicationException {
            short status = data[3];
            condition = (ZoneConditionEnum) MessageHelper.valueToEnum(ZoneConditionEnum.metaInfo, MessageHelper.getBits(status, 0, 2));
            latchedAlarmStatus = (LatchedAlarmStatusEnum) MessageHelper.valueToEnum(LatchedAlarmStatusEnum.metaInfo, MessageHelper.getBits(status, 2, 2));
            armingStatus = (ArmingStatusEnum) MessageHelper.valueToEnum(ArmingStatusEnum.metaInfo, MessageHelper.getBits(status, 4, 2));
			loopReading = data[4];
			int typeVal = data[5];
			type = (ZoneTypeEnum) MessageHelper.valueToEnum(ZoneTypeEnum.metaInfo, typeVal);
			area = data[6];
			int optionsVal = data[7];
			options = new ArrayList<ZoneOptionEnum>();
			if (MessageHelper.isBitOn(optionsVal, 0)) {
				options.add(ZoneOptionEnum.CROSS_ZONING);
			}
			if (MessageHelper.isBitOn(optionsVal, 1)) {
				options.add(ZoneOptionEnum.SWINGER_SHUTDOWN);
			}
			if (MessageHelper.isBitOn(optionsVal, 2)) {
				options.add(ZoneOptionEnum.DIAL_OUT_DELAY);
			}
			name = MessageHelper.getText(8, data);
        }

        public ZoneConditionEnum getCondition() {
            return condition;
        }

        public LatchedAlarmStatusEnum getLatchedAlarmStatus() {
            return latchedAlarmStatus;
        }

        public ArmingStatusEnum getArmingStatus() {
            return armingStatus;
        }

        public int getAnalogLoopReading() {
            return loopReading;
        }

        public ZoneTypeEnum getType() {
        	return type;
        }
        
        public int getArea() {
        	return area;
        }
        
        public Collection<ZoneOptionEnum> getOptions() {
        	return options;
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("CONDITION: " + getCondition() + "\n");
            strbuf.append("LATCHED ALARM STATUS: " + getLatchedAlarmStatus() + "\n");
            strbuf.append("ARMING STATUS: " + getArmingStatus() + "\n");
            strbuf.append("LOOPBACK READING: " + getAnalogLoopReading() + "\n");
            strbuf.append("ZONE TYPE: " + getType() + "\n");
            strbuf.append("AREA: " + getArea() + "\n");
            strbuf.append("OPTIONS: " + MessageHelper.getEnumItemsString(getOptions()) + "\n");
            return strbuf.toString();
        }
    }

    public class UnitProperties extends ObjectProperties {
    	private UnitControlEnum condition;
    	private int time;
    	private UnitTypeEnum type;
    	
        public UnitProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
        
        public UnitControlEnum getCondition() {
            return condition;
        }
        
        public int getRemainingTime() {
        	return time;
        }
        
        public UnitTypeEnum getType() {
        	return type;
        }
        
        public void dataChanged(short[] data) throws CommunicationException {
        	short stateVal = data[3];
        	condition = (UnitControlEnum) MessageHelper.valueToEnum(UnitControlEnum.metaInfo, stateVal);
        	time = MessageHelper.createWord(data[4], data[5]);
        	int typeVal = data[6];
        	type = (UnitTypeEnum) MessageHelper.valueToEnum(UnitTypeEnum.metaInfo, typeVal);
        	name = MessageHelper.getText(7, data);
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("CONDITION: " + getCondition() + "\n");
            strbuf.append("TIME REMAINING: " + getRemainingTime() + "\n");
            strbuf.append("UNIT TYPE: " + getType() + "\n");
            return strbuf.toString();
        }
    }
    
    public class ButtonProperties extends ObjectProperties {
        public ButtonProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
    }

    public class CodeProperties extends ObjectProperties {
        public CodeProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
    }

    public class AreaProperties extends ObjectProperties {
    	private SecurityModeEnum areaMode;
    	private Collection<AlarmTypeEnum> areaAlarms = new ArrayList<AlarmTypeEnum>();
        private int entryTimer;
        private int exitTimer;
        private boolean enabled;
        private int exitDelay;
        private int entryDelay;

        public AreaProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
        
		public void dataChanged(short[] data) throws CommunicationException {
        	int areaModeVal = data[3];
        	areaMode = (SecurityModeEnum) SecurityModeEnum.metaInfo.getByValue(areaModeVal);
        	int areaAlarmsVal = data[4];
        	for (Enum enumMember : AlarmTypeEnum.metaInfo.members()) {
        		if (MessageHelper.isBitOn(areaAlarmsVal, enumMember.getValue() - 1)) {
        			areaAlarms.add((AlarmTypeEnum) enumMember);
        		}
        	}
            entryTimer = data[5];
            exitTimer = data[6];
            enabled = (data[7] == 1);
            exitDelay = data[8];
            entryDelay = data[9];
			name = MessageHelper.getText(10, data);
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

        public int getEntryDelay() {
            return entryDelay;
        }

        public int getExitDelay() {
            return exitDelay;
        }

        public boolean isEnabled() {
        	return enabled;
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("AREA MODE: " + getAreaMode() + "\n");
            strbuf.append("AREA ALARMS: " + MessageHelper.getEnumItemsString(getAreaAlarms()) + "\n");
            strbuf.append("ENTRY TIMER: " + getEntryTimer() + "\n");
            strbuf.append("EXIT TIMER: " + getExitTimer() + "\n");
            strbuf.append("EXIT DELAY: " + getExitDelay() + "\n");
            strbuf.append("ENTRY DELAY: " + getEntryDelay() + "\n");
            strbuf.append("ENABLED: " + isEnabled() + "\n");
            return strbuf.toString();
        }
    }
    
    public class ThermostatProperties extends ObjectProperties {
        private boolean commFailure;
        private boolean freezeAlarm;
        private double currentTemp;
        private double lowSetPoint;
        private double highSetPoint;
        private SystemModeEnum systemMode;
        private FanModeEnum fanMode;
        private HoldStatusEnum holdStatus;
        private ThermostatTypeEnum type;
        private double humidity;
        private double humiditySetPoint;
        private double dehumiditySetPoint;
        private double outdoorTemp;
        private ThermostatHCHDStatusEnum hchdStatus;

        public ThermostatProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
        
        public void dataChanged(short[] data) throws CommunicationException {
            short status = data[3];
            commFailure = MessageHelper.isBitOn(status, 0);
            freezeAlarm = MessageHelper.isBitOn(status, 1);
            currentTemp = MessageHelper.omniToCelcius(data[4]);
            lowSetPoint = MessageHelper.omniToCelcius(data[5]);
            highSetPoint = MessageHelper.omniToCelcius(data[6]);
            systemMode = (SystemModeEnum) MessageHelper.valueToEnum(SystemModeEnum.metaInfo, data[7]);
            fanMode = (FanModeEnum) MessageHelper.valueToEnum(FanModeEnum.metaInfo, data[8]);
            int holdStatusVal = (data[9] <= 2) ? data[9] : 1;
            holdStatus = (HoldStatusEnum) MessageHelper.valueToEnum(HoldStatusEnum.metaInfo, holdStatusVal);
            type = (ThermostatTypeEnum) MessageHelper.valueToEnum(ThermostatTypeEnum.metaInfo, data[10]);
			name = MessageHelper.getText(11, data);
			humidity = MessageHelper.omniToHumidity(data[24]);
			humiditySetPoint = MessageHelper.omniToHumidity(data[25]);
			dehumiditySetPoint = MessageHelper.omniToHumidity(data[26]);
			outdoorTemp = MessageHelper.omniToCelcius(data[27]);
			hchdStatus = (ThermostatHCHDStatusEnum) MessageHelper.valueToEnum(ThermostatHCHDStatusEnum.metaInfo, data[28]);
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

        public HoldStatusEnum getHoldStatus()
        {
            return holdStatus;
        }

        public ThermostatTypeEnum getType()
        {
        	return type;
        }
        
        public double getHumidity() {
        	return humidity;
        }

        public double getHumiditySetPoint() {
        	return humiditySetPoint;
        }

        public double getDehumiditySetPoint() {
        	return dehumiditySetPoint;
        }

        public double getOutdoorTemperature() {
        	return outdoorTemp;
        }
        
        public ThermostatHCHDStatusEnum getHCHDStatus() {
        	return hchdStatus;
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
            strbuf.append("TYPE: " + getType() + "\n");
            strbuf.append("HUMIDITY: " + getHumidity() + "\n");
            strbuf.append("HUMIDITY SETPOINT: " + getHumiditySetPoint() + "\n");
            strbuf.append("DEHUMIDITY SETPOINT: " + getDehumiditySetPoint() + "\n");
            strbuf.append("OUTDOOR TEMPERATURE: " + getOutdoorTemperature() + "\n");
            strbuf.append("HCHD STATUS: " + getHCHDStatus() + "\n");
            return strbuf.toString();
        }
    }

    public class MessageProperties extends ObjectProperties {
        public MessageProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
    }

    public class AuxiliarySensorProperties extends ObjectProperties {
    	private int outputStatus;
        private double currentTemp;
        private double lowSetPoint;
        private double highSetPoint;
        private SensorTypeEnum type;

        public AuxiliarySensorProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
        
        public void dataChanged(short[] data) throws CommunicationException {
        	outputStatus = data[3];
            currentTemp = MessageHelper.omniToCelcius(data[4]);
            lowSetPoint = MessageHelper.omniToCelcius(data[5]);
            highSetPoint = MessageHelper.omniToCelcius(data[6]);
            type = (SensorTypeEnum) MessageHelper.valueToEnum(SensorTypeEnum.metaInfo, data[7]);
			name = MessageHelper.getText(8, data);
        }

        public int getOutputStatus()
        {
            return outputStatus;
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

        public SensorTypeEnum getType()
        {
        	return type;
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("OUTPUT STATUS: " + getOutputStatus() + "\n");
            strbuf.append("LOW SETPOINT: " + getLowSetPoint() + "\n");
            strbuf.append("HIGH SETPOINT: " + getHighSetPoint() + "\n");
            strbuf.append("TEMPERATURE: " + getCurrentTemperature() + "\n");
            strbuf.append("TYPE: " + getType() + "\n");
            return strbuf.toString();
        }
    }

    public class AudioZoneProperties extends ObjectProperties {
    	private boolean on;
        private int source;
        private int volume;
        private boolean mute;

        public AudioZoneProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
        
        public void dataChanged(short[] data) throws CommunicationException {
        	on = MessageHelper.isBitOn(data[3], 0);
        	source = data[4];
        	volume = data[5];
        	mute = MessageHelper.isBitOn(data[6], 0);
			name = MessageHelper.getText(7, data);
        }

        public boolean isOn()
        {
            return on;
        }

        public int getSource()
        {
            return source;
        }

        public int getVolume()
        {
            return volume;
        }

        public boolean isMute()
        {
            return mute;
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("ON: " + isOn() + "\n");
            strbuf.append("SOURCE: " + getSource() + "\n");
            strbuf.append("VOLUME: " + getVolume() + "\n");
            strbuf.append("MUTE: " + isMute() + "\n");
            return strbuf.toString();
        }
    }

    public class UserSettingProperties extends ObjectProperties {
    	private UserSettingTypeEnum type;
        private int value;
        
        public UserSettingProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
        
        public void dataChanged(short[] data) throws CommunicationException {
        	type = (UserSettingTypeEnum) MessageHelper.valueToEnum(UserSettingTypeEnum.metaInfo, data[3]);
        	value = MessageHelper.createWord(data[4], data[5]);
			name = MessageHelper.getText(6, data);
        }

        public UserSettingTypeEnum getType()
        {
            return type;
        }

        public int getValue()
        {
            return value;
        }
        
        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("TYPE: " + getType() + "\n");
            strbuf.append("VALUE: " + getValue() + "\n");
            return strbuf.toString();
        }
    }

    public class AccessControlReaderProperties extends ObjectProperties {
    	private boolean locked;
        private int unlockTimer;
        private boolean accessGranted;
        private int lastUserToAccessReader;
        
        public AccessControlReaderProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
        
        public void dataChanged(short[] data) throws CommunicationException {
        	locked = (data[3] == 1);
        	unlockTimer = MessageHelper.createWord(data[4], data[5]);
        	accessGranted = (data[6] == 0);
        	lastUserToAccessReader = data[7];
			name = MessageHelper.getText(8, data);
        }

        public boolean isLocked()
        {
            return locked;
        }

        public int getUnlockTimer()
        {
            return unlockTimer;
        }

        public boolean isAccessGranted()
        {
            return accessGranted;
        }

        public int getLastUserToAccessReader()
        {
            return lastUserToAccessReader;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("LOCKED: " + isLocked() + "\n");
            strbuf.append("UNLOCK TIMER: " + getUnlockTimer() + "\n");
            strbuf.append("ACCESS GRANTED: " + isAccessGranted() + "\n");
            strbuf.append("LAST USER TO ACCESS READER: " + getLastUserToAccessReader() + "\n");
            return strbuf.toString();
        }
    }

    public class AudioSourceProperties extends ObjectProperties {
        public AudioSourceProperties(ObjectTypeEnum objectType, int objectNo) {
        	super(objectType, objectNo);
        }
    }

    private ObjectProperties objectProperties;
    
    protected ObjectPropertiesReport() {
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x21, 0); // Variable length reply
	}

	protected void checkMessageType(int messageType) throws CommunicationException {
		if (messageType == END_OF_DATA) {
			throw new EndOfDataException(); 
		} else {
			super.checkMessageType(messageType);
		}
	}

	protected void checkMessageLength(int messageLength) throws CommunicationException {
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		int objectTypeVal = data[0];
		ObjectTypeEnum objectType = (ObjectTypeEnum) MessageHelper.valueToEnum(ObjectTypeEnum.metaInfo, objectTypeVal);
		int objectNo = MessageHelper.createWord(data[1], data[2]);
		if (objectType.equals(ObjectTypeEnum.ZONE)) {
			ZoneProperties zoneProperties = new ZoneProperties(objectType, objectNo);
			zoneProperties.dataChanged(data);
			objectProperties = zoneProperties;
		} else if (objectType.equals(ObjectTypeEnum.UNIT)) {
			UnitProperties unitProperties = new UnitProperties(objectType, objectNo);
			unitProperties.dataChanged(data);
			objectProperties = unitProperties;
		} else if (objectType.equals(ObjectTypeEnum.BUTTON)) {
			ButtonProperties buttonProperties = new ButtonProperties(objectType, objectNo);
			buttonProperties.dataChanged(data);
			objectProperties = buttonProperties;
		} else if (objectType.equals(ObjectTypeEnum.CODE)) {
			CodeProperties codeProperties = new CodeProperties(objectType, objectNo);
			codeProperties.dataChanged(data);
			objectProperties = codeProperties;
		} else if (objectType.equals(ObjectTypeEnum.AREA)) {
			AreaProperties areaProperties = new AreaProperties(objectType, objectNo);
			areaProperties.dataChanged(data);
			objectProperties = areaProperties;
		} else if (objectType.equals(ObjectTypeEnum.THERMOSTAT)) {
			ThermostatProperties thermostatProperties = new ThermostatProperties(objectType, objectNo);
			thermostatProperties.dataChanged(data);
			objectProperties = thermostatProperties;
		} else if (objectType.equals(ObjectTypeEnum.MESSAGE)) {
			MessageProperties messageProperties = new MessageProperties(objectType, objectNo);
			messageProperties.dataChanged(data);
			objectProperties = messageProperties;
		} else if (objectType.equals(ObjectTypeEnum.AUXILIARY_SENSOR)) {
			AuxiliarySensorProperties auxiliarySensorProperties = new AuxiliarySensorProperties(objectType, objectNo);
			auxiliarySensorProperties.dataChanged(data);
			objectProperties = auxiliarySensorProperties;
		} else if (objectType.equals(ObjectTypeEnum.AUDIO_SOURCE)) {
			AudioSourceProperties audioSourceProperties = new AudioSourceProperties(objectType, objectNo);
			audioSourceProperties.dataChanged(data);
			objectProperties = audioSourceProperties;
		} else if (objectType.equals(ObjectTypeEnum.AUDIO_ZONE)) {
			AudioZoneProperties audioZoneProperties = new AudioZoneProperties(objectType, objectNo);
			audioZoneProperties.dataChanged(data);
			objectProperties = audioZoneProperties;
		} else if (objectType.equals(ObjectTypeEnum.USER_SETTING)) {
			UserSettingProperties userSettingProperties = new UserSettingProperties(objectType, objectNo);
			userSettingProperties.dataChanged(data);
			objectProperties = userSettingProperties;
		} else if (objectType.equals(ObjectTypeEnum.ACCESS_CONTROL_READER)) {
			AccessControlReaderProperties accessControlReaderProperties = new AccessControlReaderProperties(objectType, objectNo);
			accessControlReaderProperties.dataChanged(data);
			objectProperties = accessControlReaderProperties;
		} else {
			throw new CommunicationException("Unsupported object type: " + objectType);
		}
	}
	
	public ObjectProperties getObjectProperties() {
		return objectProperties;
	}
	
	public String toString() {
		return getObjectProperties().toString();
	}
}
