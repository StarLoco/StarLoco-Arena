/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aHZ
 */
public class ahz_0 {
    final String m_name;

    public ahz_0(String string, float[] fArray, ef_1 ef_12, adz_1 adz_12, fa_0 fa_02) {
        this.m_name = string;
        aaR aaR2 = ef_12 == null ? wn_2.Dj().a(string, fa_02) : wn_2.Dj().a(string, ef_12, adz_12, fa_02);
        aaR2.q(fArray);
    }

    public ahz_0(String string, float[] fArray) {
        this(string, fArray, null, null, fa_0.ry);
    }

    public final void y(int n2, int n3, short s) {
        this.aUB().y(n2, n3, s);
    }

    private aaR aUB() {
        return wn_2.Dj().cJ(this.m_name);
    }

    public final void clear() {
        this.aUB().clear();
    }

    public final boolean i(ry ry2) {
        return this.x(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public final boolean x(int n2, int n3, short s) {
        return this.aUB().x(n2, n3, s);
    }

    public final void b(String string, fa_0 fa_02) {
        adz_1 adz_12 = new adz_1();
        ef_1 ef_12 = ahz_0.a(string, adz_12);
        this.aUB().a(adz_12, ef_12, fa_02);
    }

    public final void q(float[] fArray) {
        this.aUB().q(fArray);
    }

    public final void z(int n2, int n3, short s) {
        this.aUB().z(n2, n3, s);
    }

    public static ef_1 a(String string, adz_1 adz_12) {
        String string2 = vq_2.gs(string);
        return cx_0.JY().a(arX.cQT.iE(), ej_0.aa(string2), string, adz_12, false);
    }

    public final void oK(int n2) {
        this.aUB().cgL = n2;
    }
}

