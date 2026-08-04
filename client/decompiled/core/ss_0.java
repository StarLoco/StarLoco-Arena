/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from sS
 */
class ss_0 {
    protected static final Logger a = Logger.getLogger(ss_0.class);
    private static final apx amg = new afv_1();
    private static final apx amh = new afz_2();
    private final apx ami = new afx_2(this);
    private final lb_0 amj = new lb_0();
    private final ArrayList amk = new ArrayList();

    ss_0() {
    }

    void a(adf_0 adf_02) {
        aGx[] aGxArray;
        assert (this.amj.size() == 0);
        for (aGx aGx2 : aGxArray = adf_02.atc()) {
            if (aGx2.afY() != AV.aIr) continue;
            anl_2 anl_22 = (anl_2)aGx2;
            kk_0 kk_02 = anl_22.aXM();
            this.amj.c(aGx2.ao(), new FO(this, kk_02, null));
        }
    }

    ArrayList h(ArrayList arrayList) {
        this.amk.clear();
        if (arrayList.isEmpty()) {
            return this.amk;
        }
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            aHS aHS2 = (aHS)arrayList.get(j);
            if (!aHS2.isActive()) continue;
            ArrayList arrayList2 = aHS2.dOd.aAg();
            for (int i2 = 0; i2 < arrayList2.size(); ++i2) {
                gt_0 gt_02 = (gt_0)arrayList2.get(i2);
                ((FO)this.amj.get(gt_02.ao())).a(gt_02);
            }
        }
        this.amj.a(this.ami);
        return this.amk;
    }

    public final void clear() {
        this.amj.a(amg);
    }

    public final void reset() {
        this.amj.a(amh);
    }

    static /* synthetic */ ArrayList a(ss_0 ss_02) {
        return ss_02.amk;
    }
}

