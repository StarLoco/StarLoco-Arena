/*
 * Decompiled with CFR 0.152.
 */
import java.util.Date;

/*
 * Renamed from zB
 */
public class zb_1
implements atG {
    private static zb_1 aFF = new zb_1();

    public static zb_1 GG() {
        return aFF;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            po_0.abV().cc(false);
            add_1.aOG().a("calendarDialog", oh_2.bq("calendarDialog"), (short)10000);
            de_2.Mc().Mf().setTime(new Date());
            azs_0.aLV().g("calendar", de_2.Mc());
            add_1.aOG().l("dofusarena.calendar", aug.class);
            apN.aDK().Ln().yH();
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kO("calendarDialog");
            azs_0.aLV().kb("calendar");
            add_1.aOG().kG("dofusarena.calendar");
            apN.aDK().b(alv_0.aWM());
            apN.aDK().Ln().yI();
        }
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16700: {
                ia_2 ia_22 = (ia_2)pr_02;
                wy_2 wy_22 = ia_22.lm();
                if (wy_22 != null) {
                    azs_0.aLV().g("coachManagement.selectedCard", wy_22);
                }
                return false;
            }
            case 16701: {
                azs_0.aLV().g("coachManagement.selectedCard", (Object)null);
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
}

