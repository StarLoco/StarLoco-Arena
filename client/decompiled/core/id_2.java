/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/*
 * Renamed from Id
 */
public class id_2
implements cf_1 {
    private String name;
    private String bgi = "";
    private String bgj = "";
    private List bgk = null;
    private List aUS = new ArrayList();
    private axc_0 pW = axc_0.diY;
    private UI hL;
    private String description = null;

    public id_2() {
    }

    public id_2(id_2 id_22) {
        this.name = id_22.name;
        this.bgi = id_22.bgi;
        this.bgj = id_22.bgj;
        this.bgk = id_22.bgk;
        this.pW = id_22.pW;
        this.hL = id_22.hL;
        this.description = id_22.description;
        this.aUS = id_22.aUS;
    }

    public void l(UI uI) {
        this.hL = uI;
    }

    public UI TP() {
        return this.hL;
    }

    public void a(axc_0 axc_02) {
        this.pW = axc_02;
    }

    public axc_0 hW() {
        return this.pW;
    }

    public void eE(String string) {
        if (string.length() > 0) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ",", true);
            while (stringTokenizer.hasMoreTokens()) {
                String string2 = stringTokenizer.nextToken().trim();
                if ("".equals(string2) || ",".equals(string2)) {
                    throw new eq_2("Syntax Error: depends attribute of target \"" + this.getName() + "\" has an empty string as dependency.");
                }
                this.eF(string2);
                if (!stringTokenizer.hasMoreTokens()) continue;
                string2 = stringTokenizer.nextToken();
                if (stringTokenizer.hasMoreTokens() && ",".equals(string2)) continue;
                throw new eq_2("Syntax Error: Depend attribute for target \"" + this.getName() + "\" ends with a , character");
            }
        }
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public void a(dm_1 dm_12) {
        this.aUS.add(dm_12);
    }

    public void d(fy_2 fy_22) {
        this.aUS.add(fy_22);
    }

    public dm_1[] TQ() {
        ArrayList arrayList = new ArrayList(this.aUS.size());
        Iterator iterator = this.aUS.iterator();
        while (iterator.hasNext()) {
            Object e = iterator.next();
            if (!(e instanceof dm_1)) continue;
            arrayList.add(e);
        }
        return arrayList.toArray(new dm_1[arrayList.size()]);
    }

    public void eF(String string) {
        if (this.bgk == null) {
            this.bgk = new ArrayList(2);
        }
        this.bgk.add(string);
    }

    public Enumeration TR() {
        return this.bgk != null ? Collections.enumeration(this.bgk) : new jj_2();
    }

    public boolean eG(String string) {
        UI uI = this.TP();
        Hashtable hashtable = uI == null ? null : uI.ahn();
        return uI != null && uI.a(this.getName(), hashtable, false).contains(hashtable.get(string));
    }

    public void w(String string) {
        this.bgi = string == null ? "" : string;
    }

    public String TS() {
        return "".equals(this.bgi) ? null : this.bgi;
    }

    public void x(String string) {
        this.bgj = string == null ? "" : string;
    }

    public String TT() {
        return "".equals(this.bgj) ? null : this.bgj;
    }

    public void setDescription(String string) {
        this.description = string;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return this.name;
    }

    public void execute() {
        if (this.TV() && this.TW()) {
            for (int j = 0; j < this.aUS.size(); ++j) {
                Object object;
                Object e = this.aUS.get(j);
                if (e instanceof dm_1) {
                    object = (dm_1)e;
                    ((dm_1)object).perform();
                    continue;
                }
                object = (fy_2)e;
                ((fy_2)object).m(this.hL);
            }
        } else if (!this.TV()) {
            this.hL.a(this, "Skipped because property '" + this.hL.fZ(this.bgi) + "' not set.", 3);
        } else {
            this.hL.a(this, "Skipped because property '" + this.hL.fZ(this.bgj) + "' set.", 3);
        }
    }

    public final void TU() {
        RuntimeException runtimeException = null;
        this.hL.f(this);
        try {
            this.execute();
        }
        catch (RuntimeException runtimeException2) {
            runtimeException = runtimeException2;
            throw runtimeException2;
        }
        finally {
            this.hL.a(this, (Throwable)runtimeException);
        }
    }

    void a(dm_1 dm_12, fy_2 fy_22) {
        int n2;
        while ((n2 = this.aUS.indexOf(dm_12)) >= 0) {
            this.aUS.set(n2, fy_22);
        }
    }

    void a(dm_1 dm_12, dm_1 dm_13) {
        int n2;
        while ((n2 = this.aUS.indexOf(dm_12)) >= 0) {
            this.aUS.set(n2, dm_13);
        }
    }

    private boolean TV() {
        if ("".equals(this.bgi)) {
            return true;
        }
        String string = this.hL.fZ(this.bgi);
        return this.hL.getProperty(string) != null;
    }

    private boolean TW() {
        if ("".equals(this.bgj)) {
            return true;
        }
        String string = this.hL.fZ(this.bgj);
        return this.hL.getProperty(string) == null;
    }
}

