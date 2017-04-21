package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class ThermostatSystemModeCommand extends CommandMessage {
	private int tempZone;
	private SystemModeEnum systemMode;
	
    public ThermostatSystemModeCommand(SystemModeEnum systemMode) {
        this(MessageConstants.DEFAULT_TEMP_ZONE, systemMode);
    }

    public ThermostatSystemModeCommand(int tempZone, SystemModeEnum systemMode) {
    	this.tempZone = tempZone;
    	this.systemMode = systemMode;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		MessageHelper.validateValue(tempZone, 0, 16);
	    MessageHelper.validateEnum(systemMode);
	
	    super.initialize(model, protocolType, 68, systemMode.getValue(), tempZone);
	}
}
