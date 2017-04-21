package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class AudioZoneStatusReport extends MultipleInfoReplyMessage
{
    public class AudioZoneStatusInfo extends Info
    {
    	private boolean on;
        private int source;
        private int volume;
        private boolean mute;

        public AudioZoneStatusInfo(int number) {
            super(number);
        }

        public void dataChanged(short[] data) throws CommunicationException {
        	on = MessageHelper.isBitOn(data[0], 0);
        	source = data[1];
        	volume = data[2];
        	mute = MessageHelper.isBitOn(data[3], 0);
        }

        public boolean isOn() {
            return on;
        }

        public int getSource() {
            return source;
        }

        public int getVolume() {
            return volume;
        }

        public boolean isMute() {
            return mute;
        }
        
        public String toString() {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("ON: " + isOn() + "\n");
            strbuf.append("SOURCE: " + getSource() + "\n");
            strbuf.append("VOLUME: " + getVolume() + "\n");
            strbuf.append("MUTE: " + isMute() + "\n");
            return strbuf.toString();
        }
    }

    public AudioZoneStatusReport() {
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
   		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 6, ObjectTypeEnum.AUDIO_ZONE);
	}

	protected Info createInfo(int number, short[] data) {
        return new AudioZoneStatusInfo(number);
    }
}
