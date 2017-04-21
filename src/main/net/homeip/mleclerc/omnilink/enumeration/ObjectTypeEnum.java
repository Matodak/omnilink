package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class ObjectTypeEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static ObjectTypeEnum ZONE = new ObjectTypeEnum("Zone", 1);
    public final static ObjectTypeEnum UNIT = new ObjectTypeEnum("Unit", 2);
    public final static ObjectTypeEnum BUTTON = new ObjectTypeEnum("Button", 3);
    public final static ObjectTypeEnum CODE = new ObjectTypeEnum("Code", 4);
    public final static ObjectTypeEnum AREA = new ObjectTypeEnum("Area", 5);
    public final static ObjectTypeEnum THERMOSTAT = new ObjectTypeEnum("Thermostat", 6);
    public final static ObjectTypeEnum MESSAGE = new ObjectTypeEnum("Message", 7);
    public final static ObjectTypeEnum AUXILIARY_SENSOR = new ObjectTypeEnum("Auxiliary Sensor", 8);
    public final static ObjectTypeEnum AUDIO_SOURCE = new ObjectTypeEnum("Audio Source", 9);
    public final static ObjectTypeEnum AUDIO_ZONE = new ObjectTypeEnum("Audio Zone", 10);
    public final static ObjectTypeEnum EXPANSION_ENCLOSURE = new ObjectTypeEnum("Expansion Enclosure", 11);
    public final static ObjectTypeEnum CONSOLE = new ObjectTypeEnum("Console", 12);
    public final static ObjectTypeEnum USER_SETTING = new ObjectTypeEnum("User Setting", 13);
    public final static ObjectTypeEnum ACCESS_CONTROL_READER = new ObjectTypeEnum("Access Control Reader", 14);
    public final static ObjectTypeEnum ACCESS_CONTROL_LOCK = new ObjectTypeEnum("Access Control Lock", 15);

    public ObjectTypeEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
