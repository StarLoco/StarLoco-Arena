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
 * Renamed from Xl
 */
public class xl_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(xl_1.class);

    public xl_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileToHitAnimation";
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
            mT2.aY(mT2.aTC());
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

