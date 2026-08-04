/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from os
 */
class os_2
implements ov_1 {
    final /* synthetic */ ahz UX;

    os_2(ahz ahz2) {
        this.UX = ahz2;
    }

    public boolean a(ke ke2) {
        ArrayList arrayList = ((Oc)ahz.a((ahz)this.UX).get((int)ahz.b((ahz)this.UX))).bBG;
        for (int j = 0; j < arrayList.size(); ++j) {
            ((aqq_0)arrayList.get(j)).setVisible(false);
        }
        ahz.a(this.UX, (byte)(ahz.b(this.UX) == ahz.a(this.UX).size() - 1 ? 0 : ahz.b(this.UX) + 1));
        nq_2 nq_22 = new nq_2(this.UX, ahz.b(this.UX));
        nq_22.b();
        this.UX.f(nq_22);
        arrayList = ((Oc)ahz.a((ahz)this.UX).get((int)ahz.b((ahz)this.UX))).bBG;
        for (int j = 0; j < arrayList.size(); ++j) {
            ((aqq_0)arrayList.get(j)).setVisible(true);
        }
        this.UX.Am();
        return true;
    }
}

