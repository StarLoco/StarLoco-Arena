/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from aEx
 */
class aex_1 {
    private static final int dzQ = 10;
    private final int dzR;
    private final ArrayList dzS = new ArrayList();
    private final ArrayList dzT = new ArrayList();
    private final Object bVA = new Object();

    public aex_1(int n2) {
        this.dzR = n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ByteBuffer aPW() {
        Object object = this.bVA;
        synchronized (object) {
            if (this.dzT.isEmpty()) {
                for (int j = 0; j < 10; ++j) {
                    this.dzT.add(ByteBuffer.allocate(this.dzR));
                }
            }
            if (this.dzT.isEmpty()) {
                return null;
            }
            ByteBuffer byteBuffer = (ByteBuffer)this.dzT.remove(0);
            this.dzS.add(byteBuffer);
            return byteBuffer;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean N(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("buffer = null");
        }
        Object object = this.bVA;
        synchronized (object) {
            if (!this.dzS.contains(byteBuffer)) {
                return false;
            }
            byteBuffer.clear();
            this.dzS.remove(byteBuffer);
            this.dzT.add(byteBuffer);
            return true;
        }
    }
}

