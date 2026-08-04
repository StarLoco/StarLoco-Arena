/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from af
 */
public abstract class af_1 {
    public abstract String getFontName();

    public abstract int aA();

    public abstract ma_1 getFont();

    public abstract ma_1 a(int var1, float var2);

    public abstract int a(char var1);

    public abstract int aB();

    public abstract int aC();

    public abstract int a(String var1, int var2, int var3);

    public abstract int g(String var1);

    public abstract int h(String var1);

    public abstract int i(String var1);

    public abstract boolean aD();

    public abstract void setColor(float var1, float var2, float var3, float var4);

    public abstract void beginRendering(int var1, int var2);

    public abstract void a(char[] var1, int var2, int var3);

    public abstract void a(char[] var1, int var2, int var3, float var4);

    public abstract void a(char[] var1, int var2, int var3, int var4);

    public abstract void a(char[] var1, int var2, int var3, int var4, float var5);

    public void a(char[] cArray, int n2, int n3, int n4, float f, float f2) {
        this.a(cArray, n2, n3, n4, f);
    }

    public abstract void endRendering();

    public abstract void begin3DRendering();

    public abstract void end3DRendering();

    public int a(String string, int n2) {
        return this.a(string, n2, true);
    }

    public int a(String string, int n2, boolean bl2) {
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n4 < string.length()) {
            int n6;
            while (n4 < string.length()) {
                char c;
                ++n5;
                n6 = string.charAt(n4++);
                boolean bl3 = true;
                if (n4 < string.length() && ((c = string.charAt(n4)) == '.' || c == '?' || c == '!' || c == ':' || c == ';' || c == ',')) {
                    bl3 = false;
                }
                if (n6 != 32 && n6 != 10 && n6 != 9 || !bl3) continue;
                break;
            }
            if (n5 == 0) {
                ++n5;
            }
            if ((n6 = this.a(string, n5, n2)) < n5) {
                if (n3 != 0) {
                    return n3;
                }
                if (bl2) {
                    return n6;
                }
                return 0;
            }
            n3 = n5;
        }
        return string.length();
    }
}

