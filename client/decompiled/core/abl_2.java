/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.chat.console.command.VicinityContentCommand;
import org.apache.log4j.Logger;

/*
 * Renamed from aBL
 */
public class abl_2
extends ml_0 {
    private static String dsy = "viewName";
    private static Logger a = Logger.getLogger(abl_2.class);
    private String m_name;
    public static final String[] ce = new String[]{dsy};
    public static final String[] oT = new String[ce.length + ml_0.ce.length];
    private static String dsz;
    private apk_0 bFW = new apk_0();

    public abl_2(int n2) {
        super(n2);
        this.bFW.dP(false);
        this.bFW.dO(false);
        this.bFW.a(this);
        this.bFW.a(new VicinityContentCommand());
        azs_0.aLV().g("chat.selectedPipe", aon_0.aYc().getString("chat.pipeName.vicinity"));
        if (mx_0.Ko != null) {
            this.bFW.f(mx_0.Ko);
        } else {
            a.error((Object)"Impossible de charger les commandes de chat !");
        }
    }

    public static String aNI() {
        return dsz;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public Object getFieldValue(String string) {
        if (string.equals(dsy)) {
            return this.m_name;
        }
        return super.getFieldValue(string);
    }

    protected String c(zc_0 zc_02) {
        rw_2 rw_22 = new rw_2();
        switch (zc_02.GH()) {
            case 6: {
                rw_22.wN().bM(ahi_1.dNB).bJ(zc_02.getMessage()).wO().bJ("\n");
                break;
            }
            case 5: {
                rw_22.wN().bM(ahi_1.dNC).bJ(zc_02.getMessage()).wO().bJ("\n");
                break;
            }
            case 2: {
                String string;
                rw_22.wN().bM(ahi_1.dND);
                if (zc_02.GI() != apN.aDK().Ln().getId()) {
                    string = "chat.privateMessageFrom";
                    dsz = zc_02.getSourceName();
                } else {
                    string = "chat.privateMessageTo";
                }
                rw_22.bJ(aon_0.aYc().getString(string, zc_02.getSourceName(), zc_02.getMessage()));
                rw_22.wO();
                rw_22.bJ(">\n");
                break;
            }
            case 4: {
                if (zc_02.getMessage() != null) {
                    if (zc_02.getMessage().length() > 0) {
                        rw_22.wN().bM(ahi_1.dNA).bJ(zc_02.getMessage()).wO().bJ("\n");
                        break;
                    }
                    rw_22.wN().bM(ahi_1.dNA).bJ("error").wO().bJ("\n");
                    break;
                }
                rw_22.wN().bM(ahi_1.dNA).bJ("error").wO().bJ("\n");
                break;
            }
            case 7: {
                rw_22.wN().bM(ahi_1.dNG).bJ("(").bJ(aon_0.aYc().getString("chat.pipeName.guild")).bJ(") ").bJ("<b>" + zc_02.getSourceName() + "</b>").bJ(" : ").bJ(zc_02.getMessage()).wO().bJ("\n");
                break;
            }
            case 8: {
                rw_22.wN().bM(ahi_1.dNH).bJ("(").bJ(aon_0.aYc().getString("chat.pipeName.trade")).bJ(") ").bJ(zc_02.getSourceName()).bJ(" : ").bJ(zc_02.getMessage()).wO().bJ("\n");
                break;
            }
            case 9: {
                rw_22.wN().bM(ahi_1.dNE).bJ(aon_0.aYc().getString("chat.pipeName.teammate")).bJ(") ").bJ(zc_02.getSourceName()).bJ(" : ").bJ(zc_02.getMessage()).wO().bJ("\n");
                break;
            }
            default: {
                rw_22.wN().bM(ahi_1.dNF).bJ("(").bJ(aon_0.aYc().getString("chat.pipeName.vicinity")).bJ(")  ").bJ(zc_02.getSourceName()).bJ(" : ").bJ(zc_02.getMessage()).wO().bJ("\n");
            }
        }
        return rw_22.wR();
    }

    public void err(String string) {
        String string2 = aon_0.aYc().getString("error.chat.malformedCommand");
        zc_0 zc_02 = new zc_0(string2);
        zc_02.eD(4);
        ql_1.acX().a(zc_02);
    }

    public void b(String string, int n2) {
    }

    public apk_0 acY() {
        return this.bFW;
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(ml_0.ce, 0, oT, ce.length, ml_0.ce.length);
    }
}

