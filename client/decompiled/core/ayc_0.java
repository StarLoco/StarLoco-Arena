/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ayC
 */
public class ayc_0 {
    private static final ayc_0 dmy = new ayc_0();
    private final lb_0 dmz = new lb_0();

    public static ayc_0 aLE() {
        return dmy;
    }

    protected ayc_0() {
    }

    public void a(aqy_0 aqy_02) {
        this.dmz.c(aqy_02.getId(), aqy_02);
    }

    public aqy_0 mS(int n2) {
        return (aqy_0)this.dmz.get(n2);
    }

    public aqy_0 mT(int n2) {
        return (aqy_0)this.dmz.remove(n2);
    }

    public void removeAll() {
        this.dmz.clear();
    }

    public lb_0 aLF() {
        return this.dmz;
    }
}

