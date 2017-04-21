package net.homeip.mleclerc.omnilink.message;

import net.homeip.mleclerc.omnilink.CommunicationException;
import net.homeip.mleclerc.omnilink.enumeration.ProtocolTypeEnum;
import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;
import net.homeip.mleclerc.omnilink.messagebase.ExpectedReplyMessage;
import net.homeip.mleclerc.omnilink.messagebase.MessageHelper;
import net.homeip.mleclerc.omnilink.messagebase.ProtocolTypeHelper;

@SuppressWarnings("serial")
public class SystemInformationReport extends ExpectedReplyMessage
{
    private String localPhoneNumber = null;
    private SystemTypeEnum model = null;
    private int modelNumber = 0;
    private int majorVersion = 0;
    private int minorVersion = 0;
    private int revision = 0;

    protected SystemInformationReport()
    {
    }

	protected void initialize(SystemTypeEnum model, ProtocolTypeEnum protocolType) throws CommunicationException {
		if (ProtocolTypeHelper.isOmniLinkII(protocolType)) {
			super.initialize(model, protocolType, 0x17, 0x1E);
		} else {
			super.initialize(model, protocolType, 0x12, 0x1E);
		}
	}

	protected void dataChanged(short[] data) throws CommunicationException
    {
        // Extract the model number
        modelNumber = data[0];
        model = (SystemTypeEnum) MessageHelper.valueToEnum(SystemTypeEnum.metaInfo, modelNumber);

        // Extract the major version
        majorVersion = data[1];

        // Extract the minor version
        minorVersion = data[2];

        // Extract the revision
        revision = data[3];

        // Extract the local phone number
        localPhoneNumber = MessageHelper.toPhoneNumber(data, 4, 28);
    }

    public SystemTypeEnum getModel()
    {
        return model;
    }

    public int getModelNumber()
    {
        return modelNumber;
    }

    public int getMajorVersion()
    {
        return majorVersion;
    }

    public int getMinorVersion()
    {
        return minorVersion;
    }

    public int getRevision()
    {
        return revision;
    }

    public String getLocalPhoneNumber()
    {
        return localPhoneNumber;
    }

    public String getVersion()
    {
        return majorVersion + "." + minorVersion + MessageHelper.toRevisionCode(revision);
    }

    public String toString()
    {
        StringBuffer strbuf = new StringBuffer();
        strbuf.append("MODEL NUMBER: " + getModelNumber() + " (" + getModel() + ")\n");
        strbuf.append("MAJOR VERSION: " + getMajorVersion() + "\n");
        strbuf.append("MINOR VERSION: " + getMinorVersion() + "\n");
        strbuf.append("REVISION: " + getRevision() + "\n");
        strbuf.append("VERSION: " + getVersion() + "\n");
        strbuf.append("PHONE NUMBER: " + getLocalPhoneNumber() + "\n");
        return strbuf.toString();
    }
}
