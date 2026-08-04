/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.GeometrySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

public final class agg {
    private Entity3D AH;
    protected vP AC = null;
    private ahr_2 coj;
    private int NS;
    private int NR;
    private int ctJ = -1;

    public void b(ahr_2 ahr_22) {
        this.coj = ahr_22;
    }

    public void setZoom(float f) {
        avz avz2 = (avz)this.AH.aUM().aI(0);
        avz2.m(f, f, 1.0f);
        this.AH.aUM().b(0, avz2);
    }

    public void setScreenPosition(int n2, int n3) {
        avz avz2 = (avz)this.AH.aUM().aI(1);
        avz2.e(n2, n3, 0.0f);
        this.AH.aUM().b(1, avz2);
    }

    public void bo(int n2, int n3) {
        if (this.ctJ != -1) {
            GeometrySprite geometrySprite = (GeometrySprite)this.AH.ma(this.ctJ);
            geometrySprite.x(n3 + this.NR + this.coj.coi.getCellHeight(), n2 + this.NS);
        }
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        int n2;
        int n3;
        ayr_0 ayr_02;
        int n4;
        this.NS = insets.left + insets2.left + insets3.left;
        this.NR = insets.bottom + insets2.bottom + insets3.bottom;
        this.AH.clear();
        ArrayList arrayList = this.coj.coi.Xn();
        int n5 = this.coj.coi.getCellWidth();
        int n6 = this.coj.coi.getCellHeight();
        int n7 = arrayList.size();
        for (n4 = 0; n4 < n7; ++n4) {
            ayr_02 = (ayr_0)arrayList.get(n4);
            int n8 = ayr_02.aLe() * n5;
            n3 = ayr_02.aLf() * n6;
            n2 = this.b(n8 + this.NS, n3 + this.NR + n6, n5, n6, ayr_02.aLy());
            ayr_02.mQ(n2);
        }
        n7 = arrayList.size();
        for (n4 = 0; n4 < n7; ++n4) {
            ayr_02 = (ayr_0)arrayList.get(n4);
            akq_1 akq_12 = ayr_02.getPixmap();
            if (akq_12 == null) continue;
            n3 = ayr_02.aLe() * n5;
            n2 = ayr_02.aLf() * n6;
            int n9 = this.b(n3 + this.NS, n2 + this.NR + n6, n5, n6, akq_12);
            ayr_02.mP(n9);
        }
        this.ctJ = this.b(this.coj.aQg + this.NS, this.coj.aQh + this.NR + n6, n5, n6, this.coj.coi.MW());
    }

    public void j() {
        this.AH.HF();
        this.AH = null;
        this.AC = null;
    }

    public void b() {
        assert (this.AH == null);
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        this.AH.aUM().a(new avz());
        this.AH.aUM().a(new avz());
        this.ctJ = -1;
    }

    public final Entity getEntity() {
        return this.AH;
    }

    public void a(int n2, akq_1 akq_12) {
        if (akq_12 != null) {
            GeometrySprite geometrySprite = (GeometrySprite)this.AH.ma(n2);
            geometrySprite.a(akq_12.Hy(), akq_12.Hw(), akq_12.Hz(), akq_12.Hx(), akq_12.getRotation());
            this.AH.a(n2, akq_12.jI());
        }
    }

    private int b(int n2, int n3, int n4, int n5, akq_1 akq_12) {
        if (n4 == 0 || n5 == 0) {
            return -1;
        }
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        gLGeometrySprite.x(n3, n2);
        gLGeometrySprite.setSize(n4, n5);
        if (this.AC != null) {
            gLGeometrySprite.setColor(this.AC.Cp(), this.AC.Cq(), this.AC.Cr(), this.AC.getAlpha());
        } else {
            gLGeometrySprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        ef_1 ef_12 = null;
        if (akq_12 != null) {
            gLGeometrySprite.a(akq_12.Hy(), akq_12.Hw(), akq_12.Hz(), akq_12.Hx(), akq_12.getRotation());
            ef_12 = akq_12.jI();
        }
        return this.AH.a(gLGeometrySprite, ef_12, null);
    }
}

