/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Sr
 */
class sr_1
extends uc_1 {
    final /* synthetic */ uc_2 aBX;

    public sr_1(uc_2 uc_22, LuaState luaState) {
        this.aBX = uc_22;
        super(luaState);
    }

    public String getName() {
        return "ignoreEventsMessages";
    }

    public LX[] Q() {
        return new LX[0];
    }

    public final LX[] R() {
        return null;
    }

    protected void c(int n2) {
        apN.aDK().vJ().b(new Ab());
        Ab ab = new Ab();
        apN.aDK().vJ().b(ab);
        for (mT mT2 : bd_1.Is().Iv()) {
            if (mT2.getId() == apN.aDK().Ln().getId()) continue;
            bd_1.Is().j(mT2);
        }
    }
}

