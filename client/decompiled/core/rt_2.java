/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Rt
 */
public class rt_2
implements RE {
    private static Logger a = Logger.getLogger(rt_2.class);

    public boolean d(ka_2 ka_22, ait_0 ait_02) {
        a.info((Object)"onConnectionClose");
        ip_2.Un().a(new lz_2(this), 20L, 1);
        return true;
    }

    public boolean c(ka_2 ka_22, ait_0 ait_02) {
        a.info((Object)("onConnectionError isConnected=" + ait_02.isConnected() + " isRetrying=" + ait_02.Yu()));
        ip_2.Un().a(new mb_1(this, ait_02), 20L, 1);
        return true;
    }

    public boolean a(ka_2 ka_22) {
        a.info((Object)"onConnectionHandlerCreationError");
        return true;
    }

    public boolean c(ka_2 ka_22) {
        a.info((Object)"onConnectionHandlerInLoopError");
        return true;
    }

    public boolean b(ka_2 ka_22) {
        a.info((Object)"onConnectionHandlerInitializationError");
        return true;
    }

    public boolean b(ka_2 ka_22, ait_0 ait_02) {
        return true;
    }

    public boolean e(ka_2 ka_22, ait_0 ait_02) {
        a.debug((Object)"onConnectionRecovered");
        ip_2.Un().a(new lv_2(this), 20L, 1);
        return true;
    }

    public boolean a(ka_2 ka_22, ait_0 ait_02) {
        a.info((Object)"onNewConnection");
        ip_2.Un().a(new ly_0(this), 20L, 1);
        return true;
    }

    public void f(ka_2 ka_22, ait_0 ait_02) {
    }

    public void a(akm_2 akm_22) {
    }
}

