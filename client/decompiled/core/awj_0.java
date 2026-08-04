/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from awJ
 */
class awj_0
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public awj_0(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "XvsXInvitation";
    }

    public LX[] Q() {
        return new LX[]{new LX("invitedId", aos_1.elR, false), new LX("allyId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        long l2 = this.hY(0);
        long l3 = this.hY(1);
        aju_1 aju_12 = new aju_1();
        aju_12.bq((byte)14);
        aju_12.U(l2);
        aju_12.eD(l3);
        apN.aDK().vJ().b(aju_12);
    }
}

