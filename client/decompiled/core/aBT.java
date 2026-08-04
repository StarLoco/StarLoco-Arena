/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBuffer;
import com.ankamagames.framework.graphics.engine.fx.FixedPipeline.Water;
import javax.media.opengl.GL;

public class aBT {
    private float fG;
    private VertexBuffer fH;
    private ams_1 az;
    private ef_1 tl;
    private air tK;
    private air tL;
    final /* synthetic */ Water fI;

    public aBT(Water water) {
        this.fI = water;
        yb_1 yb_12 = new yb_1();
        yb_12.a(new agt_2(1, 3));
        yb_12.a(new agt_2(4, 4));
        yb_12.a(new agt_2(8, 2));
        this.fH = new VertexBuffer(4, yb_12);
        this.az = new ams_1(6);
        this.fH.a(0, 1, -0.5f, -0.5f, -0.05f);
        this.fH.a(0, 4, 1.0f, 1.0f, 1.0f, 1.0f);
        this.fH.a(0, 8, 0.0f, 0.0f);
        this.fH.a(1, 1, 0.5f, -0.5f, -0.05f);
        this.fH.a(1, 4, 1.0f, 1.0f, 1.0f, 1.0f);
        this.fH.a(1, 8, 1.0f, 0.0f);
        this.fH.a(2, 1, -0.5f, 0.5f, -0.05f);
        this.fH.a(2, 4, 1.0f, 1.0f, 1.0f, 1.0f);
        this.fH.a(2, 8, 0.0f, 1.0f);
        this.fH.a(3, 1, 0.5f, 0.5f, -0.05f);
        this.fH.a(3, 4, 1.0f, 1.0f, 1.0f, 1.0f);
        this.fH.a(3, 8, 1.0f, 1.0f);
        this.az.add(0);
        this.az.add(1);
        this.az.add(3);
        this.az.add(0);
        this.az.add(3);
        this.az.add(2);
        this.fG = 0.0f;
        this.tK = air.cya;
        this.tL = air.cye;
    }

    public void setTexture(ef_1 ef_12) {
        this.tl = ef_12;
    }

    public void c(air air2, air air3) {
        this.tK = air2;
        this.tL = air3;
    }

    public void a(float f) {
        this.fG += f;
        int n2 = 0;
        for (float f2 = 0.0f; f2 <= 1.0f; f2 += 1.0f) {
            for (float f3 = 0.0f; f3 <= 1.0f; f3 += 1.0f) {
                float f4 = (0.5f * f2 + 0.5f * f3 + this.fG * 0.001f) * 0.6f;
                float f5 = (0.8f * f2 + 0.2f * f3 + this.fG * 0.001f) * 0.6f;
                this.fH.a(n2, 4, Water.a(this.fI), Water.b(this.fI), Water.c(this.fI), 1.0f);
                this.fH.a(n2, 8, f3 * 2.0f + 0.02f * ej_0.k(f4), f2 * 2.0f + 0.02f * ej_0.k(f5));
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

