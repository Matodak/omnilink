package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class PhoneCommand extends CommandMessage {
	private int phoneNumber;
	private int messageNumber;

	public PhoneCommand(int phoneNumber, int messageNumber) {
		this.phoneNumber = phoneNumber;
		this.messageNumber = messageNumber;
    }
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        MessageHelper.validateValue(phoneNumber, 1, 8);
        MessageHelper.validateValue(messageNumber, 1, 255);
        
        super.initialize(model, protocolType, 84, phoneNumber, messageNumber);
    }
}