package net.homeip.mleclerc.omnilink;

@SuppressWarnings("serial")
public class EndOfDataException extends CommunicationException {
	public EndOfDataException() {
		super("No more objects found");
	}
}
