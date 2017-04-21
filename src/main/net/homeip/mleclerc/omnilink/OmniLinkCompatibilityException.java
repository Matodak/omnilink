package net.homeip.mleclerc.omnilink;

@SuppressWarnings("serial")
public class OmniLinkCompatibilityException extends CommunicationException {
	public OmniLinkCompatibilityException() {
		super("Message only supported on OmniLink");
	}
}
