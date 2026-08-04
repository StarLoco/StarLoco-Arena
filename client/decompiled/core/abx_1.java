/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from aBx
 */
public final class abx_1 {
    private EntityGroup arC;
    private Entity3D drw;
    private Entity3D drx;
    private avz dry;
    private avz drz;
    private avz drA;
    protected vP AC = null;
    protected akq_1 arn = null;
    protected akq_1 dop = null;
    protected int drB = 0;
    protected int drC = 0;
    protected int drD = 0;
    protected int drE = 0;
    protected lb_0 dou;
    protected float drF = 0.0f;
    protected float drG = 0.0f;
    protected float bck = 1.0f;
    protected float vy;
    protected float vz;
    protected int drH;
    protected int drI;
    protected int bsW = 0;
    protected int bsX = 0;
    protected int fc = 0;
    protected int fb = 0;
    protected kx_1 aFh = kx_1.FR;
    protected boolean AE = false;
    private static final short arB = 3855;

    public int getXOffset() {
        return this.bsW;
    }

    public void setXOffset(int n2) {
        this.bsW = n2;
    }

    public int getYOffset() {
        return this.bsX;
    }

    public void setYOffset(int n2) {
        this.bsX = n2;
    }

    public void R(float f, float f2) {
        this.dry.e(f, f2, 0.0f);
        this.aNu();
    }

    public int getHeight() {
        return this.fc;
    }

    public void setHeight(int n2) {
        this.fc = n2;
    }

    public int getWidth() {
        return this.fb;
    }

    public void setWidth(int n2) {
        this.fb = n2;
    }

    public float QJ() {
        return this.bck;
    }

    public void cc(int n2, int n3) {
        this.fb = n2;
        this.fc = n3;
    }

    public void setSize(int n2, int n3) {
        this.drH = n2;
        this.drI = n3;
    }

    public void bn(float f) {
        this.bck = f;
        this.dry.m(f, f, f);
        this.aNu();
    }

    public float HC() {
        return this.vy;
    }

    public void by(float f) {
        this.vy = f;
    }

    public float HD() {
        return this.vz;
    }

    public void bz(float f) {
        this.vz = f;
    }

    public kx_1 getShape() {
        return this.aFh;
    }

    public void setShape(kx_1 kx_12) {
        this.aFh = kx_12;
    }

    public void a(int n2, awi awi2) {
        this.dou.c(n2, awi2);
    }

    public void nb(int n2) {
        this.dou.remove(n2);
    }

    public void aMg() {
        this.dou.clear();
    }

    public void setPixmap(akq_1 akq_12) {
        this.arn = akq_12;
        this.AE = this.arn != null;
    }

    public void a(akq_1 akq_12, int n2, int n3, int n4, int n5) {
        this.dop = akq_12;
        this.drB = n2;
        this.drC = n3;
        this.drD = n4;
        this.drE = n5;
    }

    public akq_1 getPixmap() {
        return this.arn;
    }

