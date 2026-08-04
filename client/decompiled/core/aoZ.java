/*
 * Decompiled with CFR 0.152.
 */
abstract class aoZ
implements adX {
    private aji_1 blb;
    static final /* synthetic */ boolean bb;

    private aoZ() {
    }

    public void a(yf_0 yf_02) {
        this.blb = add_1.aOG().azj().lh(yf_02.getElementMap().getId());
        if (!bb && this.blb == null) {
            throw new AssertionError((Object)"Impossible de charger une bulle");
        }
    }

    protected void iR(String string) {
        if (this.blb == null) {
            return;
        }
        yt_1 yt_12 = (yt_1)this.blb.R("text");
        if (yt_12 == null) {
            return;
        }
        yt_12.setStyle(string);
    }

    protected void a(String string, agj_1 agj_12) {
        if (this.blb == null) {
            return;
        }
        aht_1 aht_12 = (aht_1)this.blb.R("container");
        if (aht_12 == null) {
            return;
        }
        aht_12.setStyle(string);
        aht_12.setPrefSize(agj_12);
        aht_12.getAppearance().setModulationColor(null);
        aht_1 aht_13 = (aht_1)this.blb.R("coloredContainer");
        if (aht_13 == null) {
            return;
        }
        aht_13.setPrefSize(new agj_1(agj_12.getSize().width, agj_12.getSize().height + 20));
    }

    protected void d(String string, int n2, int n3) {
        if (this.blb == null) {
            return;
        }
        azc_0 azc_02 = (azc_0)this.blb.R("image");
        if (azc_02 == null) {
            return;
        }
        azc_02.setStyle(string);
        azc_02.getAppearance().setModulationColor(null);
        auW auW2 = (auW)azc_02.getLayoutData();
        auW2.setXOffset(n2);
        auW2.setYOffset(n3);
    }

    public void b(yf_0 yf_02) {
        this.blb = add_1.aOG().azj().lh(yf_02.getElementMap().getId());
        azc_0 azc_02 = (azc_0)this.blb.R("image");
        boolean bl2 = yf_02.isToRight();
        azc_02.setStyle(bl2 ? "BubbleArrowLeft" : "BubbleArrowRight");
        auW auW2 = (auW)azc_02.getLayoutData();
        auW2.setAlign(bl2 ? ajn_1.dSy : ajn_1.dSC);
        this.a(yf_02);
    }

    /* synthetic */ aoZ(awU awU2) {
        this();
    }

    static {
        bb = !hv_2.class.desiredAssertionStatus();
    }
}

