/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

public class abS
extends aht_1
implements fj_0,
lk {
    public static final String TAG = "WatcherContainer";
    protected aFy q;
    protected boolean ciK = true;
    private BT cG = null;
    private int bsW = 0;
    private int bsX = 0;
    private int ciL = 0;
    private int ciM = 0;
    private boolean ciN = true;
    protected gg_2 ciO;
    public static final int cJ = "align".hashCode();
    public static final int ciP = "target".hashCode();
    public static final int ciQ = "useTargetPositionning".hashCode();
    public static final int ciR = "xOffset".hashCode();
    public static final int ciS = "yOffset".hashCode();

    public gg_2 getWatcherContainerAdviser() {
        return this.ciO;
    }

    protected void j(int n2, float f) {
    }

    public int pZ() {
        return wj_2.Df().a(this.ciO);
    }

    public void qa() {
    }

    public double getWorldX() {
        if (this.q != null) {
            return this.q.getWorldX();
        }
        return 0.0;
    }

    public double getWorldY() {
        if (this.q != null) {
            return this.q.getWorldY();
        }
        return 0.0;
    }

    public double getAltitude() {
        if (this.q != null) {
            return this.q.getAltitude();
        }
        return 0.0;
    }

    public String getTag() {
        return TAG;
    }

    public BT getAlign() {
        return this.cG;
    }

    public void setAlign(BT bT) {
        this.cG = bT;
    }

    public aFy getTarget() {
        return this.q;
    }

    public void setTarget(aFy aFy2) {
        if (this.q != aFy2) {
            if (this.q != null) {
                this.q.b(this);
            }
            this.q = aFy2;
            if (this.q != null) {
                this.q.a(this);
            }
        }
        if (this.q != null) {
            this.a(null, this.q.getScreenX(), this.q.getScreenY(), this.q.hB());
        }
    }

    public void setTarget(aFy aFy2, int n2, int n3) {
        this.bsW = n2;
        this.bsX = n3;
        if (this.q != null && this.q != aFy2) {
            this.q.b(this);
        }
        this.q = aFy2;
        if (this.q != null) {
            this.q.a(this);
            this.a(null, this.q.getScreenX(), this.q.getScreenY(), this.q.hB());
        }
    }

    public boolean isUseTargetPositionning() {
        return this.ciN;
    }

    public void setUseTargetPositionning(boolean bl2) {
        this.ciN = bl2;
    }

    public int getXOffset() {
        return this.bsW;
    }

    public void setXOffset(int n2) {
        this.bsW = n2;
        if (this.q != null) {
            this.a(null, this.q.getScreenX(), this.q.getScreenY(), this.q.hB());
        }
    }

    public int getYOffset() {
        return this.bsX;
    }

    public void setYOffset(int n2) {
        this.bsX = n2;
        if (this.q != null) {
            this.a(null, this.q.getScreenX(), this.q.getScreenY(), this.q.hB());
        }
    }

    public void setOffset(int n2, int n3) {
        this.bsW = n2;
        this.bsX = n3;
        if (this.q != null && this.q.hC()) {
            this.a(null, this.q.getScreenX(), this.q.getScreenY(), this.q.hB());
        }
    }

    public void setPosition(int n2, int n3) {
        if (!this.ciN) {
            super.setPosition(n2, n3);
        }
    }

    public void setPosition(int n2, int n3, boolean bl2) {
        if (!this.ciN) {
            super.setPosition(n2, n3, bl2);
        }
    }

    public void setPosition(Point point) {
        if (!this.ciN) {
            super.setPosition(point);
        }
    }

    public void setX(int n2) {
        if (!this.ciN) {
            super.setX(n2);
        }
    }

    public void setY(int n2) {
        if (!this.ciN) {
            super.setY(n2);
        }
    }

    public void setTargetIsVisible(boolean bl2) {
        this.ciK = bl2;
    }

    protected void EK() {
        this.setVisible(this.ciK);
    }

    public void EO() {
        super.EO();
        if (this.q != null) {
            this.q.b(this);
        }
    }

    public void yx() {
        super.yx();
        ago_2 ago_22 = ago_2.getInstance();
        this.ciM = ago_22.getHeight() / 2;
        this.ciL = ago_22.getWidth() / 2;
    }

    public void a(aFy aFy2, int n2, int n3, int n4) {
        if (this.ciN) {
            adz_1 adz_12 = this.getComputedPosition(n2, n3, n4);
            super.setPosition(adz_12.getX(), adz_12.getY(), false);
        }
    }

    public final int getHalfDisplayWidth() {
        return this.ciL;
    }

    public final int getHalfDisplayHeight() {
        return this.ciM;
    }

    public adz_1 getComputedPosition(int n2, int n3, int n4) {
        return new adz_1(n2 + this.ciL + (int)((float)this.bsW * this.ciO.QJ()) - this.cG.eL(this.getWidth()), n3 + this.ciM + (int)((float)this.bsX * this.ciO.QJ()) + this.cG.eM(n4));
    }

    public void validate() {
        super.validate();
        if (this.q != null) {
            this.a(null, this.q.getScreenX(), this.q.getScreenY(), this.q.hB());
        }
    }

    public void j() {
        super.j();
        wj_2.Df().b(this.ciO);
        this.cG = null;
        this.q = null;
        this.ciO = null;
    }

    public void b() {
        super.b();
        this.setVisible(true);
        this.ciN = true;
        this.cG = BT.aJU;
        this.ciO = new gg_2(this, null);
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        abS abS2 = (abS)air_12;
        abS2.setUseTargetPositionning(this.isUseTargetPositionning());
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else if (n2 == ciQ) {
            this.setUseTargetPositionning(Gr.getBoolean(string));
        } else if (n2 == ciR) {
            this.setXOffset(Gr.R(string));
        } else if (n2 == ciS) {
            this.setYOffset(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cJ) {
            this.setAlign((BT)((Object)object));
        } else if (n2 == ciQ) {
            this.setUseTargetPositionning(Gr.getBoolean(object));
        } else if (n2 == ciR) {
            this.setXOffset(Gr.R(object));
        } else if (n2 == ciS) {
            this.setYOffset(Gr.R(object));
        } else if (n2 == ciP) {
            this.setTarget((aFy)object);
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ int a(abS abS2, int n2) {
        abS2.bsW = n2;
        return abS2.bsW;
    }

    static /* synthetic */ int b(abS abS2, int n2) {
        abS2.bsX = n2;
        return abS2.bsX;
    }
}

