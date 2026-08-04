/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Random;

/*
 * Renamed from jG
 */
public class jg_0
implements Externalizable,
Cloneable {
    static final long serialVersionUID = 1L;
    protected int[] BH;
    protected int BI;
    protected static final int DEFAULT_CAPACITY = 10;

    public jg_0() {
        this(10);
    }

    public jg_0(int n2) {
        this.BH = new int[n2];
        this.BI = 0;
    }

    public jg_0(int[] nArray) {
        this(Math.max(nArray.length, 10));
        this.d(nArray);
    }

    public void ensureCapacity(int n2) {
        if (n2 > this.BH.length) {
            int n3 = Math.max(this.BH.length << 1, n2);
            int[] nArray = new int[n3];
            System.arraycopy(this.BH, 0, nArray, 0, this.BH.length);
            this.BH = nArray;
        }
    }

    public int size() {
        return this.BI;
    }

    public boolean isEmpty() {
        return this.BI == 0;
    }

    public void trimToSize() {
        if (this.BH.length > this.size()) {
            int[] nArray = new int[this.size()];
            this.b(nArray, 0, nArray.length);
            this.BH = nArray;
        }
    }

    public void add(int n2) {
        this.ensureCapacity(this.BI + 1);
        this.BH[this.BI++] = n2;
    }

    public void d(int[] nArray) {
        this.a(nArray, 0, nArray.length);
    }

    public void a(int[] nArray, int n2, int n3) {
        this.ensureCapacity(this.BI + n3);
        System.arraycopy(nArray, n2, this.BH, this.BI, n3);
        this.BI += n3;
    }

    public void v(int n2, int n3) {
        if (n2 == this.BI) {
            this.add(n3);
            return;
        }
        this.ensureCapacity(this.BI + 1);
        System.arraycopy(this.BH, n2, this.BH, n2 + 1, this.BI - n2);
        this.BH[n2] = n3;
        ++this.BI;
    }

    public void a(int n2, int[] nArray) {
        this.b(n2, nArray, 0, nArray.length);
    }

    public void b(int n2, int[] nArray, int n3, int n4) {
        if (n2 == this.BI) {
            this.a(nArray, n3, n4);
            return;
        }
        this.ensureCapacity(this.BI + n4);
        System.arraycopy(this.BH, n2, this.BH, n2 + n4, this.BI - n2);
        System.arraycopy(nArray, n3, this.BH, n2, n4);
        this.BI += n4;
    }

    public int get(int n2) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        return this.BH[n2];
    }

    public int bu(int n2) {
        return this.BH[n2];
    }

    public void set(int n2, int n3) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        this.BH[n2] = n3;
    }

    public int w(int n2, int n3) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        int n4 = this.BH[n2];
        this.BH[n2] = n3;
        return n4;
    }

    public void b(int n2, int[] nArray) {
        this.c(n2, nArray, 0, nArray.length);
    }

    public void c(int n2, int[] nArray, int n3, int n4) {
        if (n2 < 0 || n2 + n4 > this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(nArray, n3, this.BH, n2, n4);
    }

    public void x(int n2, int n3) {
        this.BH[n2] = n3;
    }

    public void clear() {
        this.clear(10);
    }

    public void clear(int n2) {
        this.BH = new int[n2];
        this.BI = 0;
    }

    public void reset() {
        this.BI = 0;
        this.bw(0);
    }

    public void nl() {
        this.BI = 0;
    }

    public int bv(int n2) {
        int n3 = this.get(n2);
        this.remove(n2, 1);
        return n3;
    }

    public void remove(int n2, int n3) {
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        if (n2 == 0) {
            System.arraycopy(this.BH, n3, this.BH, 0, this.BI - n3);
        } else if (this.BI - n3 != n2) {
            System.arraycopy(this.BH, n2 + n3, this.BH, n2, this.BI - (n2 + n3));
        }
        this.BI -= n3;
    }

    public void a(aMV aMV2) {
        int n2 = this.BI;
        while (n2-- > 0) {
            this.BH[n2] = aMV2.pu(this.BH[n2]);
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
        int n4 = this.BH[n2];
        this.BH[n2] = this.BH[n3];
        this.BH[n3] = n4;
    }

    public Object clone() {
        jg_0 jg_02 = null;
        try {
            jg_02 = (jg_0)super.clone();
            jg_02.BH = this.nm();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return jg_02;
    }

    public jg_0 A(int n2, int n3) {
        if (n3 < n2) {
            throw new IllegalArgumentException("end index " + n3 + " greater than begin index " + n2);
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        if (n3 > this.BH.length) {
            throw new IndexOutOfBoundsException("end index < " + this.BH.length);
        }
        jg_0 jg_02 = new jg_0(n3 - n2);
        for (int j = n2; j < n3; ++j) {
            jg_02.add(this.BH[j]);
        }
        return jg_02;
    }

    public int[] nm() {
        return this.B(0, this.BI);
    }

    public int[] B(int n2, int n3) {
        int[] nArray = new int[n3];
        this.b(nArray, n2, n3);
        return nArray;
    }

    public void b(int[] nArray, int n2, int n3) {
        if (n3 == 0) {
            return;
        }
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(this.BH, n2, nArray, 0, n3);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof jg_0) {
            jg_0 jg_02 = (jg_0)object;
            if (jg_02.size() != this.size()) {
                return false;
            }
            int n2 = this.BI;
            while (n2-- > 0) {
                if (this.BH[n2] == jg_02.BH[n2]) continue;
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
            n2 += ha_0.aQ(this.BH[n3]);
        }
        return n2;
    }

    public boolean a(aLR aLR2) {
        for (int j = 0; j < this.BI; ++j) {
            if (aLR2.eG(this.BH[j])) continue;
            return false;
        }
        return true;
    }

    public boolean b(aLR aLR2) {
        int n2 = this.BI;
        while (n2-- > 0) {
            if (aLR2.eG(this.BH[n2])) continue;
            return false;
        }
        return true;
    }

    public void sort() {
        Arrays.sort(this.BH, 0, this.BI);
    }

    public void C(int n2, int n3) {
        Arrays.sort(this.BH, n2, n3);
    }

    public void bw(int n2) {
        Arrays.fill(this.BH, 0, this.BI, n2);
    }

    public void g(int n2, int n3, int n4) {
        if (n3 > this.BI) {
            this.ensureCapacity(n3);
            this.BI = n3;
        }
        Arrays.fill(this.BH, n2, n3, n4);
    }

    public int bx(int n2) {
        return this.h(n2, 0, this.BI);
    }

    public int h(int n2, int n3, int n4) {
        if (n3 < 0) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        if (n4 > this.BI) {
            throw new ArrayIndexOutOfBoundsException(n4);
        }
        int n5 = n3;
        int n6 = n4 - 1;
        while (n5 <= n6) {
            int n7 = n5 + n6 >>> 1;
            int n8 = this.BH[n7];
            if (n8 < n2) {
                n5 = n7 + 1;
                continue;
            }
            if (n8 > n2) {
                n6 = n7 - 1;
                continue;
            }
            return n7;
        }
        return -(n5 + 1);
    }

    public int indexOf(int n2) {
        return this.indexOf(0, n2);
    }

    public int indexOf(int n2, int n3) {
        for (int j = n2; j < this.BI; ++j) {
            if (this.BH[j] != n3) continue;
            return j;
        }
        return -1;
    }

    public int lastIndexOf(int n2) {
        return this.lastIndexOf(this.BI, n2);
    }

    public int lastIndexOf(int n2, int n3) {
        int n4 = n2;
        while (n4-- > 0) {
            if (this.BH[n4] != n3) continue;
            return n4;
        }
        return -1;
    }

    public boolean contains(int n2) {
        return this.lastIndexOf(n2) >= 0;
    }

    public jg_0 c(aLR aLR2) {
        jg_0 jg_02 = new jg_0();
        for (int j = 0; j < this.BI; ++j) {
            if (!aLR2.eG(this.BH[j])) continue;
            jg_02.add(this.BH[j]);
        }
        return jg_02;
    }

    public jg_0 d(aLR aLR2) {
        jg_0 jg_02 = new jg_0();
        for (int j = 0; j < this.BI; ++j) {
            if (aLR2.eG(this.BH[j])) continue;
            jg_02.add(this.BH[j]);
        }
        return jg_02;
    }

    public int max() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        int n2 = Integer.MIN_VALUE;
        for (int j = 0; j < this.BI; ++j) {
            if (this.BH[j] <= n2) continue;
            n2 = this.BH[j];
        }
        return n2;
    }

    public int min() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        int n2 = Integer.MAX_VALUE;
        for (int j = 0; j < this.BI; ++j) {
            if (this.BH[j] >= n2) continue;
            n2 = this.BH[j];
        }
        return n2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        int n2 = this.BI - 1;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(this.BH[j]);
            stringBuffer.append(", ");
        }
        if (this.size() > 0) {
            stringBuffer.append(this.BH[this.BI - 1]);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this.BI);
        int n2 = this.BH.length;
        objectOutput.writeInt(n2);
        for (int j = 0; j < n2; ++j) {
            objectOutput.writeInt(this.BH[j]);
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        this.BI = objectInput.readInt();
        int n2 = objectInput.readInt();
        this.BH = new int[n2];
        for (int j = 0; j < n2; ++j) {
            this.BH[j] = objectInput.readInt();
        }
    }
}

