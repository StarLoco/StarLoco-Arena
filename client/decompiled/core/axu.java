/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

public class axu
extends ws_2
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected transient short[] Ol;

    public axu() {
    }

    public axu(int n2) {
        super(n2);
    }

    public axu(int n2, float f) {
        super(n2, f);
    }

    public axu(alo_0 alo_02) {
        super(alo_02);
    }

    public axu(int n2, alo_0 alo_02) {
        super(n2, alo_02);
    }

    public axu(int n2, float f, alo_0 alo_02) {
        super(n2, f, alo_02);
    }

    public Object clone() {
        axu axu2 = (axu)super.clone();
        axu2.Ol = (short[])this.Ol.clone();
        return axu2;
    }

    public mr_2 aKc() {
        return new mr_2(this);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.Ol = new short[n3];
        return n3;
    }

    public short b(byte by, short s) {
        short s2 = 0;
        int n2 = this.E(by);
        boolean bl2 = true;
        if (n2 < 0) {
            n2 = -n2 - 1;
            s2 = this.Ol[n2];
            bl2 = false;
        }
        byte by2 = this.bCp[n2];
        this.auE[n2] = by;
        this.bCp[n2] = 1;
        this.Ol[n2] = s;
        if (bl2) {
            this.Z(by2 == 0);
        }
        return s2;
    }

    protected void rehash(int n2) {
        int n3 = this.auE.length;
        byte[] byArray = this.auE;
        short[] sArray = this.Ol;
        byte[] byArray2 = this.bCp;
        this.auE = new byte[n2];
        this.Ol = new short[n2];
        this.bCp = new byte[n2];
        int n4 = n3;
        while (n4-- > 0) {
            if (byArray2[n4] != 1) continue;
            byte by = byArray[n4];
            int n5 = this.E(by);
            this.auE[n5] = by;
            this.Ol[n5] = sArray[n4];
            this.bCp[n5] = 1;
        }
    }

    public short ba(byte by) {
        int n2 = this.D(by);
        return n2 < 0 ? (short)0 : this.Ol[n2];
    }

    public void clear() {
        super.clear();
        byte[] byArray = this.auE;
        short[] sArray = this.Ol;
        byte[] byArray2 = this.bCp;
        int n2 = byArray.length;
        while (n2-- > 0) {
            byArray[n2] = 0;
            sArray[n2] = 0;
            byArray2[n2] = 0;
        }
    }

    public short bb(byte by) {
        short s = 0;
        int n2 = this.D(by);
        if (n2 >= 0) {
            s = this.Ol[n2];
            this.O(n2);
        }
        return s;
    }

    public boolean equals(Object object) {
        if (!(object instanceof axu)) {
            return false;
        }
        axu axu2 = (axu)object;
        if (axu2.size() != this.size()) {
            return false;
        }
        return this.a(new xk_2(axu2));
    }

    public int hashCode() {
        em_0 em_02 = new em_0(this, null);
        this.a(em_02);
        return em_02.dY();
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

    public byte[] GF() {
        byte[] byArray = new byte[this.size()];
        byte[] byArray2 = this.auE;
        byte[] byArray3 = this.bCp;
        int n2 = byArray2.length;
        int n3 = 0;
        while (n2-- > 0) {
            if (byArray3[n2] != 1) continue;
            byArray[n3++] = byArray2[n2];
        }
        return byArray;
    }

    public byte[] B(byte[] byArray) {
        int n2 = this.size();
        if (byArray.length < n2) {
            byArray = (byte[])Array.newInstance(byArray.getClass().getComponentType(), n2);
        }
        byte[] byArray2 = this.auE;
        int n3 = byArray2.length;
        int n4 = 0;
        while (n3-- > 0) {
            if (byArray2[n3] == 0 || byArray2[n3] == 2) continue;
            byArray[n4++] = byArray2[n3];
        }
        return byArray;
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

    public boolean K(byte by) {
        return this.contains(by);
    }

    public boolean b(amm_2 amm_22) {
        return this.a(amm_22);
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

    public boolean a(gg_1 gg_12) {
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        short[] sArray = this.Ol;
        int n2 = byArray2.length;
        while (n2-- > 0) {
            if (byArray[n2] != 1 || gg_12.a(byArray2[n2], sArray[n2])) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(gg_1 gg_12) {
        boolean bl2 = false;
        byte[] byArray = this.bCp;
        byte[] byArray2 = this.auE;
        short[] sArray = this.Ol;
        this.pf();
        try {
            int n2 = byArray2.length;
            while (n2-- > 0) {
                if (byArray[n2] != 1 || gg_12.a(byArray2[n2], sArray[n2])) continue;
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

    public boolean L(byte by) {
        return this.c(by, (short)1);
    }

    public boolean c(byte by, short s) {
        int n2 = this.D(by);
        if (n2 < 0) {
            return false;
        }
        int n3 = n2;
        this.Ol[n3] = (short)(this.Ol[n3] + s);
        return true;
    }

    public short a(byte by, short s, short s2) {
        boolean bl2;
        short s3;
        int n2 = this.E(by);
        if (n2 < 0) {
            int n3 = n2 = -n2 - 1;
            short s4 = (short)(this.Ol[n3] + s);
            this.Ol[n3] = s4;
            s3 = s4;
            bl2 = false;
        } else {
            s3 = this.Ol[n2] = s2;
            bl2 = true;
        }
        byte by2 = this.bCp[n2];
        this.auE[n2] = by;
        this.bCp[n2] = 1;
        if (bl2) {
            this.Z(by2 == 0);
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
            byte by = objectInput.readByte();
            short s = objectInput.readShort();
            this.b(by, s);
        }
    }
}

