package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ExtendedMessageStatusReport extends MessageStatusReport {
    public ExtendedMessageStatusReport() {
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
    	super.initialize(model, protocolType, ExtendedObjectStatusReport.MESSAGE_TYPE, 3, ObjectTypeEnum.MESSAGE, true);
    }

    protected Info createInfo(int messageNumber, short[] data) {
        return new MessageStatusInfo(messageNumber);
    }
}
