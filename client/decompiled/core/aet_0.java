/*
 * Decompiled with CFR 0.152.
 */
import java.util.regex.Pattern;

/*
 * Renamed from aET
 */
public class aet_0 {
    public static final int dBX = 2;
    public static final int dBY = 5;
    public static int dBZ;
    public static String dCa;
    public static String dCb;
    public static String dCc;
    public static final boolean dCd = false;
    public static final boolean dCe = false;
    public static final boolean dCf = false;
    public static final boolean dCg = false;
    private static final int dCh = 2500;
    public static int dCi;
    public static final long dCj = 300000L;
    public static final long dCk = 100L;
    public static final long dCl = 500L;
    public static final int dCm = 6;
    public static final int dCn = 19;
    public static final int dCo = 8;
    public static final int dCp = 49;
    public static final byte dCq = 100;
    public static final byte dCr = 100;
    public static final short dCs = 14;
    public static final short dCt = 100;
    public static final short dCu = 10;
    public static final short dCv = 10;
    public static final float dCw = 1.25f;
    public static final String asY;
    public static final String dCx = "?";
    public static final String dCy = "-";
    public static final int dCz = 0;
    private static final int dCA = 1;
    private static final int dCB = 2;
    private static final int dCC = 128;
    private static final int dCD = 256;
    public static final int dCE = 0;
    public static final byte dCF = -1;
    public static final byte dCG = 4;
    public static final byte dCH = 14;
    public static final long dCI = 0L;
    public static final long dCJ = 21600L;
    public static final short bMs = 0;
    public static final short dCK = 1;
    public static final short dCL = 2;
    public static final short dCM = 100;
    public static final int dCN = 3;
    public static final byte dCO = 0;
    public static final byte dCP = 1;
    public static final byte dCQ = 2;
    public static final int dCR = 1;
    public static final int dCS = 50;
    public static final int dCT = 1000;
    public static final int dCU = 3000;
    public static final long dCV = 0L;
    public static final long dCW = -2L;
    public static final int dCX = 0;
    public static final int dCY = 1000000;
    public static final int dCZ = 0;
    public static final String dDa = "";
    public static final int dDb = 40;
    public static final int dDc = 40;
    public static final int dDd = 1024;
    public static final String dDe = "";
    public static final String dDf = "";
    public static final String dDg = "ANKAMA";
    public static final String dDh = "NAME CENSORED";
    public static final String dDi = "DESCRIPTION CENSORED";
    public static final int dDj = 16;
    public static final int dDk = 8;
    public static final int dDl = 900000;
    public static final int dDm = 900000;
    public static final int dDn = 0;
    public static final short dDo = 0;
    public static final short dDp = 1000;
    public static final short dDq = 4500;
    public static final short dDr = 350;
    public static final short dDs = 50;
    public static final short dDt = 0;
    public static final short dDu = 1;
    public static final short dDv = 24;
    public static final long dDw = Long.MIN_VALUE;
    public static final long dDx = 20000L;
    public static final long dDy = 1000L;
    public static final long dDz = 10000L;
    public static final long dDA = 1000000L;
    public static final int dDB = 12;
    public static final int dDC = 0;
    public static final int dDD = 32;
    public static final short dDE = 5;
    public static final short dDF = 6;
    public static final byte dDG = 4;
    public static final int dDH = 32;
    public static final String dDI = "atd";
    public static final String[] dDJ;
    public static final Pattern dDK;
    public static final int dDL = 16;
    public static final Pattern dDM;
    public static final int dDN = 20;
    public static final byte dDO = -1;
    public static final int dDP = 1;
    public static final byte dDQ = 4;
    public static final short dDR = 35;
    public static final short dDS = 85;
    public static final int dDT = 8;
    public static final int dDU = 10;
    public static final short dDV = 0;
    public static final int dDW = 99;
    public static final short dDX = 23;
    public static final int dDY = -56;
    public static final int dDZ = -2;
    public static final short dEa = 2;
    public static final int dEb = 202;
    public static final short dEc = 111;
    public static final int dEd = 46;
    public static final int dEe = 66;
    public static final short dEf = 0;
    public static final short dEg = 112;
    public static final int dEh = 12;
    public static final int dEi = 18;
    public static final short dEj = -8;
    public static final int dEk = 28;
    public static final int dEl = 37;
    public static final short dEm = -24;
    public static final ry dEn;
    public static final int dEo = 5;
    public static final int dEp = 50;
    public static final byte dEq = 10;
    public static final long dEr = -2L;
    public static final long dEs = -3L;
    public static final short dEt = 0;
    public static final byte dEu = 0;
    public static final byte dsp = 0;
    public static final short dEv = 5;
    public static final int dEw = 0;
    public static final int dEx = 0;
    public static final long dEy = 604800000L;
    public static final int dEz = 4;
    public static long dEA;
    public static final long dEB = 0L;
    public static final int dEC = 0;

    public static boolean nC(int n2) {
        return 0 < n2;
    }

    private static boolean ce(int n2, int n3) {
        return aet_0.nC(n2 & n3);
    }

    public static boolean nD(int n2) {
        return aet_0.ce(n2, 1);
    }

    public static boolean nE(int n2) {
        return aet_0.ce(n2, 2) || aet_0.nD(n2);
    }

    public static boolean nF(int n2) {
        return aet_0.ce(n2, 128) || aet_0.nE(n2);
    }

    public static boolean nG(int n2) {
        return aet_0.ce(n2, 256);
    }

    public static int nH(int n2) {
        return n2 < 1000 ? 0 : 1 + Math.round((float)(n2 - 1000) / 2000.0f * 49.0f);
    }

    public static int nI(int n2) {
        return (int)(n2 <= 1 ? 1000.0f : 1001.0f + ((float)n2 - 1.5f) * 2000.0f / 49.0f);
    }

    public static int nJ(int n2) {
        return Math.max(1, Math.min((int)Math.sqrt(n2 / 10), 50));
    }

    public static int nK(int n2) {
        return nr_0.ct(n2);
    }

    public static int nL(int n2) {
        return aet_0.nN(aet_0.nH(n2));
    }

    public static int nM(int n2) {
        return aet_0.nN(aet_0.nJ(n2));
    }

    public static short nN(int n2) {
        if (n2 <= 5) {
            return 1;
        }
        if (n2 <= 10) {
            return 2;
        }
        if (n2 <= 15) {
            return 3;
        }
        if (n2 <= 20) {
            return 4;
        }
        if (n2 <= 25) {
            return 5;
        }
        if (n2 <= 30) {
            return 6;
        }
        if (n2 <= 35) {
            return 7;
        }
        if (n2 <= 40) {
            return 8;
        }
        if (n2 <= 45) {
            return 9;
        }
        if (n2 <= 49) {
            return 10;
        }
        return 11;
    }

    static {
        dCi = 2500;
        asY = null;
        dDJ = new String[]{""};
        dDK = Pattern.compile("([\\p{L}]|[\\p{L}][-]){2,}\\p{L}", 64);
        dDM = Pattern.compile("([\\p{L}]|[\\p{L}][-]){2,}\\p{L}", 64);
        dEn = new ry(28, 37, -24);
        dEA = Long.MAX_VALUE;
    }
}

