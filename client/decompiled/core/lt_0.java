/*
 * Decompiled with CFR 0.152.
 */
import java.awt.event.MouseEvent;

/*
 * Renamed from lT
 */
public class lt_0
extends abd_1 {
    kn_1 Is;
    Object It;
    Object dE;
    private static final acl_0 uG = new ym_0(new aCd());

    public lt_0() {
    }

    public lt_0(kn_1 kn_12, Object object) {
        this.setDragNDropable(kn_12);
        this.DK = kn_12;
        this.dE = object;
    }

    public static lt_0 a(MouseEvent mouseEvent, kn_1 kn_12, Object object) {
        lt_0 lt_02;
        try {
            lt_02 = (lt_0)uG.adr();
            lt_02.DG = uG;
        }
        catch (Exception exception) {
            lt_02 = new lt_0();
            lt_02.b();
        }
        lt_02.ng(mouseEvent.getButton());
        lt_02.nh(mouseEvent.getClickCount());
        lt_02.setModifiers(mouseEvent.getModifiersEx());
        lt_02.ai(awS.aJG().getX());
        lt_02.aj(awS.aJG().getY());
        lt_02.e(kn_12);
        lt_02.a(qe_1.bFg);
        lt_02.setDragNDropable(kn_12);
        lt_02.dE = object;
        return lt_02;
    }

    public static lt_0 a(abd_1 abd_12, kn_1 kn_12, Object object) {
        lt_0 lt_02;
        try {
            lt_02 = (lt_0)uG.adr();
            lt_02.DG = uG;
        }
        catch (Exception exception) {
            lt_02 = new lt_0();
            lt_02.b();
        }
        lt_02.ng(abd_12.bTl);
        lt_02.nh(abd_12.bTm);
        lt_02.setModifiers(abd_12.jH);
        lt_02.ai(abd_12.oI);
        lt_02.aj(abd_12.oJ);
        lt_02.e(kn_12);
        lt_02.a(qe_1.bFg);
        lt_02.setDragNDropable(kn_12);
        lt_02.dE = object;
        return lt_02;
    }

    public kn_1 getDragNDropable() {
        return this.Is;
    }

    public void setDragNDropable(kn_1 kn_12) {
        qa_1 qa_12;
        if (kn_12 instanceof na_1) {
            this.Is = kn_12;
        }
        if (kn_12 != null && (qa_12 = kn_12.getRenderableParent()) != null) {
            this.It = qa_12.getItemValue();
        }
    }

    public Object qJ() {
        return this.It;
    }

    public void x(Object object) {
        this.It = object;
    }

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public qe_1 aV() {
        return qe_1.bFg;
    }
}

