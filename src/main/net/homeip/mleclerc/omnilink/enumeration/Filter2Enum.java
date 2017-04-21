package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class Filter2Enum extends Enum {
	public final static EnumInfo metaInfo = new EnumInfo();

	public final static Filter2Enum NONE = new Filter2Enum("None", 0);
	public final static Filter2Enum ALL_AREAS = new Filter2Enum("All Areas", 255);
	public final static Filter2Enum AREA_1 = new Filter2Enum("Area 1", 1<<0);
	public final static Filter2Enum AREA_2 = new Filter2Enum("Area 2", 1<<1);
	public final static Filter2Enum AREA_3 = new Filter2Enum("Area 3", 1<<2);
	public final static Filter2Enum AREA_4 = new Filter2Enum("Area 4", 1<<3);
	public final static Filter2Enum AREA_5 = new Filter2Enum("Area 5", 1<<4);
	public final static Filter2Enum AREA_6 = new Filter2Enum("Area 6", 1<<5);
	public final static Filter2Enum AREA_7 = new Filter2Enum("Area 7", 1<<6);
	public final static Filter2Enum AREA_8 = new Filter2Enum("Area 8", 1<<7);

    public Filter2Enum(String userLabel, int value) {
	    super(userLabel, value);
	    metaInfo.add(this);
	}
}
