/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from alc
 */
class alc_2
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public alc_2(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "updateGameCalendarEvent";
    }

    public LX[] Q() {
        return new LX[0];
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        agh_2 agh_22 = new agh_2();
        rd_1 rd_12 = rd_1.aF(System.currentTimeMillis());
        rd_12.a(0, 0, 1, 0, 0, 0);
        wk_1 wk_12 = new wk_1(rd_1.aF(System.currentTimeMillis()), rd_12.a(0, 0, 1, 0, 0, 0), new jx_0(0, 0, 0, 0, 1, 0), 1);
        agh_22.f(wk_12);
        agh_22.M(5L);
        apN.aDK().vJ().b(agh_22);
    }
}

