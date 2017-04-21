package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class UnitTypeEnum extends Enum {
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static UnitTypeEnum STANDARD = new UnitTypeEnum("Standard", 1);
    public final static UnitTypeEnum EXTENDED = new UnitTypeEnum("Extended", 2);
    public final static UnitTypeEnum COMPOSE = new UnitTypeEnum("Compose", 3);
    public final static UnitTypeEnum UPB = new UnitTypeEnum("UPB", 4);
    public final static UnitTypeEnum HLC_ROOM = new UnitTypeEnum("HLC Room", 5);
    public final static UnitTypeEnum HLC_LOAD = new UnitTypeEnum("HLC Load", 6);
    public final static UnitTypeEnum LUMINA_MODE = new UnitTypeEnum("Lumina Mode", 7);
    public final static UnitTypeEnum RADIO_RA = new UnitTypeEnum("Radio RA", 8);
    public final static UnitTypeEnum CENTRA_LITE = new UnitTypeEnum("CentraList", 9);
    public final static UnitTypeEnum VIZIA_RF_ROOM = new UnitTypeEnum("ViziaRF Room", 10);
    public final static UnitTypeEnum VIZIA_RF_LOAD = new UnitTypeEnum("ViziaRF Load", 11);
    public final static UnitTypeEnum FLAG = new UnitTypeEnum("Flag", 12);
    public final static UnitTypeEnum OUTPUT = new UnitTypeEnum("Output", 13);
    public final static UnitTypeEnum AUDIO_ZONE = new UnitTypeEnum("Audio Zone", 14);
    public final static UnitTypeEnum AUDIO_SOURCE = new UnitTypeEnum("Audio Source", 15);

    public UnitTypeEnum(String userLabel, int value) {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
