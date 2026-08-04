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
 * Renamed from tT
 */
public class tt_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(ej_2.class);

    public tt_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "unlinkMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            mT2.rG();
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

