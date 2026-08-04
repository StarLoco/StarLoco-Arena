/*
 * Decompiled with CFR 0.152.
 */
public class aJS
extends aht_1 {
    public static final String TAG = "FoldingContainer";
    private static final String dps = "titleBar";
    private static final String dpu = "content";
    private bo_0 dSE = bo_0.aJs;
    private aht_1 dSF;
    private aht_1 aRH;
    private boolean dSG;
    private boolean dSH;
    public static final int dSI = "titleBarPosition".hashCode();
    public static final int dSJ = "folded".hashCode();

    public void f(na_1 na_12) {
        if (na_12 instanceof aht_1 && ((adg_2)na_12).getThemeElementName().equals(dps)) {
            if (this.dSF != null) {
                this.dSF.aab();
            }
            this.dSF = (aht_1)na_12;
            this.a(this.dSF);
        } else if (na_12 instanceof aht_1 && ((adg_2)na_12).getThemeElementName().equals(dpu)) {
            if (this.aRH != null) {
                this.aRH.aab();
            }
            this.aRH = (aht_1)na_12;
            this.aRH.setVisible(!this.dSG);
            this.a(this.aRH);
        } else if (!(na_12 instanceof adg_2)) {
            super.f(na_12);
        }
    }

    public String getTag() {
        return TAG;
    }

    public bo_0 getTitleBarPosition() {
        return this.dSE;
    }

    public void setTitleBarPosition(bo_0 bo_02) {
        this.dSE = bo_02;
        this.invalidate();
    }

    public void setFolded(boolean bl2) {
        if (bl2) {
            this.aVu();
        } else {
            this.aVv();
        }
    }

    public boolean isFolded() {
        return this.dSG;
    }

    public void aVu() {
        this.dSG = true;
        this.dSH = this.dya;
        this.setExpandable(false);
        if (this.aRH != null) {
            this.aRH.setVisible(false);
        }
        this.Am();
    }

    public void aVv() {
        this.dSG = false;
        this.setExpandable(this.dSH);
        this.aRH.setVisible(true);
        this.Am();
    }

    public void j() {
        super.j();
        this.dSE = null;
        this.aRH = null;
        this.dSF = null;
    }

    public void b() {
        super.b();
        hq_2 hq_22 = new hq_2(this);
        hq_22.b();
        this.a(hq_22);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == dSI) {
            this.setTitleBarPosition((bo_0)((Object)if_12.c(bo_0.class, string)));
        } else if (n2 == dSJ) {
            this.setFolded(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == dSI) {
            this.setTitleBarPosition((bo_0)((Object)object));
        } else if (n2 == dSJ) {
            this.setFolded(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ aht_1 a(aJS aJS2) {
        return aJS2.aRH;
    }

    static /* synthetic */ bo_0 b(aJS aJS2) {
        return aJS2.dSE;
    }

    static /* synthetic */ aht_1 c(aJS aJS2) {
        return aJS2.dSF;
    }
}

