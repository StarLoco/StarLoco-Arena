/*
 * Decompiled with CFR 0.152.
 */
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Renamed from tv
 */
public class tv_1
implements oa_1 {
    private final AtomicInteger amW = new AtomicInteger(0);
    private int ajD = 0;
    private ans_2 amX;

    public tv_1(ans_2 ans_22) {
        this.amX = ans_22;
    }

    public boolean zu() {
        this.ajD = 0;
        return true;
    }

    public boolean b(auk auk2) {
        this.ajD = 0;
        return true;
    }

    public String getDescription() {
        return this.amX.aCz().getDescription();
    }

    public int a(byte[] byArray, int n2) {
        if (!this.amX.isInitialized()) {
            return 0;
        }
        byte[] byArray2 = this.amX.aCA();
        int n3 = Math.min(byArray.length - n2, byArray2.length - this.ajD);
        n3 = Math.max(0, n3 - n3 % this.amX.aCz().zA());
        System.arraycopy(byArray2, this.ajD, byArray, n2, n3);
        this.ajD += n3;
        if (this.ajD >= byArray2.length) {
            return -n3;
        }
        return n3;
    }

    public int getNumChannels() {
        return this.amX.aCz().getNumChannels();
    }

    public int zv() {
        return this.amX.aCz().zv();
    }

    public void aB(boolean bl2) {
    }

    public void reset() {
        this.ajD = 0;
    }

    public void close() {
    }

    public int zw() {
        return this.amX.aCz().zw();
    }

    public long zx() {
        throw new UnsupportedOperationException("JOrbisVirtualStream.rawTell");
    }

    public long zy() {
        throw new UnsupportedOperationException("JOrbisVirtualStream.pcmTell");
    }

    public float zz() {
        throw new UnsupportedOperationException("JOrbisVirtualStream.timeTell");
    }

    public int T(float f) {
        throw new UnsupportedOperationException("Impossible de seek sur un son non stream\u00e9 \u00e0 la seconds=" + f);
    }

    public int aK(long l2) {
        if (l2 > Integer.MAX_VALUE) {
            throw new UnsupportedOperationException("Impossible de pcmSeek sur un son non stream\u00e9 \u00e0 la position offset=" + l2);
        }
        this.ajD = (int)l2;
        return 0;
    }

    public int aL(long l2) {
        throw new UnsupportedOperationException("Impossible de rawSeek sur un son non stream\u00e9 \u00e0 la position offset=" + l2);
    }

    public int zA() {
        return this.amX.aCz().zA();
    }

    public int zB() {
        return this.amX.aCz().zB();
    }

    public void dF(int n2) {
    }

    public int zC() {
        return this.amW.get();
    }

    public void zD() {
        this.amW.incrementAndGet();
    }

    public void zE() {
        this.amW.decrementAndGet();
    }
}

