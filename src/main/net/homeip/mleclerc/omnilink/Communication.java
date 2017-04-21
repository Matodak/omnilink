package net.homeip.mleclerc.omnilink;

import net.homeip.mleclerc.omnilink.enumeration.SystemTypeEnum;

/**
 * @deprecated
 */
public class Communication extends SerialCommunication
{
    public Communication(SystemTypeEnum model, String comPortStr, int baudRate)
    {
    	super(model, comPortStr, baudRate);
    }

    public Communication(SystemTypeEnum model, String comPortStr, int baudRate, String initCmd, String localAccessCmd)
    {
        super(model, comPortStr, baudRate, initCmd, localAccessCmd);
    }
}
