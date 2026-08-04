/*
 * Decompiled with CFR 0.152.
 */
class Wm
implements alx_0 {
    private ai_2 bTW;
    private adg_2 DD;

    private Wm() {
    }

    public boolean a(pr_0 pr_02) {
        if (this.bTW != null && this.DD != null) {
            this.bTW.b((aci_0)this.DD);
        }
        return false;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    public ai_2 getPopup() {
        return this.bTW;
    }

    public void setPopup(ai_2 ai_22) {
        this.bTW = ai_22;
    }

    public adg_2 getWidget() {
        return this.DD;
    }

    public void setWidget(adg_2 adg_22) {
        this.DD = adg_22;
    }

    /* synthetic */ Wm(ne_1 ne_12) {
        this();
    }
}

