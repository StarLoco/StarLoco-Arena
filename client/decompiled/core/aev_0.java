/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aev
 */
class aev_0
extends aFA {
    private final String coI;
    private final ahr_1 yo;

    aev_0(ahr_1 ahr_12, String string) {
        super(ahr_12, null);
        this.yo = ahr_12;
        this.coI = string;
    }

    public boolean aub() {
        return true;
    }

    public boolean hE(String string) {
        return this.coI == string;
    }

    public boolean r(String[] stringArray) {
        for (int j = 0; j < stringArray.length; ++j) {
            if (this.coI != stringArray[j]) continue;
            return true;
        }
        return false;
    }

    public String auc() {
        return this.coI;
    }

    public String toString() {
        return this.coI;
    }
}

