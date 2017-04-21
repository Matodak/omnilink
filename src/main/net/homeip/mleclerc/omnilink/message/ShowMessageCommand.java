package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class ShowMessageCommand extends CommandMessage {
	private int messageNo;
	
    public ShowMessageCommand(int messageNo) {
    	this.messageNo = messageNo;
    }
    
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        MessageHelper.validateValue(messageNo, 1, 255);

		super.initialize(model, protocolType, 80, 0, messageNo);
    }
}
