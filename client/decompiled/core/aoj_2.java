/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from aoJ
 */
public class aoj_2 {
    private static final Logger a = Logger.getLogger(aoj_2.class);
    private static final boolean DEBUG = true;
    private static final aoj_2 cLg = new aoj_2();
    private final HashMap cLh = new HashMap();

    private aoj_2() {
    }

    public static aoj_2 aCR() {
        return cLg;
    }

    public final void a(afs_1 afs_12, JX jX, String string, jJ[] jJArray, boolean bl2) {
        ef_2 ef_22 = (ef_2)this.cLh.get(afs_12);
        if (ef_22 == null) {
            ef_22 = new ef_2();
            this.cLh.put(afs_12, ef_22);
        }
        ef_22.a(jX, string, jJArray, bl2);
    }

    public final ef_2 b(afs_1 afs_12) {
        return (ef_2)this.cLh.get(afs_12);
    }

    public final ef_2 c(afs_1 afs_12) {
        return (ef_2)this.cLh.remove(afs_12);
    }

    public void d(afs_1 afs_12) {
        ef_2 ef_22 = (ef_2)this.cLh.get(afs_12);
        if (ef_22 != null && ef_22.a(afs_12)) {
            this.cLh.remove(afs_12);
        }
    }

    public void a(afs_1 afs_12, JX jX) {
        ef_2 ef_22 = (ef_2)this.cLh.get(afs_12);
        if (ef_22 != null && ef_22.a(jX)) {
            this.cLh.remove(afs_12);
        }
    }

    public void clean() {
        this.cLh.clear();
    }
}

