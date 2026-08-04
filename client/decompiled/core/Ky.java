/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 *  org.keplerproject.luajava.LuaStateFactory
 */
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class Ky
extends aFe
implements ec_0,
Runnable {
    public static final boolean bnN = false;
    private static Logger a = Logger.getLogger(Ky.class);
    private static Logger bnO = Logger.getLogger((String)"LUA");
    public static final String bnP = "script";
    public static final String bnQ = ".lua";
    public static final FilenameFilter rL = new xq_1();
    private final HashMap bnR = new HashMap();
    private static final int bnS = 30;
    private static Ky bnT = new Ky();
    private final ArrayList G = new ArrayList();
    private long bnU = 0L;
    private final ConcurrentHashMap bnV = new ConcurrentHashMap();
    private mp_0[] bnW = new mp_0[]{aja.ayY()};

    private Ky() {
        super(204800L, false);
        ip_2.Un().a(this, 30L);
    }

    public static Ky WG() {
        return bnT;
    }

    protected final sP g(InputStream inputStream) {
        assert (inputStream != null);
        byte[] byArray = new byte[inputStream.available()];
        String string = inputStream.read(byArray) > 0 ? new String(byArray) : null;
        inputStream.close();
        return new Ix(this, string, null);
    }

    public final String getExtension() {
        return bnQ;
    }

    public final FilenameFilter getFilenameFilter() {
        return rL;
    }

    public final void d(ec_0 ec_02) {
        if (!this.G.contains(ec_02)) {
            this.G.add(ec_02);
        }
    }

    public final void e(ec_0 ec_02) {
        this.G.remove(ec_02);
    }

    public final void c(mp_0 ... mp_0Array) {
        assert (this.bnW != null);
        if (mp_0Array != null && mp_0Array.length != 0) {
            HashSet<mp_0> hashSet = new HashSet<mp_0>();
            hashSet.addAll(Arrays.asList(this.bnW));
            hashSet.addAll(Arrays.asList(mp_0Array));
            this.bnW = hashSet.toArray(new mp_0[hashSet.size()]);
        }
    }

    public final Iterable WH() {
        return this.bnV.values();
    }

    public final JX gz(int n2) {
        return (JX)this.bnV.get(n2);
    }

    String gA(int n2) {
        assert (this.getPath() != null);
        return String.format("%d%s", n2, this.getExtension());
    }

    public final JX a(int n2, mp_0[] mp_0Array, boolean bl2) {
        assert (this.getPath() != null);
        return this.a(this.gA(n2), mp_0Array, bl2);
    }

    public JX a(String string, mp_0[] mp_0Array, boolean bl2) {
        JX jX = this.d(mp_0Array);
        try {
            jX.h(this.getPath() + string, bl2);
            jX.setSource(string);
            jX.bM(bl2);
        }
        catch (Exception exception) {
            a.error((Object)("Impossible de charger le fichier " + string), (Throwable)exception);
            return null;
        }
        if (jX.Wt() == vt_1.asL) {
            this.bnV.put(jX.getId(), jX);
            return jX;
        }
        return null;
    }

    public JX b(String string, mp_0[] mp_0Array, boolean bl2) {
        JX jX = this.d(mp_0Array);
        jX.eS(string);
        jX.bM(bl2);
        if (jX.Wt() == vt_1.asL) {
            int n2 = jX.getId();
            this.bnV.put(n2, jX);
            return jX;
        }
        return null;
    }

    public final JX a(String string, ec_0 ec_02) {
        return this.a(string, null, null, ec_02, false);
    }

    public final JX eY(String string) {
        return this.a(string, null, null);
    }

    public final JX a(String string, ec_0 ec_02, boolean bl2) {
        return this.a(string, null, null, ec_02, bl2);
    }

    public final JX i(String string, boolean bl2) {
        return this.a(string, (ec_0)null, bl2);
    }

    public final JX a(int n2, ec_0 ec_02) {
        return this.a(n2, null, null, ec_02, false);
    }

    public final JX gB(int n2) {
        return this.a(n2, null, null);
    }

    public final JX a(int n2, ec_0 ec_02, boolean bl2) {
        return this.a(n2, null, null, ec_02, bl2);
    }

    public final JX e(int n2, boolean bl2) {
        return this.a(n2, (ec_0)null, bl2);
    }

    public final JX a(int n2, mp_0[] mp_0Array, ec_0 ec_02) {
        return this.a(n2, mp_0Array, null, ec_02, false);
    }

    public final JX a(int n2, mp_0[] mp_0Array) {
        return this.a(n2, mp_0Array, null);
    }

    public final JX a(int n2, mp_0[] mp_0Array, Map map, ec_0 ec_02, boolean bl2) {
        JX jX = this.a(n2, mp_0Array, bl2);
        if (jX != null) {
            if (ec_02 != null) {
                jX.a(ec_02);
            }
            jX.g(map);
        } else {
            a.error((Object)("Le Script de [" + n2 + "] n'existe pas"));
        }
        return jX;
    }

    public final JX a(int n2, mp_0[] mp_0Array, Map map, boolean bl2) {
        return this.a(n2, mp_0Array, map, null, bl2);
    }

    public final JX a(String string, mp_0[] mp_0Array, ec_0 ec_02) {
        return this.a(string, mp_0Array, null, ec_02, false);
    }

    public final JX a(String string, mp_0[] mp_0Array) {
        return this.a(string, mp_0Array, null);
    }

    public final JX a(String string, mp_0[] mp_0Array, Map map, ec_0 ec_02, boolean bl2) {
        JX jX = this.a(string, mp_0Array, bl2);
        if (jX != null) {
            if (ec_02 != null) {
                jX.a(ec_02);
            }
            jX.g(map);
        } else {
            a.error((Object)("Le Script de [" + string + "] n'existe pas"));
        }
        return jX;
    }

    public final JX eZ(String string) {
        return this.b(string, null);
    }

    public final JX j(String string, boolean bl2) {
        return this.a(string, null, null, bl2);
    }

    public final JX b(String string, mp_0[] mp_0Array) {
        return this.a(string, mp_0Array, null, false);
    }

    public final JX c(String string, mp_0[] mp_0Array, boolean bl2) {
        return this.a(string, mp_0Array, null, bl2);
    }

    public final JX a(String string, mp_0[] mp_0Array, Map map, boolean bl2) {
        JX jX = this.b(string, mp_0Array, bl2);
        if (jX != null) {
            jX.setSource("Command : " + string);
            jX.g(map);
        } else {
            a.error((Object)("Le Script de commande [" + string + "] n'existe pas"));
        }
        return jX;
    }

    public final void gC(int n2) {
        JX jX = this.gz(n2);
        if (jX != null) {
            jX.interrupt();
        }
    }

    public final int WI() {
        int n2 = 1;
        while (this.bnV.containsKey(n2)) {
            ++n2;
        }
        return n2;
    }

    public void update(int n2) {
        Iterator iterator = this.bnV.values().iterator();
        while (iterator.hasNext()) {
            JX jX = (JX)iterator.next();
            jX.update(n2);
            if (jX.Wt() != vt_1.asP) continue;
            iterator.remove();
        }
    }

    private JX d(mp_0[] mp_0Array) {
        mp_0[] mp_0Array2;
        if (mp_0Array != null) {
            mp_0Array2 = new mp_0[this.bnW.length + mp_0Array.length];
            System.arraycopy(this.bnW, 0, mp_0Array2, 0, this.bnW.length);
            System.arraycopy(mp_0Array, 0, mp_0Array2, this.bnW.length, mp_0Array.length);
        } else {
            mp_0Array2 = this.bnW;
        }
        LuaState luaState = LuaStateFactory.newLuaState();
        int n2 = this.WI();
        JX jX = new JX(n2, luaState, this, mp_0Array2);
        luaState.pushJavaObject((Object)jX);
        luaState.setGlobal(bnP);
        return jX;
    }

    public final void interrupt() {
        ip_2.Un().b(this);
    }

    public final long getId() {
        return 1L;
    }

    public final void c(long l2) {
    }

    public final void run() {
        long l2 = System.currentTimeMillis();
        int n2 = (int)(l2 - this.bnU);
        this.update(n2);
        this.bnU = l2;
    }

    public mp_0 fa(String string) {
        return (mp_0)this.bnR.get(string);
    }

    public void e(mp_0 ... mp_0Array) {
        for (int j = 0; j < mp_0Array.length; ++j) {
            this.bnR.put(mp_0Array[j].getName(), mp_0Array[j]);
        }
    }

    public void a(JX jX, aeF aeF2, String string) {
        for (int j = 0; j < this.G.size(); ++j) {
            assert (this.G.get(j) != null);
            ((ec_0)this.G.get(j)).a(jX, aeF2, string);
        }
        if (!jX.Wr()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[LUA] ").append((Object)aeF2).append(" Fichier ").append(jX.getSource()).append(' ').append(string);
            bnO.error((Object)stringBuilder.toString());
        }
        a.error((Object)("Erreur dans un script (" + jX.getSource() + " ) : erreur " + (Object)((Object)aeF2) + " " + string), (Throwable)new Exception("callStack"));
    }

    public void b(JX jX) {
        for (int j = 0; j < this.G.size(); ++j) {
            ((ec_0)this.G.get(j)).b(jX);
        }
    }

    public void c(JX jX) {
    }

    public void WJ() {
        Collection collection = this.bnV.values();
        for (JX jX : collection) {
            jX.Wy();
        }
        this.bnV.clear();
    }
}

