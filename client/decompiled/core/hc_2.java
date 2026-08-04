/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.event.KeyEvent;
import org.apache.log4j.Logger;

/*
 * Renamed from hC
 */
public final class hc_2
extends Tm {
    private static hc_2 wa = new hc_2();
    private static final Logger a = Logger.getLogger(hc_2.class);

    public static hc_2 kI() {
        return wa;
    }

    public boolean a(aex aex2, KeyEvent keyEvent) {
        if (aex2.aue() != null) {
            String string = aex2.aue().replace("${keyCode}", Integer.toString(keyEvent.getKeyCode())).replace("${keyChar}", Character.toString(keyEvent.getKeyChar()));
            if (aex2.aum() != null) {
                string = string + aex2.aum();
            }
            apk_0.aDz().v(string, false);
            return true;
        }
        if (aex2.auk() != null) {
            aex2.auk().run();
            return true;
        }
        return false;
    }
}

