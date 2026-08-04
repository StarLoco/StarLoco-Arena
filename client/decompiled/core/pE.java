/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class pE
extends uc_1 {
    private static final Logger a = Logger.getLogger(pE.class);

    public pE(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileAnimationSpeed";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("animationSpeed", aos_1.elU, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        float f = (float)this.hX(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            mT2.ar(f);
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

