/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import java.util.ArrayList;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Rm
 */
final class rm_1
implements ayc {
    public final int mK;
    private final String mO;
    private final jJ[] mP;
    private ArrayList mQ;
    final /* synthetic */ JX mR;

    rm_1(JX jX, String string, jJ[] jJArray) {
        this.mR = jX;
        this.mO = string;
        this.mP = jJArray;
        this.mK = JX.d(jX);
    }

    public int getId() {
        return this.mK;
    }

    public void execute() {
        if (JX.e(this.mR).isClosed()) {
            JX.sP().error((Object)("Tentative d'execution d'une WaitingTask sur un script ferm\u00e9 id=" + JX.f(this.mR)));
            return;
        }
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
}

