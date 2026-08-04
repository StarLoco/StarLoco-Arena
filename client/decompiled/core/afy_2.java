/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from afY
 */
public class afy_2
extends axX {
    private static final acl_0 aU = new ym_0(new akX());
    private String bEp;
    private int csZ;
    private String cta;
    private int ctb;
    private byte ctc;
    private short ctd;
    private int cte;
    private long ctf;
    private double ctg;
    private float cth;
    private String cti;

    public static afy_2 avQ() {
        try {
            afy_2 afy_22 = (afy_2)aU.adr();
            afy_22.a(aU);
            return afy_22;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return new afy_2();
        }
    }

    private afy_2() {
    }

    public byte[] encode() {
        byte[] byArray = this.bEp.getBytes();
        int n2 = 0;
        switch (this.csZ) {
            case 1: {
                n2 = 1;
                break;
            }
            case 2: {
                n2 = 2;
                break;
            }
            case 3: {
                n2 = 4;
                break;
            }
            case 4: {
                n2 = 8;
                break;
            }
            case 5: {
                n2 = 8;
                break;
            }
            case 6: {
                n2 = 4;
                break;
            }
            case 7: {
                n2 = this.cti.length() + 1;
            }
        }
        byte[] byArray2 = new byte[]{};
        if (this.cta != null) {
            byArray2 = this.cta.getBytes();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length + 1 + byArray2.length + 4 + 4 + n2);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putInt(this.csZ);
        byteBuffer.put((byte)byArray2.length);
        byteBuffer.put(byArray2);
        byteBuffer.putInt(this.ctb);
        switch (this.csZ) {
            case 1: {
                byteBuffer.put(this.ctc);
                break;
            }
            case 2: {
                byteBuffer.putShort(this.ctd);
                break;
            }
            case 3: {
                byteBuffer.putInt(this.cte);
                break;
            }
            case 4: {
                byteBuffer.putLong(this.ctf);
                break;
            }
            case 5: {
                byteBuffer.putDouble(this.ctg);
                break;
            }
            case 6: {
                byteBuffer.putFloat(this.cth);
                break;
            }
            case 7: {
                byte[] byArray3 = this.cti.getBytes();
                byteBuffer.put((byte)byArray3.length);
                byteBuffer.put(byArray3);
            }
        }
        return this.x(byteBuffer.array());
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.bEp = new String(byArray2);
        this.csZ = byteBuffer.getInt();
        byte[] byArray3 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray3);
        this.cta = new String(byArray3);
        this.ctb = byteBuffer.getInt();
        switch (this.csZ) {
            case 1: {
                this.ctc = byteBuffer.get();
                break;
            }
            case 2: {
                this.ctd = byteBuffer.getShort();
                break;
            }
            case 3: {
                this.cte = byteBuffer.getInt();
                break;
            }
            case 4: {
                this.ctf = byteBuffer.getLong();
                break;
            }
            case 5: {
                this.ctg = byteBuffer.getDouble();
                break;
            }
            case 6: {
                this.cth = byteBuffer.getFloat();
                break;
            }
            case 7: {
                byte[] byArray4 = new byte[byteBuffer.get() & 0xFF];
                byteBuffer.get(byArray4);
                this.cti = new String(byArray4);
            }
        }
        return true;
    }

    public int getId() {
        return 11;
    }

    public String getPropertyName() {
        return this.bEp;
    }

    public void fF(String string) {
        this.bEp = string;
    }

    public int rl() {
        return this.csZ;
    }

    public void ks(int n2) {
        this.csZ = n2;
    }

    public byte aj() {
        return this.ctc;
    }

    public void a(byte by) {
        this.ctc = by;
    }

    public short ak() {
        return this.ctd;
    }

    public void bF(short s) {
        this.ctd = s;
    }

    public int getIntValue() {
        return this.cte;
    }

    public void g(int n2) {
        this.cte = n2;
    }

    public long getLongValue() {
        return this.ctf;
    }

    public void e(long l2) {
        this.ctf = l2;
    }

    public double getDoubleValue() {
        return this.ctg;
    }

    public void a(double d) {
        this.ctg = d;
    }

    public float getFloatValue() {
        return this.cth;
    }

    public void c(float f) {
        this.cth = f;
    }

    public String getStringValue() {
        return this.cti;
    }

    public void b(String string) {
        this.cti = string;
    }

    public String avR() {
        return this.cta;
    }

    public void ic(String string) {
        this.cta = string;
    }

    public int avS() {
        return this.ctb;
    }

    public void kt(int n2) {
        this.ctb = n2;
    }

    public boolean isSecure() {
        return false;
    }

    /* synthetic */ afy_2(akX akX2) {
        this();
    }
}

