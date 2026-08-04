/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aqB
 */
public class aqb_0
extends ael_2 {
    private final ArrayList cOs = new ArrayList();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.cOs.clear();
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            this.cOs.add(new dx_1(this, byteBuffer.getLong(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort()));
        }
        return true;
    }

    public int getId() {
        return 4106;
    }

    public Iterable aEc() {
        return this.cOs;
    }

    public int aEd() {
        return this.cOs.size();
    }
}

