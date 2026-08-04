/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaException
 *  org.keplerproject.luajava.LuaObject
 *  org.keplerproject.luajava.LuaState
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;

public class JX {
    private static final Logger a = Logger.getLogger(JX.class);
    private static final int bmP = -1;
    private final int aW;
    private vt_1 bmQ;
    private int bmR;
    private String bmS;
    private int bmT = 0;
    private final LuaState bmU;
    private boolean bmV = false;
    private rm_1 bmW;
    private final ConcurrentLinkedQueue bmX = new ConcurrentLinkedQueue();
    private final ArrayList bmY = new ArrayList();
    private boolean bmZ = false;
    private Integer bna;

    JX(int n2, LuaState luaState, Ky ky, mp_0[] mp_0Array) {
        assert (luaState != null);
        assert (ky != null);
        this.aW = n2;
        this.bmU = luaState;
        this.a(ky);
        this.bmQ = vt_1.asK;
        if (mp_0Array != null) {
            for (mp_0 mp_02 : mp_0Array) {
                try {
                    mp_02.d(this.bmU);
                }
                catch (Exception exception) {
                    a.error((Object)exception);
                }
            }
        }
        this.bmU.openBase();
        this.bmU.openMath();
        this.bmU.openTable();
        this.bmU.openOs();
        this.bmT = 0;
    }

    private void finish() {
        if (!this.bmU.isClosed()) {
            this.bmU.close();
        }
        this.bmQ = vt_1.asP;
        for (int j = 0; j < this.bmY.size(); ++j) {
            ((ec_0)this.bmY.get(j)).b(this);
        }
    }

    private void a(LuaState luaState, aeF aeF2) {
        String string = null;
        if (!luaState.isClosed() && luaState.getTop() >= 1 && luaState.isString(-1)) {
            string = luaState.toString(-1);
            luaState.pop(1);
        }
        if (string == null) {
            string = "";
        }
        for (int j = 0; j < this.bmY.size(); ++j) {
            ((ec_0)this.bmY.get(j)).a(this, aeF2, string);
        }
        this.interrupt();
    }

    public final void Wp() {
        if (this.bmX.isEmpty() && this.bmW == null) {
            this.finish();
        }
    }

    public final void Wq() {
        if (this.bmX.isEmpty() && this.bmW == null) {
            this.bmQ = vt_1.asN;
        }
    }

    final boolean Wr() {
        return this.bmZ;
    }

    final void bM(boolean bl2) {
        this.bmZ = bl2;
    }

    public final int getId() {
        return this.aW;
    }

    public final int Ws() {
        return this.bmR;
    }

    public final void interrupt() {
        this.bmV = true;
    }

    public final dN a(int n2, int n3, String string, jJ[] jJArray) {
        if (this.bmQ == vt_1.asN) {
            this.bmQ = vt_1.asM;
        }
        dN dN2 = new dN(this, n2, n3, string, jJArray);
        this.bmX.add(dN2);
        return dN2;
    }

    public final boolean a(String string, jJ[] jJArray) {
        rm_1 rm_12;
        if (this.bmW != null) {
            return false;
        }
        if (this.bmQ == vt_1.asN) {
            this.bmQ = vt_1.asM;
        }
        this.bmW = rm_12 = new rm_1(this, string, jJArray);
        return true;
    }

    public final boolean a(ayc ayc2) {
        if (ayc2 instanceof rm_1) {
            if (this.bmW != null) {
                this.bmW = null;
                return true;
            }
            return false;
        }
        return ayc2 instanceof dN && this.bmX.remove(ayc2);
    }

    public final boolean gx(int n2) {
        if (this.bmW != null && this.bmW.getId() == n2) {
            this.bmW = null;
            return true;
        }
        Iterator iterator = this.bmX.iterator();
        while (iterator.hasNext()) {
            if (((dN)iterator.next()).getId() != n2) continue;
            iterator.remove();
            return true;
        }
        return false;
    }

    final vt_1 Wt() {
        return this.bmQ;
    }

    final void eS(String string) {
        if (string != null && this.bmU.LloadString(string) == 0) {
            this.bmQ = vt_1.asL;
        } else {
            this.a(this.bmU, aeF.cpe);
        }
    }

    final void eT(String string) {
        this.h(string, false);
    }

