/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from kb
 */
public class kb_2
extends ub_0 {
    private final adg_2 DD;
    private boolean DE = true;

    public kb_2(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public void b(db_2 db_22) {
        aht_1 aht_12 = this.DD.getContainer();
        if (aht_12 == null) {
            return;
        }
        if (this.DE) {
            if (aht_12.aPx()) {
                this.DD.setScreenPosition(this.DD.getScreenX(), this.DD.getScreenY());
                aht_12.setScreenPosition(aht_12.getScreenX(), aht_12.getScreenY());
                nm_0 nm_02 = aht_12.getScissor(this.DD);
                add_1.aOG().aON().f(nm_02);
                alj_0.aWw().h(nm_02);
                nm_0 nm_03 = this.DD.getComputedScissor();
                if (nm_03 != null) {
                    add_1.aOG().aON().f(nm_03);
                    alj_0.aWw().h(nm_03);
                }
                nm_0 nm_04 = alj_0.aWw().aWy();
                vo_1.aik().cv(true);
                vo_1.aik().w(nm_04.getX(), nm_04.getY(), nm_04.getWidth() + 1, nm_04.getHeight() + 1);
            }
        } else if (aht_12.aPx()) {
            nm_0 nm_05;
            this.DD.setScreenPosition(-1, -1);
            aht_12.setScreenPosition(-1, -1);
            alj_0.aWw().aWz();
            if (this.DD.getScissor() != null) {
                alj_0.aWw().aWz();
            }
            if ((nm_05 = alj_0.aWw().aWy()) != null) {
                vo_1.aik().cv(true);
                vo_1.aik().w(nm_05.getX(), nm_05.getY(), nm_05.getWidth() + 1, nm_05.getHeight() + 1);
            }
        }
        this.DE = !this.DE;
    }
}

