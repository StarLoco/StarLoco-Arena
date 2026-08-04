/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aoD
 */
class aod_0
extends adi_0 {
    private final acc_0 cLb;
    private final zh_2 rW;

    aod_0(zh_2 zh_22, asn asn2, acc_0 acc_02) {
        asn asn3 = asn2;
        asn3.getClass();
        super(asn3);
        this.rW = zh_22;
        this.cLb = acc_02;
    }

    public amf ib() {
        switch (this.cLb.HC & 7) {
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

    public String getDescriptor() {
        if (!(this.cLb.aro() instanceof eb_0)) {
            return super.getDescriptor();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        asn asn2 = zh_2.a(this.rW, this.cLb.aro()).aFp();
        if (asn2 != null) {
            arrayList.add(asn2.getDescriptor());
        }
        anb_1[] anb_1Array = this.cLb.aro().doR.values().iterator();
        while (anb_1Array.hasNext()) {
            jz_0 jz_02 = (jz_0)anb_1Array.next();
            if (!jz_02.getName().startsWith("val$")) continue;
            arrayList.add(jz_02.tF().getDescriptor());
        }
        anb_1Array = this.cLb.azz;
        for (int j = 0; j < anb_1Array.length; ++j) {
            arrayList.add(zh_2.b(this.rW, anb_1Array[j].HD).getDescriptor());
        }
        String[] stringArray = arrayList.toArray(new String[arrayList.size()]);
        return new cc_2(stringArray, "V").toString();
    }

    public asn[] iy() {
        anb_1[] anb_1Array = this.cLb.azz;
        asn[] asnArray = new asn[anb_1Array.length];
        for (int j = 0; j < anb_1Array.length; ++j) {
            asnArray[j] = zh_2.b(this.rW, anb_1Array[j].HD);
        }
        return asnArray;
    }

    public asn[] iz() {
        asn[] asnArray = new asn[this.cLb.azA.length];
        for (int j = 0; j < asnArray.length; ++j) {
            asnArray[j] = zh_2.b(this.rW, this.cLb.azA[j]);
        }
        return asnArray;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.cLb.bV().getClassName());
        stringBuffer.append('(');
        anb_1[] anb_1Array = this.cLb.azz;
        for (int j = 0; j < anb_1Array.length; ++j) {
            if (j != 0) {
                stringBuffer.append(", ");
            }
            try {
                stringBuffer.append(zh_2.b(this.rW, anb_1Array[j].HD).toString());
                continue;
            }
            catch (ajy_2 ajy_22) {
                stringBuffer.append("???");
            }
        }
        return stringBuffer.append(')').toString();
    }
}

