/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.JavaFunction
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from mP
 */
public abstract class mp_0 {
    protected static final Logger a = Logger.getLogger(mp_0.class);
    private final String m_name;

    public final String getName() {
        return this.m_name;
    }

    protected mp_0(String string) {
        this.m_name = string;
    }

    void d(LuaState luaState) {
        uc_1[] uc_1Array;
        uc_1[] uc_1Array2 = this.a(luaState);
        if (uc_1Array2 != null) {
            luaState.newTable();
            uc_1Array = uc_1Array2;
            int n2 = uc_1Array.length;
            for (int j = 0; j < n2; ++j) {
                uc_1 uc_12 = uc_1Array[j];
                assert (uc_12.getName() != null);
                luaState.pushString(uc_12.getName());
                luaState.pushJavaFunction((JavaFunction)uc_12);
                luaState.setTable(-3);
            }
            if (this.getName() != null) {
                luaState.setGlobal(this.getName());
            } else {
                luaState.setGlobal("UnknownLibrary " + this.toString());
            }
        }
        if ((uc_1Array = this.b(luaState)) != null) {
            for (uc_1 uc_13 : uc_1Array) {
                uc_13.register();
            }
        }
    }

    public abstract uc_1[] a(LuaState var1);

    public abstract uc_1[] b(LuaState var1);
}

