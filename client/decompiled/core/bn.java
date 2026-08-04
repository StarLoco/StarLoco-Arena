/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBuffer;
import com.ankamagames.framework.graphics.engine.fx.FixedPipeline.Water;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL;

public class bn {
    public float[] fE;
    public float fF = 0.1f;
    public float fG;
    public VertexBuffer fH;
    public ams_1 az;
    final /* synthetic */ Water fI;

    public bn(Water water) {
        float f;
        int n2;
        this.fI = water;
        this.fF += ej_0.hL() * 0.025f;
        ArrayList<agu_0> arrayList = new ArrayList<agu_0>();
        for (int j = 0; j < 10; ++j) {
            arrayList.add(new agu_0((float)j / 9.0f - 0.5f, ej_0.hL() * 0.05f, 0.0f, 1.0f));
        }
        ArrayList<agu_0> arrayList2 = new ArrayList<agu_0>();
        int n3 = 8;
        int n4 = arrayList.size();
        for (int j = 0; j < n4; ++j) {
            agu_0 agu_02 = (agu_0)arrayList.get(Math.max(0, j - 1));
            agu_0 agu_03 = (agu_0)arrayList.get(j);
            agu_0 agu_04 = (agu_0)arrayList.get(Math.min(n4 - 1, j + 1));
            agu_0 agu_05 = (agu_0)arrayList.get(Math.min(n4 - 1, j + 2));
            for (n2 = 0; n2 < 8; ++n2) {
                float f2 = (float)n2 / 8.0f;
                f = agu_03.Hk + 0.5f * (f2 * (-agu_02.Hk + agu_04.Hk + f2 * (2.0f * agu_02.Hk - 5.0f * agu_03.Hk + 4.0f * agu_04.Hk - agu_05.Hk + f2 * (-agu_02.Hk + 3.0f * (agu_03.Hk - agu_04.Hk) + agu_05.Hk))));
                float f3 = agu_03.Hl + 0.5f * (f2 * (-agu_02.Hl + agu_04.Hl + f2 * (2.0f * agu_02.Hl - 5.0f * agu_03.Hl + 4.0f * agu_04.Hl - agu_05.Hl + f2 * (-agu_02.Hl + 3.0f * (agu_03.Hl - agu_04.Hl) + agu_05.Hl))));
                arrayList2.add(new agu_0(f, f3, 0.0f, 1.0f));
            }
        }
        yb_1 yb_12 = new yb_1();
        yb_12.a(new agt_2(1, 3));
        yb_12.a(new agt_2(4, 4));
        yb_12.a(new agt_2(8, 2));
        int n5 = 8;
        int n6 = arrayList2.size();
        int n7 = n6 * 8;
        this.fH = new VertexBuffer(n7, yb_12);
        this.fE = new float[n7];
        int n8 = 0;
        for (n2 = 0; n2 < n6; ++n2) {
            agu_0 agu_06 = (agu_0)arrayList2.get(n2);
            f = -1.49f;
            for (int j = 0; j < 8; ++j) {
                float f4 = (float)j / 7.0f;
                this.fE[n8] = agu_06.Hl + 0.5f + f4 * this.fF;
                this.fH.a(n8, 1, agu_06.Hk, this.fE[n8], -0.03725f);
                this.fH.a(n8, 4, 1.0f, 1.0f, 1.0f, 1.0f);
                this.fH.a(n8, 8, 0.5f + agu_06.Hk, f4);
                ++n8;
            }
        }
        this.az = new ams_1(n6 * 6 * 8);
        for (float f5 = 0.0f; f5 < 7.0f; f5 += 1.0f) {
            for (float f6 = 0.0f; f6 < (float)(n6 - 1); f6 += 1.0f) {
                this.az.add((int)(f6 * 8.0f + f5));
                this.az.add((int)((f6 + 1.0f) * 8.0f + f5));
                this.az.add((int)((f6 + 1.0f) * 8.0f + (f5 + 1.0f)));
                this.az.add((int)(f6 * 8.0f + f5));
                this.az.add((int)((f6 + 1.0f) * 8.0f + (f5 + 1.0f)));
                this.az.add((int)(f6 * 8.0f + (f5 + 1.0f)));
            }
        }
    }

    public void d(float f, float f2) {
        this.fG += f;
        int n2 = this.fH.fq();
        FloatBuffer floatBuffer = this.fH.fs();
        int n3 = this.fH.fr() / 4;
        for (int j = 0; j < n2; ++j) {
            float f3 = floatBuffer.get(j * n3);
            float f4 = this.fE[j] - this.fG * 0.05f * 0.001f + ej_0.i(this.fG * 0.001f * 1.25f + f3) * 0.06125f * 0.33f;
            float f5 = (0.5f * f4 * 40.0f + 0.5f * f3 * 40.0f + f2 * 0.001f) * 0.6f;
            float f6 = (0.8f * f4 * 40.0f + 0.2f * f3 * 40.0f + f2 * 0.001f) * 0.6f;
            float f7 = -1.5f;
            this.fH.a(j, 1, f3, f4, f7 / 40.0f - 0.015f);
            this.fH.a(j, 4, Water.a(this.fI), Water.b(this.fI), Water.c(this.fI), 1.0f);
        }
    }

    public void a(db_2 db_22) {
        GL gL = (GL)((qp_2)db_22).LV();
        ef_1 ef_12 = cx_0.JY().bt(-1296775008915292155L);
        ef_12.f(db_22);
        vo_1 vo_12 = vo_1.aik();
        vo_12.cu(true);
        vo_12.a(air.cya, air.cye);
        vo_12.n(db_22);
        qp_2 qp_22 = (qp_2)db_22;
        qp_22.adV.nO(13);
        int n2 = this.fH.fr();
        gL.glVertexPointer(3, 5126, n2, this.fH.V(1));
        gL.glColorPointer(4, 5126, n2, this.fH.V(4));
        gL.glTexCoordPointer(2, 5126, n2, this.fH.V(8));
        gL.glDrawElements(4, this.az.aWY(), 5123, this.az.aWZ());
    }
}

