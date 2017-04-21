package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ObjectTypeCapacityReport extends ExpectedReplyMessage {
	private ObjectTypeEnum capacityType;
	private int capacity;

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x1F, 0x04);
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		int capacityTypeVal = data[0];
		capacityType = (ObjectTypeEnum) MessageHelper.valueToEnum(ObjectTypeEnum.metaInfo, capacityTypeVal);
		capacity = MessageHelper.createWord(data[1], data[2]);
	}
	
	public ObjectTypeEnum getCapacityType() {
		return capacityType;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public String toString() {
	    StringBuffer strbuf = new StringBuffer();	
	    strbuf.append("CAPACITY TYPE: " + getCapacityType() + "\n");
	    strbuf.append("CAPACITY: " + getCapacity() + "\n");
	    return strbuf.toString();
	}
}
