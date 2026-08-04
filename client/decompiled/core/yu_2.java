/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Yu
 */
public abstract class yu_2
extends vz_2 {
    public int C(float f, float f2) {
        int n2 = 0;
        ArrayList arrayList = this.atf;
        for (int j = 0; j < arrayList.size(); ++j) {
            aiu_0 aiu_02 = (aiu_0)arrayList.get(j);
            if (aiu_02.T(f, f2)) {
                aiu_02.setSelected(true);
                ++n2;
                continue;
            }
            aiu_02.setSelected(false);
        }
        return n2;
    }

    public void a(aiu_0 aiu_02) {
        ArrayList arrayList = this.atf;
        for (int j = 0; j < arrayList.size(); ++j) {
            aiu_0 aiu_03 = (aiu_0)arrayList.get(j);
            if (aiu_03 == aiu_02) continue;
            aiu_03.setSelected(false);
        }
    }

    protected double a(aiu_0 aiu_02, acy_1 acy_12, qs_2 qs_22, float f, float f2) {
        int n2 = (int)Math.floor(qs_22.aNA());
        YR yR = qs_22.vn();
        double d = (aiu_02.getAltitude() - yR.Fu()) * (double)n2;
        double d2 = aiu_02.getWorldX() - yR.oV();
        double d3 = aiu_02.getWorldY() - yR.oW();
        double d4 = qs_22.i(d2, d3);
        double d5 = qs_22.j(d2, d3);
        float f3 = yR.aEK();
        double d6 = d4 * (double)f3;
        double d7 = (d5 + d) * (double)f3;
        int n3 = (int)((float)(aiu_02.ge() * n2) * f3);
        if (acy_12 != null) {
            acy_12.G((float)d4, (float)d7);
        }
        if (aiu_02.aTL() && ((int)d6 != aiu_02.getScreenX() || (int)d7 != aiu_02.getScreenY() || n3 != aiu_02.hB())) {
            aiu_02.ai((int)d6);
            aiu_02.aj((int)d7);
            aiu_02.ak(n3);
            aiu_02.hD();
        }
        return d5;
    }
}

