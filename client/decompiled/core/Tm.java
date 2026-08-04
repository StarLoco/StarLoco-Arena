/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public abstract class Tm
implements eo_2 {
    protected final String bMX = "name";
    protected final String bMY = "id";
    protected final String bMZ = "category";
    protected final String bNa = "group";
    protected final String bNb = "shortcut";
    protected final String bNc = "consoleCommand";
    protected final String bNd = "rebindAllowed";
    protected final String bNe = "alwaysValid";
    protected final String bNf = "altKey";
    protected final String bNg = "ctrlKey";
    protected final String bNh = "shiftKey";
    protected final String bNi = "keyCode";
    protected final String bNj = "keyRegExp";
    protected final String bNk = "params";
    protected final String bNl = "onKeyReleased";
    protected static final String bNm = "shortcuts";
    private static final Logger a = Logger.getLogger(Tm.class);
    protected ArrayList bNn = new ArrayList();
    private boolean bNo = true;
    private int bNp = -1;
    public static final String bNq = "binding";

    public void k(String string, boolean bl2) {
        for (zM zM2 : this.bNn) {
            if (!zM2.getName().equalsIgnoreCase(string)) continue;
            zM2.setEnabled(bl2);
            return;
        }
    }

    public void b(String string, String string2, boolean bl2) {
        for (zM zM2 : this.bNn) {
            if (!zM2.getName().equalsIgnoreCase(string)) continue;
            aex aex2 = zM2.dm(string2);
            if (aex2 == null) break;
            aex2.setEnabled(bl2);
            break;
        }
    }

    public void l(String string, boolean bl2) {
        aAN aAN2 = aAN.aMW();
        qf_1 qf_12 = aAN2.aCj();
        aAN2.iJ(string);
        aAN2.a(qf_12, new tf_2[0]);
        aAN2.close();
        if (bl2) {
            this.f(qf_12);
        } else {
            this.e(qf_12);
        }
    }

    public void e(qf_1 qf_12) {
        ArrayList arrayList = qf_12.bz("group");
        for (k_0 k_02 : arrayList) {
            ArrayList arrayList2;
            String string;
            zM zM2;
            if (k_02.f("name") == null) {
                a.error((Object)"Nom de groupe invalide dans le chargement des raccourcis");
            }
            if ((zM2 = this.fR(string = k_02.f("name").getStringValue())) == null) {
                zM2 = new zM(string);
                this.bNn.add(zM2);
            }
            if ((arrayList2 = k_02.d("shortcut")) == null) continue;
            String string2 = null;
            String string3 = null;
            String string4 = null;
            String string5 = null;
            String string6 = null;
            aex aex2 = null;
            for (k_0 k_03 : arrayList2) {
                if (k_03.f("consoleCommand") == null && k_03.f("id") == null) continue;
                string2 = k_03.f("consoleCommand") != null ? k_03.f("consoleCommand").getStringValue() : null;
                boolean bl2 = k_03.f("ctrlKey") != null && k_03.f("ctrlKey").getBooleanValue();
                boolean bl3 = k_03.f("altKey") != null && k_03.f("altKey").getBooleanValue();
                boolean bl4 = k_03.f("shiftKey") != null && k_03.f("shiftKey").getBooleanValue();
                boolean bl5 = k_03.f("alwaysValid") != null && k_03.f("alwaysValid").getBooleanValue();
                string4 = k_03.f("params") != null ? k_03.f("params").getStringValue() : null;
                boolean bl6 = k_03.f("onKeyReleased") != null && k_03.f("onKeyReleased").getBooleanValue();
                string5 = k_03.f("id") != null ? k_03.f("id").getStringValue() : null;
                aex2 = this.dm(string5);
                if (k_03.f("keyCode") != null) {
                    string3 = k_03.f("keyCode").getStringValue();
                    aex2 = new aex(string5, string3, string2, bl2, bl3, bl4, bl6, bl5, string4);
                } else if (k_03.f("keyRegExp") != null) {
                    string3 = k_03.f("keyRegExp").getStringValue();
                    aex2 = new aex(string5, Pattern.compile(string3), string2, bl2, bl3, bl4, bl6, bl5, string4);
                } else {
                    a.error((Object)"keyCode manquant dans le chargement des raccourcis");
                    aex2 = new aex(string5, -1, string2, false, false, false, bl6, bl5, string4);
                }
                string6 = k_03.f("category") != null ? k_03.f("category").getStringValue() : null;
                aex2.setCategory(string6);
                zM2.a(aex2);
            }
        }
    }

    public void f(qf_1 qf_12) {
        ArrayList arrayList = qf_12.bz("shortcut");
        String string = null;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        aex aex2 = null;
        if (arrayList == null) {
            return;
        }
        for (k_0 k_02 : arrayList) {
            if (k_02.f("id") == null) continue;
            string = k_02.f("consoleCommand") != null ? k_02.f("consoleCommand").getStringValue() : null;
            boolean bl2 = k_02.f("ctrlKey") != null && k_02.f("ctrlKey").getBooleanValue();
            boolean bl3 = k_02.f("altKey") != null && k_02.f("altKey").getBooleanValue();
            boolean bl4 = k_02.f("shiftKey") != null && k_02.f("shiftKey").getBooleanValue();
            boolean bl5 = k_02.f("alwaysValid") != null && k_02.f("alwaysValid").getBooleanValue();
            string3 = k_02.f("params") != null ? k_02.f("params").getStringValue() : null;
            boolean bl6 = k_02.f("onKeyReleased") != null && k_02.f("onKeyReleased").getBooleanValue();
            string5 = k_02.f("name") != null ? k_02.f("name").getStringValue() : null;
            string4 = k_02.f("id") != null ? k_02.f("id").getStringValue() : null;
            aex2 = this.dm(string4);
            if (k_02.f("keyCode") != null) {
                string2 = k_02.f("keyCode").getStringValue();
            }
            if (aex2 != null) {
                if (k_02.f("keyCode") != null) {
                    aex2.ke(k_02.f("keyCode").getIntValue());
                    aex2.kf(k_02.f("keyCode").getIntValue());
                }
                aex2.dl(bl2);
                aex2.dm(bl3);
                aex2.dn(bl4);
                aex2.do(bl5);
                if (k_02.f("params") == null) continue;
                aex2.hG(string3);
                continue;
            }
            if (string5 == null) continue;
            aex2 = new aex(string4, string2, string, bl2, bl3, bl4, bl6, bl5, string3);
            zM zM2 = this.fR(string5);
            if (zM2 == null) {
                a.warn((Object)("Le groupe de raccourcis de nom " + string5 + " est inconnu, on le cr\u00e9\u00e9"));
                zM2 = new zM(string5);
                this.bNn.add(zM2);
            }
            zM2.a(aex2);
        }
    }

    public boolean c(aex aex2) {
        for (zM zM2 : this.bNn) {
            if (zM2 == null || zM2.getName() == null || !zM2.getName().equalsIgnoreCase(aex2.CL())) continue;
            if (zM2.dm(aex2.getId()) == null) {
                a.error((Object)("On essai de modifier un raccourci inexistant dans le group " + zM2.getName()));
                return false;
            }
            return true;
        }
        a.warn((Object)("On essai de modifier un raccourci appartenant au groupe inexistant " + aex2.CL()));
        return false;
    }

    public aex dm(String string) {
        for (zM zM2 : this.bNn) {
            aex aex2 = zM2.dm(string);
            if (aex2 == null) continue;
            return aex2;
        }
        return null;
    }

    public void b(String string, int n2, int n3, String string2) {
        this.a(this.dm(string), n2, n3, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void a(aex aex2, int n2, int n3, String string) {
        if (!this.c(aex2)) {
            return;
        }
        aex2.ke(n2);
        aex2.kf(n2);
        aex2.kg(n3);
        aAN aAN2 = aAN.aMW();
        aNe aNe2 = aAN2.aMX();
        if (!vq_2.gn(string)) {
            aAN2.iK(string);
            aNe2.a(new PU(bNm, null));
            aAN2.a(aNe2, "");
            aAN2.close();
        }
        aAN2.iJ(string);
        aAN2.a(aNe2, new tf_2[0]);
        aAN2.close();
        ArrayList arrayList = aNe2.bz("shortcut");
        k_0 k_02 = null;
        if (arrayList != null) {
            for (k_0 k_03 : arrayList) {
                zo_2 zo_22;
                block20: {
                    if (k_03.f("id") == null || !k_03.f("id").getStringValue().equalsIgnoreCase(aex2.getId())) continue;
                    k_02 = k_03;
                    int n4 = aex2.auf();
                    if (k_03.f("keyCode") == null) {
                        if (n4 != -1) {
                            zo_22 = new zo_2("keyCode", String.valueOf(n4));
                            k_03.c(zo_22);
                            break block20;
                        } else {
                            a.warn((Object)"Le raccourci trouv\u00e9 ne poss\u00e8de pas de touche associ\u00e9e");
                            break;
                        }
                    }
                    k_03.f("keyCode").g(n2);
                }
                if (aex2.aui()) {
                    if (k_03.f("shiftKey") != null) {
                        k_03.f("shiftKey").b(true);
                    } else {
                        zo_22 = new zo_2("shiftKey", String.valueOf(true));
                        k_03.c(zo_22);
                    }
                } else if (k_03.f("shiftKey") != null) {
                    k_03.d(k_03.f("shiftKey"));
                }
                if (aex2.auh()) {
                    if (k_03.f("altKey") != null) {
                        k_03.f("altKey").b(true);
                    } else {
                        zo_22 = new zo_2("altKey", String.valueOf(true));
                        k_03.c(zo_22);
                    }
                } else if (k_03.f("altKey") != null) {
                    k_03.d(k_03.f("altKey"));
                }
                if (aex2.aug()) {
                    if (k_03.f("ctrlKey") != null) {
                        k_03.f("ctrlKey").b(true);
                        continue;
                    }
                    zo_22 = new zo_2("ctrlKey", String.valueOf(true));
                    k_03.c(zo_22);
                    continue;
                }
                if (k_03.f("ctrlKey") == null) continue;
                k_03.d(k_03.f("ctrlKey"));
            }
        }
        if (k_02 == null) {
            this.a(aex2, aNe2, false, false);
        }
        aAN2.iK(string);
        aAN2.b(aNe2);
        aAN2.close();
    }

    public void a(aex aex2, String string, String string2) {
        if (this.c(aex2)) {
            return;
        }
        zM zM2 = this.fR(string);
        aAN aAN2 = aAN.aMW();
        aNe aNe2 = aAN2.aMX();
        if (!vq_2.gn(string2)) {
            aAN2.iK(string2);
            aNe2.a(new PU(bNm, null));
            aAN2.a(aNe2, "");
            aAN2.close();
        }
        aAN2.iJ(string2);
        aAN2.a(aNe2, new tf_2[0]);
        aAN2.close();
        zM2.a(aex2);
        this.a(aex2, aNe2, true, true);
        aAN2.iK(string2);
        aAN2.b(aNe2);
        aAN2.close();
    }

    public void a(aex aex2, String string) {
        if (!this.c(aex2)) {
            return;
        }
        zM zM2 = this.fR(aex2.CL());
        aAN aAN2 = aAN.aMW();
        aNe aNe2 = aAN2.aMX();
        if (!vq_2.gn(string)) {
            aAN2.iK(string);
            aNe2.a(new PU(bNm, null));
            aAN2.a(aNe2, "");
            aAN2.close();
        }
        aAN2.iJ(string);
        aAN2.a(aNe2, new tf_2[0]);
        aAN2.close();
        ArrayList arrayList = aNe2.bz("shortcut");
        if (arrayList == null) {
            a.error((Object)("impossible de supprimer le raccourci du document " + string + "qui semble vide"));
            return;
        }
        for (k_0 k_02 : arrayList) {
            if (k_02.f("id") == null || !k_02.f("id").getStringValue().equalsIgnoreCase(aex2.getId())) continue;
            aNe2.aXo().b(k_02);
        }
        zM2.b(aex2);
        aAN2.iK(string);
        aAN2.b(aNe2);
        aAN2.close();
    }

    public void a(aex aex2, aNe aNe2, boolean bl2, boolean bl3) {
        PU pU = new PU("shortcut", null);
        if (aex2.getId() != null && aex2.getId().length() > 0) {
            pU.c(new zo_2("id", aex2.getId()));
        }
        if (bl3) {
            if (aex2.CL() != null && aex2.CL().length() > 0) {
                pU.c(new zo_2("name", aex2.CL()));
            }
            if (aex2.aue() != null && aex2.aue().length() > 0) {
                pU.c(new zo_2("consoleCommand", aex2.aue()));
            }
        }
        if (bl2 && aex2.aum() != null && aex2.aum().length() > 0) {
            pU.c(new zo_2("params", aex2.aum().replaceAll("\"", "&quot;")));
        }
        if (!bl2) {
            if (aex2.auh()) {
                pU.c(new zo_2("altKey", "true"));
            }
            if (aex2.aug()) {
                pU.c(new zo_2("ctrlKey", "true"));
            }
            if (aex2.aui()) {
                pU.c(new zo_2("shiftKey", "true"));
            }
            pU.c(new zo_2("keyCode", String.valueOf(aex2.auf())));
        }
        aNe2.aXo().a(pU);
    }

    private k_0 a(String string, k_0 k_02) {
        PU pU = new PU("group", null);
        if (string != null && string.length() > 0) {
            pU.c(new zo_2("name", string));
            k_02.a(pU);
        }
        k_02.a(pU);
        return pU;
    }

    public void b(aex aex2, String string, String string2) {
        if (!this.c(aex2)) {
            return;
        }
        aex2.hG(string);
        aAN aAN2 = aAN.aMW();
        aNe aNe2 = aAN2.aMX();
        if (!vq_2.gn(string2)) {
            aAN2.iK(string2);
            aNe2.a(new PU(bNm, null));
            aAN2.a(aNe2, "");
            aAN2.close();
        }
        aAN2.iJ(string2);
        aAN2.a(aNe2, new tf_2[0]);
        aAN2.close();
        ArrayList arrayList = aNe2.bz("group");
        ArrayList arrayList2 = aNe2.aXo().d("shortcut");
        k_0 k_02 = null;
        if (arrayList2 != null) {
            for (k_0 k_03 : arrayList2) {
                if (k_03.f("id") == null || !k_03.f("id").getStringValue().equalsIgnoreCase(aex2.getId())) continue;
                k_02 = k_03;
                if (k_03.f("params") == null) {
                    k_03.c(new zo_2("params", string));
                    continue;
                }
                k_03.f("params").b(string);
            }
        }
        if (k_02 == null) {
            this.a(aex2, aNe2, true, false);
        }
        aAN2.iK(string2);
        aAN2.b(aNe2);
    }

    public void co(boolean bl2) {
        this.bNo = bl2;
    }

    public boolean afU() {
        return this.bNo;
    }

    public void a(String string, hz_0 hz_02) {
        for (zM zM2 : this.bNn) {
            if (!zM2.isEnabled()) continue;
            for (aex aex2 : zM2.GM()) {
                if (aex2.getId() == null || !aex2.getId().equalsIgnoreCase(string)) continue;
                aex2.a(hz_02);
            }
        }
    }

    public boolean d(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 17 || keyEvent.getKeyCode() == 16 || keyEvent.getKeyCode() == 18) {
            return false;
        }
        for (zM zM2 : this.bNn) {
            if (!zM2.isEnabled()) continue;
            for (aex aex2 : zM2.GM()) {
                if (!aex2.a(keyEvent.getKeyCode(), keyEvent.getKeyChar()) || !aex2.auj()) continue;
                return true;
            }
        }
        return false;
    }

    public boolean c(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 17 || keyEvent.getKeyCode() == 16 || keyEvent.getKeyCode() == 18) {
            return false;
        }
        this.bNp = -1;
        for (zM zM2 : this.bNn) {
            if (!zM2.isEnabled()) continue;
            for (aex aex2 : zM2.GM()) {
                boolean bl2 = aex2.isEnabled() && (this.bNo || aex2.auj());
                if (!bl2 || !aex2.aun() || !aex2.a(keyEvent.getKeyCode(), keyEvent.getKeyChar()) || aex2.aug() != keyEvent.isControlDown() || aex2.auh() != keyEvent.isAltDown() || aex2.aui() != keyEvent.isShiftDown() || !this.a(aex2, keyEvent)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean b(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 17 || keyEvent.getKeyCode() == 16 || keyEvent.getKeyCode() == 18 || this.bNp == keyEvent.getKeyCode()) {
            return false;
        }
        this.bNp = keyEvent.getKeyCode();
        for (zM zM2 : this.bNn) {
            if (!zM2.isEnabled()) continue;
            for (aex aex2 : zM2.GM()) {
                boolean bl2 = aex2.isEnabled() && (this.bNo || aex2.auj());
                if (!bl2 || aex2.aun() || !aex2.a(keyEvent.getKeyCode(), keyEvent.getKeyChar()) || aex2.aug() != keyEvent.isControlDown() || aex2.auh() != keyEvent.isAltDown() || aex2.aui() != keyEvent.isShiftDown() || !this.a(aex2, keyEvent)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean a(KeyEvent keyEvent) {
        return false;
    }

    public abstract boolean a(aex var1, KeyEvent var2);

    public ArrayList afV() {
        return this.bNn;
    }

    public zM fR(String string) {
        for (zM zM2 : this.bNn) {
            if (!zM2.getName().equalsIgnoreCase(string)) continue;
            return zM2;
        }
        return null;
    }

    public void hS(int n2) {
        this.bNp = n2;
    }
}

