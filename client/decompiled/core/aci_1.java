/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import java.util.Collections;

/*
 * Renamed from acI
 */
class aci_1
extends aba_2 {
    final /* synthetic */ alp_2 cjL;

    aci_1(alp_2 alp_22) {
        this.cjL = alp_22;
    }

    public void bI(int n2) {
        int n3;
        int n4;
        boolean bl2 = alp_2.a(this.cjL);
        if (Float.isNaN(this.cjL.dWw) || Float.isNaN(this.cjL.dWx) || Float.isNaN(this.cjL.dWz) || Float.isNaN(this.cjL.dWA)) {
            super.bI(n2);
            return;
        }
        if (alp_2.b(this.cjL) == null) {
            super.bI(n2);
            this.cjL.dWC = this.cjL.dWw;
            this.cjL.dWD = this.cjL.dWx;
            this.cjL.dWw = alp_2.a(this.cjL, this.cjL.dWw, this.cjL.dWz, 20);
            this.cjL.dWx = alp_2.a(this.cjL, this.cjL.dWx, this.cjL.dWA, 20);
            float f = (float)this.i(this.cjL.dWw - this.cjL.dWC, this.cjL.dWx - this.cjL.dWD);
            float f2 = (float)this.j(this.cjL.dWw - this.cjL.dWC, this.cjL.dWx - this.cjL.dWD);
            int n5 = alp_2.c(this.cjL).size();
            for (int j = 0; j < n5; ++j) {
                EntitySprite entitySprite = (EntitySprite)alp_2.c(this.cjL).get(j);
                entitySprite.x(entitySprite.Hy() - f2, entitySprite.Hw() - f);
            }
            alp_2.d(this.cjL).x(alp_2.d(this.cjL).Hy() - f2, alp_2.d(this.cjL).Hw() - f);
            return;
        }
        this.cjL.cFH.removeAllChildren();
        this.cjL.dWw = bl2 ? this.cjL.dWz : alp_2.a(this.cjL, this.cjL.dWw, this.cjL.dWz, 20);
        this.cjL.dWx = bl2 ? this.cjL.dWA : alp_2.a(this.cjL, this.cjL.dWx, this.cjL.dWA, 20);
        float f = (float)this.i(this.cjL.dWw, this.cjL.dWx);
        float f3 = (float)this.j(this.cjL.dWw, this.cjL.dWx);
        float f4 = this.bIz / 2.0f + (float)this.cjL.cLZ.getLeftInset();
        float f5 = this.bIA / 2.0f + (float)this.cjL.cLZ.getBottomInset();
        if (alp_2.e(this.cjL) != null) {
            n4 = alp_2.e(this.cjL).size();
            for (n3 = 0; n3 < n4; ++n3) {
                if (alp_2.e(this.cjL).get(n3) == null) continue;
                alp_2.a(this.cjL, this, (aaj)alp_2.e(this.cjL).get(n3), (EntitySprite)alp_2.f(this.cjL).get(n3), f4, f5, f, f3, false, bl2);
            }
        }
        if (alp_2.b(this.cjL) != null) {
            n4 = alp_2.b(this.cjL).size();
            for (n3 = 0; n3 < n4; ++n3) {
                aaj aaj2 = (aaj)alp_2.b(this.cjL).get(n3);
                if (aaj2 == null) continue;
                alp_2.a(this.cjL, this, aaj2, (EntitySprite)alp_2.c(this.cjL).get(n3), f4, f5, f, f3, false, bl2);
            }
        }
        if (alp_2.g(this.cjL) != null) {
            alp_2.a(this.cjL, this, alp_2.g(this.cjL), alp_2.d(this.cjL), f4, f5, f, f3, true, bl2);
        }
        if (bl2) {
            alp_2.a(this.cjL, false);
        }
        Collections.sort(this.cjL.cFH.aUK(), SJ.afm());
        super.bI(n2);
    }
}

