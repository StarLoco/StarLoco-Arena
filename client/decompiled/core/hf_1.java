/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import java.util.ArrayList;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from HF
 */
class hf_1
extends uc_1 {
    final /* synthetic */ fp yb;

    public hf_1(fp fp2, LuaState luaState) {
        this.yb = fp2;
        super(luaState);
    }

    public String getName() {
        return "getElementId";
    }

    public LX[] Q() {
        return new LX[]{new LX("type", aos_1.elT, false), new LX("posX", aos_1.elT, false), new LX("posY", aos_1.elT, false), new LX("posZ", aos_1.elT, true)};
    }

    public LX[] R() {
        return new LX[]{new LX("id", aos_1.elR, false)};
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        int n5 = this.hW(2);
        ArrayList arrayList = GY.Ss().ap(n4, n5);
        int n6 = arrayList.size();
        if (n6 == 0) {
            this.agB();
            return;
        }
        if (n2 < 4) {
            for (int j = 0; j < n6; ++j) {
                tp_1 tp_12 = (tp_1)arrayList.get(j);
                if (tp_12.zn() != n3) continue;
                this.da(tp_12.getId());
                return;
            }
        } else {
            int n7 = this.hW(3);
            for (int j = 0; j < n6; ++j) {
                tp_1 tp_13 = (tp_1)arrayList.get(j);
                if (tp_13.zn() != n3 || tp_13.getAltitude() != (double)n7) continue;
                this.da(tp_13.getId());
                return;
            }
        }
        this.a(a, "L'element interactif de type=" + n3 + " en (" + n4 + "," + n5 + ") n'existe pas");
    }
}

