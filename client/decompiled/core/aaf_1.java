/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aAf
 */
public class aaf_1
extends ael_2 {
    private final ArrayList doV = new ArrayList();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.get() & 0xFF;
        this.doV.clear();
        for (int j = 0; j < n2; ++j) {
            short s = byteBuffer.getShort();
            byte[] byArray2 = new byte[s];
            byteBuffer.get(byArray2);
            qm qm2 = new qm(this);
            qm2.b(byArray2);
            this.doV.add(qm2);
        }
        return true;
    }

    public int getId() {
        return 3144;
    }

    public void j() {
        super.j();
        this.doV.clear();
    }

    public Iterable aMB() {
        return this.doV;
    }
}

