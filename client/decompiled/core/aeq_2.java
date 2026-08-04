/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Renamed from aEQ
 */
class aeq_2
extends JPanel {
    JTextField dBU;
    JLabel dBV;
    final /* synthetic */ zb_0 dBW;

    private aeq_2(zb_0 zb_02) {
        this.dBW = zb_02;
        super(new FlowLayout(0, 0, 0), true);
        this.dBV = new JLabel("fps");
        this.dBU = new JTextField();
        this.dBU.setEnabled(false);
        this.dBU.setHorizontalAlignment(4);
        this.dBU.setPreferredSize(new Dimension(40, 20));
        this.add(this.dBU);
        this.add(this.dBV);
    }

    /* synthetic */ aeq_2(zb_0 zb_02, Ou ou) {
        this(zb_02);
    }
}

