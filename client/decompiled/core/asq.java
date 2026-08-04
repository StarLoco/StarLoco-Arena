/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

public class asq
extends aht_1 {
    public static final String TAG = "WindowResizePoint";
    public static final String aTJ = "WRP";
    private aab_2 bGA = null;
    private BT cRZ;
    private Point cwT;
    private boolean cSa = false;
    private aht_1 cSb = null;
    private ov_1 aDc;
    public static final int cSc = "pointAlign".hashCode();

    public String getTag() {
        return TAG;
    }

    public void setPointAlign(BT bT) {
        this.cRZ = bT;
        switch (this.cRZ) {
            case aJY: 
            case aJW: {
                this.setCursorType(xy_0.bYp);
                break;
            }
            case aJU: 
            case aKa: {
                this.setCursorType(xy_0.bYq);
                break;
            }
            case aJT: 
            case aKb: {
                this.setCursorType(xy_0.bYr);
                break;
            }
            case aJV: 
            case aJZ: {
                this.setCursorType(xy_0.bYs);
                break;
            }
            case aJX: {
                this.setCursorType(xy_0.bYn);
            }
        }
    }

    public BT getPointAlign() {
        return this.cRZ;
    }

    public void setWindow(aab_2 aab_22) {
        this.bGA = aab_22;
    }

    public aab_2 getWindow() {
        return this.bGA;
    }

    protected int setCheckedWidth(int n2) {
        boolean bl2 = false;
        agj_1 agj_12 = this.bGA.getPrefSize();
        if (n2 >= agj_12.width) {
            this.bGA.setSize(n2, this.bGA.aLd.height);
        } else {
            n2 = agj_12.width;
            this.bGA.setSize(n2, this.bGA.aLd.height);
        }
        return n2;
    }

    protected int setCheckedHeight(int n2) {
        boolean bl2 = false;
        agj_1 agj_12 = this.bGA.getPrefSize();
        if (n2 >= agj_12.height) {
            this.bGA.setSize(this.bGA.aLd.width, n2);
        } else {
            n2 = agj_12.height;
            this.bGA.setSize(this.bGA.aLd.width, n2);
        }
        return n2;
    }

    public void ade() {
        this.aDc = new aNd(this);
        ago_2.getInstance().a(qe_1.bFA, this.aDc, false);
        this.a(qe_1.bFv, new aMZ(this), false);
    }

    public void yx() {
        super.yx();
        this.bGA = (aab_2)this.getParentOfType(aab_2.class);
        if (this.bGA != null) {
            this.cSb = this.bGA.getWidgetParentOfType(ex_2.class);
        }
    }

    public void j() {
        super.j();
        ago_2.getInstance().b(qe_1.bFA, this.aDc, false);
        this.bGA = null;
        this.cRZ = null;
    }

    public void b() {
        super.b();
        this.ade();
        this.dyc = false;
    }

    public void a(air_1 air_12) {
        asq asq2 = (asq)air_12;
        super.a(air_12);
        asq2.setPointAlign(this.cRZ);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != cSc) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setPointAlign(BT.dv(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        if (bl2) {
            this.setPointAlign(this.cRZ);
        } else {
            this.setCursorType(xy_0.bYl);
        }
    }

    static /* synthetic */ boolean a(asq asq2, boolean bl2) {
        asq2.cSa = bl2;
        return asq2.cSa;
    }

    static /* synthetic */ boolean a(asq asq2) {
        return asq2.cSa;
    }

    static /* synthetic */ Point a(asq asq2, Point point) {
        asq2.cwT = point;
        return asq2.cwT;
    }

    static /* synthetic */ aab_2 b(asq asq2) {
        return asq2.bGA;
    }

    static /* synthetic */ Point c(asq asq2) {
        return asq2.cwT;
    }

    static /* synthetic */ BT d(asq asq2) {
        return asq2.cRZ;
    }

    static /* synthetic */ aht_1 e(asq asq2) {
        return asq2.cSb;
    }
}

