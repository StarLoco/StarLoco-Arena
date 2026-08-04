/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from adI
 */
public abstract class adi_0
extends ms_2 {
    private final asn zn;

    public adi_0(asn asn2) {
        super(asn2);
        this.zn = asn2;
    }

    public abstract asn[] iy();

    public String getDescriptor() {
        asn[] asnArray = this.iy();
        asn asn2 = this.zn.aFp();
        if (asn2 != null) {
            asn[] asnArray2 = new asn[asnArray.length + 1];
            asnArray2[0] = asn2;
            System.arraycopy(asnArray, 0, asnArray2, 1, asnArray.length);
            asnArray = asnArray2;
        }
        return new cc_2(asn.a(asnArray), "V").toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.ic().toString());
        stringBuffer.append('(');
        try {
            asn[] asnArray = this.iy();
            for (int j = 0; j < asnArray.length; ++j) {
                if (j > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(asnArray[j].toString());
            }
        }
        catch (ajy_2 ajy_22) {
            stringBuffer.append("<invalid type>");
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }
}

