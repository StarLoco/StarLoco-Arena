/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ayV
extends ael_2 {
    private ArrayList dmR;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.dmR = new ArrayList();
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            ho_0 ho_02 = new ho_0();
            ho_02.f(byteBuffer);
            this.dmR.add(ho_02);
        }
        return true;
    }

    public int getId() {
        return 15001;
    }

    public ArrayList aLH() {
        return this.dmR;
    }
}

