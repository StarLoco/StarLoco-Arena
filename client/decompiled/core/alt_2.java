/*
 * Decompiled with CFR 0.152.
 */
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * Renamed from aLT
 */
public class alt_2
extends ym_0 {
    private final Queue dXg = new ConcurrentLinkedQueue();

    public alt_2(sq_1 sq_12) {
        super(sq_12);
    }

    private void aWL() {
        if (this.dXg != null) {
            Object e;
            while ((e = this.dXg.poll()) != null) {
                try {
                    super.af(e);
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
            }
        }
    }

    public synchronized Object adr() {
        this.aWL();
        return super.adr();
    }

    public void af(Object object) {
        if (this.dXg != null) {
            this.dXg.offer(object);
        }
    }
}

