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
 * Renamed from aIv
 */
public class aiv_0
extends uc_1 {
    private static final Logger a = Logger.getLogger(aiv_0.class);

    public aiv_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileMovementStyle";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("walkStyle", aos_1.elS, false), new LX("runStyle", aos_1.elS, true), new LX("uniqueUsage", aos_1.elV, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        String string = this.hZ(1);
        String string2 = n2 > 2 ? this.hZ(2) : string;
        boolean bl2 = n2 > 3 ? this.ic(3) : true;
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null && mT2 instanceof abm_2) {
            abm_2 abm_22 = (abm_2)mT2;
            abm_22.a(bl2, string, string2);
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

