/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Ec
 */
public class ec_1
extends uc_1 {
    public ec_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "removeParticleSystem";
    }

    public LX[] Q() {
        return new LX[]{new LX("particleUniqueId", aos_1.elT, false), new LX("stopEmittersToKill", aos_1.elV, true)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        int n3 = this.hW(0);
        boolean bl2 = n2 >= 2 && this.ic(1);
        qd_1.uW().b(n3, bl2);
    }
}

