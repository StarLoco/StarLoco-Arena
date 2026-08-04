/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

class ot
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public ot(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "removeGuildMember";
    }

    public LX[] Q() {
        return new LX[]{new LX("guildId", aos_1.elR, false), new LX("memberId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = 0x200000000000001L;
        long l3 = this.hY(1);
        nP nP2 = new nP();
        nP2.as(l2);
        nP2.at(l3);
        nP2.u(false);
        apN.aDK().vJ().b(nP2);
    }
}

