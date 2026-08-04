/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aGt
 */
public class agt_1
extends uc_1 {
    final /* synthetic */ bh_1 zq;

    public agt_1(bh_1 bh_12, LuaState luaState) {
        this.zq = bh_12;
        super(luaState);
    }

    public final String getName() {
        return "playGroundSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("walkType", aos_1.elT, false), new LX("gain", aos_1.elT, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        Object object;
        DisplayedScreenElement displayedScreenElement;
        if (!this.zq.he.dLe) {
            return;
        }
        byte by = iq_1.bio.getType();
        byte by2 = (byte)this.hW(0);
        int n3 = 100;
        if (n2 > 1) {
            n3 = this.hW(1);
        }
        if ((displayedScreenElement = aga_0.aSG().b(this.zq.he.gn(), this.zq.he.go(), this.zq.he.gp(), pq_2.abX)) != null && (object = displayedScreenElement.atV().avY()) != null) {
            by = ((zl_1)object).aon();
        }
        try {
            object = aau_0.apB().h(by, by2);
            if (object == null) {
                a.debug((Object)"Impossible de trouver de GroundSoundData ad\u00e9quat");
                return;
            }
            if (!this.zq.he.dLk.dl(((avg_0)object).sq())) {
                return;
            }
            aau_0.apB().a(((avg_0)object).sq(), ((avg_0)object).getGain() * (float)n3 / 100.0f, 1, -1L, -1L, this.agC().Gf(), this.zq.he, ((avg_0)object).sr());
        }
        catch (Exception exception) {
            a.debug((Object)("soundExtension or soundPath not initialized " + exception));
        }
    }
}

