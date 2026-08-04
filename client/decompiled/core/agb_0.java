/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.Geometry;

/*
 * Renamed from aGb
 */
public class agb_0 {
    private Geometry dId;
    private ef_1 tl;
    private aPb tJ;
    private boolean GD;
    final /* synthetic */ Entity3D dIe;

    agb_0(Entity3D entity3D) {
        this.dIe = entity3D;
        this.tl = null;
        this.dId = null;
        this.GD = false;
    }

    public agb_0(Entity3D entity3D, Geometry geometry, ef_1 ef_12, aPb aPb2) {
        this.dIe = entity3D;
        this.setTexture(ef_12);
        this.d(geometry);
        this.setMaterial(aPb2);
    }

    public final void destroy() {
        if (this.tl != null) {
            this.tl.HF();
            this.tl = null;
        }
        if (this.dId != null) {
            this.dId.HF();
            this.dId = null;
        }
        this.tJ = null;
    }

    public final Geometry aSk() {
        return this.dId;
    }

    public final void d(Geometry geometry) {
        if (this.dId != null) {
            this.dId.HF();
        }
        this.dId = geometry;
    }

    public final ef_1 jI() {
        return this.tl;
    }

    public final void setTexture(ef_1 ef_12) {
        if (ef_12 != null) {
            ef_12.HE();
        }
        if (this.tl != null) {
            this.tl.HF();
        }
        this.tl = ef_12;
    }

    public final aPb getMaterial() {
        return this.tJ;
    }

    public final void setMaterial(aPb aPb2) {
        this.tJ = aPb2;
        this.GD = true;
    }

    public final void bj(boolean bl2) {
        this.GD = true;
    }

    public final void a(db_2 db_22, boolean bl2) {
        vo_1 vo_12 = vo_1.aik();
        if (this.tl != null) {
            vo_12.cu(true);
            vo_12.n(db_22);
            this.tl.f(db_22);
        } else {
            vo_12.cu(false);
            vo_12.n(db_22);
        }
        if (this.tJ != null) {
            db_22.a(this.tJ);
            if (this.GD && bl2) {
                this.GD = false;
                this.dId.a(this.tJ);
            }
        }
        this.dId.a(db_22);
    }
}

