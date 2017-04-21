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
public class WriteVoiceNameCommand extends RequestMessage {
	private NameTypeEnum nameType;
	private int objectNo;
	private int[] phrases;
	
    public WriteVoiceNameCommand(NameTypeEnum nameType, int objectNo, int[] phrases) {
    	this.nameType = nameType;
    	this.objectNo = objectNo;
    	this.phrases = phrases;
    }

    public void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
    	
    	int maxPhraseCount = MessageHelper.getVoiceNameMaxPhraseCount(nameType);
    	int phraseCount = Math.min(phrases.length, maxPhraseCount);
    	short[] phraseData = MessageHelper.toPhraseData(phrases, phraseCount);
    	
    	short[] data = new short[3 + phraseData.length];
    	data[0] = (short) nameType.getValue();
    	data[1] = MessageHelper.highByte(objectNo);
    	data[2] = MessageHelper.lowByte(objectNo);
    	System.arraycopy(phraseData, 0, data, 3, phraseData.length);
    	
		super.initialize(model, protocolType, 0x10, (phraseCount + 1) * 2 + 4, data);
    }

    protected ReplyMessage createReplyMessage() {
        return new AcknowledgeReplyMessage();
    }
}
