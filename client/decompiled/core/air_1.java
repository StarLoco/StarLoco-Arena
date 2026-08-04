/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Stack;
import org.apache.log4j.Logger;

/*
 * Renamed from aiR
 */
public abstract class air_1
implements JG {
    private static final Logger a = Logger.getLogger(air_1.class);
    private static final boolean czb = true;
    protected acl_0 DG;
    protected boolean czc = false;
    private int czd = 0;
    private int cze = 0;
    protected air_1 czf;
    protected ArrayList czg = null;
    protected boolean czh = false;
    protected boolean czi = false;
    protected boolean czj = false;
    private int czk = -1;
    private int czl = -1;
    private int czm = -1;
    protected boolean czn = false;
    private boolean czo = false;
    private boolean czp = false;
    public static final String czq = "include";
    public static final String czr = "includeId";
    static final int[] czs = new int[]{"include".hashCode(), "includeId".hashCode(), "templateId".hashCode(), "atlas".hashCode()};

    public void b(afl_0 afl_02) {
        if (this.czg == null) {
            this.czg = new ArrayList(1);
        }
        if (!this.czg.contains(afl_02)) {
            this.czg.add(afl_02);
        }
    }

    public void e(afl_0 afl_02) {
        if (this.czg != null) {
            this.czg.remove(afl_02);
        }
    }

    public void a(amx_1 amx_12) {
        amx_12.setBasicParent(this);
    }

    public void a(na_1 na_12) {
        na_12.setBasicParent(this);
    }

    public void b(amx_1 amx_12) {
        this.a(amx_12);
    }

    public void f(na_1 na_12) {
        this.a(na_12);
    }

    public void j(air_1 air_12) {
        air_12.setBasicParent(this);
        switch (air_12.getElementType()) {
            case aTF: {
                this.f((na_1)air_12);
                break;
            }
            case aTG: {
                this.b((amx_1)air_12);
                break;
            }
        }
    }

    public int getLastPreProcessFrame() {
        return this.czk;
    }

    public void setLastPreProcessFrame(int n2) {
        this.czk = n2;
    }

    public int getLastMiddleProcessFrame() {
        return this.czl;
    }

    public void setLastMiddleProcessFrame(int n2) {
        this.czl = n2;
    }

    public int getLastPostProcessFrame() {
        return this.czm;
    }

    public void setLastPostProcessFrame(int n2) {
        this.czm = n2;
    }

    public void setNeedsToPreProcess() {
        if (!(this.czn || !bd.ce().cg() && this.czh)) {
            bd.ce().e(this);
            this.czh = true;
        }
    }

    public void setNeedsToMiddleProcess() {
        if (!(this.czn || !bd.ce().cg() && this.czi)) {
            bd.ce().f(this);
            this.czi = true;
        }
    }

    public void setNeedsToPostProcess() {
        if (!(this.czn || !bd.ce().cg() && this.czj)) {
            bd.ce().g(this);
            this.czj = true;
        }
    }

    public na_1 getEventDispatcherParent() {
        if (this.czf == null) {
            return null;
        }
        if (this.czf instanceof na_1) {
            return (na_1)this.czf;
        }
        return this.czf.getEventDispatcherParent();
    }

    public adg_2 getWidgetParent() {
        if (this.czf == null) {
            return null;
        }
        if (this.czf instanceof adg_2) {
            return (adg_2)this.czf;
        }
        return this.czf.getWidgetParent();
    }

    public void setBasicParent(air_1 air_12) {
        this.czf = air_12;
    }

    public air_1 getBasicParent() {
        return this.czf;
    }

    public boolean isUnloading() {
        return this.czc;
    }

    public abstract eh_0 getElementType();

    public static String getTag(Class clazz) {
        try {
            return (String)clazz.getDeclaredField("TAG").get(null);
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors de la r\u00e9cup\u00e9ration du tag de " + clazz.getSimpleName()));
            return null;
        }
    }

    public String getTag() {
        return "";
    }

    public int getTreeDepth() {
        return this.czf.getTreeDepth();
    }

    public int getTreePosition() {
        return this.czf.getTreePosition();
    }

    public boolean isInTreeDepthManager() {
        return this.czo;
    }

    public void setInTreeDepthManager(boolean bl2) {
        this.czo = bl2;
    }

    public boolean isAddedNextInTreeDepthManager() {
        return this.czp;
    }

    public void setAddedNextInTreeDepthManager(boolean bl2) {
        this.czp = bl2;
    }

    public void setIsATemplate(boolean bl2) {
        this.czn |= bl2;
    }

    public boolean isATemplate() {
        return this.czn;
    }

    public boolean setXMLAttribute(String string, String string2) {
        int n2 = string.hashCode();
        for (int n3 : czs) {
            if (n2 != n3) continue;
            return true;
        }
        if (!this.setXMLAttribute(n2, string2, if_1.UG())) {
            a.debug((Object)new StringBuilder("Impossible de trouver l'attribut ").append(string).append(" pour ").append(this.getClass().getSimpleName()));
            return false;
        }
        return true;
    }

    public boolean V(String string, String string2) {
        if (!this.a(string.hashCode(), string2, if_1.UG())) {
            a.debug((Object)new StringBuilder("Impossible de trouver l'attribut ").append(string).append(" pour ").append(this.getClass().getSimpleName()));
            return false;
        }
        return true;
    }

    public boolean W(String string, String string2) {
        if (!this.b(string.hashCode(), string2, if_1.UG())) {
            a.debug((Object)new StringBuilder("Impossible de trouver l'attribut ").append(string).append(" pour ").append(this.getClass().getSimpleName()));
            return false;
        }
        return true;
    }

    public boolean setPropertyAttribute(String string, Object object) {
        if (!this.setPropertyAttribute(string.hashCode(), object)) {
            a.debug((Object)new StringBuilder("Impossible de trouver l'attribut ").append(string).append(" pour ").append(this.getClass().getSimpleName()));
            return false;
        }
        return true;
    }

    public boolean q(String string, Object object) {
        if (!this.d(string.hashCode(), object)) {
            a.debug((Object)new StringBuilder("Impossible de trouver l'attribut ").append(string).append(" pour ").append(this.getClass().getSimpleName()));
            return false;
        }
        return true;
    }

    public boolean r(String string, Object object) {
        if (!this.g(string.hashCode(), object)) {
            a.debug((Object)new StringBuilder("Impossible de trouver l'attribut ").append(string).append(" pour ").append(this.getClass().getSimpleName()));
            return false;
        }
        return true;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        return false;
    }

    public boolean a(int n2, String string, if_1 if_12) {
        return false;
    }

    public boolean b(int n2, String string, if_1 if_12) {
        return false;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return false;
    }

    public boolean d(int n2, Object object) {
        return false;
    }

    public boolean g(int n2, Object object) {
        return false;
    }

    public boolean cc(int n2) {
        return false;
    }

    public boolean gU(int n2) {
        return false;
    }

    public boolean cb(int n2) {
        return false;
    }

    public void kW(int n2) {
        if (this.czc) {
            return;
        }
        if (this.czh) {
            this.czh = false;
            if (agV.awI()) {
                boolean bl2 = this.cc(n2);
                this.czh |= bl2;
            }
            if (this.czh) {
                bd.ce().b(this);
            }
        }
    }

    public void kX(int n2) {
        if (this.czc) {
            return;
        }
        if (this.czi) {
            this.czi = false;
            if (agV.awI()) {
                boolean bl2 = this.gU(n2);
                this.czi |= bl2;
            }
            if (this.czi) {
                bd.ce().c(this);
            }
        }
    }

    public void kY(int n2) {
        if (this.czc) {
            return;
        }
        if (this.czj) {
            this.czj = false;
            if (agV.awI()) {
                boolean bl2 = this.cb(n2);
                this.czj |= bl2;
            }
            if (this.czj) {
                bd.ce().d(this);
            }
        }
    }

    public void release() {
        try {
            if (this.DG != null) {
                this.DG.af(this);
            } else {
                this.j();
            }
        }
        catch (Exception exception) {
            a.warn((Object)"Probl\u00e8me lors du retour dans un pool", (Throwable)exception);
            this.j();
        }
    }

    public void Ak() {
    }

    public void Aj() {
    }

    public void j() {
        this.czc = true;
        if (this.czd >= this.cze) {
            a.error((Object)("Le nombre de checkIn ne correspond pas aux nombre de checkOut " + this.getClass().getSimpleName()));
        }
        ++this.czd;
        this.czf = null;
        if (this.czg != null) {
            for (int j = this.czg.size() - 1; j >= 0; --j) {
                ((afl_0)this.czg.get(j)).i(this);
            }
            this.czg = null;
        }
    }

    public void b() {
        this.czc = false;
        if (this.cze != this.czd) {
            a.error((Object)("Le nombre de checkOut ne correspond pas aux nombre de checkIn " + this.getClass().getSimpleName()));
        }
        ++this.cze;
        this.czh = false;
        this.czi = false;
        this.czj = false;
        this.czk = -1;
        this.czl = -1;
        this.czm = -1;
    }

    public void a(air_1 air_12) {
    }

    public void s(k_0 k_02) {
        ArrayList arrayList = k_02.al();
        int n2 = arrayList != null ? arrayList.size() : 0;
        aLH aLH2 = ye_2.amJ().ij(k_02.getName());
        for (int j = 0; j < n2; ++j) {
            String string;
            k_0 k_03 = (k_0)arrayList.get(j);
            String string2 = k_03.getName();
            if (this.setXMLAttribute(string2, string = k_03.getStringValue())) continue;
            DS.a(this, aLH2, string2, string);
        }
        this.Ak();
    }

    public void a(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
    }

    public void c(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
    }

    public void d(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
    }

    public void b(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
    }

    public air_1 getNewElement(String string, na_1 na_12, Stack stack, afq_1 afq_12) {
        aLH aLH2 = ye_2.amJ().ij(string);
        if (aLH2 == null) {
            a.error((Object)("Tag Inconnu : " + string));
            return null;
        }
        try {
            return (air_1)aLH2.newInstance();
        }
        catch (Exception exception) {
            a.error((Object)new StringBuilder("Erreur lors de l'instanciation du tag ").append(string).append("."));
            return null;
        }
    }

    public void e(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
        ArrayList arrayList = k_02.getChildren();
        int n2 = arrayList.size();
        na_1 na_13 = this.getElementType() == eh_0.aTF ? (na_1)this : na_12;
        this.d(k_02, na_12, stack, afq_12);
        for (int j = 0; j < n2; ++j) {
            air_1 air_12;
            k_0 k_03 = (k_0)arrayList.get(j);
            String string = k_03.getName();
            if (string.equals("#text") || string.equals("#comment") || (air_12 = this.getNewElement(string, na_13, stack, afq_12)) == null) continue;
            air_12.a(k_03, na_12, stack, afq_12);
            air_12.s(k_03);
            air_12.c(k_03, na_12, stack, afq_12);
            this.j(air_12);
            if (k_03.f(czq) != null) {
                String string2 = k_03.f(czr).getStringValue();
                if (string2 == null) {
                    a.error((Object)"Pas d'id pour le tag Include, impossible de l'ajouter");
                    continue;
                }
                aji_1 aji_12 = (aji_1)stack.peek();
                String string3 = aji_12 != null ? aji_12.getId() : "";
                aji_1 aji_13 = afq_12.lf(string3 + "." + string2);
                aji_13.c(aji_12);
                stack.push(aji_13);
                air_12.e(k_03, na_13, stack, afq_12);
                stack.pop();
                continue;
            }
            air_12.e(k_03, na_13, stack, afq_12);
        }
        this.Aj();
        this.b(k_02, na_12, stack, afq_12);
    }
}

