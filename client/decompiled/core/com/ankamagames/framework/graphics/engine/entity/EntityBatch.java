/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.entity;

import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL;

public final class EntityBatch
extends Entity {
    private boolean[] bBf;
    private boolean bBg;
    private boolean bBh;
    private boolean bBi;
    private air GB;
    private air GC;
    private ef_1[] bBj;
    private aPb tJ;
    private zf_1 ajg;
    private zf_1 ajh;
    private zf_1[] bBk;
    private FloatBuffer ajj;
    private FloatBuffer ajk;
    private FloatBuffer[] bBl;
    private int kA = 0;
    private final int kz;
    private int bBm = 1;
    private ams_1 az;
    private static EntityBatch bBn = new EntityBatch();

    private EntityBatch() {
        this.kz = 2048;
    }

    public static EntityBatch abm() {
        return bBn;
    }

    public final boolean isFull() {
        return this.kA == this.kz;
    }

    public final void a(float f) {
    }

    public final void initialize(int n2) {
        int n3;
        this.bBm = n2;
        this.bBj = new ef_1[this.bBm];
        this.bBf = new boolean[this.bBm];
        this.ajg = aoj_1.aXZ().pI(this.kz * 2 * 4);
        this.ajj = (FloatBuffer)this.ajg.getBuffer();
        this.ajj.rewind();
        this.ajh = aoj_1.aXZ().pI(this.kz * 4 * 4);
        this.ajk = (FloatBuffer)this.ajh.getBuffer();
        this.bBk = new zf_1[this.bBm];
        this.bBl = new FloatBuffer[this.bBm];
        for (n3 = 0; n3 < this.bBm; ++n3) {
            this.bBk[n3] = aoj_1.aXZ().pI(this.kz * 2 * 4);
            this.bBl[n3] = (FloatBuffer)this.bBk[n3].getBuffer();
        }
        this.az = new ams_1(this.kz);
        for (n3 = 0; n3 < this.kz / 4; n3 += 4) {
            this.az.add(n3);
            this.az.add(n3 + 1);
            this.az.add(n3 + 3);
            this.az.add(n3 + 2);
        }
    }

    public final void a(FloatBuffer floatBuffer) {
        this.ajj.position(this.kA * 2);
        this.ajj.put(floatBuffer);
    }

    public final void g(float[] fArray) {
        this.ajj.put(fArray);
    }

    public final void b(FloatBuffer floatBuffer) {
        this.ajk.position(this.kA * 4);
        this.ajk.put(floatBuffer);
    }

    public final void a(int n2, FloatBuffer floatBuffer) {
        this.bBl[n2].position(this.kA * 2);
        this.bBl[n2].put(floatBuffer);
    }

    public final void g(int n2, float[] fArray) {
        this.bBl[n2].put(fArray);
    }

    public void gW(int n2) {
        this.kA += n2;
    }

    public final int fq() {
        return this.kA;
    }

    public final void a(db_2 db_22) {
        int n2;
        assert (this.bBj != null) : "You must call setNumTextures one time before calling the render function";
        assert (db_22.vg() == arX.cQT);
        if (!dPD) {
            this.ajj.rewind();
            this.ajk.rewind();
            for (int j = 0; j < this.bBm; ++j) {
                this.bBl[j].rewind();
            }
            this.kA = 0;
            return;
        }
        wq_1.Dn().Dp();
        this.cws.b(db_22);
        if (this.cwp != null) {
            this.cwp.mg(this.cwq);
            if (this.bBh) {
                QI qI;
                int n3;
                this.bBh = false;
                n2 = this.cwr.size();
                if (n2 > 1) {
                    for (n3 = 0; n3 < n2; ++n3) {
                        qI = (QI)this.cwr.get(n3);
                        this.cwp.a(qI);
                    }
                } else {
                    for (n3 = 0; n3 < n2; ++n3) {
                        qI = (QI)this.cwr.get(n3);
                        this.cwp.a(qI);
                    }
                }
            }
            dPE = this.cwp;
            this.cwp.a(db_22, this);
        } else {
            if (dPE != null) {
                dPE.reset();
                dPE = null;
            }
            this.d(db_22);
        }
        this.cwt.b(db_22);
        this.ajj.rewind();
        this.ajk.rewind();
        for (n2 = 0; n2 < this.bBm; ++n2) {
            this.bBl[n2].rewind();
        }
        this.kA = 0;
    }

    public final void d(db_2 db_22) {
        int n2;
        int n3;
        assert (db_22.vg() == arX.cQT);
        qp_2 qp_22 = (qp_2)db_22;
        GL gL = (GL)qp_22.LV();
        if (qp_22.LX()) {
            for (n3 = 0; n3 < this.bBm; ++n3) {
                this.bBf[n3] = true;
            }
            this.bBh = true;
            this.bBi = true;
            db_22.b(Matrix44.bEn);
        }
        n3 = 0;
        vo_1 vo_12 = vo_1.aik();
        if (this.bBf[0]) {
            vo_12.cu(this.bBj[0] != null);
            n3 = 1;
        }
        for (n2 = 0; n2 < this.bBm; ++n2) {
            if (!this.bBf[n2]) continue;
            this.bBf[n2] = false;
            gL.glClientActiveTexture(33984 + n2);
            gL.glActiveTexture(33984 + n2);
            if (this.bBj[n2] == null) {
                vo_12.cu(false);
                vo_12.n(db_22);
                continue;
            }
            vo_12.cu(true);
            vo_12.n(db_22);
            n3 = 1;
            this.bBj[n2].f(db_22);
        }
        if (this.bBg) {
            this.bBg = false;
            db_22.a(this.tJ);
        }
        if (this.bBi) {
            this.bBi = false;
            vo_12.cr(true);
            vo_12.a(this.GB, this.GC);
            n3 = 1;
        }
        if (qp_22.LX()) {
            this.ajj.rewind();
            this.ajk.rewind();
            qp_22.adV.nO(0);
            qp_22.adV.nO(13);
            gL.glVertexPointer(2, 5126, 0, this.ajj);
            gL.glColorPointer(4, 5126, 0, this.ajk);
            for (n2 = 1; n2 < this.bBm; ++n2) {
                gL.glActiveTexture(33984 + n2);
                vo_12.cu(true);
                vo_12.n(db_22);
                gL.glTexEnvf(8960, 8704, 34160.0f);
                gL.glTexEnvf(8960, 34161, 260.0f);
            }
            for (n2 = 0; n2 < this.bBm; ++n2) {
                this.bBl[n2].rewind();
                gL.glClientActiveTexture(33984 + n2);
                gL.glTexCoordPointer(2, 5126, 0, this.bBl[n2]);
                gL.glEnableClientState(32888);
            }
            gL.glClientActiveTexture(33984);
            gL.glActiveTexture(33984);
            vo_12.n(db_22);
            qp_22.bf(false);
        } else if (n3 != 0) {
            vo_12.n(db_22);
        }
        gL.glDrawElements(7, this.kA, 5123, this.az.aWZ());
    }

    public final void a(int n2, ef_1 ef_12) {
        this.bBf[n2] = true;
        this.bBj[n2] = ef_12;
    }

    public final void setMaterial(aPb aPb2) {
        this.bBg = true;
        this.tJ = aPb2;
    }

    public final void o(ArrayList arrayList) {
        this.cwr.clear();
        for (int j = 0; j < arrayList.size(); ++j) {
            this.cwr.add(arrayList.get(j));
        }
        this.bBh = true;
    }

    public final void a(air air2) {
        this.GB = air2;
        this.bBi = true;
    }

    public void b(air air2) {
        this.GC = air2;
        this.bBi = true;
    }

    public final void b(air air2, air air3) {
        this.GB = air2;
        this.GC = air3;
        this.bBi = true;
    }
}

