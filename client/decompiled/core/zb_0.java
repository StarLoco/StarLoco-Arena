/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GLAutoDrawable;
import javax.swing.JComponent;

/*
 * Renamed from ZB
 */
public class zb_0
implements aki_0,
jA {
    private final aeq_2 cdo;
    private static final int cdp = 5;
    private static final long cdq = 60000000L;
    private final long[] cdr = new long[5];
    private int cds = -1;
    private long cdt = 0L;

    public zb_0() {
        this.cdo = new aeq_2(this, null);
    }

    private int jr(int n2) {
        int n3 = n2 % 5;
        if (n3 >= 0) {
            return n3;
        }
        return n3 + 5;
    }

    public void b(GLAutoDrawable gLAutoDrawable) {
        long l2;
        int n2 = this.jr(++this.cds);
        this.cdr[n2] = l2 = System.nanoTime();
        if (l2 - this.cdt <= 60000000L) {
            return;
        }
        long l3 = l2 - this.cdr[this.jr(n2 + 1)];
        if (l3 == 0L) {
            return;
        }
        long l4 = Math.round(1.0E9 / (double)l3 * 4.0);
        this.cdo.dBU.setText(Long.toString(l4));
        this.cdt = l2;
    }

    public void b(mk_1 mk_12) {
        mk_12.kW().a(this);
    }

    public void c(mk_1 mk_12) {
        mk_12.kW().b(this);
    }

    public JComponent eg() {
        return this.cdo;
    }

    public String getName() {
        return "FPS Viewer";
    }

    public void a(GLAutoDrawable gLAutoDrawable) {
    }

    public void a(GLAutoDrawable gLAutoDrawable, int n2, int n3, int n4, int n5) {
    }

    public void a(GLAutoDrawable gLAutoDrawable, boolean bl2, boolean bl3) {
    }
}

