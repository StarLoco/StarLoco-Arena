/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from tG
 */
public class tg_1
extends ael_2 {
    private int anV;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.anV = byteBuffer.getInt();
        return true;
    }

    public int zM() {
        return this.anV;
    }

    public int getId() {
        return 4000;
    }
}

