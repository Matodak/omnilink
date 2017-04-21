package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class SystemModeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static SystemModeEnum OFF = new SystemModeEnum("Off", 0);
    public final static SystemModeEnum HEAT = new SystemModeEnum("Heat", 1);
    public final static SystemModeEnum COOL = new SystemModeEnum("Cool", 2);
    public final static SystemModeEnum AUTO = new SystemModeEnum("Auto", 3);
    public final static SystemModeEnum EMERGENCY_HEAT = new SystemModeEnum("Emergency Heat", 4);

    public SystemModeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
