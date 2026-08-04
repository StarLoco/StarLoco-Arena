/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;

public class avV
extends JPanel {
    private static Logger a = Logger.getLogger(avV.class);
    private final String dgW = "debugBar.properties";
    private static final int HEIGHT = 38;
    private static final Border dgX = new EmptyBorder(0, 0, 0, 0);
    private final mk_1 ii;
    private final Vector dgY = new Vector();
    private final JPanel dgZ;
    private final Vector dha = new Vector();

    public avV(mk_1 mk_12) {
        super(new BorderLayout(0, 0), true);
        this.ii = mk_12;
        this.setIgnoreRepaint(true);
        Dimension dimension = new Dimension(-1, 38);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.setPreferredSize(dimension);
        this.dgZ = new JPanel(new FlowLayout(0, 0, 0));
        this.dgZ.setMaximumSize(dimension);
        this.dgZ.setMinimumSize(dimension);
        this.dgZ.setPreferredSize(dimension);
        this.add((Component)this.dgZ, "Center");
    }

    public void F(Class clazz) {
        aki_0 aki_02;
        Object object2;
        for (Object object2 : this.dgY) {
            if (!object2.getClass().getCanonicalName().equals(clazz.getCanonicalName())) continue;
            a.debug((Object)(clazz.toString() + " component already present"));
            return;
        }
        try {
            aki_02 = (aki_0)clazz.newInstance();
        }
        catch (Exception exception) {
            a.info((Object)("Unable to instanciate a " + clazz + " DebugComponent"));
            return;
        }
        this.dgY.add(aki_02);
        aki_02.b(this.ii);
        if (this.dgZ.getComponentCount() > 0) {
            this.dgZ.add(new asy_0(8, 38));
        }
        object2 = aki_02.eg();
        ((JComponent)object2).setBorder(dgX);
        this.dgZ.add((Component)object2);
        this.dgZ.validate();
    }

    public void G(Class clazz) {
        this.dha.add(clazz);
    }

    private void H(Class clazz) {
        for (aki_0 aki_02 : this.dgY) {
            if (aki_02.getClass() != clazz) continue;
            this.a(aki_02);
            break;
        }
    }

    private boolean a(aki_0 aki_02) {
        int n2 = this.dgZ.getComponentZOrder(aki_02.eg());
        if (n2 == -1) {
            return false;
        }
        this.dgY.remove(aki_02);
        this.dgZ.remove(aki_02.eg());
        if (this.dgZ.getComponentCount() > 0) {
            if (n2 > 0) {
                this.dgZ.remove(n2 - 1);
            } else {
                this.dgZ.remove(n2);
            }
        }
        aki_02.c(this.ii);
        this.dgZ.validate();
        return true;
    }

    public void removeAll() {
        this.aJj();
    }

    private void aJj() {
        while (this.dgY.size() > 0) {
            aki_0 aki_02 = (aki_0)this.dgY.get(0);
            if (this.a(aki_02)) continue;
            a.error((Object)("Error while deleting DebugComponent " + aki_02));
            this.dgY.remove(0);
        }
        if (this.dgZ.getComponentCount() != 0) {
            a.error((Object)"Encore des composants de pr\u00e9sent alors qu'on vient de tous les supprimer");
        }
        if (this.dgY.size() != 0) {
            a.error((Object)"Encore des debugcomposants de pr\u00e9sent alors qu'on vient de tous les supprimer");
        }
    }

    public void reset() {
        this.aJj();
    }
}

