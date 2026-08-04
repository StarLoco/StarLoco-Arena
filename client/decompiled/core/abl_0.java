/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;

/*
 * Renamed from aBl
 */
public class abl_0
implements Serializable {
    private static final long serialVersionUID = 637783570208674312L;
    final String dre;
    final String version;
    private final boolean exact;

    public abl_0(String string, String string2) {
        this.dre = string;
        this.version = string2;
        this.exact = true;
    }

    public abl_0(String string, String string2, boolean bl2) {
        this.dre = string;
        this.version = string2;
        this.exact = bl2;
    }

    public String aNk() {
        return this.dre;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean aNl() {
        return this.exact;
    }

    public int hashCode() {
        int n2 = 31;
        int n3 = 1;
        n3 = 31 * n3 + (this.dre == null ? 0 : this.dre.hashCode());
        return n3;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        abl_0 abl_02 = (abl_0)object;
        if (this.dre == null ? abl_02.dre != null : !this.dre.equals(abl_02.dre)) {
            return false;
        }
        if (this.exact != abl_02.exact) {
            return false;
        }
        return !(this.version == null ? abl_02.version != null : !this.version.equals(abl_02.version));
    }
}

