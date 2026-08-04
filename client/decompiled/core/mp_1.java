/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.util.ArrayList;

/*
 * Renamed from Mp
 */
public class mp_1
extends agu_1 {
    public int aW;
    public ArrayList btu;
    public final ArrayList btv = new ArrayList();
    public apI btw;
    public final float btx;
    public final float bty;
    public final int btz;
    public final int btA;
    public final float auL;
    public final float btB;
    public final float btC;
    public final float btD;
    public final float btE;
    public final float btF;
    public final float btG;
    public final float btH;
    public final float btI;
    public final float btJ;
    public final float btK;
    public final float btL;
    public final float btM;
    public final float btN;
    public final float btO;
    public final float btP;
    public boolean Lh;

    public mp_1(float f, float f2, int n2, int n3, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18, boolean bl2) {
        this.btx = f;
        this.bty = f2;
        this.btz = n2;
        this.btA = n3;
        this.auL = f3;
        this.btB = f4;
        this.btC = f5;
        this.btD = f6;
        this.btE = f7;
        this.btF = f8;
        this.btG = f9;
        this.btH = f10;
        this.btI = f11;
        this.btJ = f12;
        this.btK = f13;
        this.btL = f14;
        this.btM = f15;
        this.btN = f16;
        this.btO = f17;
        this.btP = f18;
        this.Lh = bl2;
    }

    public void reset() {
        this.btv.clear();
        this.awE();
        this.btw = null;
        this.Lh = false;
    }

    public void a(ye_1 ye_12) {
        this.btv.add(ye_12);
    }

    public void a(ParticleSystem particleSystem, Particle particle, Particle particle2) {
        this.a(particle, particle2);
        if (!particle.Lh) {
            particle2.Hk += particle.getX();
            particle2.Hl += particle.getY();
            particle2.Hm += particle.id();
        }
        if (this.btu != null) {
            particle2.a(particleSystem, this.btu);
        }
    }

    public void a(Particle particle, Particle particle2) {
        float f = this.btE;
        float f2 = this.btF;
        float f3 = this.btG;
        float f4 = this.btK;
        float f5 = this.btL;
        float f6 = this.btM;
        if (this.btH != 0.0f) {
            f += (ej_0.hL() - 0.5f) * this.btH;
        }
        if (this.btI != 0.0f) {
            f2 += (ej_0.hL() - 0.5f) * this.btI;
        }
        if (this.btJ != 0.0f) {
            f3 += (ej_0.hL() - 0.5f) * this.btJ;
        }
        if (this.btN != 0.0f) {
            f4 += (ej_0.hL() - 0.5f) * this.btN;
        }
        if (this.btO != 0.0f) {
            f5 += (ej_0.hL() - 0.5f) * this.btO;
        }
        if (this.btP != 0.0f) {
            f6 += (ej_0.hL() - 0.5f) * this.btP;
        }
        particle2.Hk = f;
        particle2.Hl = f2;
        particle2.Hm = f3;
        particle2.KM = f4;
        particle2.KN = f5;
        particle2.KO = f6;
        particle2.KR = this.btC + ej_0.hL() * this.btD;
        particle2.KQ = 0.0f;
        particle2.Lh = this.Lh;
        particle2.Lf = particle;
    }

    public void a(mp_1 mp_12) {
        if (this.btu == null) {
            this.btu = new ArrayList();
        }
        this.btu.add(mp_12);
    }

    public int Yq() {
        if (this.btv.size() == 0) {
            return -1;
        }
        return ej_0.am(this.btv.size());
    }

    public void a(apI apI2) {
        this.btw = apI2;
    }

    public Emitter Yr() {
        Emitter emitter = (Emitter)yW.FL().a(Emitter.it(), Emitter.class);
        emitter.c(this);
        return emitter;
    }
}

