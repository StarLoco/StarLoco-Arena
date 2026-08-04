/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Iu
 */
public class iu_0
implements alx_0,
aho_0 {
    public static final Byte bhc = 8;
    private static final iu_0 bhd = new iu_0();
    private static zm_1 bhe;
    private static final cp_2 bhf;
    private long Ht;
    private long agL = 0L;
    public static final String bhg = "firework1";
    public static final String bhh = "firework2";
    public static final String bhi = "firework3";
    public static final String bhj = "firework4";
    public static final String bhk = "firework5";
    public static final String bhl = "firework6";
    public static final String bhm = "firework7";
    public static final String bhn = "firework8";
    public static final String[] ce;

    public static iu_0 Ut() {
        return bhd;
    }

    public iu_0() {
        bhe = new zm_1(bhc.byteValue());
        for (short s = 0; s < bhc; s = (short)(s + 1)) {
            bhe.b(s, new akl_2(null, 0L, 0, 0));
        }
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(bhg)) {
            return bhe.an((short)0);
        }
        if (string.equals(bhh)) {
            return bhe.an((short)1);
        }
        if (string.equals(bhi)) {
            return bhe.an((short)2);
        }
        if (string.equals(bhj)) {
            return bhe.an((short)3);
        }
        if (string.equals(bhk)) {
            return bhe.an((short)4);
        }
        if (string.equals(bhl)) {
            return bhe.an((short)5);
        }
        if (string.equals(bhm)) {
            return bhe.an((short)6);
        }
        if (string.equals(bhn)) {
            return bhe.an((short)7);
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    public zm_1 Uu() {
        return bhe;
    }

    public void clean() {
        for (short s = 0; s < bhc; s = (short)(s + 1)) {
            ((akl_2)bhe.an(s)).i(null);
            ((akl_2)bhe.an(s)).setDelay(0L);
            ((akl_2)bhe.an(s)).li(0);
            ((akl_2)bhe.an(s)).lj(0);
        }
    }

    public void start() {
        this.stop();
        for (short s = 0; s < bhc; s = (short)(s + 1)) {
            akl_2 akl_22 = (akl_2)bhe.an(s);
            if (akl_22 == null || akl_22.apc() == null) continue;
            long l2 = aam_1.aMF().a(bhd, akl_22.azW() * 1000L, 0, 1);
            bhf.a(l2, akl_22);
        }
    }

    public void stop() {
        if (this.agL > 0L) {
            aam_1.aMF().en(this.agL);
        }
        this.agL = 0L;
    }

    public boolean a(pr_0 pr_02) {
        if (pr_02.getId() == Integer.MIN_VALUE) {
            axe_0 axe_02 = (axe_0)pr_02;
            akl_2 akl_22 = (akl_2)bhf.t(axe_02.aKD());
            axf_0 axf_02 = new axf_0();
            axf_02.i(akl_22.apc().jf());
            axf_02.li(akl_22.qp());
            axf_02.lj(akl_22.qq());
            axf_02.bO(this.Ht);
            apN.aDK().vJ().b(axf_02);
            bhf.u(axe_02.aKD());
        }
        return false;
    }

    public long getId() {
        return ys_0.aEd;
    }

    public void c(long l2) {
    }

    public void bO(long l2) {
        this.Ht = l2;
    }

    static {
        bhf = new cp_2(bhc.byteValue());
        ce = new String[]{bhg, bhh, bhi, bhj, bhk, bhl, bhm, bhn};
    }
}

