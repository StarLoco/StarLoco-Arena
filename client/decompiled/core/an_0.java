/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

/*
 * Renamed from an
 */
public class an_0
extends us
implements Externalizable {
    static final long serialVersionUID = 1L;

    public an_0() {
    }

    public an_0(int n2) {
        super(n2);
    }

    public an_0(int n2, float f) {
        super(n2, f);
    }

    public an_0(short[] sArray) {
        this(sArray.length);
        this.b(sArray);
    }

    public an_0(Nh nh) {
        super(nh);
    }

    public an_0(int n2, Nh nh) {
        super(n2, nh);
    }

    public an_0(int n2, float f, Nh nh) {
        super(n2, f, nh);
    }

    public an_0(short[] sArray, Nh nh) {
        this(sArray.length, nh);
        this.b(sArray);
    }

    public gq_0 aS() {
        return new gq_0(this);
    }

    public boolean a(short s) {
        int n2 = this.ac(s);
        if (n2 < 0) {
            return false;
        }
        byte by = this.bCp[n2];
        this.aqv[n2] = s;
        this.bCp[n2] = 1;
        this.Z(by == 0);
        return true;
    }

    protected void rehash(int n2) {
        int n3 = this.aqv.length;
        short[] sArray = this.aqv;
        byte[] byArray = this.bCp;
        this.aqv = new short[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            short s = sArray[n4];
            int n5 = this.ac(s);
            this.aqv[n5] = s;
            this.bCp[n5] = 1;
        }
    }

    public short[] aT() {
        short[] sArray = new short[this.size()];
        short[] sArray2 = this.aqv;
        byte[] byArray = this.bCp;
        int n2 = byArray.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            sArray[n3++] = sArray2[n2];
        }
        return sArray;
    }

    public void clear() {
        super.clear();
        short[] sArray = this.aqv;
        byte[] byArray = this.bCp;
        int n2 = sArray.length;
        while (n2-- > 0) {
            sArray[n2] = 0;
            byArray[n2] = 0;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof an_0)) {
            return false;
        }
        an_0 an_02 = (an_0)object;
        if (an_02.size() != this.size()) {
            return false;
        }
        return this.a(new zz_2(this, an_02));
    }

    public int hashCode() {
        XW xW = new XW(this, null);
        this.a(xW);
        return xW.dY();
    }

    public boolean b(short s) {
        int n2 = this.ab(s);
        if (n2 >= 0) {
            this.O(n2);
            return true;
        }
        return false;
    }

    public boolean a(short[] sArray) {
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (this.contains(sArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean b(short[] sArray) {
        boolean bl2 = false;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (!this.a(sArray[n2])) continue;
            bl2 = true;
        }
        return bl2;
    }

    public boolean c(short[] sArray) {
        boolean bl2 = false;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (!this.b(sArray[n2])) continue;
            bl2 = true;
        }
        return bl2;
    }

    public boolean d(short[] sArray) {
        boolean bl2 = false;
        Arrays.sort(sArray);
        short[] sArray2 = this.aqv;
        byte[] byArray = this.bCp;
        int n2 = sArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || Arrays.binarySearch(sArray, sArray2[n2]) >= 0) continue;
            this.b(sArray2[n2]);
            bl2 = true;
        }
        return bl2;
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this._size);
        atr atr2 = new atr(objectOutput);
        if (!this.a(atr2)) {
            throw atr2.cTR;
        }
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        int n2 = objectInput.readInt();
        this.N(n2);
        while (n2-- > 0) {
            short s = objectInput.readShort();
            this.a(s);
        }
    }
}

