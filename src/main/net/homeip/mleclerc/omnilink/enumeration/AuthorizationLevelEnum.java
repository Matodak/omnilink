package net.homeip.mleclerc.omnilink.enumeration;

@SuppressWarnings("serial")
public class AuthorizationLevelEnum extends Enum
{
    public final static EnumInfo metaInfo = new EnumInfo();

    public final static AuthorizationLevelEnum INVALID_CODE = new AuthorizationLevelEnum("Invalid code", 0);
    public final static AuthorizationLevelEnum MASTER = new AuthorizationLevelEnum("Master", 1);
    public final static AuthorizationLevelEnum MANAGER = new AuthorizationLevelEnum("Manager", 2);
    public final static AuthorizationLevelEnum USER = new AuthorizationLevelEnum("User", 3);

    public AuthorizationLevelEnum(String userLabel, int value)
    {
        super(userLabel, value);
        metaInfo.add(this);
    }
}
