/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aeg
 */
public class aeg_1
extends tj_0 {
    private int yV;
    private int yW;
    private int yX;
    private int yY;
    private ArrayList cod;
    private ayr_0 coe;
    private ayr_0 cof;
    private boolean cog = false;
    private boolean coh = false;
    private Ei coi;
    private ahr_2 coj;
    private boolean cok = false;

    public aeg_1(ahr_2 ahr_22, int n2, int n3, ys ys2, ArrayList arrayList, Ei ei) {
        super(null, null, ahr_22, n2, 0, ys2);
        this.setDuration(n3 / (arrayList.size() - 1));
        this.coj = ahr_22;
        this.coi = ei;
        this.coe = (ayr_0)arrayList.get(0);
        if (((ayr_0)arrayList.get(1)).aLb()) {
            this.cof = (ayr_0)arrayList.get(2);
            this.setDuration((int)((double)(n3 * 2 / (arrayList.size() - 1)) * Math.PI / 4.0));
            this.coh = this.b((ajM)arrayList.get(0), (ajM)arrayList.get(1));
            this.cog = true;
            arrayList.remove(0);
        } else {
            this.cof = (ayr_0)arrayList.get(1);
        }
        arrayList.remove(0);
        this.cod = arrayList;
        this.yV = this.coe.aLe() * this.coi.getCellWidth();
        this.yW = this.coe.aLf() * this.coi.getCellHeight();
        this.yX = this.cof.aLe() * this.coi.getCellWidth();
        this.yY = this.cof.aLf() * this.coi.getCellHeight();
    }

    private boolean b(ajM ajM2, ajM ajM3) {
        return ajM2.azs() == ajM3 || ajM2.azp() == ajM3;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            int n3;
            int n4;
            if (this.yV != this.yX && this.yW != this.yY) {
                if (this.coh) {
                    n4 = (int)this.amA.b(this.yV, this.yX, (int)((1.0 - Math.cos(1.5707963267948966 * (double)this.IP / (double)this.wg)) * 1000.0), 1000);
                    n3 = (int)this.amA.b(this.yW, this.yY, (int)(Math.sin(1.5707963267948966 * (double)this.IP / (double)this.wg) * 1000.0), 1000);
                } else {
                    n4 = (int)this.amA.b(this.yV, this.yX, (int)(Math.sin(1.5707963267948966 * (double)this.IP / (double)this.wg) * 1000.0), 1000);
                    n3 = (int)this.amA.b(this.yW, this.yY, (int)((1.0 - Math.cos(1.5707963267948966 * (double)this.IP / (double)this.wg)) * 1000.0), 1000);
                }
            } else {
                n4 = (int)this.amA.b(this.yV, this.yX, this.IP, this.wg);
                n3 = (int)this.amA.b(this.yW, this.yY, this.IP, this.wg);
            }
            this.coj.setTokenPixelPosition(n4, n3);
        }
        return true;
    }

    public void ly() {
        this.coj.setTokenPixelPosition(this.yX, this.yY);
        super.ly();
        if (this.cod.size() > 1) {
            this.getWidget().a(new aeg_1(this.coj, 0, (int)((double)(this.wg * (this.cod.size() - 1)) / (1.0 + (this.cog ? 0.7853981633974483 : 0.0))), ys.aCp, this.cod, this.coi));
        } else {
            this.cok = true;
        }
    }

    public boolean wv() {
        return this.cok;
    }

    public ArrayList atA() {
        return this.cod;
    }
}

