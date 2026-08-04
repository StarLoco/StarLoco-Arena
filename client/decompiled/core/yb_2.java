/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/*
 * Renamed from Yb
 */
public class yb_2
implements aoy_1 {
    public static final int cad = 1000;
    public static final int cae = 1000;
    private MO caf;
    private int IP;
    private int cag;
    private int cah = 1000;
    private float[] cai;
    private float[] caj;
    private float[] aaV;
    private EntitySprite cak;
    private final ArrayList G = new ArrayList();
    private final ArrayList jG = new ArrayList();
    private static final yb_2 cal = new yb_2();

    private yb_2() {
        this.caj = new float[4];
        this.cai = new float[4];
        this.aaV = new float[4];
        this.caj[3] = 0.0f;
        this.cai[3] = 1.0f;
        this.IP = 0;
        this.cag = 1000;
        this.caf = MO.byh;
        this.cak = new EntitySprite();
        this.cak.a(new GLGeometrySprite());
        this.cak.setColor(this.cai[0], this.cai[1], this.cai[2], this.cai[3]);
        this.cak.setTexture(null);
        this.cak.setVisible(false);
        this.cak.Hu().a(air.cyd, air.cye);
    }

    public static yb_2 amk() {
        return cal;
    }

    public boolean aml() {
        return this.caf == MO.bye;
    }

    public boolean amm() {
        return this.caf == MO.byg;
    }

    public boolean amn() {
        return this.caf == MO.byh;
    }

    private void b(int n2, vP vP2) {
        this.caj[0] = this.aaV[0];
        this.caj[1] = this.aaV[1];
        this.caj[2] = this.aaV[2];
        this.caj[3] = this.aaV[3];
        this.p(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        this.cag = n2;
        this.IP = 0;
    }

    public void jb(int n2) {
        this.cah = n2;
    }

    public final void jc(int n2) {
        this.b(n2, vP.atM);
        this.a(MO.bye);
    }

    public final void jd(int n2) {
        this.b(n2, vP.atH);
        this.a(MO.byf);
    }

    public final void p(float f, float f2, float f3, float f4) {
        this.cai[0] = f;
        this.cai[1] = f2;
        this.cai[2] = f3;
        this.cai[3] = f4;
    }

    public final void bI(int n2) {
        if (this.amm()) {
            for (int j = this.jG.size() - 1; j >= 0; --j) {
                if (((xy)this.jG.get(j)).isValid()) continue;
                return;
            }
            this.jd(this.cah);
            return;
        }
        if (this.caf == MO.byh) {
            return;
        }
        this.IP += n2;
        if (this.IP > this.cag) {
            this.IP = this.cag;
            this.aaV[0] = this.cai[0];
            this.aaV[1] = this.cai[1];
            this.aaV[2] = this.cai[2];
            this.aaV[3] = this.cai[3];
            if (this.caf == MO.bye) {
                this.a(MO.byg);
            } else {
                this.a(MO.byh);
            }
        } else {
            float f = (float)this.IP / (float)this.cag;
            this.aaV[0] = ej_0.a(this.caj[0], this.cai[0], f);
            this.aaV[1] = ej_0.a(this.caj[1], this.cai[1], f);
            this.aaV[2] = ej_0.a(this.caj[2], this.cai[2], f);
            this.aaV[3] = ej_0.a(this.caj[3], this.cai[3], f);
        }
        this.cak.setColor(this.aaV[0], this.aaV[1], this.aaV[2], this.aaV[3]);
        this.cak.setVisible(this.aaV[3] > 0.004f);
    }

    public void init(GLAutoDrawable gLAutoDrawable) {
    }

    public void P(int n2, int n3) {
        this.cak.setSize(n2, n3);
        this.cak.x(n3 / 2, -n2 / 2);
    }

    public void i(GL gL) {
    }

    public void h(GL gL) {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        qp_22.c(Matrix44.bEn);
        this.cak.a(qp_22);
    }

    public final MO amo() {
        return this.caf;
    }

    public void a(NE nE) {
        this.G.add(nE);
    }

    public void b(NE nE) {
        this.G.remove(nE);
    }

    public void a(xy xy2) {
        this.jG.add(xy2);
    }

    public void b(xy xy2) {
        this.jG.remove(xy2);
    }

    private void a(MO mO) {
        if (this.caf == mO) {
            return;
        }
        this.caf = mO;
        switch (this.caf) {
            case bye: {
                for (int j = this.G.size() - 1; j >= 0; --j) {
                    ((NE)this.G.get(j)).iI();
                }
                break;
            }
            case byf: {
                for (int j = this.G.size() - 1; j >= 0; --j) {
                    ((NE)this.G.get(j)).iJ();
                }
                break;
            }
            case byg: {
                for (int j = this.G.size() - 1; j >= 0; --j) {
                    ((NE)this.G.get(j)).iG();
                }
                break;
            }
            case byh: {
                for (int j = this.G.size() - 1; j >= 0; --j) {
                    ((NE)this.G.get(j)).iH();
                }
                break;
            }
        }
    }
}

