/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/*
 * Renamed from xr
 */
class xr_0
extends InputStreamReader {
    private String fZ;
    private String ayx;
    private char[] ayy = new char[8192];
    private char[] ayz = new char[1024];
    private int ayA = 0;
    private int ayB = 0;

    public xr_0(InputStream inputStream) {
        super(inputStream, Charset.forName("UTF-8"));
    }

    private void ek(int n2) {
        for (int j = 1; j < n2; ++j) {
            if (this.ayz[j] != '=') continue;
            this.fZ = String.valueOf(this.ayz, 0, j);
            this.ayx = String.valueOf(this.ayz, j + 1, n2 - j - 1);
            return;
        }
    }

    public boolean aY() {
        int n2 = 0;
        boolean bl2 = true;
        boolean bl3 = false;
        boolean bl4 = true;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = false;
        this.fZ = null;
        this.ayx = null;
        while (true) {
            if (this.ayA >= this.ayB) {
                try {
                    this.ayB = this.read(this.ayy);
                }
                catch (IOException iOException) {
                    alj_1.a.error((Object)"Exception", (Throwable)iOException);
                }
                this.ayA = 0;
                if (this.ayB <= 0) {
                    if (n2 != 0) {
                        this.ek(n2);
                    }
                    return false;
                }
            }
            char c = this.ayy[this.ayA++];
            if (bl7) {
                bl7 = false;
                if (c == '\n') continue;
            }
            if (bl2) {
                if (c == ' ' || c == '\t' || c == '\f' || !bl5 && (c == '\r' || c == '\n')) continue;
                bl2 = false;
                bl5 = false;
            }
            if (bl4) {
                bl4 = false;
                if (c == '#' || c == '!') {
                    bl3 = true;
                    continue;
                }
            }
            if (c != '\n' && c != '\r') {
                this.ayz[n2++] = c;
                if (n2 == this.ayz.length) {
                    int n3 = this.ayz.length * 2;
                    if (n3 < 0) {
                        n3 = Integer.MAX_VALUE;
                    }
                    char[] cArray = new char[n3];
                    System.arraycopy(this.ayz, 0, cArray, 0, this.ayz.length);
                    this.ayz = cArray;
                }
                if (c == '\\') {
                    bl6 = !bl6;
                    continue;
                }
                bl6 = false;
                continue;
            }
            if (bl3 || n2 == 0) {
                bl3 = false;
                bl4 = true;
                bl2 = true;
                n2 = 0;
                continue;
            }
            if (this.ayA >= this.ayB) {
                try {
                    this.ayB = this.read(this.ayy);
                }
                catch (IOException iOException) {
                    alj_1.a.error((Object)"Exception", (Throwable)iOException);
                }
                this.ayA = 0;
                if (this.ayB <= 0) {
                    if (n2 != 0) {
                        this.ek(n2);
                    }
                    return false;
                }
            }
            if (!bl6) break;
            --n2;
            bl2 = true;
            bl5 = true;
            bl6 = false;
            if (c != '\r') continue;
            bl7 = true;
        }
        if (n2 != 0) {
            this.ek(n2);
        }
        return true;
    }

    static /* synthetic */ String a(xr_0 xr_02) {
        return xr_02.fZ;
    }

    static /* synthetic */ String b(xr_0 xr_02) {
        return xr_02.ayx;
    }
}

