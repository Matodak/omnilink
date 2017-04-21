package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.FanModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class ThermostatFanModeCommand extends CommandMessage {
	private int tempZone;
	private FanModeEnum fanMode;
	
    public ThermostatFanModeCommand(FanModeEnum fanMode) {
        this(MessageConstants.DEFAULT_TEMP_ZONE, fanMode);
    }

    public ThermostatFanModeCommand(int tempZone, FanModeEnum fanMode) {
    	this.tempZone = tempZone;
    	this.fanMode = fanMode;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        MessageHelper.validateValue(tempZone, 0, 16);
        MessageHelper.validateEnum(fanMode);

        super.initialize(model, protocolType, 69, fanMode.getValue(), tempZone);
	}
}
