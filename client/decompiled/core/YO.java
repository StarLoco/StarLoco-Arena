/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.awt.event.MouseEvent;

public class YO
implements alx_0 {
    private static YO cbz = new YO();
    private static final int cbA = 500;
    private boolean cX;
    private long nD = this.hashCode();
    private int cbB;
    private int cbC;
    private int cbD;

    public static YO amN() {
        return cbz;
    }

    private YO() {
    }

    public void i(MouseEvent mouseEvent) {
        this.cbB = mouseEvent.getX();
        this.cbC = mouseEvent.getY();
        this.cbD = mouseEvent.getButton();
        if (!this.isRunning()) {
            this.cX = true;
            aam_1.aMF().a(this, 500L, 0, -1);
            this.E(this.cbD, this.cbB, this.cbC);
        }
    }

    public void j(MouseEvent mouseEvent) {
        int n2 = -1;
        n2 = DofusArenaClientInstance.yl().aod().a(adc_0.clW) ? 1 : 3;
        if (mouseEvent.getButton() == n2) {
            this.cbD = -1;
            this.cbB = -1;
            this.cbC = -1;
            this.cX = false;
        }
    }

    public void E(int n2, int n3, int n4) {
        acl_1 acl_12 = acl_1.aqX();
        acl_12.jH(n2);
        acl_12.jL(n3);
        acl_12.jM(n4);
        acu_1.ara().c(acl_12);
    }

    public boolean isRunning() {
        return this.cX;
    }

    public boolean a(pr_0 pr_02) {
        if (this.isRunning()) {
            this.E(this.cbD, this.cbB + 1, this.cbC);
        } else {
            aam_1.aMF().b(this);
        }
        return false;
    }

    public long getId() {
        return this.nD;
    }

    public void c(long l2) {
        this.nD = l2;
    }
}

