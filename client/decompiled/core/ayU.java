/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class ayU
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public ayU(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "updateGuildMember";
    }

    public LX[] Q() {
        return new LX[]{new LX("guildId", aos_1.elR, false), new LX("memberId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = 144115188075855892L;
        long l3 = 3002421L;
        short s = 2;
        abn_2 abn_22 = new abn_2();
        abn_22.as(l2);
        abn_22.at(l3);
        abn_22.bx(s);
        apN.aDK().vJ().b(abn_22);
    }
}

