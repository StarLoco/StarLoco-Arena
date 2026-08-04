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
 * Renamed from SR
 */
public class sr_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(sr_2.class);

    public sr_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileAnimationSuffix";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("animationSuffix", aos_1.elS, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 == null) {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
            return;
        }
        mT2.ls(n2 == 2 ? this.hZ(1) : null);
    }
}

