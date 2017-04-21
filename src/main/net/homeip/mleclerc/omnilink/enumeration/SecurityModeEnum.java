package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class SecurityModeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static SecurityModeEnum OFF = new SecurityModeEnum("Off", 0);
    public final static SecurityModeEnum DISARM = new SecurityModeEnum("Disarm", 0);
    public final static SecurityModeEnum DAY = new SecurityModeEnum("Day", 1);
    public final static SecurityModeEnum NIGHT = new SecurityModeEnum("Night", 2);
    public final static SecurityModeEnum AWAY = new SecurityModeEnum("Away", 3);
    public final static SecurityModeEnum VACATION = new SecurityModeEnum("Vacation", 4);
    public final static SecurityModeEnum DAY_INSTANT = new SecurityModeEnum("Day Instant", 5);
    public final static SecurityModeEnum NIGHT_DELAYED = new SecurityModeEnum("Night Delayed", 6);
    public final static SecurityModeEnum ARMING_DAY = new SecurityModeEnum("Arming Day", 9);
    public final static SecurityModeEnum ARMING_NIGHT = new SecurityModeEnum("Arming Night", 10);
    public final static SecurityModeEnum ARMING_AWAY = new SecurityModeEnum("Arming Away", 11);
    public final static SecurityModeEnum ARMING_VACATION = new SecurityModeEnum("Arming Vacation", 12);
    public final static SecurityModeEnum ARMING_DAY_INSTANT = new SecurityModeEnum("Arming Day Instant", 13);
    public final static SecurityModeEnum ARMING_NIGHT_DELAYED = new SecurityModeEnum("Arming Night Delayed", 14);

    public SecurityModeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
