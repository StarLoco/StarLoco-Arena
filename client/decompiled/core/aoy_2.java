/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aOY
 */
public class aoy_2
extends iz_0 {
    private rd_1 emV;

    public aoy_2() {
    }

    public aoy_2(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2) {
        super(rd_12, rd_13, jx_02, n2);
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        this.B(byteBuffer);
        return byteBuffer.array();
    }

    public int nj() {
        return this.UD();
    }

    public iz_0 h(ByteBuffer byteBuffer) {
        aoy_2 aoy_22 = new aoy_2();
        aoy_22.C(byteBuffer);
        aoy_22.emV = rd_1.aF(byteBuffer.getLong());
        return aoy_22;
    }

    public iz_0 nk() {
        aoy_2 aoy_22 = new aoy_2();
        this.c(aoy_22);
        aoy_22.emV = this.emV;
        return aoy_22;
    }

    public rd_1 aYE() {
        return this.emV;
    }

    public int getType() {
        return 7;
    }
}

