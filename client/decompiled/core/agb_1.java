/*
 * Decompiled with CFR 0.152.
 */
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.util.List;

/*
 * Renamed from agB
 */
class agb_1 {
    final int cux = -2;
    FontRenderContext cuy;
    int[] cuz;
    akm_1[] cuA;
    acr_0 cuB = new acr_0();
    final /* synthetic */ afg_0 bKu;

    agb_1(afg_0 afg_02, int n2) {
        this.bKu = afg_02;
        this.cuz = new int[512];
        this.cuA = new akm_1[n2];
        this.clearAllCacheEntries();
    }

    public int a(CharSequence charSequence, List list) {
        boolean bl2;
        list.clear();
        this.cuB.initFromCharSequence(charSequence);
        GlyphVector glyphVector = dt_2.MB().a(charSequence, afg_0.s(this.bKu), this.bKu.getFontRenderContext(), this.cuB);
        boolean bl3 = bl2 = glyphVector.getLayoutFlags() != 0;
        if (bl2) {
            list.add(new akm_1(this.bKu, ((Object)charSequence).toString(), false));
            return 1;
        }
        int n2 = glyphVector.getNumGlyphs();
        int n3 = 0;
        while (n3 < n2) {
            akm_1 akm_12 = this.a(charSequence, glyphVector, n3);
            if (akm_12 != null) {
                list.add(akm_12);
                ++n3;
                continue;
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (n3 < n2 && this.a(charSequence, glyphVector, n3) == null) {
                stringBuffer.append(charSequence.charAt(n3++));
            }
            list.add(new akm_1(this.bKu, stringBuffer.toString(), n3 < n2));
        }
        return list.size();
    }

    public void clearCacheEntry(int n2) {
        int n3 = this.cuz[n2];
        if (n3 != -2) {
            akm_1 akm_12 = this.cuA[n3];
            if (akm_12 != null) {
                akm_12.clear();
            }
            this.cuA[n3] = null;
        }
        this.cuz[n2] = -2;
    }

    public void clearAllCacheEntries() {
        for (int j = 0; j < this.cuz.length; ++j) {
            this.clearCacheEntry(j);
        }
    }

    public void a(akm_1 akm_12) {
        this.cuz[akm_12.getUnicodeID()] = akm_12.getGlyphCode();
        this.cuA[akm_12.getGlyphCode()] = akm_12;
    }

    public float getGlyphPixelWidth(char c) {
        akm_1 akm_12 = this.kC(c);
        if (akm_12 != null) {
            return akm_12.getAdvance();
        }
        afg_0.v((afg_0)this.bKu)[0] = c;
        GlyphVector glyphVector = afg_0.s(this.bKu).createGlyphVector(this.cuy, afg_0.v(this.bKu));
        return glyphVector.getGlyphMetrics(0).getAdvance();
    }

    private akm_1 a(CharSequence charSequence, GlyphVector glyphVector, int n2) {
        char c = charSequence.charAt(n2);
        if (c >= this.cuz.length) {
            return null;
        }
        int n3 = this.cuz[c];
        if (n3 != -2) {
            return this.cuA[n3];
        }
        afg_0.v((afg_0)this.bKu)[0] = c;
        GlyphVector glyphVector2 = afg_0.s(this.bKu).createGlyphVector(this.bKu.getFontRenderContext(), afg_0.v(this.bKu));
        return this.a(c, glyphVector2, glyphVector.getGlyphMetrics(n2));
    }

    private akm_1 kC(int n2) {
        if (n2 >= this.cuz.length) {
            return null;
        }
        int n3 = this.cuz[n2];
        if (n3 != -2) {
            return this.cuA[n3];
        }
        afg_0.v((afg_0)this.bKu)[0] = (char)n2;
        GlyphVector glyphVector = afg_0.s(this.bKu).createGlyphVector(this.bKu.getFontRenderContext(), afg_0.v(this.bKu));
        return this.a(n2, glyphVector, glyphVector.getGlyphMetrics(0));
    }

    private akm_1 a(int n2, GlyphVector glyphVector, GlyphMetrics glyphMetrics) {
        int n3 = glyphVector.getGlyphCode(0);
        if (n3 >= this.cuA.length) {
            return null;
        }
        akm_1 akm_12 = new akm_1(this.bKu, n2, n3, glyphMetrics.getAdvance(), glyphVector, this);
        this.a(akm_12);
        return akm_12;
    }
}

