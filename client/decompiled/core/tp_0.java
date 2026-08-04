/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tP
 */
public class tp_0
extends aur_0
implements cf_1 {
    private rs_0 aol;
    private String name;

    public void setName(String string) {
        this.name = string;
    }

    public void a(dm_1 dm_12) {
        if (this.aol != null) {
            throw new eq_2("Only one nested element allowed");
        }
        if (!(dm_12 instanceof rs_0)) {
            throw new eq_2("addTask called with a task that is not an unknown element");
        }
        this.aol = (rs_0)dm_12;
    }

    public void execute() {
        String string;
        if (this.aol == null) {
            throw new eq_2("Missing nested element");
        }
        if (this.name == null) {
            throw new eq_2("Name not specified");
        }
        this.name = es_2.s(this.getURI(), this.name);
        abm_1 abm_12 = abm_1.D(this.TP());
        alv_2 alv_22 = abm_12.at(string = es_2.s(this.aol.getNamespace(), this.aol.getTag()));
        if (alv_22 == null) {
            throw new eq_2("Unable to find typedef " + string);
        }
        cc_0 cc_02 = new cc_0(alv_22, this.aol);
        cc_02.setName(this.name);
        abm_12.a(cc_02);
        this.l("defining preset " + this.name, 3);
    }
}

