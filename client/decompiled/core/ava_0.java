/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUtessellator;

/*
 * Renamed from ava
 */
public abstract class ava_0
implements up_1 {
    private ady_2 cYq = null;
    protected final float[][] cYr;
    public static final float[] cYs = new float[]{1.0f, 1.0f, 1.0f, 0.7f};
    private float[] cYt = cYs;
    private float fh = 1.5f;
    public static final float[] cYu = new float[]{0.06f, 0.04f, 0.03f, 0.4f};
    private float[] cYv = cYu;

    protected ava_0() {
        assert (this.wZ() != null) : "Le tableau de vertex ne devrait pas \u00eatre null";
        assert (this.xa() != null) : "Le tableau de vertex ne devrait pas \u00eatre null";
        assert (this.wZ().length == this.xa().length) : "Les tableaux de vertex doivent avoir la m\u00eame taille";
        this.cYr = new float[this.wZ().length][];
        for (int j = 0; j < this.wZ().length; ++j) {
            assert (this.wZ()[j].length == 2) : "Le tableau de vertex doit avoir 2 coordonn\u00e9es";
            assert (this.xa()[j].length == 2) : "Le tableau de vertex doit avoir 2 coordonn\u00e9es";
            this.cYr[j] = new float[2];
        }
    }

    public void n(float f, float f2, float f3, float f4) {
        this.cYt = new float[]{f, f2, f3, f4};
    }

    public float[] aIa() {
        return this.cYt;
    }

    public float getBorderWidth() {
        return this.fh;
    }

    public void setBorderWidth(float f) {
        this.fh = f;
    }

    public void b(float f, float f2, float f3, float f4) {
        this.cYv = new float[]{f, f2, f3, f4};
    }

    public float[] aIb() {
        return this.cYv;
    }

    public void a(GL gL, float f, float f2, float f3, float f4) {
        db_2 db_22 = arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        vo_12.cu(false);
        this.P(f3, f4);
        vo_12.a(jq_0.bmI);
        vo_12.n(db_22);
        gL.glPushMatrix();
        gL.glTranslatef(f, f2, 0.0f);
        GLU gLU = bx_2.cQ();
        if (this.cYq == null) {
            this.cYq = new ady_2(this, gL, gLU);
        }
        GLUtessellator gLUtessellator = gLU.gluNewTess();
        gLU.gluTessCallback(gLUtessellator, 100101, this.cYq);
        gLU.gluTessCallback(gLUtessellator, 100100, this.cYq);
        gLU.gluTessCallback(gLUtessellator, 100102, this.cYq);
        gLU.gluTessCallback(gLUtessellator, 100103, this.cYq);
        gL.glColor4fv(this.cYt, 0);
        gL.glShadeModel(7425);
        gLU.gluTessProperty(gLUtessellator, 100140, 100132.0);
        gLU.gluTessBeginPolygon(gLUtessellator, null);
        gLU.gluTessBeginContour(gLUtessellator);
        for (float[] fArray : this.cYr) {
            double[] dArray = new double[]{fArray[0], fArray[1], 0.0};
            gLU.gluTessVertex(gLUtessellator, dArray, 0, dArray);
        }
        gLU.gluTessEndContour(gLUtessellator);
        gLU.gluTessEndPolygon(gLUtessellator);
        gL.glEnable(2848);
        gL.glLineWidth(this.fh);
        gL.glColor4fv(this.cYv, 0);
        gL.glBegin(2);
        for (float[] fArray : this.cYr) {
            gL.glVertex2fv(fArray, 0);
        }
        gL.glEnd();
        gL.glPopMatrix();
        vo_12.cu(true);
        vo_12.n(db_22);
    }

    protected void P(float f, float f2) {
        for (int j = 0; j < this.cYr.length; ++j) {
            this.cYr[j][0] = this.xa()[j][0] * f + this.wZ()[j][0];
            this.cYr[j][1] = this.xa()[j][1] * f2 + this.wZ()[j][1];
        }
    }
}

