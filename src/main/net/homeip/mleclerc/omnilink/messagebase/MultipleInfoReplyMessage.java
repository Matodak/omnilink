package net.homeip.mleclerc.omnilink.messagebase;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ObjectTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public abstract class MultipleInfoReplyMessage extends ExpectedReplyMessage
{
    public static abstract class Info implements Serializable
    {
        private int number = -1;

        public Info(int number)
        {
            this.number = number;
        }

        protected abstract void dataChanged(short[] data) throws CommunicationException;

        public int getNumber()
        {
            return number;
        }

        public String toString()
        {
            StringBuffer strbuf = new StringBuffer();
            strbuf.append("NUMBER: " + getNumber() + "\n");
            return strbuf.toString();
        }
    }

    private Map<Integer, Info> infoMap = new TreeMap<Integer, Info>();
    private int infoSize;
    private int firstNumber;
    private int lastNumber;
    private int objectCount;
    private ObjectTypeEnum objectType;
    private boolean extendedReply;

    protected MultipleInfoReplyMessage() {
    }
    
    protected MultipleInfoReplyMessage(int firstNumber, int lastNumber) {
    	this.firstNumber = firstNumber;
    	this.lastNumber = lastNumber;
    }

    protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType, int messageType, int infoSize, ObjectTypeEnum objectType) throws CommunicationException {
    	this.initialize(model, protocolType, messageType, infoSize, objectType, false);
    }

    protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType, int messageType, int infoSize, ObjectTypeEnum objectType, boolean extendedReply) throws CommunicationException {
    	ProtocolTypeHelper.ensureOmniLinkII(protocolType);
    	this.infoSize = infoSize;
    	this.objectType = objectType;
    	this.extendedReply = extendedReply;
        super.initialize(model, protocolType, messageType, 0); // Variable length reply
    }

    protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType, int messageType, int infoSize) throws CommunicationException {
    	this.infoSize = infoSize;
        int expectedMessageLength = (infoSize * (lastNumber - firstNumber + 1) + 1);
        super.initialize(model, protocolType, messageType, expectedMessageLength);
    }

    protected abstract Info createInfo(int number, short[] data) throws CommunicationException;

	protected void checkMessageLength(int messageLength) throws CommunicationException {
		if (objectType != null && ProtocolTypeHelper.isOmniLinkII(getProtocolType())) {
			objectCount = (messageLength - 2) / infoSize;
		} else {
			super.checkMessageLength(messageLength);
		}
	}

    protected void dataChanged(short[] data) throws CommunicationException {
    	if (objectType != null && ProtocolTypeHelper.isOmniLinkII(getProtocolType())) {
    		int offset = 1;
    		int objectTypeVal = data[0];
    		if (objectTypeVal != objectType.getValue()) {
    			throw new CommunicationException("Unexpected object type in reply: " + objectTypeVal + " Should be: " + objectType.getValue());
    		}
    		if (extendedReply) {
    			offset += 1;
    			int objectRecordLength = data[1];
    			if (objectRecordLength != infoSize) {
        			throw new CommunicationException("Unexpected object record length in reply: " + objectRecordLength + " Should be: " + infoSize);
    			}
    		}
    		for (int i = 0; i < objectCount; i++) {
    			int pos = offset + (i * infoSize);
    			int objectNo = MessageHelper.createWord(data[pos], data[pos + 1]);
    			short[] newData = new short[infoSize - 2];
	            System.arraycopy(data, pos + 2, newData, 0, infoSize - 2);
	            addInfo(objectNo, newData);
    		}
    	} else {
	        int blockCount = ( getMessageLength() - 1 ) / infoSize;
	        for (int i = 0; i < blockCount; i++) {
	            int pos = i * infoSize;
	            short[] newData = new short[infoSize];
	            System.arraycopy(data, pos, newData, 0, infoSize);
	            addInfo(firstNumber + i, newData);
	        }
    	}
    }

    protected void addInfo(int number, short[] data) throws CommunicationException
    {
        Info info = createInfo(number, data);
        info.dataChanged(data);
        infoMap.put(new Integer(number), info);
    }

    public Info getInfo(int number) throws CommunicationException
    {
        Info ret = (Info) infoMap.get(new Integer(number));
        if (ret == null)
            throw new CommunicationException("Info not found: " + number);

        return ret;
    }

    public Collection getInfoList()
    {
        return infoMap.values();
    }

    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        for (Iterator iter = getInfoList().iterator(); iter.hasNext(); )
        {
            strbuf.append(iter.next());
            if (iter.hasNext())
                strbuf.append("\n");
        }

        return strbuf.toString();
    }
}
