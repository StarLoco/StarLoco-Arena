/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.RenderTreeStencil;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 * Renamed from amo
 */
public final class amo_2 {
    public static final amo_2 cGH = new amo_2();
    private boolean aK = false;
    private JFrame cDM;
    private kn_0 cGI;
    private RenderTreeStencil cGJ;

    private amo_2() {
    }

    public void initialize() {
        if (this.aK) {
            return;
        }
        SwingUtilities.invokeLater(new xb_0(this));
        this.aK = true;
    }

    public void c(RenderTreeStencil renderTreeStencil) {
        this.cGJ = renderTreeStencil;
    }

    static /* synthetic */ JFrame a(amo_2 amo_22, JFrame jFrame) {
        amo_22.cDM = jFrame;
        return amo_22.cDM;
    }

    static /* synthetic */ kn_0 a(amo_2 amo_22, kn_0 kn_02) {
        amo_22.cGI = kn_02;
        return amo_22.cGI;
    }

    static /* synthetic */ kn_0 a(amo_2 amo_22) {
        return amo_22.cGI;
    }

    static /* synthetic */ JFrame b(amo_2 amo_22) {
        return amo_22.cDM;
    }

    static /* synthetic */ RenderTreeStencil c(amo_2 amo_22) {
        return amo_22.cGJ;
    }
}

