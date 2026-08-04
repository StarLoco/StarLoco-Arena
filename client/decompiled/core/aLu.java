/*
 * Decompiled with CFR 0.152.
 */
public class aLu
extends Zb {
    public static final String dVM = "progressBar";
    public static final String TAG = "progressBarAppearance";
    private aab_0 dVN = null;
    private ur_1[] dVO = new ur_1[9];

    public void a(na_1 na_12) {
        aab_0 aab_02;
        if (na_12 instanceof ur_1) {
            ur_1 ur_12 = (ur_1)na_12;
            switch (ur_12.getPosition()) {
                case dSm: {
                    this.dVO[0] = ur_12;
                    break;
                }
                case dSo: {
                    this.dVO[1] = ur_12;
                    break;
                }
                case dSq: {
                    this.dVO[2] = ur_12;
                    break;
                }
                case dSt: {
                    this.dVO[3] = ur_12;
                    break;
                }
                case dSu: {
                    this.dVO[4] = ur_12;
                    break;
                }
                case dSv: {
                    this.dVO[5] = ur_12;
                    break;
                }
                case dSy: {
                    this.dVO[6] = ur_12;
                    break;
                }
                case dSA: {
                    this.dVO[7] = ur_12;
                    break;
                }
                case dSC: {
                    this.dVO[8] = ur_12;
                }
            }
            this.aWo();
        } else if (na_12 instanceof aab_0 && ((aab_02 = (aab_0)na_12).getName() == null || aab_02.getName().equalsIgnoreCase(dVM))) {
            aab_02.a(qe_1.bFa, new qa_0(this), false);
            this.dVN = (aab_0)na_12;
            this.EM();
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public void setWidget(adg_2 adg_22) {
        super.setWidget(adg_22);
        if (this.dVN != null) {
            this.EM();
        } else {
            this.aWo();
        }
    }

    public void Pj() {
        super.Pj();
        this.aWp();
        this.aWq();
    }

    private void aWo() {
        if (this.DD == null || !(this.DD instanceof apc_1)) {
            return;
        }
        for (int j = 0; j < this.dVO.length; ++j) {
            if (this.dVO[j] != null) continue;
            return;
        }
        ((apc_1)this.DD).setPixmaps(this.dVO[0], this.dVO[1], this.dVO[2], this.dVO[3], this.dVO[4], this.dVO[5], this.dVO[6], this.dVO[7], this.dVO[8]);
    }

    private void EM() {
        if (this.DD == null || !(this.DD instanceof apc_1)) {
            return;
        }
        apc_1 apc_12 = (apc_1)this.DD;
        if (this.dVN != null) {
            apc_12.setColor(this.dVN.getColor(), dVM);
        }
    }

    private void aWp() {
        if (this.dVN != null) {
            this.k(this.dVN);
            this.dVN = null;
        }
    }

    private void aWq() {
        for (int j = 0; j < this.dVO.length; ++j) {
            if (this.dVO[j] == null) continue;
            this.k(this.dVO[j]);
            this.dVO[j] = null;
        }
    }

    public void j() {
        super.j();
        this.dVN = null;
        for (int j = 0; j < this.dVO.length; ++j) {
            this.dVO[j] = null;
        }
    }

    static /* synthetic */ void a(aLu aLu2) {
        aLu2.EM();
    }
}

