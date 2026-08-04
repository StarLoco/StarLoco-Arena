/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aoB
 */
class aob_1
extends ff_2 {
    private final kc_0 cLa;
    private final zh_2 rW;

    aob_1(zh_2 zh_22, asn asn2, kc_0 kc_02) {
        asn asn3 = asn2;
        asn3.getClass();
        super(asn3);
        this.rW = zh_22;
        this.cLa = kc_02;
    }

    public amf ib() {
        switch (this.cLa.HC & 7) {
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

    public asn[] iy() {
        anb_1[] anb_1Array = this.cLa.azz;
        asn[] asnArray = new asn[anb_1Array.length];
        for (int j = 0; j < anb_1Array.length; ++j) {
            asnArray[j] = zh_2.b(this.rW, anb_1Array[j].HD);
        }
        return asnArray;
    }

    public asn[] iz() {
        asn[] asnArray = new asn[this.cLa.azA.length];
        for (int j = 0; j < asnArray.length; ++j) {
            asnArray[j] = zh_2.b(this.rW, this.cLa.azA[j]);
        }
        return asnArray;
    }

    public boolean isStatic() {
        return (this.cLa.HC & 8) != 0;
    }

    public boolean isAbstract() {
        return this.cLa.bV() instanceof cg_2 || (this.cLa.HC & 0x400) != 0;
    }

    public asn ix() {
        return zh_2.a(this.rW, this.cLa);
    }

    public String getName() {
        return this.cLa.name;
    }
}

