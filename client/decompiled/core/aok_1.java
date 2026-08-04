/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Vector;

/*
 * Renamed from aOk
 */
public class aok_1
extends hx_1 {
    private String classname = null;
    private R ebJ = null;
    private Vector ebK = new Vector();
    private bk_2 sK = null;

    public void setClassname(String string) {
        this.classname = string;
    }

    public void aYb() {
        if (this.classname != null && this.classname.length() > 0) {
            try {
                Object object;
                Class<?> clazz = null;
                if (this.sK == null) {
                    clazz = Class.forName(this.classname);
                } else {
                    object = this.TP().g(this.sK);
                    clazz = Class.forName(this.classname, true, (ClassLoader)object);
                }
                this.ebJ = (R)clazz.newInstance();
                object = this.TP();
                if (object != null) {
                    ((UI)object).at(this.ebJ);
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
                this.eC("Selector " + this.classname + " not initialized, no such class");
            }
            catch (InstantiationException instantiationException) {
                this.eC("Selector " + this.classname + " not initialized, could not create class");
            }
            catch (IllegalAccessException illegalAccessException) {
                this.eC("Selector " + this.classname + " not initialized, class not accessible");
            }
        } else {
            this.eC("There is no classname specified");
        }
    }

    public void a(vj_0 vj_02) {
        this.ebK.addElement(vj_02);
    }

    public final void e(bk_2 bk_22) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (this.sK == null) {
            this.sK = bk_22;
        } else {
            this.sK.b(bk_22);
        }
    }

    public final bk_2 jz() {
        if (this.aId()) {
            throw this.aIi();
        }
        if (this.sK == null) {
            this.sK = new bk_2(this.TP());
        }
        return this.sK.dB();
    }

    public final bk_2 jC() {
        return this.sK;
    }

    public void b(awq_0 awq_02) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.jz().a(awq_02);
    }

    public void dQ() {
        if (this.ebJ == null) {
            this.aYb();
        }
        if (this.classname == null || this.classname.length() < 1) {
            this.eC("The classname attribute is required");
        } else if (this.ebJ == null) {
            this.eC("Internal Error: The custom selector was not created");
        } else if (!(this.ebJ instanceof aeo_0) && this.ebK.size() > 0) {
            this.eC("Cannot set parameters on custom selector that does not implement ExtendFileSelector");
        }
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        if (this.ebK.size() > 0 && this.ebJ instanceof aeo_0) {
            Object[] objectArray = new vj_0[this.ebK.size()];
            this.ebK.copyInto(objectArray);
            ((aeo_0)this.ebJ).a((vj_0[])objectArray);
        }
        return this.ebJ.a(file, string, file2);
    }
}

