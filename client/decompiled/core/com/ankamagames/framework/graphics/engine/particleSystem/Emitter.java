/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.engine.particleSystem;

import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.util.ArrayList;

public class Emitter
extends ams_2 {
    public float fG;
    public float dnF;
    public float dnG;
    public boolean chi;
    public boolean dnH;
    public ArrayList dnI;
    public ArrayList uA;
    public Particle Lf;
    public mp_1 dnJ;
    public apI btw;
    public Particle dnK;
    private lh_0 dnL;
    private static final int qL = Emitter.L(Emitter.class);
    private float dnM = 0.0f;

    public Emitter() {
        this.clear();
    }

    public void c(mp_1 mp_12) {
        this.dnJ = mp_12;
        this.a(mp_12.btw);
    }

    public void a(ParticleSystem particleSystem, float f) {
        Particle particle;
        Object object;
        int n2;
        int n3;
        this.fG += f;
        if (this.uA != null) {
            n3 = this.uA.size();
            n2 = 0;
            while (n2 < n3) {
                object = (Particle)this.uA.get(n2);
                if (((Particle)object).isAlive()) {
                    ++n2;
                    continue;
                }
                ((Particle)object).a(particleSystem);
                ((afB)object).HF();
                this.uA.remove(n2);
                --n3;
            }
        }
        if (this.aLY()) {
            this.b(particleSystem, f);
        }
        if (this.btw != null) {
            if (this.btw.awC()) {
                n3 = this.btw.awD();
                for (n2 = 0; n2 < n3; ++n2) {
                    object = this.btw.kE(n2);
                    ((ua_0)object).b(f, this.Lf, this.dnK, particleSystem);
                }
            }
            if (this.btw.awA()) {
                n3 = this.btw.awB();
                for (n2 = 0; n2 < n3; ++n2) {
                    object = this.btw.kD(n2);
                    ((ua_0)object).b(0.03f, this.Lf, this.dnK, particleSystem);
                }
            }
            this.dnL.a(f, this, particleSystem);
        }
        if (this.uA == null) {
            return;
        }
        n3 = this.uA.size();
        if (this.dnJ.awA()) {
            if (n3 != 0) {
                this.dnF += f;
            }
            n2 = this.dnJ.awB();
            while (this.dnF >= 0.03f) {
                for (int j = 0; j < n3; ++j) {
                    particle = (Particle)this.uA.get(j);
                    float f2 = particle.KQ;
                    particle.KQ = particle.KP;
                    if (particle.KP <= particle.KR) {
                        for (int i2 = 0; i2 < n2; ++i2) {
                            ua_0 ua_02 = this.dnJ.kD(i2);
                            ua_02.b(0.03f, this.Lf, particle, particleSystem);
                        }
                    }
                    particle.KP += 0.03f;
                    particle.KQ = f2;
                }
                this.dnF -= 0.03f;
            }
        }
        if (this.dnJ.awC()) {
            n2 = this.dnJ.awD();
            for (int j = 0; j < n3; ++j) {
                particle = (Particle)this.uA.get(j);
                for (int i3 = 0; i3 < n2; ++i3) {
                    ua_0 ua_03 = this.dnJ.kE(i3);
                    ua_03.b(f, this.Lf, particle, particleSystem);
                }
                particle.a(particleSystem, f);
            }
        } else {
            for (n2 = 0; n2 < n3; ++n2) {
                Particle particle2 = (Particle)this.uA.get(n2);
                particle2.a(particleSystem, f);
            }
        }
    }

    public boolean isAlive() {
        if (this.aLX()) {
            return true;
        }
        if (this.aLY()) {
            return true;
        }
        if (this.uA == null) {
            return false;
        }
        int n2 = this.uA.size();
        for (int j = 0; j < n2; ++j) {
            Particle particle = (Particle)this.uA.get(j);
            if (particle.KQ < particle.KR) {
                return true;
            }
            if (!particle.ro()) continue;
            int n3 = particle.Li.size();
            for (int i2 = 0; i2 < n3; ++i2) {
                Emitter emitter = (Emitter)particle.Li.get(i2);
                if (!emitter.isAlive()) continue;
                return true;
            }
        }
        return false;
    }

    private boolean aLX() {
        return this.fG < this.dnJ.btx;
    }

    public boolean aLY() {
        if (this.Lf != null && this.Lf.KQ <= 0.0f) {
            return false;
        }
        if (this.dnH) {
            return false;
        }
        if (!this.chi) {
            return false;
        }
        if (this.dnJ.btx == 0.0f && this.dnJ.bty == 0.0f) {
            return true;
        }
        if (this.fG < this.dnJ.btx) {
            return false;
        }
        return !(this.fG > this.dnJ.bty);
    }

    public void b(ParticleSystem particleSystem, float f) {
        if (this.dnJ.auL == 0.0f) {
            return;
        }
        this.dnG += f;
        if (this.dnG < this.dnJ.auL + this.dnM) {
            return;
        }
        if (this.uA == null) {
            this.uA = new ArrayList(this.dnJ.btz);
        }
        if (this.uA.size() >= this.dnJ.btz) {
            return;
        }
        this.dnG -= this.dnJ.auL + this.dnM;
        this.dnM = this.dnJ.btB * ej_0.hL();
        for (int j = 0; j < this.dnJ.btA && this.uA.size() < this.dnJ.btz; ++j) {
            Particle particle;
            ye_1 ye_12;
            int n2 = this.dnJ.Yq();
            if (n2 < 0 || (ye_12 = (ye_1)this.dnJ.btv.get(n2)) == null || (particle = ye_12.b(particleSystem)) == null) continue;
            this.uA.add(particle);
            particle.Ld = n2;
            particle.Lg = this;
            this.dnJ.a(particleSystem, this.Lf, particle);
            ye_12.a(particle);
            if (!particle.ro()) continue;
            int n3 = particle.Li.size();
            for (int i2 = 0; i2 < n3; ++i2) {
                Emitter emitter = (Emitter)particle.Li.get(i2);
                if (!emitter.aLY()) continue;
                emitter.b(particleSystem, f);
            }
        }
    }

    public void eC(boolean bl2) {
        this.dnH = bl2;
        if (this.uA == null) {
            return;
        }
        int n2 = this.uA.size();
        for (int j = 0; j < n2; ++j) {
            Particle particle = (Particle)this.uA.get(j);
            if (!particle.ro()) continue;
            int n3 = particle.Li.size();
            for (int i2 = 0; i2 < n3; ++i2) {
                Emitter emitter = (Emitter)particle.Li.get(i2);
                emitter.eC(bl2);
            }
        }
    }

    public void reset() {
        if (this.uA != null) {
            int n2 = this.uA.size();
            for (int j = 0; j < n2; ++j) {
                Particle particle = (Particle)this.uA.get(j);
                if (particle.ro()) {
                    int n3 = particle.Li.size();
                    for (int i2 = 0; i2 < n3; ++i2) {
                        Emitter emitter = (Emitter)particle.Li.get(i2);
                        emitter.reset();
                    }
                }
                particle.HF();
            }
            this.uA.clear();
        }
        if (this.dnK != null) {
            this.dnK.Hk = 0.0f;
            this.dnK.Hl = 0.0f;
            this.dnK.Hm = 0.0f;
            this.dnK.KM = 0.0f;
            this.dnK.KN = 0.0f;
            this.dnK.KO = 0.0f;
            this.dnK.KQ = 0.0f;
            this.dnK.KR = 0.0f;
        }
        this.fG = 0.0f;
        this.chi = true;
        this.dnH = false;
        this.dnG = 0.0f;
    }

    public void clear() {
        if (this.uA != null) {
            int n2 = this.uA.size();
            for (int j = 0; j < n2; ++j) {
                Particle particle = (Particle)this.uA.get(j);
                if (particle.ro()) {
                    int n3 = particle.Li.size();
                    for (int i2 = 0; i2 < n3; ++i2) {
                        Emitter emitter = (Emitter)particle.Li.get(i2);
                        emitter.clear();
                    }
                }
                particle.HF();
            }
            this.uA.clear();
            this.uA = null;
        }
        if (this.dnI != null) {
            this.dnI.clear();
            this.dnI = null;
        }
        this.a(null);
        this.fG = 0.0f;
        this.dnF = 0.0f;
        this.chi = true;
        this.dnH = false;
        this.dnG = 0.0f;
        this.dnM = 0.0f;
        this.Lf = null;
        this.dnJ = null;
    }

    public void aLZ() {
        if (this.btw == null || this.dnL == null) {
            return;
        }
        this.dnL.unregister();
        this.dnL = null;
    }

    public void a(apI apI2) {
        if (apI2 == this.btw) {
            return;
        }
        if (this.dnL != null) {
            this.dnL.unregister();
            this.dnL = null;
            this.dnK.reset();
            this.dnK.HF();
            this.dnK = null;
        }
        this.btw = apI2;
        if (this.btw != null) {
            this.dnL = this.btw.aDw();
            this.dnL.register();
            this.dnK = this.btw.aDx();
            this.dnJ.a(this.Lf, this.dnK);
            this.dnK.KR -= 0.1f;
            this.dnK.Lf = this.Lf;
        }
    }

    public static int it() {
        return qL;
    }

    protected void af() {
    }

    protected void ag() {
        this.clear();
    }
}

