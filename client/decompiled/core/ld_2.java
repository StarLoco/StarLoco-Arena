/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Constructor;

/*
 * Renamed from Ld
 */
public class ld_2
implements aep_0 {
    public static String bpR = "1.5.6";
    static final String bpS = "http://logback.qos.ch/codes.html#null_CS";
    private static ld_2 bpT = new ld_2();
    private boolean initialized = false;
    private ahu_0 bpU = new ahu_0();
    private ff_1 bpV;

    private ld_2() {
        this.bpU.setName("default");
    }

    public static ld_2 Xt() {
        return bpT;
    }

    static void reset() {
        bpT = new ld_2();
        bpT.init();
    }

    void init() {
        try {
            try {
                new aha_2(this.bpU).aUd();
            }
            catch (azG azG2) {
                ql.a("Failed to auto configure default logger context", azG2);
            }
            ape.b(this.bpU);
            String string = dh_2.getSystemProperty("logback.ContextSelector");
            this.bpV = string == null ? new im_0(this.bpU) : (string.equals("JNDI") ? new aks_0(this.bpU) : ld_2.a(this.bpU, string));
            this.initialized = true;
        }
        catch (Throwable throwable) {
            ql.a("Failed to instantiate [" + ahu_0.class.getName() + "]", throwable);
        }
    }

    static ff_1 a(ahu_0 ahu_02, String string) {
        Class clazz = agw_0.loadClass(string);
        Constructor constructor = clazz.getConstructor(ahu_0.class);
        return (ff_1)constructor.newInstance(ahu_02);
    }

    public cs_2 Xu() {
        if (!this.initialized) {
            return this.bpU;
        }
        if (this.bpV == null) {
            throw new IllegalStateException("contextSelector cannot be null. See also http://logback.qos.ch/codes.html#null_CS");
        }
        return this.bpV.ON();
    }

    public String Xv() {
        return this.bpV.getClass().getName();
    }

    public ff_1 Xw() {
        return this.bpV;
    }

    static {
        bpT.init();
    }
}

