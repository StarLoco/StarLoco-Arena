/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Renamed from aPm
 */
public abstract class apm_0 {
    private static final boolean DEBUG = false;
    public asn eoQ;
    public asn eoR;
    public asn eoS;
    public asn eoT;
    public asn eoU;
    public asn eoV;
    public asn eoW;
    public asn eoX;
    public asn cRE;
    public asn cRx;
    public asn eoY;
    public asn cRD;
    public asn eoZ;
    public asn cRC;
    public asn cRA;
    public asn cRz;
    private final apm_0 epa;
    private final Map epb = new HashMap();
    private final Set epc = new HashSet();

    public apm_0(apm_0 apm_02) {
        this.epa = apm_02;
    }

    protected final void aYV() {
        try {
            this.eoQ = this.lT("Ljava/lang/Object;");
            this.eoR = this.lT("Ljava/lang/String;");
            this.eoS = this.lT("Ljava/lang/Class;");
            this.eoT = this.lT("Ljava/lang/Throwable;");
            this.eoU = this.lT("Ljava/lang/RuntimeException;");
            this.eoV = this.lT("Ljava/lang/Error;");
            this.eoW = this.lT("Ljava/lang/Cloneable;");
            this.eoX = this.lT("Ljava/io/Serializable;");
            this.cRE = this.lT("Ljava/lang/Boolean;");
            this.cRx = this.lT("Ljava/lang/Byte;");
            this.eoY = this.lT("Ljava/lang/Character;");
            this.cRD = this.lT("Ljava/lang/Short;");
            this.eoZ = this.lT("Ljava/lang/Integer;");
            this.cRC = this.lT("Ljava/lang/Long;");
            this.cRA = this.lT("Ljava/lang/Float;");
            this.cRz = this.lT("Ljava/lang/Double;");
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new aHY("Cannot load simple types");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final asn lT(String string) {
        asn asn2;
        if (sA.ce(string)) {
            return string.equals("V") ? asn.cRw : (string.equals("B") ? asn.cRx : (string.equals("C") ? asn.cRy : (string.equals("D") ? asn.cRz : (string.equals("F") ? asn.cRA : (string.equals("I") ? asn.cRB : (string.equals("J") ? asn.cRC : (string.equals("S") ? asn.cRD : (string.equals("Z") ? asn.cRE : null))))))));
        }
        if (this.epa != null && (asn2 = this.epa.lT(string)) != null) {
            return asn2;
        }
        apm_0 apm_02 = this;
        synchronized (apm_02) {
            if (this.epc.contains(string)) {
                return null;
            }
            asn2 = (asn)this.epb.get(string);
            if (asn2 != null) {
                return asn2;
            }
            if (sA.bW(string)) {
                asn asn3 = this.lT(sA.bX(string));
                if (asn3 == null) {
                    return null;
                }
                asn asn4 = asn3.j(this.eoQ);
                this.epb.put(string, asn4);
                return asn4;
            }
            asn2 = this.dx(string);
            if (asn2 == null) {
                this.epc.add(string);
                return null;
            }
        }
        if (!asn2.getDescriptor().equalsIgnoreCase(string)) {
            throw new aHY("\"findIClass()\" returned \"" + asn2.getDescriptor() + "\" instead of \"" + string + "\"");
        }
        return asn2;
    }

    protected abstract asn dx(String var1);

    protected final void l(asn asn2) {
        String string = asn2.getDescriptor();
        asn asn3 = (asn)this.epb.get(string);
        if (asn3 != null) {
            if (asn3 == asn2) {
                return;
            }
            throw new aHY("Non-identical definition of IClass \"" + string + "\"");
        }
        this.epb.put(string, asn2);
    }

    public static apm_0 a(File[] fileArray, File[] fileArray2, File[] fileArray3) {
        wj_0 wj_02 = new wj_0(fileArray == null ? wj_0.gB(System.getProperty("sun.boot.class.path")) : fileArray);
        amn_2 amn_22 = new amn_2(fileArray2 == null ? wj_0.gB(System.getProperty("java.ext.dirs")) : fileArray2);
        wj_0 wj_03 = new wj_0(fileArray3);
        ca_1 ca_12 = new ca_1(wj_02, null);
        ca_12 = new ca_1(amn_22, ca_12);
        ca_12 = new ca_1(wj_03, ca_12);
        return ca_12;
    }
}

