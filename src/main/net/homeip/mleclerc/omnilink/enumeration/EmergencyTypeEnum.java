package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class EmergencyTypeEnum extends Enum {
     public final static EnumInfo metaInfo = new EnumInfo();

     public final static EmergencyTypeEnum BURGLARY = new EmergencyTypeEnum("Burglary", 1);
     public final static EmergencyTypeEnum FIRE = new EmergencyTypeEnum("Fire", 2);
     public final static EmergencyTypeEnum AUXILIARY = new EmergencyTypeEnum("Auxiliary", 3);

     public EmergencyTypeEnum(String userLabel, int value) {
         super(userLabel, value);
         metaInfo.add(this);
    }
}
