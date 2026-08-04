/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity3D;

class abJ
implements apx {
    final /* synthetic */ avu_0 ciA;

    abJ(avu_0 avu_02) {
        this.ciA = avu_02;
    }

    public boolean a(ee_2 ee_22) {
        vD vD2 = ee_22.NW();
        if (ee_22.PL().b((aak_2)avx_0.deu)) {
            vD2.W(0.1f);
        } else {
            vD2.W(0.3f);
        }
        ((Entity3D)vD2.aTm()).c(vD2.getMaterial());
        return true;
    }
}

