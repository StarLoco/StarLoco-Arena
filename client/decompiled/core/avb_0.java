/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from avB
 */
class avb_0
extends uc_1 {
    final /* synthetic */ adg_1 hW;

    public avb_0(adg_1 adg_12, LuaState luaState) {
        this.hW = adg_12;
        super(luaState);
    }

    public String getName() {
        return "addGameCalendarEvent";
    }

    public LX[] Q() {
        return new LX[0];
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        ald_2 ald_22 = new ald_2();
        mb_2 mb_22 = new mb_2(rd_1.aF(System.currentTimeMillis()), rd_1.aF(System.currentTimeMillis()), jx_0.blQ, 1);
        rd_1 rd_12 = rd_1.aF(System.currentTimeMillis());
        jF jF2 = new jF(rd_12, new rd_1(rd_12).a(0, 10, 0, 0, 0, 0), jx_0.blR, 3);
        rd_1 rd_13 = new rd_1(rd_12).a(0, 5, 0, 0, 0, 0);
        qr_0 qr_02 = new qr_0(rd_13, new rd_1(rd_13).a(0, 10, 0, 0, 0, 0), jx_0.blR, 4, "Test - " + System.currentTimeMillis(), 1);
        ald_22.a(jF2, qr_02);
        rd_1 rd_14 = new rd_1(rd_12).a(0, 1, 0, 0, 0, 0);
        nc_0 nc_02 = new nc_0(rd_14, rd_14, jx_0.blQ, 5);
        nc_02.ae(true);
        nc_02.af(true);
        rd_1 rd_15 = new rd_1(rd_14).a(0, 3, 0, 0, 0, 0);
        ayo ayo2 = new ayo(rd_15, rd_15, jx_0.blQ, 6);
        ayo2.ae(true);
        ayo2.af(true);
        ald_22.a(nc_02, ayo2);
        ald_22.e(mb_22);
        apN.aDK().vJ().b(ald_22);
    }
}

