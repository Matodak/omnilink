package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class ReadEventRecordRequest extends RequestMessage {
	private int eventNo;
	private int relativeDirection;

	public ReadEventRecordRequest(int eventNo) {
		this(eventNo, 0);
	}

	public ReadEventRecordRequest(int eventNo, int relativeDirection) {
		this.eventNo = eventNo;
		this.relativeDirection = relativeDirection;
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		MessageHelper.validateValue(eventNo, 0, 65535);
		MessageHelper.validateValue(relativeDirection, -1, 1);
		
		short[] data = new short[3];
		data[0] = MessageHelper.highByte(eventNo);
		data[1] = MessageHelper.lowByte(eventNo);
		data[2] = MessageHelper.relativeDirectionToByte(relativeDirection);
		super.initialize(model, protocolType, 0x24, 0x04, data);
	}

	protected ReplyMessage createReplyMessage() throws CommunicationException {
		return new ReadEventRecordReport();
	}
}
