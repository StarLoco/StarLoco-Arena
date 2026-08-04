/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.io.FfmpegIO;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class arS {
    private final Bk ub = LD.p(this.getClass());
    private final ConcurrentMap cQK = new ConcurrentHashMap();
    public static final String bDZ = "xugglerfile";
    public static final String cQL = "xugglernull";
    private static final arS cQM = new arS();

    public static arS aFa() {
        return cQM;
    }

    public static void init() {
    }

    private arS() {
        this.a(bDZ, new aOJ());
        this.a(cQL, new anx_2());
    }

    public aFD a(String string, aFD aFD2) {
        if (string == null) {
            throw new IllegalArgumentException("protocol required");
        }
        aFD aFD3 = aFD2 == null ? (aFD)this.cQK.remove(string) : this.cQK.put(string, aFD2);
        this.ub.i("Registering factory for URLProtocol: {}", string);
        if (aFD3 == null) {
            this.ub.i("Letting FFMPEG know about an additional protocol: {}", string);
            FfmpegIO.a(string, this);
        }
        return aFD3;
    }

    public bc_1 C(String string, int n2) {
        bc_1 bc_12 = null;
        this.ub.i("looking for protocol handler for: {}", string);
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("expected valid URL");
        }
        int n3 = string.indexOf(":");
        String string2 = null;
        string2 = n3 > 0 ? string.substring(0, n3) : bDZ;
        aFD aFD2 = (aFD)this.cQK.get(string2);
        if (aFD2 != null) {
            bc_12 = aFD2.a(string2, string, n2);
        } else {
            this.ub.m("asked to get handler for unsupported protocol: {}", string2);
        }
        return bc_12;
    }

    public static String js(String string) {
        String string2 = string;
        if (string != null && string.length() > 0) {
            int n2 = string.indexOf("://");
            if (n2 > 0) {
                string2 = string.substring(n2 + 3);
            } else {
                n2 = string.indexOf(":");
                if (n2 > 0) {
                    string2 = string.substring(n2 + 1);
                }
            }
        }
        return string2;
    }

    public static String jt(String string) {
        int n2;
        String string2 = null;
        if (string != null && string.length() > 0 && (n2 = string.indexOf(":")) > 0) {
            string2 = string.substring(0, n2);
        }
        return string2;
    }
}

