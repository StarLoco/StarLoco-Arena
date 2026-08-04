/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;

public class ayL
extends ye_1 {
    public ayL(int n2, float f, float f2, float f3, float f4, float f5, float f6, boolean bl2, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) {
        super(n2, f, f2, f3, f4, f5, f6, bl2, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16);
    }

    public Particle b(ParticleSystem particleSystem) {
        if (particleSystem.isEditable() && this.aAj && !this.g(particleSystem)) {
            return null;
        }
        return super.b(particleSystem);
    }

    public boolean isSequence() {
        return false;
    }

    public boolean g(ParticleSystem particleSystem) {
        assert (this.aAj) : "Texture is already up to date";
        assert (particleSystem.isEditable());
        afw_2 afw_22 = particleSystem.amb().lk(this.EI());
        if (afw_22 == null) {
            return false;
        }
        int n2 = afw_22.getWidth();
        int n3 = afw_22.getHeight();
        int n4 = ej_0.aq(n2);
        int n5 = ej_0.aq(n3);
        this.KV = (float)n2 * 0.5f;
        this.KW = (float)n3 * 0.5f;
        this.Lb = (float)n3 / (float)n5;
        this.Lc = (float)n2 / (float)n4;
        this.aAj = false;
        return true;
    }
}

