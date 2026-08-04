/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aCB
 */
public class acb_1
extends abS
implements dE {
    private static final Logger a = Logger.getLogger(acb_1.class);
    public static final String TAG = "smiley";
    public static final String duv = "animatedElementViewer";
    private String duw;
    private boolean aAB;
    private boolean dux = true;
    public static int aAz = Integer.MIN_VALUE;
    private lj_1 duy;
    private float Gx;

    public void ao(String string, String string2) {
        this.h(string, string2, true);
    }

    public void h(String string, String string2, boolean bl2) {
        this.duw = string;
        aji_1 aji_12 = add_1.aOG().azj().lh(this.duw);
        assert (aji_12 != null) : "Impossible de charger un smiley";
        this.duy = (lj_1)aji_12.R(duv);
        if (this.duy == null) {
            throw new Exception("On a charg\u00e9 un Widget de Smiley, mais il n'a pas de widget d'animatedElementViewer...");
        }
        this.aAB = bl2;
        this.setAnimation(string2);
        this.Gx = this.duy.getAnimatedElement().getScale();
    }

    public void setAnimation(String string) {
        this.duy.setAnimName(string);
    }

    public void setTarget(gq_2 gq_22) {
        this.setTarget((ahh_1)gq_22);
    }

    public void setTarget(ahh_1 ahh_12) {
        if (ahh_12 == this.q) {
            return;
        }
        if (this.q != null && this.q instanceof ahh_1) {
            ((ahh_1)this.q).b(this);
        }
        super.setTarget(ahh_12);
        if (ahh_12 != null) {
            ahh_12.a(this);
            this.setTargetIsVisible(ahh_12.isVisible());
        }
    }

    public void qa() {
        if (this.aAB) {
            this.EN();
        } else {
            this.setSmileyIsVisible(false);
        }
    }

    public void setSmileyIsVisible(boolean bl2) {
        if (this.dux == bl2) {
            return;
        }
        this.dux = bl2;
        this.EK();
    }

    protected void EK() {
        this.setVisible(this.ciK && this.dux);
    }

    public final void invalidate() {
        super.invalidate();
    }

    public adz_1 getComputedPosition(int n2, int n3, int n4) {
        adz_1 adz_12 = super.getComputedPosition(n2, n3, n4);
        int n5 = adz_12.getX();
        int n6 = adz_12.getY();
        adz_12.set(n5, n6);
        return adz_12;
    }

    public void a(aFy aFy2, int n2, int n3, int n4) {
        this.EK();
        super.a(aFy2, n2, n3, 0);
    }

    protected void j(int n2, float f) {
        super.j(n2, f);
        ahh_1 ahh_12 = this.duy.getAnimatedElement();
        float f2 = f - 0.5f + this.Gx;
        this.duy.setScale(f2);
        float f3 = 1.0f;
        long l2 = this.getWatcherContainerAdviser().QI();
        int n3 = this.getDuration();
        if (l2 < (long)(n3 / 4)) {
            f3 = (float)l2 / ((float)n3 / 4.0f);
        } else if (l2 > (long)(n3 * 3 / 4)) {
            f3 = 1.0f - ((float)l2 - (float)(3 * n3) / 4.0f) / ((float)n3 - (float)(3 * n3) / 4.0f);
        }
        ahh_12.W(f3);
        ahh_12.E(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
    }

    private static float l(float f, float f2, float f3, float f4) {
        f = f / f4 - 1.0f;
        return -f3 * (f * f * f * f - 1.0f) + f2;
    }

    public void setDuration(int n2) {
        this.ciO.setDuration(n2);
    }

    public void EL() {
        this.ciO.fW(0);
        this.EK();
    }

    public lj_1 getAnimatedElementViewer() {
        return this.duy;
    }

    public int getAdviserId() {
        return this.ciO.getId();
    }

    public String getWidgetId() {
        return this.duw;
    }

    public void EN() {
        add_1.aOG().kO(this.duw);
    }

    public void a(boolean bl2, ns_1 ns_12) {
        this.setTargetIsVisible(bl2);
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

    public int getDuration() {
        return this.ciO.getDuration();
    }

    public void EO() {
        super.EO();
        if (this.q != null && this.q instanceof ahh_1) {
            ((ahh_1)this.q).b(this);
        }
    }
}

