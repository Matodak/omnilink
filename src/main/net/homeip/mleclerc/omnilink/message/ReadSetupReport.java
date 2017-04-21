package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ReadSetupReport extends ExpectedReplyMessage {
	private int startIndex;
	private int byteCount;
	private short[] data;
	
    protected ReadSetupReport(int startIndex, int byteCount) {
    	this.startIndex = startIndex;
    	this.byteCount = byteCount;
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x06, byteCount + 1);
	}

	protected void checkMessageLength(int messageLength) throws CommunicationException {
		// Variable length
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		// Get the content
		this.data = data;
	}
	
	public short[] getInfo() {
		return data;
	}

	public int getNextStartIndex() {
		return startIndex + data.length;
	}

    public String toString() {
    	StringBuffer strbuf = new StringBuffer();
    	
    	strbuf.append("DATA BYTE COUNT: " + data.length + "\n");
    	strbuf.append("NEXT START INDEX: " + getNextStartIndex() + "\n");
    	
    	strbuf.append("DATA (bytes): ");
		for (int i = 0; i < data.length; i++) {
			short b = data[i];
			strbuf.append(Short.toString(b) + "|");
		}
		strbuf.append("\n");

    	strbuf.append("DATA (chars): ");
		for (int i = 0; i < data.length; i++) {
			short b = data[i];
			strbuf.append(((char) b) + "|");
		}
		strbuf.append("\n");
		
		return strbuf.toString();
    }
}
