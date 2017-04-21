package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class ThermostatMinTemperatureCommand extends CommandMessage {
	private int tempZone;
	private double lowSetPoint;
	
	public ThermostatMinTemperatureCommand(double lowSetPoint) {
        this(MessageConstants.DEFAULT_TEMP_ZONE, lowSetPoint);
    }

    public ThermostatMinTemperatureCommand(int tempZone, double lowSetPoint) {
    	this.tempZone = tempZone;
    	this.lowSetPoint = lowSetPoint;
    }
    
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        short lowTempPoint = MessageHelper.tempToOmni(lowSetPoint);

        MessageHelper.validateValue(tempZone, 0, 16);
        MessageHelper.validateValue(lowTempPoint, 44, 180);

        super.initialize(model, protocolType, 66, lowTempPoint, tempZone);
	}
}
