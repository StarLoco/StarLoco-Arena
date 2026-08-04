/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ok
 */
class ok_2
extends jz_0 {
    private final Object UL;
    private final String UM;
    private final asn UN;
    private final axo_0 UO;
    private final amf UP;
    private final yy_2 UQ;

    ok_2(yy_2 yy_22, Object object, String string, asn asn2, axo_0 axo_02, amf amf2) {
        super(yy_22);
        this.UQ = yy_22;
        this.UL = object;
        this.UM = string;
        this.UN = asn2;
        this.UO = axo_02;
        this.UP = amf2;
    }

    public Object getConstantValue() {
        return this.UL;
    }

    public String getName() {
        return this.UM;
    }

    public asn tF() {
        return this.UN;
    }

    public boolean isStatic() {
        return (this.UO.asX() & 8) != 0;
    }

    public amf ib() {
        return this.UP;
    }
}

