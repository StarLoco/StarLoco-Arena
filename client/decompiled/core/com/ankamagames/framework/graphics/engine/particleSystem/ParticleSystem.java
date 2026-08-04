/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.particleSystem;

import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem$1;
import java.util.ArrayList;

public class ParticleSystem
extends Entity3D {
    private ald bZA;
    public static final int bZB = 16384;
    public static final short[] bZC = new short[bZB];
    public static final aPb ta;
    public long bZD;
    public int bZE;
    public boolean bZF;
    public byte bPN;
    public air tK = air.cya;
    public air bZG = air.cye;
    protected int bZH;
    public Particle bZI;
    protected ArrayList bZJ;
    protected float bZK;
    protected boolean Lh;
    protected long bZL;
    protected float bZM;
    protected float Hk;
    protected float Hl;
    protected float Hm;
    private static int bZN;
    private final int aW;
    private boolean bZO;
    private boolean bZP;
    private float bZQ;
    private boolean bZR = false;
    public String eA;
    private boolean bZS = false;
    private static final float[] auO;
    private static final float[] auP;
    private static final float[] auQ;
    private static int tf;
    private static int tg;
    private static int th;
    protected GeometryMesh bZT = null;

    public ParticleSystem(boolean bl2) {
        if (bl2) {
            this.bZA = new ald(this);
        } else {
            this.bZH = 0;
        }
        this.bZL = -2521772799257739264L;
        this.bZI = new ParticleSystem$1(this);
        this.bZI.KQ = 0.0f;
        this.bZI.KR = Float.MAX_VALUE;
        this.bZI.Hk = 0.0f;
        this.bZI.Hl = 0.0f;
        this.bZI.Hm = 0.0f;
        this.bZI.KM = 0.0f;
        this.bZI.KN = 0.0f;
        this.bZI.KO = 0.0f;
        this.bZJ = new ArrayList(1);
        this.aW = ParticleSystem.WI();
    }

    protected boolean Gd() {
        return akk_0.aVL().d(this.bZE, qo_2.hl(this.aW));
    }

    public void a(float f) {
        if (this.bZI == null) {
            return;
        }
        if (this.bZR) {
            this.bZS = this.Gd();
            this.bZR = false;
        }
        this.bZI.a(this, f);
        if (this.bZK != 0.0f) {
            this.bZM -= f;
            if (this.bZM <= 0.0f && !this.bZP) {
                this.alQ();
            }
        }
        if (!this.bZI.isAlive() && !this.bZP) {
            this.alQ();
        }
        if (!this.bZO) {
            return;
        }
        if (this.bZP) {
            if (this.bZI.ro()) {
                int n2 = this.bZI.Li.size();
                for (int j = 0; j < n2; ++j) {
                    Emitter emitter = (Emitter)this.bZI.Li.get(j);
                    if (!emitter.isAlive()) continue;
                    return;
                }
            }
            this.agM();
        } else {
            this.bZQ -= f;
            if (this.bZQ <= 0.0f) {
                this.agM();
            }
        }
    }

    public void d(db_2 db_22) {
        if (this.bZI == null || this.bZI.Li == null) {
            return;
        }
        super.d(db_22);
    }

    public void start() {
        if (this.bZI == null || this.bZI.Li == null) {
            return;
        }
        int n2 = this.bZI.Li.size();
        for (int j = 0; j < n2; ++j) {
            Emitter emitter = (Emitter)this.bZI.Li.get(j);
            emitter.reset();
            emitter.eC(false);
        }
    }

    public void reset() {
        this.bZT = null;
        this.bZO = false;
        this.bZP = false;
        this.bZR = true;
    }

    public void stop() {
        if (this.bZI.Li == null) {
            return;
        }
        int n2 = this.bZI.Li.size();
        for (int j = 0; j < n2; ++j) {
            Emitter emitter = (Emitter)this.bZI.Li.get(j);
            emitter.eC(true);
        }
    }

    public void alQ() {
        this.stop();
        this.bZO = true;
        this.bZP = true;
    }

    public void iZ(int n2) {
        this.stop();
        this.bZQ = (float)n2 / 1000.0f;
        this.bZO = true;
        this.bZP = false;
    }

    public void kill() {
        this.bZO = true;
        this.bZP = false;
    }

    public void agM() {
        if (this.bZI == null) {
            return;
        }
        int n2 = this.bZJ.size();
        for (int j = 0; j < n2; ++j) {
            mp_1 mp_12 = (mp_1)this.bZJ.get(j);
            mp_12.reset();
        }
        qo_2.f(this.getId(), this.bZS);
        this.bZI.a(this);
        this.bZI.HF();
        this.bZI = null;
        this.HF();
    }

    public boolean isEditable() {
        return this.bZA != null;
    }

    public void a(mp_1 mp_12) {
        this.bZJ.add(mp_12);
        if (!this.isEditable()) {
            this.bZH += mp_12.btz;
        }
    }

    public final int getId() {
        return this.aW;
    }

    public int getDuration() {
        return (int)(this.bZK * 1000.0f);
    }

    public void setDuration(int n2) {
        this.bZK = (float)n2 / 1000.0f;
        if (this.bZK != 0.0f) {
            this.bZM = this.bZK;
        }
    }

    public boolean alR() {
        return this.Lh;
    }

    public void cF(boolean bl2) {
        this.Lh = bl2;
        this.bZI.Lh = bl2;
    }

    public ArrayList alS() {
        return this.bZJ;
    }

    public void r(float f, float f2) {
        this.Hk = f;
        this.Hl = f2;
    }

    public void setPosition(float f, float f2, float f3) {
        this.Hk = f;
        this.Hl = f2;
        this.Hm = f3;
    }

    public float getX() {
        return this.Hk;
    }

    public float getY() {
        return this.Hl;
    }

    public float id() {
        return this.Hm;
    }

    public void dj(long l2) {
        this.bZL = l2;
    }

    public final long ja(int n2) {
        assert (this.bZL != -2521772799257739264L);
        return this.bZL + (long)n2;
    }

    public void alT() {
        this.bZI.a(this, this.bZJ);
    }

    public int b(mp_1 mp_12) {
        int n2 = 0;
        n2 += mp_12.btz;
        if (mp_12.btu == null) {
            return n2;
        }
        int n3 = mp_12.btu.size();
        for (int j = 0; j < n3; ++j) {
            mp_1 mp_13 = (mp_1)mp_12.btu.get(j);
            n2 += mp_12.btz * this.b(mp_13);
        }
        return n2;
    }

    public final void e(ef_1 ef_12) {
        assert (!this.isEditable()) : "Initialize should not be called on editable particle systems";
        this.alU();
        if (this.bZH == 0) {
            return;
        }
        GLGeometryMesh gLGeometryMesh = (GLGeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
        this.a(gLGeometryMesh);
        gLGeometryMesh.a(this.tK, this.bZG);
        this.a(gLGeometryMesh, ef_12, ta);
        this.bZR = true;
    }

    private void alU() {
        this.bZH = 0;
        int n2 = this.bZJ.size();
        for (int j = 0; j < n2; ++j) {
            mp_1 mp_12 = (mp_1)this.bZJ.get(j);
            this.bZH += this.b(mp_12);
        }
    }

    private void a(GeometryMesh geometryMesh) {
        geometryMesh.a(jB.Ba, this.bZH * 4, this.bZH * 4);
        geometryMesh.ac().c(bZC, 0, this.bZH * 4);
    }

    public void alV() {
        this.alU();
        this.a((GeometryMesh)this.ma(0));
    }

    protected void b(Particle particle, float f, float f2, int n2) {
        float f3 = particle.KV * particle.KT;
        float f4 = particle.KW * particle.KU;
        float f5 = ej_0.l(particle.KS);
        float f6 = ej_0.k(particle.KS);
        float f7 = -particle.KX * particle.KT;
        float f8 = (particle.KY - particle.KW * 2.0f) * particle.KU;
        float f9 = f5 * f3;
        float f10 = f6 * f3;
        float f11 = -f6 * f4;
        float f12 = f5 * f4;
        float f13 = f + (f5 * f7 - f6 * f8);
        float f14 = f2 + (f6 * f7 + f5 * f8);
        float f15 = f13 + f11 + f11;
        float f16 = f14 + f12 + f12;
        float f17 = f15 + f9 + f9;
        float f18 = f16 + f10 + f10;
        float f19 = f13 + f9 + f9;
        float f20 = f14 + f10 + f10;
        GeometryMesh geometryMesh = this.isEditable() ? (GeometryMesh)particle.Lg.dnI.get(particle.Ld) : (GeometryMesh)this.ma(0);
        if (this.bZT != null && this.bZT != geometryMesh) {
            this.alW();
        }
        this.bZT = geometryMesh;
        if (f13 == Float.NaN || f14 == Float.NaN || f15 == Float.NaN || f16 == Float.NaN || f17 == Float.NaN || f18 == Float.NaN || f19 == Float.NaN || f20 == Float.NaN) {
            System.err.println("merde");
            return;
        }
        ParticleSystem.auO[ParticleSystem.tf++] = f13;
        ParticleSystem.auO[ParticleSystem.tf++] = f14;
        ParticleSystem.auO[ParticleSystem.tf++] = f15;
        ParticleSystem.auO[ParticleSystem.tf++] = f16;
        ParticleSystem.auO[ParticleSystem.tf++] = f17;
        ParticleSystem.auO[ParticleSystem.tf++] = f18;
        ParticleSystem.auO[ParticleSystem.tf++] = f19;
        ParticleSystem.auO[ParticleSystem.tf++] = f20;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IQ;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IR;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IS;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IT;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IQ;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IR;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IS;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IT;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IQ;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IR;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IS;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IT;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IQ;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IR;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IS;
        ParticleSystem.auP[ParticleSystem.tg++] = particle.IT;
        ParticleSystem.auQ[ParticleSystem.th++] = particle.La;
        ParticleSystem.auQ[ParticleSystem.th++] = particle.Lb;
        ParticleSystem.auQ[ParticleSystem.th++] = particle.La;
        ParticleSystem.auQ[ParticleSystem.th++] = particle.KZ;
        ParticleSystem.auQ[ParticleSystem.th++] = particle.Lc;
        ParticleSystem.auQ[ParticleSystem.th++] = particle.KZ;
        ParticleSystem.auQ[ParticleSystem.th++] = particle.Lc;
        ParticleSystem.auQ[ParticleSystem.th++] = particle.Lb;
        if (f13 < (float)this.EN) {
            this.EN = (int)f13;
        }
        if (f14 < (float)this.EO) {
            this.EO = (int)f14;
        }
        if (f17 > (float)this.EP) {
            this.EP = (int)f17;
        }
        if (f18 > (float)this.EQ) {
            this.EQ = (int)f18;
        }
    }

    protected void alW() {
        if (this.bZT == null) {
            return;
        }
        VertexBufferPCT vertexBufferPCT = this.bZT.ab();
        vertexBufferPCT.b(auO, tf);
        vertexBufferPCT.d(auP, tg);
        vertexBufferPCT.f(auQ, th);
        vertexBufferPCT.dz(vertexBufferPCT.fq() + tf / 2);
        tf = 0;
        tg = 0;
        th = 0;
    }

    protected void alX() {
        throw new UnsupportedOperationException();
    }

    private static int WI() {
        return bZN++;
    }

    public boolean isAlive() {
        return !this.bZO;
    }

    public ArrayList alY() {
        if (this.bZI != null) {
            return this.bZI.Li;
        }
        return null;
    }

    public boolean alZ() {
        return this.bZP;
    }

    public float ama() {
        return this.bZM;
    }

    public ald amb() {
        return this.bZA;
    }

    public void b(air air2, air air3) {
        this.tK = air2;
        this.bZG = air3;
    }

    public static /* synthetic */ ald c(ParticleSystem particleSystem) {
        return particleSystem.bZA;
    }

    public static /* synthetic */ void d(ParticleSystem particleSystem) {
        super.clear();
    }

    static {
        for (int j = 0; j < bZC.length; ++j) {
            ParticleSystem.bZC[j] = (short)j;
        }
        ta = aPb.aYI();
        ta.d(aPb.enf);
        ta.H(0.0f, 0.0f, 0.0f, 0.0f);
        auO = new float[2 * bZB];
        auP = new float[4 * bZB];
        auQ = new float[2 * bZB];
        bZN = 1;
        tf = 0;
        tg = 0;
        th = 0;
    }
}

