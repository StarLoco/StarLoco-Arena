/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.transformer.BatchTransformer;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import org.apache.log4j.Logger;

public class agV
extends rk_2
implements aiQ {
    private static Logger a = Logger.getLogger(agV.class);
    public static final boolean cuH = false;
    private GL go;
    private alj_0 cuI;
    private ago_2 cuJ;
    private bd cuK;
    private amf_2 cuL = new amf_2(0.0f, 0.0f, 0.0f);
    private aGs clE = new aGs();
    private final ArrayList G = new ArrayList();
    private final ArrayList cuM = new ArrayList();
    private int fb;
    private int fc;
    private float KT = 1.0f;
    private float KU = 1.0f;
    private boolean cuN = false;
    private oz_0 cuO = null;
    private static final avz aem = new avz();
    public static long cuP;
    public static long cuQ;
    public static long cuR;
    public static long cuS;
    public static long cuT;
    public static long cuU;
    public static long cuV;
    public static long cuW;
    private static final boolean cuX = false;
    private static final int cuY = 5;
    private static final long cuZ = 3000000L;
    public static int cva;
    private static long cvb;
    private static boolean cvc;

    public agV() {
        this.a(add_1.aOG());
    }

    public void a(amn_1 amn_12) {
        if (!this.G.contains(amn_12)) {
            this.G.add(amn_12);
        }
    }

    public void b(amn_1 amn_12) {
        if (this.G.contains(amn_12)) {
            this.G.remove(amn_12);
        }
    }

    public void a(sv_0 sv_02) {
        if (!this.cuM.contains(sv_02)) {
            this.cuM.add(sv_02);
        }
    }

    public void b(sv_0 sv_02) {
        if (this.cuM.contains(sv_02)) {
            this.cuM.remove(sv_02);
        }
    }

    public oz_0 awF() {
        return this.cuO;
    }

    public void P(int n2, int n3) {
        awo_0 awo_02;
        this.J((float)n2 / 1024.0f, (float)n3 / 768.0f);
        n2 = (int)((float)n2 / this.KT);
        n3 = (int)((float)n3 / this.KU);
        super.P(n2, n3);
        int n4 = n2 - this.fb;
        int n5 = n3 - this.fc;
        this.fb = n2;
        this.fc = n3;
        if (this.cuI != null) {
            this.cuI.cm(this.fb, this.fc);
        }
        if (this.cuJ != null) {
            this.cuJ.setSize(this.fb, this.fc);
        }
        if ((awo_02 = add_1.aOG().aOK()) != null) {
            awo_02.aJz();
        }
        this.bq(n4, n5);
    }

    public ago_2 getMasterRootContainer() {
        return this.cuJ;
    }

    public boolean awG() {
        return this.cuN;
    }

    public void init(GLAutoDrawable gLAutoDrawable) {
        try {
            super.init(gLAutoDrawable);
            ((pg_2)gLAutoDrawable).setFocusTraversalKeysEnabled(false);
            this.go = gLAutoDrawable.getGL();
            this.cuI = alj_0.aWw();
            this.cuI.cm(this.fb, this.fc);
            this.cuI.a(this.go, gLAutoDrawable);
            afq_1 afq_12 = add_1.aOG().azj();
            aji_1 aji_12 = afq_12.lf("masterRootContainer");
            this.cuJ = ago_2.getInstance();
            this.cuJ.b();
            this.cuJ.setSize(this.fb, this.fc);
            this.cuJ.setElementMap(aji_12);
            BatchTransformer batchTransformer = this.cuJ.getEntity().aUM();
            avz avz2 = (avz)batchTransformer.aI(0);
            avz2.m(this.KT, this.KU, 1.0f);
            batchTransformer.b(0, avz2);
            this.awK();
        }
        catch (Throwable throwable) {
            a.error((Object)"Exception", throwable);
        }
    }

    private void g(GL gL) {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        qp_22.adV.nO(0);
        vo_12.cr(true);
        vo_12.a(air.cyd, air.cye);
        vo_12.a(jq_0.bmI);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        vo_12.a(jq_0.bmH);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        vo_12.a(jq_0.bmG);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        TV tV = this.Ni();
        int n2 = (int)tV.getX();
        int n3 = (int)tV.getY();
        int n4 = (int)tV.getWidth();
        int n5 = (int)tV.getHeight();
        float f = (float)(tV.getWidth() - tV.getX()) / 2.0f;
        float f2 = (float)(tV.getHeight() - tV.getY()) / 2.0f;
        gL.glViewport(n2, n3, n4, n5);
        gL.glOrtho(-f, f, -f2, f2, 0.0, 65535.0);
        aem.OH();
        aem.e(0.0f, -2.0f * f2, 0.0f);
        qp_22.c(aem.ki());
        vo_1.aik().reset();
    }

    private void p(GL gL) {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        if (qp_22.LW()) {
            qp_22.cO(0);
        }
        gL.glAlphaFunc(516, 0.0f);
    }

    public void awH() {
        this.bq();
    }

    public void h(GL gL) {
        if (!aoz_1.aYF().aYG()) {
            return;
        }
        this.g(gL);
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        try {
            if (this.cuJ != null && this.cuJ.getVisible()) {
                this.cuJ.getEntity().a(qp_22);
                if (this.cuO != null) {
                    this.cuO.ap().a(qp_22);
                }
            }
            int n2 = this.cuM.size();
            for (int j = 0; j < n2; ++j) {
                ((sv_0)this.cuM.get(j)).a(qp_22, this.cuI);
            }
        }
        catch (Throwable throwable) {
            a.error((Object)"Exception", throwable);
        }
        vo_1.aik().cv(false);
        vo_1.aik().n(qp_22);
        this.p(gL);
    }

    public static boolean awI() {
        return true;
    }

    public static void awJ() {
    }

    public void bI(int n2) {
        if (!aoz_1.aYF().aYG()) {
            return;
        }
        if (this.cuO == null) {
            this.cuO = new oz_0(abw_1.kh("arial-plain-10"));
        }
        if (this.cuJ != null && !this.cuJ.getVisible()) {
            return;
        }
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        long l5 = 0L;
        try {
            super.bI(n2);
            this.kH(n2);
            if (this.cuJ == null) {
                return;
            }
            if (atQ.aGT().aGU()) {
                atQ.aGT().sort();
            }
            atQ.aGT().aGV();
            bd.ce().v(n2);
            afo_1.dGT.bI(n2);
        }
        catch (Throwable throwable) {
            a.error((Object)"Exception", throwable);
        }
    }

    private void J(float f, float f2) {
        if (f >= 0.9f && f2 >= 0.9f) {
            this.KT = 1.0f;
            this.KU = 1.0f;
        } else {
            this.KU = this.KT = Math.min(f, f2);
        }
        if (this.cuJ != null) {
            this.e(this.cuJ.getEntity());
        }
    }

    public float getScale() {
        return this.KT;
    }

    public boolean isScaled() {
        return this.KT != 1.0f || this.KU != 1.0f;
    }

    public void e(Entity entity) {
        BatchTransformer batchTransformer = entity.aUM();
        avz avz2 = (avz)batchTransformer.aI(0);
        avz2.m(this.KT, this.KU, 1.0f);
        batchTransformer.b(0, avz2);
    }

    public void f(nm_0 nm_02) {
        nm_02.setX((int)((float)nm_02.getX() * this.KT));
        nm_02.setY((int)((float)nm_02.getY() * this.KU));
        nm_02.setWidth((int)((float)nm_02.getWidth() * this.KT));
        nm_02.setHeight((int)((float)nm_02.getHeight() * this.KU));
    }

    public int kF(int n2) {
        return (int)((float)n2 / this.KT);
    }

    public int kG(int n2) {
        return (int)((float)n2 / this.KU);
    }

    public boolean b(KeyEvent keyEvent) {
        if (this.cuJ != null) {
            return this.cuJ.b(keyEvent);
        }
        return false;
    }

    public boolean c(KeyEvent keyEvent) {
        if (this.cuJ != null) {
            return this.cuJ.c(keyEvent);
        }
        return false;
    }

    public boolean a(KeyEvent keyEvent) {
        if (this.cuJ != null) {
            return this.cuJ.a(keyEvent);
        }
        return false;
    }

    public boolean b(MouseEvent mouseEvent) {
        return false;
    }

    public boolean d(MouseEvent mouseEvent) {
        return false;
    }

    public boolean e(MouseEvent mouseEvent) {
        return false;
    }

    public boolean f(MouseEvent mouseEvent) {
        if (this.cuJ != null) {
            MouseEvent mouseEvent2 = mouseEvent;
            if (this.isScaled()) {
                mouseEvent2 = new MouseEvent(mouseEvent.getComponent(), mouseEvent.getID(), mouseEvent.getWhen(), mouseEvent.getModifiers(), this.kF(mouseEvent.getX()), this.kG(mouseEvent.getY()), mouseEvent.getClickCount(), mouseEvent.isPopupTrigger(), mouseEvent.getButton());
            }
            return this.cuJ.g(mouseEvent2);
        }
        return false;
    }

    public boolean g(MouseEvent mouseEvent) {
        if (this.cuJ != null) {
            MouseEvent mouseEvent2 = mouseEvent;
            if (this.isScaled()) {
                mouseEvent2 = new MouseEvent(mouseEvent.getComponent(), mouseEvent.getID(), mouseEvent.getWhen(), mouseEvent.getModifiers(), this.kF(mouseEvent.getX()), this.kG(mouseEvent.getY()), mouseEvent.getClickCount(), mouseEvent.isPopupTrigger(), mouseEvent.getButton());
            }
            return this.cuJ.g(mouseEvent2);
        }
        return false;
    }

    public boolean mousePressed(MouseEvent mouseEvent) {
        if (this.cuJ != null) {
            MouseEvent mouseEvent2 = mouseEvent;
            if (this.isScaled()) {
                mouseEvent2 = new MouseEvent(mouseEvent.getComponent(), mouseEvent.getID(), mouseEvent.getWhen(), mouseEvent.getModifiers(), this.kF(mouseEvent.getX()), this.kG(mouseEvent.getY()), mouseEvent.getClickCount(), mouseEvent.isPopupTrigger(), mouseEvent.getButton());
            }
            return this.cuJ.mousePressed(mouseEvent2);
        }
        return false;
    }

    public boolean c(MouseEvent mouseEvent) {
        if (this.cuJ != null) {
            MouseEvent mouseEvent2 = mouseEvent;
            if (this.isScaled()) {
                mouseEvent2 = new MouseEvent(mouseEvent.getComponent(), mouseEvent.getID(), mouseEvent.getWhen(), mouseEvent.getModifiers(), this.kF(mouseEvent.getX()), this.kG(mouseEvent.getY()), mouseEvent.getClickCount(), mouseEvent.isPopupTrigger(), mouseEvent.getButton());
            }
            return this.cuJ.c(mouseEvent2);
        }
        return false;
    }

    public boolean a(MouseWheelEvent mouseWheelEvent) {
        if (this.cuJ != null) {
            MouseWheelEvent mouseWheelEvent2 = mouseWheelEvent;
            if (this.isScaled()) {
                mouseWheelEvent2 = new MouseWheelEvent(mouseWheelEvent.getComponent(), mouseWheelEvent.getID(), mouseWheelEvent.getWhen(), mouseWheelEvent.getModifiers(), this.kF(mouseWheelEvent.getX()), this.kG(mouseWheelEvent.getY()), mouseWheelEvent.getClickCount(), mouseWheelEvent.isPopupTrigger(), mouseWheelEvent.getScrollType(), mouseWheelEvent.getScrollAmount(), mouseWheelEvent.getWheelRotation());
            }
            return this.cuJ.a(mouseWheelEvent2);
        }
        return false;
    }

    protected void awK() {
        int n2 = this.G.size();
        for (int j = 0; j < n2; ++j) {
            ((amn_1)this.G.get(j)).b(this);
        }
    }

    protected void kH(int n2) {
        int n3 = this.G.size();
        for (int j = 0; j < n3; ++j) {
            ((amn_1)this.G.get(j)).a(this, n2);
        }
    }

    protected void bq(int n2, int n3) {
        int n4 = this.G.size();
        for (int j = 0; j < n4; ++j) {
            ((amn_1)this.G.get(j)).a(this, n2, n3);
        }
    }

    public boolean a(FocusEvent focusEvent) {
        return false;
    }

    public boolean b(FocusEvent focusEvent) {
        add_1.aOG().aOM().hS(-1);
        ago_2.getInstance().setMovePointMode(false);
        return false;
    }

    static {
        cva = 0;
        cvb = 0L;
    }
}

