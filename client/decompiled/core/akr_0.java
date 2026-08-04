/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from akr
 */
class akr_0
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public akr_0(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "getGuildMembers";
    }

    public LX[] Q() {
        return new LX[]{new LX("guildId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = 0x200000000000001L;
        add_2 add_22 = new add_2(l2);
        apN.aDK().vJ().b(add_22);
    }
}

