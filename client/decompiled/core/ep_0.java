/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;

/*
 * Renamed from ep
 */
public class ep_0
implements abc_0 {
    protected static final Logger a = Logger.getLogger(ep_0.class);
    private int ol;
    private int om;
    private short on;
    private qc_0 ak;
    private static final lb_0 oo = new lb_0();

    public amx_2 af(int n2) {
        return (amx_2)oo.get(n2);
    }

    public void a(int n2, int n3, short s, qc_0 qc_02) {
        this.ol = n2;
        this.om = n3;
        this.on = s;
        this.ak = qc_02;
    }

    public List a(int n2, int n3, short s) {
        Vector<amx_2> vector = new Vector<amx_2>();
        vector.add(this.b(n2, n3, s));
        return vector;
    }

    public amx_2 b(int n2, int n3, short s) {
        if (this.ak == null) {
            a.error((Object)"direction ou position null : update partLocalisator first");
            return null;
        }
        if (this.ol == n2 && this.om == n3 && this.on == s) {
            return (amx_2)oo.get(0);
        }
        agv_0 agv_02 = new agv_0(this.ak.acJ()[0], this.ak.acJ()[1], 0.0f);
        agv_0 agv_03 = new agv_0(this.ol - n2, this.om - n3, this.on - s);
        double d = (agv_03 = agv_03.aSA()).p(agv_02);
        if (d >= 0.5) {
            return (amx_2)oo.get(2);
        }
        if (d >= -0.5) {
            return (amx_2)oo.get(3);
        }
        return (amx_2)oo.get(0);
    }

    public amx_2 a(agv_0 agv_02) {
        if (this.ak == null) {
            a.error((Object)"direction null : update partLocalisator first");
            return null;
        }
        if (agv_02.getX() == 0.0f && agv_02.getY() == 0.0f) {
            return (amx_2)oo.get(0);
        }
        agv_0 agv_03 = new agv_0(this.ak.acJ()[0], this.ak.acJ()[1], 0.0f);
        double d = (agv_02 = agv_02.aSA()).p(agv_03);
        if (d >= 0.5) {
            return (amx_2)oo.get(2);
        }
        if (d >= -0.5) {
            return (amx_2)oo.get(3);
        }
        return (amx_2)oo.get(0);
    }

    public void reset() {
        this.ol = 0;
        this.om = 0;
        this.on = 0;
        this.ak = null;
    }

    static {
        oo.c(0, new amx_2(0));
        oo.c(1, new amx_2(1));
        oo.c(2, new amx_2(2));
        oo.c(3, new amx_2(3));
    }
}

