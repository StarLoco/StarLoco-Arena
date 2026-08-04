/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from awa
 */
public class awa_0
extends ael_2 {
    private ArrayList byq = new ArrayList();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            int n3 = byteBuffer.getInt();
            this.byq.add(((iz_0)tb_0.zl().dD(n3)).h(byteBuffer));
        }
        return true;
    }

    public int getId() {
        return 17003;
    }

    public ArrayList Zz() {
        return this.byq;
    }
}

