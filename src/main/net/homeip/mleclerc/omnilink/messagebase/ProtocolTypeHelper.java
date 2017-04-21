package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.OmniLinkIICompatibilityException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;

public class ProtocolTypeHelper {
	static public boolean isOmniLink(ProtocolTypeEnum protocolType) {
		return protocolType.equals(ProtocolTypeEnum.HAI_OMNI_LINK);
	}
	
	static public boolean isOmniLinkII(ProtocolTypeEnum protocolType) {
		return protocolType.equals(ProtocolTypeEnum.HAI_OMNI_LINK_II);
	}
	
	static public void ensureOmniLinkII(ProtocolTypeEnum protocolType) throws OmniLinkIICompatibilityException {
		if (!isOmniLinkII(protocolType)) {
			throw new OmniLinkIICompatibilityException();
		}
	}
	
	static public void ensureOmniLink(ProtocolTypeEnum protocolType) throws CommunicationException {
		if (!isOmniLink(protocolType)) {
			throw new CommunicationException("Message only supported on OmniLink");
		}
	}
}
