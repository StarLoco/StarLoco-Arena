/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from dR
 */
public class dr_0 {
    private final String nq;
    private final JX nr;
    private jJ[] ns;
    static final /* synthetic */ boolean bb;
    final /* synthetic */ rt_0 nt;

    public dr_0(rt_0 rt_02, String string, JX jX, jJ[] jJArray) {
        this.nt = rt_02;
        if (!bb && jX == null) {
            throw new AssertionError((Object)"BubbleClosedListener avec un script null");
        }
        if (!bb && string == null) {
            throw new AssertionError((Object)"BubbleClosedListener avec une fonction nulle");
        }
        this.nq = string;
        this.ns = jJArray;
        this.nr = jX;
    }

    public void gF() {
        this.nr.a(this.nq, this.ns, new amd_0[0]);
    }

    static {
        bb = !rt_0.class.desiredAssertionStatus();
    }
}

