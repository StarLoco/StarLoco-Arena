/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Insets;
import java.util.ArrayList;

/*
 * Renamed from Ug
 */
public class ug_0
extends aNZ {
    protected Insets bPw;
    protected Insets bPx;
    protected Insets bPy;
    protected kx_1 aFh;
    protected agj_1 bPz = null;
    protected boolean apf = false;
    protected boolean bPA = false;
    protected adg_2 DD;
    public static final int bPB = "margin".hashCode();
    public static final int bPC = "border".hashCode();
    public static final int bPD = "padding".hashCode();
    public static final int bPE = "shape".hashCode();

    public ug_0() {
    }

    public ug_0(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public void a(na_1 na_12) {
        if (na_12 instanceof awh_0) {
            na_12.a(qe_1.bFI, new yh_2(this), false);
            this.setSpacing((awh_0)na_12);
        }
        super.a(na_12);
    }

    public void setSpacing(awh_0 awh_02) {
        if (awh_02 instanceof awc_0) {
            this.setMargin(awh_02.getInsets());
        } else if (awh_02 instanceof ase_0) {
            this.setPadding(awh_02.getInsets());
        } else if (awh_02 instanceof n_0) {
            this.setBorder(awh_02.getInsets());
        }
    }

    public Insets getBorder() {
        return this.bPx;
    }

    public void setBorder(Insets insets) {
        this.bPx.bottom = insets.bottom;
        this.bPx.top = insets.top;
        this.bPx.left = insets.left;
        this.bPx.right = insets.right;
        this.apf = true;
        this.setNeedsToPreProcess();
    }

    public Insets getMargin() {
        return this.bPw;
    }

    public void setMargin(Insets insets) {
        this.bPw.bottom = insets.bottom;
        this.bPw.top = insets.top;
        this.bPw.left = insets.left;
        this.bPw.right = insets.right;
        this.apf = true;
        this.setNeedsToPreProcess();
    }

    public Insets getPadding() {
        return this.bPy;
    }

    public void setPadding(Insets insets) {
        this.bPy.bottom = insets.bottom;
        this.bPy.top = insets.top;
        this.bPy.left = insets.left;
        this.bPy.right = insets.right;
        this.apf = true;
        this.setNeedsToPreProcess();
    }

    public adg_2 getWidget() {
        return this.DD;
    }

    public void setWidget(adg_2 adg_22) {
        this.DD = adg_22;
        this.bPA = true;
        this.setNeedsToPreProcess();
        this.setNeedsToPostProcess();
        this.apf = true;
    }

    public void setShape(kx_1 kx_12) {
        this.aFh = kx_12;
    }

    public kx_1 getShape() {
        return this.aFh;
    }

    public Insets getTotalInsets() {
        return new Insets(this.bPy.top + this.bPx.top + this.bPw.top, this.bPy.left + this.bPx.left + this.bPw.left, this.bPy.bottom + this.bPx.bottom + this.bPw.bottom, this.bPy.right + this.bPx.right + this.bPw.right);
    }

    public int getTopInset() {
        return this.bPy.top + this.bPx.top + this.bPw.top;
    }

    public int getBottomInset() {
        return this.bPy.bottom + this.bPx.bottom + this.bPw.bottom;
    }

    public int getLeftInset() {
        return this.bPy.left + this.bPx.left + this.bPw.left;
    }

    public int getRightInset() {
        return this.bPy.right + this.bPx.right + this.bPw.right;
    }

    private void agE() {
        this.bPz = new agj_1(this.DD.aLd.width - this.bPw.left - this.bPw.right - this.bPy.left - this.bPy.right - this.bPx.left - this.bPx.right, this.DD.aLd.height - this.bPw.top - this.bPw.bottom - this.bPy.top - this.bPy.bottom - this.bPx.top - this.bPx.bottom);
    }

    public agj_1 getContentSize() {
        if (this.bPz == null) {
            this.agE();
        }
        return this.bPz;
    }

    public int getContentWidth() {
        if (this.bPz == null) {
            this.agE();
        }
        return this.bPz.width;
    }

    public int getContentHeight() {
        if (this.bPz == null) {
            this.agE();
        }
        return this.bPz.height;
    }

    public void invalidate() {
        super.invalidate();
        this.bPz = null;
    }

    public boolean aY(int n2, int n3) {
        return this.aFh.g(n2 - this.bPw.left, n3 - this.bPw.bottom, this.DD.aLd.width - this.bPw.left - this.bPw.right, this.DD.aLd.height - this.bPw.bottom - this.bPw.top);
    }

    public int getOnScreenX(int n2, int n3) {
        return this.aFh.h(n2, n3, this.DD.aLd.width - this.bPw.left - this.bPw.right, this.DD.aLd.height - this.bPw.bottom - this.bPw.top);
    }

    public int getOnScreenY(int n2, int n3) {
        return this.aFh.i(n2, n3, this.DD.aLd.width - this.bPw.left - this.bPw.right, this.DD.aLd.height - this.bPw.bottom - this.bPw.top);
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.apf) {
            this.apf = false;
            this.invalidate();
            if (this.DD != null && this.DD instanceof aht_1) {
                ArrayList arrayList = ((aht_1)this.DD).getWidgetChildren();
                for (int j = arrayList.size() - 1; j >= 0; --j) {
                    ((adg_2)arrayList.get(j)).setNeedsToPostProcess();
                }
            }
        }
        return bl2;
    }

    public boolean cb(int n2) {
        this.bPA = false;
        return super.cb(n2);
    }

    public void j() {
        super.j();
        this.bPw = null;
        this.bPx = null;
        this.bPy = null;
        this.aFh = null;
        this.DD = null;
        this.bPz = null;
    }

    public void b() {
        super.b();
        this.bPw = new Insets(0, 0, 0, 0);
        this.bPx = new Insets(0, 0, 0, 0);
        this.bPy = new Insets(0, 0, 0, 0);
        this.aFh = kx_1.FR;
        this.apf = false;
        this.bPA = false;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ((ug_0)air_12).setShape(this.aFh);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == bPE) {
            this.setShape(kx_1.aP(string));
        } else if (n2 == bPC) {
            this.setBorder(if_12.eN(string));
        } else if (n2 == bPB) {
            this.setMargin(if_12.eN(string));
        } else if (n2 == bPD) {
            this.setPadding(if_12.eN(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == bPE) {
            this.setShape((kx_1)((Object)object));
        } else if (n2 == bPC) {
            this.setBorder((Insets)object);
        } else if (n2 == bPB) {
            this.setMargin((Insets)object);
        } else if (n2 == bPD) {
            this.setPadding((Insets)object);
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

