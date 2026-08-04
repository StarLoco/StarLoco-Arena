/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class aFu
extends so_0 {
    private int dGZ;
    private int[] dHa = ug_2.bQd;
    private String BF;
    private String bGv;
    private rd_1 dHb;
    private rd_1 dHc;
    private rd_1 dHd;
    private ArrayList dHe = new ArrayList();
    private byte dHf;
    private jx_0 bhG = jx_0.blQ;
    private int dHg;
    private int[] dHh;

    public byte[] encode() {
        int n2;
        byte[] byArray = aey_0.hH(this.BF);
        byte[] byArray2 = aey_0.hH(this.bGv);
        ByteBuffer byteBuffer = ByteBuffer.allocate(6 + this.dHa.length * 4 + 8 + 8 + 8 + 1 + this.dHe.size() * 8 + 2 + byArray2.length + 1 + byArray.length + 1 + 4 + 8 + 1 + this.dHh.length * 4);
        byteBuffer.putInt(this.dGZ);
        byteBuffer.putShort((short)this.dHa.length);
        for (n2 = 0; n2 < this.dHa.length; ++n2) {
            byteBuffer.putInt(this.dHa[n2]);
        }
        byteBuffer.putLong(this.dHb.uJ());
        byteBuffer.putLong(this.dHc.uJ());
        byteBuffer.putLong(this.dHd.uJ());
        byteBuffer.put((byte)this.dHe.size());
        for (n2 = 0; n2 < this.dHe.size(); ++n2) {
            byteBuffer.putLong(((rd_1)this.dHe.get(n2)).uJ());
        }
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putShort((short)byArray2.length);
        byteBuffer.put(byArray2);
        byteBuffer.put(this.dHf);
        byteBuffer.putLong(this.bhG.uJ());
        byteBuffer.putInt(this.dHg);
        byteBuffer.put((byte)this.dHh.length);
        for (n2 = 0; n2 < this.dHh.length; ++n2) {
            byteBuffer.putInt(this.dHh[n2]);
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 17010;
    }

    public void op(int n2) {
        this.dGZ = n2;
    }

    public void oq(int n2) {
        int[] nArray = new int[this.dHa.length + 1];
        System.arraycopy(this.dHa, 0, nArray, 0, this.dHa.length);
        nArray[nArray.length - 1] = n2;
        this.dHa = nArray;
    }

    public void J(int[] nArray) {
        this.dHa = nArray;
    }

    public void d(rd_1 rd_12) {
        this.dHb = rd_12;
    }

    public void e(rd_1 rd_12) {
        this.dHc = rd_12;
    }

    public void f(rd_1 rd_12) {
        this.dHd = rd_12;
    }

    public void b(rd_1 rd_12, rd_1 rd_13) {
        this.dHe.add(rd_12);
        this.dHe.add(rd_13);
    }

    public void ll(String string) {
        this.BF = string;
    }

    public void lm(String string) {
        this.bGv = string;
    }

    public void bm(byte by) {
        this.dHf = by;
    }

    public void or(int n2) {
        this.dHg = n2;
    }

    public void K(int[] nArray) {
        this.dHh = nArray;
    }

    public void d(jx_0 jx_02) {
        this.bhG = jx_02;
    }

    public void c(rd_1 rd_12, rd_1 rd_13) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        this.dHb = rd_1.aF(gregorianCalendar.getTimeInMillis());
        this.dHb.m(0, 2, 0, 0);
        this.dHe.add(rd_12);
        this.dHe.add(rd_13);
        this.dHc = new rd_1(0, 58, 23, rd_13.getDay(), rd_13.getMonth(), rd_13.getYear());
        this.dHd = new rd_1(0, 59, 23, rd_13.getDay(), rd_13.getMonth(), rd_13.getYear());
        this.dHf = (byte)23;
        this.bhG = jx_0.blT;
        this.dHg = 0;
        this.dHh = ug_2.bQd;
    }
}

