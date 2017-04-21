package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class AccessControlLockStatusReport extends MultipleInfoReplyMessage {
    public class AccessControlLockStatusInfo extends Info {
        private boolean locked;
        private int unlockTimer;

        AccessControlLockStatusInfo(int number) {
            super(number);
        }

        public void dataChanged(short[] data) throws CommunicationException {
        	locked = (data[0] == 0);
        	unlockTimer = MessageHelper.createWord(data[1], data[2]);
        }

        public boolean isLocked() {
            return locked;
        }

        public int getUnlockTimer() {
            return unlockTimer;
        }
        
        public String toString() {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("LOCKED: " + isLocked() + "\n");
            strbuf.append("UNLOCK TIMER: " + getUnlockTimer() + "\n");
            return strbuf.toString();
        }
    }

    public AccessControlLockStatusReport() {
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
   		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 5, ObjectTypeEnum.ACCESS_CONTROL_LOCK);
	}
	
	protected Info createInfo(int number, short[] data) {
        return new AccessControlLockStatusInfo(number);
    }
}
