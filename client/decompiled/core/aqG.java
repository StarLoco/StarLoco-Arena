/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class aqG
extends cq_2 {
    private static Logger a = Logger.getLogger(aqG.class);
    private static final acl_0 uG = new ym_0(new azn_0());
    private int cOu;
    private char cOv;

    public char getKeyChar() {
        return this.cOv;
    }

    public void setKeyChar(char c) {
        this.cOv = c;
    }

    public int getKeyCode() {
        return this.cOu;
    }

    public void setKeyCode(int n2) {
        this.cOu = n2;
    }

    public static aqG aEf() {
        aqG aqG2;
        try {
            aqG2 = (aqG)uG.adr();
            aqG2.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            aqG2 = new aqG();
            aqG2.b();
        }
        return aqG2;
    }

    public void j() {
        this.cOv = (char)65535;
        this.cOu = -1;
    }
}

