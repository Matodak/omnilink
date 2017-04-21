package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class LatchedAlarmStatusEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static LatchedAlarmStatusEnum SECURE = new LatchedAlarmStatusEnum("Secure", 0);
    public final static LatchedAlarmStatusEnum TRIPPED = new LatchedAlarmStatusEnum("Tripped", 1);
    public final static LatchedAlarmStatusEnum RESET = new LatchedAlarmStatusEnum("Reset but previously tripped", 2);

    public LatchedAlarmStatusEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
