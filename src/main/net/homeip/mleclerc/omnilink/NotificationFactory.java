package net.homeip.mleclerc.omnilink;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.homeip.mleclerc.omnilink.NetworkCommunication.PacketInfo;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.message.ExtendedObjectStatusReport;
import net.homeip.mleclerc.omnilink.message.ObjectStatusReport;
import net.homeip.mleclerc.omnilink.message.SystemEventsReport;
import net.homeip.mleclerc.omnilink.messagebase.Message;

public class NotificationFactory {
	public static Message createNotification(SystemTypeEnum model, ProtocolTypeEnum protocolType, PacketInfo packetInfo) throws CommunicationException {
		int messageType = packetInfo.appData[2];
		if (messageType == ExtendedObjectStatusReport.MESSAGE_TYPE) {
			// Extended object status report
			ObjectTypeEnum objectType = (ObjectTypeEnum) ObjectTypeEnum.metaInfo.getByValue(packetInfo.appData[3]);
			ExtendedObjectStatusReport report = new ExtendedObjectStatusReport(objectType);
			InputStream is = new ByteArrayInputStream(packetInfo.appData);
			report.execute(model, protocolType, is);
			return report.getObjectStatus();			
		} else if (messageType == ObjectStatusReport.MESSAGE_TYPE) {
			// Object status report
			ObjectTypeEnum objectType = (ObjectTypeEnum) ObjectTypeEnum.metaInfo.getByValue(packetInfo.appData[3]);
			ObjectStatusReport report = new ObjectStatusReport(objectType);
			InputStream is = new ByteArrayInputStream(packetInfo.appData);
			report.execute(model, protocolType, is);
			return report.getObjectStatus();
		} else if (messageType == SystemEventsReport.MESSAGE_TYPE) {
			SystemEventsReport report = new SystemEventsReport();
			InputStream is = new ByteArrayInputStream(packetInfo.appData);
			report.execute(model, protocolType, is);
			return report;
		} else {
			System.out.println("Unhandled notification. Message type: " + messageType);
		}
		
		return null;
	}
}
