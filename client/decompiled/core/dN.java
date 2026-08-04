/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import java.util.ArrayList;
import org.keplerproject.luajava.LuaState;

public final class dN
implements ayc {
    public final int mK;
    private final int mL;
    private int mM;
    private int mN;
    private final String mO;
    private final jJ[] mP;
    private ArrayList mQ;
    final /* synthetic */ JX mR;

    dN(JX jX, int n2, int n3, String string, jJ[] jJArray) {
        this.mR = jX;
        this.mL = n2;
        this.mM = JX.j(jX) + n2;
        this.mN = n3;
        this.mO = string;
        this.mP = jJArray;
        this.mK = JX.d(jX);
    }

    public int getId() {
        return this.mK;
    }

    public void execute() {
        if (this.mO.contains(".")) {
            String[] stringArray = this.mO.split("\\.");
            JX.e(this.mR).pushString(stringArray[0]);
            JX.e(this.mR).getTable(LuaState.LUA_GLOBALSINDEX.intValue());
            if (!JX.e(this.mR).isTable(-1)) {
                JX.sP().error((Object)(stringArray[0] + " n'est pas une librairie connue"));
                JX.e(this.mR).remove(-1);
                switch (JX.g(this.mR)) {
                    case asO: {
                        this.mR.Wp();
                        break;
                    }
                    case asM: {
                        this.mR.Wq();
                    }
                }
                return;
            }
            JX.e(this.mR).pushString(stringArray[1]);
            JX.e(this.mR).getTable(-2);
            JX.e(this.mR).remove(-2);
        } else {
            JX.e(this.mR).pushString(this.mO);
            JX.e(this.mR).getTable(LuaState.LUA_GLOBALSINDEX.intValue());
        }
        if (JX.h(this.mR)) {
            int n2 = this.mP == null ? 0 : this.mP.length;
            for (int j = 0; j < n2; ++j) {
                this.mP[j].c(JX.e(this.mR));
            }
            if (JX.e(this.mR).pcall(n2, LuaState.LUA_MULTRET.intValue(), 0) != 0) {
                JX.a(this.mR, JX.e(this.mR), aeF.cpf);
            }
        } else {
            JX.sP().error((Object)("Fonction inconnue " + this.mO + " dans le script " + JX.i(this.mR)));
        }
        JX.e(this.mR).pop(JX.e(this.mR).getTop());
        switch (JX.g(this.mR)) {
            case asO: {
                this.mR.Wp();
                break;
            }
            case asM: {
                this.mR.Wq();
            }
        }
    }

    final boolean fZ() {
        if (JX.j(this.mR) >= this.mM) {
            this.execute();
            if (this.mN == -1) {
                this.mM += this.mL;
            } else {
                --this.mN;
                if (this.mN > 0) {
                    this.mM += this.mL;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}