    final void h(String string, boolean bl2) {
        Object object;
        InputStream inputStream;
        block8: {
            inputStream = null;
            this.bmS = string;
            try {
                object = new URL(string);
                inputStream = ((URL)object).openStream();
            }
            catch (Exception exception) {
                File file = new File(string);
                if (!file.exists()) break block8;
                inputStream = new FileInputStream(file);
            }
        }
        if (inputStream != null) {
            object = new byte[inputStream.available()];
            if ((inputStream = new BufferedInputStream(inputStream)).read((byte[])object) > 0) {
                this.eS(new String((byte[])object));
            } else if (!bl2) {
                a.error((Object)("Erreur lors du chargement du script : " + string + ", pas de donn\u00e9es."));
            }
            inputStream.close();
        } else if (!bl2) {
            a.error((Object)("Erreur lors du chargement du script : " + string + ", pas de stream ouvert."));
        }
    }

    final void g(Map map) {
        if (this.bmQ == vt_1.asL) {
            if (map != null) {
                for (Map.Entry object : map.entrySet()) {
                    try {
                        this.bmU.pushObjectValue(object.getValue());
                        this.bmU.setGlobal((String)object.getKey());
                    }
                    catch (LuaException ec_02) {
                        a.error((Object)"Impossible de d\u00e9finir une variable de contexte pour un script", (Throwable)ec_02);
                    }
                }
            }
            int n2 = this.bmU.resume(0);
            this.bmQ = vt_1.asO;
            for (ec_0 ec_02 : this.bmY) {
                ec_02.c(this);
            }
            if (n2 != 0) {
                this.a(this.bmU, aeF.cpf);
            } else {
                this.Wp();
            }
        } else if (!this.bmV) {
            this.bmU.pushString("No file loaded");
            this.a(this.bmU, aeF.cpg);
        }
    }

    public void Wu() {
        if (this.bmW == null) {
            return;
        }
        this.bmW.execute();
        this.bmW = null;
    }

    public void Wv() {
        this.bmW = null;
    }

    final void update(int n2) {
        if (this.bmV) {
            switch (this.bmQ) {
                case asN: {
                    break;
                }
                case asM: {
                    this.bmQ = vt_1.asN;
                    break;
                }
                default: {
                    this.Wy();
                    break;
                }
            }
        } else {
            this.bmR += n2;
            switch (this.bmQ) {
                case asN: 
                case asK: 
                case asL: {
                    break;
                }
                case asM: {
                    Iterator iterator = this.bmX.iterator();
                    while (iterator.hasNext()) {
                        if (!((dN)iterator.next()).fZ()) continue;
                        iterator.remove();
                    }
                    this.Wq();
                    break;
                }
                case asO: {
                    Iterator iterator = this.bmX.iterator();
                    while (iterator.hasNext()) {
                        if (!((dN)iterator.next()).fZ()) continue;
                        iterator.remove();
                    }
                    this.Wp();
                    break;
                }
            }
        }
    }

    public String getSource() {
        return this.bmS;
    }

    public void setSource(String string) {
        this.bmS = string;
    }

    public LuaState getLuaState() {
        return this.bmU;
    }

    public jJ eU(String string) {
        if (this.bmU.isClosed()) {
            a.error((Object)("Tente de r\u00e9cup\u00e9rer une variable (" + string + ") alors que le script est ferm\u00e9"));
            return null;
        }
        this.bmU.getGlobal(string);
        jJ jJ2 = null;
        try {
            jJ2 = jJ.a(this.bmU, -1);
        }
        catch (LuaException luaException) {
            a.error((Object)("Variable " + string + " inconnue?"), (Throwable)luaException);
        }
        this.bmU.pop(1);
        return jJ2;
    }

    private boolean isFunction() {
        if (!this.bmU.isFunction(-1) && !this.bmU.isJavaFunction(-1)) {
            this.bmU.Lwhere(1);
            this.bmU.pop(1);
            return false;
        }
        return true;
    }

