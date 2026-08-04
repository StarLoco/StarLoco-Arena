/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aI
 */
public class ai_2
extends aNZ {
    public static final String TAG = "popup";
    private BT cF = BT.aJT;
    private BT cG = BT.aKb;
    private boolean cH = true;
    private adg_2 cI;
    public static final int cJ = "align".hashCode();
    public static final int cK = "hotSpotPosition".hashCode();
    public static final int cL = "hideOnClick".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof adg_2) {
            this.cI = (adg_2)na_12;
            this.cI.setIsATemplate(true);
            super.a(na_12, false);
        } else {
            super.a(na_12);
        }
    }

    public String getTag() {
        return TAG;
    }

    public BT getHotSpotPosition() {
        return this.cF;
    }

    public void setHotSpotPosition(BT bT) {
        this.cF = bT;
    }

    public BT getAlign() {
        return this.cG;
    }

    public void setAlign(BT bT) {
        this.cG = bT;
    }

    public boolean getHideOnClick() {
        return this.cH;
    }

    public void setHideOnClick(boolean bl2) {
        this.cH = bl2;
    }

    public adg_2 getContent() {
        return this.cI;
    }

    public void a(air_1 air_12) {
        ai_2 ai_22 = (ai_2)air_12;
        super.a(air_12);
        ai_22.setAlign(this.cG);
        ai_22.setHotSpotPosition(this.cF);
        ai_22.setHideOnClick(this.cH);
    }

    public void a(aci_0 aci_02) {
        abz_2 abz_22 = ago_2.getInstance().getPopupContainer();
        if (abz_22 != null) {
            if (!abz_22.getVisible()) {
                this.a(aci_02, abz_22);
            } else if (this.cH) {
                this.hide();
            }
        }
    }

    public void b(aci_0 aci_02) {
        abz_2 abz_22 = ago_2.getInstance().getPopupContainer();
        this.a(aci_02, abz_22);
    }

    public void a(aci_0 aci_02, abz_2 abz_22) {
        if (abz_22 != null) {
            abz_22.setAlign(this.cG);
            abz_22.setHotSpotPosition(this.cF);
            abz_22.setContent(this.cI);
            abz_22.setClient(aci_02);
            abz_22.setHideOnClick(this.cH);
            abz_22.show();
            aek.atD().atK();
        }
    }

    public void hide() {
        abz_2 abz_22 = ago_2.getInstance().getPopupContainer();
        if (abz_22 != null) {
            abz_22.hide();
        }
    }

    public void j() {
        super.j();
        if (this.cI != null) {
            this.cI = null;
        }
        this.cG = null;
        this.cF = null;
    }

    public void b() {
        super.b();
        this.cH = true;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else if (n2 == cK) {
            this.setHotSpotPosition(BT.dv(string));
        } else if (n2 == cL) {
            this.setHideOnClick(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != cL) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setHideOnClick(Gr.getBoolean(object));
        return true;
    }
}

