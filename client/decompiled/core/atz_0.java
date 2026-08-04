/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atz
 */
public class atz_0
extends ub_0 {
    private final adg_2 DD;
    private boolean DE = true;

    public atz_0(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public void b(db_2 db_22) {
        if (this.DD.getContainer() == null) {
            return;
        }
        if (this.DE) {
            nm_0 nm_02 = nm_0.k(this.DD.getScreenX() + this.DD.getAppearance().getLeftInset(), this.DD.getScreenY() + this.DD.getAppearance().getBottomInset(), this.DD.getAppearance().getContentWidth(), this.DD.getAppearance().getContentHeight());
            add_1.aOG().aON().f(nm_02);
            alj_0.aWw().h(nm_02);
            nm_0 nm_03 = alj_0.aWw().aWy();
            vo_1.aik().cv(true);
            vo_1.aik().w(nm_03.getX(), nm_03.getY(), nm_03.getWidth() + 1, nm_03.getHeight() + 1);
            vo_1.aik().n(db_22);
        } else {
            alj_0.aWw().aWz();
            nm_0 nm_04 = alj_0.aWw().aWy();
            if (nm_04 != null) {
                vo_1.aik().cv(true);
                vo_1.aik().w(nm_04.getX(), nm_04.getY(), nm_04.getWidth() + 1, nm_04.getHeight() + 1);
                vo_1.aik().n(db_22);
            }
        }
        this.DE = !this.DE;
    }
}

