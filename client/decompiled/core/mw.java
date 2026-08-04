/*
 * Decompiled with CFR 0.152.
 */
import java.util.Set;

final class mw
extends km_1 {
    private final Set Ka;

    mw(Set set) {
        this.Ka = set;
    }

    public void a(anM anM2) {
        for (int j = 0; j < anM2.rb.length; ++j) {
            if (!Character.isUpperCase(anM2.rb[j].charAt(0))) continue;
            return;
        }
        this.Ka.add(anM2.rb[0]);
    }
}

