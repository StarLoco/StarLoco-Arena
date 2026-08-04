/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.log4j.Logger;

public class aCH {
    private static final Logger a = Logger.getLogger(aCH.class);
    private final zm_1 duD = new zm_1();
    private static final aCH duE = new aCH();

    public static aCH aOu() {
        return duE;
    }

    private aCH() {
    }

    public void load(String string) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(vq_2.readFile(string));
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            bg_2 bg_22 = new bg_2(byteBuffer);
            this.duD.b(bg_22.ayK, bg_22);
        }
    }

    public bg_2 cl(short s) {
        return (bg_2)this.duD.an(s);
    }
}

