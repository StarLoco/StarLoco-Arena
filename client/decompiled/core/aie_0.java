/*
 * Decompiled with CFR 0.152.
 */
import java.util.Locale;

/*
 * Renamed from aIe
 */
public enum aie_0 {
    dOv(new Locale("fr")),
    dOw(new Locale("en")),
    dOx(new Locale("de")),
    dOy(new Locale("es")),
    dOz(new Locale("it")),
    dOA(new Locale("nl")),
    dOB(new Locale("jp")),
    dOC(new Locale("ja")),
    dOD(new Locale("ru")),
    dOE(new Locale("pt"));

    private Locale dOF;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aie_0() {
        void var3_1;
        this.dOF = var3_1;
    }

    public Locale getLocale() {
        return this.dOF;
    }

    public static aie_0 ly(String string) {
        if (string.equals("fr")) {
            return dOv;
        }
        if (string.equals("en")) {
            return dOw;
        }
        if (string.equals("de")) {
            return dOx;
        }
        if (string.equals("es")) {
            return dOy;
        }
        if (string.equals("it")) {
            return dOz;
        }
        if (string.equals("nl")) {
            return dOA;
        }
        if (string.equals("jp")) {
            return dOB;
        }
        if (string.equals("ja")) {
            return dOC;
        }
        if (string.equals("ru")) {
            return dOD;
        }
        if (string.equals("pt")) {
            return dOE;
        }
        return dOw;
    }

    public static boolean a(aie_0[] aie_0Array, aie_0 aie_02) {
        if (aie_0Array == null || aie_02 == null) {
            return false;
        }
        for (aie_0 aie_03 : aie_0Array) {
            if (!aie_03.equals((Object)aie_02)) continue;
            return true;
        }
        return false;
    }

    public String getName() {
        switch (this) {
            case dOv: {
                return "Fran\u00e7ais";
            }
            case dOw: {
                return "Anglais";
            }
            case dOx: {
                return "Allemand";
            }
            case dOy: {
                return "Espagnol";
            }
            case dOz: {
                return "Italien";
            }
            case dOB: 
            case dOC: {
                return "Japonais";
            }
            case dOA: {
                return "Neerlandais";
            }
            case dOE: {
                return "Portugais";
            }
            case dOD: {
                return "Russe";
            }
        }
        return this.name();
    }
}

