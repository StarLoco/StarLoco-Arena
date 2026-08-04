/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import java.util.ArrayList;

/*
 * Renamed from NR
 */
public class nr_1 {
    public ArrayList jG;
    public float bAH = 1.0f;
    public float bAI = 0.0f;
    final /* synthetic */ Emitter bAJ;

    public nr_1(Emitter emitter) {
        this.bAJ = emitter;
    }

    public void a(float f, Emitter emitter) {
        if (this.jG != null) {
            int n2 = this.jG.size();
            for (int j = 0; j < n2; ++j) {
                md_2 md_22 = (md_2)this.jG.get(j);
                if (md_22.a(null, emitter.Lf, f, null)) continue;
                return;
            }
        }
        this.bAH += this.bAI;
    }

    public float aaZ() {
        return this.bAH;
    }
}

