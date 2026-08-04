/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class sH {
    private final kl_1 akS = new kl_1();
    private final ano_2 akT = new ano_2();
    private final aLO akU = new aLO();
    protected no akV = new no();

    public void reset() {
        this.akS.clear();
        this.akT.clear();
        this.akU.clear();
        this.akV.clear();
    }

    public void yB() {
        this.akT.clear();
        this.akU.clear();
    }

    public void a(fv fv2, short s, aOf aOf2) {
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        if (fv2.iV() > 0) {
            if (fv2.iV() == 63) {
                this.akS.h(fv2.getId(), (short)-1);
            } else {
                this.akS.h(fv2.getId(), (short)(s + fv2.iV()));
            }
        }
        if (fv2.iU() > 0) {
            Integer n2 = this.akT.get(fv2.getId());
            if (n2 == null) {
                this.akT.bz(fv2.getId(), 1);
            } else {
                this.akT.bz(fv2.getId(), n2 + 1);
            }
        }
        if (aOf2 != null && fv2.iS() > 0) {
            long l2 = this.a(fv2, aOf2);
            Integer n3 = this.akU.eL(l2);
            if (n3 == null) {
                this.akU.m(l2, 1);
            } else {
                this.akU.m(l2, n3 + 1);
            }
        }
    }

    public void a(fv fv2, int n2) {
        Short s;
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        if (fv2.iV() > 0 && this.akS.v(fv2.getId()) && (s = Short.valueOf(this.akS.bU(fv2.getId()))) != -1 && n2 >= s) {
            this.akS.bV(fv2.getId());
        }
    }

    public jv_1 b(fv fv2, int n2) {
        return this.a(fv2, n2, null);
    }

    public jv_1 a(fv fv2, int n2, aOf aOf2) {
        long l2;
        Integer n3;
        Number number;
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        if (fv2.iV() > 0 && this.akS.v(fv2.getId()) && ((Short)(number = Short.valueOf(this.akS.bU(fv2.getId()))) == -1 || n2 < (Short)number)) {
            return jv_1.bly;
        }
        if (fv2.iU() > 0 && (number = Integer.valueOf(this.akT.get(fv2.getId()))) != null && (Integer)number >= fv2.iU()) {
            return jv_1.blx;
        }
        if (aOf2 != null && fv2.iS() > 0 && (n3 = Integer.valueOf(this.akU.eL(l2 = this.a(fv2, aOf2)))) != null && n3 >= fv2.iS()) {
            return jv_1.blw;
        }
        return jv_1.blp;
    }

    public jv_1 c(fv fv2) {
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        if (fv2.iT() > 0 && this.akV.contains(fv2.getId()) && this.akV.get(fv2.getId()) >= fv2.iT()) {
            return jv_1.blw;
        }
        return jv_1.blp;
    }

    public void d(fv fv2) {
        Short s;
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        if ((s = Short.valueOf(this.akV.get(fv2.getId()))) == null) {
            s = 0;
        }
        Short s2 = s;
        Short s3 = s = Short.valueOf((short)(s + 1));
        this.akV.g(fv2.getId(), s);
    }

    public void e(fv fv2) {
        int n2;
        Short s;
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        if ((s = Short.valueOf(this.akV.get(n2 = fv2.getId()))) != null) {
            Short s2 = s;
            Short s3 = s = Short.valueOf((short)(s - 1));
            if (s > 0) {
                this.akV.g(n2, s);
            } else {
                this.akV.ch(n2);
            }
        }
    }

    private long a(fv fv2, aOf aOf2) {
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        return (long)fv2.getId() << 32 | (long)ha_0.q(aOf2);
    }

    public void a(fv fv2, int n2, short s) {
        int n3;
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        if (this.akS.v(n3 = fv2.getId()) && (this.akS.bU(n3) == -1 || this.akS.bU(n3) > n2 + s)) {
            this.akS.h(n3, (short)(n2 + s));
        }
    }

    public int c(fv fv2, int n2) {
        if (fv2.jd() != null) {
            fv2 = fv2.jd();
        }
        if (this.akS.v(fv2.getId())) {
            if (this.akS.bU(fv2.getId()) != -1) {
                return this.akS.bU(fv2.getId()) - n2;
            }
            return -1;
        }
        return 0;
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + this.akS.size() * 10);
        byteBuffer.put((byte)this.akS.size());
        long[] lArray = this.akS.eJ();
        for (int j = 0; j < lArray.length; ++j) {
            byteBuffer.putLong(lArray[j]);
            byteBuffer.putShort(this.akS.bU(lArray[j]));
        }
        return byteBuffer.array();
    }

    public void b(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            this.akS.h(byteBuffer.getLong(), byteBuffer.getShort());
        }
    }
}

