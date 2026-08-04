/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aV
 */
public class av_1
extends aNZ
implements jn_2 {
    public static final String TAG = "Condition";
    private alt_0 dD;
    private Object dE = true;
    private boolean dF = false;
    private Object dG = false;
    private boolean dH = false;
    private boolean dI = false;
    private final ArrayList dJ = new ArrayList();
    protected yw_1 dK;
    public static final int dL = "value".hashCode();
    public static final int dM = "elseValue".hashCode();
    public static final int dN = "returnOriginalValue".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof alt_0) {
            this.setCondition((alt_0)na_12);
        } else if (na_12 instanceof av_1) {
            this.a((av_1)na_12);
        }
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public Object getResult(Object object) {
        Object object2;
        Object object3 = this.dF || !this.dI ? this.dE : object;
        Object object4 = object2 = this.dH || !this.dI ? this.dG : object;
        if (this.dJ.size() == 0) {
            if (this.dD.isValid(object)) {
                return object3;
            }
            return object2;
        }
        for (av_1 av_12 : this.dJ) {
            if (!av_12.getCondition().isValid(object)) continue;
            return av_12.isComposite() ? av_12.getResult(object) : av_12.getValue();
        }
        return object2;
    }

    public alt_0 getCondition() {
        return this.dD;
    }

    public void setCondition(alt_0 alt_02) {
        this.dD = alt_02;
        if (this.dD != null) {
            this.dD.setConditionParent(this);
        }
    }

    public Object getElseValue() {
        return this.dG;
    }

    public void setElseValue(Object object) {
        this.dG = object;
        this.dH = true;
    }

    public void setElseValue(String string) {
        this.dG = string;
        this.dH = true;
    }

    public Object getValue() {
        return this.dE;
    }

    public void setValue(Object object) {
        this.dE = object;
        this.dF = true;
    }

    public void setValue(String string) {
        this.dE = string;
        this.dF = true;
    }

    public boolean isReturnOriginalValue() {
        return this.dI;
    }

    public void setReturnOriginalValue(boolean bl2) {
        this.dI = bl2;
    }

    public void g(boolean bl2) {
        if (bl2 && this.dK != null) {
            this.dK.a(this);
        }
        this.bZ();
    }

    public void bZ() {
        ie ie2 = (ie)this.getParentOfType(ie.class);
        if (ie2 != null) {
            ie2.getManager().yW();
        }
    }

    public void setResultProviderParent(yw_1 yw_12) {
        this.dK = yw_12;
    }

    public void a(av_1 av_12) {
        this.dJ.add(av_12);
    }

    public boolean isComposite() {
        return this.dJ.size() != 0;
    }

    public void a(air_1 air_12) {
        av_1 av_12 = (av_1)air_12;
        super.a((air_1)av_12);
        if (this.dF) {
            av_12.setValue(this.dE);
        }
        if (this.dH) {
            av_12.setElseValue(this.dG);
        }
        av_12.setReturnOriginalValue(this.dI);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == dM) {
            this.setElseValue(if_12.eM(string));
        } else if (n2 == dL) {
            this.setValue(if_12.eM(string));
        } else if (n2 == dN) {
            this.setReturnOriginalValue(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == dM) {
            this.setElseValue(object);
        } else if (n2 == dL) {
            this.setValue(object);
        } else if (n2 == dN) {
            this.setReturnOriginalValue(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

