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
 * Renamed from NQ
 */
public class nq_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(nq_1.class);

    public nq_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "isMobile";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return new LX[]{new LX("isMobile", aos_1.elV, false)};
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        this.cp(mT2 != null);
    }
}

