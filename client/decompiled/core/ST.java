/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

class ST
implements ja_1 {
    final /* synthetic */ wy_2 bLY;

    ST(wy_2 wy_22) {
        this.bLY = wy_22;
    }

    public void b(int n2) {
        if (n2 == 8) {
            ajm_2 ajm_22 = new ajm_2();
            ajm_22.kV(this.bLY.jf());
            apN.aDK().vJ().b(ajm_22);
            asc asc2 = apN.aDK().Ln().aQm();
            asc2.d(this.bLY.jf(), (byte)1);
            azs_0.aLV().a((aho_0)this.bLY, "isInTome");
            azs_0.aLV().a((aho_0)this.bLY, "tomeStyle");
            ArrayList arrayList = qy_2.ady().hs(this.bLY.jf());
            int n3 = arrayList.size();
            for (int j = 0; j < n3; ++j) {
                qy_2.ady().aU(((aau_1)arrayList.get(j)).tI());
            }
        }
    }
}

