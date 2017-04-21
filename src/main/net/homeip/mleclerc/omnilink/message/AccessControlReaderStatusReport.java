package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class AccessControlReaderStatusReport extends MultipleInfoReplyMessage {
    public class AccessControlReaderStatusInfo extends Info {
        private boolean accessGranted;
        private int lastUserToAccessReader;

        AccessControlReaderStatusInfo(int number) {
            super(number);
        }

        public void dataChanged(short[] data) throws CommunicationException {
        	accessGranted = (data[0] == 0);
        	lastUserToAccessReader = data[1];
        }

        public boolean isAccessGranted() {
            return accessGranted;
        }

        public int getLastUserToAccessReader() {
            return lastUserToAccessReader;
        }
        
        public String toString() {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("ACCESS GRANTED: " + isAccessGranted() + "\n");
            strbuf.append("LAST USER TO ACCESS READER: " + getLastUserToAccessReader() + "\n");
            return strbuf.toString();
        }
    }

    public AccessControlReaderStatusReport() {
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
   		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 4, ObjectTypeEnum.ACCESS_CONTROL_READER);
	}
	
	protected Info createInfo(int number, short[] data) {
        return new AccessControlReaderStatusInfo(number);
    }
}
