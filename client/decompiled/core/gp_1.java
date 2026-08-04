/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from gp
 */
class gp_1
implements ja_1 {
    final /* synthetic */ ca_0 sP;
    final /* synthetic */ aez_0 sQ;

    gp_1(ca_0 ca_02, aez_0 aez_02) {
        this.sP = ca_02;
        this.sQ = aez_02;
    }

    public void b(int n2) {
        if (n2 == 8) {
            KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
            nP nP2 = new nP();
            nP2.as(this.sP.Kd());
            nP2.at(this.sQ.getId());
            nP2.u(false);
            apN.aDK().vJ().b(nP2);
            kI.bY(this.sQ.getId());
            azs_0.aLV().a((aho_0)kI, "guild.members");
            add_1.aOG().kO("guildCoachStatsDialog");
        }
    }
}

