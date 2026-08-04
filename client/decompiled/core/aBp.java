/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

public class aBp
extends aMP
implements Externalizable {
    static final long serialVersionUID = 1L;

    public aBp() {
    }

    public aBp(int n2) {
        super(n2);
    }

    public aBp(int n2, float f) {
        super(n2, f);
    }

    public aBp(int[] nArray) {
        this(nArray.length);
        this.F(nArray);
    }

    public aBp(ui_0 ui_02) {
        super(ui_02);
    }

    public aBp(int n2, ui_0 ui_02) {
        super(n2, ui_02);
    }

    public aBp(int n2, float f, ui_0 ui_02) {
        super(n2, f, ui_02);
    }

    public aBp(int[] nArray, ui_0 ui_02) {
        this(nArray.length, ui_02);
        this.F(nArray);
    }

    public qk aNm() {
        return new qk(this);
    }

    public boolean nk(int n2) {
        int n3 = this.pr(n2);
        if (n3 < 0) {
            return false;
        }
        byte by = this.bCp[n3];
        this.dYH[n3] = n2;
        this.bCp[n3] = 1;
        this.Z(by == 0);
        return true;
    }

    protected void rehash(int n2) {
        int n3 = this.dYH.length;
        int[] nArray = this.dYH;
        byte[] byArray = this.bCp;
        this.dYH = new int[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            int n5 = nArray[n4];
            int n6 = this.pr(n5);
            this.dYH[n6] = n5;
            this.bCp[n6] = 1;
        }
    }

    public int[] aNn() {
        int[] nArray = new int[this.size()];
        int[] nArray2 = this.dYH;
        byte[] byArray = this.bCp;
        int n2 = byArray.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n3++] = nArray2[n2];
        }
        return nArray;
    }

    public void clear() {
        super.clear();
        int[] nArray = this.dYH;
        byte[] byArray = this.bCp;
        int n2 = nArray.length;
        while (n2-- > 0) {
            nArray[n2] = 0;
            byArray[n2] = 0;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof aBp)) {
            return false;
        }
        aBp aBp2 = (aBp)object;
        if (aBp2.size() != this.size()) {
            return false;
        }
        return this.a(new AU(this, aBp2));
    }

    public int hashCode() {
        axm axm2 = new axm(this, null);
        this.a(axm2);
        return axm2.dY();
    }

    public boolean remove(int n2) {
        int n3 = this.hJ(n2);
        if (n3 >= 0) {
            this.O(n3);
            return true;
        }
        return false;
    }

    public boolean E(int[] nArray) {
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (this.contains(nArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean F(int[] nArray) {
        boolean bl2 = false;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (!this.nk(nArray[n2])) continue;
            bl2 = true;
        }
        return bl2;
    }

    public boolean G(int[] nArray) {
        boolean bl2 = false;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (!this.remove(nArray[n2])) continue;
            bl2 = true;
        }
        return bl2;
    }

    public boolean H(int[] nArray) {
        boolean bl2 = false;
        Arrays.sort(nArray);
        int[] nArray2 = this.dYH;
        byte[] byArray = this.bCp;
        int n2 = nArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || Arrays.binarySearch(nArray, nArray2[n2]) >= 0) continue;
            this.remove(nArray2[n2]);
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
            int n3 = objectInput.readInt();
            this.nk(n3);
        }
    }
}

