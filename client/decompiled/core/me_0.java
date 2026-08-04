/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.framework.graphics.engine.transformer.BatchTransformer;
import java.util.ArrayList;

/*
 * Renamed from Me
 */
public class me_0 {
    static final aBp bta = new aBp();
    public float btb = 0.001f;
    public float aaS = 2.0f;
    public float bsE = 10.0f;
    private float btc = 0.0f;

    public void a(aga_0 aga_02, int n2) {
        this.btc += (float)n2;
        ArrayList arrayList = aga_02.aSK();
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            DisplayedScreenElement[] displayedScreenElementArray;
            abb_0 abb_02 = (abb_0)arrayList.get(j);
            if (abb_02 == null || (displayedScreenElementArray = abb_02.apJ()) == null) continue;
            for (DisplayedScreenElement displayedScreenElement : displayedScreenElementArray) {
                float f;
                int n3;
                if (!displayedScreenElement.isVisible() || !bta.contains(n3 = displayedScreenElement.atV().avY().getId())) continue;
                float f2 = displayedScreenElement.gn();
                float f3 = (float)Math.sqrt(f2 * f2 + (f = (float)displayedScreenElement.go()) * f) + this.aaS * this.btc / 1000.0f;
                float f4 = this.btb * ej_0.k((float)Math.PI * 2 * f3 / this.bsE);
                if (f4 < 0.0f) {
                    f4 *= 0.2f;
                }
                BatchTransformer batchTransformer = displayedScreenElement.atW().aUM();
                float f5 = batchTransformer.ki().Pn()[12];
                float f6 = batchTransformer.ki().Pn()[13];
                batchTransformer.ki().o(new float[]{1.0f + f4, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f + f4, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f5, f6, 0.0f, 1.0f});
            }
        }
    }

    static {
        bta.nk(10208);
        bta.nk(11138);
        bta.nk(10036);
        bta.nk(10211);
        bta.nk(17702);
        bta.nk(17704);
        bta.nk(17712);
        bta.nk(17713);
        bta.nk(17755);
        bta.nk(17756);
    }
}

