package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class BasicUnitControlEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static BasicUnitControlEnum OFF = new BasicUnitControlEnum("Off", 0);
    public final static BasicUnitControlEnum ON = new BasicUnitControlEnum("On", 1);

    public BasicUnitControlEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
