/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.isometric.highlight.HighLightEntity;
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class aaR {
    private static final Logger a = Logger.getLogger(aaR.class);
    String m_name;
    public int cgL;
    private aPb tJ;
    private final cp_2 cgM = new cp_2();
    final se_2 cgN = new se_2();
    private boolean aQv = true;
    private final adz_1 avv = new adz_1();
    private ef_1 tl;
    private fa_0 cgO;
    private static final apx cgP = new aDc();

    aaR(String string, ef_1 ef_12, adz_1 adz_12, fa_0 fa_02) {
        assert (ef_12 != null);
        this.m_name = string;
        this.cgO = fa_02;
        this.tJ = aPb.aYI();
        this.tl = ef_12;
        this.tl.HE();
        this.avv.b(adz_12);
    }

    public Iterator apt() {
        return new ar_1(this);
    }

    public fa_0 apu() {
        return this.cgO;
    }

    public void a(adz_1 adz_12, ef_1 ef_12, fa_0 fa_02) {
        boolean bl2 = !adz_12.a(this.avv) || !ef_12.lB(0).pl().a(this.tl.lB(0).pl());
        this.tl.HF();
        this.tl = ef_12;
        this.tl.HE();
        this.avv.b(adz_12);
        this.cgO = fa_02;
        akz_0 akz_02 = this.cgM.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            HighLightEntity highLightEntity = (HighLightEntity)akz_02.value();
            highLightEntity.a(0, this.tl);
            if (!bl2) continue;
            GeometryMesh geometryMesh = (GeometryMesh)highLightEntity.ma(0);
            this.b(geometryMesh);
            highLightEntity.aRc = false;
        }
    }

    public boolean isEmpty() {
        return this.cgM.isEmpty();
    }

    public float[] Aa() {
        return this.tJ.aYK();
    }

    public void q(float[] fArray) {
        this.tJ.G(fArray);
        akz_0 akz_02 = this.cgM.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            HighLightEntity highLightEntity = (HighLightEntity)akz_02.value();
            GeometryMesh geometryMesh = (GeometryMesh)highLightEntity.ma(0);
            geometryMesh.setColor(fArray[0], fArray[1], fArray[2], fArray[3]);
            highLightEntity.aRc = false;
        }
    }

    final adz_1 apv() {
        return this.avv;
    }

    public boolean isVisible() {
        return this.aQv;
    }

    public void setVisible(boolean bl2) {
        this.aQv = bl2;
    }

    boolean m(long l2) {
        return this.cgM.m(l2);
    }

    public boolean x(int n2, int n3, short s) {
        return this.m(wn_2.o(n2, n3, s));
    }

    final HighLightEntity G(int n2, int n3, int n4) {
        return this.dt(wn_2.o(n2, n3, n4));
    }

    HighLightEntity dt(long l2) {
        return (HighLightEntity)this.cgM.t(l2);
    }

    public void clear() {
        if (!this.cgM.isEmpty()) {
            this.cgM.a(cgP);
        }
        this.cgM.clear();
    }

    public void y(int n2, int n3, short s) {
        long l2 = wn_2.o(n2, n3, s);
        this.ct(l2);
    }

    public void z(int n2, int n3, short s) {
        long l2 = wn_2.o(n2, n3, s);
        this.l(l2);
    }

    void ct(long l2) {
        if (this.cgM.t(l2) != null) {
            return;
        }
        assert (this.avv != null);
        try {
            HighLightEntity highLightEntity = (HighLightEntity)yW.FL().a(HighLightEntity.it(), HighLightEntity.class);
            GLGeometryMesh gLGeometryMesh = (GLGeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
            assert (highLightEntity.avb() == 0);
            assert (gLGeometryMesh.avb() == 0);
            highLightEntity.HE();
            gLGeometryMesh.HE();
            gLGeometryMesh.a(jB.Ba, 4, 4);
            this.b(gLGeometryMesh);
            gLGeometryMesh.setColor(0.5f, 0.5f, 0.5f, 1.0f);
            ams_1 ams_12 = gLGeometryMesh.ac();
            ams_12.add(0);
            ams_12.add(1);
            ams_12.add(2);
            ams_12.add(3);
            avz avz2 = new avz();
            avz2.OH();
            highLightEntity.aUM().a(avz2);
            aPb aPb2 = aPb.enf;
            aPb2.H(0.0f, 0.0f, 0.0f, 0.0f);
            highLightEntity.a(gLGeometryMesh, this.tl, aPb2);
            highLightEntity.b(ahA.axi().ih("transform"));
            highLightEntity.oM(-180157682);
            highLightEntity.at(2.0f);
            this.cgM.a(l2, highLightEntity);
            wn_2.Dj().a(this.m_name, l2);
        }
        catch (Exception exception) {
            a.error((Object)("probl\u00e8me cr\u00e9ation highlight entity layer=" + this.m_name), (Throwable)exception);
        }
    }

    private void b(GeometryMesh geometryMesh) {
        VertexBufferPCT vertexBufferPCT = geometryMesh.ab();
        vertexBufferPCT.dz(4);
        float f = (float)this.avv.getX() / (float)this.tl.lC(0).getX();
        float f2 = (float)this.avv.getY() / (float)this.tl.lC(0).getY();
        vertexBufferPCT.a(0, 0.0f, 0.0f);
        vertexBufferPCT.a(1, 0.0f, f2);
        vertexBufferPCT.a(2, f, f2);
        vertexBufferPCT.a(3, f, 0.0f);
    }

    void l(long l2) {
        if (this.cgM.isEmpty()) {
            return;
        }
        HighLightEntity highLightEntity = (HighLightEntity)this.cgM.u(l2);
        if (highLightEntity != null) {
            highLightEntity.HF();
            highLightEntity.aRc = false;
            wn_2.Dj().b(this.m_name, l2);
        }
    }

    void c(apx apx2) {
        if (!this.cgM.isEmpty()) {
            this.cgM.a(apx2);
        }
    }

    void release() {
        this.clear();
        this.tJ.release();
        this.tl.HF();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.m_name).append(" cellCount=").append(this.cgM.size());
        return stringBuilder.toString();
    }

    static /* synthetic */ cp_2 c(aaR aaR2) {
        return aaR2.cgM;
    }
}

