/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from db
 */
class db_1
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public db_1(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "createGuildRank";
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
        abo_0 abo_02 = new abo_0();
        abo_02.g(l2);
        abo_02.setName(string);
        abo_02.gV(n3);
        apN.aDK().vJ().b(abo_02);
    }
}

