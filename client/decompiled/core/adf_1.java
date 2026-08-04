/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.net.URL;
import java.util.Enumeration;

/*
 * Renamed from adf
 */
class adf_1
implements Enumeration {
    private String cmf;
    private int cmg;
    private URL cmh;
    private final ny_1 cmi;

    adf_1(ny_1 ny_12, String string) {
        this.cmi = ny_12;
        this.cmf = string;
        this.cmg = 0;
        this.asf();
    }

    public boolean hasMoreElements() {
        return this.cmh != null;
    }

    public Object nextElement() {
        URL uRL = this.cmh;
        this.asf();
        return uRL;
    }

    private void asf() {
        URL uRL = null;
        while (this.cmg < ny_1.a(this.cmi).size() && uRL == null) {
            try {
                File file = (File)ny_1.a(this.cmi).elementAt(this.cmg);
                uRL = this.cmi.b(file, this.cmf);
                ++this.cmg;
            }
            catch (eq_2 eq_22) {}
        }
        this.cmh = uRL;
    }
}

