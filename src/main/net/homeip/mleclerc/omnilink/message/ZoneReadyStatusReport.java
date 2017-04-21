package net.homeip.mleclerc.omnilink.message;

import java.util.Map;
import java.util.TreeMap;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class ZoneReadyStatusReport extends ExpectedReplyMessage {
    private int zoneBlockCount;
    private Map<Integer, Boolean> zoneReadyStatuses = new TreeMap<Integer, Boolean>();

    protected ZoneReadyStatusReport() {
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
		super.initialize(model, protocolType, 0x39, 0); // Variable message length
	}

	protected void checkMessageLength(int messageLength) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(ProtocolTypeEnum.HAI_OMNI_LINK_II);
		this.zoneBlockCount = messageLength - 1;
	}

	protected void dataChanged(short[] data) throws CommunicationException {
		for (int zoneBlock = 0; zoneBlock < zoneBlockCount; zoneBlock++) {
			short zoneBlockStatuses = data[zoneBlock];
			for (int zoneBlockBitNo = 0; zoneBlockBitNo < 8; zoneBlockBitNo++) {
				int zoneNo = zoneBlock * 8 + (8 - zoneBlockBitNo);
				boolean zoneNotReady = MessageHelper.isBitOn(zoneBlockStatuses, zoneBlockBitNo);
				zoneReadyStatuses.put(zoneNo, zoneNotReady);
			}
		}
	}
	
	public boolean isZoneNotReady(int zoneNo) {
		return zoneReadyStatuses.get(zoneNo);
	}
	
    public String toString() {
        StringBuffer strbuf = new StringBuffer();

        for (int zoneNo : zoneReadyStatuses.keySet()) {
            strbuf.append("ZONE " + zoneNo + " NOT READY: " + isZoneNotReady(zoneNo) + "\n");
        }

        return strbuf.toString();
    }
}
