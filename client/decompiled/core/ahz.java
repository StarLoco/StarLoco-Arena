/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class ahz
extends aht_1 {
    public static final String TAG = "MRU";
    public static final String ajq = "Button";
    public static final String cvM = "IncreaseButton";
    public static final String cvN = "DecreaseButton";
    public static final int cvO = 40;
    public static final float cvP = 0.7853982f;
    private aqq_0 aDf;
    private ai_2 cvQ;
    private ps cvR;
    private ov_1 cvS;
    private boolean cvT = false;
    private int cvU;
    private int cvV;
    private int bBc = 40;
    private final ArrayList dh = new ArrayList(3);
    private byte cvW = 0;
    private aqq_0 apb;
    private aqq_0 apc;
    public static final int cvX = "radius".hashCode();

    public static float kJ(int n2) {
        switch (n2) {
            case 2: {
                return 1.5707964f;
            }
            case 4: 
            case 6: {
                return 1.0471976f;
            }
        }
        return 0.7853982f;
    }

    public void f(na_1 na_12) {
        if (na_12 instanceof ai_2) {
            this.cvQ = (ai_2)na_12;
        } else if (na_12 instanceof aqq_0) {
            this.aDf = (aqq_0)na_12;
        } else if (!(na_12 instanceof adg_2)) {
            super.f(na_12);
        }
    }

    public void a(String string, String string2, akq_1 akq_12, String string3, ArrayList arrayList, apc apc2, boolean bl2) {
        Object object;
        if (this.dh.isEmpty()) {
            return;
        }
        aqq_0 aqq_02 = (aqq_0)this.aDf.aah();
        if (string != null) {
            aqq_02.setText(string);
        }
        if (string2 != null && this.cvR != null) {
            aqq_02.a(qe_1.bFE, new op_1(this, string2, aqq_02), true);
            aqq_02.a(qe_1.bFF, new oo_1(this), true);
        }
        if (akq_12 != null) {
            object = ur_1.checkOut();
            ((ur_1)object).setPixmap(akq_12);
            aqq_02.setPixmap((ur_1)object);
        }
        if (string3 != null) {
            aqq_02.kU(string3);
        }
        aqq_02.setStyle(TAG + this.getStyle() + "$buttonNorth", true);
        aqq_02.setOnClick(apc2);
        aqq_02.setEnabled(bl2);
        aqq_02.setVisible(false);
        if (arrayList != null) {
            for (ob_1 ob_12 : arrayList) {
                ob_12.setRemovable(false);
                aqq_02.getAppearance().a(ob_12);
            }
        }
        object = ((Oc)this.dh.get((int)(this.dh.size() - 1))).bBG;
        ((ArrayList)object).add(aqq_02);
        this.a(aqq_02);
    }

    public String getTag() {
        return TAG;
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if (ajq.equalsIgnoreCase(string)) {
            if (this.aDf != null) {
                return this.aDf;
            }
            aqq_0 aqq_02 = new aqq_0();
            aqq_02.b();
            return aqq_02;
        }
        if (cvN.equalsIgnoreCase(string)) {
            return this.apc;
        }
        if (cvM.equalsIgnoreCase(string)) {
            return this.apb;
        }
        return null;
    }

    public int getRadius() {
        return this.bBc;
    }

    public void setRadius(int n2) {
        this.bBc = n2;
    }

    public void axh() {
        this.dh.add(new Oc(null));
    }

    public int getGroupSize() {
        return this.dh.size();
    }

    public void br(int n2, int n3) {
        this.setVisible(true);
        this.cvU = n2;
        this.cvV = n3;
    }

    public void show() {
        this.br(awS.aJG().getX(), awS.aJG().getY());
    }

    public void hide() {
        if (this.aQv) {
            add_1.aOG().kO(this.blb.getId());
        }
    }

    public void ade() {
        this.cvS = new ou_2(this);
        ago_2.getInstance().a(qe_1.bFB, this.cvS, false);
        this.apb.a(qe_1.bFB, new os_2(this), false);
        this.apc.a(qe_1.bFB, new oy_2(this), false);
    }

    public void Am() {
        this.aTY();
        this.invalidate();
    }

    public void validate() {
        this.setSizeToPrefSize();
        int n2 = this.cvU - BP.aJB.eL(this.getWidth());
        int n3 = this.cvV - BP.aJB.eM(this.getHeight());
        n2 = Math.min(Math.max(0, n2), this.dxR.getAppearance().getContentWidth() - this.getWidth());
        n3 = Math.min(Math.max(0, n3), this.dxR.getAppearance().getContentHeight() - this.getHeight());
        this.setPosition(n2, n3);
        super.validate();
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (!this.cvT) {
            this.ade();
            this.cvT = true;
        }
        return bl2;
    }

    public void Aj() {
        super.Aj();
        this.cvR = (ps)this.blb.R("popupText");
    }

    public void b() {
        super.b();
        aje_2 aje_22 = new aje_2(this, null);
        aje_22.b();
        this.a(aje_22);
        this.apb = new aqq_0();
        this.apb.b();
        this.a(this.apb);
        this.apb.setVisible(false);
        this.apc = new aqq_0();
        this.apc.b();
        this.a(this.apc);
        this.apc.setVisible(false);
        this.setNeedsToPostProcess();
    }

    public void j() {
        super.j();
        ago_2.getInstance().b(qe_1.bFB, this.cvS, false);
        this.dh.clear();
        if (this.cvQ != null) {
            this.cvQ.aab();
            this.cvQ = null;
        }
        if (this.aDf != null) {
            this.aDf.aab();
            this.aDf = null;
        }
        if (this.apc != null) {
            this.apc.aab();
            this.apc = null;
        }
        if (this.apb != null) {
            this.apb.aab();
            this.apb = null;
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != cvX) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setRadius(Gr.R(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != cvX) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setRadius(Gr.R(object));
        return true;
    }

    static /* synthetic */ ArrayList a(ahz ahz2) {
        return ahz2.dh;
    }

    static /* synthetic */ byte b(ahz ahz2) {
        return ahz2.cvW;
    }

    static /* synthetic */ int a(ahz ahz2, int n2) {
        ahz2.bBc = n2;
        return ahz2.bBc;
    }

    static /* synthetic */ int c(ahz ahz2) {
        return ahz2.bBc;
    }

    static /* synthetic */ aqq_0 d(ahz ahz2) {
        return ahz2.apb;
    }

    static /* synthetic */ aqq_0 e(ahz ahz2) {
        return ahz2.apc;
    }

    static /* synthetic */ ps f(ahz ahz2) {
        return ahz2.cvR;
    }

    static /* synthetic */ ai_2 g(ahz ahz2) {
        return ahz2.cvQ;
    }

    static /* synthetic */ aji_1 h(ahz ahz2) {
        return ahz2.blb;
    }

    static /* synthetic */ byte a(ahz ahz2, byte by) {
        ahz2.cvW = by;
        return ahz2.cvW;
    }
}

