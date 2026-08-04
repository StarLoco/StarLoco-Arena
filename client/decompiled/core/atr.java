/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.ObjectOutput;

class atr
implements Bg,
br_2,
cj_1,
di_2,
da_0,
Dw,
gg_1,
ku_0,
lw_2,
nm_1,
op_0,
px_1,
rn_2,
td_1,
to_1,
uq_0,
uu_1,
vl_1,
aDN,
aht_0,
ahx_0,
aib_2,
aii_2,
aLR,
aOl,
aPt,
aay_2,
abg_0,
aca_2,
acp_1,
afq_2,
aje_0,
aky_0,
akg_1,
amm_2,
aoU,
aom_1,
apx,
asz_0,
asv_0,
atl_0,
fc_0,
gr_2,
hm_0,
hz_1,
kc_1,
mr_1,
ro_1,
sg_1,
st_1,
tr_2,
uB,
xf,
xi_2,
ya_0,
zD {
    private final ObjectOutput cTQ;
    IOException cTR;

    atr(ObjectOutput objectOutput) {
        this.cTQ = objectOutput;
    }

    public boolean aH(byte by) {
        try {
            this.cTQ.writeByte(by);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean aq(short s) {
        try {
            this.cTQ.writeShort(s);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean eG(int n2) {
        try {
            this.cTQ.writeInt(n2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean G(double d) {
        try {
            this.cTQ.writeDouble(d);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean aM(long l2) {
        try {
            this.cTQ.writeLong(l2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean ag(float f) {
        try {
            this.cTQ.writeFloat(f);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(Object object) {
        try {
            this.cTQ.writeObject(object);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean i(Object object, Object object2) {
        try {
            this.cTQ.writeObject(object);
            this.cTQ.writeObject(object2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(Object object, byte by) {
        try {
            this.cTQ.writeObject(object);
            this.cTQ.writeByte(by);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean b(Object object, short s) {
        try {
            this.cTQ.writeObject(object);
            this.cTQ.writeShort(s);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(Object object, int n2) {
        try {
            this.cTQ.writeObject(object);
            this.cTQ.writeInt(n2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(Object object, long l2) {
        try {
            this.cTQ.writeObject(object);
            this.cTQ.writeLong(l2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean b(Object object, double d) {
        try {
            this.cTQ.writeObject(object);
            this.cTQ.writeDouble(d);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean c(Object object, float f) {
        try {
            this.cTQ.writeObject(object);
            this.cTQ.writeFloat(f);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean c(int n2, byte by) {
        try {
            this.cTQ.writeInt(n2);
            this.cTQ.writeByte(by);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean i(int n2, short s) {
        try {
            this.cTQ.writeInt(n2);
            this.cTQ.writeShort(s);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean b(int n2, Object object) {
        try {
            this.cTQ.writeInt(n2);
            this.cTQ.writeObject(object);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean ba(int n2, int n3) {
        try {
            this.cTQ.writeInt(n2);
            this.cTQ.writeInt(n3);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean c(int n2, long l2) {
        try {
            this.cTQ.writeInt(n2);
            this.cTQ.writeLong(l2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(int n2, double d) {
        try {
            this.cTQ.writeInt(n2);
            this.cTQ.writeDouble(d);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean k(int n2, float f) {
        try {
            this.cTQ.writeInt(n2);
            this.cTQ.writeFloat(f);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean b(long l2, Object object) {
        try {
            this.cTQ.writeLong(l2);
            this.cTQ.writeObject(object);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean c(long l2, byte by) {
        try {
            this.cTQ.writeLong(l2);
            this.cTQ.writeByte(by);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean g(long l2, short s) {
        try {
            this.cTQ.writeLong(l2);
            this.cTQ.writeShort(s);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean i(long l2, int n2) {
        try {
            this.cTQ.writeLong(l2);
            this.cTQ.writeInt(n2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean f(long l2, long l3) {
        try {
            this.cTQ.writeLong(l2);
            this.cTQ.writeLong(l3);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(long l2, double d) {
        try {
            this.cTQ.writeLong(l2);
            this.cTQ.writeDouble(d);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(long l2, float f) {
        try {
            this.cTQ.writeLong(l2);
            this.cTQ.writeFloat(f);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(double d, Object object) {
        try {
            this.cTQ.writeDouble(d);
            this.cTQ.writeObject(object);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(double d, byte by) {
        try {
            this.cTQ.writeDouble(d);
            this.cTQ.writeByte(by);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(double d, short s) {
        try {
            this.cTQ.writeDouble(d);
            this.cTQ.writeShort(s);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(double d, int n2) {
        try {
            this.cTQ.writeDouble(d);
            this.cTQ.writeInt(n2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(double d, long l2) {
        try {
            this.cTQ.writeDouble(d);
            this.cTQ.writeLong(l2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean h(double d, double d2) {
        try {
            this.cTQ.writeDouble(d);
            this.cTQ.writeDouble(d2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(double d, float f) {
        try {
            this.cTQ.writeDouble(d);
            this.cTQ.writeFloat(f);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(float f, Object object) {
        try {
            this.cTQ.writeFloat(f);
            this.cTQ.writeObject(object);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(float f, byte by) {
        try {
            this.cTQ.writeFloat(f);
            this.cTQ.writeByte(by);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(float f, short s) {
        try {
            this.cTQ.writeFloat(f);
            this.cTQ.writeShort(s);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean b(float f, int n2) {
        try {
            this.cTQ.writeFloat(f);
            this.cTQ.writeInt(n2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(float f, long l2) {
        try {
            this.cTQ.writeFloat(f);
            this.cTQ.writeLong(l2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(float f, double d) {
        try {
            this.cTQ.writeFloat(f);
            this.cTQ.writeDouble(d);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean y(float f, float f2) {
        try {
            this.cTQ.writeFloat(f);
            this.cTQ.writeFloat(f2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(byte by, Object object) {
        try {
            this.cTQ.writeByte(by);
            this.cTQ.writeObject(object);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean d(byte by, byte by2) {
        try {
            this.cTQ.writeByte(by);
            this.cTQ.writeByte(by2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(byte by, short s) {
        try {
            this.cTQ.writeByte(by);
            this.cTQ.writeShort(s);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(byte by, int n2) {
        try {
            this.cTQ.writeByte(by);
            this.cTQ.writeInt(n2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean b(byte by, long l2) {
        try {
            this.cTQ.writeByte(by);
            this.cTQ.writeLong(l2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(byte by, double d) {
        try {
            this.cTQ.writeByte(by);
            this.cTQ.writeDouble(d);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(byte by, float f) {
        try {
            this.cTQ.writeByte(by);
            this.cTQ.writeFloat(f);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(short s, Object object) {
        try {
            this.cTQ.writeShort(s);
            this.cTQ.writeObject(object);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(short s, byte by) {
        try {
            this.cTQ.writeShort(s);
            this.cTQ.writeByte(by);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean f(short s, short s2) {
        try {
            this.cTQ.writeShort(s);
            this.cTQ.writeShort(s2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean c(short s, int n2) {
        try {
            this.cTQ.writeShort(s);
            this.cTQ.writeInt(n2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean c(short s, long l2) {
        try {
            this.cTQ.writeShort(s);
            this.cTQ.writeLong(l2);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean a(short s, double d) {
        try {
            this.cTQ.writeShort(s);
            this.cTQ.writeDouble(d);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }

    public boolean c(short s, float f) {
        try {
            this.cTQ.writeShort(s);
            this.cTQ.writeFloat(f);
        }
        catch (IOException iOException) {
            this.cTR = iOException;
            return false;
        }
        return true;
    }
}

