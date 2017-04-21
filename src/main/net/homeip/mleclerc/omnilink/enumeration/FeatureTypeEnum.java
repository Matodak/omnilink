package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class FeatureTypeEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static FeatureTypeEnum NUVO_CONCERTO = new FeatureTypeEnum("NuVo Concerto", 1);
    public final static FeatureTypeEnum NUVO_ESSENTIA = new FeatureTypeEnum("NuVo Essentia/Simplese", 2);
    public final static FeatureTypeEnum NUVO_GRAND_CONCERTO = new FeatureTypeEnum("NuVo Grand Concerto", 3);
    public final static FeatureTypeEnum RUSSOUND = new FeatureTypeEnum("Russound", 4);
    public final static FeatureTypeEnum HAI_HIFI = new FeatureTypeEnum("HAI Hi-Fi", 5);
    public final static FeatureTypeEnum XANTECH = new FeatureTypeEnum("Xantech", 6);
    public final static FeatureTypeEnum SPEAKERCRAFT = new FeatureTypeEnum("Speakercraft", 7);
    public final static FeatureTypeEnum PROFICIENT = new FeatureTypeEnum("Duress", 8);
    public final static FeatureTypeEnum DHC_SECURITY = new FeatureTypeEnum("DHC Security", 9);

    public FeatureTypeEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
