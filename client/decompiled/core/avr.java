/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class avr
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public avr(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "deleteGuildRank";
    }

    public LX[] Q() {
        return new LX[]{new LX("guildId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = 0x200000000000024L;
        short s = 5;
        Ko ko = new Ko();
        ko.g(l2);
        ko.aH(s);
        apN.aDK().vJ().b(ko);
    }
}

