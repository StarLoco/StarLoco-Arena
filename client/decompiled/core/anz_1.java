/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anz
 */
public class anz_1
extends ki_1 {
    acd_1 cJi;

    anz_1(int n2) {
        super(n2);
    }

    anz_1(int n2, Object object) {
        super(n2, object);
    }

    public acd_1 aCu() {
        return this.cJi;
    }

    public void a(acd_1 acd_12) {
        this.cJi = acd_12;
    }

    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        if (!(object instanceof anz_1)) {
            return false;
        }
        anz_1 anz_12 = (anz_1)object;
        return this.cJi != null ? this.cJi.equals(anz_12.cJi) : anz_12.cJi == null;
    }
}

