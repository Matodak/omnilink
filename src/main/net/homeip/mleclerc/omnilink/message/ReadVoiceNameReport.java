package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.NameTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage.Info;

@SuppressWarnings("serial")
public class ReadVoiceNameReport extends ExpectedReplyMessage {
	
    public static class VoiceNameInfo extends Info
    {
        private NameTypeEnum nameType;
        private int objectNo;
        private int[] phrases;

        public VoiceNameInfo(int number)
        {
            super(number);
        }

        protected void dataChanged(short[] data) throws CommunicationException {
    		nameType = (NameTypeEnum) NameTypeEnum.metaInfo.getByValue( data[0]);
    		objectNo = MessageHelper.createWord(data[1], data[2]);
    		phrases = MessageHelper.getPhrases(3, data);
        }

		public int getNumber() {
			return objectNo;
		}

		public int getObjectNo() {
			return objectNo;
		}

        public NameTypeEnum getType()
        {
            return nameType;
        }

        public int[] getPhrases()
        {
            return phrases;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("TYPE: " + getType() + "\n");
            for (int i = 0; i < phrases.length; i++) {
                strbuf.append("PHRASE " + (i + 1) + ": " + phrases[i] + "\n");
            }
            return strbuf.toString();
        }
    }

	private VoiceNameInfo info;
	
    protected ReadVoiceNameReport() {
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x12, 0);  // Variable reply length
	}

	protected void checkMessageLength(int messageLength) throws CommunicationException {
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		info = new VoiceNameInfo(0);
		info.dataChanged(data);
	}
	
	public VoiceNameInfo getInfo() {
		return info;
	}
	
    public String toString() {
    	return info.toString();
    }
}
