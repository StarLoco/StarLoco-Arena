/*
 * Decompiled with CFR 0.152.
 */
public class awC
extends aht_1 {
    public static final String TAG = "PopupMenu";
    public static final String ajq = "Button";
    public static final String dhX = "Label";
    public static final String dhY = "Separator";
    private BT cF = BT.aJT;
    private int cvU = -1;
    private int cvV = -1;
    private ov_1 cvS;
    private ov_1 dhZ;
    private aqq_0 aDf;
    private OE lU;
    private tt dia;
    private boolean cvT = false;
    public static final int cK = "hotSpotPosition".hashCode();

    public void f(na_1 na_12) {
        if (na_12 instanceof aqq_0) {
            this.aDf = (aqq_0)na_12;
        } else if (na_12 instanceof OE) {
            this.lU = (OE)na_12;
        } else if (na_12 instanceof tt) {
            this.dia = (tt)na_12;
        } else if (!(na_12 instanceof adg_2)) {
            super.f(na_12);
        }
    }

    public void a(String string, akq_1 akq_12) {
        OE oE = (OE)this.lU.aah();
        oE.setText(string);
        this.a(oE);
    }

    public void a(String string, akq_1 akq_12, apc apc2, boolean bl2) {
        aqq_0 aqq_02 = (aqq_0)this.aDf.aah();
        this.a(aqq_02);
        aqq_02.setText(string);
        aqq_02.setOnClick(apc2);
        aqq_02.setEnabled(bl2);
    }

    public void addSeparator() {
        tt tt2 = (tt)this.dia.aah();
        this.a(tt2);
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
        if (dhX.equalsIgnoreCase(string)) {
            if (this.lU != null) {
                return this.lU;
            }
            OE oE = new OE();
            oE.b();
            return oE;
        }
        if (dhY.equalsIgnoreCase(string)) {
            if (this.dia != null) {
                return this.dia;
            }
            tt tt2 = new tt();
            tt2.b();
            return tt2;
        }
        return null;
    }

    public void setHotSpotPosition(BT bT) {
        this.cF = bT;
    }

    public void br(int n2, int n3) {
        this.setVisible(true);
        this.cvU = n2;
        this.cvV = n3;
    }

    public void show() {
        this.br(awS.aJG().getX(), awS.aJG().getY());
    }

    public void ade() {
        this.cvS = new ip_0(this);
        this.dhZ = new is_0(this);
        ago_2.getInstance().a(qe_1.bFB, this.cvS, false);
        ago_2.getInstance().a(qe_1.bFz, this.dhZ, false);
    }

    public void validate() {
        this.setSizeToPrefSize();
        this.cvU -= this.cF.eL(this.getWidth());
        this.cvV -= this.cF.eM(this.getHeight());
        ex_2 ex_22 = (ex_2)this.getWidgetParentOfType(ex_2.class);
        this.cvU = Math.min(ex_22.getWidth() - this.getWidth(), this.cvU);
        if (this.cvV < 0) {
            this.cvV = 0;
        }
        this.cvV = Math.min(ex_22.getHeight() - this.getHeight(), this.cvV);
        this.setPosition(this.cvU, this.cvV);
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
        this.setStyle(this.dyf[0], true);
    }

    public void b() {
        super.b();
        ei_1 ei_12 = ei_1.checkOut();
        ei_12.setHorizontal(false);
        this.a(ei_12);
        this.setNeedsToPostProcess();
    }

    public void j() {
        super.j();
        ago_2.getInstance().b(qe_1.bFB, this.cvS, false);
        ago_2.getInstance().b(qe_1.bFz, this.dhZ, false);
        if (this.aDf != null) {
            this.aDf.aab();
            this.aDf = null;
        }
        if (this.lU != null) {
            this.lU.aab();
            this.lU = null;
        }
        if (this.dia != null) {
            this.dia.aab();
            this.dia = null;
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != cK) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setHotSpotPosition(BT.dv(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != cK) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setHotSpotPosition((BT)((Object)object));
        return true;
    }

    static /* synthetic */ aji_1 b(awC awC2) {
        return awC2.blb;
    }

    static /* synthetic */ aji_1 c(awC awC2) {
        return awC2.blb;
    }

    static /* synthetic */ aji_1 d(awC awC2) {
        return awC2.blb;
    }
}

