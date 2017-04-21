package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class BaseZoneCommand extends CommandMessage {
	private int commandNo;
	private int code;
    private int zone;

    protected BaseZoneCommand(int commandNo, int zone, int code) {
    	this.commandNo = commandNo;
    	this.zone = zone;
    	this.code = code;
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
        // Validate parameters
        MessageHelper.validateValue(zone, 1, 255);
        MessageHelper.validateValue(code, 1, 255);
        int maxZones = MessageHelper.getMaxZones(model);
        if (zone > maxZones)
            throw new CommunicationException("Selection zone the number of zones supported by this system: " + maxZones);

    	super.initialize(model, protocolType, commandNo, code, zone);
	}
}
