/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class IL
extends ael_2 {
    private int bhZ = 0;
    private lb_0 bia;
    private int bib = 0;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bhZ = byteBuffer.getInt();
        int n2 = byteBuffer.getInt();
        this.bia = new lb_0(n2);
        for (int j = n2 - 1; 0 <= j; --j) {
            String string;
            int n3 = byteBuffer.getInt();
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            try {
                string = new String(byArray2, "UTF-8");
            }
            catch (Exception exception) {
                string = "";
            }
            this.bia.c(n3, string);
        }
        this.bib = byteBuffer.getInt();
        return true;
    }

    public int UN() {
        return this.bhZ;
    }

    public lb_0 UO() {
        return this.bia;
    }

    public int UP() {
        return this.bib;
    }

    public int getId() {
        return 28650;
    }
}

