package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.BasicUnitControlEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class AllUnitsCommand extends CommandMessage {
	private int area;
	private BasicUnitControlEnum control;
	
    public AllUnitsCommand(BasicUnitControlEnum control) {
    	this(MessageConstants.DEFAULT_AREA, control);
    }
    
    public AllUnitsCommand(int area, BasicUnitControlEnum control) {
    	this.area = area;
    	this.control = control;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        MessageHelper.validateValue(area, 1, 16);
        MessageHelper.validateEnum(control);
        
        super.initialize(model, protocolType, control.getValue() + 2, 0, area);
	}
}
