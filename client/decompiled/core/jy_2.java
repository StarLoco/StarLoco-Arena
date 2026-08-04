/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from jY
 */
public abstract class jy_2
extends alb_0
implements fd_2 {
    private TK Dh = null;
    static final Object Di = new Object();
    Object Dj = Di;
    public static final Object Dk = new Throwable();
    public static final boolean Dl = true;
    public static final boolean Dm = false;

    protected jy_2(lc_0 lc_02) {
        super(lc_02);
    }

    public final void a(TK tK) {
        this.a((EO)new xe_0(this, tK).WZ());
    }

    public TK oi() {
        return this.Dh;
    }

    public jy_2 oj() {
        return this;
    }

    public abstract void a(EO var1);

    static TK a(jy_2 jy_22) {
        return jy_22.Dh;
    }

    static TK a(jy_2 jy_22, TK tK) {
        jy_22.Dh = tK;
        return jy_22.Dh;
    }
}

