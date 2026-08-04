/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.net.URL;
import org.apache.log4j.Logger;

public class aDU
extends anc_1 {
    public static final Logger a = Logger.getLogger(aDU.class);

    aDU() {
        try {
            String string = mu_1.rM().getString("applicationSkinPath");
            this.lM(string);
            this.setTitle("Arena");
        }
        catch (aih_2 aih_22) {
            a.error((Object)"Unable to create skinPath", (Throwable)aih_22);
        }
    }

    protected URL kT() {
        return this.getClass().getResource("icon.png");
    }
}

