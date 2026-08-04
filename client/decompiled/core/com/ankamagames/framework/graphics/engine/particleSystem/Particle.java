/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.particleSystem;

import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import com.sun.opengl.util.texture.TextureCoords;
import java.util.ArrayList;

public class Particle
extends ams_2 {
    public float Hk;
    public float Hl;
    public float Hm;
    public float KJ;
    public float KK;
    public float KL;
    public float KM;
    public float KN;
    public float KO;
    public float KP;
    public float KQ;
    public float KR;
    public float KS;
    public float KT;
    public float KU;
    public float IQ;
    public float IR;
    public float IS;
    public float IT;
    public float KV;
    public float KW;
    public float KX;
    public float KY;
    public float KZ;
    public float La;
    public float Lb;
    public float Lc;
    public int Ld;
    public ye_1 Le;
    public Particle Lf;
    public Emitter Lg;
    public boolean Lh;
    public ArrayList Li;
    private static final int qL = Particle.L(Particle.class);

    public Particle() {
        this.initialize();
    }

    public void reset() {
        if (this.Li != null) {
            int n2 = this.Li.size();
            for (int j = 0; j < n2; ++j) {
                Emitter emitter = (Emitter)this.Li.get(j);
                emitter.clear();
            }
            this.Li.clear();
            this.Li = null;
        }
        this.Le = null;
    }

    public void a(ParticleSystem particleSystem, ArrayList arrayList) {
        int n2 = arrayList.size();
        if (this.Li == null) {
            this.Li = new ArrayList(n2);
        }
        for (int j = 0; j < n2; ++j) {
            Emitter emitter = ((mp_1)arrayList.get(j)).Yr();
            emitter.Lf = this;
            this.Li.add(emitter);
            if (!particleSystem.isEditable()) continue;
            particleSystem.amb().a(emitter);
        }
    }

    public void a(ParticleSystem particleSystem, float f) {
        this.KQ += f;
        this.Hk += this.KM * f;
        this.Hl += this.KN * f;
        this.Hm += this.KO * f;
        if (this.Le != null && this.Le.isSequence()) {
            amj_0 amj_02 = (amj_0)this.Le;
            TextureCoords textureCoords = amj_02.pp((int)(1000.0f * f));
            this.KZ = textureCoords.top();
            this.La = textureCoords.left();
            this.Lb = textureCoords.bottom();
            this.Lc = textureCoords.right();
        }
        if (this.Li == null) {
            return;
        }
        int n2 = this.Li.size();
        for (int j = 0; j < n2; ++j) {
            Emitter emitter = (Emitter)this.Li.get(j);
            emitter.a(particleSystem, f);
        }
    }

    public boolean isAlive() {
        if (this.KQ <= this.KR && this.KR != Float.MAX_VALUE) {
            return true;
        }
        if (this.Li != null && this.Lf == null) {
            int n2 = this.Li.size();
            for (int j = 0; j < n2; ++j) {
                Emitter emitter = (Emitter)this.Li.get(j);
                if (!emitter.isAlive()) continue;
                return true;
            }
        }
        return false;
    }

    public void a(ParticleSystem particleSystem) {
        if (this.Li == null) {
            return;
        }
        int n2 = this.Li.size();
        for (int j = 0; j < n2; ++j) {
            ArrayList arrayList;
            int n3;
            Emitter emitter = (Emitter)this.Li.get(j);
            emitter.aLZ();
            if (emitter.dnI != null) {
                int n4 = emitter.dnI.size();
                for (n3 = 0; n3 < n4; ++n3) {
                    GeometryMesh geometryMesh = (GeometryMesh)emitter.dnI.get(n3);
                    particleSystem.c(geometryMesh);
                }
                emitter.dnI.clear();
            }
            if ((arrayList = emitter.uA) != null) {
                n3 = arrayList.size();
                for (int i2 = 0; i2 < n3; ++i2) {
                    Particle particle = (Particle)arrayList.get(i2);
                    particle.a(particleSystem);
                }
            }
            emitter.HF();
        }
        this.Li.clear();
        this.Li = null;
    }

    public boolean ro() {
        return this.Li != null;
    }

    public static int it() {
        return qL;
    }

    public float getX() {
        if (this.Lh || this.Lf == null || this.Lf.Lh) {
            return this.Hk;
        }
        return !this.Lf.Lh ? 0.0f : this.Lf.getX();
    }

    public float getY() {
        if (this.Lh || this.Lf == null || this.Lf.Lh) {
            return this.Hl;
        }
        return !this.Lf.Lh ? 0.0f : this.Lf.getY();
    }

    public float id() {
        if (this.Lh || this.Lf == null || this.Lf.Lh) {
            return this.Hm;
        }
        return !this.Lf.Lh ? 0.0f : this.Lf.id();
    }

    protected void af() {
        this.initialize();
    }

    protected void ag() {
        assert (this.Li == null);
    }

    private void initialize() {
        this.KJ = Float.NaN;
        this.KK = Float.NaN;
        this.KL = Float.NaN;
        this.Lf = null;
        this.Le = null;
        this.Lg = null;
        this.KP = 0.0f;
    }
}

