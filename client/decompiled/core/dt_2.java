/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.text.CharacterIterator;
import org.apache.log4j.Logger;

/*
 * Renamed from DT
 */
public class dt_2 {
    private static final Logger a = Logger.getLogger(dt_2.class);
    private static dt_2 aPx = new dt_2();
    private azR aPy = new azR(this, null);
    private static int aPz = 15000;
    private int aPA = 0;
    private final lb_0 aPB = new lb_0();

    private dt_2() {
    }

    public static final dt_2 MB() {
        return aPx;
    }

    public GlyphVector a(CharSequence charSequence, Font font, FontRenderContext fontRenderContext, CharacterIterator characterIterator) {
        int n2 = charSequence.hashCode() + font.hashCode();
        aNu aNu2 = (aNu)this.aPB.get(n2);
        if (aNu2 == null) {
            aNu2 = aNu.a(font.createGlyphVector(fontRenderContext, characterIterator));
            this.aPB.c(n2, aNu2);
        }
        aNu2.bi(true);
        return aNu2.aXw();
    }

    public void update(int n2) {
        this.aPA += n2;
        if (this.aPA > aPz) {
            if (!this.aPB.isEmpty()) {
                this.aPB.a(this.aPy);
            }
            this.aPA = 0;
        }
    }

    static /* synthetic */ Logger dT() {
        return a;
    }

    static /* synthetic */ lb_0 a(dt_2 dt_22) {
        return dt_22.aPB;
    }
}

