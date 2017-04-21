package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class HoldModeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static HoldModeEnum OFF = new HoldModeEnum("Off", 0);
    public final static HoldModeEnum HOLD = new HoldModeEnum("Hold", 255);

    public HoldModeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}

