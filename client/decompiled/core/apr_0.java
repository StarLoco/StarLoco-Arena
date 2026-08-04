/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aPr
 */
class apr_0
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public apr_0(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "addGuildMember";
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
        uq_2 uq_22 = new uq_2();
        uq_22.as(l2);
        uq_22.l(kG.Fi.lV());
        uq_22.aN(l3);
        uq_22.u(false);
        apN.aDK().vJ().b(uq_22);
    }
}

