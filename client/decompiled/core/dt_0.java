/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from dt
 */
public final class dt_0 {
    private static final Logger a = Logger.getLogger(dt_0.class);
    public static final dt_0 lH = new dt_0();
    private final lb_0 lI = new lb_0();
    private final aIc lJ = new aIc();

    private dt_0() {
    }

    public void a(adf_0 adf_02) {
        if (adf_02 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/ambiance/AmbianceManager.init must not be null");
        }
        this.lJ.a(adf_02);
    }

    public void update(int n2) {
        this.lJ.update(n2);
    }

    public boolean a(int n2, zb_2 zb_22) {
        if (zb_22 == null) {
            return false;
        }
        this.lI.c(n2, zb_22);
        this.lJ.a(zb_22);
        return true;
    }

    public boolean Z(int n2) {
        zb_2 zb_22 = (zb_2)this.lI.get(n2);
        if (zb_22 == null) {
            return false;
        }
        this.lJ.b(zb_22);
        return true;
    }

    public void clear() {
        int[] nArray = this.lI.pL();
        this.lI.clear();
        this.lJ.clear();
    }

    public void reset() {
        this.clear();
        this.lJ.reset();
    }
}

