/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Lm
 */
class lm_0
extends uc_1 {
    static final /* synthetic */ boolean bb;
    final /* synthetic */ apM oR;

    public lm_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "loadDialog";
    }

    public LX[] Q() {
        return new LX[]{new LX("dialog", aos_1.elS, false)};
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        String string = this.hZ(0);
        String string2 = null;
        short s = -1;
        if (string.equals("chatDialog")) {
            string2 = oh_2.bq("chatDialog");
            s = 19501;
        }
        if (string.equals("menuBarDialog")) {
            string2 = oh_2.bq("menuBarDialog");
            s = 10000;
        }
        if (string.equals("menuBarEvolutionDialog")) {
            string2 = oh_2.bq("menuBarEvolutionDialog");
            s = 10000;
        }
        if (string.equals("menuBarTutoDialog")) {
            string2 = oh_2.bq("menuBarTutoDialog");
            s = 10000;
        }
        if (string.equals("menuBarTuto2Dialog")) {
            string2 = oh_2.bq("menuBarTuto2Dialog");
            s = 10000;
        }
        if (string2 == null) {
            this.a(a, "Ce dialogue n'est pas pris en charge");
        }
        if (!(bb || string2 != null && s > 0)) {
            throw new AssertionError();
        }
        if (add_1.aOG().kR(string)) {
            a.info((Object)("dialog " + string + " d\u00e9j\u00e0 ouvert"));
            return;
        }
        add_1.aOG().a(string, string2, s);
    }

    static {
        bb = !apM.class.desiredAssertionStatus();
    }
}

