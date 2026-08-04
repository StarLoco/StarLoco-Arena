/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from de
 */
public class de_1
extends cr_2 {
    private static final int kG = 1000;
    private static final int kH = 1024;
    private static final int kI = 4;
    private static final int kJ = 1000000;
    private static final int kK = 9;
    private static final int kL = 0x100000;
    private static final int kM = 13;
    private static final long kN = 1000000000L;
    private static final int kO = 18;
    private static final long kP = 0x40000000L;
    private static final int kQ = 22;
    private static final long kR = 1000000000000L;
    private static final int kS = 27;
    private static final long kT = 0x10000000000L;
    private static final int kU = 31;
    private static final int kV = 36;
    public static final String kW = "value";
    public static final String kX = "units";
    public static final String kY = "when";
    private long size = -1L;
    private long kZ = 1L;
    private long la = -1L;
    private aCP lb = aCP.duH;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{sizeselector value: ");
        stringBuffer.append(this.la);
        stringBuffer.append("compare: ").append(this.lb.getValue());
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void x(long l2) {
        this.size = l2;
        if (this.kZ != 0L && l2 > -1L) {
            this.la = l2 * this.kZ;
        }
    }

    public void a(asx_0 asx_02) {
        int n2 = asx_02.getIndex();
        this.kZ = 0L;
        if (n2 > -1 && n2 < 4) {
            this.kZ = 1000L;
        } else if (n2 < 9) {
            this.kZ = 1024L;
        } else if (n2 < 13) {
            this.kZ = 1000000L;
        } else if (n2 < 18) {
            this.kZ = 0x100000L;
        } else if (n2 < 22) {
            this.kZ = 1000000000L;
        } else if (n2 < 27) {
            this.kZ = 0x40000000L;
        } else if (n2 < 31) {
            this.kZ = 1000000000000L;
        } else if (n2 < 36) {
            this.kZ = 0x10000000000L;
        }
        if (this.kZ > 0L && this.size > -1L) {
            this.la = this.size * this.kZ;
        }
    }

    public void a(anc_2 anc_22) {
        this.lb = anc_22;
    }

    public void a(vj_0[] vj_0Array) {
        super.a(vj_0Array);
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                aNk aNk2;
                String string = vj_0Array[j].getName();
                if (kW.equalsIgnoreCase(string)) {
                    try {
                        this.x(Long.parseLong(vj_0Array[j].getValue()));
                    }
                    catch (NumberFormatException numberFormatException) {
                        this.eC("Invalid size setting " + vj_0Array[j].getValue());
                    }
                    continue;
                }
                if (kX.equalsIgnoreCase(string)) {
                    aNk2 = new asx_0();
                    aNk2.setValue(vj_0Array[j].getValue());
                    this.a((asx_0)aNk2);
                    continue;
                }
                if (kY.equalsIgnoreCase(string)) {
                    aNk2 = new anc_2();
                    aNk2.setValue(vj_0Array[j].getValue());
                    this.a((anc_2)aNk2);
                    continue;
                }
                this.eC("Invalid parameter " + string);
            }
        }
    }

    public void dQ() {
        if (this.size < 0L) {
            this.eC("The value attribute is required, and must be positive");
        } else if (this.kZ < 1L) {
            this.eC("Invalid Units supplied, must be K,Ki,M,Mi,G,Gi,T,or Ti");
        } else if (this.la < 0L) {
            this.eC("Internal error: Code is not setting sizelimit correctly");
        }
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        if (file2.isDirectory()) {
            return true;
        }
        long l2 = file2.length() - this.la;
        return this.lb.nn(l2 == 0L ? 0 : (int)(l2 / Math.abs(l2)));
    }
}

