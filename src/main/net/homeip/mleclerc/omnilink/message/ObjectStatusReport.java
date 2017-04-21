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
public class ObjectStatusReport extends ReplyMessage {
	public static int MESSAGE_TYPE = 0x23;

    private MultipleInfoReplyMessage objectStatus;
    
    public ObjectStatusReport(ObjectTypeEnum objectType) throws CommunicationException {
		if (objectType.equals(ObjectTypeEnum.ZONE)) {
			objectStatus = new ZoneStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.UNIT)) {
			objectStatus = new UnitStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.AREA)) {
			objectStatus = new AreaStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.THERMOSTAT)) {
			objectStatus = new ThermostatStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.MESSAGE)) {
			objectStatus = new MessageStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.AUXILIARY_SENSOR)) {
			objectStatus = new AuxiliaryStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.AUDIO_ZONE)) {
			objectStatus = new AudioZoneStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.EXPANSION_ENCLOSURE)) {
			objectStatus = new ExpansionEnclosureStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.USER_SETTING)) {
			objectStatus = new UserSettingStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.ACCESS_CONTROL_READER)) {
			objectStatus = new AccessControlReaderStatusReport();
		} else if (objectType.equals(ObjectTypeEnum.ACCESS_CONTROL_LOCK)) {
			objectStatus = new AccessControlLockStatusReport();
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
