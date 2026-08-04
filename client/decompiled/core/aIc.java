/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

class aIc {
    protected static final Logger a = Logger.getLogger(aIc.class);
    private final ss_0 dOr = new ss_0();
    private final ArrayList dOs = new ArrayList();
    private final ArrayList dOt = new ArrayList();

    aIc() {
    }

    void update(int n2) {
        this.dOt.clear();
        for (int j = 0; j < this.dOs.size(); ++j) {
            zb_2 zb_22 = (zb_2)this.dOs.get(j);
            zb_22.update(n2);
            zb_22.j(this.dOt);
        }
        ArrayList arrayList = this.dOr.h(this.dOt);
        int n3 = arrayList.size();
        for (int j = 0; j < n3; ++j) {
            ((kk_0)arrayList.get(j)).pv();
        }
    }

    void a(zb_2 zb_22) {
        this.dOs.add(zb_22);
    }

    void b(zb_2 zb_22) {
        this.dOs.remove(zb_22);
    }

    void a(adf_0 adf_02) {
        this.dOr.a(adf_02);
    }

    void clear() {
        this.dOs.clear();
        this.dOr.clear();
    }

    public void reset() {
        this.dOr.reset();
    }
}

