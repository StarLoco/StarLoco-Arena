/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class no
extends aMP
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient short[] Ol;

    public no() {
    }

    public no(int n2) {
        super(n2);
    }

    public no(int n2, float f) {
        super(n2, f);
    }

    public no(ui_0 ui_02) {
        super(ui_02);
    }

    public no(int n2, ui_0 ui_02) {
        super(n2, ui_02);
    }

    public no(int n2, float f, ui_0 ui_02) {
        super(n2, f, ui_02);
    }

    public Object clone() {
        no no2 = (no)super.clone();
        no2.Ol = (short[])this.Ol.clone();
        return no2;
    }

    public pe_0 sm() {
        return new pe_0(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.Ol = new short[n3];
        return n3;
    }

    public short g(int n2, short s) {
        short s2 = 0;
        int n3 = this.pr(n2);
        boolean bl2 = true;
        if (n3 < 0) {
            n3 = -n3 - 1;
            s2 = this.Ol[n3];
            bl2 = false;
        }
        byte by = this.bCp[n3];
        this.dYH[n3] = n2;
        this.bCp[n3] = 1;
        this.Ol[n3] = s;
        if (bl2) {
            this.Z(by == 0);
        }
        return s2;
    }

    protected void rehash(int n2) {
        int n3 = this.dYH.length;
        int[] nArray = this.dYH;
        short[] sArray = this.Ol;
        byte[] byArray = this.bCp;
        this.dYH = new int[n2];
        this.Ol = new short[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            int n5 = nArray[n4];
            int n6 = this.pr(n5);
            this.dYH[n6] = n5;
            this.Ol[n6] = sArray[n4];
            this.bCp[n6] = 1;
        }
    }

    public short get(int n2) {
        int n3 = this.hJ(n2);
        return n3 < 0 ? (short)0 : this.Ol[n3];
    }

    public void clear() {
        super.clear();
        int[] nArray = this.dYH;
        short[] sArray = this.Ol;
        byte[] byArray = this.bCp;
        int n2 = nArray.length;
        while (n2-- > 0) {
            nArray[n2] = 0;
            sArray[n2] = 0;
            byArray[n2] = 0;
        }
    }

    public short ch(int n2) {
        short s = 0;
        int n3 = this.hJ(n2);
        if (n3 >= 0) {
            s = this.Ol[n3];
            this.O(n3);
        }
        return s;
    }

    public boolean equals(Object object) {
        if (!(object instanceof no)) {
            return false;
        }
        no no2 = (no)object;
        if (no2.size() != this.size()) {
            return false;
        }
        return this.a(new zk_2(no2));
    }

    public int hashCode() {
        wr_1 wr_12 = new wr_1(this, null);
        this.a(wr_12);
        return wr_12.dY();
    }

    protected void O(int n2) {
        this.Ol[n2] = 0;
        super.O(n2);
    }

    public short[] getValues() {
        short[] sArray = new short[this.size()];
        short[] sArray2 = this.Ol;
        byte[] byArray = this.bCp;
        int n2 = sArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            sArray[n3++] = sArray2[n2];
        }
        return sArray;
    }

    public int[] pL() {
        int[] nArray = new int[this.size()];
        int[] nArray2 = this.dYH;
        byte[] byArray = this.bCp;
        int n2 = nArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            nArray[n3++] = nArray2[n2];
        }
        return nArray;
    }

    public int[] h(int[] nArray) {
        int n2 = this.size();
        if (nArray.length < n2) {
            nArray = (int[])Array.newInstance(nArray.getClass().getComponentType(), n2);
        }
        int[] nArray2 = this.dYH;
        int n3 = nArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (nArray2[n3] == 0 || nArray2[n3] == 2) continue;
            nArray[n4++] = nArray2[n3];
        }
        return nArray;
    }

    public boolean J(short s) {
        byte[] byArray = this.bCp;
        short[] sArray = this.Ol;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || s != sArray[n2]) continue;
            return true;
        }
        return false;
    }

    public boolean bY(int n2) {
        return this.contains(n2);
    }

    public boolean e(aLR aLR2) {
        return this.a(aLR2);
    }

    public boolean e(cj_1 cj_12) {
        byte[] byArray = this.bCp;
        short[] sArray = this.Ol;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || cj_12.aq(sArray[n2])) continue;
            return false;
        }
        return true;
    }

    public boolean a(Dw dw) {
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        short[] sArray = this.Ol;
        int n2 = nArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || dw.i(nArray[n2], sArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(Dw dw) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        int[] nArray = this.dYH;
        short[] sArray = this.Ol;
        this.pf();
        try {
            int n2 = nArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || dw.i(nArray[n2], sArray[n2])) continue;
                this.O(n2);
                bl2 = true;
            }
        }
        finally {
            this.Y(true);
        }
        return bl2;
    }

    public void a(apk_1 apk_12) {
        byte[] byArray = this.bCp;
        short[] sArray = this.Ol;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            sArray[n2] = apk_12.bR(sArray[n2]);
        }
    }

    public boolean cm(int n2) {
        return this.h(n2, (short)1);
    }

    public boolean h(int n2, short s) {
        int n3 = this.hJ(n2);
        if (n3 < 0) {
            return false;
        }
        int n4 = n3;
        this.Ol[n4] = (short)(this.Ol[n4] + s);
        return true;
    }

    public short a(int n2, short s, short s2) {
        boolean bl2;
        short s3;
        int n3 = this.pr(n2);
        if (n3 < 0) {
            int n4 = n3 = -n3 - 1;
            short s4 = (short)(this.Ol[n4] + s);
            this.Ol[n4] = s4;
            s3 = s4;
            bl2 = false;
        } else {
            s3 = this.Ol[n3] = s2;
            bl2 = true;
        }
        byte by = this.bCp[n3];
        this.dYH[n3] = n2;
        this.bCp[n3] = 1;
        if (bl2) {
            this.Z(by == 0);
        }
        return s3;
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
            short s = objectInput.readShort();
            this.g(n3, s);
        }
    }
}

