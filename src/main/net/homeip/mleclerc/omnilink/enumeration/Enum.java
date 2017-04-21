package net.homeip.mleclerc.omnilink.enumeration;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Enum implements Serializable
{
    private String userLabel;
    private int value;

    public Enum(String userLabel, int value)
    {
        this.userLabel = userLabel;
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public String getUserLabel()
    {
        return userLabel;
    }

    public String toString()
    {
        return userLabel;
    }

    public boolean equals(Object o)
    {
        if (o instanceof Enum)
        {
            Enum enumeration = (Enum) o;
            return enumeration.getValue() == getValue() && enumeration.getUserLabel().equals(getUserLabel());
        }

        return false;
    }
}
