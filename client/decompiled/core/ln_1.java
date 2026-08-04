/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ln
 */
public class ln_1
implements abc_2 {
    public String q(String string) {
        int n2 = string.lastIndexOf(46);
        if (n2 != -1) {
            return string.substring(n2 + 1, string.length());
        }
        return string;
    }
}

