/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class ug
extends aLb {
    public static final short apv = 1;
    public static final short apw = 2;
    public static final short apx = 3;
    public static final short apy = 4;
    private String wV;
    private String sI;
    protected int apz;
    private jg_0 apA = null;
    public static final acl_0 uG = new ym_0(new cl_2());

    public int Aq() {
        if (this.wV == null || this.wV.equals("")) {
            return 0;
        }
        byte[] byArray = aey_0.hH(this.wV);
        return 6 + byArray.length;
    }

    public ByteBuffer p(ByteBuffer byteBuffer) {
        if (this.wV == null || this.wV.equals("")) {
            return byteBuffer;
        }
        byteBuffer.putShort((short)1);
        byte[] byArray = aey_0.hH(this.wV);
        byteBuffer.putInt(byArray.length);
        byteBuffer.put(byArray);
        return byteBuffer;
    }

    public void q(ByteBuffer byteBuffer) {
        byte[] byArray = new byte[byteBuffer.getInt()];
        byteBuffer.get(byArray);
        this.wV = aey_0.V(byArray);
    }

    public int Ar() {
        if (this.sI == null || this.sI.equals("")) {
            return 0;
        }
        byte[] byArray = aey_0.hH(this.sI);
        return 6 + byArray.length;
    }

    public ByteBuffer r(ByteBuffer byteBuffer) {
        if (this.sI == null || this.sI.equals("")) {
            return byteBuffer;
        }
        byteBuffer.putShort((short)2);
        byte[] byArray = aey_0.hH(this.sI);
        byteBuffer.putInt(byArray.length);
        byteBuffer.put(byArray);
        return byteBuffer;
    }

    public void s(ByteBuffer byteBuffer) {
        byte[] byArray = new byte[byteBuffer.getInt()];
        byteBuffer.get(byArray);
        this.sI = aey_0.V(byArray);
    }

    public int As() {
        if (this.apz == -1) {
            return 0;
        }
        return 6;
    }

    public ByteBuffer t(ByteBuffer byteBuffer) {
        if (this.apz == -1) {
            return byteBuffer;
        }
        byteBuffer.putShort((short)4);
        byteBuffer.putInt(this.apz);
        return byteBuffer;
    }

    public void u(ByteBuffer byteBuffer) {
        this.apz = byteBuffer.getInt();
    }

    public int At() {
        if (this.apA == null || this.apA.size() == 0) {
            return 0;
        }
        return 4 + this.apA.size() * 4;
    }

    public ByteBuffer v(ByteBuffer byteBuffer) {
        if (this.apA == null || this.apA.size() == 0) {
            return byteBuffer;
        }
        byteBuffer.putShort((short)3);
        byteBuffer.putShort((short)this.apA.size());
        for (int n2 : this.apA.nm()) {
            byteBuffer.putInt(n2);
        }
        return byteBuffer;
    }

    public void w(ByteBuffer byteBuffer) {
        int n2 = byteBuffer.getShort();
        if (this.apA == null) {
            this.apA = new jg_0();
        } else {
            this.apA.clear();
        }
        for (int j = 0; j < n2; ++j) {
            this.apA.add(byteBuffer.getInt());
        }
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.Av());
        this.p(byteBuffer);
        this.r(byteBuffer);
        this.v(byteBuffer);
        this.t(byteBuffer);
        this.dUF = byteBuffer.array();
        return super.cd();
    }

    public void f(ByteBuffer byteBuffer) {
        super.f(byteBuffer);
        this.Au();
    }

    public void Au() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(this.dUF);
        while (byteBuffer.hasRemaining()) {
            short s = byteBuffer.getShort();
            switch (s) {
                case 1: {
                    this.q(byteBuffer);
                    break;
                }
                case 2: {
                    this.s(byteBuffer);
                    break;
                }
                case 3: {
                    this.w(byteBuffer);
                    break;
                }
                case 4: {
                    this.u(byteBuffer);
                }
            }
        }
    }

    public int Av() {
        int n2 = 0;
        n2 += this.Aq();
        n2 += this.Ar();
        n2 += this.At();
        return n2 += this.As();
    }

    public int nj() {
        return this.Av() + super.aVZ();
    }

    public static ug Aw() {
        ug ug2;
        try {
            ug2 = (ug)uG.adr();
        }
        catch (Exception exception) {
            ug2 = new ug();
            a.fatal((Object)"Erreur lors d'un checkOut sur un objet de type Mail", (Throwable)exception);
        }
        return ug2;
    }

    public void release() {
        try {
            uG.af(this);
        }
        catch (Exception exception) {
            a.error((Object)"Erreur lors du release d'un Mail", (Throwable)exception);
        }
    }

    public void b() {
        super.b();
        this.sI = null;
        this.wV = null;
        if (this.apA != null) {
            this.apA.clear();
        }
        this.apz = 0;
    }

    public String getTitle() {
        return this.wV;
    }

    public void setTitle(String string) {
        this.wV = string;
    }

    public String getMessage() {
        return this.sI;
    }

    public void setMessage(String string) {
        this.sI = string;
    }

    public jg_0 Ax() {
        return this.apA;
    }

    public void b(jg_0 jg_02) {
        this.apA = jg_02;
    }

    public void aM(int n2) {
        if (this.apA == null) {
            this.apA = new jg_0();
        }
        this.apA.add(n2);
    }

    public void aN(int n2) {
        if (this.apA != null) {
            for (int j = 0; j < this.apA.size(); ++j) {
                if (this.apA.get(j) != n2) continue;
                this.apA.bv(j);
                break;
            }
        }
    }

    public int Ay() {
        return this.apz;
    }

    public void dL(int n2) {
        this.apz = n2;
    }

    public String toString() {
        String string = "";
        if (this.apA != null) {
            for (int j = 0; j < this.apA.size(); ++j) {
                string = string + this.apA.bu(j) + " ";
            }
        }
        return super.toString() + " Title : " + this.wV + " Message : " + this.sI + " DAItems : " + (this.apA == null ? "null " : this.apA.size() + " objet(s) : " + string) + "DAServerMessageId : " + this.apz;
    }
}

