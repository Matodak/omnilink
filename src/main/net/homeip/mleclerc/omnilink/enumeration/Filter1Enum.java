package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class Filter1Enum extends Enum {
	public final static EnumInfo metaInfo = new EnumInfo();

	public final static Filter1Enum NAMED_OR_UNNAMED = new Filter1Enum("Named or Unnamed", 0);
	public final static Filter1Enum NAMED = new Filter1Enum("Named", 1);
	public final static Filter1Enum UNNAMED = new Filter1Enum("Unnamed", 2);

    public Filter1Enum(String userLabel, int value) {
	    super(userLabel, value);
	    metaInfo.add(this);
	}
}
