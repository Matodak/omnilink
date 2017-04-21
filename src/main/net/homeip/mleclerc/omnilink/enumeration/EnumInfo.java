package net.homeip.mleclerc.omnilink.enumeration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EnumInfo
{
    private List<Enum> enums = new ArrayList<Enum>();

    public void add(Enum enumeration)
    {
        enums.add(enumeration);
    }

    public Collection<Enum> members()
    {
        return enums;
    }

    public Enum getByValue(int value)
    {
        for (Iterator iter = enums.iterator(); iter.hasNext(); )
        {
            Enum enumeration = (Enum) iter.next();
            if (enumeration.getValue() == value)
                return enumeration;
        }

        return null;
    }

    public Enum getByUserLabel(String userLabel)
    {
        for (Iterator iter = enums.iterator(); iter.hasNext(); )
        {
            Enum enumeration = (Enum) iter.next();
            if (enumeration.getUserLabel().equalsIgnoreCase(userLabel))
                return enumeration;
        }

        return null;
    }
}
