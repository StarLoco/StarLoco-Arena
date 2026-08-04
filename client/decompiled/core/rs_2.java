/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from rs
 */
public abstract class rs_2 {
    private long uN;
    private short uM;
    private final zm_1 agK = new zm_1();

    public final byte[] wB() {
        int n2 = 0;
        short[] sArray = this.agK.Gj();
        Object[] objectArray = this.agK.getValues();
        int n3 = objectArray.length;
        for (int j = 0; j < n3; ++j) {
            alm_1 alm_12 = (alm_1)objectArray[j];
            n2 += 3 + alm_12.aBa().fP();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(12 + n2);
        byteBuffer.putShort(this.uM);
        byteBuffer.putLong(this.uN);
        byteBuffer.putShort((short)this.agK.size());
        for (short s : sArray) {
            alm_1 alm_13 = (alm_1)this.agK.an(s);
            byteBuffer.putShort(s);
            byteBuffer.put(alm_13.aBa().fQ());
            if (alm_13.aBa() == dr_2.ly) {
                byteBuffer.putInt(alm_13.getIntValue());
                continue;
            }
            if (alm_13.aBa() == dr_2.lz) {
                byteBuffer.putLong(alm_13.getLongValue());
                continue;
            }
            if (alm_13.aBa() != dr_2.lA) continue;
            byteBuffer.putFloat(alm_13.getFloatValue());
        }
        return byteBuffer.array();
    }

    public final void u(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.uM = byteBuffer.getShort();
        this.uN = byteBuffer.getLong();
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            short s = byteBuffer.getShort();
            byte by = byteBuffer.get();
            if (by == dr_2.ly.fQ()) {
                this.agK.b(s, new alm_1(byteBuffer.getInt()));
                continue;
            }
            if (by == dr_2.lz.fQ()) {
                this.agK.b(s, new alm_1(byteBuffer.getLong()));
                continue;
            }
            if (by != dr_2.lA.fQ()) continue;
            this.agK.b(s, new alm_1(byteBuffer.getFloat()));
        }
    }

    static short v(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        return byteBuffer.getShort();
    }

    static long w(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byteBuffer.getShort();
        return byteBuffer.getLong();
    }

    final void a(rs_2 rs_22) {
        this.uM = rs_22.uM;
        for (short s : this.agK.Gj()) {
            alm_1 alm_12 = (alm_1)this.agK.an(s);
            dr_2 dr_22 = alm_12.aBa();
            if (dr_22 == dr_2.ly) {
                this.a(s, alm_12.getIntValue());
                continue;
            }
            if (dr_22 == dr_2.lz) {
                this.a(s, alm_12.getLongValue());
                continue;
            }
            if (dr_22 != dr_2.lA) continue;
            this.a(s, alm_12.getFloatValue());
        }
    }

    final void a(short s, int n2) {
        this.agK.b(s, new alm_1(n2));
    }

    final void a(short s, long l2) {
        this.agK.b(s, new alm_1(l2));
    }

    final void a(short s, float f) {
        this.agK.b(s, new alm_1(f));
    }

    public final void b(short s, int n2) {
        alm_1 alm_12 = (alm_1)this.agK.an(s);
        if (alm_12 == null) {
            this.a(s, n2);
        } else {
            alm_12.g(n2);
        }
    }

    public final void b(short s, long l2) {
        alm_1 alm_12 = (alm_1)this.agK.an(s);
        if (alm_12 == null) {
            this.a(s, l2);
        } else {
            alm_12.e(l2);
        }
    }

    public final void b(short s, float f) {
        alm_1 alm_12 = (alm_1)this.agK.an(s);
        if (alm_12 == null) {
            this.a(s, f);
        } else {
            alm_12.c(f);
        }
    }

    final dr_2 U(short s) {
        alm_1 alm_12 = (alm_1)this.agK.an(s);
        if (alm_12 != null) {
            return alm_12.aBa();
        }
        return null;
    }

    public int V(short s) {
        int n2 = 0;
        alm_1 alm_12 = (alm_1)this.agK.an(s);
        if (alm_12 != null) {
            n2 = alm_12.aBa() == dr_2.lA ? Math.round(alm_12.getFloatValue()) : alm_12.getIntValue();
        }
        return n2;
    }

    public long W(short s) {
        long l2 = 0L;
        alm_1 alm_12 = (alm_1)this.agK.an(s);
        if (alm_12 != null) {
            l2 = alm_12.aBa() == dr_2.lA ? (long)Math.round(alm_12.getFloatValue()) : alm_12.getLongValue();
        }
        return l2;
    }

    public float X(short s) {
        float f = 0.0f;
        alm_1 alm_12 = (alm_1)this.agK.an(s);
        if (alm_12 != null) {
            f = alm_12.aBa() != dr_2.lA ? (float)alm_12.getLongValue() : alm_12.getFloatValue();
        }
        return f;
    }

    public final short wC() {
        return this.uM;
    }

    final void y(short s) {
        this.uM = s;
    }

    public final long wD() {
        return this.uN;
    }

    final void R(long l2) {
        this.uN = l2;
    }

    public abstract rs_2 S();

    public abstract void initialize();

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Statistiques (type = ").append(this.uM).append(", id = ").append(this.uN).append("){\r\n");
        for (short s : this.agK.Gj()) {
            alm_1 alm_12 = (alm_1)this.agK.an(s);
            stringBuffer.append("\t\t").append(s).append(" = ").append(alm_12).append("\r\n");
        }
        stringBuffer.append("}\r\n");
        return stringBuffer.toString();
    }
}

