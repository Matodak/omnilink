package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class ThermostatMaxTemperatureCommand extends CommandMessage {
	private int tempZone;
	private double highSetPoint;
	
    public ThermostatMaxTemperatureCommand(double highSetPoint) {
        this(MessageConstants.DEFAULT_TEMP_ZONE, highSetPoint);
    }

    public ThermostatMaxTemperatureCommand(int tempZone, double highSetPoint)  {
    	this.tempZone = tempZone;
    	this.highSetPoint = highSetPoint;
    }
    
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        short highTempPoint = MessageHelper.tempToOmni(highSetPoint);

        MessageHelper.validateValue(tempZone, 0, 16);
        MessageHelper.validateValue(highTempPoint, 44, 180);

        super.initialize(model, protocolType, 67, highTempPoint, tempZone);
	}
}
