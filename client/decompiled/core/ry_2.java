/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.alea.display.RenderTree;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import org.apache.log4j.Logger;

/*
 * Renamed from RY
 */
public class ry_2
extends qs_2 {
    public static final short bKS = -1;
    private static final Logger a = Logger.getLogger(ry_2.class);
    private short ayK = (short)-1;
    private boolean bKT = true;
    private qs_2 bKU = null;

    public ry_2() {
        super(new RenderTree(), aga_0.aSF());
        this.an(false);
    }

    public kv_1 aeB() {
        return (kv_1)this.dsa;
    }

    protected void vm() {
        this.dsa = new kv_1(this, null);
    }

    public void a(int n2, acy_1 acy_12) {
    }

    public boolean b(MouseEvent mouseEvent) {
        return false;
    }

    public boolean mousePressed(MouseEvent mouseEvent) {
        return false;
    }

    public boolean c(MouseEvent mouseEvent) {
        return false;
    }

    public boolean d(MouseEvent mouseEvent) {
        return false;
    }

    public boolean e(MouseEvent mouseEvent) {
        return false;
    }

    public boolean f(MouseEvent mouseEvent) {
        return false;
    }

    public boolean a(MouseWheelEvent mouseWheelEvent) {
        return false;
    }

    public boolean a(KeyEvent keyEvent) {
        return false;
    }

    public boolean b(KeyEvent keyEvent) {
        return false;
    }

    public boolean c(KeyEvent keyEvent) {
        return false;
    }

    public boolean a(FocusEvent focusEvent) {
        return false;
    }

    public boolean b(FocusEvent focusEvent) {
        return false;
    }

    protected void vq() {
    }

    public void bI(int n2) {
        if (!this.cT(n2)) {
            return;
        }
        this.hB(n2);
        this.aq(true);
        this.cS(n2);
        this.aen.clear();
        this.vu();
        this.drX.clear();
        this.csb.clear();
    }

    protected void a(Entity entity) {
        this.aen.c(entity);
    }

    protected void cU(int n2) {
        throw new UnsupportedOperationException("pas de light sur les map de d\u00e9cor de fond");
    }

    public void aq(boolean bl2) {
        this.aep.b(this.dsa.aEO());
    }

    protected void p(float f, float f2) {
        this.aep.c(this);
    }

    public boolean vy() {
        return !this.aeC() || !this.aeD() || this.aep.is();
    }

    public void c(bx_2 bx_22) {
        this.a((short)-1, bx_22, 0.0f, 0.0f, 0.0f);
    }

    public void a(short s, bx_2 bx_22, float f) {
        this.a(s, bx_22, f, f, 1.0f);
    }

    public void a(short s, bx_2 bx_22, float f, float f2) {
        this.a(s, bx_22, f, f, f2);
    }

    public void a(short s, bx_2 bx_22, float f, float f2, float f3) {
        this.ao(true);
        bx_22.a(this);
        this.ayK = s;
        if (this.aeD()) {
            YR yR = this.bKU == null ? null : this.bKU.vn();
            this.aeB().a(yR, f, f2, f3);
            this.aep.a(xx_1.el(s));
            this.aep.ot(s);
            this.an(true);
            this.bk(true);
            if (this.aeC()) {
                bx_22.a(this, this.bKU, !this.aeE());
            }
        }
    }

    public void a(UX uX, bx_2 bx_22) {
        this.cn(uX.bRY);
        this.a(uX.ayK, bx_22, uX.bRZ, uX.bSa, uX.aaw);
    }

    public final boolean aeC() {
        return (aga_0.aoq() & 2) == 2;
    }

    public boolean aeD() {
        return this.ayK != -1;
    }

    public void d(qs_2 qs_22) {
        this.bKU = qs_22;
    }

    public boolean aeE() {
        return this.bKT;
    }

    public void cn(boolean bl2) {
        this.bKT = bl2;
    }

    private void hB(int n2) {
        if (this.bKU == null) {
            return;
        }
        YR yR = this.bKU.vn();
        this.aeB().a(yR, this.bKT, n2);
    }
}

