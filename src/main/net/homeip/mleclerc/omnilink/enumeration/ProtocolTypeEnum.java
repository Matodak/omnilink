package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class ProtocolTypeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static ProtocolTypeEnum HAI_OMNI_LINK = new ProtocolTypeEnum("HAI Omni-Link", 1);
    public final static ProtocolTypeEnum HAI_OMNI_LINK_II = new ProtocolTypeEnum("HAI Omni-Link II", 2);
    
    private ProtocolTypeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
