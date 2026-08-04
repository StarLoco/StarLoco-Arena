/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ans
 */
class ans_0
implements alx_0 {
    private static final ans_0 cIN = new ans_0();
    private static final int cIO = 0;
    private static final long cIP = 6 * fl_2.rQ;
    private static final int cIQ = 0;
    private static final int cIR = -1;
    private static final long cIS = 0L;
    private static final aLY cIT = null;
    private static final aam_1 cIU = aam_1.aMF();
    private static long cIV = 0L;
    private static final Object no = new Object();
    private static long cIW = 0L;
    private static aLY cIX;

    private ans_0() {
    }

    public long getId() {
        return ys_0.aDX;
    }

    public void c(long l2) {
        wp_2.Dm().error((Object)("Impossible de changer l'id d'une instance : Id = " + this.getId() + " fix\u00e9 dans le constructeur."));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a(aLY aLY2) {
        Object object = no;
        synchronized (object) {
            if (cIW == 0L) {
                if (cIV != 0L) {
                    cIU.en(cIV);
                }
                apN.aDK().vJ().b(aLY2);
                ++cIW;
                cIX = cIT;
                cIV = cIU.a(cIN, cIP, 0, -1);
            } else {
                ++cIW;
                if (cIX != cIT && !cIX.uA()) {
                    cIX.release();
                }
                cIX = aLY2;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean a(pr_0 pr_02) {
        axe_0 axe_02;
        long l2;
        boolean bl2 = true;
        if (pr_02.getId() == Integer.MIN_VALUE && (l2 = (axe_02 = (axe_0)pr_02).aKD()) == cIV) {
            Object object = no;
            synchronized (object) {
                if (cIX == cIT) {
                    cIU.en(cIV);
                    cIW = 0L;
                } else {
                    apN.aDK().vJ().b(cIX);
                    cIX = cIT;
                }
            }
            bl2 = false;
        }
        return bl2;
    }
}

