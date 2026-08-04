/*
 * Decompiled with CFR 0.152.
 */
import javax.swing.JCheckBox;
import javax.swing.JComponent;

/*
 * Renamed from aph
 */
public class aph_1
implements aMW,
aki_0 {
    private final JCheckBox ih = new JCheckBox("Lights", false);

    public aph_1() {
        this.ih.addItemListener(new aho_1(this));
    }

    public void b(mk_1 mk_12) {
        this.ih.setSelected(ahn_0.dNL.aUg());
    }

    public void c(mk_1 mk_12) {
    }

    public JComponent eg() {
        return this.ih;
    }

    public String getName() {
        return "Switch Lights";
    }

    public void m(boolean bl2) {
        this.ih.setSelected(bl2);
    }
}

