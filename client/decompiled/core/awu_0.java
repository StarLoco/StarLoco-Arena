/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from awu
 */
class awu_0
implements ja_1 {
    final /* synthetic */ ayr_0 dhO;
    final /* synthetic */ wy_2 bLY;
    final /* synthetic */ ee_2 cDu;
    final /* synthetic */ ahr_2 dhP;
    final /* synthetic */ ArrayList dhQ;
    final /* synthetic */ ac_2 dhR;
    final /* synthetic */ afb_1 dhS;

    awu_0(afb_1 afb_12, ayr_0 ayr_02, wy_2 wy_22, ee_2 ee_22, ahr_2 ahr_22, ArrayList arrayList, ac_2 ac_22) {
        this.dhS = afb_12;
        this.dhO = ayr_02;
        this.bLY = wy_22;
        this.cDu = ee_22;
        this.dhP = ahr_22;
        this.dhQ = arrayList;
        this.dhR = ac_22;
    }

    public void b(int n2) {
        if (n2 == 8) {
            aow_2 aow_22 = new aow_2();
            aow_22.lE(this.dhO.getId());
            if (this.dhO.aKZ().equals("Barrier") && !this.dhO.aKY()) {
                sj_1 sj_12 = apN.aDK().Ln();
                ky_2 ky_22 = sj_12.yD();
                wy_2 wy_22 = (wy_2)ky_22.bW(this.bLY.jf());
                if (wy_22 == null) {
                    wy_22 = (wy_2)ky_22.bW(-this.bLY.jf());
                }
                aow_22.i(wy_22.jf());
                ky_22.ag(wy_22.je());
                azs_0.aLV().a((aho_0)this.dhO, "barrier");
                xj xj2 = (xj)la_0.XJ().pj(this.bLY.jf());
                for (int j = 0; j < xj2.tu().length; ++j) {
                    akw_0 akw_02 = xj2.tu()[j];
                    if (akw_02.getType() != AI.aHK.tI() || akw_02.rg().length < 2) continue;
                    this.cDu.kh().b((short)akw_02.rg()[0], (byte)akw_02.rg()[1]);
                }
            }
            aow_22.j(afb_1.a(this.dhS));
            apN.aDK().vJ().b(aow_22);
            this.dhP.x(this.dhQ);
            afb_1.b(this.dhS).fi(this.dhO.aLe());
            afb_1.b(this.dhS).fj(this.dhO.aLf());
            this.cDu.a(this.dhO, this.dhR.be(), false);
        }
    }
}

