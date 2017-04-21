package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;

@SuppressWarnings("serial")
public class LogMessageCommand extends CommandMessage {
	private int messageNo;
	
    public LogMessageCommand(int messageNo) {
    	this.messageNo = messageNo;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		super.initialize(model, protocolType, 81, 0, messageNo);
	}
}
