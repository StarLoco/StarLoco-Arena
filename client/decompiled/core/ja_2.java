/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ja
 */
class ja_2
extends asn {
    private final asn zl;
    private final asn zm;
    private final asn zn;

    ja_2(asn asn2, asn asn3, asn asn4) {
        this.zn = asn2;
        this.zl = asn3;
        this.zm = asn4;
    }

    public adi_0[] lK() {
        return new adi_0[0];
    }

    public ff_2[] lL() {
        return new ff_2[]{new fl(this)};
    }

    public jz_0[] lM() {
        return new jz_0[0];
    }

    public asn[] lN() {
        return new asn[0];
    }

    public asn lO() {
        return null;
    }

    public asn lP() {
        return null;
    }

    public asn lQ() {
        return this.zl;
    }

    public asn[] lR() {
        return new asn[0];
    }

    public String lS() {
        return '[' + this.zm.getDescriptor();
    }

    public amf ib() {
        return this.zm.ib();
    }

    public boolean isFinal() {
        return true;
    }

    public boolean isInterface() {
        return false;
    }

    public boolean isAbstract() {
        return false;
    }

    public boolean isArray() {
        return true;
    }

    public boolean isPrimitive() {
        return false;
    }

    public boolean lT() {
        return false;
    }

    public asn lU() {
        return this.zm;
    }

    public String toString() {
        return this.zm.toString() + "[]";
    }

    static asn a(ja_2 ja_22) {
        return ja_22.zl;
    }
}

