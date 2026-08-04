/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class NN
extends ael_2 {
    final lb_0 bAx = new lb_0();
    private int btt;
    private boolean bAy;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bAy = byteBuffer.get() == 1;
        this.btt = byteBuffer.getInt();
        while (byteBuffer.hasRemaining()) {
            nn_0 nn_02 = new nn_0();
            nn_02.b(byteBuffer);
            nn_02.q(byteBuffer.getShort());
            this.bAx.c(nn_02.jf(), nn_02);
        }
        return true;
    }

    public int getId() {
        return 5401;
    }

    public lb_0 aaV() {
        return this.bAx;
    }

    public int aaW() {
        return this.btt;
    }

    public boolean aaX() {
        return this.bAy;
    }
}

