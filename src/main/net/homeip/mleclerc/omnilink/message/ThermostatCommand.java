package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.enumeration.FanModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.HoldModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemModeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CompositeCommandMessage;

@SuppressWarnings("serial")
public class ThermostatCommand extends CompositeCommandMessage {
    public ThermostatCommand(double lowSetPoint, double highSetPoint, SystemModeEnum systemMode, FanModeEnum fanMode, HoldModeEnum holdMode) {
        this(MessageConstants.DEFAULT_TEMP_ZONE, lowSetPoint, highSetPoint, systemMode, fanMode, holdMode);
    }

    public ThermostatCommand(int tempZone, double lowSetPoint, double highSetPoint, SystemModeEnum systemMode, FanModeEnum fanMode, HoldModeEnum holdMode) {
        addCommand(new ThermostatMinTemperatureCommand(tempZone, lowSetPoint));
        addCommand(new ThermostatMaxTemperatureCommand(tempZone, highSetPoint));
        addCommand(new ThermostatSystemModeCommand(tempZone, systemMode));
        addCommand(new ThermostatFanModeCommand(tempZone, fanMode));
        addCommand(new ThermostatHoldModeCommand(tempZone, holdMode));
    }
}
