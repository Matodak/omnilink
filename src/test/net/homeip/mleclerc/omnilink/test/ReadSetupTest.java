package net.homeip.mleclerc.omnilink.test;

import net.homeip.mleclerc.omnilink.EndOfDataException;
import net.homeip.mleclerc.omnilink.MessageManager;
import net.homeip.mleclerc.omnilink.NetworkCommunication;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.message.ReadSetupReport;
import net.homeip.mleclerc.omnilink.message.ReadSetupRequest;
import net.homeip.mleclerc.omnilink.message.UploadSetupMessageReport;

public class ReadSetupTest {
    private final static SystemTypeEnum SYSTEM_TYPE = SystemTypeEnum.HAI_OMNI_IIE;
    private final static String IP_ADDRESS = "192.168.1.3";
    private final static int PORT = 4369;
    private final static String PRIVATE_KEY = "a0-b1-c2-d3-e4-f5-a6-b7-c8-d9-e0-f1-a2-b3-c4-d5";
    private final static ProtocolTypeEnum PROTOCOL_TYPE = ProtocolTypeEnum.HAI_OMNI_LINK_II;
    private final static int DIAL_PHONE_NUMBER_COUNT = 8;
    private final static int BYTE_COUNT = 200;
    
	public static void main(String[] args) throws Exception {
        // Open communication with the system
		MessageManager comm = new NetworkCommunication(SYSTEM_TYPE, IP_ADDRESS, PORT, NetworkCommunication.DEFAULT_TIMEOUT, PRIVATE_KEY, PROTOCOL_TYPE);
        comm.open();

        // Get the setup data
        short[] data = new short[0];
        int index = 0;
        while(true) {
        	try {
	            ReadSetupRequest request1 = new ReadSetupRequest(index, BYTE_COUNT);
	            ReadSetupReport response1 = (ReadSetupReport) comm.execute(request1);
	            short[] data1 = response1.getInfo();
	            index = response1.getNextStartIndex();	            
	            short data2[] = new short[data.length + data1.length];
	            System.arraycopy(data, 0, data2, 0, data.length);
	            System.arraycopy(data1, 0, data2, data.length, data1.length);
	            data = data2;
        	} catch(EndOfDataException ex) {
        		break;
        	}
        }
        
		// Extract rings before answer
		int ringsBeforeAnswer = UploadSetupMessageReport.getRingsBeforeAnswer(data);
		
		// Extract "my phone number"
		String myPhoneNumber = UploadSetupMessageReport.getMyPhoneNumber(data);
		
		// Extract phone numbers
		String[] dialPhoneNumbers = UploadSetupMessageReport.getDialPhoneNumbers(data, DIAL_PHONE_NUMBER_COUNT);
		
		// Display results
    	StringBuffer strbuf = new StringBuffer();
    	strbuf.append("RINGS BEFORE ANSWER: " + ringsBeforeAnswer + "\n");
    	strbuf.append("MY PHONE NUMBER: " + myPhoneNumber + "\n");;
    	for (int i = 0; i < dialPhoneNumbers.length; i++) {
    		strbuf.append("DIAL PHONE NUMBER " + (i + 1) + ": " + dialPhoneNumbers[i] + "\n");
    	}    	
    	System.out.println(strbuf);
    	
    	System.exit(0);
	}
}
