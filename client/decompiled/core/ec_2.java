/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from EC
 */
public class ec_2
extends aFA {
    private final String keyword;
    private final ahr_1 yo;

    ec_2(ahr_1 ahr_12, String string) {
        super(ahr_12, null);
        this.yo = ahr_12;
        this.keyword = string;
    }

    public boolean NU() {
        return true;
    }

    public boolean dN(String string) {
        return this.keyword == string;
    }

    public boolean i(String[] stringArray) {
        for (int j = 0; j < stringArray.length; ++j) {
            if (this.keyword != stringArray[j]) continue;
            return true;
        }
        return false;
    }

    public String NV() {
        return this.keyword;
    }

    public String toString() {
        return this.keyword;
    }
}

