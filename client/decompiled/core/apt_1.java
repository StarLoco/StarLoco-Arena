/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from apt
 */
public class apt_1 {
    private static final apt_1 cMf = new apt_1();
    private final ArrayList cMg = new ArrayList();

    public static final apt_1 aDo() {
        return cMf;
    }

    public void a(KU kU) {
        if (!this.cMg.contains(kU)) {
            this.cMg.add(kU);
        }
    }

    public void b(KU kU) {
        if (this.cMg.contains(kU)) {
            this.cMg.remove(kU);
        }
    }

    public void aDp() {
        for (KU kU : this.cMg) {
            kU.Xr();
        }
    }

    public void a(agV agV2, int n2, int n3) {
        for (KU kU : this.cMg) {
            boolean bl2;
            na_1 na_12 = kU.ade;
            if (!(na_12 instanceof adg_2)) continue;
            adg_2 adg_22 = (adg_2)na_12;
            int n4 = adg_22.getX();
            int n5 = adg_22.getY();
            float f = agV2.adF();
            float f2 = agV2.adG();
            boolean bl3 = (float)(n4 + adg_22.getWidth()) >= f - (float)n2;
            boolean bl4 = bl2 = (float)(n5 + adg_22.getHeight()) >= f2 - (float)n3;
            if (!bl3 && !bl2) continue;
            if (bl3) {
                n4 += n2;
            }
            if (bl2) {
                n5 += n3;
            }
            adg_22.setPosition(n4, n5);
        }
    }
}

