package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class ThermostatTypeEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static ThermostatTypeEnum NOT_USED = new ThermostatTypeEnum("Not Used", 0);
    public final static ThermostatTypeEnum AUTO_HEAT_COOL = new ThermostatTypeEnum("Auto Heat/Cool", 1);
    public final static ThermostatTypeEnum HEAT_COOL = new ThermostatTypeEnum("Heat/Cool", 2);
    public final static ThermostatTypeEnum HEAT_ONLY = new ThermostatTypeEnum("Heat Only", 3);
    public final static ThermostatTypeEnum COOL_ONLY = new ThermostatTypeEnum("Cool Only", 4);
    public final static ThermostatTypeEnum SETPOINT_ONLY = new ThermostatTypeEnum("Setpoint Only", 5);

    public ThermostatTypeEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
