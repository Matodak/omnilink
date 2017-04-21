package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.AuthorizationLevelEnum;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;

@SuppressWarnings("serial")
public class SecurityCodeValidationReport extends ExpectedReplyMessage
{
    private int userCodeNumber;
    private AuthorizationLevelEnum authorizationLevel;

    public SecurityCodeValidationReport()
    {
    }

	protected void initialize(SystemTypeEnum model,	ProtocolTypeEnum protocolType) throws CommunicationException {
		super.initialize(model, protocolType, 0x27, 0x03);
	}

	protected void dataChanged(short[] data) throws CommunicationException
    {
        userCodeNumber = data[0];
        authorizationLevel = (AuthorizationLevelEnum) MessageHelper.valueToEnum(AuthorizationLevelEnum.metaInfo, data[1]);
    }

    public int getUserCodeNumber()
    {
        return userCodeNumber;
    }

    public AuthorizationLevelEnum getAuthorizationLevel()
    {
        return authorizationLevel;
    }

    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append("USER CODE NUMBER: " + getUserCodeNumber() + "\n");
        strbuf.append("AUTHORIZATION LEVEL: " + getAuthorizationLevel() + "\n");
        return strbuf.toString();
    }
}
