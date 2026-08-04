/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.geometry.Geometry;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.Particle;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.util.ArrayList;

public class ald {
    protected final lb_0 cEG = new lb_0();
    protected final lb_0 cEH = new lb_0();
    static final /* synthetic */ boolean bb;
    final /* synthetic */ ParticleSystem qd;

    public ald(ParticleSystem particleSystem) {
        this.qd = particleSystem;
    }

    public final afw_2 lk(int n2) {
        return (afw_2)this.cEG.get(n2);
    }

    public final void a(int n2, afw_2 afw_22, boolean bl2) {
        this.cEG.c(n2, afw_22);
        this.b(n2, afw_22, bl2);
    }

    public final void ll(int n2) {
        this.cEG.remove(n2);
    }

    private void b(int n2, afw_2 afw_22, boolean bl2) {
        long l2 = this.qd.ja(n2);
        ef_1 ef_12 = cx_0.JY().bt(l2);
        if (ef_12 != null) {
            ef_12.HE();
            this.cEH.c(n2, ef_12);
            return;
        }
        kf_0 kf_02 = aon_2.b(afw_22.getData(), afw_22.getWidth(), afw_22.getHeight(), 32);
        aon_2 aon_22 = new aon_2(pw.acb, kf_02);
        ef_12 = cx_0.JY().a(arX.cQT.iE(), l2, aon_22, bl2);
        ef_12.HE();
        this.cEH.c(n2, ef_12);
        kf_02.HF();
        aon_22.HF();
    }

    public void a(Emitter emitter) {
        int n2;
        ef_1 ef_12;
        Geometry geometry;
        int n3;
        if (!bb && !this.qd.isEditable()) {
            throw new AssertionError();
        }
        int n4 = emitter.dnJ.btz * 4;
        ArrayList arrayList = emitter.dnJ.btv;
        int n5 = arrayList.size();
        if (emitter.dnI == null) {
            emitter.dnI = new ArrayList(n5);
        }
        for (n3 = 0; n3 < n5; ++n3) {
            ye_1 ye_12 = (ye_1)arrayList.get(n3);
            geometry = (GLGeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
            ((GeometryMesh)geometry).a(jB.Ba, n4, n4);
            ((GeometryMesh)geometry).ac().c(ParticleSystem.bZC, 0, n4);
            geometry.a(air.cya, air.cye);
            ef_12 = (ef_1)ParticleSystem.c((ParticleSystem)this.qd).cEH.get(ye_12.EI());
            this.qd.a(geometry, ef_12, ParticleSystem.ta);
            geometry.a(this.qd.tK, this.qd.bZG);
            emitter.dnI.add(geometry);
        }
        n3 = this.qd.aFz();
        for (n2 = 0; n2 < n3; ++n2) {
            geometry = this.qd.ma(n2);
            geometry.HE();
            ef_12 = this.qd.ln(n2);
            ef_12.HE();
        }
        ParticleSystem.d(this.qd);
        if (this.qd.bZI.Li != null) {
            n2 = this.qd.bZI.Li.size();
            for (int j = 0; j < n2; ++j) {
                this.b((Emitter)this.qd.bZI.Li.get(j));
            }
        }
        for (n2 = 0; n2 < n3; ++n2) {
            ef_1 ef_13 = this.qd.ln(n2);
            ef_13.HF();
        }
    }

    private void b(Emitter emitter) {
        afB afB2;
        int n2;
        ArrayList arrayList = emitter.dnJ.btv;
        int n3 = arrayList.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ye_1 ye_12 = (ye_1)arrayList.get(n2);
            afB2 = (ef_1)ParticleSystem.c((ParticleSystem)this.qd).cEH.get(ye_12.EI());
            GeometryMesh geometryMesh = (GeometryMesh)emitter.dnI.get(n2);
            this.qd.a(geometryMesh, (ef_1)afB2, ParticleSystem.ta);
        }
        if (emitter.uA != null) {
            n2 = emitter.uA.size();
            for (int j = 0; j < n2; ++j) {
                afB2 = (Particle)emitter.uA.get(j);
                if (!((Particle)afB2).ro()) continue;
                int n4 = ((Particle)afB2).Li.size();
                for (int i2 = 0; i2 < n4; ++i2) {
                    this.b((Emitter)((Particle)afB2).Li.get(i2));
                }
            }
        }
    }

    static {
        bb = !ParticleSystem.class.desiredAssertionStatus();
    }
}

