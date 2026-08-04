/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from FF
 */
public abstract class ff_2
extends ms_2 {
    private final asn zn;

    public ff_2(asn asn2) {
        super(asn2);
        this.zn = asn2;
    }

    public abstract boolean isStatic();

    public abstract boolean isAbstract();

    public abstract asn ix();

    public abstract String getName();

    public String getDescriptor() {
        return new cc_2(asn.a(this.iy()), this.ix().getDescriptor()).toString();
    }

    public String toString() {
        int n2;
        asn[] asnArray;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.ib().toString()).append(' ');
        if (this.isStatic()) {
            stringBuffer.append("static ");
        }
        if (this.isAbstract()) {
            stringBuffer.append("abstract ");
        }
        try {
            stringBuffer.append(this.ix().toString());
        }
        catch (ajy_2 ajy_22) {
            stringBuffer.append("<invalid type>");
        }
        stringBuffer.append(' ');
        stringBuffer.append(this.ic().toString());
        stringBuffer.append('.');
        stringBuffer.append(this.getName());
        stringBuffer.append('(');
        try {
            asnArray = this.iy();
            for (n2 = 0; n2 < asnArray.length; ++n2) {
                if (n2 > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(asnArray[n2].toString());
            }
        }
        catch (ajy_2 ajy_23) {
            stringBuffer.append("<invalid type>");
        }
        stringBuffer.append(')');
        try {
            asnArray = this.iz();
            if (asnArray.length > 0) {
                stringBuffer.append(" throws ").append(asnArray[0]);
                for (n2 = 1; n2 < asnArray.length; ++n2) {
                    stringBuffer.append(", ").append(asnArray[n2]);
                }
            }
        }
        catch (ajy_2 ajy_24) {
            stringBuffer.append("<invalid thrown exception type>");
        }
        return stringBuffer.toString();
    }
}

