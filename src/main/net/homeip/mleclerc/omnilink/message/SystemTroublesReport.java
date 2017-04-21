package net.homeip.mleclerc.omnilink.message;

import java.util.ArrayList;
import java.util.Collection;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.TroubleTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class SystemTroublesReport extends ExpectedReplyMessage{
	private int troubleCount;
	private Collection<TroubleTypeEnum> troubles = new ArrayList<TroubleTypeEnum>();
	
	protected SystemTroublesReport() {
	}
	
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x1B, 0); // Variable message length
	}

	protected void checkMessageLength(int messageLength) throws CommunicationException {
		troubleCount = messageLength - 1;
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		for (int i = 0; i < troubleCount; i++) {
			int troubleId = data[i];
			TroubleTypeEnum trouble = (TroubleTypeEnum) MessageHelper.valueToEnum(TroubleTypeEnum.metaInfo, troubleId);
			troubles.add(trouble);
		}
	}

	public Collection<TroubleTypeEnum> getTroubles() {
		return troubles;
	}
	
	public String toString() {
		StringBuffer strbuf = new StringBuffer();
        strbuf.append("TROUBLES: " + MessageHelper.getEnumItemsString(getTroubles()) + "\n");     
        return strbuf.toString();
	}
}
