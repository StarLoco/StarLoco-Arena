/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.log4j.Logger;

public class UH {
    String bQn;
    private final float[] bQo = new float[4];
    private int bQp;
    private int bQq;
    private int bQr;
    private static final ano_0 bQs = new ano_0();
    private static final Logger a = Logger.getLogger(adq.class);
    private static final ReentrantReadWriteLock aNo = new ReentrantReadWriteLock();
    private static final Lock aNp = aNo.readLock();
    private static final Lock aNq = aNo.writeLock();

    private UH(String string) {
        this.bQn = string;
        this.bQo[0] = Float.MAX_VALUE;
    }

    public void a(gd_1 gd_12) {
        switch (gd_12) {
            case sc: {
                ++this.bQp;
                break;
            }
            case sd: {
                ++this.bQq;
                break;
            }
            case se: {
                ++this.bQr;
            }
        }
    }

    public void ih(int n2) {
        if ((float)n2 < this.bQo[0]) {
            this.bQo[0] = n2;
        }
        if ((float)n2 > this.bQo[1]) {
            this.bQo[1] = n2;
        }
        this.bQo[2] = this.bQo[2] + (float)n2;
        this.bQo[3] = this.bQo[3] + 1.0f;
    }

    public String agP() {
        return this.bQn;
    }

    public float ii(int n2) {
        float f = this.bQo[n2];
        if (n2 <= 1) {
            this.bQo[0] = Float.MAX_VALUE;
            this.bQo[1] = 0.0f;
        }
        return f;
    }

    public int agQ() {
        return this.bQp;
    }

    public int agR() {
        return this.bQq;
    }

    public int agS() {
        return this.bQr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ArrayList agT() {
        ArrayList arrayList = new ArrayList();
        aNp.lock();
        try {
            if (!bQs.isEmpty()) {
                bQs.b(new eB(arrayList));
            }
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        finally {
            aNp.unlock();
        }
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UH m(String string, boolean bl2) {
        aNp.lock();
        UH uH = null;
        try {
            uH = (UH)bQs.get(string);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        finally {
            aNp.unlock();
        }
        if (uH == null && bl2) {
            uH = new UH(string);
            aNq.lock();
            try {
                bQs.put(string, uH);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
            finally {
                aNq.unlock();
            }
        }
        return uH;
    }
}

