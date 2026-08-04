/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/*
 * Renamed from aNC
 */
public abstract class anc_1
extends hs_1
implements ala,
avk_0 {
    public static final Logger a = Logger.getLogger(anc_1.class);
    private jL beN;
    protected JFrame cDM;
    protected JComponent dZB;

    public anc_1() {
    }

    public anc_1(String string) {
        this.beN = new jL(string);
    }

    protected void lM(String string) {
        this.beN = new jL(string);
    }

    protected JFrame kS() {
        JFrame.setDefaultLookAndFeelDecorated(false);
        this.cDM = new JFrame();
        this.cDM.setUndecorated(true);
        this.cDM.setFocusable(false);
        Container container = this.cDM.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        Insets insets = this.beN.getBorderInsets();
        Dimension dimension = new Dimension(-1, Math.max(1, insets.top));
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        Hu hu = new Hu(this, this.beN, dimension);
        anc_1.a(hu, dimension);
        container.add((Component)hu, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        amz_1 amz_12 = new amz_1(this.beN.nx());
        amz_1 amz_13 = new amz_1(this.beN.nF());
        amz_1 amz_14 = new amz_1(this.beN.nz());
        amz_1 amz_15 = new amz_1(this.beN.nB());
        amz_1 amz_16 = new amz_1(this.beN.nD());
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        anc_1.a(amz_12, new Dimension(insets.left, -1));
        container.add((Component)amz_12, gridBagConstraints);
        gridBagConstraints.gridx = 2;
        anc_1.a(amz_13, new Dimension(insets.right, -1));
        container.add((Component)amz_13, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 0;
        anc_1.a(amz_14, new Dimension(insets.left, insets.bottom));
        container.add((Component)amz_14, gridBagConstraints);
        gridBagConstraints.gridx = 1;
        anc_1.a(amz_15, new Dimension(-1, insets.bottom));
        container.add((Component)amz_15, gridBagConstraints);
        gridBagConstraints.gridx = 2;
        anc_1.a(amz_16, new Dimension(insets.right, insets.bottom));
        container.add((Component)amz_16, gridBagConstraints);
        gridBagConstraints.fill = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.weightx = 1.0;
        this.dZB = new JPanel(new BorderLayout(0, 0), true);
        this.dZB.add((Component)this.kV(), "Center");
        container.add((Component)this.dZB, gridBagConstraints);
        adj_2 adj_22 = new adj_2(this.cDM, hu);
        xu_0 xu_02 = new xu_0(this.cDM, amz_15, amz_16, amz_13);
        xu_02.setMinimumSize(this.getMinimumSize());
        xu_02.a(hu, amz_16, amz_15, amz_13, amz_12, amz_14);
        amz_15.setCursor(Cursor.getPredefinedCursor(9));
        amz_13.setCursor(Cursor.getPredefinedCursor(11));
        amz_16.setCursor(Cursor.getPredefinedCursor(5));
        this.kW().a(this, false);
        return this.cDM;
    }

    private static void a(Component component, Dimension dimension) {
        component.setMinimumSize(dimension);
        component.setSize(dimension);
        component.setPreferredSize(dimension);
        component.setMaximumSize(dimension);
    }

    private void fo(boolean bl2) {
        for (Component component : this.cDM.getContentPane().getComponents()) {
            if (component == this.dZB) continue;
            component.setVisible(bl2);
        }
    }

    protected boolean f(int n2, int n3, int n4, int n5) {
        boolean bl2 = super.f(n2, n3, n4, n5);
        if (bl2) {
            this.fo(false);
        }
        return bl2;
    }

    protected void lc() {
        this.fo(false);
        super.lc();
    }

    protected void q(int n2, int n3) {
        this.fo(true);
        super.q(n2, n3);
    }

    public boolean a(FocusEvent focusEvent) {
        return false;
    }

    public boolean b(FocusEvent focusEvent) {
        Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (component != this.kV()) {
            this.kV().requestFocus();
        }
        return false;
    }

    public void aJb() {
        this.close();
    }

    public void aJc() {
        if (this.cDM.getExtendedState() == 6) {
            this.cDM.setExtendedState(0);
        } else {
            this.cDM.setSize(1024, 768);
            this.cDM.setExtendedState(6);
        }
    }

    public void aJd() {
        int n2 = this.cDM.getExtendedState() & 6;
        this.cDM.setExtendedState(1 | n2);
    }

    public void a(avV avV2) {
        this.dZB.add((Component)avV2, "South");
        avV2.setVisible(true);
        this.cDM.pack();
    }

    public void kU() {
        for (Component component : this.dZB.getComponents()) {
            if (!(component instanceof avV)) continue;
            this.dZB.remove(component);
        }
        this.cDM.pack();
    }
}

