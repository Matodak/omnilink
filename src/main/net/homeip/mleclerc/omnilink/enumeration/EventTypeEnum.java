package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class EventTypeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static EventTypeEnum ZONE_BYPASSED = new EventTypeEnum("Zone P2 bypassed with code P1", 4);
    public final static EventTypeEnum ZONE_RESTORED = new EventTypeEnum("Zone P2 restored with code P1", 5);
    public final static EventTypeEnum ALL_ZONES_RESTORED = new EventTypeEnum("All area P2 zones restored with code P1", 6);
    public final static EventTypeEnum DISARMED = new EventTypeEnum("Area P2 armed in Disarm mode with code P1", 48);
    public final static EventTypeEnum DAY = new EventTypeEnum("Area P2 armed in Day mode with code P1", 49);
    public final static EventTypeEnum NIGHT = new EventTypeEnum("Area P2 armed in Night mode with code P1", 50);
    public final static EventTypeEnum AWAY = new EventTypeEnum("Area P2 armed in Away mode with code P1", 51);
    public final static EventTypeEnum VACATION = new EventTypeEnum("Area P2 armed in Vacation mode with code P1", 52);
    public final static EventTypeEnum DAY_INSTANT = new EventTypeEnum("Area P2 armed in Day Instant mode with code P1", 53);
    public final static EventTypeEnum NIGHT_DELAYED = new EventTypeEnum("Area P2 armed in Night Delayed mode with code P1", 54);
    public final static EventTypeEnum ZONE_TRIPPED = new EventTypeEnum("Zone P2 tripped", 128);
    public final static EventTypeEnum ZONE_TROUBLE = new EventTypeEnum("Zone P2 trouble", 129);
    public final static EventTypeEnum REMOTE_PHONE_ACCESS = new EventTypeEnum("Remote phone access with code P1", 130);
    public final static EventTypeEnum REMOTE_PHONE_LOCKOUT = new EventTypeEnum("Remote phone lockout", 131);
    public final static EventTypeEnum ZONE_AUTO_BYPASSED = new EventTypeEnum("Zone P2 auto bypassed", 132);
    public final static EventTypeEnum ZONE_TROUBLE_CLEARED = new EventTypeEnum("Zone P2 trouble cleared", 133);
    public final static EventTypeEnum PC_ACCESS = new EventTypeEnum("PC access with code P1", 134);
    public final static EventTypeEnum ALARM_ACTIVATED = new EventTypeEnum("Alarm P1 activated in area P2", 135);
    public final static EventTypeEnum ALARM_RESET = new EventTypeEnum("Alarm P1 reset in area P2", 136);
    public final static EventTypeEnum SYSTEM_RESET = new EventTypeEnum("System reset", 137);
    public final static EventTypeEnum MESSAGE_LOGGED = new EventTypeEnum("Message P2 logged", 138);
    public final static EventTypeEnum ZONE_SHUTDOWN = new EventTypeEnum("Zone P2 shutdown", 139);
    public final static EventTypeEnum ACCESS_GRANTED = new EventTypeEnum("Access granted to user P1 at reader P2", 140);
    public final static EventTypeEnum ACCESS_DENIED = new EventTypeEnum("Access denied to user P1 at reader P2", 141);

    public EventTypeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
