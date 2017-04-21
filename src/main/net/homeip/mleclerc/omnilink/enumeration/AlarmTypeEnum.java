package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class AlarmTypeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static AlarmTypeEnum BURGLARY = new AlarmTypeEnum("Burglary", 1);
    public final static AlarmTypeEnum FIRE = new AlarmTypeEnum("Fire", 2);
    public final static AlarmTypeEnum GAS = new AlarmTypeEnum("Gas", 3);
    public final static AlarmTypeEnum AUXILIARY = new AlarmTypeEnum("Auxiliary", 4);
    public final static AlarmTypeEnum FREEZE = new AlarmTypeEnum("Freeze", 5);
    public final static AlarmTypeEnum WATER = new AlarmTypeEnum("Water", 6);
    public final static AlarmTypeEnum DURESS = new AlarmTypeEnum("Duress", 7);
    public final static AlarmTypeEnum TEMPERATURE = new AlarmTypeEnum("Temperature", 8);

    public AlarmTypeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
