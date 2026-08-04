/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aBh
 */
public class abh_0
extends ael_2 {
    private ArrayList drb = new ArrayList();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
            byteBuffer.get(byArray2);
            String string = aey_0.V(byArray2);
            this.drb.add(string);
        }
        return true;
    }

    public void main(String[] stringArray) {
        byte[] byArray = new byte[]{18};
        this.a(byArray);
    }

    public int getId() {
        return 3146;
    }

    public ArrayList aNe() {
        return this.drb;
    }
}

