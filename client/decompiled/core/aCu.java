/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBuffer;
import com.ankamagames.framework.graphics.engine.fx.FixedPipeline.Water;
import javax.media.opengl.GL;

public class aCu {
    private VertexBuffer fH;
    private ams_1 az;
    private float aqQ;
    private float fG;
    private ef_1 tl;
    private air tK;
    private air tL;
    private float duo;
    private float dup;
    private float cSR;
    private float cSS;
    final /* synthetic */ Water fI;

    public aCu(Water water, int n2) {
        float f;
        float f2;
        this.fI = water;
        this.aqQ = n2;
        yb_1 yb_12 = new yb_1();
        yb_12.a(new agt_2(1, 3));
        yb_12.a(new agt_2(4, 4));
        yb_12.a(new agt_2(8, 2));
        int n3 = (int)(this.aqQ * this.aqQ);
        this.fH = new VertexBuffer(n3, yb_12);
        this.az = new ams_1(n3 * 6);
        float f3 = 1.0f / this.aqQ;
        int n4 = 0;
        for (f2 = -this.aqQ / 2.0f; f2 < this.aqQ / 2.0f; f2 += 1.0f) {
            for (f = -this.aqQ / 2.0f; f < this.aqQ / 2.0f; f += 1.0f) {
                this.fH.a(n4, 1, f * f3, f2 * f3, -0.15f);
                this.fH.a(n4, 4, 1.0f, 1.0f, 1.0f, 1.0f);
                this.fH.a(n4, 8, f * 2.0f / this.aqQ, f2 * 2.0f / this.aqQ);
                ++n4;
            }
        }
        for (f2 = 0.0f; f2 < this.aqQ - 1.0f; f2 += 1.0f) {
            for (f = 0.0f; f < this.aqQ - 1.0f; f += 1.0f) {
                this.az.add((int)(f2 + f * this.aqQ));
                this.az.add((int)(f2 + 1.0f + f * this.aqQ));
                this.az.add((int)(f2 + 1.0f + (f + 1.0f) * this.aqQ));
                this.az.add((int)(f2 + f * this.aqQ));
                this.az.add((int)(f2 + 1.0f + (f + 1.0f) * this.aqQ));
                this.az.add((int)(f2 + (f + 1.0f) * this.aqQ));
            }
        }
    }

    public void setTexture(ef_1 ef_12) {
        this.tl = ef_12;
    }

    public void c(air air2, air air3) {
        this.tK = air2;
        this.tL = air3;
    }

    public void S(float f, float f2) {
        this.duo = f;
        this.dup = f2;
    }

    public void a(float f) {
        this.fG += f;
        float f2 = f * 0.001f;
        float f3 = this.fG * 0.001f;
        this.cSR += this.duo * f2;
        this.cSS += this.dup * f2;
        int n2 = 0;
        for (float f4 = -this.aqQ / 2.0f; f4 < this.aqQ / 2.0f; f4 += 1.0f) {
            for (float f5 = -this.aqQ / 2.0f; f5 < this.aqQ / 2.0f; f5 += 1.0f) {
                float f6 = (0.5f * f4 + 0.5f * f5 + f3) * 0.6f;
                float f7 = (0.8f * f4 + 0.2f * f5 + f3) * 0.6f;
                this.fH.a(n2, 4, Water.a(this.fI), Water.b(this.fI), Water.c(this.fI), 1.0f);
                this.fH.a(n2, 8, f5 * 1.5f + 0.2f * ej_0.k(f6) + this.cSR, f4 * 1.5f + 0.2f * ej_0.k(f7) + this.cSS);
                ++n2;
            }
        }
    }

    public void a(db_2 db_22) {
        if (this.tl != null) {
            this.tl.f(db_22);
        }
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        vo_1.aik().a(this.tK, this.tL);
        vo_1.aik().n(db_22);
        qp_22.adV.nO(13);
        int n2 = this.fH.fr();
        gL.glVertexPointer(3, 5126, n2, this.fH.V(1));
        gL.glColorPointer(4, 5126, n2, this.fH.V(4));
        gL.glTexCoordPointer(2, 5126, n2, this.fH.V(8));
        gL.glDrawElements(4, this.az.aWY(), 5123, this.az.aWZ());
    }
}

