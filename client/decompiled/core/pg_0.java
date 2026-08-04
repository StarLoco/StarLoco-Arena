/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from pg
 */
public class pg_0
extends uc_1 {
    public pg_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "isMobileVisible";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("visible", aos_1.elV, false)};
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        this.cp(mT2 != null && mT2.isVisible());
    }
}