    public final jJ[] a(String string, jJ[] jJArray, amd_0 ... amd_0Array) {
        int n2;
        if (this.bmQ == vt_1.asK || this.bmQ == vt_1.asP) {
            a.error((Object)("Le script devrait \u00eatre charg\u00e9 avant d'appeler une fonction. (loadFile) " + (Object)((Object)this.bmQ)));
            return null;
        }
        if (this.bmQ == vt_1.asL) {
            this.bmU.resume(0);
            this.bmQ = vt_1.asM;
        }
        if (amd_0Array != null) {
            for (int j = 0; j < amd_0Array.length; ++j) {
                if (amd_0Array[j] == null) continue;
                amd_0Array[j].c(this.bmU);
            }
        }
        if (string.contains(".")) {
            String[] stringArray = string.split("\\.");
            this.bmU.pushString(stringArray[0]);
            this.bmU.getTable(LuaState.LUA_GLOBALSINDEX.intValue());
            if (!this.bmU.isTable(-1)) {
                a.error((Object)(stringArray[0] + " n'est pas une librairie connue"));
                this.bmU.remove(-1);
                switch (this.bmQ) {
                    case asO: {
                        this.Wp();
                        break;
                    }
                    case asM: {
                        this.Wq();
                    }
                }
                return null;
            }
            this.bmU.pushString(stringArray[1]);
            this.bmU.getTable(-2);
            this.bmU.remove(-2);
        } else {
            this.bmU.getGlobal(string);
        }
        if (!this.isFunction()) {
            a.error((Object)("Fonction inconnue " + string + " dans le script " + this.bmS + " ligne " + this.Ww()));
            return null;
        }
        int n3 = 0;
        if (jJArray != null) {
            n3 = jJArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                if (jJArray[n2] == null) {
                    this.bmU.pushNil();
                    continue;
                }
                jJArray[n2].c(this.bmU);
            }
        }
        if (this.bmU.pcall(n3, LuaState.LUA_MULTRET.intValue(), 0) != 0) {
            this.a(this.bmU, aeF.cpf);
        }
        n2 = this.bmU.getTop();
        jJ[] jJArray2 = new jJ[n2];
        for (int j = 0; j < n2; ++j) {
            try {
                jJArray2[j] = jJ.a(this.bmU, -1);
            }
            catch (LuaException luaException) {
                a.error((Object)("Error retrieving a function(" + string + ") result : " + (Object)((Object)luaException)));
            }
            this.bmU.pop(1);
        }
        return jJArray2;
    }

    public String Ww() {
        String[] stringArray;
        this.bmU.Lwhere(1);
        String string = this.bmU.toString(-1);
        this.bmU.pop(1);
        if (string != null && string.length() > 0 && (stringArray = string.split(":")).length > 1) {
            string = stringArray[1];
        }
        return string;
    }

    public void eV(String string) {
        this.a(string, null, new amd_0[0]);
    }

    public boolean a(ec_0 ec_02) {
        return this.bmY.add(ec_02);
    }

    public final void Wx() {
        this.bmY.clear();
    }

    public final boolean b(ec_0 ec_02) {
        return this.bmY.contains(ec_02);
    }

    public final boolean c(ec_0 ec_02) {
        return this.bmY.remove(ec_02);
    }

    public final void Wy() {
        this.bmX.clear();
        this.bmW = null;
        this.Wp();
    }

    public int Gf() {
        if (this.bna == null) {
            this.bna = -1;
            LuaObject luaObject = this.bmU.getLuaObject("fightId");
            if (luaObject != null) {
                if (luaObject.isNumber()) {
                    this.bna = (int)luaObject.getNumber();
                } else if (luaObject.isJavaObject()) {
                    try {
                        this.bna = Integer.parseInt(luaObject.toString());
                    }
                    catch (NumberFormatException numberFormatException) {
                        a.error((Object)("Impossible de recuperer un id de combat sur un objet non transformable en entier : " + luaObject));
                    }
                }
            }
        }
        return this.bna;
    }

    static /* synthetic */ int d(JX jX) {
        return ++jX.bmT;
    }

    static /* synthetic */ LuaState e(JX jX) {
        return jX.bmU;
    }

    static /* synthetic */ int f(JX jX) {
        return jX.aW;
    }

    static /* synthetic */ Logger sP() {
        return a;
    }

    static /* synthetic */ vt_1 g(JX jX) {
        return jX.bmQ;
    }

    static /* synthetic */ boolean h(JX jX) {
        return jX.isFunction();
    }

    static /* synthetic */ void a(JX jX, LuaState luaState, aeF aeF2) {
        jX.a(luaState, aeF2);
    }

    static /* synthetic */ String i(JX jX) {
        return jX.bmS;
    }

    static /* synthetic */ int j(JX jX) {
        return jX.bmR;
    }
}

