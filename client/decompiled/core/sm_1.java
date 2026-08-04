/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from sM
 */
public final class sm_1 {
    private static final Logger a = Logger.getLogger(sm_1.class);
    private static final sm_1 alT = new sm_1();
    private final ArrayList alU = new ArrayList();
    private final ArrayList alV = new ArrayList();
    private final ArrayList alW = new ArrayList();

    private sm_1() {
        this.start();
    }

    public static sm_1 yS() {
        return alT;
    }

    public void start() {
        if (!acu_1.ara().isRunning()) {
            acu_1.ara().start();
        }
        Je.Vv().rd().bp("AdminListener");
    }

    public void a(String string, int n2, String string2, String string3, String string4, String string5) {
        try {
            Je.Vv().b(string2, string3, string4, string5);
            Je.Vv().o(string, n2);
        }
        catch (Exception exception) {
            a.error((Object)("Impossible d'initialiser l'administration du serveur : " + exception.getMessage()));
        }
    }

    public Iy cl(String string) {
        Iy iy = new Iy(string);
        this.alU.add(iy);
        ka_2 ka_22 = iy.rd();
        ka_22.bp(string);
        return iy;
    }

    public ml_2 cm(String string) {
        ml_2 ml_22 = new ml_2(string);
        this.alV.add(ml_22);
        ka_2 ka_22 = ml_22.rd();
        ka_22.bp(string);
        return ml_22;
    }

    public oc_1 a(String string, String string2, int n2, String string3, String string4, int n3, String string5) {
        oc_1 oc_12 = this.cn(string);
        if (oc_12 == null) {
            oc_12 = new oe_2(string, string2, string3, string4, n3, n2);
            oc_12.bp(string5);
            oc_12.initialize();
            this.alW.add(oc_12);
        } else {
            a.warn((Object)("Database " + string + " already defined -- ignoring"));
        }
        return oc_12;
    }

    public oc_1 b(String string, String string2, int n2, String string3, String string4, int n3, String string5) {
        oc_1 oc_12 = this.cn(string);
        if (oc_12 == null) {
            oc_12 = new dq_2(string, string2, string3, string4, n3, n2);
            oc_12.bp(string5);
            oc_12.initialize();
            this.alW.add(oc_12);
        } else {
            a.warn((Object)("Database " + string + " already defined -- ignoring"));
        }
        return oc_12;
    }

    public oc_1 a(String string, String string2, int n2, String string3, String string4, int n3, String string5, HashMap hashMap) {
        oc_1 oc_12 = this.cn(string);
        if (oc_12 == null) {
            oc_12 = new oe_2(string, string2, string3, string4, n3, n2);
            oc_12.bp(string5);
            oc_12.a(hashMap);
            oc_12.initialize();
            this.alW.add(oc_12);
        } else {
            a.warn((Object)("Database " + string + " already defined -- ignoring"));
        }
        return oc_12;
    }

    public oc_1 b(String string, String string2, int n2, String string3, String string4, int n3, String string5, HashMap hashMap) {
        oc_1 oc_12 = this.cn(string);
        if (oc_12 == null) {
            oc_12 = new dq_2(string, string2, string3, string4, n3, n2);
            oc_12.bp(string5);
            oc_12.a(hashMap);
            oc_12.initialize();
            this.alW.add(oc_12);
        } else {
            a.warn((Object)("Database " + string + " already defined -- ignoring"));
        }
        return oc_12;
    }

    public oc_1 cn(String string) {
        for (oc_1 oc_12 : this.alW) {
            if (!oc_12.tb().equals(string)) continue;
            return oc_12;
        }
        return null;
    }

    public ArrayList yT() {
        return this.alU;
    }

    public ArrayList yU() {
        return this.alV;
    }

    public ArrayList yV() {
        return this.alW;
    }
}

