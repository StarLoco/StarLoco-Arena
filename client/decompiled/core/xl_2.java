/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from XL
 */
public class xl_2 {
    private short bZr = (short)-1;
    private String name;
    private asn rC;
    private va_2 bZs;
    private va_2 bZt;

    public xl_2(String string, short s, asn asn2) {
        this.name = string;
        this.bZr = s;
        this.rC = asn2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("local var(");
        stringBuffer.append(this.name);
        stringBuffer.append(", ").append(this.bZr);
        if (this.name != null) {
            stringBuffer.append(", ").append(this.rC);
            stringBuffer.append(", ").append(this.bZs.offset);
            stringBuffer.append(", ").append(this.bZt.offset);
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    public short jl() {
        return this.bZr;
    }

    public void bs(short s) {
        this.bZr = s;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public va_2 als() {
        return this.bZs;
    }

    public void a(va_2 va_22) {
        this.bZs = va_22;
    }

    public va_2 alt() {
        return this.bZt;
    }

    public void b(va_2 va_22) {
        this.bZt = va_22;
    }

    public asn tF() {
        return this.rC;
    }
}

