/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;

/*
 * Renamed from aFd
 */
public class afd_2 {
    public final int asw;
    public final Anm dEZ;
    public final ju_2 dFa;
    private Ze dFb = null;

    public afd_2(int n2, Anm anm, ju_2 ju_22) {
        this.asw = n2;
        this.dEZ = anm;
        this.dFa = ju_22;
    }

    public afd_2(afd_2 afd_22) {
        this.asw = afd_22.asw;
        this.dEZ = afd_22.dEZ;
        this.dFa = null;
    }

    public final boolean aRu() {
        return this.dEZ.is() && this.dEZ.qG.auH();
    }

    public final Ze aRv() {
        if (!this.aRu()) {
            return null;
        }
        if (this.dFb != null) {
            return this.dFb;
        }
        assert (this.dFa != null);
        String string = this.dFa.getName();
        assert (string != null);
        Ze[] zeArray = this.dEZ.qG.auI();
        for (int j = 0; j < zeArray.length; ++j) {
            if (!string.contains(zeArray[j].ccF)) continue;
            this.dFb = zeArray[j];
            break;
        }
        return this.dFb;
    }
}

