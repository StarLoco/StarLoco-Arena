/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.impl.Debug;
import com.sun.opengl.impl.packrect.Rect;
import com.sun.opengl.impl.packrect.RectanglePacker;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.j2d.TextureRenderer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.media.opengl.GL;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLContext;
import javax.media.opengl.glu.GLU;
import org.apache.log4j.Logger;

/*
 * Renamed from aFg
 */
public class afg_0
extends af_1 {
    protected static final Logger a = Logger.getLogger(afg_0.class);
    private static final boolean DEBUG = Debug.debug("TextRenderer");
    private static final boolean dFj = false;
    private static final boolean dFk = false;
    static final int dFl = 256;
    private static final int dFm = 100;
    private static final float dFn = 0.7f;
    static final int dFo = 100;
    static final int dFp = 3;
    static final int dFq = 2;
    static final int dFr = 4;
    static final int dFs = 400;
    static final int dFt = 1200;
    static final int dFu = 800;
    static final int dFv = 4800;
    static final int dFw = 3200;
    static final int dFx = 12;
    static final int dFy = 8;
    private Font font;
    private boolean dFz;
    private boolean dFA;
    private boolean dFB;
    private RectanglePacker dFC;
    private boolean dFD;
    private bE dFE;
    private TextureRenderer dFF;
    private Graphics2D dFG;
    private FontRenderContext dFH;
    private Map dFI = new HashMap();
    private agb_1 dFJ;
    private int dFK;
    private boolean dFL;
    private boolean dFM;
    private int dFN;
    private int dFO;
    private boolean dFP;
    private boolean dFQ;
    private float dFR;
    private float dFS;
    private float dFT;
    private float dFU;
    private Color dFV;
    private boolean dFW;
    private Frame dFX;
    private boolean dFY;
    axl_0 dFZ;
    private boolean dGa = true;
    private boolean dGb;
    private boolean dGc;
    private boolean dGd = true;
    private static final List dGe = new ArrayList();
    private abQ dGf;
    private char[] dGg = new char[1];

    public afg_0(Font font) {
        this(font, false, false, null, false);
    }

    public afg_0(Font font, boolean bl2) {
        this(font, false, false, null, bl2);
    }

    public afg_0(Font font, boolean bl2, boolean bl3) {
        this(font, bl2, bl3, null, false);
    }

    public afg_0(Font font, boolean bl2, boolean bl3, bE bE2) {
        this(font, bl2, bl3, bE2, false);
    }

    public afg_0(Font font, boolean bl2, boolean bl3, bE bE2, boolean bl4) {
        this.font = font;
        this.dFz = bl2;
        this.dFA = bl3;
        this.dFB = bl4;
        this.dFC = new RectanglePacker(new ake_1(this), 256, ej_0.aq(font.getSize()));
        if (bE2 == null) {
            bE2 = new ahk_1();
        }
        this.dFE = bE2;
        this.dFJ = new agb_1(this, font.getNumGlyphs());
    }

    public Rectangle2D getBounds(String string) {
        return this.getBounds((CharSequence)string);
    }

    public Rectangle2D getBounds(CharSequence charSequence) {
        Rect rect = null;
        rect = (Rect)this.dFI.get(charSequence);
        if (rect != null) {
            adh_2 adh_22 = (adh_2)rect.getUserData();
            return new Rectangle2D.Double(-adh_22.aPz().x, -adh_22.aPz().y, rect.w(), rect.h());
        }
        return this.b(this.dFE.getBounds(charSequence, this.font, this.getFontRenderContext()));
    }

    private Font aRz() {
        return this.font;
    }

    public FontRenderContext getFontRenderContext() {
        if (this.dFH == null) {
            this.dFH = this.aRB().getFontRenderContext();
        }
        return this.dFH;
    }

    public void beginRendering(int n2, int n3) {
        this.beginRendering(n2, n3, true);
    }

    public void beginRendering(int n2, int n3, boolean bl2) {
        this.a(true, n2, n3, bl2);
    }

    public void begin3DRendering() {
        this.a(false, 0, 0, false);
    }

    public void setColor(Color color) {
        boolean bl2;
        boolean bl3 = bl2 = this.dFQ && this.dFV != null && color.equals(this.dFV);
        if (!bl2) {
            this.aRD();
        }
        this.aRA().setColor(color);
        this.dFQ = true;
        this.dFV = color;
    }

    public void setColor(float f, float f2, float f3, float f4) {
        boolean bl2;
        boolean bl3 = bl2 = this.dFQ && this.dFV == null && f == this.dFR && f2 == this.dFS && f3 == this.dFT && f4 == this.dFU;
        if (!bl2) {
            this.aRD();
        }
        this.aRA().setColor(f, f2, f3, f4);
        this.dFQ = true;
        this.dFR = f;
        this.dFS = f2;
        this.dFT = f3;
        this.dFU = f4;
        this.dFV = null;
    }

    public void draw(CharSequence charSequence, int n2, int n3) {
        this.draw3D(charSequence, n2, n3, 0.0f, 1.0f);
    }

    public void a(char[] cArray, int n2, int n3) {
        this.draw3D(new String(cArray), n2, n3, cArray.length, 1.0f);
    }

    public void a(char[] cArray, int n2, int n3, float f) {
        this.draw3D(new String(cArray), n2, n3, cArray.length, f);
    }

    public void a(char[] cArray, int n2, int n3, int n4, float f) {
        this.draw3D(new String(cArray), n2, n3, n4, f);
    }

    public void a(char[] cArray, int n2, int n3, int n4) {
        this.draw3D(new String(cArray), n2, n3, n4, 1.0f);
    }

    public void draw3D(CharSequence charSequence, float f, float f2, float f3, float f4) {
        this.b(charSequence, f, charSequence.length(), f2, f3, f4);
    }

    public void a(CharSequence charSequence, float f, float f2, float f3, float f4, float f5) {
        this.b(charSequence, f, f2, f3, f4, f5);
    }

    public float getCharWidth(char c) {
        return this.dFJ.getGlyphPixelWidth(c);
    }

    public void flush() {
        this.aRD();
    }

    public void endRendering() {
        this.eO(true);
    }

    public void end3DRendering() {
        this.eO(false);
    }

    public void dispose() {
        this.dFC.dispose();
        this.dFC = null;
        this.dFF = null;
        this.dFG = null;
        this.dFH = null;
        if (this.dFX != null) {
            this.dFX.dispose();
        }
    }

    private static Rectangle2D a(Rectangle2D rectangle2D) {
        int n2 = (int)Math.floor(rectangle2D.getMinX()) - 1;
        int n3 = (int)Math.floor(rectangle2D.getMinY()) - 1;
        int n4 = (int)Math.ceil(rectangle2D.getMaxX()) + 1;
        int n5 = (int)Math.ceil(rectangle2D.getMaxY()) + 1;
        return new Rectangle2D.Double(n2, n3, n4 - n2, n5 - n3);
    }

    private Rectangle2D b(Rectangle2D rectangle2D) {
        int n2 = (int)Math.max(1.0, 0.015 * (double)this.font.getSize());
        return new Rectangle2D.Double((int)Math.floor(rectangle2D.getMinX() - (double)n2), (int)Math.floor(rectangle2D.getMinY() - (double)n2), (int)Math.ceil(rectangle2D.getWidth() + (double)(2 * n2)), (int)Math.ceil(rectangle2D.getHeight()) + 2 * n2);
    }

    private TextureRenderer aRA() {
        TextureRenderer textureRenderer = (TextureRenderer)this.dFC.getBackingStore();
        if (textureRenderer != this.dFF) {
            if (this.dFG != null) {
                this.dFG.dispose();
                this.dFG = null;
                this.dFH = null;
            }
            this.dFF = textureRenderer;
        }
        return this.dFF;
    }

    private Graphics2D aRB() {
        TextureRenderer textureRenderer = this.aRA();
        if (this.dFG == null) {
            this.dFG = textureRenderer.createGraphics();
            this.dFG.setComposite(AlphaComposite.Src);
            this.dFG.setColor(Color.WHITE);
            this.dFG.setFont(this.font);
            this.dFG.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.dFz ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            this.dFG.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.dFA ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        }
        return this.dFG;
    }

    private void a(boolean bl2, int n2, int n3, boolean bl3) {
        if (DEBUG && !this.dFY) {
            this.aRE();
        }
        this.dFL = true;
        this.dFM = bl2;
        this.dFN = n2;
        this.dFO = n3;
        this.dFP = bl3;
        if (bl2) {
            this.aRA().beginOrthoRendering(n2, n3, bl3);
        } else {
            this.aRA().begin3DRendering();
        }
        GL gL = GLU.getCurrentGL();
        gL.glPushClientAttrib(-1);
        if (!this.dFD) {
            int[] nArray = new int[1];
            gL.glGetIntegerv(3379, nArray, 0);
            this.dFC.setMaxSize(nArray[0], nArray[0]);
            this.dFD = true;
        }
        if (this.dFW && this.dFQ) {
            if (this.dFV == null) {
                this.aRA().setColor(this.dFR, this.dFS, this.dFT, this.dFU);
            } else {
                this.aRA().setColor(this.dFV);
            }
            this.dFW = false;
        }
        if (this.dFB && !this.aRA().isUsingAutoMipmapGeneration()) {
            if (DEBUG) {
                System.err.println("Disabled mipmapping in TextRenderer");
            }
            this.dFB = false;
        }
    }

    private void eO(boolean bl2) {
        this.aRD();
        this.dFL = false;
        GL gL = GLU.getCurrentGL();
        gL.glPopClientAttrib();
        if (this.s(gL)) {
            try {
                gL.glBindBuffer(34962, 0);
            }
            catch (Exception exception) {
                this.dGb = false;
            }
        }
        if (bl2) {
            this.aRA().endOrthoRendering();
        } else {
            this.aRA().end3DRendering();
        }
        if (++this.dFK >= 100) {
            this.dFK = 0;
            if (DEBUG) {
                System.err.println("Clearing unused entries in endRendering()");
            }
            this.aRC();
        }
    }

    private void aRC() {
        ArrayList arrayList = new ArrayList();
        this.dFC.visit(new rm_2(this, arrayList));
        for (int j = 0; j < arrayList.size(); ++j) {
            Rect rect = (Rect)arrayList.get(j);
            this.dFC.remove(rect);
            this.dFI.remove(((adh_2)rect.getUserData()).aPy());
            int n2 = ((adh_2)rect.getUserData()).dyS;
            if (n2 <= 0) continue;
            this.dFJ.clearCacheEntry(n2);
        }
        float f = this.dFC.verticalFragmentationRatio();
        if (!arrayList.isEmpty() && f > 0.7f) {
            if (DEBUG) {
                System.err.println("Compacting TextRenderer backing store due to vertical fragmentation " + f);
            }
            this.dFC.compact();
        }
        if (DEBUG) {
            this.aRA().markDirty(0, 0, this.aRA().getWidth(), this.aRA().getHeight());
        }
    }

    private void b(CharSequence charSequence, float f, float f2, float f3, float f4, float f5) {
        int n2 = this.dFJ.a(charSequence, dGe);
        for (int j = 0; j < n2; ++j) {
            akm_1 akm_12 = (akm_1)dGe.get(j);
            float f6 = akm_12.b(f, f2, f3, f4, f5);
            f += f6 * f5;
        }
    }

    private void aRD() {
        if (this.dFZ != null) {
            axl_0.a(this.dFZ);
        }
    }

    private void c(CharSequence charSequence, float f, float f2, float f3, float f4, float f5) {
        Rectangle2D rectangle2D;
        Object object;
        Object object2;
        String string = charSequence instanceof String ? (String)charSequence : ((Object)charSequence).toString();
        Rect rect = (Rect)this.dFI.get(string);
        if (rect == null) {
            object2 = this.aRB();
            object = afg_0.a(this.dFE.getBounds(string, this.font, this.getFontRenderContext()));
            rectangle2D = this.b((Rectangle2D)object);
            Point point = new Point((int)(-rectangle2D.getMinX()), (int)(-rectangle2D.getMinY()));
            rect = new Rect(0, 0, (int)rectangle2D.getWidth(), (int)rectangle2D.getHeight(), new adh_2(string, point, (Rectangle2D)object, -1));
            this.dFC.add(rect);
            this.dFI.put(string, rect);
            object2 = this.aRB();
            int n2 = rect.x() + point.x;
            int n3 = rect.y() + point.y;
            ((Graphics2D)object2).setComposite(AlphaComposite.Clear);
            ((Graphics)object2).fillRect(rect.x(), rect.y(), rect.w(), rect.h());
            ((Graphics2D)object2).setComposite(AlphaComposite.Src);
            this.dFE.draw((Graphics2D)object2, string, n2, n3);
            this.aRA().markDirty(rect.x(), rect.y(), rect.w(), rect.h());
        }
        object2 = this.aRA();
        object = (adh_2)rect.getUserData();
        ((adh_2)object).aPE();
        rectangle2D = ((adh_2)object).aPC();
        ((TextureRenderer)object2).draw3DRect(f - f5 * (float)((adh_2)object).aPA(), f3 - f5 * ((float)rectangle2D.getHeight() - (float)((adh_2)object).aPB()), f4, rect.x() + (((adh_2)object).aPz().x - ((adh_2)object).aPA()), ((TextureRenderer)object2).getHeight() - rect.y() - (int)rectangle2D.getHeight() - (((adh_2)object).aPz().y - ((adh_2)object).aPB()), (int)(rectangle2D.getWidth() - (double)f2), (int)rectangle2D.getHeight(), f5);
    }

    private void aRE() {
        this.dFX = new Frame("TextRenderer Debug Output");
        GLCanvas gLCanvas = new GLCanvas(new GLCapabilities(), null, GLContext.getCurrent(), null);
        gLCanvas.addGLEventListener(new aLE(this, this.dFX));
        this.dFX.add(gLCanvas);
        FPSAnimator fPSAnimator = new FPSAnimator(gLCanvas, 10);
        this.dFX.addWindowListener(new rl_0(this, fPSAnimator));
        this.dFX.setSize(256, 256);
        this.dFX.setVisible(true);
        fPSAnimator.start();
        this.dFY = true;
    }

    public String getFontName() {
        Font font = this.aRz();
        if (font == null) {
            return null;
        }
        String string = font.isBold() ? (font.isItalic() ? "bolditalic" : "bold") : (font.isItalic() ? "italic" : "plain");
        return font.getFamily() + '-' + string + '-' + font.getSize();
    }

    public int aA() {
        int n2 = 0;
        if (this.font.isBold()) {
            n2 |= 1;
        }
        if (this.font.isItalic()) {
            n2 |= 2;
        }
        return n2;
    }

    public ma_1 getFont() {
        if (this.font == null) {
            return null;
        }
        if (this.dGf == null) {
            this.dGf = new abQ(this.font, true, false);
        }
        return this.dGf;
    }

    public ma_1 a(int n2, float f) {
        if (this.font == null) {
            return null;
        }
        Font font = this.font.deriveFont(n2, f);
        return new abQ(font, true, false);
    }

    public int a(char c) {
        FontRenderContext fontRenderContext = this.getFontRenderContext();
        Font font = this.aRz();
        Rectangle2D rectangle2D = font.getStringBounds(String.valueOf(c), fontRenderContext);
        return (int)rectangle2D.getWidth();
    }

    public int aB() {
        Font font = this.aRz();
        FontRenderContext fontRenderContext = this.getFontRenderContext();
        Rectangle2D rectangle2D = font.getMaxCharBounds(fontRenderContext);
        return (int)rectangle2D.getWidth();
    }

    public int aC() {
        Font font = this.aRz();
        FontRenderContext fontRenderContext = this.getFontRenderContext();
        Rectangle2D rectangle2D = font.getMaxCharBounds(fontRenderContext);
        return (int)rectangle2D.getHeight();
    }

    public int a(String string, int n2, int n3) {
        for (int j = Math.min(string.length() - 1, n2 - 1); j >= 0; --j) {
            if (this.g(string.substring(0, j + 1)) >= n3) continue;
            return j + 1;
        }
        return 0;
    }

    public int g(String string) {
        Rectangle2D rectangle2D = this.font.getStringBounds(string, this.getFontRenderContext());
        return (int)rectangle2D.getWidth();
    }

    public int h(String string) {
        Rectangle2D rectangle2D = this.font.getStringBounds(string, this.getFontRenderContext());
        return (int)rectangle2D.getHeight();
    }

    public int i(String string) {
        LineMetrics lineMetrics = this.font.getLineMetrics(string, this.getFontRenderContext());
        return (int)Math.ceil(lineMetrics.getDescent());
    }

    public boolean aD() {
        return false;
    }

    public void setUseVertexArrays(boolean bl2) {
        this.dGa = bl2;
    }

    public boolean getUseVertexArrays() {
        return this.dGa;
    }

    public void setSmoothing(boolean bl2) {
        this.dGd = bl2;
        this.aRA().setSmoothing(bl2);
    }

    public boolean getSmoothing() {
        return this.dGd;
    }

    private boolean s(GL gL) {
        if (!this.dGc) {
            this.dGb = gL.isExtensionAvailable("GL_VERSION_1_5");
            this.dGc = true;
        }
        return this.dGb;
    }

    static /* synthetic */ bE a(afg_0 afg_02) {
        return afg_02.dFE;
    }

    static /* synthetic */ boolean b(afg_0 afg_02) {
        return afg_02.dFB;
    }

    static /* synthetic */ boolean c(afg_0 afg_02) {
        return afg_02.dGd;
    }

    static /* synthetic */ boolean aRF() {
        return DEBUG;
    }

    static /* synthetic */ boolean d(afg_0 afg_02) {
        return afg_02.dFL;
    }

    static /* synthetic */ void e(afg_0 afg_02) {
        afg_02.aRC();
    }

    static /* synthetic */ RectanglePacker f(afg_0 afg_02) {
        return afg_02.dFC;
    }

    static /* synthetic */ Map g(afg_0 afg_02) {
        return afg_02.dFI;
    }

    static /* synthetic */ agb_1 h(afg_0 afg_02) {
        return afg_02.dFJ;
    }

    static /* synthetic */ boolean a(afg_0 afg_02, GL gL) {
        return afg_02.s(gL);
    }

    static /* synthetic */ boolean a(afg_0 afg_02, boolean bl2) {
        afg_02.dGb = bl2;
        return afg_02.dGb;
    }

    static /* synthetic */ boolean i(afg_0 afg_02) {
        return afg_02.dFM;
    }

    static /* synthetic */ int j(afg_0 afg_02) {
        return afg_02.dFN;
    }

    static /* synthetic */ int k(afg_0 afg_02) {
        return afg_02.dFO;
    }

    static /* synthetic */ boolean l(afg_0 afg_02) {
        return afg_02.dFP;
    }

    static /* synthetic */ boolean m(afg_0 afg_02) {
        return afg_02.dFQ;
    }

    static /* synthetic */ Color n(afg_0 afg_02) {
        return afg_02.dFV;
    }

    static /* synthetic */ float o(afg_0 afg_02) {
        return afg_02.dFR;
    }

    static /* synthetic */ float p(afg_0 afg_02) {
        return afg_02.dFS;
    }

    static /* synthetic */ float q(afg_0 afg_02) {
        return afg_02.dFT;
    }

    static /* synthetic */ float r(afg_0 afg_02) {
        return afg_02.dFU;
    }

    static /* synthetic */ boolean b(afg_0 afg_02, boolean bl2) {
        afg_02.dFW = bl2;
        return afg_02.dFW;
    }

    static /* synthetic */ void a(afg_0 afg_02, CharSequence charSequence, float f, float f2, float f3, float f4, float f5) {
        afg_02.c(charSequence, f, f2, f3, f4, f5);
    }

    static /* synthetic */ Font s(afg_0 afg_02) {
        return afg_02.font;
    }

    static /* synthetic */ TextureRenderer t(afg_0 afg_02) {
        return afg_02.aRA();
    }

    static /* synthetic */ Rectangle2D c(Rectangle2D rectangle2D) {
        return afg_0.a(rectangle2D);
    }

    static /* synthetic */ Rectangle2D a(afg_0 afg_02, Rectangle2D rectangle2D) {
        return afg_02.b(rectangle2D);
    }

    static /* synthetic */ Graphics2D u(afg_0 afg_02) {
        return afg_02.aRB();
    }

    static /* synthetic */ char[] v(afg_0 afg_02) {
        return afg_02.dGg;
    }

    static /* synthetic */ boolean w(afg_0 afg_02) {
        return afg_02.dGa;
    }
}

