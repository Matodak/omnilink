package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class SystemEventTypeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static SystemEventTypeEnum USER_MACRO_BUTTON = new SystemEventTypeEnum("User Macro Button", 1);
    public final static SystemEventTypeEnum ALARM_ACTIVATION = new SystemEventTypeEnum("Alarm Activation", 2);
    public final static SystemEventTypeEnum ZONE_STATE_CHANGE = new SystemEventTypeEnum("Zone State Change", 3);
    public final static SystemEventTypeEnum UNIT_STATE_CHANGE = new SystemEventTypeEnum("Unit State Change", 4);
    public final static SystemEventTypeEnum X10_CODE_RECEIVED = new SystemEventTypeEnum("X10 Code Received", 5);
    public final static SystemEventTypeEnum SECURITY_ARMING   = new SystemEventTypeEnum("Security Arming", 6);
    public final static SystemEventTypeEnum ALL_ON_OFF        = new SystemEventTypeEnum("All On/Off", 7);
    public final static SystemEventTypeEnum PHONE_LINE_DEAD   = new SystemEventTypeEnum("Phone Line Dead", 8);
    public final static SystemEventTypeEnum PHONE_LINE_RING   = new SystemEventTypeEnum("Phone Line Ring", 9);
    public final static SystemEventTypeEnum PHONE_LINE_OFF_HOOK = new SystemEventTypeEnum("Phone Line Off Hook", 10);
    public final static SystemEventTypeEnum PHONE_LINE_ON_HOOK = new SystemEventTypeEnum("Phone Line On Hook", 11);
    public final static SystemEventTypeEnum AC_POWER_OFF   = new SystemEventTypeEnum("AC Power Off", 12);
    public final static SystemEventTypeEnum AC_POWER_RESTORED = new SystemEventTypeEnum("AC Power Restored", 13);
    public final static SystemEventTypeEnum BATTERY_LOW = new SystemEventTypeEnum("Battery Low", 14);
    public final static SystemEventTypeEnum BATTERY_OK = new SystemEventTypeEnum("Battery OK", 15);
    public final static SystemEventTypeEnum DCM_TROUBLE = new SystemEventTypeEnum("DCM OK", 16);
    public final static SystemEventTypeEnum DCM_OK = new SystemEventTypeEnum("DCM OK", 17);
    public final static SystemEventTypeEnum ENERGY_COST_LOW = new SystemEventTypeEnum("Energy Cost Low", 18);
    public final static SystemEventTypeEnum ENERGY_COST_MID = new SystemEventTypeEnum("Energy Cost Mid", 19);
    public final static SystemEventTypeEnum ENERGY_COST_HIGH = new SystemEventTypeEnum("Energy Cost High", 20);
    public final static SystemEventTypeEnum ENERGY_COST_CRITICAL = new SystemEventTypeEnum("Energy Cost Critical", 21);
    public final static SystemEventTypeEnum PRO_LINK_MESSAGE = new SystemEventTypeEnum("Pro-Link Message", 22);

    public SystemEventTypeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
