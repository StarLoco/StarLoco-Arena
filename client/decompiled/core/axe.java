/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;
import java.util.Iterator;

public interface axe
extends Serializable {
    public static final String djf = "*";
    public static final String djg = "+";

    public String getName();

    public void g(axe var1);

    public boolean h(axe var1);

    public boolean hasChildren();

    public boolean aJN();

    public Iterator iterator();

    public boolean i(axe var1);

    public boolean contains(String var1);

    public boolean equals(Object var1);

    public int hashCode();
}

