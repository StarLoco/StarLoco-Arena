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
 * Renamed from abf
 */
public class abf_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(ej_2.class);

    public abf_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "incrementMobileDeltaZ";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("deltaZ", aos_1.elT, false)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        int n3 = this.hW(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            mT2.bR(mT2.aTo() + (float)n3 * 0.0625f);
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

