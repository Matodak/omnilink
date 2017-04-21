package net.homeip.mleclerc.omnilink;

@SuppressWarnings("serial")
public class OmniLinkIICompatibilityException extends CommunicationException {
	public OmniLinkIICompatibilityException() {
		super("Message only supported on OmniLink II");
	}
}
