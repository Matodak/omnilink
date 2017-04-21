package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ExtendedAuxiliaryStatusReport extends AuxiliaryStatusReport
{
    public ExtendedAuxiliaryStatusReport() {
    }
    
	protected void initialize(SystemTypeEnum model,ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
    	super.initialize(model, protocolType, ExtendedObjectStatusReport.MESSAGE_TYPE, 6, ObjectTypeEnum.AUXILIARY_SENSOR, true);
	}

	protected Info createInfo(int number, short[] data) {
        return new AuxiliaryStatusInfo(number);
    }
}
