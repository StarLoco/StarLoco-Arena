/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;
import java.util.List;

public final class LD {
    static final String brN = "http://www.slf4j.org/codes.html#StaticLoggerBinder";
    static final String brO = "http://www.slf4j.org/codes.html#null_LF";
    static final String brP = "http://www.slf4j.org/codes.html#version_mismatch";
    static final String brQ = "http://www.slf4j.org/codes.html#substituteLogger";
    static final String brR = "http://www.slf4j.org/codes.html#unsuccessfulInit";
    static final String brS = "org.slf4j.LoggerFactory could not be successfully initialized. See also http://www.slf4j.org/codes.html#unsuccessfulInit";
    static final int UNINITIALIZED = 0;
    static final int brT = 1;
    static final int brU = 2;
    static final int brV = 3;
    static final int brW = 1;
    static final int brX = 2;
    static int brY = 0;
    static int brZ = 0;
    static lx_2 bsa = new lx_2();
    private static final String[] bsb = new String[]{"1.5.5", "1.5.6"};

    private LD() {
    }

    static void reset() {
        brY = 0;
        brZ = 0;
        bsa = new lx_2();
    }

    private static final void XS() {
        LD.bind();
        LD.XU();
    }

    private static final void bind() {
        try {
            LD.Xt();
            brY = 3;
            LD.XT();
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            brY = 2;
            String string = noClassDefFoundError.getMessage();
            if (string != null && string.indexOf("org/slf4j/impl/StaticLoggerBinder") != -1) {
                ql.bC("Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".");
                ql.bC("See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.");
            }
            throw noClassDefFoundError;
        }
        catch (Exception exception) {
            brY = 2;
            ql.a("Failed to instantiate logger [" + LD.Xt().Xv() + "]", exception);
        }
    }

    private static final void XT() {
        List list = bsa.XD();
        if (list.size() == 0) {
            return;
        }
        ql.bC("The following loggers will not work becasue they were created");
        ql.bC("during the default configuration phase of the underlying logging system.");
        ql.bC("See also http://www.slf4j.org/codes.html#substituteLogger");
        for (int j = 0; j < list.size(); ++j) {
            String string = (String)list.get(j);
            ql.bC(string);
        }
    }

    private static final void XU() {
        try {
            String string = ld_2.bpR;
            boolean bl2 = false;
            for (int j = 0; j < bsb.length; ++j) {
                if (!bsb[j].equals(string)) continue;
                bl2 = true;
            }
            if (!bl2) {
                ql.bC("The requested version " + string + " by your slf4j binding is not compatible with " + Arrays.toString(bsb));
                ql.bC("See http://www.slf4j.org/codes.html#version_mismatch for further details.");
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {
        }
        catch (Throwable throwable) {
            ql.a("Unexpected problem occured during version sanity check", throwable);
        }
    }

    private static final ld_2 Xt() {
        if (brZ == 1) {
            return ld_2.SINGLETON;
        }
        if (brZ == 2) {
            return ld_2.Xt();
        }
        try {
            ld_2 ld_22 = ld_2.Xt();
            brZ = 2;
            return ld_22;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            brZ = 1;
            return ld_2.SINGLETON;
        }
    }

    public static Bk D(String string) {
        cs_2 cs_22 = LD.XV();
        return cs_22.D(string);
    }

    public static Bk p(Class clazz) {
        return LD.D(clazz.getName());
    }

    public static cs_2 XV() {
        if (brY == 0) {
            brY = 1;
            LD.XS();
        }
        switch (brY) {
            case 3: {
                return LD.Xt().Xu();
            }
            case 2: {
                throw new IllegalStateException(brS);
            }
            case 1: {
                return bsa;
            }
        }
        throw new IllegalStateException("Unreachable code");
    }
}

