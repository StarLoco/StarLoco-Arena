/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import java.util.Iterator;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aov
 */
class aov_2
extends uc_1 {
    final /* synthetic */ uc_2 aBX;

    public aov_2(uc_2 uc_22, LuaState luaState) {
        this.aBX = uc_22;
        super(luaState);
    }

    public String getName() {
        return "getFighterId";
    }

    public String getDescription() {
        return "Renvoi l'id du mobile positionn\u00e9 en X, Y ou nil si aucun mobile trouv\u00e9";
    }

    public LX[] Q() {
        return new LX[]{new LX("worldX", aos_1.elT, false), new LX("worldY", aos_1.elT, false)};
    }

    public final LX[] R() {
        return new LX[]{new LX("mobileId", aos_1.elR, false)};
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 == null) {
            a.error((Object)"pas de fight");
            this.agB();
            return;
        }
        Iterator iterator = adu_02.aKq();
        while (iterator.hasNext()) {
            ee_2 ee_22 = (ee_2)iterator.next();
            if (ee_22.gn() != n3 || ee_22.go() != n4) continue;
            this.da(ee_22.getId());
            return;
        }
        this.agB();
    }
}

