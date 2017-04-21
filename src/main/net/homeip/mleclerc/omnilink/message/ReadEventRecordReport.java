package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.message.UploadEventLogMessageReport.EventLogInfo;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ReadEventRecordReport extends ExpectedReplyMessage {
	private EventLogInfo eventLogData;
	
    protected ReadEventRecordReport() {
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x25, 0x0C);
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		// Get the event number
		int eventNo = MessageHelper.createWord(data[0], data[1]);
		eventLogData = new EventLogInfo(eventNo, 0);
		short[] newData = new short[data.length - 2];
        System.arraycopy(data, 2, newData, 0, newData.length);
		eventLogData.dataChanged(newData);
	}
	
	public EventLogInfo getInfo() {
		return eventLogData;
	}
	
    public String toString() {
    	return eventLogData.toString();
    }
}
