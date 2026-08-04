/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from UQ
 */
public class uq_1
implements aGa,
alx_0 {
    private static final long bRH = 60000L;
    private int bRI;
    private int bRJ = -1;
    private long bRK;
    private static final uq_1 bRL = new uq_1();

    public static uq_1 ahQ() {
        return bRL;
    }

    private uq_1() {
    }

    public static void init(int n2) {
        if (n2 < 0 || n2 > 255) {
            throw new IllegalArgumentException("Le num\u00e9ro de serveur doit \u00eatre compris entre 0 et 255");
        }
        uq_1.bRL.bRJ = n2;
        uq_1.bRL.bRI = 0;
        bRL.ahS();
        aam_1.aMF().a(bRL, 60000L, 0);
    }

    public static long ahR() {
        long l2 = uq_1.bRL.bRK + ((long)uq_1.bRL.bRI & 0xFFFFFFL);
        ++uq_1.bRL.bRI;
        return l2;
    }

    public boolean a(pr_0 pr_02) {
        if (pr_02.getId() == Integer.MIN_VALUE) {
            this.ahS();
            this.bRI = 0;
            return false;
        }
        return true;
    }

    private void ahS() {
        if (this.bRJ < 0 || this.bRJ > 255) {
            throw new IllegalArgumentException("Impossible d'initialiser le GUIDGenerator : Le num\u00e9ro de serveur doit \u00eatre fix\u00e9 par la m\u00e9thode init");
        }
        this.bRK = ((long)this.bRJ & 0xFFL) << 48;
        this.bRK |= (System.currentTimeMillis() / 60000L & 0xFFFFFFL) << 24;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    public long ahT() {
        return uq_1.ahR();
    }
}

