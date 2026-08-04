/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.InputStream;
import org.apache.log4j.Logger;

/*
 * Renamed from alJ
 */
public class alj_1 {
    private final lb_0 cFx = new lb_0(20000, 1.0f);
    protected static final Logger a = Logger.getLogger(alj_1.class);

    public alj_1(InputStream inputStream) {
        this.load(inputStream);
    }

    private void c(xr_0 xr_02) {
        if (xr_0.a(xr_02) == null) {
            return;
        }
        String string = xr_0.b(xr_02);
        if (string != null && string.length() > 0) {
            int n2 = string.length() - 1;
            for (int j = 0; j < n2; ++j) {
                char c;
                if (string.charAt(j) != '\\' || (c = string.charAt(j + 1)) != 'n') continue;
                string = new StringBuilder(string.length()).append(string.substring(0, j)).append('\n').append(string.substring(j + 2)).toString();
                --n2;
            }
        } else {
            string = "";
        }
        this.cFx.c(xr_0.a(xr_02).hashCode(), string.intern());
    }

    public void load(InputStream inputStream) {
        xr_0 xr_02 = new xr_0(inputStream);
        this.cFx.clear();
        while (xr_02.aY()) {
            this.c(xr_02);
        }
        this.c(xr_02);
        this.cFx.compact();
    }

    public String get(String string) {
        return this.get(string.hashCode());
    }

    public String get(int n2) {
        return (String)this.cFx.get(n2);
    }

    public boolean containsKey(String string) {
        return this.bY(string.hashCode());
    }

    public boolean bY(int n2) {
        return this.cFx.bY(n2);
    }
}

