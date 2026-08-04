/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Random;

/*
 * Renamed from pS
 */
public class ps_0
implements Externalizable,
Cloneable {
    static final long serialVersionUID = 1L;
    protected float[] acL;
    protected int BI;
    protected static final int DEFAULT_CAPACITY = 10;

    public ps_0() {
        this(10);
    }

    public ps_0(int n2) {
        this.acL = new float[n2];
        this.BI = 0;
    }

    public ps_0(float[] fArray) {
        this(Math.max(fArray.length, 10));
        this.b(fArray);
    }

    public void ensureCapacity(int n2) {
        if (n2 > this.acL.length) {
            int n3 = Math.max(this.acL.length << 1, n2);
            float[] fArray = new float[n3];
            System.arraycopy(this.acL, 0, fArray, 0, this.acL.length);
            this.acL = fArray;
        }
    }

    public int size() {
        return this.BI;
    }

    public boolean isEmpty() {
        return this.BI == 0;
    }

    public void trimToSize() {
        if (this.acL.length > this.size()) {
            float[] fArray = new float[this.size()];
            this.c(fArray, 0, fArray.length);
            this.acL = fArray;
        }
    }

    public void add(float f) {
        this.ensureCapacity(this.BI + 1);
        this.acL[this.BI++] = f;
    }

    public void b(float[] fArray) {
        this.b(fArray, 0, fArray.length);
    }

    public void b(float[] fArray, int n2, int n3) {
        this.ensureCapacity(this.BI + n3);
        System.arraycopy(fArray, n2, this.acL, this.BI, n3);
        this.BI += n3;
    }

    public void c(int n2, float f) {
        if (n2 == this.BI) {
            this.add(f);
            return;
        }
        this.ensureCapacity(this.BI + 1);
        System.arraycopy(this.acL, n2, this.acL, n2 + 1, this.BI - n2);
        this.acL[n2] = f;
        ++this.BI;
    }

    public void d(int n2, float[] fArray) {
        this.b(n2, fArray, 0, fArray.length);
    }

    public void b(int n2, float[] fArray, int n3, int n4) {
        if (n2 == this.BI) {
            this.b(fArray, n3, n4);
            return;
        }
        this.ensureCapacity(this.BI + n4);
        System.arraycopy(this.acL, n2, this.acL, n2 + n4, this.BI - n2);
        System.arraycopy(fArray, n3, this.acL, n2, n4);
        this.BI += n4;
    }

    public float get(int n2) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        return this.acL[n2];
    }

    public float cG(int n2) {
        return this.acL[n2];
    }

    public void d(int n2, float f) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        this.acL[n2] = f;
    }

    public float e(int n2, float f) {
        if (n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        float f2 = this.acL[n2];
        this.acL[n2] = f;
        return f2;
    }

    public void e(int n2, float[] fArray) {
        this.c(n2, fArray, 0, fArray.length);
    }

    public void c(int n2, float[] fArray, int n3, int n4) {
        if (n2 < 0 || n2 + n4 > this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(fArray, n3, this.acL, n2, n4);
    }

    public void f(int n2, float f) {
        this.acL[n2] = f;
    }

    public void clear() {
        this.clear(10);
    }

    public void clear(int n2) {
        this.acL = new float[n2];
        this.BI = 0;
    }

    public void reset() {
        this.BI = 0;
        this.B(0.0f);
    }

    public void nl() {
        this.BI = 0;
    }

    public float cH(int n2) {
        float f = this.get(n2);
        this.remove(n2, 1);
        return f;
    }

    public void remove(int n2, int n3) {
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        if (n2 == 0) {
            System.arraycopy(this.acL, n3, this.acL, 0, this.BI - n3);
        } else if (this.BI - n3 != n2) {
            System.arraycopy(this.acL, n2 + n3, this.acL, n2, this.BI - (n2 + n3));
        }
        this.BI -= n3;
    }

    public void a(PZ pZ) {
        int n2 = this.BI;
        while (n2-- > 0) {
            this.acL[n2] = pZ.an(this.acL[n2]);
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
        float f = this.acL[n2];
        this.acL[n2] = this.acL[n3];
        this.acL[n3] = f;
    }

    public Object clone() {
        ps_0 ps_02 = null;
        try {
            ps_02 = (ps_0)super.clone();
            ps_02.acL = this.uD();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return ps_02;
    }

    public ps_0 M(int n2, int n3) {
        if (n3 < n2) {
            throw new IllegalArgumentException("end index " + n3 + " greater than begin index " + n2);
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("begin index can not be < 0");
        }
        if (n3 > this.acL.length) {
            throw new IndexOutOfBoundsException("end index < " + this.acL.length);
        }
        ps_0 ps_02 = new ps_0(n3 - n2);
        for (int j = n2; j < n3; ++j) {
            ps_02.add(this.acL[j]);
        }
        return ps_02;
    }

    public float[] uD() {
        return this.N(0, this.BI);
    }

    public float[] N(int n2, int n3) {
        float[] fArray = new float[n3];
        this.c(fArray, n2, n3);
        return fArray;
    }

    public void c(float[] fArray, int n2, int n3) {
        if (n3 == 0) {
            return;
        }
        if (n2 < 0 || n2 >= this.BI) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        System.arraycopy(this.acL, n2, fArray, 0, n3);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof ps_0) {
            ps_0 ps_02 = (ps_0)object;
            if (ps_02.size() != this.size()) {
                return false;
            }
            int n2 = this.BI;
            while (n2-- > 0) {
                if (this.acL[n2] == ps_02.acL[n2]) continue;
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
            n2 += ha_0.s(this.acL[n3]);
        }
        return n2;
    }

    public boolean a(lw_2 lw_22) {
        for (int j = 0; j < this.BI; ++j) {
            if (lw_22.ag(this.acL[j])) continue;
            return false;
        }
        return true;
    }

    public boolean b(lw_2 lw_22) {
        int n2 = this.BI;
        while (n2-- > 0) {
            if (lw_22.ag(this.acL[n2])) continue;
            return false;
        }
        return true;
    }

    public void sort() {
        Arrays.sort(this.acL, 0, this.BI);
    }

    public void C(int n2, int n3) {
        Arrays.sort(this.acL, n2, n3);
    }

    public void B(float f) {
        Arrays.fill(this.acL, 0, this.BI, f);
    }

    public void c(int n2, int n3, float f) {
        if (n3 > this.BI) {
            this.ensureCapacity(n3);
            this.BI = n3;
        }
        Arrays.fill(this.acL, n2, n3, f);
    }

    public int C(float f) {
        return this.a(f, 0, this.BI);
    }

    public int a(float f, int n2, int n3) {
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
            float f2 = this.acL[n6];
            if (f2 < f) {
                n4 = n6 + 1;
                continue;
            }
            if (f2 > f) {
                n5 = n6 - 1;
                continue;
            }
            return n6;
        }
        return -(n4 + 1);
    }

    public int D(float f) {
        return this.g(0, f);
    }

    public int g(int n2, float f) {
        for (int j = n2; j < this.BI; ++j) {
            if (this.acL[j] != f) continue;
            return j;
        }
        return -1;
    }

    public int E(float f) {
        return this.h(this.BI, f);
    }

    public int h(int n2, float f) {
        int n3 = n2;
        while (n3-- > 0) {
            if (this.acL[n3] != f) continue;
            return n3;
        }
        return -1;
    }

    public boolean F(float f) {
        return this.E(f) >= 0;
    }

    public ps_0 c(lw_2 lw_22) {
        ps_0 ps_02 = new ps_0();
        for (int j = 0; j < this.BI; ++j) {
            if (!lw_22.ag(this.acL[j])) continue;
            ps_02.add(this.acL[j]);
        }
        return ps_02;
    }

    public ps_0 d(lw_2 lw_22) {
        ps_0 ps_02 = new ps_0();
        for (int j = 0; j < this.BI; ++j) {
            if (lw_22.ag(this.acL[j])) continue;
            ps_02.add(this.acL[j]);
        }
        return ps_02;
    }

    public float uE() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        float f = Float.NEGATIVE_INFINITY;
        for (int j = 0; j < this.BI; ++j) {
            if (!(this.acL[j] > f)) continue;
            f = this.acL[j];
        }
        return f;
    }

    public float uF() {
        if (this.size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        float f = Float.POSITIVE_INFINITY;
        for (int j = 0; j < this.BI; ++j) {
            if (!(this.acL[j] < f)) continue;
            f = this.acL[j];
        }
        return f;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        int n2 = this.BI - 1;
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append(this.acL[j]);
            stringBuffer.append(", ");
        }
        if (this.size() > 0) {
            stringBuffer.append(this.acL[this.BI - 1]);
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this.BI);
        int n2 = this.acL.length;
        objectOutput.writeInt(n2);
        for (int j = 0; j < n2; ++j) {
            objectOutput.writeFloat(this.acL[j]);
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        this.BI = objectInput.readInt();
        int n2 = objectInput.readInt();
        this.acL = new float[n2];
        for (int j = 0; j < n2; ++j) {
            this.acL[j] = objectInput.readFloat();
        }
    }
}

