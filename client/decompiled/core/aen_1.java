/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aEN
 */
public class aen_1 {
    public static final int dBN = 0;
    public static final int dBO = 1;
    public static final int dBP = 2;
    public static final int dBQ = 4;
    public static final int dBR = 8;
    public static final int dBS = 16;
    protected int bzu;
    protected short bny;
    protected String dBT;

    public aen_1() {
    }

    public aen_1(int n2, short s, String string) {
        this.bzu = n2;
        this.bny = s;
        this.dBT = string;
    }

    public static boolean y(short s, short s2) {
        return s < s2;
    }

    public static boolean z(short s, short s2) {
        return s <= s2;
    }

    public boolean aQQ() {
        return (this.bzu & 1) != 0;
    }

    public boolean aQR() {
        return (this.bzu & 1) != 0;
    }

    public boolean aQS() {
        return (this.bzu & 1) != 0;
    }

    public boolean aQT() {
        return (this.bzu & 1) != 0;
    }

    public boolean aQU() {
        return (this.bzu & 1) != 0;
    }

    public boolean aQV() {
        return (this.bzu & 1) != 0;
    }

    public boolean aQW() {
        return (this.bzu & 1) != 0;
    }

    public boolean aQX() {
        return (this.bzu & 1) != 0;
    }

    public boolean aQY() {
        return (this.bzu & 1 | this.bzu & 2) != 0;
    }

    public boolean aQZ() {
        return (this.bzu & 1 | this.bzu & 4) != 0;
    }

    public boolean aRa() {
        return (this.bzu & 1 | this.bzu & 8) != 0;
    }

    public boolean aRb() {
        return (this.bzu & 1 | this.bzu & 0x10) != 0;
    }

    public void nB(int n2) {
        this.bzu = n2;
    }

    public int aRc() {
        byte[] byArray = aey_0.hH(this.dBT);
        return 7 + byArray.length;
    }

    public byte[] cd() {
        byte[] byArray = aey_0.hH(this.dBT);
        ByteBuffer byteBuffer = ByteBuffer.allocate(7 + byArray.length);
        byteBuffer.putShort(this.bny);
        byteBuffer.putInt(this.bzu);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return byteBuffer.array();
    }

    public int aRd() {
        return this.bzu;
    }

    public void gV(int n2) {
        this.bzu = n2;
    }

    public short aRe() {
        return this.bny;
    }

    public void aH(short s) {
        this.bny = s;
    }

    public String aRf() {
        return this.dBT;
    }

    public void lb(String string) {
        this.dBT = string;
    }
}

