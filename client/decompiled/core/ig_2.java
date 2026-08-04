/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from iG
 */
public class ig_2
implements jp_1 {
    private static final ig_2 yH = new ig_2();
    private final aww hj = new vh_2(0.0f, 0.5f, 0.5f, 1.0f);
    private final aww hk = new nb_1(0.0f, 0.5f, 0.5f, 1.0f);

    public static ig_2 lE() {
        return yH;
    }

    protected ig_2() {
    }

    public int a(gj_2 gj_22) {
        return gj_22.Pt().hH();
    }

    public aww dy() {
        return this.hj;
    }

    public aww dz() {
        return this.hk;
    }

    public void b(gj_2 gj_22) {
        String string = gj_22.AU();
        gj_22.aY("AnimStatique");
    }

    public void a(gj_2 gj_22, int n2, db_0 db_02) {
        ama_2.b(gj_22, n2, db_02);
    }

    public void a(gj_2 gj_22, int n2) {
        String string = gj_22.AU();
        if (!(string.equals("AnimMarche") || string.equals("AnimMarche-Debut") || string.equals("AnimMarche-Boucle") || string.equals("AnimMarche-Fin"))) {
            gj_22.aY("AnimMarche");
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
        return awm_0.dhD;
    }
}

