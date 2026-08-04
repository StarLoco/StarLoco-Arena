/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from TH
 */
public abstract class th_2
extends iz_0 {
    protected rd_1 bOF;
    private iz_0 bOG;

    public th_2() {
    }

    public th_2(rd_1 rd_12, rd_1 rd_13, jx_0 jx_02, int n2) {
        super(rd_12, rd_13, jx_02, n2);
    }

    public void C(ByteBuffer byteBuffer) {
        super.C(byteBuffer);
        this.bOF = rd_1.aF(byteBuffer.getLong());
        if (this.bOF.f(this.OV) > 0 && this.bhG != jx_0.blQ) {
            this.bOF.c(this.bhG);
        }
    }

    public void c(iz_0 iz_02) {
        ((th_2)iz_02).c(new rd_1(this.bOF));
        super.c(iz_02);
    }

    public rd_1 agh() {
        return this.bOF;
    }

    public void c(rd_1 rd_12) {
        this.bOF = rd_12;
    }

    public iz_0 agi() {
        return this.bOG;
    }

    public void d(iz_0 iz_02) {
        this.bOG = iz_02;
    }
}

