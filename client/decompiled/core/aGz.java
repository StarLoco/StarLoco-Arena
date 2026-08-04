/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class aGz
extends us
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient short[] Ol;

    public aGz() {
    }

    public aGz(int n2) {
        super(n2);
    }

    public aGz(int n2, float f) {
        super(n2, f);
    }

    public aGz(Nh nh) {
        super(nh);
    }

    public aGz(int n2, Nh nh) {
        super(n2, nh);
    }

    public aGz(int n2, float f, Nh nh) {
        super(n2, f, nh);
    }

    public Object clone() {
        aGz aGz2 = (aGz)super.clone();
        aGz2.Ol = (short[])this.Ol.clone();
        return aGz2;
    }

    public ll_1 aSE() {
        return new ll_1(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.Ol = new short[n3];
        return n3;
    }

    public short A(short s, short s2) {
        short s3 = 0;
        int n2 = this.ac(s);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            s3 = this.Ol[n2];
            bl2 = false;
        }
        byte by = this.bCp[n2];
        this.aqv[n2] = s;
        this.bCp[n2] = 1;
        this.Ol[n2] = s2;
        if (bl2) {
            this.Z(by == 0);
        }
        return s3;
    }

    protected void rehash(int n2) {
        int n3 = this.aqv.length;
        short[] sArray = this.aqv;
        short[] sArray2 = this.Ol;
        byte[] byArray = this.bCp;
        this.aqv = new short[n2];
        this.Ol = new short[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray[n4] != 1) continue;
            short s = sArray[n4];
            int n5 = this.ac(s);
            this.aqv[n5] = s;
            this.Ol[n5] = sArray2[n4];
            this.bCp[n5] = 1;
        }
    }

    public short cp(short s) {
        int n2 = this.ab(s);
        return n2 < 0 ? (short)0 : this.Ol[n2];
    }

    public void clear() {
        super.clear();
        short[] sArray = this.aqv;
        short[] sArray2 = this.Ol;
        byte[] byArray = this.bCp;
        int n2 = sArray.length;
        while (n2-- > 0) {
            sArray[n2] = 0;
            sArray2[n2] = 0;
            byArray[n2] = 0;
        }
    }

    public short cq(short s) {
        short s2 = 0;
        int n2 = this.ab(s);
        if (n2 >= 0) {
            s2 = this.Ol[n2];
            this.O(n2);
        }
        return s2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof aGz)) {
            return false;
        }
        aGz aGz2 = (aGz)object;
        if (aGz2.size() != this.size()) {
            return false;
        }
        return this.a(new sh_1(aGz2));
    }

    public int hashCode() {
        hw_1 hw_12 = new hw_1(this, null);
        this.a(hw_12);
        return hw_12.dY();
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

    public short[] Gj() {
        short[] sArray = new short[this.size()];
        short[] sArray2 = this.aqv;
        byte[] byArray = this.bCp;
        int n2 = sArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray[n2] != 1) continue;
            sArray[n3++] = sArray2[n2];
        }
        return sArray;
    }

    public short[] i(short[] sArray) {
        int n2 = this.size();
        if (sArray.length < n2) {
            sArray = (short[])Array.newInstance(sArray.getClass().getComponentType(), n2);
        }
        short[] sArray2 = this.aqv;
        int n3 = sArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (sArray2[n3] == 0 || sArray2[n3] == 2) continue;
            sArray[n4++] = sArray2[n3];
        }
        return sArray;
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

    public boolean ap(short s) {
        return this.contains(s);
    }

    public boolean f(cj_1 cj_12) {
        return this.a(cj_12);
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

    public boolean a(tr_2 tr_22) {
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        short[] sArray2 = this.Ol;
        int n2 = sArray.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || tr_22.f(sArray[n2], sArray2[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(tr_2 tr_22) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        short[] sArray = this.aqv;
        short[] sArray2 = this.Ol;
        this.pf();
        try {
            int n2 = sArray.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || tr_22.f(sArray[n2], sArray2[n2])) continue;
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

    public boolean br(short s) {
        return this.B(s, (short)1);
    }

    public boolean B(short s, short s2) {
        int n2 = this.ab(s);
        if (n2 < 0) {
            return false;
        }
        int n3 = n2;
        this.Ol[n3] = (short)(this.Ol[n3] + s2);
        return true;
    }

    public short b(short s, short s2, short s3) {
        boolean bl2;
        short s4;
        int n2 = this.ac(s);
        if (n2 < 0) {
            int n3 = n2 = -n2 - 1;
            short s5 = (short)(this.Ol[n3] + s2);
            this.Ol[n3] = s5;
            s4 = s5;
            bl2 = false;
        } else {
            s4 = this.Ol[n2] = s3;
            bl2 = true;
        }
        byte by = this.bCp[n2];
        this.aqv[n2] = s;
        this.bCp[n2] = 1;
        if (bl2) {
            this.Z(by == 0);
        }
        return s4;
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
            short s2 = objectInput.readShort();
            this.A(s, s2);
        }
    }
}

