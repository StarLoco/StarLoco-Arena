/*
 * Decompiled with CFR 0.152.
 */
import javax.swing.JCheckBox;
import javax.swing.JComponent;

/*
 * Renamed from cf
 */
public class cf_0
implements aMW,
aki_0 {
    private final JCheckBox ih = new JCheckBox("VSync", false);
    mk_1 ii;

    public cf_0() {
        this.ih.addItemListener(new xn_0(this));
    }

    public void b(mk_1 mk_12) {
        this.ii = mk_12;
        mk_12.kW().a(this);
        this.ih.setSelected(mk_12.kW().cV());
    }

    public void c(mk_1 mk_12) {
        this.ii = null;
        mk_12.kW().b(this);
    }

    public JComponent eg() {
        return this.ih;
    }

    public String getName() {
        return "Switch VSync";
    }

    public void m(boolean bl2) {
        this.ih.setSelected(bl2);
    }
}

