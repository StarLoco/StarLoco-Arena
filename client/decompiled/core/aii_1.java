/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aiI
 */
class aii_1
extends uc_1 {
    final /* synthetic */ uc_2 aBX;

    public aii_1(uc_2 uc_22, LuaState luaState) {
        this.aBX = uc_22;
        super(luaState);
    }

    public String getName() {
        return "updateAchievement";
    }

    public LX[] Q() {
        return new LX[]{new LX("achievementConditionId", aos_1.elT, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        nq nq2 = new nq();
        nq2.K((short)this.hW(0));
        nq2.ab(true);
        nq2.L((short)1);
        apN.aDK().vJ().b(nq2);
    }
}

