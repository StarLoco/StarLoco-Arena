/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from OF
 */
public class of_2 {
    private int aW;
    private ArrayList bCh = new ArrayList();
    private static final rf_1 bCi = new rf_1(null);

    public of_2() {
    }

    public of_2(int n2) {
        this.aW = n2;
    }

    public int nj() {
        return 5 + this.bCh.size() * zy_2.nj();
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        byteBuffer.putInt(this.aW);
        byteBuffer.put((byte)this.bCh.size());
        for (int j = 0; j < this.bCh.size(); ++j) {
            byteBuffer.put(((zy_2)this.bCh.get(j)).cd());
        }
        return byteBuffer.array();
    }

    public void f(ByteBuffer byteBuffer) {
        this.aW = byteBuffer.getInt();
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            zy_2 zy_22 = new zy_2();
            zy_22.f(byteBuffer);
            this.bCh.add(zy_22);
        }
    }

    public int getId() {
        return this.aW;
    }

    public void a(zy_2 zy_22) {
        this.bCh.add(zy_22);
        Collections.sort(this.bCh, bCi);
    }

    public zy_2 hd(int n2) {
        return (zy_2)this.bCh.get(n2);
    }

    public int abz() {
        return this.bCh.size();
    }
}

