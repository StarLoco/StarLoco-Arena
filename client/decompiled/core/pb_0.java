/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;

/*
 * Renamed from pB
 */
class pb_0
implements aji_0 {
    final acv_2 aci = new acv_2("<undefined>");

    pb_0() {
    }

    public void a(mT mT2, boolean bl2) {
        if (bl2 && mT2.isVisible()) {
            String string = null;
            if (mT2 instanceof aez_0) {
                aez_0 aez_02 = (aez_0)mT2;
                ca_0 ca_02 = aez_02.aPY();
                String string2 = "";
                if (ca_02 != null && ca_02.Kf() >= 5L) {
                    string2 = ca_02.hd();
                }
                string = ((aez_0)mT2).Ld();
                if (string2 != null && !string2.equals("")) {
                    string = string + "\n[" + string2 + "]";
                }
            } else if (mT2 instanceof vD) {
                ee_2 ee_22 = ((vD)mT2).tG();
                String string3 = ee_22.getName();
                int n2 = ee_22.d(Lr.bqx);
                int n3 = ee_22.a(Lr.bqx).max();
                string = String.format("%s (%d/%d %s)", string3, n2, n3, aon_0.aYc().getString("HP"));
                if (!(apN.aDK().c(agd_1.awz()) || apN.aDK().c(alx_2.aWN()) || apN.aDK().c(azL.aMm()) || apN.aDK().c(fk_0.jo()))) {
                    ArrayList arrayList;
                    boolean bl3;
                    boolean bl4 = bl3 = DofusArenaClientInstance.yl().aod().a(adc_0.clZ) && (!apN.aDK().c(anx_1.aXx()) || !apN.aDK().aDL().ass().r(ee_22.getId()));
                    if (bl3) {
                        yv_0.amA().e(ee_22);
                    }
                    if ((arrayList = ee_22.Qb()).size() > 0) {
                        Iterable iterable = ((ack_1)arrayList.get(0)).aqO().b(ee_22.gn(), ee_22.go(), (short)ee_22.getAltitude(), ee_22.gn(), ee_22.go(), (short)ee_22.getAltitude(), ee_22.L());
                        abs_2.aNo().c(iterable);
                    }
                }
            }
            if (string != null) {
                this.aci.setText(string);
                this.aci.c(mT2);
                wj_2.Df().a(this.aci);
            }
        } else {
            wj_2.Df().b(this.aci);
            yv_0.amA().clear();
            abs_2.aNo().clear();
        }
    }
}

