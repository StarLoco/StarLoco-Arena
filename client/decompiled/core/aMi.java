/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public final class aMi {
    protected static final Logger a = Logger.getLogger(aMi.class);
    private static final aMi dXC = new aMi();
    private ayy_0 bxI;
    private final HashMap dXD = new HashMap();
    private final HashMap dXE = new HashMap();
    private final HashMap dXF = new HashMap();

    private aMi() {
    }

    public static aMi aWT() {
        return dXC;
    }

    public void a(ayy_0 ayy_02) {
        this.bxI = ayy_02;
    }

    public final void a(JX jX, String string, String string2, String string3, String string4, agt_0 agt_02) {
        zy_1 zy_12 = new zy_1(this, jX, string, string2, string3, string4, null);
        this.dXD.put(zy_12, agt_02);
    }

    public final agt_0 a(JX jX, String string, String string2, String string3, String string4) {
        zy_1 zy_12 = new zy_1(this, jX, string, string2, string3, string4, null);
        return (agt_0)this.dXD.get(zy_12);
    }

    public final agt_0 b(JX jX, String string, String string2, String string3, String string4) {
        zy_1 zy_12 = new zy_1(this, jX, string, string2, string3, string4, null);
        return (agt_0)this.dXD.remove(zy_12);
    }

    public final void a(String string, axq_0 axq_02) {
        this.dXF.put(string, axq_02);
        this.bxI.a(axq_02);
    }

    public final void lF(String string) {
        axq_0 axq_02 = (axq_0)this.dXF.remove(string);
        if (axq_02 != null) {
            this.bxI.b(axq_02);
        } else {
            a.error((Object)("Aucun DialogUnloadListener n'est enregistr\u00e9 pour " + string));
        }
    }

    public final void a(String string, zh_0 zh_02) {
        this.dXE.put(string, zh_02);
        this.bxI.a(zh_02);
    }

    public final void lG(String string) {
        zh_0 zh_02 = (zh_0)this.dXE.remove(string);
        if (zh_02 != null) {
            this.bxI.b(zh_02);
        } else {
            a.error((Object)("Aucun DialogLoadListener n'est enregistr\u00e9 pour " + string));
        }
    }

    public void k(JX jX) {
        Set set = this.dXD.entrySet();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : set) {
            if (((zy_1)entry.getKey()).aoF() != jX) continue;
            arrayList.add(entry.getKey());
        }
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            this.dXD.remove(arrayList.get(j));
        }
    }

    public void lH(String string) {
        Set set = this.dXD.entrySet();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : set) {
            if (!((zy_1)entry.getKey()).aoD().equals(string)) continue;
            arrayList.add(entry.getKey());
        }
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            this.dXD.remove(arrayList.get(j));
        }
    }

    public void clean() {
        this.dXD.clear();
    }
}

