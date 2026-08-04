/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;
import java.io.IOException;

/*
 * Renamed from WZ
 */
public class wz_0
implements Runnable {
    final /* synthetic */ byte[] bWe;
    final /* synthetic */ Anm bWf;

    public wz_0(Anm anm, byte[] byArray) {
        this.bWf = anm;
        this.bWe = byArray;
    }

    public void run() {
        try {
            this.bWf.b(acf.T(this.bWe));
        }
        catch (IOException iOException) {
            Anm.dT().error((Object)("Exception while loading ANM " + this), (Throwable)iOException);
        }
    }
}

