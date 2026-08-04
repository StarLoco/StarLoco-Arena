/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from WX
 */
class wx_1
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public wx_1(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "updateGuildRank";
    }

    public LX[] Q() {
        return new LX[]{new LX("guildId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = 0x200000000000024L;
        String string = "blou";
        int n3 = 0;
        short s = 4;
        short s2 = 5;
        Nr nr = new Nr();
        nr.g(l2);
        nr.setName(string);
        nr.gV(n3);
        nr.aH(s);
        nr.aM(s2);
        apN.aDK().vJ().b(nr);
    }
}

