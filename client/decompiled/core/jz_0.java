/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from JZ
 */
public abstract class jz_0
implements ff_0 {
    private final asn zn;

    public jz_0(asn asn2) {
        this.zn = asn2;
    }

    public abstract amf ib();

    public asn ic() {
        return this.zn;
    }

    public abstract boolean isStatic();

    public abstract asn tF();

    public abstract String getName();

    public String getDescriptor() {
        return this.tF().getDescriptor();
    }

    public abstract Object getConstantValue();

    public String toString() {
        return this.ic().toString() + "." + this.getName();
    }
}

