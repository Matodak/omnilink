package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.UnitControlEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class UnitCommand extends CommandMessage {
	private int unitNumber;
	private UnitControlEnum control;
	private int delayInMins;
	
	public UnitCommand(int unitNumber, UnitControlEnum control) {
		this(unitNumber, control, 0);		
	}
	
    public UnitCommand(int unitNumber, UnitControlEnum control, int delayInMins) {
    	this.unitNumber = unitNumber;
    	this.control = control;
    	this.delayInMins = delayInMins;
    }
 
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        MessageHelper.validateValue(unitNumber, 1, 255);
        MessageHelper.validateEnum(control);

        if (control.equals(UnitControlEnum.ON) || control.equals(UnitControlEnum.OFF))
		{
            // Unit ON or OFF
            int p1 = (delayInMins > 0) ? delayInMins + 100 : 0;
            super.initialize(model, protocolType, control.getValue(), p1, unitNumber);
        }
        else if (control.getValue() >= UnitControlEnum.DIM_1.getValue() && control.getValue() <= UnitControlEnum.DIM_9.getValue())
        {
            // Unit Dim 1-9
            int p1 = (delayInMins > 0) ? delayInMins + 100 : 0;
            int s = (control.getValue() - UnitControlEnum.DIM_1.getValue() + 1);
            super.initialize(model, protocolType, 16 + s, p1, unitNumber);
        }
        else if (control.getValue() >= UnitControlEnum.BRIGHT_1.getValue() && control.getValue() <= UnitControlEnum.BRIGHT_9.getValue())
        {
            // Unit Bright 1-9
            int p1 = (delayInMins > 0) ? delayInMins + 100 : 0;
            int s = (control.getValue() - UnitControlEnum.BRIGHT_1.getValue() + 1);
            super.initialize(model, protocolType, 32 + s, p1, unitNumber);
        }
        else if (control.getValue() >= UnitControlEnum.LEVEL_0.getValue() && control.getValue() <= UnitControlEnum.LEVEL_100.getValue())
        {
            // Unit Level 0-100
            int p1 = control.getValue() - UnitControlEnum.LEVEL_0.getValue();
            super.initialize(model, protocolType, 9, p1, unitNumber);
        }
        else
            throw new CommunicationException("Invalid unit command");
    }
}
