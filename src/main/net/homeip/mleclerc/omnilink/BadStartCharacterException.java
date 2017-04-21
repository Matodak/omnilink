package net.homeip.mleclerc.omnilink;

@SuppressWarnings("serial")
public class BadStartCharacterException extends CommunicationException {
	
	private int startChar;
	private int expectedStartChar;
	
	public BadStartCharacterException(int startChar, int expectedStartChar) {
		super("Bad start char: " + startChar + " should be: " + expectedStartChar);
		
		this.startChar = startChar;
		this.expectedStartChar = expectedStartChar;
	}
	
	public int getStartChar() {
		return startChar;
	}
	
	public int getExpectedStartChar() {
		return expectedStartChar;
	}
}
