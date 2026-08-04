/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.sql.Timestamp;
import java.util.HashMap;
import org.apache.log4j.Logger;

public abstract class arr
implements JG {
    protected static final Logger a = Logger.getLogger(arr.class);
    protected acl_0 uG = null;
    protected eP cPN = null;
    private HashMap bPO = null;

    public void b() {
    }

    public void j() {
        this.bPO = null;
        this.cPN = null;
    }

    public void a(acl_0 acl_02) {
        this.uG = acl_02;
    }

    public void a(ob_0 ob_02) {
        this.cPN = new eP();
        this.cPN.a(ob_02);
    }

    public boolean aEx() {
        return this.cPN != null;
    }

    public boolean aEy() {
        if (this.cPN != null) {
            return this.cPN.asi();
        }
        return false;
    }

    public ob_0 aEz() {
        if (this.cPN == null) {
            return null;
        }
        return (ob_0)this.cPN.asj();
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.cPN = null;
                this.bPO = null;
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)("Exception lev\u00e9e lors du release d'une SqlRequest (" + this.getClass().getSimpleName() + "): "), (Throwable)exception);
            }
        }
    }

    public abstract pr_0 a(jn_0 var1);

    public abstract int kn();

    public abstract int getId();

    public void setProperty(String string, Object object) {
        if (this.bPO == null) {
            this.bPO = new HashMap();
        }
        this.bPO.put(string, object);
    }

    public int jf(String string) {
        if (this.bPO != null && this.bPO.containsKey(string)) {
            return (Integer)this.bPO.get(string);
        }
        throw new tx_0(string);
    }

    public long jg(String string) {
        if (this.bPO != null && this.bPO.containsKey(string)) {
            return (Long)this.bPO.get(string);
        }
        throw new tx_0(string);
    }

    public float jh(String string) {
        if (this.bPO != null && this.bPO.containsKey(string)) {
            return ((Float)this.bPO.get(string)).floatValue();
        }
        throw new tx_0(string);
    }

    public double ji(String string) {
        if (this.bPO != null && this.bPO.containsKey(string)) {
            return (Double)this.bPO.get(string);
        }
        throw new tx_0(string);
    }

    public String jj(String string) {
        if (this.bPO != null && this.bPO.containsKey(string)) {
            return (String)this.bPO.get(string);
        }
        throw new tx_0(string);
    }

    public Timestamp jk(String string) {
        if (this.bPO != null && this.bPO.containsKey(string)) {
            return (Timestamp)this.bPO.get(string);
        }
        throw new tx_0(string);
    }
}

