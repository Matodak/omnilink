package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SecurityModeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.CommandMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class SecurityCommand extends CommandMessage {
	private int area;
	private SecurityModeEnum mode;
	private int code;
	
    public SecurityCommand(SecurityModeEnum mode) {
       this(MessageConstants.DEFAULT_AREA, mode, MessageConstants.DEFAULT_USER_CODE);
    }

    public SecurityCommand(SecurityModeEnum mode, int code) {
        this(MessageConstants.DEFAULT_AREA, mode, code);
    }

    public SecurityCommand(int area, SecurityModeEnum mode, int code) {
    	this.area = area;
    	this.mode = mode;
    	this.code = code;
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
        MessageHelper.validateEnum(mode);
        MessageHelper.validateValue(code, 1, 255);
        MessageHelper.validateValue(area, 1, 16);

        super.initialize(model, protocolType, 48 + mode.getValue(), code, area);
	}
}
