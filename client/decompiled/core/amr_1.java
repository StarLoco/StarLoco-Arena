/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aMr
 */
class amr_1
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public amr_1(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "getGuild";
    }

    public LX[] Q() {
        return new LX[]{new LX("playerId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        auZ auZ2 = new auZ(l2);
        apN.aDK().vJ().b(auZ2);
    }
}

