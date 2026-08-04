/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from XE
 */
public class xe_2
extends ael_2 {
    private static final Logger a = Logger.getLogger(xe_2.class);
    private final ArrayList bYT = new ArrayList();

    public boolean a(byte[] byArray) {
        if (byArray == null || !this.a(byArray.length, 1, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(wa_1.P(byArray));
        int n2 = byteBuffer.getInt();
        this.bYT.clear();
        for (int j = 0; j < n2; ++j) {
            hy_0 hy_02;
            byte by = byteBuffer.get();
            if (by == 1) {
                hy_02 = new aez_0();
                if (!hy_02.b(byteBuffer, 3179)) continue;
                this.bYT.add(hy_02);
                continue;
            }
            if (by == 2) {
                hy_02 = new ee_2();
                if (!((gn_0)((Object)hy_02)).b(byteBuffer)) continue;
                this.bYT.add(hy_02);
                continue;
            }
            a.error((Object)("Type d'acteur inconnu : ActorType \u00e9gal \u00e0 " + by + "."));
        }
        return true;
    }

    public int getId() {
        return 4096;
    }

    public Iterable alm() {
        return this.bYT;
    }
}

