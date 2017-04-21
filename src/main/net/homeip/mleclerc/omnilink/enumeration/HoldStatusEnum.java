package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class HoldStatusEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static HoldStatusEnum OFF = new HoldStatusEnum("Off", 0);
    public final static HoldStatusEnum HOLD = new HoldStatusEnum("Hold", 1);
    public final static HoldStatusEnum VACATION_HOLD = new HoldStatusEnum("Hold", 2);

    public HoldStatusEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}

