/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Random;

/*
 * Renamed from aKU
 */
public class aku_2
implements Externalizable,
Cloneable {
    static final long serialVersionUID = 1L;
    protected byte[] dUn;
    protected int BI;
    protected static final int DEFAULT_CAPACITY = 10;

    public aku_2() {
        this(10);
    }

    public aku_2(int n2) {
        this.dUn = new byte[n2];
        this.BI = 0;
    }

    public aku_2(byte[] byArray) {
        this(Math.max(byArray.length, 10));
        this.ae(byArray);
    }

    public void ensureCapacity(int n2) {
        if (n2 > this.dUn.length) {
            int n3 = Math.max(this.dUn.length << 1, n2);
            byte[] byArray = new byte[n3];
            System.arraycopy(this.dUn, 0, byArray, 0, this.dUn.length);
            this.dUn = byArray;
        }
    }

    public int size() {
        return this.BI;
    }

    public boolean isEmpty() {
        return this.BI == 0;
    }

    public void trimToSize() {
        if (this.dUn.length > this.size()) {
            byte[] byArray = new byte[this.size()];
            this.j(byArray, 0, byArray.length);
            this.dUn = byArray;
        }
    }

    public void add(byte by) {
        this.ensureCapacity(this.BI + 1);
        this.dUn[this.BI++] = by;
    }

    public void ae(byte[] byArray) {
        this.i(byArray, 0, byArray.length);
    }

    public void i(byte[] byArray, int n2, int n3) {
        this.ensureCapacity(this.BI + n3);
        System.arraycopy(byArray, n2, this.dUn, this.BI, n3);
        this.BI += n3;
    }

    public void f(int n2, byte by) {
        if (n2 == this.BI) {
            this.add(by);
            return;
        }
        this.ensureCapacity(this.BI + 1);
        System.arraycopy(this.dUn, n2, this.dUn, n2 + 1, this.BI - n2);
        this.dUn[n2] = by;
        ++this.BI;
    }

    public void b(int n2, byte[] byArray) {
        this.b(n2, byArray, 0, byArray.length);
    }

    public void b(int n2, byte[] byArray, int n3, int n4) {
        if (n2 == this.BI) {
            this.i(byArray, n3, n4);
            return;
        }
        this.ensureCapacity(this.BI + n4);
        System.arraycopy(this.dUn, n2, this.dUn, n2 + n4, this.BI - n2);
        System.arraycopy(byArray, n3, this.dUn, n2, n4);
        this.BI += n4;
    }

    public byte get(int n2) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        return this.dUn[n2];
    }

    public byte ph(int n2) {
        return this.dUn[n2];
    }

    public void g(int n2, byte by) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        this.dUn[n2] = by;
    }

    public byte h(int n2, byte by) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        byte by2 = this.dUn[n2];
        this.dUn[n2] = by;
        return by2;
    }

    public void c(int n2, byte[] byArray) {
        this.c(n2, byArray, 0, byArray.length);
    }

    public void c(int n2, byte[] byArray, int n3, int n4) {
        if (n2 < 0 || n2 + n4 > this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(byArray, n3, this.dUn, n2, n4);
    }

    public void i(int n2, byte by) {
        this.dUn[n2] = by;
    }

    public void clear() {
        this.clear(10);
    }

    public void clear(int n2) {
        this.dUn = new byte[n2];
        this.BI = 0;
    }

    public void reset() {
        this.BI = 0;
        this.br((byte)0);
    }

    public void nl() {
        this.BI = 0;
    }

    public byte lX(int n2) {
        byte by = this.get(n2);
        this.remove(n2, 1);
        return by;
    }

    public void remove(int n2, int n3) {
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        if (n2 == 0) {
            System.arraycopy(this.dUn, n3, this.dUn, 0, this.BI - n3);
        } else if (this.BI - n3 != n2) {
            System.arraycopy(this.dUn, n2 + n3, this.dUn, n2, this.BI - (n2 + n3));
        }
        this.BI -= n3;
    }

    public void a(aqI aqI2) {
        int n2 = this.BI;
        while (n2-- > 0) {
            this.dUn[n2] = aqI2.aQ(this.dUn[n2]);
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
        byte by = this.dUn[n2];
        this.dUn[n2] = this.dUn[n3];
        this.dUn[n3] = by;
    }

    public Object clone() {
        aku_2 aku_22 = null;
        try {
            aku_22 = (aku_2)super.clone();
            aku_22.dUn = this.aVT();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return aku_22;
    }

    public aku_2 cj(int n2, int n3) {
        if (n3 < n2) {
            throw new IllegalArgumentException("end index " + n3 + " greater than begin index " + n2);
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        if (n3 > this.dUn.length) {
            throw new IndexOutOfBoundsException("end index < " + this.dUn.length);
        }
        aku_2 aku_22 = new aku_2(n3 - n2);
        for (int j = n2; j < n3; ++j) {
            aku_22.add(this.dUn[j]);
        }
        return aku_22;
    }

    public byte[] aVT() {
        return this.ck(0, this.BI);
    }

    public byte[] ck(int n2, int n3) {
        byte[] byArray = new byte[n3];
        this.j(byArray, n2, n3);
        return byArray;
    }

    public void j(byte[] byArray, int n2, int n3) {
        if (n3 == 0) {
            return;
        }
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(this.dUn, n2, byArray, 0, n3);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof aku_2) {
            aku_2 aku_22 = (aku_2)object;
            if (aku_22.size() != this.size()) {
                return false;
            }
            int n2 = this.BI;
            while (n2-- > 0) {
                if (this.dUn[n2] == aku_22.dUn[n2]) continue;
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
            n2 += ha_0.aQ(this.dUn[n3]);
        }
        return n2;
    }

    public boolean a(amm_2 amm_22) {
        for (int j = 0; j < this.BI; ++j) {
            if (amm_22.aH(this.dUn[j])) continue;
            return false;
        }
        return true;
    }

    public boolean d(amm_2 amm_22) {
        int n2 = this.BI;
        while (n2-- > 0) {
            if (amm_22.aH(this.dUn[n2])) continue;
            return false;
        }
        return true;
    }

    public void sort() {
        Arrays.sort(this.dUn, 0, this.BI);
    }

    public void C(int n2, int n3) {
        Arrays.sort(this.dUn, n2, n3);
    }

    public void br(byte by) {
        Arrays.fill(this.dUn, 0, this.BI, by);
    }

    public void a(int n2, int n3, byte by) {
        if (n3 > this.BI) {
            this.ensureCapacity(n3);
            this.BI = n3;
        }
        Arrays.fill(this.dUn, n2, n3, by);
    }

    public int bs(byte by) {
        return this.c(by, 0, this.BI);
    }

    public int c(byte by, int n2, int n3) {
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
            byte by2 = this.dUn[n6];
            if (by2 < by) {
                n4 = n6 + 1;
                continue;
            }
            if (by2 > by) {
                n5 = n6 - 1;
                continue;
            }
            return n6;
        }
        return -(n4 + 1);
    }

    public int indexOf(byte by) {
        return this.j(0, by);
    }

    public int j(int n2, byte by) {
        for (int j = n2; j < this.BI; ++j) {
            if (this.dUn[j] != by) continue;
            return j;
        }
        return -1;
    }

    public int bt(byte by) {
        return this.k(this.BI, by);
    }

    public int k(int n2, byte by) {
        int n3 = n2;
        while (n3-- > 0) {
            if (this.dUn[n3] != by) continue;
            return n3;
        }
        return -1;
    }

    public boolean contains(byte by) {
        return this.bt(by) >= 0;
    }

    public aku_2 e(amm_2 amm_22) {
        aku_2 aku_22 = new aku_2();
        for (int j = 0; j < this.BI; ++j) {
            if (!amm_22.aH(this.dUn[j])) continue;
            aku_22.add(this.dUn[j]);
        }
        return aku_22;
    }

    public aku_2 f(amm_2 amm_22) {
        aku_2 aku_22 = new aku_2();
        for (int j = 0; j < this.BI; ++j) {
            if (amm_22.aH(this.dUn[j])) continue;
            aku_22.add(this.dUn[j]);
        }
        return aku_22;
    }

    public byte aVU() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        byte by = -128;
        for (int j = 0; j < this.BI; ++j) {
            if (this.dUn[j] <= by) continue;
            by = this.dUn[j];
        }
        return by;
    }

    public byte aVV() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        byte by = 127;
        for (int j = 0; j < this.BI; ++j) {
            if (this.dUn[j] >= by) continue;
            by = this.dUn[j];
        }
        return by;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        int n2 = this.BI - 1;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(this.dUn[j]);
            stringBuffer.append(", ");
        }
        if (this.size() > 0) {
            stringBuffer.append(this.dUn[this.BI - 1]);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this.BI);
        int n2 = this.dUn.length;
        objectOutput.writeInt(n2);
        for (int j = 0; j < n2; ++j) {
            objectOutput.writeByte(this.dUn[j]);
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        this.BI = objectInput.readInt();
        int n2 = objectInput.readInt();
        this.dUn = new byte[n2];
        for (int j = 0; j < n2; ++j) {
            this.dUn[j] = objectInput.readByte();
        }
    }
}

