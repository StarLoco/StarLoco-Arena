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
 * Renamed from ej
 */
public class ej_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(ej_2.class);

    public ej_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileNext4Direction";
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
        if (mT2 != null && !mT2.L().acL()) {
            mT2.b(qc_0.hf((mT2.L().getIndex() + 1) % 8));
        } else if (mT2 == null) {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

