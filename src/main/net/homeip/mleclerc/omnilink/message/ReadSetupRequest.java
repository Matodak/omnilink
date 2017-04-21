package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class ReadSetupRequest extends RequestMessage {
	private int startIndex;
	private int byteCount;

	public ReadSetupRequest(int startIndex, int byteCount) {
		this.startIndex = startIndex;
		this.byteCount = byteCount;
	}

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		MessageHelper.validateValue(startIndex, 0, 65535);
		MessageHelper.validateValue(byteCount, 0, 200);

		short[] data = new short[3];
		data[0] = MessageHelper.highByte(startIndex);
		data[1] = MessageHelper.lowByte(startIndex);
		data[2] = (short) byteCount;
		super.initialize(model, protocolType, 0x05, 0x04, data);
	}

	protected ReplyMessage createReplyMessage() throws CommunicationException {
		return new ReadSetupReport(startIndex, byteCount);
	}
}
