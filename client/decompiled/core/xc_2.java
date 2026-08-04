/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from xc
 */
public class xc_2 {
    protected static Logger a = Logger.getLogger(xc_2.class);
    private static final jw_1[] avZ = new jw_1[0];
    abd_0[] Db;
    jw_1[] awa;

    static xc_2 a(xc_2 xc_22) {
        xc_2 xc_23 = new xc_2();
        xc_23.Db = xc_22.Db;
        xc_23.awa = avZ;
        return xc_23;
    }

    public final int b(acf acf2, int n2) {
        int n3;
        int n4 = acf2.readShort() & 0xFFFF;
        this.Db = new abd_0[n4];
        for (n3 = 0; n3 < this.Db.length; ++n3) {
            this.Db[n3] = aca.o(acf2);
        }
        n3 = acf2.readByte() & 0xFF;
        this.awa = new jw_1[n3];
        for (int j = 0; j < this.awa.length; ++j) {
            byte by = (byte)(acf2.readByte() & 0xFF);
            byte by2 = (byte)(acf2.readByte() & 0xFF);
            aro aro2 = aro.aR(by);
            jw_1 jw_12 = abz_0.cjd.aqC().a(aro2);
            try {
                jw_12.gw(n2);
                jw_12.a(by2, acf2);
                this.awa[j] = jw_12;
                continue;
            }
            catch (Exception exception) {
                a.error((Object)("Exception durant le chargement d'une action anm actionId=" + by + " parametersCount=" + by2), (Throwable)exception);
            }
        }
        return acf2.readShort() & 0xFFFF;
    }

    public final void a(aij_1 aij_12) {
        int n2;
        aij_12.writeShort((short)this.Db.length);
        for (n2 = 0; n2 < this.Db.length; ++n2) {
            this.Db[n2].a(aij_12);
        }
        aij_12.writeByte((byte)this.awa.length);
        for (n2 = 0; n2 < this.awa.length; ++n2) {
            try {
                this.awa[n2].a(aij_12);
                continue;
            }
            catch (Exception exception) {
                a.error((Object)"Exception durant la sauvegarde d'une action anm", (Throwable)exception);
            }
        }
        aij_12.writeShort((short)0);
    }

    public final int getSize() {
        int n2;
        int n3 = 0;
        for (n2 = 0; n2 < this.Db.length; ++n2) {
            n3 += this.Db[n2].getSize();
        }
        for (n2 = 0; n2 < this.awa.length; ++n2) {
            n3 += this.awa[n2].getSize();
        }
        return n3;
    }

    public final abd_0[] oe() {
        return this.Db;
    }
}

