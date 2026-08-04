/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ai
 */
public class ai_1
extends a_0 {
    final /* synthetic */ HW bV;

    public ai_1(HW hW) {
        this.bV = hW;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        int n2 = HW.a((HW)this.bV).width * HW.b(this.bV) + HW.c(this.bV) * (HW.b(this.bV) - 1);
        int n3 = HW.a((HW)this.bV).height * HW.d(this.bV) + HW.e(this.bV) * (HW.d(this.bV) - 1);
        return new agj_1(n2, n3);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        return this.getContentMinSize(aht_12);
    }

    public void a(aht_1 aht_12) {
        int n2;
        if (HW.f(this.bV) == null) {
            return;
        }
        int n3 = 0;
        if (HW.g(this.bV) != null) {
            n2 = HW.g(this.bV).get(5);
            HW.g(this.bV).set(5, 1);
            n3 = (HW.g(this.bV).get(7) - HW.g(this.bV).getFirstDayOfWeek() + HW.TE()) % HW.TE();
            HW.g(this.bV).set(5, n2);
        }
        n2 = n3;
        int n4 = HW.d(this.bV) - 1;
        for (int j = 0; j < HW.f(this.bV).size(); ++j) {
            aht_1 aht_13 = ((np_2)HW.f(this.bV).get(j)).getContainer();
            int n5 = (HW.a((HW)this.bV).width + HW.c(this.bV)) * n2;
            int n6 = (HW.a((HW)this.bV).height + HW.e(this.bV)) * n4;
            aht_13.setPosition(n5, n6);
            aht_13.setSize(HW.a((HW)this.bV).width, HW.a((HW)this.bV).height);
            if (++n2 != HW.b(this.bV)) continue;
            n2 = 0;
            --n4;
        }
        HW.a(this.bV, HW.h(this.bV) + 1);
    }
}

