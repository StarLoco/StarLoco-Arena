/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from abH
 */
public class abh_2
extends dm_1
implements cf_1 {
    public static final String TAG = "antlib";
    private ClassLoader civ;
    private String uri = "";
    private List ciw = new ArrayList();
    static Class cix;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static abh_2 a(UI uI, URL uRL, String string) {
        try {
            uRL.openConnection().connect();
        }
        catch (IOException iOException) {
            throw new eq_2("Unable to find " + uRL, iOException);
        }
        abm_1 abm_12 = abm_1.D(uI);
        abm_12.hs(string);
        try {
            amj_2 amj_22 = new amj_2();
            rs_0 rs_02 = amj_22.a(uI, uRL);
            if (!rs_02.getTag().equals(TAG)) {
                throw new eq_2("Unexpected tag " + rs_02.getTag() + " expecting " + TAG, rs_02.hW());
            }
            abh_2 abh_22 = new abh_2();
            abh_22.l(uI);
            abh_22.a(rs_02.hW());
            abh_22.cW(TAG);
            abh_22.init();
            rs_02.ah(abh_22);
            abh_2 abh_23 = abh_22;
            return abh_23;
        }
        finally {
            abm_12.apV();
        }
    }

    protected void setClassLoader(ClassLoader classLoader) {
        this.civ = classLoader;
    }

    protected void setURI(String string) {
        this.uri = string;
    }

    private ClassLoader getClassLoader() {
        if (this.civ == null) {
            this.civ = (cix == null ? (cix = abh_2.a("abH")) : cix).getClassLoader();
        }
        return this.civ;
    }

    public void a(dm_1 dm_12) {
        this.ciw.add(dm_12);
    }

    public void execute() {
        Iterator iterator = this.ciw.iterator();
        while (iterator.hasNext()) {
            rs_0 rs_02 = (rs_0)iterator.next();
            this.a(rs_02.hW());
            rs_02.LH();
            Object object = rs_02.adO();
            if (object == null) continue;
            if (!(object instanceof aur_0)) {
                throw new eq_2("Invalid task in antlib " + rs_02.getTag() + " " + object.getClass() + " does not " + "extend org.apache.tools.ant.taskdefs.AntlibDefinition");
            }
            aur_0 aur_02 = (aur_0)object;
            aur_02.setURI(this.uri);
            aur_02.g(this.getClassLoader());
            aur_02.init();
            aur_02.execute();
        }
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

