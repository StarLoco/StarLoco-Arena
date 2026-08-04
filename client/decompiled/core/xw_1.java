/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import org.apache.log4j.Logger;

/*
 * Renamed from xW
 */
public final class xw_1 {
    public static final Logger a = Logger.getLogger(xw_1.class);
    private static final boolean azS = true;
    private final HashMap azT = new HashMap();
    private boolean azU = true;
    private boolean azV = false;
    private final Semaphore azW;
    private final ArrayList azX = new ArrayList();
    private static final xw_1 azY = new xw_1();

    private xw_1() {
        this.azW = new Semaphore(1);
    }

    public static xw_1 EB() {
        return azY;
    }

    public boolean EC() {
        return this.azV;
    }

    public void aT(boolean bl2) {
        this.azV = bl2;
    }

    public void cU(String string) {
        try {
            acf acf2 = new acf(vq_2.gm(string));
            String string2 = vq_2.getPath(string);
            long l2 = 0L;
            int n2 = 0;
            int n3 = acf2.readInt();
            for (int j = 0; j < n3; ++j) {
                int n4 = acf2.readShort() & 0xFFFF;
                byte[] byArray = acf2.jE(n4);
                String string3 = string2 + "/" + aey_0.V(byArray) + ".anm";
                Anm anm = this.f(string3, false);
                anm.HE();
                a.info((Object)("pr\u00e9chargement des anms: " + string3));
                l2 += (long)vq_2.readFile(string3).length;
                ++n2;
            }
            acf2.close();
            a.info((Object)("pr\u00e9chargement des anms: " + n2 + " fichiers (" + l2 / 1024L + "ko)"));
        }
        catch (IOException iOException) {
            a.error((Object)"Error while loading Common Animations :", (Throwable)iOException);
        }
    }

    public gw_2 e(String string, boolean bl2) {
        Anm anm = this.f(string, bl2);
        if (anm == null) {
            return null;
        }
        String string2 = vq_2.gs(string);
        String string3 = vq_2.gq(string);
        return new gw_2(anm, string2, string3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Anm f(String string, boolean bl2) {
        Anm anm;
        this.azW.acquireUninterruptibly();
        try {
            anm = (Anm)this.azT.get(string);
            if (anm == null) {
                anm = (Anm)yW.FL().a(Anm.it(), Anm.class);
                anm.b(string, bl2);
                this.azT.put(string, anm);
            }
        }
        finally {
            this.azW.release();
        }
        return anm;
    }

    public void cV(String string) {
        Anm anm = (Anm)this.azT.remove(string);
        if (anm == null) {
            return;
        }
        anm.HF();
    }

    public void a(String string, Fa fa, gw_2 gw_22) {
        assert (gw_22 != null);
        assert (fa != null);
        gw_22.a((Anm)null, (ju_2)null);
        String string2 = gw_22.getPath() + string + ".anm";
        Anm anm = (Anm)this.azT.get(string2);
        if (anm == null) {
            try {
                anm = this.f(string2, this.azU);
                anm.HE();
            }
            catch (IOException iOException) {
                a.error((Object)("Unable to load file " + string2), (Throwable)iOException);
            }
        }
        if (anm == null) {
            return;
        }
        if (anm.is()) {
            gw_22.a(anm, anm.aw(fa.asw));
        } else {
            gw_22.ty = fa.m_name;
        }
    }

    public void ED() {
        Object object;
        this.azW.acquireUninterruptibly();
        long l2 = Long.MAX_VALUE;
        Set set = this.azT.entrySet();
        for (Map.Entry entry : set) {
            object = (Anm)entry.getValue();
            if (!((Anm)object).is()) {
                if (l2 > 0L) {
                    try {
                        l2 = ((Anm)object).L(l2);
                    }
                    catch (IOException iOException) {
                        a.error((Object)"Exception", (Throwable)iOException);
                    }
                }
            } else {
                ((afB)object).avd();
            }
            if (((afB)object).avb() != 0 || ((afB)object).avc() > 0) continue;
            this.azX.add(entry);
        }
        for (int j = 0; j < this.azX.size(); ++j) {
            object = (Map.Entry)this.azX.get(j);
            Anm anm = (Anm)object.getValue();
            this.azT.remove(object.getKey());
            anm.HF();
        }
        this.azX.clear();
        this.azW.release();
    }

    public void update(int n2) {
        Object object;
        this.azW.acquireUninterruptibly();
        long l2 = 1000000L;
        Set set = this.azT.entrySet();
        for (Map.Entry entry : set) {
            object = (Anm)entry.getValue();
            if (!((Anm)object).is()) {
                if (l2 > 0L) {
                    try {
                        l2 = ((Anm)object).L(l2);
                    }
                    catch (IOException iOException) {
                        a.error((Object)"Exception", (Throwable)iOException);
                    }
                }
            } else {
                ((afB)object).avd();
            }
            if (((afB)object).avb() != 0 || ((afB)object).avc() > 0) continue;
            this.azX.add(entry);
        }
        for (int j = 0; j < this.azX.size(); ++j) {
            object = (Map.Entry)this.azX.get(j);
            Anm anm = (Anm)object.getValue();
            this.azT.remove(object.getKey());
            anm.HF();
        }
        this.azX.clear();
        this.azW.release();
    }

    public final void aU(boolean bl2) {
        this.azU = bl2;
    }

    public final boolean EE() {
        return this.azU;
    }
}

