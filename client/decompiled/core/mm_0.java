/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Random;

/*
 * Renamed from mM
 */
public class mm_0
implements Externalizable,
Cloneable {
    static final long serialVersionUID = 1L;
    protected short[] Lr;
    protected int BI;
    protected static final int DEFAULT_CAPACITY = 10;

    public mm_0() {
        this(10);
    }

    public mm_0(int n2) {
        this.Lr = new short[n2];
        this.BI = 0;
    }

    public mm_0(short[] sArray) {
        this(Math.max(sArray.length, 10));
        this.g(sArray);
    }

    public void ensureCapacity(int n2) {
        if (n2 > this.Lr.length) {
            int n3 = Math.max(this.Lr.length << 1, n2);
            short[] sArray = new short[n3];
            System.arraycopy(this.Lr, 0, sArray, 0, this.Lr.length);
            this.Lr = sArray;
        }
    }

    public int size() {
        return this.BI;
    }

    public boolean isEmpty() {
        return this.BI == 0;
    }

    public void trimToSize() {
        if (this.Lr.length > this.size()) {
            short[] sArray = new short[this.size()];
            this.b(sArray, 0, sArray.length);
            this.Lr = sArray;
        }
    }

    public void add(short s) {
        this.ensureCapacity(this.BI + 1);
        this.Lr[this.BI++] = s;
    }

    public void g(short[] sArray) {
        this.a(sArray, 0, sArray.length);
    }

    public void a(short[] sArray, int n2, int n3) {
        this.ensureCapacity(this.BI + n3);
        System.arraycopy(sArray, n2, this.Lr, this.BI, n3);
        this.BI += n3;
    }

    public void a(int n2, short s) {
        if (n2 == this.BI) {
            this.add(s);
            return;
        }
        this.ensureCapacity(this.BI + 1);
        System.arraycopy(this.Lr, n2, this.Lr, n2 + 1, this.BI - n2);
        this.Lr[n2] = s;
        ++this.BI;
    }

    public void a(int n2, short[] sArray) {
        this.b(n2, sArray, 0, sArray.length);
    }

    public void b(int n2, short[] sArray, int n3, int n4) {
        if (n2 == this.BI) {
            this.a(sArray, n3, n4);
            return;
        }
        this.ensureCapacity(this.BI + n4);
        System.arraycopy(this.Lr, n2, this.Lr, n2 + n4, this.BI - n2);
        System.arraycopy(sArray, n3, this.Lr, n2, n4);
        this.BI += n4;
    }

    public short get(int n2) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        return this.Lr[n2];
    }

    public short cg(int n2) {
        return this.Lr[n2];
    }

    public void b(int n2, short s) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        this.Lr[n2] = s;
    }

    public short c(int n2, short s) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        short s2 = this.Lr[n2];
        this.Lr[n2] = s;
        return s2;
    }

    public void b(int n2, short[] sArray) {
        this.c(n2, sArray, 0, sArray.length);
    }

    public void c(int n2, short[] sArray, int n3, int n4) {
        if (n2 < 0 || n2 + n4 > this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(sArray, n3, this.Lr, n2, n4);
    }

    public void d(int n2, short s) {
        this.Lr[n2] = s;
    }

    public void clear() {
        this.clear(10);
    }

    public void clear(int n2) {
        this.Lr = new short[n2];
        this.BI = 0;
    }

    public void reset() {
        this.BI = 0;
        this.F((short)0);
    }

    public void nl() {
        this.BI = 0;
    }

    public short ch(int n2) {
        short s = this.get(n2);
        this.remove(n2, 1);
        return s;
    }

    public void remove(int n2, int n3) {
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        if (n2 == 0) {
            System.arraycopy(this.Lr, n3, this.Lr, 0, this.BI - n3);
        } else if (this.BI - n3 != n2) {
            System.arraycopy(this.Lr, n2 + n3, this.Lr, n2, this.BI - (n2 + n3));
        }
        this.BI -= n3;
    }

    public void a(apk_1 apk_12) {
        int n2 = this.BI;
        while (n2-- > 0) {
            this.Lr[n2] = apk_12.bR(this.Lr[n2]);
        }
    }

    public void reverse() {
        this.y(0, this.BI);
    }

    public void y(int n2, int n3) {
        if (n2 == n3) {
            return;
        }
        if (n2 > n3) {
            throw new IllegalArgumentException("from cannot be greater than to");
        }
        int n4 = n2;
        for (int j = n3 - 1; n4 < j; ++n4, --j) {
            this.z(n4, j);
        }
    }

    public void a(Random random) {
        int n2 = this.BI;
        while (n2-- > 1) {
            this.z(n2, random.nextInt(n2));
        }
    }

    private final void z(int n2, int n3) {
        short s = this.Lr[n2];
        this.Lr[n2] = this.Lr[n3];
        this.Lr[n3] = s;
    }

    public Object clone() {
        mm_0 mm_02 = null;
        try {
            mm_02 = (mm_0)super.clone();
            mm_02.Lr = this.ru();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return mm_02;
    }

    public mm_0 J(int n2, int n3) {
        if (n3 < n2) {
            throw new IllegalArgumentException("end index " + n3 + " greater than begin index " + n2);
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        if (n3 > this.Lr.length) {
            throw new IndexOutOfBoundsException("end index < " + this.Lr.length);
        }
        mm_0 mm_02 = new mm_0(n3 - n2);
        for (int j = n2; j < n3; ++j) {
            mm_02.add(this.Lr[j]);
        }
        return mm_02;
    }

    public short[] ru() {
        return this.K(0, this.BI);
    }

    public short[] K(int n2, int n3) {
        short[] sArray = new short[n3];
        this.b(sArray, n2, n3);
        return sArray;
    }

    public void b(short[] sArray, int n2, int n3) {
        if (n3 == 0) {
            return;
        }
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(this.Lr, n2, sArray, 0, n3);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof mm_0) {
            mm_0 mm_02 = (mm_0)object;
            if (mm_02.size() != this.size()) {
                return false;
            }
            int n2 = this.BI;
            while (n2-- > 0) {
                if (this.Lr[n2] == mm_02.Lr[n2]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        int n2 = 0;
        int n3 = this.BI;
        while (n3-- > 0) {
            n2 += ha_0.aQ(this.Lr[n3]);
        }
        return n2;
    }

    public boolean a(cj_1 cj_12) {
        for (int j = 0; j < this.BI; ++j) {
            if (cj_12.aq(this.Lr[j])) continue;
            return false;
        }
        return true;
    }

    public boolean b(cj_1 cj_12) {
        int n2 = this.BI;
        while (n2-- > 0) {
            if (cj_12.aq(this.Lr[n2])) continue;
            return false;
        }
        return true;
    }

    public void sort() {
        Arrays.sort(this.Lr, 0, this.BI);
    }

    public void C(int n2, int n3) {
        Arrays.sort(this.Lr, n2, n3);
    }

    public void F(short s) {
        Arrays.fill(this.Lr, 0, this.BI, s);
    }

    public void i(int n2, int n3, short s) {
        if (n3 > this.BI) {
            this.ensureCapacity(n3);
            this.BI = n3;
        }
        Arrays.fill(this.Lr, n2, n3, s);
    }

    public int G(short s) {
        return this.a(s, 0, this.BI);
    }

    public int a(short s, int n2, int n3) {
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        if (n3 > this.BI) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        int n4 = n2;
        int n5 = n3 - 1;
        while (n4 <= n5) {
            int n6 = n4 + n5 >>> 1;
            short s2 = this.Lr[n6];
            if (s2 < s) {
                n4 = n6 + 1;
                continue;
            }
            if (s2 > s) {
                n5 = n6 - 1;
                continue;
            }
            return n6;
        }
        return -(n4 + 1);
    }

    public int H(short s) {
        return this.e(0, s);
    }

    public int e(int n2, short s) {
        for (int j = n2; j < this.BI; ++j) {
            if (this.Lr[j] != s) continue;
            return j;
        }
        return -1;
    }

    public int I(short s) {
        return this.f(this.BI, s);
    }

    public int f(int n2, short s) {
        int n3 = n2;
        while (n3-- > 0) {
            if (this.Lr[n3] != s) continue;
            return n3;
        }
        return -1;
    }

    public boolean contains(short s) {
        return this.I(s) >= 0;
    }

    public mm_0 c(cj_1 cj_12) {
        mm_0 mm_02 = new mm_0();
        for (int j = 0; j < this.BI; ++j) {
            if (!cj_12.aq(this.Lr[j])) continue;
            mm_02.add(this.Lr[j]);
        }
        return mm_02;
    }

    public mm_0 d(cj_1 cj_12) {
        mm_0 mm_02 = new mm_0();
        for (int j = 0; j < this.BI; ++j) {
            if (cj_12.aq(this.Lr[j])) continue;
            mm_02.add(this.Lr[j]);
        }
        return mm_02;
    }

    public short rv() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        short s = Short.MIN_VALUE;
        for (int j = 0; j < this.BI; ++j) {
            if (this.Lr[j] <= s) continue;
            s = this.Lr[j];
        }
        return s;
    }

    public short rw() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        short s = Short.MAX_VALUE;
        for (int j = 0; j < this.BI; ++j) {
            if (this.Lr[j] >= s) continue;
            s = this.Lr[j];
        }
        return s;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        int n2 = this.BI - 1;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(this.Lr[j]);
            stringBuffer.append(", ");
        }
        if (this.size() > 0) {
            stringBuffer.append(this.Lr[this.BI - 1]);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this.BI);
        int n2 = this.Lr.length;
        objectOutput.writeInt(n2);
        for (int j = 0; j < n2; ++j) {
            objectOutput.writeShort(this.Lr[j]);
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        this.BI = objectInput.readInt();
        int n2 = objectInput.readInt();
        this.Lr = new short[n2];
        for (int j = 0; j < n2; ++j) {
            this.Lr[j] = objectInput.readShort();
        }
    }
}

