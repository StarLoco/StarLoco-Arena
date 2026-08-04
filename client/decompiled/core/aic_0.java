/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aIC
 */
public class aic_0
extends ael_2 {
    private byte aV;
    private zK aFN;
    private cp_2 aGY = new cp_2();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        if (this.aV == 0) {
            sw_1 sw_12 = sw_1.afp();
            if (sw_12.b(byteBuffer)) {
                this.aFN = zK.a(sw_12);
            } else {
                sw_12.release();
            }
            for (int j = 0; j < this.aFN.afF().size(); ++j) {
                long l2 = byteBuffer.getLong();
                byte[] byArray2 = new byte[byteBuffer.get()];
                byteBuffer.get(byArray2);
                this.aGY.a(l2, aey_0.V(byArray2));
            }
        }
        return true;
    }

    public int getId() {
        return 6020;
    }

    public byte an() {
        return this.aV;
    }

    public zK GJ() {
        return this.aFN;
    }

    public cp_2 Hj() {
        return this.aGY;
    }
}

