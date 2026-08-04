/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class aEV
extends ael_2 {
    private final ArrayList cOs = new ArrayList();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.cOs.clear();
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            this.cOs.add(new aam_0(this, byteBuffer.getLong(), byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort(), byteBuffer.get()));
        }
        return true;
    }

    public int getId() {
        return 4102;
    }

    public Iterable aEc() {
        return this.cOs;
    }

    public int aEd() {
        return this.cOs.size();
    }
}

