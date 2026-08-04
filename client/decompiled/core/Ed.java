/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class Ed
extends uc_1 {
    private static final Logger a = Logger.getLogger(Ed.class);

    public Ed(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileToStaticAnimation";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            mT2.aY(mT2.Po());
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

