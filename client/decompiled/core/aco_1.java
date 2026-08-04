/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aCO
 */
public class aco_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(aco_1.class);

    public aco_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobilePosition";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("worldX", aos_1.elU, false), new LX("worldY", aos_1.elU, false), new LX("altitude", aos_1.elU, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        double d = this.hX(1);
        double d2 = this.hX(2);
        double d3 = this.hX(3);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            mT2.a(d, d2, d3);
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

