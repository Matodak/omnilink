package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.AlarmTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SecurityModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemEventTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class SystemEventsReport extends MultipleInfoReplyMessage
{
    public abstract class SystemEventInfo extends Info
    {
        private SystemEventTypeEnum type;

        public SystemEventInfo(int number, SystemEventTypeEnum type)
        {
            super(number);

            this.type = type;
        }

        protected abstract void dataChanged(int eventData);

        protected void dataChanged(short[] data) throws CommunicationException
        {
            int event = MessageHelper.createWord(data[0], data[1]);
            dataChanged(event);
        }

        public int getEvent()
        {
            return getNumber();
        }

        public SystemEventTypeEnum getType()
        {
            return type;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("TYPE: " + type + "\n");
            return strbuf.toString();
        }
    }

    public class DefaultSystemEventInfo extends SystemEventInfo
    {
        public DefaultSystemEventInfo(int eventNumber, SystemEventTypeEnum type)
        {
            super(eventNumber, type);
        }

        protected void dataChanged(int eventData)
        {
            // Nothing to do
        }
    }

    public class UserMacroEventInfo extends SystemEventInfo
    {
        private int button;

        public UserMacroEventInfo(int eventNumber)
        {
            super(eventNumber, SystemEventTypeEnum.USER_MACRO_BUTTON);
        }

        protected void dataChanged(int eventData)
        {
            button = MessageHelper.getBits(eventData, 0, 8);
        }

        public int getButton()
        {
            return button;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("BUTTON: " + button + "\n");
            return strbuf.toString();
        }
    }

    public class AlarmActivationEventInfo extends SystemEventInfo
    {
        private AlarmTypeEnum alarmType;
        private int area;

        public AlarmActivationEventInfo(int eventNumber)
        {
            super(eventNumber, SystemEventTypeEnum.ALARM_ACTIVATION);
        }

        protected void dataChanged(int eventData)
        {
            int alarmTypeVal = MessageHelper.getBits(eventData, 4, 4);
            alarmType = (AlarmTypeEnum) AlarmTypeEnum.metaInfo.getByValue(alarmTypeVal);
            area = MessageHelper.getBits(eventData, 0, 4);
        }

        public AlarmTypeEnum getAlarmType()
        {
            return alarmType;
        }

        public int getArea()
        {
            return area;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("ALARM TYPE: " + alarmType + "\n");
            strbuf.append("AREA: " + area + "\n");
            return strbuf.toString();
        }
    }

    public class AllOnOffEventInfo extends SystemEventInfo
    {
        private boolean on;
        private int area;

        public AllOnOffEventInfo(int eventNumber)
        {
            super(eventNumber, SystemEventTypeEnum.ALL_ON_OFF);
        }

        protected void dataChanged(int eventData)
        {
            on = MessageHelper.isBitOn(eventData, 4);
            area = MessageHelper.getBits(eventData, 0, 4);
        }

        public boolean isOn()
        {
            return on;
        }

        public int getArea()
        {
            return area;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("IS ON: " + on + "\n");
            strbuf.append("AREA: " + area + "\n");
            return strbuf.toString();
        }
    }

    public class ZoneStateChangeEventInfo extends SystemEventInfo
    {
        private boolean on;
        private int zone;

        public ZoneStateChangeEventInfo(int eventNumber)
        {
            super(eventNumber, SystemEventTypeEnum.ZONE_STATE_CHANGE);
        }

        protected void dataChanged(int eventData)
        {
            on = MessageHelper.isBitOn(eventData, 9);
            zone = MessageHelper.getBits(eventData, 0, 9);
        }

        public boolean isOn()
        {
            return on;
        }

        public int getZone()
        {
            return zone;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("IS ON: " + on + "\n");
            strbuf.append("ZONE: " + zone + "\n");
            return strbuf.toString();
        }
    }

    public class UnitStateChangeEventInfo extends SystemEventInfo
    {
        private boolean on;
        private int unit;

        public UnitStateChangeEventInfo(int eventNumber)
        {
            super(eventNumber, SystemEventTypeEnum.UNIT_STATE_CHANGE);
        }

        protected void dataChanged(int eventData)
        {
            on = MessageHelper.isBitOn(eventData, 9);
            unit = MessageHelper.getBits(eventData, 0, 9);
        }

        public boolean isOn()
        {
            return on;
        }

        public int getUnit()
        {
            return unit;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("IS ON: " + on + "\n");
            strbuf.append("UNIT: " + unit + "\n");
            return strbuf.toString();
        }
    }

    public class X10CodeReceivedEventInfo extends SystemEventInfo
    {
        private boolean on;
        private boolean allUnits;
        private int unit;
        private char houseCode;

        public X10CodeReceivedEventInfo(int eventNumber)
        {
            super(eventNumber, SystemEventTypeEnum.X10_CODE_RECEIVED);
        }

        protected void dataChanged(int eventData)
        {
            on = MessageHelper.isBitOn(eventData, 9);
            allUnits = MessageHelper.isBitOn(eventData, 8);
            int houseCodeVal = MessageHelper.getBits(eventData, 4, 4);
            houseCode = MessageHelper.toHouseCode(houseCodeVal);
            unit = MessageHelper.getBits(eventData, 0, 4) + 1; // Unit numbers start at 0
        }

        public boolean isOn()
        {
            return on;
        }

        public boolean isAllUnits()
        {
            return allUnits;
        }

        public boolean isOneUnit()
        {
            return !allUnits;
        }

        public int getUnit()
        {
            return unit;
        }

        public char getHouseCode()
        {
            return houseCode;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("IS ON: " + on + "\n");
            strbuf.append("ALL UNIT: " + allUnits + "\n");
            strbuf.append("UNIT: " + unit + "\n");
            strbuf.append("HOUSE CODE: " + houseCode + "\n");
            return strbuf.toString();
        }
    }

    public class SecurityArmingEventInfo extends SystemEventInfo
    {
        private boolean exitDelay;
        private int area;
        private int code;
        private SecurityModeEnum securityMode;

        public SecurityArmingEventInfo(int eventNumber)
        {
            super(eventNumber, SystemEventTypeEnum.SECURITY_ARMING);
        }

        protected void dataChanged(int eventData)
        {
            exitDelay = MessageHelper.isBitOn(eventData, 15);
            int securityModeVal = MessageHelper.getBits(eventData, 12, 3);
            securityMode = (SecurityModeEnum) SecurityModeEnum.metaInfo.getByValue(securityModeVal);
            area = MessageHelper.getBits(eventData, 8, 4);
            code = MessageHelper.getBits(eventData, 0, 8);
        }

        public boolean isExitDelay()
        {
            return exitDelay;
        }

        public SecurityModeEnum getSecurityMode()
        {
            return securityMode;
        }

        public int getArea()
        {
            return area;
        }

        public int getCode()
        {
            return code;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("EXIT DELAY: " + exitDelay + "\n");
            strbuf.append("SECURITY MODE: " + securityMode + "\n");
            strbuf.append("AREA: " + area + "\n");
            strbuf.append("CODE: " + code + "\n");
            return strbuf.toString();
        }
    }

    public class ProLinkMessageInfo extends SystemEventInfo
    {
        private int messageNumber;

        public ProLinkMessageInfo(int eventNumber)
        {
            super(eventNumber, SystemEventTypeEnum.PRO_LINK_MESSAGE);
        }

        protected void dataChanged(int eventData)
        {
            messageNumber = MessageHelper.getBits(eventData, 0, 7);
        }

        public int getMessageNumber()
        {
            return messageNumber;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("MESSAGE NUMBER: " + messageNumber + "\n");
            return strbuf.toString();
        }
    }

    class SystemEventInfoFactory
    {
        public SystemEventInfo createSystemEventInfo(int eventNumber, int eventData) throws CommunicationException
        {
            SystemEventInfo ret = null;

            // Get bits from 12-15
            int bits_12_15 = MessageHelper.getBits(eventData, 12, 4);
            switch(bits_12_15)
            {
                case 0x0:
                {
                    // Get bits 10 & 11
                    int bits_10_11 = MessageHelper.getBits(eventData, 10, 2);
                    switch(bits_10_11)
                    {
                        case 0x0:
                        {
                            // Get bits 8 & 9
                            int bits_8_9 = MessageHelper.getBits(eventData, 8, 2);
                            switch(bits_8_9)
                            {
                                case 0x0:
                                {
                                    // User Macro button
                                    ret = new UserMacroEventInfo(eventNumber);
                                    break;
                                }
                                case 0x1:
                                {
                                    // Pro-Link message
                                    ret = new ProLinkMessageInfo(eventNumber);
                                    break;
                                }
                                case 0x2:
                                {
                                    // Alarm activation
                                    ret = new AlarmActivationEventInfo(eventNumber);
                                    break;
                                }
                                case 0x3:
                                {
                                    // Get bits 5-7
                                    int bits_5_7 = MessageHelper.getBits(eventData, 5, 3);
                                    switch(bits_5_7)
                                    {
                                        case 0x7:
                                        {
                                            // All On/Off
                                            ret = new AllOnOffEventInfo(eventNumber);
                                            break;
                                        }
                                        default:
                                        {
                                            int bits_4_7 = MessageHelper.getBits(eventData, 4, 4);
                                            switch(bits_4_7)
                                            {
                                                case 0x0:
                                                {
                                                    int bits_1_4 = MessageHelper.getBits(eventData, 0, 4);
                                                    switch(bits_1_4)
                                                    {
                                                        case 0x0:
                                                        {
                                                            // Phone line dead
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.PHONE_LINE_DEAD);
                                                            break;
                                                        }
                                                        case 0x1:
                                                        {
                                                            // Phone line ring
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.PHONE_LINE_RING);
                                                            break;
                                                        }
                                                        case 0x2:
                                                        {
                                                            // Phone line off hook
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.PHONE_LINE_OFF_HOOK);
                                                            break;
                                                        }
                                                        case 0x3:
                                                        {
                                                            // Phone line on hook
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.PHONE_LINE_ON_HOOK);
                                                            break;
                                                        }
                                                        case 0x4:
                                                        {
                                                            // AC Power Off
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.AC_POWER_OFF);
                                                            break;
                                                        }
                                                        case 0x5:
                                                        {
                                                            // AC Power restored
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.AC_POWER_RESTORED);
                                                            break;
                                                        }
                                                        case 0x6:
                                                        {
                                                            // Battery low
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.BATTERY_LOW);
                                                            break;
                                                        }
                                                        case 0x7:
                                                        {
                                                            // Battery Ok
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.BATTERY_OK);
                                                            break;
                                                        }
                                                        case 0x8:
                                                        {
                                                            // DCM trouble
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.DCM_TROUBLE);
                                                            break;
                                                        }
                                                        case 0x9:
                                                        {
                                                            // DCM ok
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.DCM_OK);
                                                            break;
                                                        }
                                                        case 0xA:
                                                        {
                                                            // Energy cost Low
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.ENERGY_COST_LOW);
                                                            break;
                                                        }
                                                        case 0xB:
                                                        {
                                                            // Energy cost Mid
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.ENERGY_COST_MID);
                                                            break;
                                                        }
                                                        case 0xC:
                                                        {
                                                            // Energy cost high
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.ENERGY_COST_HIGH);
                                                            break;
                                                        }
                                                        case 0xD:
                                                        {
                                                            // Energy cost Crtitical
                                                            ret = new DefaultSystemEventInfo(eventNumber, SystemEventTypeEnum.ENERGY_COST_CRITICAL);
                                                            break;
                                                        }
                                                        default:
                                                            throw new CommunicationException("Invalid bit sequence: " + eventData);
                                                    }
                                                    break;
                                                }
                                                default:
                                                    throw new CommunicationException("Invalid bit sequence: " + eventData);
                                            }
                                            break;
                                        }
                                    }
                                    break;
                                }
                                default:
                                    throw new CommunicationException("Invalid bit sequence: " + eventData);
                            }
                            break;
                        }
                        case 0x1:
                        {
                            // Zone state change
                            ret = new ZoneStateChangeEventInfo(eventNumber);
                            break;
                        }
                        case 0x2:
                        {
                            // Unit state change
                            ret = new UnitStateChangeEventInfo(eventNumber);
                            break;
                        }
                        case 0x3:
                        {
                            // X10 Code received
                            ret = new X10CodeReceivedEventInfo(eventNumber);
                            break;
                        }
                        default:
                             throw new CommunicationException("Invalid bit sequence: " + eventData);
                    }
                    break;
                }
                default:
                {
                    // Security arming
                    ret = new SecurityArmingEventInfo(eventNumber);
                    break;
                }
            }

            return ret;
        }
    }

    public static int MESSAGE_TYPE = 0x37;
    
    public SystemEventsReport() {
    	super(1, 1);
    }
    
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
			super.initialize(model, protocolType, MESSAGE_TYPE, 2);
		} else {
	        // We don't know in advance how many system events we'll receive
	    	super.initialize(model, protocolType, 0x23, 2);
		}
	}

    public Info createInfo(int eventNumber, short[] data) throws CommunicationException
    {
        // The type of info object depends on the data received in the reply
        int eventData = MessageHelper.createWord(data[0], data[1]);
        SystemEventInfoFactory factory = new SystemEventInfoFactory();
        return factory.createSystemEventInfo(eventNumber, eventData);
    }

    protected void checkMessageLength(int messageLength) throws CommunicationException
    {
        // Since we don't know the message length in advance
        // we cannot check against the expected message length
        if (messageLength < 0)
            throw new CommunicationException("Invalid message length in reply");

        int systemEventCount = ( messageLength - 1 ) / 2;
        if (systemEventCount < 0 || (messageLength - 1) % 2 != 0)
            throw new CommunicationException("Invalid number of system events in reply");
    }
}
