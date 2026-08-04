/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
 * Renamed from aoE
 */
class aoe_1
extends asn {
    private asn[] cLc;
    private final DM cLd;
    private final el_1 cLe;
    private final zh_2 rW;

    aoe_1(zh_2 zh_22, DM dM, el_1 el_12) {
        this.rW = zh_22;
        this.cLd = dM;
        this.cLe = el_12;
        this.cLc = null;
    }

    protected ff_2[] lL() {
        ff_2[] ff_2Array = new ff_2[this.cLd.hS().size()];
        int n2 = 0;
        Iterator iterator = this.cLd.hS().iterator();
        while (iterator.hasNext()) {
            ff_2Array[n2++] = this.rW.b((kc_0)iterator.next());
        }
        return ff_2Array;
    }

    protected asn[] lN() {
        if (this.cLc == null) {
            Collection collection = this.cLe.hR();
            asn[] asnArray = new asn[collection.size()];
            int n2 = 0;
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                asnArray[n2++] = zh_2.a(this.rW, (DM)iterator.next());
            }
            this.cLc = asnArray;
        }
        return this.cLc;
    }

    protected asn lO() {
        aim_2 aim_22 = this.cLd;
        while (!(aim_22 instanceof aR)) {
            if (aim_22 instanceof kh_1) {
                return null;
            }
            aim_22 = aim_22.Dw();
        }
        return zh_2.a(this.rW, (DM)aim_22.Dw());
    }

    protected asn lP() {
        DM dM = (DM)zh_2.e(this.cLd);
        if (dM == null) {
            return null;
        }
        return zh_2.a(this.rW, dM);
    }

    protected final String lS() {
        return sA.cb(this.cLd.getClassName());
    }

    public boolean isArray() {
        return false;
    }

    protected asn lU() {
        throw new aHY("SNO: Non-array type has no component type");
    }

    public boolean isPrimitive() {
        return false;
    }

    public boolean lT() {
        return false;
    }

    protected adi_0[] lK() {
        if (this.cLd instanceof azV) {
            acc_0[] acc_0Array = ((azV)this.cLd).aMx();
            adi_0[] adi_0Array = new adi_0[acc_0Array.length];
            for (int j = 0; j < acc_0Array.length; ++j) {
                adi_0Array[j] = this.rW.d(acc_0Array[j]);
            }
            return adi_0Array;
        }
        return new adi_0[0];
    }

    protected jz_0[] lM() {
        if (this.cLd instanceof azV) {
            azV azV2 = (azV)this.cLd;
            ArrayList<jz_0> arrayList = new ArrayList<jz_0>();
            for (int j = 0; j < azV2.doQ.size(); ++j) {
                TK tK = (TK)azV2.doQ.get(j);
                if (!(tK instanceof aBi)) continue;
                aBi aBi2 = (aBi)tK;
                jz_0[] jz_0Array = this.rW.d(aBi2);
                for (int i2 = 0; i2 < jz_0Array.length; ++i2) {
                    arrayList.add(jz_0Array[i2]);
                }
            }
            return arrayList.toArray(new jz_0[arrayList.size()]);
        }
        if (this.cLd instanceof cg_2) {
            cg_2 cg_22 = (cg_2)this.cLd;
            ArrayList<jz_0> arrayList = new ArrayList<jz_0>();
            for (int j = 0; j < cg_22.aKI.size(); ++j) {
                TK tK = (TK)cg_22.aKI.get(j);
                if (!(tK instanceof aBi)) continue;
                aBi aBi3 = (aBi)tK;
                jz_0[] jz_0Array = this.rW.d(aBi3);
                for (int i3 = 0; i3 < jz_0Array.length; ++i3) {
                    arrayList.add(jz_0Array[i3]);
                }
            }
            return arrayList.toArray(new jz_0[arrayList.size()]);
        }
        throw new aHY("SNO: AbstractTypeDeclaration is neither ClassDeclaration nor InterfaceDeclaration");
    }

    public jz_0[] aCO() {
        if (this.cLd instanceof azV) {
            Collection collection = ((azV)this.cLd).doR.values();
            return collection.toArray(new jz_0[collection.size()]);
        }
        return new jz_0[0];
    }

    protected asn lQ() {
        if (this.cLd instanceof uy_1) {
            asn asn2 = zh_2.b(this.rW, ((uy_1)this.cLd).aqD);
            return asn2.isInterface() ? zh_2.a((zh_2)this.rW).eoQ : asn2;
        }
        if (this.cLd instanceof gk_0) {
            gk_0 gk_02 = (gk_0)this.cLd;
            if (gk_02.sG == null) {
                return zh_2.a((zh_2)this.rW).eoQ;
            }
            asn asn3 = zh_2.b(this.rW, gk_02.sG);
            if (asn3.isInterface()) {
                zh_2.a(this.rW, "\"" + asn3.toString() + "\" is an interface; classes can only extend a class", this.cLe.aP());
            }
            return asn3;
        }
        return null;
    }

    public amf ib() {
        return zh_2.am(this.cLd.hQ());
    }

    public boolean isFinal() {
        return (this.cLd.hQ() & 0x10) != 0;
    }

    protected asn[] lR() {
        if (this.cLd instanceof uy_1) {
            asn[] asnArray;
            asn asn2 = zh_2.b(this.rW, ((uy_1)this.cLd).aqD);
            if (asn2.isInterface()) {
                asn[] asnArray2 = new asn[1];
                asnArray = asnArray2;
                asnArray2[0] = asn2;
            } else {
                asnArray = new asn[]{};
            }
            return asnArray;
        }
        if (this.cLd instanceof gk_0) {
            gk_0 gk_02 = (gk_0)this.cLd;
            asn[] asnArray = new asn[gk_02.sH.length];
            for (int j = 0; j < asnArray.length; ++j) {
                asnArray[j] = zh_2.b(this.rW, gk_02.sH[j]);
                if (asnArray[j].isInterface()) continue;
                zh_2.a(this.rW, "\"" + asnArray[j].toString() + "\" is not an interface; classes can only implement interfaces", this.cLe.aP());
            }
            return asnArray;
        }
        if (this.cLd instanceof cg_2) {
            cg_2 cg_22 = (cg_2)this.cLd;
            asn[] asnArray = new asn[cg_22.aKH.length];
            for (int j = 0; j < asnArray.length; ++j) {
                asnArray[j] = zh_2.b(this.rW, cg_22.aKH[j]);
                if (asnArray[j].isInterface()) continue;
                zh_2.a(this.rW, "\"" + asnArray[j].toString() + "\" is not an interface; interfaces can only extend interfaces", this.cLe.aP());
            }
            return asnArray;
        }
        throw new aHY("SNO: AbstractTypeDeclaration is neither ClassDeclaration nor InterfaceDeclaration");
    }

    public boolean isAbstract() {
        return this.cLd instanceof cg_2 || (this.cLd.hQ() & 0x400) != 0;
    }

    public boolean isInterface() {
        return this.cLd instanceof cg_2;
    }
}

