package net.homeip.mleclerc.omnilink.message;

import java.io.InputStream;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHandler;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;

@SuppressWarnings("serial")
public class ExtendedObjectStatusReport extends ReplyMessage {
	public static int MESSAGE_TYPE = 0x3B;
	
    private MultipleInfoReplyMessage objectStatus;
    
    public ExtendedObjectStatusReport(ObjectTypeEnum objectType) throws CommunicationException {
		if (objectType.equals(ObjectTypeEnum.ZONE)) {
			objectStatus = new ExtendedZoneStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.UNIT)) {
			objectStatus = new ExtendedUnitStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.AREA)) {
			objectStatus = new ExtendedAreaStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.THERMOSTAT)) {
			objectStatus = new ExtendedThermostatStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.MESSAGE)) {
			objectStatus = new ExtendedMessageStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.AUXILIARY_SENSOR)) {
			objectStatus = new ExtendedAuxiliaryStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.AUDIO_ZONE)) {
			objectStatus = new ExtendedAudioZoneStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.EXPANSION_ENCLOSURE)) {
			objectStatus = new ExtendedExpansionEnclosureStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.USER_SETTING)) {
			objectStatus = new ExtendedUserSettingStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.ACCESS_CONTROL_READER)) {
			objectStatus = new ExtendedAccessControlReaderStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.ACCESS_CONTROL_LOCK)) {
			objectStatus = new ExtendedAccessControlLockStatusReport();
		} else {
			throw new CommunicationException("Unsupported object type: " + objectType);
		}
    }
    
	public void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		// Not invoked
	}

	public boolean execute(SystemTypeEnum model, ProtocolTypeEnum protocolType,	InputStream is) throws CommunicationException {
		return objectStatus.execute(model, protocolType, is);
	}

	public boolean execute(SystemTypeEnum model, ProtocolTypeEnum protocolType, MessageHandler listener) throws CommunicationException {
		return objectStatus.execute(model, protocolType, listener);
	}

	public MultipleInfoReplyMessage getObjectStatus() {
		return objectStatus;
	}
	
	public String toString() {
		return getObjectStatus().toString();
	}
}
