/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from GQ
 */
public class gq_2
extends ahh_1 {
    protected qc_0 ak = qc_0.bEK;
    protected boolean GF = false;

    public gq_2() {
        this.eX(this.isVisible());
    }

    public gq_2(long l2) {
        super(l2);
        this.eX(this.isVisible());
    }

    public gq_2(long l2, double d, double d2) {
        super(l2, d, d2);
        this.eX(this.isVisible());
    }

    public gq_2(long l2, double d, double d2, double d3) {
        super(l2, d, d2, d3);
        this.eX(this.isVisible());
    }

    public gq_2(long l2, double d, double d2, double d3, boolean bl2) {
        super(l2, d, d2, d3, bl2);
        this.eX(this.isVisible());
    }

    protected final double Sc() {
        return this.ak == qc_0.bEK ? 1.0 : 0.0;
    }

    protected final double Sd() {
        return this.ak == qc_0.bEM ? 1.0 : 0.0;
    }

    public void b(qc_0 qc_02) {
        assert (qc_02 != null);
        if (this.ak != qc_02) {
            this.ak = qc_02;
            this.GF = true;
        }
    }

    public qc_0 L() {
        return this.ak;
    }

    public void b(gq_2 gq_22) {
        super.b(gq_22);
        this.ak = gq_22.ak;
        this.GF = true;
    }

    protected String en(String string) {
        return gq_2.a(this.ak.getIndex(), string, this.dLl);
    }

    protected boolean Se() {
        return super.Se() || this.GF;
    }

    protected void Sf() {
        super.Sf();
        this.GF = false;
    }

    public String Sg() {
        return gq_2.a(this.ak.getIndex(), this.AU(), this.dLl);
    }

    public int an(String string) {
        if (string == null) {
            return 0;
        }
        String string2 = gq_2.a(this.L().getIndex(), string, this.dLl);
        return super.an(string2);
    }

    public boolean ap(String string) {
        return this.a(this.ak, string);
    }

    public boolean a(qc_0 qc_02, String string) {
        if (this.aTF() == null) {
            return false;
        }
        return this.aTF().ap(gq_2.a(qc_02.getIndex(), string, this.dLl));
    }

    public static String a(int n2, String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder(string.length() + 20);
        stringBuilder.append(Integer.toString(n2)).append("_").append(string);
        if (string2 != null) {
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }
}

