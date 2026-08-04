/*
 * Decompiled with CFR 0.152.
 */
public class amq
implements yc_2 {
    private static final String cGL = "progress.task.name";
    private static final String cGM = "progress.subtask.name";
    private static final String cGN = "progress.value";
    private int cGO = 1;

    public void m(String string, int n2) {
        this.cGO = n2;
        azs_0.aLV().g(cGL, string);
        azs_0.aLV().g(cGN, 0.0);
    }

    public void done() {
    }

    public void cW(String string) {
        if (string == null) {
            string = aon_0.aYc().getString("loading");
        }
        azs_0.aLV().g(cGL, string);
    }

    public void cX(String string) {
        if (string == null) {
            string = "";
        }
        azs_0.aLV().g(cGM, string);
    }

    public void es(int n2) {
        if (this.cGO != 0) {
            azs_0.aLV().g(cGN, (double)n2 / (double)this.cGO);
        }
    }

    public int aBI() {
        return this.cGO;
    }

    public void lt(int n2) {
        this.cGO = n2;
    }
}

