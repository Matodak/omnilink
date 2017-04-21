package net.homeip.mleclerc.omnilink.messagebase;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

@SuppressWarnings("serial")
public abstract class UploadMessageReport extends MultipleInfoReplyMessage
{
	public UploadMessageReport() {
		super(0, 0);
	}
	
    protected abstract int getSmallReplyId(short[] data);

    protected void addData(short[] data) throws CommunicationException
    {
        // Get the id that uniquely identifies this block of data (i.e. this reply)
        int key = getSmallReplyId(data);

        // Put the data in a hash table using the key
        addInfo(key, data);
    }
    
	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		super.initialize(model, protocolType, 0, 0);
	}

	protected void onCompleted() {
    }
}
