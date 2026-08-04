/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import org.apache.log4j.Logger;

/*
 * Renamed from asU
 */
public class asu_0 {
    protected static final Logger a = Logger.getLogger(asu_0.class);
    public lb_0 cSF;

    public void init() {
        this.cSF = new lb_0();
    }

    public void reset() {
        if (this.cSF != null) {
            this.cSF.clear();
        }
    }

    public void c(int n2, short s, short s2) {
        int n3;
        aBp aBp2 = (aBp)this.cSF.get(n2);
        if (aBp2 == null) {
            aBp2 = new aBp();
            this.cSF.c(n2, aBp2);
        }
        if (!aBp2.contains(n3 = asu_0.v(s, s2))) {
            aBp2.nk(n3);
        }
    }

    public aBp mh(int n2) {
        if (this.cSF != null) {
            return (aBp)this.cSF.get(n2);
        }
        return null;
    }

    public static int v(short s, short s2) {
        return ej_0.a(s, s2);
    }

    public static short mi(int n2) {
        return ej_0.an(n2);
    }

    public static short mj(int n2) {
        return ej_0.ao(n2);
    }

    public void b(acf acf2) {
        int n2 = acf2.readInt();
        this.cSF = new lb_0(n2);
        for (int j = 0; j < n2; ++j) {
            int n3 = acf2.readInt();
            int n4 = acf2.readInt();
            if (n4 == 0) continue;
            aBp aBp2 = new aBp(n4);
            this.cSF.c(n3, aBp2);
            for (int i2 = 0; i2 < n4; ++i2) {
                aBp2.nk(acf2.readInt());
            }
        }
    }

    public void a(aij_1 aij_12) {
        try {
            aij_12.writeInt(this.cSF.size());
            if (!this.cSF.isEmpty()) {
                this.cSF.a(new nx_0(this, aij_12));
            }
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
        }
    }
}

