/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

public class yE
extends aht_1
implements Fc {
    public static final String TAG = "ComboBoxPlus";
    public static final String aCV = "ComboBox";
    public static final String aCW = "renderable";
    public static final String aCX = "list";
    public static final String aCY = "button";
    private boolean aCZ = false;
    private boolean aDa = true;
    private ov_1 aDb;
    private ov_1 aDc;
    private ov_1 aDd;
    private rf_0 aDe = null;
    private qa_1 vc = null;
    private aqq_0 aDf = null;
    private int ajR = -1;
    private String ed = null;
    private aji_1 ee = null;
    private BT cG = BT.aKa;
    private BT cF = BT.aJU;
    public static final int ei = "content".hashCode();
    public static final int ajW = "maxRows".hashCode();
    public static final int el = "selectedValue".hashCode();
    public static final int cJ = "align".hashCode();
    public static final int cK = "hotSpotPosition".hashCode();

    public void a(na_1 na_12) {
        boolean bl2 = true;
        if (na_12 instanceof rf_0) {
            if (this.aDe != null) {
                this.aDe.j();
            }
            bl2 = false;
            this.aDe = (rf_0)na_12;
            this.aDe.setModalLevel(amY.cIg);
            this.a(this.aDe);
        } else if (na_12 instanceof qa_1) {
            if (this.vc != null) {
                this.vc.j();
            }
            this.vc = (qa_1)na_12;
        } else if (na_12 instanceof aqq_0) {
            this.aDf = (aqq_0)na_12;
        }
        if (bl2) {
            super.a(na_12);
        }
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if (aCW.equalsIgnoreCase(string)) {
            if (this.vc != null) {
                return this.vc;
            }
        } else if (aCX.equalsIgnoreCase(string)) {
            if (this.aDe != null) {
                return this.aDe;
            }
        } else if (aCY.equalsIgnoreCase(string)) {
            return this.aDf;
        }
        return null;
    }

    public String getTag() {
        return TAG;
    }

    public aqq_0 getButton() {
        return this.aDf;
    }

    public rf_0 getList() {
        return this.aDe;
    }

    public int getMaxRows() {
        return this.ajR;
    }

    public void setMaxRows(int n2) {
        this.ajR = n2;
    }

    public void setElementMap(aji_1 aji_12) {
        super.setElementMap(aji_12);
        if (this.aDe != null) {
            this.aDe.setElementMap(aji_12);
        }
    }

    public Object getSelectedValue() {
        if (this.aDe != null) {
            return this.aDe.getSelectedValue();
        }
        return null;
    }

    public void setSelectedValue(Object object) {
        if (object == null) {
            return;
        }
        if (this.aDe != null) {
            this.aDe.setSelectedValue(object);
            Object object2 = this.aDe.getSelectedValue();
            if (!(object == object2 || object2 != null && object2.equals(object))) {
                a.error((Object)("Impossible de retrouver la valeur s\u00e9lectionn\u00e9e dans la liste - il faut appliquer l'attribut content AVANT selectedValue - " + object + " - " + object2));
            }
            this.setRenderableContent(object2, -1);
        }
    }

    public qa_1 getRenderable() {
        return this.vc;
    }

    public void setContentProperty(String string, aji_1 aji_12) {
        this.ed = string;
        this.ee = aji_12;
    }

    private void setRenderableContent(Object object, int n2) {
        if (this.vc != null) {
            Object object2;
            int n3 = 0;
            if (this.aDe != null) {
                object2 = this.aDe.getSelectedValue();
                n3 = this.aDe.getSelectedOffset();
            } else {
                object2 = object;
                if (n2 != -1) {
                    n3 = n2;
                }
            }
            if (object2 != null) {
                this.vc.setContentProperty(this.ed + "#" + n3, this.ee);
            }
            this.vc.setContent(object2);
        }
    }

    public void setContent(Iterable iterable) {
        if (iterable != null) {
            Object var4_4;
            Iterator iterator = iterable.iterator();
            boolean bl2 = !iterator.hasNext();
            Object v0 = var4_4 = bl2 ? null : iterator.next();
            if (this.aDe != null) {
                this.aDe.setContentProperty(this.ed, this.ee);
                this.aDe.setContent(iterable);
                if (!bl2 && this.aDe.getSelectedValue() == null) {
                    this.aDe.setSelectedValue(var4_4);
                }
            }
            Object var5_5 = null;
            int n2 = -1;
            if (!bl2) {
                var5_5 = var4_4;
                n2 = 0;
            }
            this.setRenderableContent(var5_5, n2);
        }
    }

    public void setContent(Object[] objectArray) {
        if (objectArray != null) {
            if (this.aDe != null) {
                this.aDe.setContentProperty(this.ed, this.ee);
                this.aDe.setContent(objectArray);
                if (objectArray.length > 0 && this.aDe.getSelectedValue() == null) {
                    this.aDe.setSelectedValue(objectArray[0]);
                }
            }
            Object object = null;
            int n2 = -1;
            if (objectArray.length > 0) {
                object = objectArray[0];
                n2 = 0;
            }
            this.setRenderableContent(object, n2);
        }
    }

    public void setHotSpotPosition(BT bT) {
        if (bT != null) {
            this.cF = bT;
        }
    }

    public void setAlign(BT bT) {
        if (bT != null) {
            this.cG = bT;
        }
    }

    public void yx() {
        super.yx();
        this.setFocusable(true);
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        this.aDf.setEnabled(bl2);
    }

    public void Fn() {
        this.aDd = new sz_1(this);
        this.a(qe_1.bFB, this.aDd, false);
    }

    public void a(ago_2 ago_22) {
        this.aDb = new ta_1(this);
        ago_22.a(qe_1.bFz, this.aDb, false);
        this.aDc = new sx_1(this);
        ago_22.a(qe_1.bFA, this.aDc, false);
    }

    public void a(rf_0 rf_02) {
        rf_02.a(qe_1.bFp, new sy_2(this), false);
    }

    private void Fo() {
        if (this.aCZ) {
            this.Fp();
        } else {
            this.Fq();
        }
    }

    private void Fp() {
        if (this.aCZ) {
            this.aDe.aaa();
            this.aCZ = false;
            aek.atD().atG();
        }
    }

    private void Fq() {
        if (!this.aCZ) {
            agj_1 agj_12 = this.aDe.getIdealSize(this.ajR, -1);
            int n2 = agj_12.height;
            int n3 = this.getDisplayY();
            ago_2 ago_22 = ago_2.getInstance();
            BT bT = this.cG;
            BT bT2 = this.cF;
            int n4 = this.getDisplayY() + bT.eM(this.getHeight()) - bT2.eM(n2);
            if (n4 < 0 || n4 > ago_22.getAppearance().getContentHeight() - n2) {
                bT = bT.IM();
                bT2 = bT2.IM();
            }
            n4 = this.getDisplayY() + bT.eM(this.getHeight()) - bT2.eM(n2);
            n4 = Math.max(0, Math.min(n4, ago_22.getAppearance().getContentHeight() - n2));
            if (n3 - n2 < 0 && n3 + this.getHeight() + n2 > ago_22.getHeight()) {
                n2 = n3;
                n4 = 0;
            }
            this.aDe.setSizeToPrefSize();
            int n5 = Math.max(this.aDe.getWidth(), this.getWidth());
            this.aDe.setSize(n5, n2);
            this.aDe.setX(this.getDisplayX());
            this.aDe.setY(n4);
            this.aDe.setNonBlocking(false);
            ago_22.getLayeredContainer().a(this.aDe, 30000);
            this.aCZ = true;
            this.aDa = true;
            aek.atD().atF();
        }
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return zb instanceof bc_2;
    }

    public void j() {
        super.j();
        ago_2.getInstance().b(qe_1.bFz, this.aDb, false);
        ago_2.getInstance().b(qe_1.bFA, this.aDc, false);
        this.aDc = null;
        this.aDb = null;
        this.aDd = null;
        this.cG = null;
        this.cF = null;
        this.aDe.aab();
    }

    public void b() {
        super.b();
        alb_2 alb_22 = new alb_2(this, null);
        alb_22.b();
        this.a(alb_22);
        bc_2 bc_22 = new bc_2();
        bc_22.b();
        bc_22.setWidget(this);
        this.a(bc_22);
        this.aDf = new aqq_0();
        this.aDf.b();
        this.a(this.aDf);
        this.aDe = new rf_0();
        this.aDe.b();
        this.vc = new qa_1();
        this.vc.b();
        this.dyc = false;
        this.Fn();
        this.a(ago_2.getInstance());
    }

    public void a(air_1 air_12) {
        yE yE2 = (yE)air_12;
        super.a((air_1)yE2);
        adg_2 adg_22 = (adg_2)this.aDe.aah();
        adg_22.dyg = false;
        adg_22.aad();
        yE2.a(adg_22);
        yE2.b(qe_1.bFz, this.aDb, false);
        yE2.b(qe_1.bFA, this.aDc, false);
        yE2.b(qe_1.bFB, this.aDd, false);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ajW) {
            this.setMaxRows(Gr.R(string));
        } else if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else if (n2 == cK) {
            this.setHotSpotPosition(BT.dv(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ajW) {
            this.setMaxRows(Gr.R(object));
            return true;
        } else if (n2 == cJ) {
            this.setAlign((BT)((Object)object));
            return true;
        } else if (n2 == cK) {
            this.setHotSpotPosition((BT)((Object)object));
            return true;
        } else if (n2 == ei) {
            if (object == null || object.getClass().isArray()) {
                this.setContent((Object[])object);
                return true;
            } else {
                if (!(object instanceof Iterable)) return false;
                this.setContent((Iterable)object);
            }
            return true;
        } else {
            if (n2 != el) return super.setPropertyAttribute(n2, object);
            this.setSelectedValue(object);
        }
        return true;
    }

    static /* synthetic */ qa_1 a(yE yE2) {
        return yE2.vc;
    }

    static /* synthetic */ aqq_0 b(yE yE2) {
        return yE2.aDf;
    }

    static /* synthetic */ void c(yE yE2) {
        yE2.Fo();
    }

    static /* synthetic */ boolean d(yE yE2) {
        return yE2.aCZ;
    }

    static /* synthetic */ rf_0 e(yE yE2) {
        return yE2.aDe;
    }

    static /* synthetic */ void f(yE yE2) {
        yE2.Fp();
    }

    static /* synthetic */ boolean g(yE yE2) {
        return yE2.aDa;
    }

    static /* synthetic */ boolean a(yE yE2, boolean bl2) {
        yE2.aDa = bl2;
        return yE2.aDa;
    }
}

