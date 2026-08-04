/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from DU
 */
public class du_1
implements hR {
    private static du_1 aPC = new du_1();

    public static du_1 MC() {
        return aPC;
    }

    public String getName() {
        return aon_0.aYc().getString("contentLoader.chat");
    }

    public void a(mk_1 mk_12) {
        aaz_0 aaz_02 = new aaz_0(1, "vicinityPipe", ahi_1.dNx, aon_0.aYc().getString("chat.pipeName.vicinity"), true);
        jm_2 jm_22 = new jm_2(8, "tradePipe", ahi_1.dNz, aon_0.aYc().getString("chat.pipeName.trade"), true);
        jm_2 jm_23 = new jm_2(2, "privatePipe", ahi_1.dNv, aon_0.aYc().getString("chat.pipeName.private"), true);
        jm_2 jm_24 = new jm_2(9, "teammatePipe", ahi_1.dNw, aon_0.aYc().getString("chat.pipeName.teammate"), true);
        jm_2 jm_25 = new jm_2(7, "guildPipe", ahi_1.dNy, aon_0.aYc().getString("chat.pipeName.guild"), true);
        apF apF2 = new apF(4, "gameErrorPipe", ahi_1.dNs, aon_0.aYc().getString("chat.pipeName.gameError"), true);
        apF apF3 = new apF(6, "fightInformationPipe", ahi_1.dNt, aon_0.aYc().getString("chat.pipeName.fightInformation"), true);
        apF apF4 = new apF(5, "gameInformationPipe", ahi_1.dNu, aon_0.aYc().getString("chat.pipeName.gameInformation"), true);
        ql_1 ql_12 = ql_1.acX();
        ql_12.a(1, aaz_02);
        ql_12.a(2, jm_23);
        ql_12.a(9, jm_24);
        ql_12.a(4, apF2);
        ql_12.a(6, apF3);
        ql_12.a(5, apF4);
        ql_12.a(7, jm_25);
        ql_12.a(8, jm_22);
        abl_2 abl_22 = ahv_0.aUv().aUw();
        abl_22.a((ua)aaz_02, aee_1.dBA);
        abl_22.a((ua)jm_22, aee_1.dBA);
        abl_22.a((ua)jm_23, aee_1.dBA);
        abl_22.a((ua)jm_24, aee_1.dBA);
        abl_22.a((ua)jm_25, aee_1.dBA);
        abl_22.a((ua)apF3, aee_1.dBz);
        abl_22.a((ua)apF2, aee_1.dBz);
        abl_22.a((ua)apF4, aee_1.dBz);
        abl_22.setName(aon_0.aYc().getString("chat.pipeName.vicinity"));
        abl_2 abl_23 = ahv_0.aUv().aUw();
        abl_23.a((ua)apF3, aee_1.dBz);
        abl_23.a((ua)apF2, aee_1.dBz);
        abl_23.a((ua)apF4, aee_1.dBz);
        abl_23.setName(aon_0.aYc().getString("chat.pipeName.fightInformation"));
        abl_2 abl_24 = ahv_0.aUv().aUw();
        abl_24.a((ua)aaz_02, aee_1.dBA);
        abl_24.a((ua)jm_22, aee_1.dBA);
        abl_24.a((ua)jm_23, aee_1.dBA);
        abl_24.a((ua)jm_24, aee_1.dBA);
        abl_24.a((ua)jm_25, aee_1.dBA);
        abl_24.a((ua)apF3, aee_1.dBz);
        abl_24.a((ua)apF2, aee_1.dBz);
        abl_24.a((ua)apF4, aee_1.dBz);
        abl_24.setName(aon_0.aYc().getString("chat.pipeName.private"));
        abl_24.a(jm_23);
        aaz_02.b(abl_24);
        jm_22.b(abl_24);
        apF3.b(abl_24);
        ll_0 ll_02 = abl_24.Zl();
        while (ll_02.hasNext()) {
            ll_02.fK();
            Xu xu = (Xu)ll_02.value();
            if (xu.akR().equals(aon_0.aYc().getString("chat.pipeName.private")) || xu.akR().equals(aon_0.aYc().getString("chat.pipeName.guild")) || xu.akR().equals(aon_0.aYc().getString("chat.pipeName.teammate"))) continue;
            xu.setOpen(false);
        }
        azs_0.aLV().g("chat.selectedView", abl_22);
        azs_0.aLV().g("chat.pipes.list", abl_22.Zm());
        mk_12.b(this);
    }
}

