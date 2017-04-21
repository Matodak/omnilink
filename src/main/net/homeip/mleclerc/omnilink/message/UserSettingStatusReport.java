package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.UserSettingTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class UserSettingStatusReport extends MultipleInfoReplyMessage {
    public class UserSettingStatusInfo extends Info {
    	private UserSettingTypeEnum type;
        private int value;

        UserSettingStatusInfo(int number) {
            super(number);
        }

        public void dataChanged(short[] data) throws CommunicationException {
        	type = (UserSettingTypeEnum) MessageHelper.valueToEnum(UserSettingTypeEnum.metaInfo, data[0]);
        	value = MessageHelper.createWord(data[1], data[2]);
        }

        public UserSettingTypeEnum getType() {
            return type;
        }

        public int getValue() {
            return value;
        }
        
        public String toString() {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("TYPE: " + getType() + "\n");
            strbuf.append("VALUE: " + getValue() + "\n");
            return strbuf.toString();
        }
    }

    public UserSettingStatusReport() {
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
   		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 5, ObjectTypeEnum.USER_SETTING);
	}

	protected Info createInfo(int number, short[] data) {
        return new UserSettingStatusInfo(number);
    }
}
