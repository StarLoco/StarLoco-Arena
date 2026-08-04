/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from gv
 */
class gv_2
extends apc {
    final /* synthetic */ ca_0 sP;
    final /* synthetic */ ca_0 sT;
    final /* synthetic */ KI sX;

    gv_2(ca_0 ca_02, ca_0 ca_03, KI kI) {
        this.sP = ca_02;
        this.sT = ca_03;
        this.sX = kI;
    }

    public boolean a(ke ke2) {
        nP nP2 = new nP();
        nP2.as(this.sP.Kd());
        nP2.at(this.sT.Ke());
        nP2.u(false);
        apN.aDK().vJ().b(nP2);
        this.sX.bY(this.sT.Ke());
        azs_0.aLV().a((aho_0)this.sX, "guild.members");
        return false;
    }
}

