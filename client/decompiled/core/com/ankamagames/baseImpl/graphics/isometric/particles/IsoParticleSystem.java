/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.isometric.particles;

import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.util.ArrayList;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public abstract class IsoParticleSystem
extends ParticleSystem
implements qq_1,
xw_0 {
    private int bPM;
    private byte bPN;

    public IsoParticleSystem(boolean bl2) {
        super(bl2);
        ajh_2.b(this);
    }

    public void a(aba_2 aba_22) {
        float f;
        float f2;
        float f3;
        int n2 = this.aFz();
        if (n2 == 0) {
            return;
        }
        aba_22.b(this, this.cpB > 0.0f);
        for (int j = 0; j < n2; ++j) {
            GeometryMesh geometryMesh = (GeometryMesh)this.ma(j);
            geometryMesh.ab().clear();
        }
        float f4 = this.getX();
        float f5 = this.getY();
        float f6 = this.id();
        this.dPy = f4;
        this.dPz = f5;
        this.dPA = f6;
        this.bsF = 0.0f;
        this.EN = Integer.MAX_VALUE;
        this.EO = Integer.MAX_VALUE;
        this.EP = Integer.MIN_VALUE;
        this.EQ = Integer.MIN_VALUE;
        float f7 = (float)aba_22.aNA();
        if (this.Lh) {
            f3 = f4;
            f2 = f5;
            f = f6;
        } else {
            f = 0.0f;
            f2 = 0.0f;
            f3 = 0.0f;
        }
        this.a(this.bZI, f3, f2, f, f7, aba_22, 0);
        this.alW();
        this.bZT = null;
    }

    private void a(Particle particle, float f, float f2, float f3, float f4, aba_2 aba_22, int n2) {
        float f5;
        if (particle == null) {
            return;
        }
        if (particle != this.bZI && particle.IT > 0.004f) {
            float f6 = particle.Hk + f;
            float f7 = particle.Hl + f2;
            float f8 = particle.Hm + f3;
            float f9 = (float)aba_22.i(f6, f7);
            f5 = (float)(aba_22.j(f6, f7) + (double)(f8 * f4));
            this.b(particle, f9, f5, n2);
        }
        if (particle.Li == null) {
            return;
        }
        int n3 = particle.Li.size();
        for (int j = 0; j < n3; ++j) {
            float f10;
            float f11;
            Emitter emitter = (Emitter)particle.Li.get(j);
            ArrayList arrayList = emitter.uA;
            if (arrayList == null) continue;
            if (!emitter.dnJ.Lh) {
                f5 = f;
                f11 = f2;
                f10 = f3;
            } else {
                f5 = f + particle.Hk;
                f11 = f2 + particle.Hl;
                f10 = f3 + particle.Hm;
            }
            int n4 = arrayList.size();
            for (int i2 = 0; i2 < n4; ++i2) {
                Particle particle2 = (Particle)arrayList.get(i2);
                this.a(particle2, f5, f11, f10, f4, aba_22, n2 + 1);
            }
        }
    }

    public void agK() {
    }

    public int Ge() {
        return this.bPM;
    }

    public void if(int n2) {
        this.bPM = n2;
    }

    public void an(byte by) {
    }

    public byte agL() {
        return this.bPN;
    }

    public void ao(byte by) {
        this.bPN = by;
    }

    public void agM() {
        super.agM();
        akK.cDL.f(this);
    }

    public float zR() {
        return this.getX() - this.getY();
    }

    public float zS() {
        return -(this.getX() + this.getY());
    }

    public boolean zT() {
        return false;
    }

    public int zU() {
        return this.Ge();
    }

    protected void af() {
        super.af();
        ajh_2.b(this);
    }

    public boolean Gg() {
        return ajh_2.c(this);
    }
}

