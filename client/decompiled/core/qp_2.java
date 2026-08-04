/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.VertexBuffer;
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.EntityBatch;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.apache.log4j.Logger;

/*
 * Renamed from qp
 */
public final class qp_2
extends db_2 {
    public aeu_1 adV = new aeu_1();
    public static final GLU gp = new GLU();
    private aPb adW = null;
    private static final Logger a = Logger.getLogger(qp_2.class);
    private final aPb adX = aPb.aYI();

    public qp_2() {
        this.aMI = new Matrix44();
        this.aNN = new Matrix44();
        this.aMI.OH();
        this.aNN.OH();
        this.adX.F(new float[]{-1.0f, -1.0f, -1.0f, -1.0f});
        this.adX.G(new float[]{-1.0f, -1.0f, -1.0f, -1.0f});
        this.adX.H(new float[]{-1.0f, -1.0f, -1.0f, -1.0f});
        this.adX.I(new float[]{-1.0f, -1.0f, -1.0f, -1.0f});
        this.adX.bY(-1.0f);
    }

    public final arX vg() {
        return arX.cQT;
    }

    public final VertexBuffer vh() {
        return new VertexBuffer();
    }

    public final ams_1 vi() {
        return new ams_1();
    }

    public final ef_1 a(long l2, String string, boolean bl2) {
        return new Ss(l2, string, bl2);
    }

    public final ef_1 a(long l2, aon_2 aon_22, boolean bl2) {
        return new Ss(l2, aon_22, bl2);
    }

    public final ef_1 a(long l2, int n2, int n3, boolean bl2) {
        return new Ss(l2, n2, n3, false);
    }

    public final void c(VertexBuffer vertexBuffer) {
        yb_1 yb_12 = vertexBuffer.ft();
        int n2 = 0;
        if (yb_12.W(1) != null) {
            ++n2;
            ((GL)this.aNM).glEnableClientState(32884);
        }
        if (yb_12.W(2) != null) {
            ++n2;
            ((GL)this.aNM).glEnableClientState(32885);
        }
        if (yb_12.W(4) != null) {
            ++n2;
            ((GL)this.aNM).glEnableClientState(32886);
        }
        if (yb_12.W(8) != null) {
            ++n2;
            ((GL)this.aNM).glClientActiveTexture(33984);
            ((GL)this.aNM).glEnableClientState(32888);
        }
        int n3 = 33985;
        while (n2 < yb_12.getNumComponents()) {
            ((GL)this.aNM).glClientActiveTexture(n3);
            ((GL)this.aNM).glEnableClientState(32888);
            ++n2;
        }
    }

    public final void a(ams_1 ams_12) {
    }

    public final void b(Matrix44 matrix44) {
        if (!this.aMI.h(matrix44)) {
            this.aMI.d(matrix44);
            this.vl();
        }
    }

    public final void c(Matrix44 matrix44) {
        if (!this.aNN.h(matrix44)) {
            this.aNN.d(matrix44);
            this.vl();
        }
    }

    public final void a(aPb aPb2) {
        float[] fArray;
        float[] fArray2;
        float[] fArray3;
        float[] fArray4 = aPb2.aYK();
        if (fArray4[0] != (fArray3 = this.adX.aYK())[0] || fArray4[1] != fArray3[1] || fArray4[2] != fArray3[2] || fArray4[3] != fArray3[3]) {
            ((GL)this.aNM).glMaterialfv(1032, 5634, fArray4, 0);
            ((GL)this.aNM).glMaterialfv(1032, 4609, fArray4, 0);
            System.arraycopy(fArray4, 0, fArray3, 0, 4);
        }
        if ((fArray2 = aPb2.aYL())[0] != (fArray = this.adX.aYL())[0] || fArray2[1] != fArray[1] || fArray2[2] != fArray[2] || fArray2[3] != fArray[3]) {
            ((GL)this.aNM).glMaterialfv(1028, 4610, fArray2, 0);
            System.arraycopy(fArray2, 0, fArray, 0, 4);
        }
    }

    public final void f(ArrayList arrayList) {
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            ((rh_1)arrayList.get(j)).k((GL)this.aNM);
        }
    }

    public final void a(rh_1 rh_12) {
        rh_12.k((GL)this.aNM);
    }

    public void a(ef_1 ef_12) {
        if (ef_12 == null) {
            ((GL)this.aNM).glBindTexture(3553, 0);
            return;
        }
        assert (ef_12.getClass() == Ss.class) : "GLRenderer can't apply Texture but GLTexture";
        Ss ss = (Ss)ef_12;
        ((GL)this.aNM).glBindTexture(3553, ss.getID());
        vo_1 vo_12 = vo_1.aik();
        vo_12.b(ss.MK());
        vo_12.n(this);
    }

    public void b(ef_1 ef_12) {
        this.aNP = ef_12;
        if (ef_12 == null) {
            ((GL)this.aNM).glBindFramebufferEXT(36160, 0);
        } else {
            assert (ef_12.getClass() == Ss.class) : "GLRenderer can't apply Texture but GLTexture";
            Ss ss = (Ss)ef_12;
            ((GL)this.aNM).glBindFramebufferEXT(36160, ss.afi());
        }
    }

    public final void f(GL gL) {
        super.B(gL);
        this.adV.r(gL);
        int[] nArray = new int[1];
        gL.glGetIntegerv(34018, nArray, 0);
        this.aNQ = nArray[0];
        if (this.aNQ > 4) {
            this.aNQ = 4;
        }
        this.aNQ = 1;
        this.aNS = EntityBatch.abm();
        this.aNS.initialize(this.aNQ);
    }

    public final void a(float f, float f2, float f3, float f4, int n2) {
        vo_1.aik().cu(false);
        vo_1.aik().n(this);
        this.b(Matrix44.bEn);
        this.c(Matrix44.bEn);
        ((GL)this.aNM).glBegin(7);
        ((GL)this.aNM).glColor4f(vP.dY(n2), vP.dX(n2), vP.dW(n2), vP.dV(n2));
        ((GL)this.aNM).glVertex2f(f, f2);
        ((GL)this.aNM).glVertex2f(f, f2 + f4);
        ((GL)this.aNM).glVertex2f(f + f3, f2 + f4);
        ((GL)this.aNM).glVertex2f(f + f3, f2);
        ((GL)this.aNM).glEnd();
    }

    public final void a(EntitySprite entitySprite) {
        asr_0 asr_02 = entitySprite.aUP();
        int n2 = entitySprite.aUR();
        ArrayList arrayList = entitySprite.aUQ();
        ub_0 ub_02 = entitySprite.aUN();
        ub_0 ub_03 = entitySprite.aUO();
        air air2 = entitySprite.Hu().bW();
        air air3 = entitySprite.Hu().bX();
        aPb aPb2 = entitySprite.getMaterial();
        ef_1 ef_12 = entitySprite.jI();
        int n3 = 0;
        if (!this.LW()) {
            this.aNR.cwp = asr_02;
            this.aNR.cwq = n2;
            this.aNR.cwr.clear();
            for (int j = 0; j < arrayList.size(); ++j) {
                this.aNR.cwr.add(arrayList.get(j));
            }
            this.aNR.cws = ub_02;
            this.aNR.cwt = ub_03;
            this.aNR.GB = air2;
            this.aNR.GC = air3;
            this.aNR.tJ = aPb2;
            this.aNS.b(this.aNR.cwp);
            this.aNS.oM(this.aNR.cwq);
            this.aNS.o(this.aNR.cwr);
            this.aNS.a(this.aNR.cws);
            this.aNS.b(this.aNR.cwt);
            this.aNS.b(this.aNR.GB, this.aNR.GC);
            this.aNS.setMaterial(this.aNR.tJ);
            switch (this.aNQ) {
                case 4: {
                    this.aNS.a(3, (ef_1)null);
                    this.aNR.cwA[3] = false;
                }
                case 3: {
                    this.aNS.a(2, (ef_1)null);
                    this.aNR.cwA[2] = false;
                }
                case 2: {
                    this.aNS.a(1, (ef_1)null);
                    this.aNR.cwA[1] = false;
                }
            }
            this.aNR.cwA[0] = true;
            this.aNR.cwz[0] = ef_12;
            this.aNS.a(0, ef_12);
            this.am(true);
        } else {
            int n4;
            boolean bl2 = false;
            if (this.aNS.isFull()) {
                this.aNS.a(this);
                bl2 = true;
            }
            if (this.aNR.cwp != asr_02) {
                this.aNS.a(this);
                bl2 = true;
                this.aNR.cwp = asr_02;
                this.aNS.b(this.aNR.cwp);
            }
            if (this.aNR.cwq != n2) {
                if (!bl2) {
                    this.aNS.a(this);
                    bl2 = true;
                }
                this.aNR.cwq = n2;
                this.aNS.oM(n2);
            }
            boolean bl3 = false;
            if (this.aNR.cwr.size() != arrayList.size()) {
                bl3 = true;
            }
            if (!bl3) {
                for (n4 = 0; n4 < this.aNR.cwr.size(); ++n4) {
                    QI qI = (QI)this.aNR.cwr.get(n4);
                    QI qI2 = (QI)arrayList.get(n4);
                    if (qI2.awT() == qI.awT() && qI.b(qI2)) continue;
                    bl3 = true;
                    break;
                }
            }
            if (bl3) {
                if (!bl2) {
                    this.aNS.a(this);
                    bl2 = true;
                }
                this.aNR.cwr.clear();
                this.aNR.cwr.addAll(entitySprite.aUQ());
                this.aNS.o(this.aNR.cwr);
            }
            if (ub_02 != this.aNR.cws || ub_03 != this.aNR.cwt) {
                if (!bl2) {
                    this.aNS.a(this);
                    bl2 = true;
                }
                this.aNR.cws = ub_02;
                this.aNS.a(this.aNR.cws);
                this.aNS.b(this.aNR.cwt);
            }
            if (this.aNR.GB != air2 || this.aNR.GC != air3) {
                if (!bl2) {
                    this.aNS.a(this);
                    bl2 = true;
                }
                this.aNR.GB = air2;
                this.aNR.GC = air3;
                this.aNS.b(this.aNR.GB, this.aNR.GC);
            }
            n4 = 0;
            if (aPb2 == null) {
                if (this.aNR.tJ != null) {
                    n4 = 1;
                }
            } else if (!this.aNR.tJ.e(aPb2)) {
                n4 = 1;
            }
            if (n4 != 0) {
                if (!bl2) {
                    this.aNS.a(this);
                    bl2 = true;
                }
                this.aNR.tJ = aPb2;
                this.aNS.setMaterial(aPb2);
            }
            boolean bl4 = false;
            for (int j = 0; j < this.aNQ; ++j) {
                if (!this.aNR.cwA[j]) {
                    this.aNR.cwA[j] = true;
                    this.aNR.cwz[j] = ef_12;
                    this.aNS.a(j, ef_12);
                    n3 = j;
                    bl4 = true;
                    break;
                }
                if (this.aNR.cwz[j] != ef_12) continue;
                n3 = j;
                bl4 = true;
                break;
            }
            if (!bl4) {
                if (!bl2) {
                    this.aNS.a(this);
                    bl2 = true;
                }
                this.aNR.cwA[0] = true;
                this.aNR.cwA[1] = false;
                this.aNR.cwA[2] = false;
                this.aNR.cwA[3] = false;
                this.aNR.cwz[n3] = ef_12;
                this.aNS.a(n3, ef_12);
            }
        }
        this.a(entitySprite, n3);
    }

    private void a(EntitySprite entitySprite, int n2) {
        VertexBufferPCT vertexBufferPCT = entitySprite.Hu().ab();
        Matrix44 matrix44 = entitySprite.ki();
        if (!matrix44.isIdentity()) {
            float[] fArray = matrix44.Pn();
            float f = entitySprite.Hy();
            float f2 = entitySprite.Hz();
            float f3 = entitySprite.Hw();
            float f4 = entitySprite.Hx();
            float f5 = fArray[12];
            float f6 = fArray[13];
            float f7 = fArray[14];
            float f8 = f * fArray[4];
            float f9 = f * fArray[5];
            float f10 = f2 * fArray[4];
            float f11 = f2 * fArray[5];
            float f12 = f3 * fArray[0];
            float f13 = f3 * fArray[1];
            float f14 = f4 * fArray[0];
            float f15 = f4 * fArray[1];
            this.tb[0] = f12 + f10 + f5;
            this.tb[1] = f13 + f11 + f6;
            this.tb[2] = f12 + f8 + f5;
            this.tb[3] = f13 + f9 + f6;
            this.tb[4] = f14 + f10 + f5;
            this.tb[5] = f15 + f11 + f6;
            this.tb[6] = f14 + f8 + f5;
            this.tb[7] = f15 + f9 + f6;
            this.aNS.g(this.tb);
        } else {
            this.aNS.a(vertexBufferPCT.ys());
        }
        this.aNS.b(vertexBufferPCT.yt());
        switch (this.aNQ) {
            default: {
                this.aNS.a(0, vertexBufferPCT.yu());
                break;
            }
            case 2: {
                this.aNS.a(n2, vertexBufferPCT.yu());
                this.aNS.g(1 - n2, this.td);
                break;
            }
            case 4: {
                this.aNS.a(n2, vertexBufferPCT.yu());
                this.aNS.g(3 - n2, this.td);
                this.aNS.g((5 - n2) % 4, this.td);
                this.aNS.g((n2 + 2) % 4, this.td);
            }
        }
        this.aNS.gW(4);
    }

    public void cO(int n2) {
        if (this.aNS.fq() != 0) {
            this.aNS.a(this);
        }
        this.am(false);
    }

    public boolean vj() {
        return Mf.btd.a(amA.cHA);
    }

    public void am(boolean bl2) {
        super.am(bl2);
        if (!this.aNT) {
            vo_1 vo_12 = vo_1.aik();
            for (int j = 0; j < this.aNQ; ++j) {
                ((GL)this.aNM).glClientActiveTexture(33984 + j);
                ((GL)this.aNM).glActiveTexture(33984 + j);
                ((GL)this.aNM).glDisable(3553);
                ((GL)this.aNM).glDisableClientState(32888);
            }
            ((GL)this.aNM).glClientActiveTexture(33984);
            ((GL)this.aNM).glActiveTexture(33984);
            ((GL)this.aNM).glEnableClientState(32888);
            vo_12.cu(true);
            vo_12.n(this);
        }
    }

    public void vk() {
        this.cP(3042);
        this.cQ(3041);
        this.cQ(3040);
        this.cP(32886);
        this.cQ(32897);
        this.cQ(32899);
        this.cQ(32898);
        this.cP(32888);
        this.cQ(32904);
        this.cQ(32906);
        this.cQ(32905);
        this.cP(32884);
        this.cQ(32890);
        this.cQ(32892);
        this.cQ(32891);
        this.cP(2884);
        this.cQ(2885);
        this.cQ(3415);
        this.cQ(2961);
        this.cQ(2964);
        this.cQ(2962);
        this.cQ(2965);
        this.cQ(2966);
        this.cQ(2967);
        this.cP(2960);
        this.cQ(2963);
        this.cQ(2968);
        this.cP(3552);
        this.cP(3553);
        this.cP(2977);
        this.cP(3089);
    }

    private void cP(int n2) {
        byte[] byArray = new byte[1];
        ((GL)this.aNM).glGetBooleanv(n2, byArray, 0);
        System.out.println("" + n2 + "\t" + byArray[0]);
    }

    private void cQ(int n2) {
        int[] nArray = new int[1];
        ((GL)this.aNM).glGetIntegerv(n2, nArray, 0);
        System.out.println("" + n2 + "\t" + nArray[0]);
    }

    private void cR(int n2) {
        float[] fArray = new float[4];
        ((GL)this.aNM).glGetFloatv(n2, fArray, 0);
        a.info((Object)("" + n2 + "\t" + fArray[0]));
        a.info((Object)("" + n2 + "\t" + fArray[1]));
        a.info((Object)("" + n2 + "\t" + fArray[2]));
        a.info((Object)("" + n2 + "\t" + fArray[3]));
    }

    public void vl() {
        vo_1 vo_12 = vo_1.aik();
        this.aNO.a(this.aMI, this.aNN);
        vo_12.a(jq_0.bmI);
        vo_12.n(this);
        ((GL)this.aNM).glLoadMatrixf(this.aNO.Pn(), 0);
    }
}

