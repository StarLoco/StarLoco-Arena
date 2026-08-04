/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import com.sun.opengl.util.texture.TextureCoords;

/*
 * Renamed from aMJ
 */
public class amj_0
extends ye_1 {
    public float aaS = 1.0f;
    public int mN = -1;
    public final afd_0 cea;
    private float dXZ;

    public amj_0(int n2, float f, float f2, float f3, float f4, float f5, float f6, boolean bl2, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, afd_0 afd_02, float f17, int n3) {
        super(n2, f, f2, f3, f4, f5, f6, bl2, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16);
        this.cea = afd_02;
        this.aaS = f17;
        this.mN = n3;
    }

    public Particle b(ParticleSystem particleSystem) {
        if (particleSystem.isEditable() && !this.g(particleSystem)) {
            return null;
        }
        return super.b(particleSystem);
    }

    public boolean isSequence() {
        return true;
    }

    public boolean g(ParticleSystem particleSystem) {
        assert (this.aAj) : "Texture is already up to date";
        assert (particleSystem.isEditable());
        afw_2 afw_22 = particleSystem.amb().lk(this.EI());
        if (afw_22 == null) {
            return false;
        }
        this.aAj = false;
        return true;
    }

    public final TextureCoords pp(int n2) {
        assert (n2 >= 0);
        this.dXZ += this.aaS * (float)n2;
        int n3 = this.cea.aev();
        if (this.dXZ >= (float)n3) {
            this.dXZ -= (float)n3;
            if (this.mN > 0) {
                --this.mN;
            }
        }
        if (this.mN == 0) {
            return this.cea.bn((short)n3);
        }
        return this.cea.bn((short)this.dXZ);
    }
}

