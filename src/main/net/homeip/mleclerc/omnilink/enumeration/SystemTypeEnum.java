package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class SystemTypeEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static SystemTypeEnum HAI_OMNI = new SystemTypeEnum("HAI Omni", 2);
    public final static SystemTypeEnum HAI_OMNI_PRO = new SystemTypeEnum("HAI OmniPro", 4);
    public final static SystemTypeEnum HAI_OMNI_PRO_II = new SystemTypeEnum("HAI OmniPro II", 16);
    public final static SystemTypeEnum HAI_OMNI_LT = new SystemTypeEnum("HAI OmniLT", 9);
    public final static SystemTypeEnum HAI_OMNI_II = new SystemTypeEnum("HAI Omni II", 15);
    public final static SystemTypeEnum HAI_OMNI_IIE = new SystemTypeEnum("HAI Omni IIe", 30);
    public final static SystemTypeEnum HAI_OMNI_IIE_B = new SystemTypeEnum("HAI Omni IIe", 33);
    public final static SystemTypeEnum HAI_LUMINA = new SystemTypeEnum("HAI Lumina", 36);
    public final static SystemTypeEnum HAI_LUMINA_PRO = new SystemTypeEnum("HAI Lumina Pro", 37);
    public final static SystemTypeEnum AEGIS_2000 = new SystemTypeEnum("Aegis 2000", 5);
    public final static SystemTypeEnum AEGIS = AEGIS_2000;
    
    private SystemTypeEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
