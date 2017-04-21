package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class TemperatureFormatEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static TemperatureFormatEnum FARENHEIT = new TemperatureFormatEnum("Farenheit", 1);
    public final static TemperatureFormatEnum CELCIUS = new TemperatureFormatEnum("Celcius", 2);

    public TemperatureFormatEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
