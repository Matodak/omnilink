package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class MacroButtonCommand extends CommandMessage {
	private int buttonNumber;
	
    public MacroButtonCommand(int buttonNumber) {
    	this.buttonNumber = buttonNumber;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {		
        MessageHelper.validateValue(buttonNumber, 1, 255);

        super.initialize(model, protocolType, 7, 0, buttonNumber);
	}
}
