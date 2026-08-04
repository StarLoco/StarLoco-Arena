/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;

/*
 * Renamed from aFp
 */
public final class afp_2
extends vz_1 {
    private akq_1[] AD = new akq_1[9];
    private boolean AE = false;
    private Entity3D AH;
    private boolean ba = true;
    private vP aZ;

    public float getDeltaAngle() {
        return 0.0f;
    }

    public void setDeltaAngle(float f) {
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
    }

    public void setColor(vP vP2) {
        if (this.aZ == vP2) {
            return;
        }
        this.aZ = vP2;
        this.aq();
    }

    public vP getColor() {
        return this.aZ;
    }

    public void setModulationColor(vP vP2) {
        super.setModulationColor(vP2);
        this.aq();
    }

    private void aq() {
        float f;
        float f2;
        float f3;
        float f4;
        if (this.aZ == null) {
            f4 = 1.0f;
            f3 = 1.0f;
            f2 = 1.0f;
            f = 1.0f;
        } else {
            f = this.aZ.Cp();
            f2 = this.aZ.Cq();
            f3 = this.aZ.Cr();
            f4 = this.aZ.getAlpha();
        }
        if (this.AC != null) {
            f *= this.AC.Cp();
            f2 *= this.AC.Cq();
            f3 *= this.AC.Cr();
            f4 *= this.AC.getAlpha();
        }
        this.AH.setColor(f, f2, f3, f4);
    }

    public void setFullCirclePercentage(float f) {
    }

    public float getFullCirclePercentage() {
        return 0.0f;
    }

    public akq_1 alu() {
        return this.AD[5];
    }

    public void b(akq_1 akq_12) {
        this.AD[5] = akq_12;
        this.alD();
    }

    public akq_1 alv() {
        return this.AD[1];
    }

    public void c(akq_1 akq_12) {
        this.AD[1] = akq_12;
        this.alD();
    }

    public akq_1 alw() {
        return this.AD[2];
    }

    public void d(akq_1 akq_12) {
        this.AD[2] = akq_12;
        this.alD();
    }

    public akq_1 alx() {
        return this.AD[0];
    }

    public void e(akq_1 akq_12) {
        this.AD[0] = akq_12;
        this.alD();
    }

    public akq_1 aly() {
        return this.AD[7];
    }

    public void f(akq_1 akq_12) {
        this.AD[7] = akq_12;
        this.alD();
    }

    public akq_1 alz() {
        return this.AD[8];
    }

    public void g(akq_1 akq_12) {
        this.AD[8] = akq_12;
        this.alD();
    }

    public akq_1 alA() {
        return this.AD[6];
    }

    public void h(akq_1 akq_12) {
        this.AD[6] = akq_12;
        this.alD();
    }

    public akq_1 alB() {
        return this.AD[3];
    }

    public void i(akq_1 akq_12) {
        this.AD[3] = akq_12;
        this.alD();
    }

    public akq_1 alC() {
        return this.AD[4];
    }

    public void j(akq_1 akq_12) {
        this.AD[4] = akq_12;
        this.alD();
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19, akq_1 akq_110) {
        this.AD[0] = akq_12;
        this.AD[1] = akq_13;
        this.AD[2] = akq_14;
        this.AD[3] = akq_15;
        this.AD[4] = akq_16;
        this.AD[5] = akq_17;
        this.AD[6] = akq_18;
        this.AD[7] = akq_19;
        this.AD[8] = akq_110;
        this.alD();
    }

    public void setPixmaps(akq_1 akq_12) {
        this.AD[5] = akq_12;
        this.alD();
    }

    public void setPixmaps(akq_1[] akq_1Array) {
        this.AD[0] = akq_1Array[0];
        this.AD[1] = akq_1Array[1];
        this.AD[2] = akq_1Array[2];
        this.AD[3] = akq_1Array[3];
        this.AD[4] = akq_1Array[4];
        this.AD[5] = akq_1Array[5];
        this.AD[6] = akq_1Array[6];
        this.AD[7] = akq_1Array[7];
        this.AD[8] = akq_1Array[8];
        this.alD();
    }

    private void alD() {
        if (this.AD[4] == null) {
            this.AE = false;
            return;
        }
        int n2 = 0;
        for (int j = this.AD.length - 1; j >= 0; --j) {
            if (this.AD[j] == null) continue;
            ++n2;
        }
        if (n2 != 1 && n2 != 9) {
            this.AE = false;
            return;
        }
        this.AE = true;
    }

    public void a(int n2, int n3, int n4, int n5, float f) {
        if (!this.AE) {
            return;
        }
        this.AH.clear();
        int[] nArray = new int[3];
        int[] nArray2 = new int[3];
        n4 = (int)((float)n4 * (this.ba ? f : 1.0f));
        n5 = (int)((float)n5 * (this.ba ? 1.0f : f));
        nArray[0] = this.AD[0].getWidth();
        nArray[2] = this.AD[2].getWidth();
        nArray[1] = Math.max(0, n4 - (nArray[0] + nArray[2]));
        nArray2[0] = this.AD[0].getHeight();
        nArray2[2] = this.AD[6].getHeight();
        nArray2[1] = Math.max(0, n5 - (nArray2[0] + nArray2[2]));
        int n6 = n5 + n3;
        for (int j = 0; j < 3; ++j) {
            int n7 = n2;
            for (int i2 = 0; i2 < 3; ++i2) {
                this.a(n7, n6, nArray[i2], nArray2[j], this.AD[j * 3 + i2]);
                n7 += nArray[i2];
            }
            n6 -= nArray2[j];
        }
    }

    public final Entity getEntity() {
        return this.AH;
    }

    public final void b() {
        assert (this.AH == null);
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
    }

    public final void j() {
        super.j();
        this.AH.HF();
        this.AH = null;
        this.aZ = null;
    }

    protected void a(int n2, int n3, int n4, int n5, akq_1 akq_12) {
        if (n4 == 0 || n5 == 0) {
            return;
        }
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        gLGeometrySprite.x(n3, n2);
        gLGeometrySprite.setSize(n4, n5);
        gLGeometrySprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        ef_1 ef_12 = null;
        if (akq_12 != null) {
            gLGeometrySprite.a(akq_12.Hy(), akq_12.Hw(), akq_12.Hz(), akq_12.Hx(), akq_12.getRotation());
            ef_12 = akq_12.jI();
        }
        this.AH.a(gLGeometrySprite, ef_12, null);
    }
}

