/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBuffer;
import com.ankamagames.framework.graphics.engine.fx.FixedPipeline.Water;
import javax.media.opengl.GL;

/*
 * Renamed from uL
 */
public class ul_1 {
    private VertexBuffer fH;
    private ams_1 az;
    private float aqQ = 30.0f;
    private float fG;
    final /* synthetic */ Water fI;

    public ul_1(Water water) {
        float f;
        float f2;
        this.fI = water;
        yb_1 yb_12 = new yb_1();
        yb_12.a(new agt_2(1, 3));
        yb_12.a(new agt_2(4, 4));
        yb_12.a(new agt_2(8, 2));
        int n2 = (int)(this.aqQ * this.aqQ);
        this.fH = new VertexBuffer(n2, yb_12);
        this.az = new ams_1(n2 * 6);
        int n3 = 0;
        for (f2 = 0.0f; f2 < this.aqQ; f2 += 1.0f) {
            for (f = 0.0f; f < this.aqQ; f += 1.0f) {
                this.fH.a(n3, 1, f2, f, -0.05f);
                this.fH.a(n3, 4, 1.0f, 1.0f, 1.0f, 1.0f);
                this.fH.a(n3, 8, f2 * 2.0f / this.aqQ, f * 2.0f / this.aqQ);
                ++n3;
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

    public void a(float f) {
        this.fG += f;
        float f2 = 1.0f / this.aqQ;
        int n2 = 0;
        for (float f3 = -this.aqQ / 2.0f; f3 < this.aqQ / 2.0f; f3 += 1.0f) {
            for (float f4 = -this.aqQ / 2.0f; f4 < this.aqQ / 2.0f; f4 += 1.0f) {
                float f5 = (0.5f * f3 + 0.5f * f4 + this.fG * 0.001f) * 0.6f;
                float f6 = (0.8f * f3 + 0.2f * f4 + this.fG * 0.001f) * 0.6f;
                float f7 = -1.5f + 3.5f * (ej_0.k(f5) + Math.abs(ej_0.l(f6)));
                this.fH.a(n2, 4, Water.a(this.fI), Water.b(this.fI), Water.c(this.fI), 1.0f);
                this.fH.a(n2, 1, f4 * f2, f3 * f2, f7 * f2);
                ++n2;
            }
        }
    }

    public void a(db_2 db_22) {
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        ef_1 ef_12 = cx_0.JY().bt(-1296775008915292156L);
        ef_12.f(db_22);
        vo_1 vo_12 = vo_1.aik();
        vo_12.a(air.cya, air.cxZ);
        vo_12.cr(false);
        vo_12.n(db_22);
        qp_22.adV.nO(13);
        int n2 = this.fH.fr();
        gL.glVertexPointer(3, 5126, n2, this.fH.V(1));
        gL.glColorPointer(4, 5126, n2, this.fH.V(4));
        gL.glTexCoordPointer(2, 5126, n2, this.fH.V(8));
        gL.glDrawElements(4, this.az.aWY(), 5123, this.az.aWZ());
        vo_12.cr(true);
        vo_12.n(db_22);
    }
}

