/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aeP
 */
class aep_1
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public aep_1(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "createGuild";
    }

    public LX[] Q() {
        return new LX[]{new LX("guildName", aos_1.elS, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        atM atM2 = new atM(kG.Fi.lV(), string);
        apN.aDK().vJ().b(atM2);
    }
}

