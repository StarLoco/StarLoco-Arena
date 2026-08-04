/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class Ad
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public Ad(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "guildChangeIcon";
    }

    public LX[] Q() {
        return new LX[]{new LX("guildId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        mx_1 mx_12 = new mx_1();
        mx_12.g(0x200000000000044L);
        mx_12.aK((short)12);
        mx_12.gS(13);
        mx_12.gT(14);
        apN.aDK().vJ().b(mx_12);
    }
}

