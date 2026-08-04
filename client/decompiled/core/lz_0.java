/*
 * Decompiled with CFR 0.152.
 */
import java.util.concurrent.TimeUnit;
import javax.media.opengl.Threading;

/*
 * Renamed from LZ
 */
class lz_0
extends nj_0 {
    final /* synthetic */ acu_1 bsR;

    lz_0(acu_1 acu_12) {
        this.bsR = acu_12;
        super((mc_0)null);
    }

    public void run() {
        this.setName("Worker (in OpenGL thread)");
        acu_1.sP().info((Object)"Worker running (in OpenGL thread)");
        this.f(true);
        while (this.isRunning()) {
            try {
                long l2;
                if (!Threading.isOpenGLThread()) {
                    Threading.invokeOnOpenGLThread(acu_1.a(this.bsR));
                } else {
                    acu_1.a(this.bsR).run();
                }
                if ((l2 = ip_2.Un().Uq()) <= 0L) continue;
                this.bsR.cjY.lock();
                this.bsR.bXd.await(l2, TimeUnit.MILLISECONDS);
                this.bsR.cjY.unlock();
            }
            catch (Throwable throwable) {
                acu_1.a(this.bsR, throwable);
            }
        }
        acu_1.sP().info((Object)"Worker stopped");
        acu_1.a(this.bsR, null);
    }
}

