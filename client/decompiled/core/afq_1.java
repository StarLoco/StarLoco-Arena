/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/*
 * Renamed from aFq
 */
public class afq_1 {
    private static Logger a = Logger.getLogger(afq_1.class);
    private final HashMap dGV = new HashMap();
    private aji_1 aPd = null;
    private HashMap dGW = null;
    private ArrayList dGX = null;

    public azs_0 aRQ() {
        return azs_0.aLV();
    }

    public void a(aji_1 aji_12, String string) {
        this.dGV.put(string, aji_12);
    }

    public aji_1 lf(String string) {
        aji_1 aji_12 = new aji_1(string, this);
        this.a(aji_12, string);
        return aji_12;
    }

    public void at(String string, String string2) {
        if (string != null && string2 != null) {
            aji_1 aji_12 = (aji_1)this.dGV.remove(string);
            this.dGV.put(string2, aji_12);
        }
    }

    public na_1 R(String string) {
        if (string == null) {
            return null;
        }
        String string2 = string.substring(0, string.indexOf("."));
        aji_1 aji_12 = (aji_1)this.dGV.get(string2);
        if (aji_12 != null) {
            return aji_12.R(string.substring(string.indexOf(".") + 1));
        }
        return null;
    }

    public void lg(String string) {
        if (this.dGV != null) {
            aji_1 aji_12 = (aji_1)this.dGV.remove(string);
            if (aji_12 == this.aPd) {
                this.aPd = null;
            }
            if (aji_12 != null) {
                aji_12.clear();
            }
        }
    }

    public aji_1 lh(String string) {
        return (aji_1)this.dGV.get(string);
    }

    public aji_1 aRR() {
        return this.aPd;
    }

    public void d(aji_1 aji_12) {
        this.aPd = aji_12;
    }

    public Ur[] aRS() {
        if (this.dGX != null) {
            return this.dGX.toArray(new Ur[0]);
        }
        return null;
    }

    public Ur aRT() {
        if (this.dGX != null) {
            return (Ur)this.dGX.get(this.dGX.size() - 1);
        }
        return null;
    }

    public Ur li(String string) {
        if (this.dGW == null) {
            return null;
        }
        return (Ur)this.dGW.get(string);
    }

    public Collection aRU() {
        if (this.dGW == null) {
            return null;
        }
        return this.dGW.values();
    }

    public void a(String string, Ur ur) {
        if (this.dGW == null) {
            this.dGW = new HashMap();
            this.dGX = new ArrayList();
        }
        this.dGW.put(string, ur);
        this.dGX.add(ur);
    }

    public void lj(String string) {
        if (this.dGW == null) {
            return;
        }
        Ur ur = (Ur)this.dGW.get(string);
        this.dGX.remove(ur);
    }

    public void lk(String string) {
        Ur ur = (Ur)this.dGW.remove(string);
        this.dGX.remove(ur);
    }

    public void a(Ur ur) {
        String string = null;
        for (Map.Entry entry : this.dGW.entrySet()) {
            if (entry.getValue() != ur) continue;
            string = (String)entry.getKey();
            break;
        }
        this.dGW.remove(string);
        this.dGX.remove(ur);
    }
}

