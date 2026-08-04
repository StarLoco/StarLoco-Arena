/*
 * Decompiled with CFR 0.152.
 */
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

/*
 * Renamed from Jr
 */
public class jr_0
extends Random
implements Serializable {
    private static final long serialVersionUID = 2932129847991607657L;
    private static final jr_0 bkN = new jr_0();
    private static final int bkO = 624;
    private static final int bkP = 397;
    private static final int bkQ = -1727483681;
    private static final int bkR = Integer.MIN_VALUE;
    private static final int bkS = Integer.MAX_VALUE;
    private static final int bkT = -1658038656;
    private static final int bkU = -272236544;
    private int[] bkV;
    private int bkW;
    private int[] bkX;
    private double bkY;
    private boolean bkZ;

    public jr_0() {
        this(System.currentTimeMillis());
    }

    public jr_0(long l2) {
        super(l2);
        this.setSeed(l2);
    }

    public jr_0(int[] nArray) {
        super(System.currentTimeMillis());
        this.y(nArray);
    }

    public synchronized void setSeed(long l2) {
        super.setSeed(l2);
        this.bkZ = false;
        this.bkV = new int[624];
        this.bkX = new int[2];
        this.bkX[0] = 0;
        this.bkX[1] = -1727483681;
        this.bkV[0] = (int)(l2 & 0xFFFFFFFL);
        this.bkW = 1;
        while (this.bkW < 624) {
            this.bkV[this.bkW] = 1812433253 * (this.bkV[this.bkW - 1] ^ this.bkV[this.bkW - 1] >>> 30) + this.bkW;
            int n2 = this.bkW++;
            this.bkV[n2] = this.bkV[n2] & 0xFFFFFFFF;
        }
    }

    public synchronized void y(int[] nArray) {
        int n2;
        this.setSeed(19650218L);
        int n3 = 1;
        int n4 = 0;
        int n5 = n2 = 624 > nArray.length ? 624 : nArray.length;
        while (n2 != 0) {
            this.bkV[n3] = (this.bkV[n3] ^ (this.bkV[n3 - 1] ^ this.bkV[n3 - 1] >>> 30) * 1664525) + nArray[n4] + n4;
            int n6 = n3++;
            this.bkV[n6] = this.bkV[n6] & 0xFFFFFFFF;
            ++n4;
            if (n3 >= 624) {
                this.bkV[0] = this.bkV[623];
                n3 = 1;
            }
            if (n4 >= nArray.length) {
                n4 = 0;
            }
            --n2;
        }
        for (n2 = 623; n2 != 0; --n2) {
            this.bkV[n3] = (this.bkV[n3] ^ (this.bkV[n3 - 1] ^ this.bkV[n3 - 1] >>> 30) * 1566083941) - n3;
            int n7 = n3++;
            this.bkV[n7] = this.bkV[n7] & 0xFFFFFFFF;
            if (n3 < 624) continue;
            this.bkV[0] = this.bkV[623];
            n3 = 1;
        }
        this.bkV[0] = Integer.MIN_VALUE;
    }

    protected synchronized int next(int n2) {
        int n3;
        if (this.bkW >= 624) {
            int n4;
            for (n4 = 0; n4 < 227; ++n4) {
                n3 = this.bkV[n4] & Integer.MIN_VALUE | this.bkV[n4 + 1] & Integer.MAX_VALUE;
                this.bkV[n4] = this.bkV[n4 + 397] ^ n3 >>> 1 ^ this.bkX[n3 & 1];
            }
            while (n4 < 623) {
                n3 = this.bkV[n4] & Integer.MIN_VALUE | this.bkV[n4 + 1] & Integer.MAX_VALUE;
                this.bkV[n4] = this.bkV[n4 + -227] ^ n3 >>> 1 ^ this.bkX[n3 & 1];
                ++n4;
            }
            n3 = this.bkV[623] & Integer.MIN_VALUE | this.bkV[0] & Integer.MAX_VALUE;
            this.bkV[623] = this.bkV[396] ^ n3 >>> 1 ^ this.bkX[n3 & 1];
            this.bkW = 0;
        }
        n3 = this.bkV[this.bkW++];
        n3 ^= n3 >>> 11;
        n3 ^= n3 << 7 & 0x9D2C5680;
        n3 ^= n3 << 15 & 0xEFC60000;
        n3 ^= n3 >>> 18;
        return n3 >>> 32 - n2;
    }

    private synchronized void a(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
    }

    private synchronized void a(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
    }

    public boolean nextBoolean() {
        return this.next(1) != 0;
    }

    public boolean af(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
        }
        if (f == 0.0f) {
            return false;
        }
        if (f == 1.0f) {
            return true;
        }
        return this.nextFloat() < f;
    }

    public boolean n(double d) {
        if (d < 0.0 || d > 1.0) {
            throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
        }
        if (d == 0.0) {
            return false;
        }
        if (d == 1.0) {
            return true;
        }
        return this.nextDouble() < d;
    }

    public int nextInt(int n2) {
        int n3;
        int n4;
        if (n2 < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        if (n2 == 0) {
            return 0;
        }
        if ((n2 & -n2) == n2) {
            return (int)((long)n2 * (long)this.next(31) >> 31);
        }
        while ((n4 = this.next(31)) - (n3 = n4 % n2) + (n2 - 1) < 0) {
        }
        return n3;
    }

    public long bS(long l2) {
        long l3;
        long l4;
        if (l2 < 0L) {
            throw new IllegalArgumentException("n must be > 0");
        }
        if (l2 == 0L) {
            return 0L;
        }
        while ((l4 = this.nextLong() >>> 1) - (l3 = l4 % l2) + (l2 - 1L) < 0L) {
        }
        return l3;
    }

    public double nextDouble() {
        return (double)(((long)this.next(26) << 27) + (long)this.next(27)) / 9.007199254740992E15;
    }

    public float nextFloat() {
        return (float)this.next(24) / 1.6777216E7f;
    }

    public void nextBytes(byte[] byArray) {
        for (int j = 0; j < byArray.length; ++j) {
            byArray[j] = (byte)this.next(8);
        }
    }

    public char VE() {
        return (char)this.next(16);
    }

    public short nextShort() {
        return (short)this.next(16);
    }

    public byte nextByte() {
        return (byte)this.next(8);
    }

    public synchronized double nextGaussian() {
        double d;
        double d2;
        double d3;
        if (this.bkZ) {
            this.bkZ = false;
            return this.bkY;
        }
        while ((d3 = (d2 = 2.0 * this.nextDouble() - 1.0) * d2 + (d = 2.0 * this.nextDouble() - 1.0) * d) >= 1.0 || d3 == 0.0) {
        }
        double d4 = Math.sqrt(-2.0 * Math.log(d3) / d3);
        this.bkY = d * d4;
        this.bkZ = true;
        return d2 * d4;
    }

    public static final synchronized jr_0 VF() {
        return bkN;
    }
}