    public boolean Gk() {
        return this.AE;
    }

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
    }

    public vP getModulationColor() {
        return this.AC;
    }

    public void a(vP vP2, int n2, boolean bl2) {
        if (n2 < 0 || n2 >= this.drx.aFz()) {
            return;
        }
        GeometryMesh geometryMesh = (GeometryMesh)this.drx.ma(n2);
        geometryMesh.setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), bl2 ? 0.6f : 0.3f);
    }

    public void l(int n2, float f) {
        if (n2 < 0 || n2 >= this.drx.aFz()) {
            return;
        }
        GeometryMesh geometryMesh = (GeometryMesh)this.drx.ma(n2);
        geometryMesh.b(f);
    }

    private void aNu() {
        this.drw.aUM().b(3, this.dry);
        this.drx.aUM().b(0, this.dry);
        this.drw.aUM().b(4, this.drz);
        this.drx.aUM().b(1, this.drz);
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        vP vP2;
        int n2 = insets.left + insets2.left + insets3.left;
        int n3 = insets.bottom + insets2.bottom + insets3.bottom;
        this.drw.clear();
        this.drx.clear();
        this.drz.e((float)dimension.getWidth() / 2.0f, (float)dimension.getHeight() / 2.0f, -10000.0f);
        this.aNu();
        vP vP3 = vP2 = this.AC == null ? vP.atL : this.AC;
        if (this.arn != null) {
            this.a(-this.fb / 2, this.fc / 2, this.fb, this.fc, this.arn, vP2, this.drw);
        }
        ll_0 ll_02 = this.dou.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            this.a(((awi)ll_02.value()).aJr(), ((awi)ll_02.value()).getColor(), 1.0f, 2.0f, jB.AV, true, this.drx);
            this.a(((awi)ll_02.value()).aJs(), ((awi)ll_02.value()).getColor(), 1.0f, 2.0f, jB.AV, false, this.drx);
        }
        ll_02 = this.dou.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            akq_1 akq_12 = ((awi)ll_02.value()).getPixmap();
            if (akq_12 == null) continue;
            int n4 = (int)((double)((float)akq_12.getWidth() / this.bck) / 1.5);
            int n5 = (int)((double)((float)akq_12.getHeight() / this.bck) / 1.5);
            this.a(((awi)ll_02.value()).aJt() - this.fb / 2, ((awi)ll_02.value()).aJu() + n5 + this.fc / 2, n4, n5, akq_12, vP2, this.drx);
        }
        if (this.dop != null) {
            this.a(-this.fb / 2 + this.drB, this.fc / 2 - this.drC, this.drD, this.drE, this.dop, vP2, this.drx);
        }
    }

    public void j() {
        this.arn = null;
        this.dop = null;
        this.dou.clear();
        this.dou = null;
        this.arC.HF();
        this.arC = null;
        this.drw.HF();
        this.drw = null;
        this.drx.HF();
        this.drw = null;
        this.AC = null;
    }

    public void b() {
        assert (this.arC == null);
        this.arC = (EntityGroup)yW.FL().a(EntityGroup.it(), EntityGroup.class);
        this.drw = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        this.drx = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        this.dry = new avz();
        this.drz = new avz();
        this.drA = new avz();
        avz avz2 = new avz();
        avz avz3 = new avz();
        avz2.f(new eu_2(new agu_0(0.0f, 0.0f, 1.0f), -0.7853982f));
        avz2.m(0.707f, 1.414f, 1.0f);
        avz3.f(new eu_2(new agu_0(1.0f, 0.0f, 0.0f), -1.0471976f));
        this.arC.i(this.drw);
        this.arC.i(this.drx);
        this.drw.aUM().a(avz2);
        this.drw.aUM().a(avz3);
        this.drw.aUM().a(this.drA);
        this.drw.aUM().a(this.dry);
        this.drx.aUM().a(this.dry);
        this.drw.aUM().a(this.drz);
        this.drx.aUM().a(this.drz);
        this.arC.a(new aok_0(this, null));
        this.arC.b(new kj_1(this, null));
        this.arC.a(new aqj_0(null));
        this.arC.b(new aff_0(null));
        this.dou = new lb_0();
    }

    public final Entity getEntity() {
        return this.arC;
    }

    private int a(float[] fArray, vP vP2, float f, float f2, jB jB2, boolean bl2, Entity3D entity3D) {
        int n2;
        GeometryMesh geometryMesh = (GeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
        int n3 = fArray.length / 2;
        VertexBufferPCT vertexBufferPCT = new VertexBufferPCT(n3);
        ams_1 ams_12 = new ams_1();
        ams_12.setSize(n3);
        short[] sArray = new short[n3];
        for (n2 = 0; n2 < sArray.length; n2 = (int)((short)(n2 + 1))) {
            sArray[n2] = n2;
        }
        ams_12.c(sArray, 0, n3);
        vertexBufferPCT.dz(n3);
        vertexBufferPCT.f(fArray);
        int n4 = vertexBufferPCT.fq();
        for (n2 = 0; n2 < n4; ++n2) {
            vertexBufferPCT.a(n2, vP2.Cp(), vP2.Cq(), vP2.Cr(), f);
        }
        geometryMesh.a(jB2, vertexBufferPCT, ams_12, false);
        geometryMesh.b(f2);
        geometryMesh.a(bl2);
        return entity3D.b(geometryMesh);
    }

    private void a(int n2, int n3, int n4, int n5, akq_1 akq_12, vP vP2, Entity3D entity3D) {
        if (n4 == 0 || n5 == 0) {
            return;
        }
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        gLGeometrySprite.x(n3, n2);
        gLGeometrySprite.setSize(n4, n5);
        gLGeometrySprite.setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        ef_1 ef_12 = null;
        if (akq_12 != null) {
            gLGeometrySprite.a(akq_12.Hy(), akq_12.Hw(), akq_12.Hz(), akq_12.Hx(), akq_12.getRotation());
            ef_12 = akq_12.jI();
        }
        entity3D.a(gLGeometrySprite, ef_12, null);
    }
}

