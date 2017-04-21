package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class NameTypeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static NameTypeEnum ZONE = new NameTypeEnum("Zone", 1);
    public final static NameTypeEnum UNIT = new NameTypeEnum("Unit", 2);
    public final static NameTypeEnum BUTTON = new NameTypeEnum("Button", 3);
    public final static NameTypeEnum CODE = new NameTypeEnum("Code", 4);
    public final static NameTypeEnum AREA = new NameTypeEnum("Area", 5);
    public final static NameTypeEnum THERMOSTAT = new NameTypeEnum("Thermostat", 6);
    public final static NameTypeEnum MESSAGE = new NameTypeEnum("Message", 7);
    public final static NameTypeEnum USER_SETTING = new NameTypeEnum("User Setting", 8);
    public final static NameTypeEnum READER = new NameTypeEnum("Reader", 9);

    public NameTypeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
