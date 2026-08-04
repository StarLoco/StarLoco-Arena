/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

/*
 * Renamed from Yy
 */
public class yy_0
implements atG {
    private static yy_0 cbg = new yy_0();

    public static yy_0 amI() {
        return cbg;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16385: {
                go_1 go_12 = (go_1)pr_02;
                apN apN2 = apN.aDK();
                DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clK, go_12.Sa());
                DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clM, go_12.Sa() != false ? go_12.RZ().getIndex() - 1 : 0);
                apN2.aQ(go_12.qc());
                apN2.setPassword(go_12.getPassword());
                apN2.dB(false);
                NM nM = go_12.RZ();
                apN2.a(nM);
                nM.aaU();
                pm_0.ur().bD(true).m(aon_0.aYc().getString("logon.progress"), 0);
                if (!apN2.aDO()) {
                    apN2.ayI();
                }
                return false;
            }
            case 16384: {
                yt_2 yt_22 = (yt_2)pr_02;
                aon_0.aYc().a(yt_22.Fd());
                DofusArenaClientInstance.yl().cleanUp();
                if (!yt_22.Fd().getName().equals(aie_0.dOv.getName()) && !yt_22.Fd().getName().equals(aie_0.dOw.getName())) {
                    add_1.aOG().a(aon_0.aYc().getString("gameNotTranslated"), 1090L, 102, 1);
                }
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        azs_0.aLV().g("replayMode", false);
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

