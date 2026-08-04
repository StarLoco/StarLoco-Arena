/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Random;

/*
 * Renamed from QA
 */
public class qa_2
implements Externalizable,
Cloneable {
    static final long serialVersionUID = 1L;
    protected long[] bGK;
    protected int BI;
    protected static final int DEFAULT_CAPACITY = 10;

    public qa_2() {
        this(10);
    }

    public qa_2(int n2) {
        this.bGK = new long[n2];
        this.BI = 0;
    }

    public qa_2(long[] lArray) {
        this(Math.max(lArray.length, 10));
        this.i(lArray);
    }

    public void ensureCapacity(int n2) {
        if (n2 > this.bGK.length) {
            int n3 = Math.max(this.bGK.length << 1, n2);
            long[] lArray = new long[n3];
            System.arraycopy(this.bGK, 0, lArray, 0, this.bGK.length);
            this.bGK = lArray;
        }
    }

    public int size() {
        return this.BI;
    }

    public boolean isEmpty() {
        return this.BI == 0;
    }

    public void trimToSize() {
        if (this.bGK.length > this.size()) {
            long[] lArray = new long[this.size()];
            this.b(lArray, 0, lArray.length);
            this.bGK = lArray;
        }
    }

    public void ct(long l2) {
        this.ensureCapacity(this.BI + 1);
        this.bGK[this.BI++] = l2;
    }

    public void i(long[] lArray) {
        this.a(lArray, 0, lArray.length);
    }

    public void a(long[] lArray, int n2, int n3) {
        this.ensureCapacity(this.BI + n3);
        System.arraycopy(lArray, n2, this.bGK, this.BI, n3);
        this.BI += n3;
    }

    public void d(int n2, long l2) {
        if (n2 == this.BI) {
            this.ct(l2);
            return;
        }
        this.ensureCapacity(this.BI + 1);
        System.arraycopy(this.bGK, n2, this.bGK, n2 + 1, this.BI - n2);
        this.bGK[n2] = l2;
        ++this.BI;
    }

    public void a(int n2, long[] lArray) {
        this.b(n2, lArray, 0, lArray.length);
    }

    public void b(int n2, long[] lArray, int n3, int n4) {
        if (n2 == this.BI) {
            this.a(lArray, n3, n4);
            return;
        }
        this.ensureCapacity(this.BI + n4);
        System.arraycopy(this.bGK, n2, this.bGK, n2 + n4, this.BI - n2);
        System.arraycopy(lArray, n3, this.bGK, n2, n4);
        this.BI += n4;
    }

    public long get(int n2) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        return this.bGK[n2];
    }

    public long hn(int n2) {
        return this.bGK[n2];
    }

    public void set(int n2, long l2) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        this.bGK[n2] = l2;
    }

    public long e(int n2, long l2) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        long l3 = this.bGK[n2];
        this.bGK[n2] = l2;
        return l3;
    }

    public void b(int n2, long[] lArray) {
        this.c(n2, lArray, 0, lArray.length);
    }

    public void c(int n2, long[] lArray, int n3, int n4) {
        if (n2 < 0 || n2 + n4 > this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(lArray, n3, this.bGK, n2, n4);
    }

    public void f(int n2, long l2) {
        this.bGK[n2] = l2;
    }

    public void clear() {
        this.clear(10);
    }

    public void clear(int n2) {
        this.bGK = new long[n2];
        this.BI = 0;
    }

    public void reset() {
        this.BI = 0;
        this.cu(0L);
    }

    public void nl() {
        this.BI = 0;
    }

    public long remove(int n2) {
        long l2 = this.get(n2);
        this.remove(n2, 1);
        return l2;
    }

    public void remove(int n2, int n3) {
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        if (n2 == 0) {
            System.arraycopy(this.bGK, n3, this.bGK, 0, this.BI - n3);
        } else if (this.BI - n3 != n2) {
            System.arraycopy(this.bGK, n2 + n3, this.bGK, n2, this.BI - (n2 + n3));
        }
        this.BI -= n3;
    }

    public void a(aaj_1 aaj_12) {
        int n2 = this.BI;
        while (n2-- > 0) {
            this.bGK[n2] = aaj_12.em(this.bGK[n2]);
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
        long l2 = this.bGK[n2];
        this.bGK[n2] = this.bGK[n3];
        this.bGK[n3] = l2;
    }

    public Object clone() {
        qa_2 qa_22 = null;
        try {
            qa_22 = (qa_2)super.clone();
            qa_22.bGK = this.adg();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return qa_22;
    }

    public qa_2 aI(int n2, int n3) {
        if (n3 < n2) {
            throw new IllegalArgumentException("end index " + n3 + " greater than begin index " + n2);
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        if (n3 > this.bGK.length) {
            throw new IndexOutOfBoundsException("end index < " + this.bGK.length);
        }
        qa_2 qa_22 = new qa_2(n3 - n2);
        for (int j = n2; j < n3; ++j) {
            qa_22.ct(this.bGK[j]);
        }
        return qa_22;
    }

    public long[] adg() {
        return this.aJ(0, this.BI);
    }

    public long[] aJ(int n2, int n3) {
        long[] lArray = new long[n3];
        this.b(lArray, n2, n3);
        return lArray;
    }

    public void b(long[] lArray, int n2, int n3) {
        if (n3 == 0) {
            return;
        }
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(this.bGK, n2, lArray, 0, n3);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof qa_2) {
            qa_2 qa_22 = (qa_2)object;
            if (qa_22.size() != this.size()) {
                return false;
            }
            int n2 = this.BI;
            while (n2-- > 0) {
                if (this.bGK[n2] == qa_22.bGK[n2]) continue;
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
            n2 += ha_0.S(this.bGK[n3]);
        }
        return n2;
    }

    public boolean b(px_1 px_12) {
        for (int j = 0; j < this.BI; ++j) {
            if (px_12.aM(this.bGK[j])) continue;
            return false;
        }
        return true;
    }

    public boolean c(px_1 px_12) {
        int n2 = this.BI;
        while (n2-- > 0) {
            if (px_12.aM(this.bGK[n2])) continue;
            return false;
        }
        return true;
    }

    public void sort() {
        Arrays.sort(this.bGK, 0, this.BI);
    }

    public void C(int n2, int n3) {
        Arrays.sort(this.bGK, n2, n3);
    }

    public void cu(long l2) {
        Arrays.fill(this.bGK, 0, this.BI, l2);
    }

    public void b(int n2, int n3, long l2) {
        if (n3 > this.BI) {
            this.ensureCapacity(n3);
            this.BI = n3;
        }
        Arrays.fill(this.bGK, n2, n3, l2);
    }

    public int cv(long l2) {
        return this.b(l2, 0, this.BI);
    }

    public int b(long l2, int n2, int n3) {
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
            long l3 = this.bGK[n6];
            if (l3 < l2) {
                n4 = n6 + 1;
                continue;
            }
            if (l3 > l2) {
                n5 = n6 - 1;
                continue;
            }
            return n6;
        }
        return -(n4 + 1);
    }

    public int cw(long l2) {
        return this.g(0, l2);
    }

    public int g(int n2, long l2) {
        for (int j = n2; j < this.BI; ++j) {
            if (this.bGK[j] != l2) continue;
            return j;
        }
        return -1;
    }

    public int cx(long l2) {
        return this.h(this.BI, l2);
    }

    public int h(int n2, long l2) {
        int n3 = n2;
        while (n3-- > 0) {
            if (this.bGK[n3] != l2) continue;
            return n3;
        }
        return -1;
    }

    public boolean m(long l2) {
        return this.cx(l2) >= 0;
    }

    public qa_2 d(px_1 px_12) {
        qa_2 qa_22 = new qa_2();
        for (int j = 0; j < this.BI; ++j) {
            if (!px_12.aM(this.bGK[j])) continue;
            qa_22.ct(this.bGK[j]);
        }
        return qa_22;
    }

    public qa_2 e(px_1 px_12) {
        qa_2 qa_22 = new qa_2();
        for (int j = 0; j < this.BI; ++j) {
            if (px_12.aM(this.bGK[j])) continue;
            qa_22.ct(this.bGK[j]);
        }
        return qa_22;
    }

    public long adh() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        long l2 = Long.MIN_VALUE;
        for (int j = 0; j < this.BI; ++j) {
            if (this.bGK[j] <= l2) continue;
            l2 = this.bGK[j];
        }
        return l2;
    }

    public long adi() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        long l2 = Long.MAX_VALUE;
        for (int j = 0; j < this.BI; ++j) {
            if (this.bGK[j] >= l2) continue;
            l2 = this.bGK[j];
        }
        return l2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        int n2 = this.BI - 1;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(this.bGK[j]);
            stringBuffer.append(", ");
        }
        if (this.size() > 0) {
            stringBuffer.append(this.bGK[this.BI - 1]);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this.BI);
        int n2 = this.bGK.length;
        objectOutput.writeInt(n2);
        for (int j = 0; j < n2; ++j) {
            objectOutput.writeLong(this.bGK[j]);
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        this.BI = objectInput.readInt();
        int n2 = objectInput.readInt();
        this.bGK = new long[n2];
        for (int j = 0; j < n2; ++j) {
            this.bGK[j] = objectInput.readLong();
        }
    }
}

