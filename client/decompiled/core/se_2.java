/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

/*
 * Renamed from sE
 */
public class se_2
extends vi_2
implements Externalizable {
    static final long serialVersionUID = 1L;

    public se_2() {
    }

    public se_2(int n2) {
        super(n2);
    }

    public se_2(int n2, float f) {
        super(n2, f);
    }

    public se_2(long[] lArray) {
        this(lArray.length);
        this.d(lArray);
    }

    public se_2(ajd_1 ajd_12) {
        super(ajd_12);
    }

    public se_2(int n2, ajd_1 ajd_12) {
        super(n2, ajd_12);
    }

    public se_2(int n2, float f, ajd_1 ajd_12) {
        super(n2, f, ajd_12);
    }

    public se_2(long[] lArray, ajd_1 ajd_12) {
        this(lArray.length, ajd_12);
        this.d(lArray);
    }

    public sg_0 yz() {
        return new sg_0(this);
    }

    public boolean add(long l2) {
        int n2 = this.aO(l2);
        if (n2 < 0) {
            return false;
        }
        byte by = this.bCp[n2];
        this.aty[n2] = l2;
        this.bCp[n2] = 1;
        this.Z(by == 0);
        return true;
    }

    protected void rehash(int n2) {
        int n3 = this.aty.length;
        long[] lArray = this.aty;
        byte[] byArray = this.bCp;
        this.aty = new long[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            long l2 = lArray[n4];
            int n5 = this.aO(l2);
            this.aty[n5] = l2;
            this.bCp[n5] = 1;
        }
    }

    public long[] toArray() {
        long[] lArray = new long[this.size()];
        long[] lArray2 = this.aty;
        byte[] byArray = this.bCp;
        int n2 = byArray.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            lArray[n3++] = lArray2[n2];
        }
        return lArray;
    }

    public void clear() {
        super.clear();
        long[] lArray = this.aty;
        byte[] byArray = this.bCp;
        int n2 = lArray.length;
        while (n2-- > 0) {
            lArray[n2] = 0L;
            byArray[n2] = 0;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof se_2)) {
            return false;
        }
        se_2 se_22 = (se_2)object;
        if (se_22.size() != this.size()) {
            return false;
        }
        return this.b(new tH(this, se_22));
    }

    public int hashCode() {
        apo_0 apo_02 = new apo_0(this, null);
        this.b(apo_02);
        return apo_02.dY();
    }

    public boolean aI(long l2) {
        int n2 = this.az(l2);
        if (n2 >= 0) {
            this.O(n2);
            return true;
        }
        return false;
    }

    public boolean c(long[] lArray) {
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (this.m(lArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean d(long[] lArray) {
        boolean bl2 = false;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (!this.add(lArray[n2])) continue;
            bl2 = true;
        }
        return bl2;
    }

    public boolean e(long[] lArray) {
        boolean bl2 = false;
        int n2 = lArray.length;
        while (n2-- > 0) {
            if (!this.aI(lArray[n2])) continue;
            bl2 = true;
        }
        return bl2;
    }

    public boolean f(long[] lArray) {
        boolean bl2 = false;
        Arrays.sort(lArray);
        long[] lArray2 = this.aty;
        byte[] byArray = this.bCp;
        int n2 = lArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || Arrays.binarySearch(lArray, lArray2[n2]) >= 0) continue;
            this.aI(lArray2[n2]);
            bl2 = true;
        }
        return bl2;
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this._size);
        atr atr2 = new atr(objectOutput);
        if (!this.b(atr2)) {
            throw atr2.cTR;
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        int n2 = objectInput.readInt();
        this.N(n2);
        while (n2-- > 0) {
            long l2 = objectInput.readLong();
            this.add(l2);
        }
    }
}

