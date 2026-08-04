/*
 * Decompiled with CFR 0.152.
 */
import java.util.LinkedHashMap;
import java.util.Map;

class Wd
extends LinkedHashMap {
    final /* synthetic */ ayd bTS;

    Wd(ayd ayd2, int n2, float f, boolean bl2) {
        this.bTS = ayd2;
        super(n2, f, bl2);
    }

    protected boolean removeEldestEntry(Map.Entry entry) {
        return this.size() > ayd.a(this.bTS);
    }
}

