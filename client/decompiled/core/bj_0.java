/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from bJ
 */
public class bj_0
implements jp_1 {
    private static final bj_0 hi = new bj_0();
    private final aww hj = new vh_2(0.0f, 0.5f, 0.5f, 1.0f);
    private final aww hk = new nb_1(0.0f, 0.5f, 0.5f, 1.0f);

    public static bj_0 dx() {
        return hi;
    }

    protected bj_0() {
    }

    public final int a(gj_2 gj_22) {
        return gj_22.Ps().hH();
    }

    public aww dy() {
        return this.hj;
    }

    public aww dz() {
        return this.hk;
    }

    public void b(gj_2 gj_22) {
        String string = gj_22.AU();
        if (string.equals("AnimSaut")) {
            gj_22.aY("AnimStatique");
        } else {
            gj_22.aY(gj_22.Pp());
        }
    }

    public void a(gj_2 gj_22, int n2, db_0 db_02) {
        ama_2.b(gj_22, n2, db_02);
    }

    public void a(gj_2 gj_22, int n2) {
        String string = gj_22.AU();
        if (!string.equals("AnimSaut") && !string.equals("AnimCourse-Fin")) {
            gj_22.aY("AnimCourse");
        }
    }

    public void a(gj_2 gj_22, qc_0 qc_02) {
        gj_22.b(qc_02);
    }

    public boolean c(gj_2 gj_22) {
        return false;
    }

    public boolean b(gj_2 gj_22, int n2) {
        return Math.abs(n2) > 2;
    }

    public String bS() {
        return awm_0.dhE;
    }
}

