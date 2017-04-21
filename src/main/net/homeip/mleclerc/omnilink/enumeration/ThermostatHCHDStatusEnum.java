package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class ThermostatHCHDStatusEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static ThermostatHCHDStatusEnum NONE = new ThermostatHCHDStatusEnum("None", 0);
    public final static ThermostatHCHDStatusEnum HEATING = new ThermostatHCHDStatusEnum("Heating", 1<<0);
    public final static ThermostatHCHDStatusEnum COOLING = new ThermostatHCHDStatusEnum("Cooling", 1<<1);
    public final static ThermostatHCHDStatusEnum HUMIDIFYING = new ThermostatHCHDStatusEnum("Humidifying", 1<<2);
    public final static ThermostatHCHDStatusEnum DEHUMIDIFYING = new ThermostatHCHDStatusEnum("Dehumidifying", 1<<3);

    public ThermostatHCHDStatusEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}