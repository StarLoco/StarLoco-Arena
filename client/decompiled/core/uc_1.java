/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.JavaFunction
 *  org.keplerproject.luajava.LuaException
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Uc
 */
public abstract class uc_1
extends JavaFunction {
    protected static final Logger a = Logger.getLogger(uc_1.class);
    private static final int bPr = -1;
    protected int bPs = 0;
    private final LX[] bPt = this.Q();
    private final LX[] bPu = this.R();

    public uc_1(LuaState luaState) {
        super(luaState);
    }

    public abstract String getName();

    public String getDescription() {
        return "not yet implemented";
    }

    public abstract LX[] Q();

    public abstract LX[] R();

    public final void register() {
        assert (this.getName() != null);
        super.register(this.getName());
    }

    public final int execute() {
        int n2;
        this.bPs = 0;
        int n3 = this.agA();
        if (n3 >= 0) {
            try {
                this.c(n3);
            }
            catch (LuaException luaException) {
                throw this.fW(luaException.toString());
            }
        } else {
            this.a(a, "Fonction " + ((Object)((Object)this)).getClass().getSimpleName() + " non \u00e9x\u00e9cut\u00e9e");
        }
        int n4 = n2 = this.bPu == null ? 0 : this.bPu.length;
        if (n2 != this.bPs) {
            boolean bl2 = this.agz();
            if (this.bPs < n2 && !bl2) {
                this.a(a, String.format(" nombre de valeur de retour incorrect (attendu " + (bl2 ? ">=" : "") + n2 + " lu: " + this.bPs + " )", new Object[0]));
            }
        }
        return this.bPs;
    }

    private boolean agz() {
        return this.bPu[this.bPu.length - 1].np() == aos_1.elX;
    }

    protected final String getLineNumber() {
        try {
            return this.agC().Ww();
        }
        catch (Exception exception) {
            a.error((Object)"Exception levee", (Throwable)exception);
            return "-1";
        }
    }

    private LuaException fW(String string) {
        return new LuaException(this.fX(string));
    }

    private String fX(String string) {
        String string2 = "<inconnu>";
        try {
            string2 = this.agC().getSource();
        }
        catch (Exception exception) {
            a.warn((Object)"pas de script associ\u00e9 a cette fonction");
        }
        return "fichier=" + string2 + " ligne=" + this.getLineNumber() + " " + string;
    }

    protected final void a(Logger logger, String string) {
    }

    protected abstract void c(int var1);

    private int agA() {
        LX[] lXArray = this.bPt;
        if (lXArray == null) {
            return this.aW(0, 0);
        }
        int n2 = 0;
        int n3 = 0;
        for (int j = 0; j < lXArray.length; ++j) {
            ++n3;
            if (!lXArray[j].Yi()) {
                ++n2;
            }
            if (lXArray[j].np() != aos_1.elX) continue;
            n3 = Integer.MAX_VALUE;
        }
        if (n3 < n2) {
            n3 = n2;
        }
        return this.aW(n2, n3);
    }

    private int aW(int n2, int n3) {
        assert (n2 <= n3);
        int n4 = this.L.getTop() - 1;
        if (n4 >= n2 && n4 <= n3) {
            for (int j = 0; j < n2; ++j) {
                if (this.hV(j)) continue;
                return -j - 1;
            }
            return n4;
        }
        String string = n2 == n3 ? String.format("(attendu: %d, lu: %d)", n2, n4) : (n3 == Integer.MAX_VALUE ? String.format("(attendu au moins: %d, lu: %d)", n2, n4) : String.format("(attendu: %d-%d, lu: %d)", n2, n3, n4));
        this.a(a, "nombre de param\u00e8tre incorrect " + string);
        return -1;
    }

    private boolean hV(int n2) {
        assert (n2 >= 0);
        LX[] lXArray = this.bPt;
        if (lXArray == null) {
            this.a(a, "La fonction n'attend pas de param\u00e8tre");
            return false;
        }
        if (n2 < lXArray.length) {
            aos_1 aos_12 = lXArray[n2].np();
            if (this.L.isNil(n2 + 2)) {
                this.a(a, " param\u00e8tre " + n2 + " est null");
                return false;
            }
            if (!aos_12.b(this.L, n2 + 2)) {
                String string = String.format("mauvais type d'argument #%d: (definition: %s, fonction:%s)", new Object[]{n2, aos_12, this.L.typeName(this.L.type(n2 + 2))});
                this.a(a, string);
                return false;
            }
        }
        return true;
    }

    public jJ[] aX(int n2, int n3) {
        int n4 = n3 - n2;
        if (n4 <= 0) {
            return null;
        }
        jJ[] jJArray = new jJ[n4];
        for (int j = 0; j < n4; ++j) {
            int n5 = n2 + j + 2;
            jJArray[j] = jJ.a(this.L, n5);
        }
        return jJArray;
    }

    public final int hW(int n2) {
        assert (n2 >= 0);
        if (this.L.isObject(n2 + 2)) {
            return (int)((Long)this.L.toJavaObject(n2 + 2)).longValue();
        }
        return this.L.toInteger(n2 + 2);
    }

    public final double hX(int n2) {
        assert (n2 >= 0);
        if (this.L.isObject(n2 + 2)) {
            return (Double)this.L.toJavaObject(n2 + 2);
        }
        return this.L.toNumber(n2 + 2);
    }

    public final long hY(int n2) {
        assert (n2 >= 0);
        if (this.L.isObject(n2 + 2)) {
            return (Long)this.L.toJavaObject(n2 + 2);
        }
        if (this.L.isNumber(n2 + 2)) {
            return (long)this.L.toNumber(n2 + 2);
        }
        return 0L;
    }

    public final String hZ(int n2) {
        assert (n2 >= 0);
        return this.L.toString(n2 + 2);
    }

    public final jJ[] ia(int n2) {
        assert (n2 >= 0);
        int n3 = n2 + 2;
        jJ[] jJArray = new jJ[this.L.objLen(n3)];
        for (int j = 0; j < jJArray.length; ++j) {
            this.L.pushNumber((double)(j + 1));
            this.L.getTable(n3);
            jJArray[j] = jJ.a(this.L, -1);
            this.L.pop(1);
        }
        return jJArray;
    }

    public final String ib(int n2) {
        assert (n2 >= 0);
        if (this.L.isObject(n2 + 2)) {
            return this.L.toJavaObject(n2 + 2).toString();
        }
        return this.L.toString(n2 + 2);
    }

    public final boolean ic(int n2) {
        assert (n2 >= 0);
        if (this.L.isObject(n2 + 2)) {
            return (Boolean)this.L.toJavaObject(n2 + 2);
        }
        return this.L.toBoolean(n2 + 2);
    }

    private void a(aos_1 aos_12) {
        assert (aos_12 != null);
        if (this.bPu == null) {
            return;
        }
        if (this.bPs >= this.bPu.length - 1 && this.agz()) {
            return;
        }
        aos_1 aos_13 = this.bPu[this.bPs].np();
        if (!aos_13.b(aos_12)) {
            throw this.fW("Type de valeur de retour incorrecte :" + (Object)((Object)aos_13) + "attendue: " + (Object)((Object)aos_12));
        }
    }

    protected final void cp(boolean bl2) {
        this.a(aos_1.elV);
        this.L.pushBoolean(bl2);
        ++this.bPs;
    }

    protected final void id(int n2) {
        this.a(aos_1.elT);
        this.L.pushNumber((double)n2);
        ++this.bPs;
    }

    protected final void ap(Object object) {
        this.a(aos_1.elQ);
        this.L.pushJavaObject(object);
        ++this.bPs;
    }

    protected final void as(float f) {
        this.a(aos_1.elU);
        this.L.pushNumber((double)f);
        ++this.bPs;
    }

    protected final void da(long l2) {
        this.a(aos_1.elR);
        this.L.pushObjectValue((Object)l2);
        ++this.bPs;
    }

    protected final void t(double d) {
        this.a(aos_1.elU);
        this.L.pushNumber(d);
        ++this.bPs;
    }

    protected final void M(byte[] byArray) {
        this.a(aos_1.elS);
        this.L.pushString(byArray);
        ++this.bPs;
    }

    protected final void fY(String string) {
        this.a(aos_1.elS);
        this.L.pushString(string);
        ++this.bPs;
    }

    protected final void agB() {
        this.L.pushNil();
        ++this.bPs;
    }

    protected final JX agC() {
        assert (this.L != null);
        this.L.getGlobal("script");
        JX jX = (JX)this.L.toJavaObject(-1);
        this.L.pop(1);
        return jX;
    }
}

