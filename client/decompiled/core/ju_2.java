/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import org.apache.log4j.Logger;

/*
 * Renamed from jU
 */
public class ju_2 {
    private static final Logger a = Logger.getLogger(ju_2.class);
    public static final int LOOP = 128;
    public static final int CV = 64;
    public static final int CW = 63;
    public short fL;
    String m_name;
    public int CX;
    public int CY;
    byte CZ;
    abd_0[] Da;
    abd_0[] Db;
    xc_2[] Dc;
    boolean Dd;

    public final void b(acf acf2) {
        int n2;
        int n3;
        int n4;
        this.fL = acf2.readShort();
        this.CZ = acf2.readByte();
        if ((this.CZ & 0x40) != 0) {
            this.m_name = acf2.readString().intern();
        }
        this.CX = acf2.readInt();
        this.CY = acf2.readInt();
        int n5 = acf2.readByte() & 0xFF;
        this.Da = new abd_0[n5];
        for (n4 = 0; n4 < this.Da.length; ++n4) {
            this.Da[n4] = aca.o(acf2);
        }
        n4 = acf2.readByte() & 0xFF;
        this.Db = new abd_0[n4];
        for (n3 = 0; n3 < this.Db.length; ++n3) {
            this.Db[n3] = aca.o(acf2);
        }
        n3 = acf2.readShort() & 0xFFFF;
        this.Dc = new xc_2[n3];
        for (int j = 0; j < this.Dc.length; ++j) {
            this.Dc[j] = new xc_2();
            n2 = this.Dc[j].b(acf2, j);
            if (n2 <= 0) continue;
            try {
                xc_2 xc_22 = xc_2.a(this.Dc[j]);
                for (int i2 = 0; i2 < n2; ++i2) {
                    this.Dc[++j] = xc_22;
                }
                continue;
            }
            catch (Exception exception) {
                throw new IOException("errur avec la defintion " + this.m_name + ". nombre de frame incorrete");
            }
        }
        this.Dd = false;
        if (this.Dc.length == 1 && this.Da.length == 0 && this.Db.length == 0) {
            jw_1[] jw_1Array = this.Dc[0].awa;
            for (n2 = 0; n2 < jw_1Array.length; ++n2) {
                switch (jw_1Array[n2].ek()) {
                    case cPs: 
                    case cPz: 
                    case cPv: 
                    case cPt: {
                        this.Dd = true;
                        return;
                    }
                }
            }
        }
    }

    public final void a(aij_1 aij_12) {
        int n2;
        aij_12.writeShort(this.fL);
        aij_12.writeByte(this.CZ);
        if ((this.CZ & 0x40) != 0) {
            aij_12.writeString(this.m_name);
        }
        aij_12.writeInt(this.CX);
        aij_12.writeInt(this.CY);
        aij_12.writeByte((byte)this.Da.length);
        for (n2 = 0; n2 < this.Da.length; ++n2) {
            this.Da[n2].a(aij_12);
        }
        aij_12.writeByte((byte)this.Db.length);
        for (n2 = 0; n2 < this.Db.length; ++n2) {
            this.Db[n2].a(aij_12);
        }
        aij_12.writeShort((short)this.Dc.length);
        for (n2 = 0; n2 < this.Dc.length; ++n2) {
            this.Dc[n2].a(aij_12);
        }
    }

    public final int getSize() {
        int n2;
        int n3 = 11;
        n3 += this.m_name.length();
        for (n2 = 0; n2 < this.Da.length; ++n2) {
            n3 += this.Da[n2].getSize();
        }
        for (n2 = 0; n2 < this.Db.length; ++n2) {
            n3 += this.Db[n2].getSize();
        }
        for (n2 = 0; n2 < this.Dc.length; ++n2) {
            n3 += this.Dc[n2].getSize();
        }
        return n3;
    }

    public final String getName() {
        return this.m_name;
    }

    public final int getFrameCount() {
        if (this.og()) {
            return Integer.MAX_VALUE;
        }
        return this.Dc.length;
    }

    public final String toString() {
        return new StringBuilder(this.fL).append(" [").append(this.m_name).append("]").toString();
    }

    public final abd_0[] od() {
        return this.Da;
    }

    public final abd_0[] oe() {
        return this.Db;
    }

    public final xc_2[] of() {
        return this.Dc;
    }

    public final boolean og() {
        return (this.CZ & 0x80) != 0;
    }

    public final boolean oh() {
        return this.Dd;
    }
}

