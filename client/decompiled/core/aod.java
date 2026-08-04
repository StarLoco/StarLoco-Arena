/*
 * Decompiled with CFR 0.152.
 */
class aod
extends jz_0 {
    private final aBi cKC;
    private final jk_2 cKD;
    private final zh_2 rW;

    aod(zh_2 zh_22, asn asn2, aBi aBi2, jk_2 jk_22) {
        asn asn3 = asn2;
        asn3.getClass();
        super(asn3);
        this.rW = zh_22;
        this.cKC = aBi2;
        this.cKD = jk_22;
    }

    public amf ib() {
        switch (this.cKC.HC & 7) {
            case 2: {
                return amf.cGq;
            }
            case 4: {
                return amf.cGr;
            }
            case 0: {
                return amf.cGs;
            }
            case 1: {
                return amf.cGt;
            }
        }
        throw new aHY("Invalid access");
    }

    public boolean isStatic() {
        return (this.cKC.HC & 8) != 0;
    }

    public asn tF() {
        return zh_2.b(this.rW, this.cKC.HD).a(this.cKD.BN, zh_2.a((zh_2)this.rW).eoQ);
    }

    public String getName() {
        return this.cKD.name;
    }

    public Object getConstantValue() {
        Object object;
        if ((this.cKC.HC & 0x10) != 0 && this.cKD.BO instanceof jy_2 && (object = this.rW.j((jy_2)this.cKD.BO)) != null) {
            return zh_2.a(this.rW, (lz_1)((Object)this.cKD.BO), object, this.tF());
        }
        return null;
    }
}

