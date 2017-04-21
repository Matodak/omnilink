package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class ZoneConditionEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static ZoneConditionEnum SECURE = new ZoneConditionEnum("Secure", 0);
    public final static ZoneConditionEnum NOT_READY = new ZoneConditionEnum("Not Ready", 1);
    public final static ZoneConditionEnum TROUBLE = new ZoneConditionEnum("Trouble", 2);

    public ZoneConditionEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
