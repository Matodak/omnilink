package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class SensorTypeEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

	public final static SensorTypeEnum PROGRAMMABLE_ENERGY_SAVER_MODULE = new SensorTypeEnum("Programmable Energy Saver Module", 80);
	public final static SensorTypeEnum OUTDOOR_TEMPERATURE = new SensorTypeEnum("Outdoor Temperature", 81);
	public final static SensorTypeEnum TEMPERATURE = new SensorTypeEnum("Temperature", 82);
	public final static SensorTypeEnum TEMPERATURE_ALARM = new SensorTypeEnum("Temperature Alarm", 83);
	public final static SensorTypeEnum HUMIDITY = new SensorTypeEnum("Humidity", 84);
	public final static SensorTypeEnum EXTENDED_RANGE_OUTDOOR_TEMPERATURE = new SensorTypeEnum("Extended Range Outdoor Temperature", 85);
	public final static SensorTypeEnum EXTENDED_RANGE_TEMPERATURE = new SensorTypeEnum("Extended Range Temperature", 86);
	public final static SensorTypeEnum EXTENDED_RANGE_TEMPERATURE_ALARM = new SensorTypeEnum("Extended Range Temperature Alarm", 87);

    public SensorTypeEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
