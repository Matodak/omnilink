package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.message.UploadNameMessageReport.NameInfo;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ReadNameReport extends ExpectedReplyMessage {
	private NameInfo info;
	
    protected ReadNameReport() {
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x0E, 0); // Variable reply length
	}

	protected void checkMessageLength(int messageLength) throws CommunicationException {
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		info = new NameInfo(0);
		info.dataChanged(data);
	}
	
	public NameInfo getInfo() {
		return info;
	}
	
    public String toString() {
    	return info.toString();
    }
}
