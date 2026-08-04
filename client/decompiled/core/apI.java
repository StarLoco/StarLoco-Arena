/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;

public class apI
extends agu_1 {
    public final float IQ;
    public final float IR;
    public final float IS;
    public final float aHh;
    public final float cMH;

    public apI(float f, float f2, float f3, float f4, float f5) {
        this.IQ = f;
        this.IR = f2;
        this.IS = f3;
        this.aHh = f4;
        this.cMH = f5;
    }

    public lh_0 aDw() {
        lh_0 lh_02 = new lh_0();
        lh_02.c(this.IQ * this.aHh, this.IR * this.aHh, this.IS * this.aHh);
        lh_02.u(this.cMH);
        return lh_02;
    }

    public Particle aDx() {
        Particle particle = (Particle)yW.FL().a(Particle.it(), Particle.class);
        particle.IQ = this.IQ;
        particle.IR = this.IR;
        particle.IS = this.IS;
        particle.IT = this.aHh;
        particle.KV = this.cMH;
        particle.KW = this.cMH;
        particle.KT = 1.0f;
        return particle;
    }
}

