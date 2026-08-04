/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaException
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

public final class jJ {
    private final Object dE;
    private final aos_1 BM;

    public jJ(Object object) {
        if (object == null) {
            this.BM = aos_1.elY;
            this.dE = object;
            return;
        }
        if (object instanceof String) {
            this.BM = aos_1.elS;
            this.dE = object;
            return;
        }
        if (object instanceof Boolean) {
            this.BM = aos_1.elV;
            this.dE = object;
            return;
        }
        if (object instanceof Double) {
            this.BM = aos_1.elU;
            this.dE = object;
            return;
        }
        if (object instanceof Integer) {
            this.BM = aos_1.elT;
            this.dE = object;
            return;
        }
        if (object instanceof Long) {
            this.BM = aos_1.elR;
            this.dE = object;
            return;
        }
        if (object instanceof Float) {
            this.BM = aos_1.elU;
            this.dE = ((Float)object).doubleValue();
            return;
        }
        if (object instanceof Short) {
            this.BM = aos_1.elT;
            this.dE = ((Short)object).intValue();
            return;
        }
        if (object instanceof Byte) {
            this.BM = aos_1.elT;
            this.dE = ((Byte)object).intValue();
            return;
        }
        if (object instanceof Character) {
            this.BM = aos_1.elS;
            this.dE = ((Character)object).toString();
            return;
        }
        this.BM = aos_1.elQ;
        this.dE = object;
    }

    public final aos_1 np() {
        return this.BM;
    }

    public final Object getValue() {
        return this.dE;
    }

    public void c(LuaState luaState) {
        switch (this.np()) {
            case elU: {
                luaState.pushNumber(((Double)this.getValue()).doubleValue());
                break;
            }
            case elT: {
                luaState.pushNumber((double)((Integer)this.getValue()).intValue());
                break;
            }
            case elV: {
                luaState.pushBoolean(((Boolean)this.getValue()).booleanValue());
                break;
            }
            case elS: {
                luaState.pushString((String)this.getValue());
                break;
            }
            case elQ: 
            case elR: {
                luaState.pushJavaObject(this.getValue());
                break;
            }
            case elY: {
                luaState.pushNil();
            }
        }
    }

    public static jJ a(LuaState luaState, int n2) {
        if (luaState.isNumber(n2)) {
            return new jJ(luaState.toNumber(n2));
        }
        if (luaState.isBoolean(n2)) {
            return new jJ(luaState.toBoolean(n2));
        }
        if (luaState.isString(n2)) {
            return new jJ(luaState.toString(n2));
        }
        if (luaState.isObject(n2)) {
            return new jJ(luaState.toJavaObject(n2));
        }
        if (luaState.isNil(n2)) {
            return new jJ(null);
        }
        throw new LuaException("Valeur de type inconnu dans un script Lua");
    }
}

