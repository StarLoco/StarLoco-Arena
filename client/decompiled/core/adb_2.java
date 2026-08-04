/*
 * Decompiled with CFR 0.152.
 */
import java.util.regex.Pattern;

/*
 * Renamed from aDb
 */
public abstract class adb_2 {
    private static final String dvs = "(?:\\s+.*|$)";
    private static final String dvt = "\\s+";
    private String m_name = "";
    private Pattern dvu;
    private Pattern dvv;
    private byte ata = (byte)-128;
    private boolean dvw = false;

    public adb_2(String string, String string2, boolean bl2) {
        String string3 = "";
        if (string != null && string.length() != 0 && !string.endsWith(dvs)) {
            string3 = string + dvs;
        }
        this.dvu = Pattern.compile(string3);
        String string4 = "(" + string + "){1}";
        if (string2 != null && string2.length() != 0 && !string2.startsWith(string4 + dvt)) {
            string4 = string4 + dvt + string2;
        }
        if (bl2) {
            string4 = string4 + "|(" + string + ")";
        }
        this.dvv = Pattern.compile(string4);
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public String getName() {
        return this.m_name;
    }

    public byte BK() {
        return this.ata;
    }

    public void ax(byte by) {
        this.ata = by;
    }

    public Pattern aOD() {
        return this.dvu;
    }

    public Pattern aOE() {
        return this.dvv;
    }

    public boolean aOF() {
        return this.dvw;
    }

    public void eL(boolean bl2) {
        this.dvw = bl2;
    }

    public abstract MC arn();
}

