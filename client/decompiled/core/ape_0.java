/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from apE
 */
public class ape_0
extends a_0 {
    final /* synthetic */ asc_0 cMp;

    public ape_0(asc_0 asc_02) {
        this.cMp = asc_02;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        int n2 = asc_0.a((asc_0)this.cMp).width * asc_0.b(this.cMp) + asc_0.c(this.cMp) * (asc_0.b(this.cMp) - 1);
        int n3 = asc_0.a((asc_0)this.cMp).height * asc_0.d(this.cMp) + asc_0.e(this.cMp) * (asc_0.d(this.cMp) - 1);
        return new agj_1(n2, n3);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        return this.getContentMinSize(aht_12);
    }

    public void a(aht_1 aht_12) {
        if (asc_0.f(this.cMp) != null) {
            for (int j = 0; j < asc_0.f(this.cMp).size(); ++j) {
                qa_1 qa_12 = (qa_1)asc_0.g(this.cMp).get(j);
                agc_0 agc_02 = (agc_0)asc_0.f(this.cMp).get(j);
                float f = agc_02.getX();
                float f2 = agc_02.getY();
                int n2 = (int)((float)(asc_0.a((asc_0)this.cMp).width + asc_0.c(this.cMp)) * f);
                int n3 = (int)((float)(asc_0.a((asc_0)this.cMp).height + asc_0.e(this.cMp)) * ((float)asc_0.d(this.cMp) - f2 - 1.0f));
                qa_12.setPosition(n2, n3);
                qa_12.setSize(asc_0.a((asc_0)this.cMp).width, asc_0.a((asc_0)this.cMp).height);
            }
        }
    }
}

