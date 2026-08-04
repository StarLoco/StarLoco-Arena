/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

class FO {
    private final ArrayList aVt = new ArrayList();
    private final kk_0 aVu;
    final /* synthetic */ ss_0 aVv;

    private FO(ss_0 ss_02, kk_0 kk_02) {
        this.aVv = ss_02;
        this.aVu = kk_02;
    }

    public final void Pk() {
        gt_0 gt_02 = this.aVu.pu();
        gt_02.reset();
        int n2 = this.aVt.size();
        for (int j = 0; j < n2; ++j) {
            gt_0 gt_03 = (gt_0)this.aVt.get(j);
            gt_03.b(gt_02);
        }
    }

    public final boolean isActive() {
        return this.aVt.size() > 0;
    }

    public final void clear() {
        this.aVt.clear();
    }

    public final void a(gt_0 gt_02) {
        this.aVt.add(gt_02);
    }

    static /* synthetic */ kk_0 a(FO fO) {
        return fO.aVu;
    }

    /* synthetic */ FO(ss_0 ss_02, kk_0 kk_02, afv_1 afv_12) {
        this(ss_02, kk_02);
    }
}

