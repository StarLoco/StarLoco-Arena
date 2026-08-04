/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class asW
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public asW(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "updateGuild";
    }

    public LX[] Q() {
        return new LX[]{new LX("guildId", aos_1.elR, false), new LX("guildName", aos_1.elS, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = 0x200000000000001L;
        String string = this.hZ(1);
        wt_1 wt_12 = new wt_1(l2, string);
        apN.aDK().vJ().b(wt_12);
    }
}

