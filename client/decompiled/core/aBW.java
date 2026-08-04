/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class aBW
extends uc_1 {
    private static final Logger a = Logger.getLogger(aBW.class);

    public aBW(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileRadius";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("radius", aos_1.elU, false)};
    }

    public final LX[] R() {
        return new LX[]{new LX("oldRadius", aos_1.elU, false)};
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        double d = this.hX(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            this.as(mT2.jY());
            mT2.aP((float)d);
            mT mT3 = mT2.rB();
            if (mT3 != null) {
                mT3.aP((float)d);
            }
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
            this.agB();
        }
    }
}

