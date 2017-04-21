package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.UploadMessageReport;

@SuppressWarnings("serial")
public class UploadSetupMessageReport extends UploadMessageReport {

	private final static int DIAL_PHONE_NUMBER_COUNT = 8;
	
	private short[] aggregatedData = new short[0];
	private int ringsBeforeAnswer;
	private String[] dialPhoneNumbers = new String[DIAL_PHONE_NUMBER_COUNT];
	private String myPhoneNumber;
	
	public UploadSetupMessageReport() {
	}
	
	protected void addData(short[] data) throws CommunicationException {
    	// Append to the data received global structure
    	// Don't copy the first 2 bytes as they contain counters
    	short[] newAggregatedData = new short[aggregatedData.length + data.length - 2];
    	System.arraycopy(aggregatedData, 0, newAggregatedData, 0, aggregatedData.length);
    	System.arraycopy(data, 2, newAggregatedData, aggregatedData.length, data.length - 2);
    	aggregatedData = newAggregatedData;
	}
    
	protected int getSmallReplyId(short[] data) {
		// Not used
		return 0;
	}

	protected Info createInfo(int number, short[] data) {
		// Not used
		return null;
	}

	protected void onCompleted() {
		// Extract rings before answer
		ringsBeforeAnswer = getRingsBeforeAnswer(aggregatedData);
		
		// Extract "my phone number"
		myPhoneNumber = getMyPhoneNumber(aggregatedData);
		
		// Extract phone numbers
		dialPhoneNumbers = getDialPhoneNumbers(aggregatedData, dialPhoneNumbers.length);
	}

	public int getRingsBeforeAnswer() {
		return ringsBeforeAnswer;
	}
	
	public String getMyPhoneNumber() {
		return myPhoneNumber;
	}
	
	public String[] getDialPhoneNumbers() {		
		return dialPhoneNumbers;
	}
	
	public String toString() {
    	StringBuffer strbuf = new StringBuffer();
    	strbuf.append("RINGS BEFORE ANSWER: " + ringsBeforeAnswer + "\n");
    	strbuf.append("MY PHONE NUMBER: " + myPhoneNumber + "\n");;
    	String[] dialPhoneNumbers = getDialPhoneNumbers();
    	for (int i = 0; i < dialPhoneNumbers.length; i++) {
    		strbuf.append("DIAL PHONE NUMBER " + (i + 1) + ": " + dialPhoneNumbers[i] + "\n");
    	}
    	return strbuf.toString();
    }
	
	public static int getRingsBeforeAnswer(short[] data) {
		return data[4];
	}
	
	public static String getMyPhoneNumber(short[] data) {
		return MessageHelper.toPhoneNumber(data, 6, 30);
	}
	
	public static String[] getDialPhoneNumbers(short[] data, int phoneNumberCount) {
		String[] dialPhoneNumbers = new String[phoneNumberCount];
		
		for (int i = 0; i < dialPhoneNumbers.length; i++) {
			int delta = i * 35;
			dialPhoneNumbers[i] = MessageHelper.toPhoneNumber(data, 31 + delta, 55 + delta);
		}
		
		return dialPhoneNumbers;
	}
}
