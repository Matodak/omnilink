package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class ExtendedObjectStatusRequest extends RequestMessage {
	private ObjectTypeEnum objectType;
	private int startingObject;
	private int endingObject;

	public ExtendedObjectStatusRequest(ObjectTypeEnum objectType, int objectNo) {
		this(objectType, objectNo, objectNo);
	}

	public ExtendedObjectStatusRequest(ObjectTypeEnum objectType, int startingObject, int endingObject) {
		this.objectType = objectType;
		this.startingObject = startingObject;
		this.endingObject = endingObject;
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		MessageHelper.validateEnum(objectType);
		MessageHelper.validateRange(startingObject, endingObject);
		
		short[] data = new short[5];
		data[0] = (short) objectType.getValue();
		data[1] = MessageHelper.highByte(startingObject);
		data[2] = MessageHelper.lowByte(startingObject);
		data[3] = MessageHelper.highByte(endingObject);
		data[4] = MessageHelper.lowByte(endingObject);
		
		super.initialize(model, protocolType, 0x3A, 0x06, data);
	}

	protected ReplyMessage createReplyMessage() throws CommunicationException {
		return new ExtendedObjectStatusReport(objectType);
	}
}
