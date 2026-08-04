/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from cH
 */
public class ch_0
extends akr_2 {
    private static final String jw = "content.";
    private static final char jx = '.';

    public static ch_0 eW() {
        return (ch_0)dUl;
    }

    public String f(int n2, int n3) {
        String string = jw + n2 + '.' + n3;
        return this.getString(string);
    }

    public String a(int n2, int n3, Object ... objectArray) {
        String string = jw + n2 + '.' + n3;
        return this.getString(string, objectArray);
    }

    public boolean g(int n2, int n3) {
        String string = jw + n2 + '.' + n3;
        return this.containsKey(string);
    }
}

