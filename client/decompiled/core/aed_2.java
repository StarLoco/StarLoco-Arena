/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aEd
 */
public abstract class aed_2
extends pr_0 {
    private int aW;
    private boolean dzC;
    private byte ctc;
    private short ctd;
    private int cte;
    private long ctf;
    private double ctg;
    private float cth;
    private String cti;

    public aed_2(alx_0 alx_02) {
        this.a(alx_02);
    }

    public boolean a(byte[] byArray) {
        return true;
    }

    public byte[] encode() {
        return null;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int getId() {
        return this.aW;
    }

    public void j() {
    }

    public void b() {
    }

    public byte aj() {
        return this.ctc;
    }

    public void a(byte by) {
        this.ctc = by;
    }

    public int getIntValue() {
        return this.cte;
    }

    public void g(int n2) {
        this.cte = n2;
    }

    public long getLongValue() {
        return this.ctf;
    }

    public void e(long l2) {
        this.ctf = l2;
    }

    public short ak() {
        return this.ctd;
    }

    public void bF(short s) {
        this.ctd = s;
    }

    public double getDoubleValue() {
        return this.ctg;
    }

    public void a(double d) {
        this.ctg = d;
    }

    public float getFloatValue() {
        return this.cth;
    }

    public void c(float f) {
        this.cth = f;
    }

    public String getStringValue() {
        return this.cti;
    }

    public void b(String string) {
        this.cti = string;
    }

    public boolean getBooleanValue() {
        return this.dzC;
    }

    public void b(boolean bl2) {
        this.dzC = bl2;
    }
}

