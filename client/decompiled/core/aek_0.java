/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aeK
 */
public class aek_0 {
    private static final int cpu = 1;
    private static final int cpv = 2;
    private static final int cpw = 4;
    private static final int cpx = 8;
    private static final int cpy = 16;
    private static final int cpz = 32;
    private static final int cpA = 64;
    private byte CZ;
    private float Gx;
    private float cpB;
    private String[] alQ;
    private Fa[] cpC;
    private final ano_0 cpD = new ano_0();
    private ait_2[] cpE;
    private Ze[] cpF;

    public aek_0() {
        this.clear();
    }

    public final void b(acf acf2) {
        int n2;
        int n3;
        int n4;
        this.CZ = acf2.readByte();
        if (this.auC()) {
            this.Gx = acf2.readFloat();
        }
        if (this.auE()) {
            this.cpB = acf2.readFloat();
        }
        if (this.auD()) {
            n4 = acf2.readShort() & 0xFFFF;
            this.alQ = new String[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.alQ[n3] = acf2.readString();
            }
        }
        if (this.auF()) {
            n4 = acf2.readByte() & 0xFF;
            this.cpE = new ait_2[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                int n5 = acf2.readInt();
                n2 = acf2.readInt();
                this.cpE[n3] = new ait_2(n5, n2);
            }
        }
        if (this.auH()) {
            n4 = acf2.readByte() & 0xFF;
            this.cpF = new Ze[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                String string = acf2.readString();
                n2 = acf2.readInt();
                this.cpF[n3] = new Ze(string, n2);
            }
        }
        n4 = acf2.readShort() & 0xFFFF;
        this.cpC = new Fa[n4];
        this.cpD.ensureCapacity(n4);
        for (n3 = 0; n3 < this.cpC.length; ++n3) {
            Fa fa;
            this.cpC[n3] = fa = new Fa();
            fa.b(acf2);
            this.cpD.put(fa.m_name, fa);
        }
    }

    public final void a(aij_1 aij_12) {
        Object object;
        int n2;
        aij_12.writeByte(this.CZ);
        if (this.auC()) {
            aij_12.writeFloat(this.Gx);
        }
        if (this.auE()) {
            aij_12.writeFloat(this.cpB);
        }
        if (this.auD()) {
            aij_12.writeShort((short)this.alQ.length);
            for (n2 = 0; n2 < this.alQ.length; ++n2) {
                aij_12.writeString(this.alQ[n2]);
            }
        }
        if (this.auF()) {
            aij_12.writeByte((byte)this.cpE.length);
            for (n2 = 0; n2 < this.cpE.length; ++n2) {
                object = this.cpE[n2];
                aij_12.writeInt(((ait_2)object).ccG);
                aij_12.writeInt(((ait_2)object).dPR);
            }
        }
        if (this.auH()) {
            aij_12.writeByte((byte)this.cpF.length);
            for (n2 = 0; n2 < this.cpF.length; ++n2) {
                object = this.cpF[n2];
                aij_12.writeString(((Ze)object).ccF);
                aij_12.writeInt(((Ze)object).ccG);
            }
        }
        if (this.cpC == null) {
            aij_12.writeShort((short)0);
        } else {
            aij_12.writeShort((short)this.cpC.length);
            for (n2 = 0; n2 < this.cpC.length; ++n2) {
                this.cpC[n2].a(aij_12);
            }
        }
    }

    public final Fa hO(String string) {
        return (Fa)this.cpD.get(string);
    }

    public final String kk(int n2) {
        return this.alQ[n2];
    }

    public final Fa[] auz() {
        return this.cpC;
    }

    public final String[] auA() {
        return this.alQ;
    }

    public final ait_2[] auB() {
        return this.cpE;
    }

    public final float getScale() {
        return this.Gx;
    }

    public final float jY() {
        return this.cpB;
    }

    private boolean auC() {
        return (this.CZ & 1) != 0;
    }

    private boolean auD() {
        return (this.CZ & 2) != 0;
    }

    public final boolean auE() {
        return (this.CZ & 8) != 0;
    }

    public final boolean auF() {
        return (this.CZ & 4) != 0;
    }

    public final boolean auG() {
        return (this.CZ & 0x10) == 0;
    }

    public final boolean cL() {
        return (this.CZ & 0x20) == 32;
    }

    public final boolean auH() {
        return (this.CZ & 0x40) != 0;
    }

    public final void clear() {
        this.CZ = 0;
        this.Gx = 1.0f;
        this.cpB = 1.0f;
        this.alQ = null;
        this.cpC = null;
        this.cpD.clear();
    }

    public void u(ArrayList arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.CZ = (byte)(this.CZ | 2);
        this.alQ = arrayList.toArray(new String[arrayList.size()]);
    }

    public void v(ArrayList arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.CZ = (byte)(this.CZ | 4);
        this.cpE = new ait_2[arrayList.size()];
        arrayList.toArray(this.cpE);
    }

    public void w(ArrayList arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.CZ = (byte)(this.CZ | 0x40);
        this.cpF = new Ze[arrayList.size()];
        arrayList.toArray(this.cpF);
    }

    public void setScale(float f) {
        if (f != 1.0f) {
            this.CZ = (byte)(this.CZ | 1);
            this.Gx = f;
        }
    }

    public void aP(float f) {
        if (f != 1.0f) {
            this.CZ = (byte)(this.CZ | 8);
            this.cpB = f;
        }
    }

    public void dq(boolean bl2) {
        this.CZ = bl2 ? (byte)(this.CZ | 0x10) : (byte)(this.CZ & 0xFFFFFFEF);
    }

    public void a(Fa[] faArray) {
        this.cpC = faArray;
    }

    public void cM() {
        this.CZ = (byte)(this.CZ | 0x20);
    }

    public Ze[] auI() {
        return this.cpF;
    }
}

