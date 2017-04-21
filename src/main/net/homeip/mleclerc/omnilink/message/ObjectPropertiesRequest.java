package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.Filter1Enum;
import net.homeip.mleclerc.omnilink.enumeration.Filter2Enum;
import net.homeip.mleclerc.omnilink.enumeration.Filter3Enum;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class ObjectPropertiesRequest extends RequestMessage {
	private ObjectTypeEnum objectType;
	private int indexNo;
	private int relativeDirection;
	private Filter1Enum filter1;
	private Filter2Enum[] filter2;
	private Filter3Enum filter3;
	
	public ObjectPropertiesRequest(ObjectTypeEnum objectType, int indexNo) {
		this(objectType, indexNo, 0, Filter1Enum.NAMED_OR_UNNAMED, new Filter2Enum[] { Filter2Enum.NONE }, Filter3Enum.ANY_LOAD);
	}
	
	public ObjectPropertiesRequest(ObjectTypeEnum objectType, int indexNo, int relativeDirection) {
		this(objectType, indexNo, relativeDirection, Filter1Enum.NAMED_OR_UNNAMED, new Filter2Enum[] { Filter2Enum.NONE }, Filter3Enum.ANY_LOAD);
	}

	public ObjectPropertiesRequest(ObjectTypeEnum objectType, int indexNo, int relativeDirection, Filter1Enum filter1, Filter2Enum filter2, Filter3Enum filter3) {
		this(objectType, indexNo, 0, filter1, new Filter2Enum[] { filter2 }, filter3);
	}
	
	public ObjectPropertiesRequest(ObjectTypeEnum objectType, int indexNo, int relativeDirection, Filter1Enum filter1, Filter2Enum[] filter2, Filter3Enum filter3) {
		this.objectType = objectType;
		this.indexNo = indexNo;
		this.relativeDirection = relativeDirection;
		this.filter1 = filter1;
		this.filter2 = filter2;
		this.filter3 = filter3;
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		MessageHelper.validateEnum(objectType);
		MessageHelper.validateValue(indexNo, 0, 255);
		MessageHelper.validateValue(relativeDirection, -1, 1);
		MessageHelper.validateEnum(filter1);
		for(Filter2Enum filter : filter2) {
			MessageHelper.validateEnum(filter);			
		}
		MessageHelper.validateEnum(filter3);
		
		short[] data = new short[7];
		data[0] = (short) objectType.getValue();
		data[1] = MessageHelper.highByte(indexNo);
		data[2] = MessageHelper.lowByte(indexNo);
		data[3] = MessageHelper.relativeDirectionToByte(relativeDirection);
		data[4] = (short) filter1.getValue();
		data[5] = 0;
		for (int i = 0; i < filter2.length; i++) {
			data[5] += (short) filter2[i].getValue();
		}
		data[6] = (short) filter3.getValue();
		
		super.initialize(model, protocolType, 0x20, 0x08, data);
	}

	protected ReplyMessage createReplyMessage() throws CommunicationException {
		return new ObjectPropertiesReport();
	}
}
