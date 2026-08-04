/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.impl.packrect.BackingStoreManager;
import com.sun.opengl.impl.packrect.Rect;
import com.sun.opengl.util.j2d.TextureRenderer;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/*
 * Renamed from aKe
 */
class ake_1
implements BackingStoreManager {
    private Graphics2D dTn;
    final /* synthetic */ afg_0 bKu;

    ake_1(afg_0 afg_02) {
        this.bKu = afg_02;
    }

    public Object allocateBackingStore(int n2, int n3) {
        TextureRenderer textureRenderer = afg_0.a(this.bKu).intensityOnly() ? TextureRenderer.createAlphaOnlyRenderer(n2, n3, afg_0.b(this.bKu)) : new TextureRenderer(n2, n3, true, afg_0.b(this.bKu));
        textureRenderer.setSmoothing(afg_0.c(this.bKu));
        if (afg_0.aRF()) {
            System.err.println(" TextRenderer allocating backing store " + n2 + " x " + n3);
        }
        return textureRenderer;
    }

    public void deleteBackingStore(Object object) {
        ((TextureRenderer)object).dispose();
    }

    public boolean preExpand(Rect rect, int n2) {
        if (n2 == 0) {
            if (afg_0.aRF()) {
                System.err.println("Clearing unused entries in preExpand(): attempt number " + n2);
            }
            if (afg_0.d(this.bKu)) {
                this.bKu.flush();
            }
            afg_0.e(this.bKu);
            return true;
        }
        return false;
    }

    public void additionFailed(Rect rect, int n2) {
        afg_0.f(this.bKu).clear();
        afg_0.g(this.bKu).clear();
        afg_0.h(this.bKu).clearAllCacheEntries();
        if (afg_0.aRF()) {
            System.err.println(" *** Cleared all text because addition failed ***");
        }
    }

    public void beginMovement(Object object, Object object2) {
        Object object3;
        if (afg_0.d(this.bKu)) {
            this.bKu.flush();
            object3 = GLU.getCurrentGL();
            object3.glPopClientAttrib();
            if (afg_0.a(this.bKu, (GL)object3)) {
                try {
                    object3.glBindBuffer(34962, 0);
                }
                catch (Exception exception) {
                    afg_0.a(this.bKu, false);
                }
            }
            if (afg_0.i(this.bKu)) {
                ((TextureRenderer)object).endOrthoRendering();
            } else {
                ((TextureRenderer)object).end3DRendering();
            }
        }
        object3 = (TextureRenderer)object2;
        this.dTn = ((TextureRenderer)object3).createGraphics();
    }

    public void move(Object object, Rect rect, Object object2, Rect rect2) {
        TextureRenderer textureRenderer = (TextureRenderer)object;
        TextureRenderer textureRenderer2 = (TextureRenderer)object2;
        if (textureRenderer == textureRenderer2) {
            this.dTn.copyArea(rect.x(), rect.y(), rect.w(), rect.h(), rect2.x() - rect.x(), rect2.y() - rect.y());
        } else {
            Image image = textureRenderer.getImage();
            this.dTn.drawImage(image, rect2.x(), rect2.y(), rect2.x() + rect2.w(), rect2.y() + rect2.h(), rect.x(), rect.y(), rect.x() + rect.w(), rect.y() + rect.h(), null);
        }
    }

    public void endMovement(Object object, Object object2) {
        this.dTn.dispose();
        TextureRenderer textureRenderer = (TextureRenderer)object2;
        textureRenderer.markDirty(0, 0, textureRenderer.getWidth(), textureRenderer.getHeight());
        if (afg_0.d(this.bKu)) {
            if (afg_0.i(this.bKu)) {
                ((TextureRenderer)object2).beginOrthoRendering(afg_0.j(this.bKu), afg_0.k(this.bKu), afg_0.l(this.bKu));
            } else {
                ((TextureRenderer)object2).begin3DRendering();
            }
            GL gL = GLU.getCurrentGL();
            gL.glPushClientAttrib(-1);
            if (afg_0.m(this.bKu)) {
                if (afg_0.n(this.bKu) == null) {
                    ((TextureRenderer)object2).setColor(afg_0.o(this.bKu), afg_0.p(this.bKu), afg_0.q(this.bKu), afg_0.r(this.bKu));
                } else {
                    ((TextureRenderer)object2).setColor(afg_0.n(this.bKu));
                }
            }
        } else {
            afg_0.b(this.bKu, true);
        }
    }
}

