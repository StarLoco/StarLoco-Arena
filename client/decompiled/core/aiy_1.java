/*
 * Decompiled with CFR 0.152.
 */
import java.util.Set;

/*
 * Renamed from aIY
 */
final class aiy_1
extends km_1 {
    private final Set dQW;
    private final Set Ka;

    aiy_1(Set set, Set set2) {
        this.dQW = set;
        this.Ka = set2;
    }

    public void b(lG lG2) {
        for (int j = 0; j < lG2.HE.length; ++j) {
            this.dQW.add(lG2.HE[j].name);
        }
        super.b(lG2);
    }

    public void a(anM anM2) {
        for (int j = 0; j < anM2.rb.length; ++j) {
            if (!Character.isUpperCase(anM2.rb[j].charAt(0))) continue;
            return;
        }
        if (this.dQW.contains(anM2.rb[0])) {
            return;
        }
        this.Ka.add(anM2.rb[0]);
    }
}

