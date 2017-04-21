package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.NameTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.AcknowledgeReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class WriteNameCommand extends RequestMessage {
	private NameTypeEnum nameType;
	private int objectNo;
	private String text;
	
    public WriteNameCommand(NameTypeEnum nameType, int objectNo, String text) {
    	this.nameType = nameType;
    	this.objectNo = objectNo;
    	this.text = text;
    }

    public void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
    	
    	int maxTextLength = MessageHelper.getItemNameMaxLength(nameType);
    	int textLength = Math.min(text.length(), maxTextLength);
    	short[] textData = MessageHelper.textToBytes(text, textLength);
    	
    	short[] data = new short[3 + textData.length];
    	data[0] = (short) nameType.getValue();
    	data[1] = MessageHelper.highByte(objectNo);
    	data[2] = MessageHelper.lowByte(objectNo);
    	System.arraycopy(textData, 0, data, 3, textData.length);
    	
		super.initialize(model, protocolType, 0x0C, textLength + 5, data);
    }

    protected ReplyMessage createReplyMessage() {
        return new AcknowledgeReplyMessage();
    }
}
