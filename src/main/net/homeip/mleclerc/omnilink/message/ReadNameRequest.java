package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.NameTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class ReadNameRequest extends RequestMessage {
	private NameTypeEnum nameType;
	private int objectNo;

	public ReadNameRequest(NameTypeEnum nameType, int objectNo) {
		this.nameType = nameType;
		this.objectNo = objectNo;
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		MessageHelper.validateEnum(nameType);
		MessageHelper.validateValue(objectNo, 0, 256);
		
		short[] data = new short[4];
		data[0] = (short) nameType.getValue();
		data[1] = MessageHelper.highByte(objectNo);
		data[2] = MessageHelper.lowByte(objectNo);
		data[3] = 0x01;
		super.initialize(model, protocolType, 0x0D, 0x05, data);
	}

	protected ReplyMessage createReplyMessage() throws CommunicationException {
		return new ReadNameReport();
	}
}
