package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.MultipleInfoReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class MessageStatusReport extends MultipleInfoReplyMessage
{
    public class MessageStatusInfo extends Info
    {
        private boolean acknowledged;
        private boolean displayed;

        public MessageStatusInfo(int messageNumber)
        {
            super(messageNumber);
        }

        public void dataChanged(short[] data)
        {
            short value = data[0];
            displayed = MessageHelper.isBitOn(value, 0);
            acknowledged = !MessageHelper.isBitOn(value, 1);
        }

        public int getMessage()
        {
            return getNumber();
        }

        public boolean isAcknowledged()
        {
            return acknowledged;
        }

        public boolean isDisplayed()
        {
            return displayed;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append(super.toString());
            strbuf.append("DISPLAYED: " + displayed + "\n");
            strbuf.append("ACKNOWLEDGED: " + acknowledged + "\n");
            return strbuf.toString();
        }
    }

    private boolean memoMessageNotYetPlayed;

    public MessageStatusReport() {
    }

    public MessageStatusReport(int fistMessage, int lastMessage) {
    	super(fistMessage, lastMessage);
    }
    
	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
    	if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
    		super.initialize(model, protocolType, ObjectStatusReport.MESSAGE_TYPE, 3, ObjectTypeEnum.MESSAGE);
    	} else {
	        // Set the expected message length based on the model type passed in
	        int messageStatusEntryCount = MessageHelper.getMessageStatusEntryCount(model);
	    	super.initialize(model, protocolType, 0x25, messageStatusEntryCount);
    	}
    }

    public boolean isMemoMessageNotYetPlayed()
    {
    	return memoMessageNotYetPlayed;
    }
    
    protected void addInfo(int number, short[] data) throws CommunicationException {
    	if (ProtocolTypeHelper.isOmniLink(getProtocolType())) {
	    	// For OmniProII, the first entry indicates if the memo message has been played yet
	    	memoMessageNotYetPlayed = MessageHelper.isBitOn(data[0], 0);
	    	
	        // There are 33 entries in the data array. The first one is not used
	        // the remaining 32 contains 4 statuses per entry for a total of 128 message
	        // statuses
	        for (int entryNo = 1; entryNo < data.length; entryNo++)
	        {
	            // Get the (4) message statuses for the current entry
	            short messageStatuses = data[entryNo];
	
	            // The 4 message status are containing in one data block (of 8 bits)
	            // There is 2 status bits per message
	            // Add 4 status info objects, one for each message contain in one data block
	            for (int subEntryNo = 0; subEntryNo < 4; subEntryNo++)
	            {
	                // Derive the message number
	                int messageNo = (entryNo * 4) - subEntryNo;
	
	                // Get the message status (2 bits)
	                short messageStatus = MessageHelper.getBits(messageStatuses, subEntryNo * 2, 2);
	
	                // Add a new info object for this message
	                super.addInfo(messageNo, new short[] { messageStatus });
	            }
	        }
    	} else {
    		super.addInfo(number, data);
    	}
    }

    protected Info createInfo(int messageNumber, short[] data) {
        return new MessageStatusInfo(messageNumber);
    }
    
    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        
        if (getSystemType().equals(SystemTypeEnum.HAI_OMNI_PRO_II))
        {
        	strbuf.append("MEMO MESSAGE NOT YET PLAYED: " + isMemoMessageNotYetPlayed() + "\n");
        }

        strbuf.append(super.toString());
        
        return strbuf.toString();
    }
}
