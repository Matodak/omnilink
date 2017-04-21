package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.HoldModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class ThermostatHoldModeCommand extends CommandMessage {
	private int tempZone;
	private HoldModeEnum holdMode;
	
    public ThermostatHoldModeCommand(HoldModeEnum holdMode) {
        this(MessageConstants.DEFAULT_TEMP_ZONE, holdMode);
    }

    public ThermostatHoldModeCommand(int tempZone, HoldModeEnum holdMode) {
    	this.tempZone = tempZone;
    	this.holdMode = holdMode;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        // Validate arguments
        MessageHelper.validateValue(tempZone, 0, 16);
        MessageHelper.validateEnum(holdMode);

        super.initialize(model, protocolType, 70, holdMode.getValue(), tempZone);
	}
}
