/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from ajC
 */
public final class ajc_0
implements nz_0 {
    protected static final Logger a = Logger.getLogger(nz_0.class);

    public void log(String string) {
        a.info((Object)string);
    }

    public void trace(String string) {
        a.info((Object)string);
    }

    public void err(String string) {
        a.error((Object)string);
    }

    public void b(String string, int n2) {
        a.info((Object)string);
    }

    public void setPrompt(String string) {
    }
}

