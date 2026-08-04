/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abz
 */
public class abz_2
extends aht_1 {
    private ov_1 cio = null;
    private BT cF = BT.aJT;
    private BT cG = BT.aKb;
    private aci_0 cip;
    private adg_2 ciq;
    private String cir = null;
    private boolean cH = true;
    public static final int cJ = "align".hashCode();
    public static final int cK = "hotSpotPosition".hashCode();

    public void yx() {
        this.cio = new kd_2(this);
        ago_2.getInstance().a(qe_1.bFz, this.cio, false);
        this.a(qe_1.bFz, new kf_2(this), false);
        this.a(qe_1.bFA, new kg_2(this), false);
    }

    public void EO() {
        ago_2.getInstance().b(qe_1.bFz, this.cio, false);
    }

    public void show() {
        if (!this.getVisible()) {
            this.setVisible(true);
            this.setSizeToPrefSize();
            this.setPositionToOptimal();
        }
    }

    public void validate() {
        super.validate();
        this.setPositionToOptimal();
    }

    private void setPositionToOptimal() {
        if (this.cip != null && this.cG != null && this.cF != null) {
            int n2 = this.cip.getDisplayX();
            int n3 = this.cip.getDisplayY();
            BT bT = this.cG;
            BT bT2 = this.cF;
            int n4 = n2 + bT.eL(this.cip.getWidth()) - bT2.eL(this.aLd.width);
            int n5 = n3 + bT.eM(this.cip.getHeight()) - bT2.eM(this.aLd.height);
            if (n4 < 0 || n4 > this.dxR.getAppearance().getContentWidth() - this.aLd.width) {
                bT = bT.IL();
                bT2 = bT2.IL();
            }
            if (n5 < 0 || n5 > this.dxR.getAppearance().getContentHeight() - this.aLd.height) {
                bT = bT.IM();
                bT2 = bT2.IM();
            }
            n4 = n2 + bT.eL(this.cip.getWidth()) - bT2.eL(this.aLd.width);
            n5 = n3 + bT.eM(this.cip.getHeight()) - bT2.eM(this.aLd.height);
            n4 = Math.max(0, Math.min(n4, this.dxR.getAppearance().getContentWidth() - this.aLd.width));
            n5 = Math.max(0, Math.min(n5, this.dxR.getAppearance().getContentHeight() - this.aLd.height));
            if (n2 >= n4 && n3 >= n5 && n2 < n4 + this.aLd.width && n3 < n5 + this.aLd.height) {
                n4 = n2 - this.aLd.width;
                n4 = Math.max(0, Math.min(n4, this.dxR.getAppearance().getContentWidth() - this.aLd.width));
            }
            this.setPosition(n4, n5);
        }
    }

    public void hide() {
        this.setVisible(false);
        this.cH = true;
    }

    public aci_0 getClient() {
        return this.cip;
    }

    public void setClient(aci_0 aci_02) {
        this.cir = null;
        this.cip = aci_02;
        if (this.cip != null) {
            aji_1 aji_12 = this.cip.getElementMap();
            while (aji_12.azk() != null) {
                aji_12 = aji_12.azk();
            }
            this.cir = aji_12.getId();
        }
    }

    public BT getHotSpotPosition() {
        return this.cF;
    }

    public void setHotSpotPosition(BT bT) {
        if (bT != null) {
            this.cF = bT;
        }
    }

    public boolean getHideOnClick() {
        return this.cH;
    }

    public void setHideOnClick(boolean bl2) {
        this.cH = bl2;
    }

    public BT getAlign() {
        return this.cG;
    }

    public void setAlign(BT bT) {
        this.cG = bT;
    }

    public void setContent(adg_2 adg_22) {
        if (this.ciq != adg_22) {
            for (int j = this.dMc.size() - 1; j >= 0; --j) {
                ((adg_2)this.dMc.get(j)).aab();
            }
            if (adg_22 != null) {
                adg_22 = (adg_2)adg_22.aah();
                this.a(adg_22);
            }
            this.ciq = adg_22;
        }
    }

    public adg_2 getContent() {
        return this.ciq;
    }

    public void b() {
        super.b();
        auW auW2 = new auW();
        auW2.b();
        this.a(auW2);
        this.setVisible(false);
        add_1.aOG().a(new ki_2(this));
        this.cH = true;
    }

    public void j() {
        super.j();
        this.cG = null;
        this.cF = null;
        this.cir = null;
        this.cio = null;
        this.ciq = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else if (n2 == cK) {
            this.setHotSpotPosition(BT.dv(string));
        } else if (n2 == dyD) {
            this.setVisible(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cJ) {
            this.setAlign((BT)((Object)object));
        } else if (n2 == cK) {
            this.setHotSpotPosition((BT)((Object)object));
        } else if (n2 == dyD) {
            this.setVisible(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ boolean a(abz_2 abz_22) {
        return abz_22.cH;
    }

    static /* synthetic */ aci_0 b(abz_2 abz_22) {
        return abz_22.cip;
    }

    static /* synthetic */ String c(abz_2 abz_22) {
        return abz_22.cir;
    }
}

