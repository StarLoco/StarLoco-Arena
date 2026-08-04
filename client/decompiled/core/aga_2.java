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
 * Renamed from aga
 */
public class aga_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(aga_2.class);

    public aga_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setMobileDirection";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("directionIndex", aos_1.elT, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        int n3 = this.hW(1);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            mT2.b(qc_0.hf(n3));
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

