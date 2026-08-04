/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from lS
 */
public class ls_0
extends ael_2 {
    private aGz Ir = new aGz();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getInt();
        int n3 = 0;
        while (n3 * 4 < n2) {
            this.Ir.A(byteBuffer.getShort(), byteBuffer.getShort());
            ++n3;
        }
        return true;
    }

    public int getId() {
        return 22002;
    }

    public aGz qI() {
        return this.Ir;
    }
}

