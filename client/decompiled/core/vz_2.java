/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from vz
 */
public abstract class vz_2
implements aHq,
aht_2 {
    protected final ArrayList ate = new ArrayList();
    protected final ArrayList atf = new ArrayList();
    private final ArrayList atg = new ArrayList();

    public ArrayList t(float f, float f2) {
        this.ate.clear();
        ArrayList arrayList = this.atf;
        for (int j = 0; j < arrayList.size(); ++j) {
            ahh_1 ahh_12 = (ahh_1)arrayList.get(j);
            if (!ahh_12.T(f, f2)) continue;
            this.ate.add(ahh_12);
        }
        return this.ate;
    }

    public ahh_1 u(float f, float f2) {
        ArrayList arrayList = this.t(f, f2);
        ahh_1 ahh_12 = null;
        for (int j = 0; j < arrayList.size(); ++j) {
            ahh_1 ahh_13 = (ahh_1)arrayList.get(j);
            if (ahh_12 != null && ahh_13.aTz() <= ahh_12.aTz()) continue;
            ahh_12 = ahh_13;
        }
        return ahh_12;
    }

    public ArrayList a(ari_0 ari_02) {
        this.atg.clear();
        int n2 = this.atf.size();
        for (int j = 0; j < n2; ++j) {
            ahh_1 ahh_12 = (ahh_1)this.atf.get(j);
            this.atg.add(ahh_12);
        }
        return this.atg;
    }

    protected void a(ahh_1 ahh_12, int n2, int n3, int n4) {
        ajh_2.b(ahh_12);
    }

    public void BO() {
        int n2 = this.atf.size();
        for (int j = 0; j < n2; ++j) {
            ahh_1 ahh_12 = (ahh_1)this.atf.get(j);
            ahh_12.eU(ahh_12.aTG());
            ahh_12.aTA();
            ahh_12.aTt();
        }
    }
}

