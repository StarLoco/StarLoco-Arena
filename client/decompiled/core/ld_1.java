/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from ld
 */
public class ld_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(ld_1.class);

    public ld_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "addMobileDestructionCallback";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("funcName", aos_1.elS, false), new LX("params", aos_1.elX, true)};
    }

    public LX[] R() {
        return null;
    }

    protected void c(int n2) {
        JX jX = this.agC();
        long l2 = this.hY(0);
        String string = this.hZ(1);
        jJ[] jJArray = this.aX(2, n2);
        bd_1.Is().b(new adn_2(this, l2, jX, string, jJArray));
    }
}

