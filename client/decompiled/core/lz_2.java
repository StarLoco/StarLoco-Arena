/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

/*
 * Renamed from lZ
 */
class lz_2
implements Runnable {
    final /* synthetic */ rt_2 Iu;

    lz_2(rt_2 rt_22) {
        this.Iu = rt_22;
    }

    public void run() {
        if (apN.aDK().ayH()) {
            String string = null;
            switch (apN.aDK().vL()) {
                case 15: {
                    string = aon_0.aYc().getString("error.connection.saveInProgress");
                    break;
                }
                case 4: 
                case 5: {
                    string = aon_0.aYc().getString("connection.kicked");
                    break;
                }
                case 16: {
                    break;
                }
                default: {
                    string = aon_0.aYc().getString("connection.closed");
                }
            }
            DofusArenaClientInstance.yl().cleanUp();
            if (string != null) {
                add_1.aOG().a(string, 1090L, 2, 2);
            }
        } else if (!apN.aDK().aDM()) {
            add_1.aOG().a(aon_0.aYc().getString("logon.notConnectedToServer"), 1091L, 1, 1);
        }
        pm_0.ur().done();
    }
}

