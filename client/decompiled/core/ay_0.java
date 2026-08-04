/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from aY
 */
public class ay_0
extends aht_1
implements Fc,
px_2 {
    public static final String TAG = "StackList";
    private boolean ba = true;
    private sn_0 dR;
    private final ArrayList dS = new ArrayList();
    private qa_1 dT = null;
    private Object dU = null;
    private int dV = -1;
    private boolean dW = true;
    private boolean dX = true;
    private boolean dY = true;
    private boolean dZ = true;
    private boolean ea = false;
    private int eb;
    private final ArrayList ec = new ArrayList();
    private String ed = null;
    private aji_1 ee = null;
    public static final int ef = "innerExpandable".hashCode();
    public static final int eg = "clickSoundId".hashCode();
    public static final int eh = "innerNonBlocking".hashCode();
    public static final int ei = "content".hashCode();
    public static final int ej = "horizontal".hashCode();
    public static final int ek = "selected".hashCode();
    public static final int el = "selectedValue".hashCode();
    public static final int em = "selectionable".hashCode();
    public static final int en = "selectionTogglable".hashCode();

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof ie) {
            this.dR.a((ie)na_12);
        }
    }

    public void b(Object object) {
    }

    public void c(Object object) {
    }

    public boolean a(int n2, Object object) {
        return false;
    }

    public void a(Object object, Object object2) {
    }

    public boolean b(Object object, Object object2) {
        return false;
    }

    public adg_2 getWidget(String string, int n2) {
        if (n2 >= 0 && n2 < this.dS.size()) {
            return (adg_2)this.dS.get(n2);
        }
        return null;
    }

    public ArrayList getRenderables() {
        return this.dS;
    }

    public boolean getSelectionable() {
        return this.dX;
    }

    public void setSelectionable(boolean bl2) {
        this.dX = bl2;
    }

    public boolean getSelectionTogglable() {
        return this.dY;
    }

    public void setSelectionTogglable(boolean bl2) {
        this.dY = bl2;
    }

    public void setEnableDND(boolean bl2) {
        this.dW = bl2;
    }

    public boolean getEnableDND() {
        return this.dW;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        ((ei_1)this.dMe).setHorizontal(bl2);
        this.ba = bl2;
        this.Am();
    }

    public boolean isInnerExpandable() {
        return this.dZ;
    }

    public void setInnerExpandable(boolean bl2) {
        if (this.dZ != bl2) {
            this.dZ = bl2;
            for (int j = 0; j < this.dS.size(); ++j) {
                ((qa_1)this.dS.get(j)).setExpandable(this.dZ);
            }
        }
    }

    public boolean getInnerNonBlocking() {
        return this.ea;
    }

    public void setInnerNonBlocking(boolean bl2) {
        if (this.ea != bl2) {
            this.ea = bl2;
            for (int j = 0; j < this.dS.size(); ++j) {
                ((qa_1)this.dS.get(j)).setNonBlocking(this.ea);
            }
        }
    }

    public int getSelectedOffsetByValue(Object object) {
        int n2;
        for (n2 = 0; n2 < this.ec.size() && this.ec.get(n2) != object; ++n2) {
        }
        if (n2 == this.ec.size()) {
            return -1;
        }
        return n2;
    }

    public Object getSelectedValue() {
        return this.dU;
    }

    public void setContent(Object[] objectArray) {
        if (this.czn) {
            return;
        }
        int n2 = this.dV;
        Object object = this.getSelectedValue();
        this.ec.clear();
        if (objectArray != null) {
            for (int j = 0; j < objectArray.length; ++j) {
                this.ec.add(objectArray[j]);
            }
        }
        this.dV = this.getSelectedOffsetByValue(object);
        if (this.dV == -1 && n2 != -1) {
            this.f(new hf_0(this, null, object, false));
            this.f(new hf_0(this, null, null, true));
        }
        this.u(this.ec.size());
        this.ca();
    }

    public void setContent(Iterable iterable) {
        if (this.czn) {
            return;
        }
        int n2 = this.dV;
        Object object = this.getSelectedValue();
        this.ec.clear();
        if (iterable != null) {
            Iterator iterator = iterable.iterator();
            while (iterator != null && iterator.hasNext()) {
                Object t = iterator.next();
                this.ec.add(t);
            }
        }
        this.dV = this.getSelectedOffsetByValue(object);
        if (this.dV == -1 && n2 != -1) {
            this.f(new hf_0(this, null, object, false));
            this.f(new hf_0(this, null, null, true));
        }
        this.u(this.ec.size());
        this.ca();
    }

    public Object getValue(int n2) {
        if (n2 >= 0 && n2 < this.ec.size()) {
            return this.ec.get(n2);
        }
        return null;
    }

    public qa_1 getSelected() {
        return this.dT;
    }

    public int getTableIndex(qa_1 qa_12) {
        return this.dS.indexOf(qa_12);
    }

    public int getItemIndex(Object object) {
        return this.ec.indexOf(object);
    }

    public void setContentProperty(String string, aji_1 aji_12) {
        this.ed = string;
        this.ee = aji_12;
    }

    public int getOffsetByRenderable(qa_1 qa_12) {
        return this.dS.indexOf(qa_12);
    }

    public void setSelected(sm_0 sm_02) {
        this.dV = this.ec.indexOf(sm_02);
        this.cb();
    }

    public int getClickSoundId() {
        return this.eb;
    }

    public void setClickSoundId(int n2) {
        this.eb = n2;
    }

    public int getSelectedOffset() {
        return this.dV;
    }

    public void setSelectedValue(Object object) {
        if (this.ec == null) {
            return;
        }
        int n2 = this.dV;
        this.dV = -1;
        for (int j = 0; j < this.ec.size(); ++j) {
            if (this.ec.get(j) != object) continue;
            this.dV = j;
            break;
        }
        if (n2 != this.dV) {
            if (n2 != -1) {
                this.f(new hf_0(this, (qa_1)this.dS.get(this.dV), this.ec.get(n2), false));
            }
            if (this.dV != -1) {
                this.f(new hf_0(this, (qa_1)this.dS.get(this.dV), this.ec.get(this.dV), true));
            }
            this.cb();
        }
    }

    protected void a(ke ke2, boolean bl2) {
        if (!(ke2.oH() || ke2.aV() != qe_1.bFi && ke2.aV() != qe_1.bFj)) {
            ke2.X(true);
            switch (this.eb) {
                case -1: {
                    aek.atD().click();
                    break;
                }
                case -2: {
                    aek.atD().atH();
                    break;
                }
                default: {
                    aek.atD().jY(this.eb);
                }
            }
        }
    }

    private void ca() {
        if (this.dS == null) {
            return;
        }
        this.dT = null;
        boolean bl2 = false;
        int n2 = this.dS.size();
        for (int j = 0; j < n2; ++j) {
            qa_1 qa_12 = (qa_1)this.dS.get(j);
            qa_12.setContentProperty(this.ed + "#" + j, this.ee);
            if (this.ec != null && j < this.ec.size()) {
                if (j == this.dV && !bl2) {
                    bl2 = true;
                    this.dT = qa_12;
                }
                qa_12.setContent(this.ec.get(j));
                continue;
            }
            qa_12.setContent(null);
        }
        if (!bl2) {
            this.dT = null;
        }
    }

    private void cb() {
        this.dT = (qa_1)this.dS.get(this.dV);
    }

    private void a(qa_1 qa_12) {
        hf_0 hf_02;
        qa_1 qa_13 = this.dT;
        if (qa_12 == this.dT) {
            return;
        }
        Object object = this.getSelectedValue();
        this.dT = qa_12;
        this.dV = this.dT != null ? this.getOffsetByRenderable(this.dT) : -1;
        Object var4_4 = this.dV == -1 ? null : this.ec.get(this.dV);
        if (qa_13 != null) {
            hf_02 = new hf_0(this);
            hf_02.b(qa_13);
            hf_02.setSelected(false);
            hf_02.setValue(object);
            this.f(hf_02);
        }
        if (this.dT != null) {
            hf_02 = new hf_0(this);
            hf_02.b(this.dT);
            hf_02.setSelected(true);
            hf_02.setValue(var4_4);
            this.f(hf_02);
        }
    }

    private void u(int n2) {
        int n3;
        for (n3 = this.dS.size(); n3 < n2; ++n3) {
            qa_1 qa_12 = new qa_1();
            qa_12.b();
            qa_12.setCollection(this);
            qa_12.setNonBlocking(this.dyc);
            qa_12.setRendererManager(this.dR);
            qa_12.setEnableDND(this.dW);
            qa_12.setEnabled(this.OD);
            qa_12.setExpandable(this.dZ);
            qa_12.setNonBlocking(this.ea);
            qa_12.a(qe_1.bFB, new aft_2(this), false);
            this.dS.add(qa_12);
            this.a((na_1)qa_12);
        }
        n3 = this.dS.size();
        for (int j = n3 - 1; j >= n2; --j) {
            ((qa_1)this.dS.remove(j)).aab();
        }
    }

    public int size() {
        return this.ec != null ? this.ec.size() : 0;
    }

    public void j() {
        super.j();
        this.ed = null;
        this.ee = null;
        this.dT = null;
        this.dS.clear();
        this.ec.clear();
        this.dU = null;
        this.ed = null;
        this.ee = null;
        this.dR = null;
    }

    public void b() {
        super.b();
        this.dyc = false;
        on_0 on_02 = new on_0();
        on_02.b();
        on_02.setWidget(this);
        this.a(on_02);
        this.dR = new sn_0();
        this.dV = -1;
        this.dW = true;
        this.dyu = true;
        this.eb = -1;
    }

    public void a(air_1 air_12) {
        ay_0 ay_02 = (ay_0)air_12;
        super.a(air_12);
        ay_02.setHorizontal(this.ba);
        ay_02.setEnableDND(this.dW);
        ay_02.setInnerExpandable(this.dZ);
        ay_02.setInnerNonBlocking(this.ea);
        ay_02.setClickSoundId(this.eb);
        for (int j = ay_02.dMc.size() - 1; j >= 0; --j) {
            adg_2 adg_22 = (adg_2)ay_02.dMc.get(j);
            adg_22.aab();
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ef) {
            this.setInnerExpandable(Gr.getBoolean(string));
        } else if (n2 == eg) {
            this.setClickSoundId(Gr.R(string));
        } else if (n2 == eh) {
            this.setInnerNonBlocking(Gr.getBoolean(string));
        } else if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == em) {
            this.setSelectionable(Gr.getBoolean(string));
        } else if (n2 == en) {
            this.setSelectionTogglable(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ef) {
            this.setInnerExpandable(Gr.getBoolean(object));
            return true;
        } else if (n2 == eg) {
            this.setClickSoundId(Gr.R(object));
            return true;
        } else if (n2 == eh) {
            this.setInnerNonBlocking(Gr.getBoolean(object));
            return true;
        } else if (n2 == ei) {
            if (object == null || object.getClass().isArray()) {
                this.setContent((Object[])object);
                return true;
            } else {
                if (!(object instanceof Iterable)) return false;
                this.setContent((Iterable)object);
            }
            return true;
        } else if (n2 == ek) {
            this.setSelected((sm_0)object);
            return true;
        } else {
            if (n2 != el) return super.setPropertyAttribute(n2, object);
            this.setSelectedValue(object);
        }
        return true;
    }

    static /* synthetic */ boolean a(ay_0 ay_02) {
        return ay_02.dX;
    }

    static /* synthetic */ boolean b(ay_0 ay_02) {
        return ay_02.dY;
    }

    static /* synthetic */ qa_1 c(ay_0 ay_02) {
        return ay_02.dT;
    }

    static /* synthetic */ void a(ay_0 ay_02, qa_1 qa_12) {
        ay_02.a(qa_12);
    }
}

