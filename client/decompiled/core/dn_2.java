/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;

/*
 * Renamed from dn
 */
abstract class dn_2
implements Bk,
Serializable {
    protected String name;

    dn_2() {
    }

    public String getName() {
        return this.name;
    }

    protected Object readResolve() {
        return LD.D(this.getName());
    }
}

