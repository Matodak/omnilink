package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public abstract class MultipleInfoRequestMessage extends RequestMessage {
	protected int firstNumber;
	protected int lastNumber;
	
	protected MultipleInfoRequestMessage(int firstNumber, int lastNumber) {
		this.firstNumber = firstNumber;
		this.lastNumber = lastNumber;
	}
	
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType, int messageType, int messageLength) throws CommunicationException {
    	initialize(model, protocolType, messageType, messageLength, false);
    }

    public void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType, int messageType, int messageLength, boolean checkForHighNumbers) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLink(protocolType);
        // Validate the range specified
        MessageHelper.validateRange(firstNumber, lastNumber);

        // Check for first & last numbers that are higher than 256 in which case a longer version
        // of the request will be sent
        if (checkForHighNumbers && (firstNumber >= 256 || lastNumber >= 256))
        {
        	// Enhanced message
        	
        	// Extract high byte (MSB) and low byte (LSB) for each number specified
        	short firstNumberHigh = MessageHelper.highByte(firstNumber);
        	short firstNumberLow = MessageHelper.lowByte(firstNumber);
        	short lastNumberHigh = MessageHelper.highByte(lastNumber);
        	short lastNumberLow = MessageHelper.lowByte(lastNumber);
        	
            short[] data = new short[] {firstNumberHigh, firstNumberLow, lastNumberHigh, lastNumberLow};
            super.initialize(model, protocolType, messageType, messageLength + 2, data);
        }
        else
        {
        	// Standard message
        	short[] data = new short[] {(short) firstNumber, (short) lastNumber};
        	super.initialize(model, protocolType, messageType, messageLength, data);
        }
    }
    
    public void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType, int messageType, int messageLength, ObjectTypeEnum objectType) throws CommunicationException {
		ProtocolTypeHelper.ensureOmniLinkII(protocolType);
        MessageHelper.validateRange(firstNumber, lastNumber);

        short objectTypeVal = (short) objectType.getValue();
    	short firstNumberHigh = MessageHelper.highByte(firstNumber);
    	short firstNumberLow = MessageHelper.lowByte(firstNumber);
    	short lastNumberHigh = MessageHelper.highByte(lastNumber);
    	short lastNumberLow = MessageHelper.lowByte(lastNumber);
        short[] data = new short[] {objectTypeVal, firstNumberHigh, firstNumberLow, lastNumberHigh, lastNumberLow};
        super.initialize(model, protocolType, messageType, messageLength, data);
    }
}
