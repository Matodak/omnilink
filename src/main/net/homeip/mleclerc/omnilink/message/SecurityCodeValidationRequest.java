package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.RequestMessage;

@SuppressWarnings("serial")
public class SecurityCodeValidationRequest extends RequestMessage {
	private String userCode;
	private int area;
	
    public SecurityCodeValidationRequest(String userCode) {
        this(userCode, MessageConstants.DEFAULT_AREA);
    }

    public SecurityCodeValidationRequest(String userCode, int area) {
        this.userCode = userCode;
        this.area = area;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        MessageHelper.validateValue(area, 0, 16);

        short[] ucb = MessageHelper.userCodeToBytes(userCode);
        short[] data = new short[1 + ucb.length];
        data[0] = (short) area;
        System.arraycopy(ucb, 0, data, 1, ucb.length);
		super.initialize(model, protocolType, 0x26, 0x06, data);
	}

	public ReplyMessage createReplyMessage(){
        return new SecurityCodeValidationReport();
    }
}
