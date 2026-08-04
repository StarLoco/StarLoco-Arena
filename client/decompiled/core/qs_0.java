/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from QS
 */
public abstract class qs_0
extends Eq
implements alx_0 {
    private static int bHT = 0;
    private static final int bHU = 30000;
    protected long agL;

    public static int adu() {
        bHT = bHT == Integer.MAX_VALUE ? 0 : ++bHT;
        return bHT;
    }

    public qs_0(int n2, int n3, int n4) {
        super(n2, n3, n4);
    }

    public final void run() {
        long l2 = this.oS();
        if (l2 > 30000L) {
            a.error((Object)("Attention ! Une action " + this.getClass().getSimpleName() + " dure plus d'une minute : " + l2 + " ms \u00e7a parait long, il y a peut etre un probleme"));
            l2 = 0L;
        }
        if (l2 == 0L) {
            this.Nn();
        } else if (l2 > 0L) {
            this.agL = aam_1.aMF().a(this, l2, -1, 1);
        }
    }

    protected abstract long oS();

    public boolean a(pr_0 pr_02) {
        if (pr_02.getId() == Integer.MIN_VALUE) {
            this.Nn();
            return false;
        }
        return true;
    }

    public long getId() {
        return -1L;
    }

    public void c(long l2) {
    }
}

