/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from wK
 */
public class wk_1
extends iz_0 {
    private String sI = null;

    public wk_1() {
    }

    public wk_1(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2) {
        super(rd_12, rd_13, jx_02, n2);
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        this.B(byteBuffer);
        byte[] byArray = aey_0.hH(this.sI);
        byteBuffer.putShort((short)byArray.length);
        byteBuffer.put(byArray);
        return byteBuffer.array();
    }

    public int nj() {
        byte[] byArray = aey_0.hH(this.sI);
        return this.UD() + 2 + byArray.length;
    }

    public iz_0 h(ByteBuffer byteBuffer) {
        wk_1 wk_12 = new wk_1();
        wk_12.C(byteBuffer);
        byte[] byArray = new byte[byteBuffer.getShort()];
        byteBuffer.get(byArray);
        wk_12.sI = aey_0.V(byArray);
        return wk_12;
    }

    public iz_0 nk() {
        wk_1 wk_12 = new wk_1();
        this.c(wk_12);
        return wk_12;
    }

    public int getType() {
        return 1;
    }

    public String getMessage() {
        return this.sI;
    }

    public Object getFieldValue(String string) {
        if (string.equals(sU)) {
            return this.sI;
        }
        return super.getFieldValue(string);
    }
}

