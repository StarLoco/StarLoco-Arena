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
 * Renamed from SP
 */
public class sp_0
extends uc_1 {
    private static final Logger a = Logger.getLogger(sp_0.class);

    public sp_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "getMobileStatus";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return new LX[]{new LX("status", aos_1.elT, false)};
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            this.id(mT2.rK());
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
            this.agB();
        }
    }
}

