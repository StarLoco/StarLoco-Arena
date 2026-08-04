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
 * Renamed from UU
 */
public class uu_0
extends uc_1 {
    private static final Logger a = Logger.getLogger(uu_0.class);

    public uu_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileJumpCapacity";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("jumpCapacity", aos_1.elT, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        short s = (short)this.hW(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null && mT2 instanceof abm_2) {
            ((abm_2)mT2).ck(s);
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ou n'est pas un PathMobile");
        }
    }
}

