/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aMe
 */
public class ame_1 {
    protected static final Logger a = Logger.getLogger(avm_0.class);
    private static ame_1 dXq = new ame_1();
    private final cp_2 dXr = new cp_2();
    private final cp_2 dXs = new cp_2();

    public static ame_1 aWP() {
        return dXq;
    }

    public void a(yl_1 yl_12) {
        this.dXr.a(yl_12.aqM(), yl_12);
    }

    public yl_1 eN(long l2) {
        return (yl_1)this.dXr.t(l2);
    }

    public void b(yl_1 yl_12) {
        this.dXs.a(yl_12.aqM(), yl_12);
    }

    public yl_1 eO(long l2) {
        return (yl_1)this.dXs.t(l2);
    }
}

