/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from ew
 */
class ew_0
extends uc_1 {
    final /* synthetic */ apM oR;

    public ew_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "manageActions";
    }

    public LX[] Q() {
        return new LX[]{new LX("packageName", aos_1.elS, false), new LX("push", aos_1.elV, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        String string = this.hZ(0);
        Class clazz = null;
        if (string.equals("Fight")) {
            clazz = aek_2.class;
        } else if (string.equals("Evolution")) {
            clazz = aio_1.class;
        } else if (string.equals("SphereBoard")) {
            clazz = og_2.class;
        }
        if (clazz != null) {
            if (this.ic(1)) {
                add_1.aOG().l(string, clazz);
            } else {
                add_1.aOG().kG(string);
            }
        } else {
            this.a(a, "Ce package n'est pas support\u00e9 par manageActions " + string);
        }
    }
}

