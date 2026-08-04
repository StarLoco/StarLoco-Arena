/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class XP
extends uc_1 {
    private static final Logger a = Logger.getLogger(XP.class);
    private static final boolean DEBUG = true;

    public XP(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileAnimationStaticKey";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("animName", aos_1.elS, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            String string = n2 == 2 ? this.hZ(1) : "AnimStatique";
            mT2.dW(string);
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

