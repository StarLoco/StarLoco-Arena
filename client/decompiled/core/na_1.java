/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Stack;
import org.apache.log4j.Logger;

/*
 * Renamed from Na
 */
public abstract class na_1
extends air_1
implements JG,
acw_1,
anf_2,
Comparable {
    protected static Logger a = Logger.getLogger(na_1.class);
    protected ArrayList uA = new ArrayList();
    protected na_1 adf;
    private asz byF;
    private asz byG;
    private EnumSet byH;
    protected ArrayList byI = null;
    private boolean byJ = false;
    private int byK;
    private int byL;
    private short byM = (short)-1;
    protected aji_1 blb;
    protected boolean byN = false;
    protected String rE = null;
    protected boolean byO = false;
    protected boolean byP = false;
    protected qa_1 byQ;
    protected KU byR = null;
    protected ArrayList byS;
    protected ArrayList byT;
    protected boolean byU = true;
    public static final int byV = "id".hashCode();

    public void a(na_1 na_12) {
        this.a(na_12, true);
    }

    public void a(na_1 na_12, boolean bl2) {
        if (na_12 != null) {
            assert (!this.czc) : "Object is already checked-in" + this.getClass().getSimpleName();
            super.a(na_12);
            this.uA.add(na_12);
            na_12.setParent(this);
            na_12.setTreeDepth(this.byK + 1);
            bd.ce().cf();
            if (bl2 && na_12 instanceof adg_2) {
                this.h((adg_2)na_12);
            }
            if (this.isInTree()) {
                na_12.aaf();
            }
            na_12.setIsATemplate(this.czn);
        } else {
            a.error((Object)("Tentative d'ajout un element null \u00e0 " + this.getClass().getSimpleName()));
        }
    }

    public void b(na_1 na_12) {
        if (this.uA != null) {
            if (na_12 instanceof adg_2) {
                this.b((adg_2)na_12);
            }
            na_12.aag();
            this.uA.remove(na_12);
            na_12.setParent(null);
        }
    }

    public void k(na_1 na_12) {
        this.b(na_12);
        na_1.n(na_12);
    }

    public void aaa() {
        if (this.adf != null) {
            this.adf.b(this);
        }
    }

    public void aab() {
        if (this.adf != null) {
            this.adf.k(this);
        } else {
            na_1.n(this);
        }
    }

    public void removeAllChildren() {
        if (this.uA != null) {
            for (int j = this.uA.size() - 1; j >= 0; --j) {
                this.b((na_1)this.uA.get(j));
            }
            this.uA.clear();
        }
    }

    public void aac() {
        if (this.uA != null) {
            for (int j = this.uA.size() - 1; j >= 0; --j) {
                this.k((na_1)this.uA.get(j));
            }
            this.uA.clear();
        }
    }

    public void h(adg_2 adg_22) {
        if (this.adf != null) {
            this.adf.h(adg_22);
        } else {
            if (this.byI == null) {
                this.byI = new ArrayList();
            }
            this.byI.add(adg_22);
            this.setNeedsToPreProcess();
        }
    }

    public void b(adg_2 adg_22) {
        if (this.adf != null) {
            this.adf.b(adg_22);
        }
    }

    public void setWidgetOnTop(adg_2 adg_22) {
        if (this.adf != null) {
            this.adf.setWidgetOnTop(adg_22);
        }
    }

    public void a(aPk aPk2) {
        if (this.byS == null) {
            this.byS = new ArrayList(5);
        }
        this.byS.add(aPk2);
        aPk2.a(this);
        this.setNeedsToPreProcess();
    }

    public void b(aPk aPk2) {
        if (aPk2 == null) {
            return;
        }
        if (this.byT == null) {
            this.byT = new ArrayList(5);
        }
        if (!this.byT.contains(aPk2)) {
            this.byT.add(aPk2);
            this.setNeedsToPreProcess();
        }
    }

    public boolean q(Class clazz) {
        if (this.byS == null) {
            return false;
        }
        boolean bl2 = false;
        for (int j = this.byS.size() - 1; j >= 0; --j) {
            aPk aPk2 = (aPk)this.byS.get(j);
            if (!aPk2.getClass().equals(clazz)) continue;
            this.b(aPk2);
            bl2 = true;
        }
        return bl2;
    }

    public boolean r(Class clazz) {
        if (this.byS == null) {
            return false;
        }
        for (int j = this.byS.size() - 1; j >= 0; --j) {
            if (!((aPk)this.byS.get(j)).getClass().equals(clazz)) continue;
            return true;
        }
        return false;
    }

    public void a(qe_1 qe_12, ov_1 ov_12, boolean bl2) {
        int n2 = qe_12.ordinal();
        if (bl2) {
            ArrayList<ov_1> arrayList;
            if (this.byF == null) {
                this.byF = new asz(5);
            }
            if ((arrayList = (ArrayList<ov_1>)this.byF.get(n2)) == null) {
                arrayList = new ArrayList<ov_1>();
                this.byF.put(n2, arrayList);
            }
            if (!arrayList.contains(ov_12)) {
                arrayList.add(ov_12);
            }
        } else {
            ArrayList<ov_1> arrayList;
            if (this.byG == null) {
                this.byG = new asz(5);
            }
            if ((arrayList = (ArrayList<ov_1>)this.byG.get(n2)) == null) {
                arrayList = new ArrayList<ov_1>();
                this.byG.put(n2, arrayList);
            }
            if (!arrayList.contains(ov_12)) {
                arrayList.add(ov_12);
            }
        }
    }

    public void b(qe_1 qe_12, ov_1 ov_12, boolean bl2) {
        int n2 = qe_12.ordinal();
        if (bl2) {
            if (this.byF == null) {
                return;
            }
            ArrayList arrayList = (ArrayList)this.byF.get(n2);
            if (arrayList != null) {
                arrayList.remove(ov_12);
                if (arrayList.isEmpty()) {
                    this.byF.remove(n2);
                }
            }
        } else {
            if (this.byG == null) {
                return;
            }
            ArrayList arrayList = (ArrayList)this.byG.get(n2);
            if (arrayList != null) {
                arrayList.remove(ov_12);
                if (arrayList.isEmpty()) {
                    this.byG.remove(n2);
                }
            }
        }
    }

    public void aad() {
        if (this.byF != null) {
            this.byF.clear();
            this.byF = null;
        }
        if (this.byG != null) {
            this.byG.clear();
            this.byG = null;
        }
    }

    public ArrayList getListeners(qe_1 qe_12, boolean bl2) {
        if (bl2 && this.byF != null) {
            return (ArrayList)this.byF.get(qe_12.ordinal());
        }
        if (!bl2 && this.byG != null) {
            return (ArrayList)this.byG.get(qe_12.ordinal());
        }
        return null;
    }

    public eh_0 getElementType() {
        return eh_0.aTF;
    }

    public na_1 getParent() {
        return this.adf;
    }

    public void setParent(na_1 na_12) {
        assert (na_12 != this) : "On ne peut pas se d\u00e9finir soi-m\u00eame en parent";
        this.adf = na_12;
        if (this.adf != null && this.blb == null) {
            this.blb = this.adf.getElementMap();
        }
    }

    public ArrayList getChildren() {
        return this.uA;
    }

    public String getId() {
        return this.rE;
    }

    public void setId(String string) {
        if (this.rE != null && !this.rE.equalsIgnoreCase(string) && this.blb != null) {
            this.blb.X(this.rE, string);
            this.rE = string;
        } else if (this.rE == null && string != null && this.blb != null) {
            this.rE = string;
            this.blb.a(string, this);
        } else {
            this.rE = string;
        }
    }

    public short getModalLevel() {
        return this.byM;
    }

    public void setModalLevel(short s) {
        this.byM = s;
    }

    public boolean isValidAdd(air_1 air_12) {
        return air_12 != this;
    }

    public int getTreePosition() {
        return this.byL;
    }

    public void setTreePosition(int n2) {
        this.byL = n2;
        if (this.uA != null) {
            int n3 = this.uA.size();
            for (int j = 0; j < n3; ++j) {
                ((na_1)this.uA.get(j)).setTreePosition(n2 + j + 1);
            }
        }
    }

    public void setTreeDepth(int n2) {
        this.byK = n2;
        if (this.uA != null) {
            for (na_1 na_12 : this.uA) {
                na_12.setTreeDepth(n2 + 1);
            }
        }
    }

    public int getTreeDepth() {
        return this.byK;
    }

    public void setIsATemplate(boolean bl2) {
        this.czn |= bl2;
        for (int j = this.uA.size() - 1; j >= 0; --j) {
            ((na_1)this.uA.get(j)).setIsATemplate(bl2);
        }
    }

    public boolean isATemplate() {
        return this.czn;
    }

    public boolean b(qe_1 qe_12) {
        if (this.byF != null && this.byF.contains(qe_12.ordinal())) {
            return true;
        }
        return this.byG != null && this.byG.contains(qe_12.ordinal());
    }

    public boolean isValid() {
        return this.byO;
    }

    public boolean setAppearance(Zb zb) {
        a.warn((Object)("Tentative d'ajout d'une apparence " + zb.getClass().getSimpleName() + " \u00e0 un " + this.getClass().getSimpleName()));
        zb.aab();
        return false;
    }

    public adg_2 getParentWidget() {
        if (this.adf != null) {
            if (this.adf instanceof adg_2) {
                return (adg_2)this.adf;
            }
            return this.adf.getParentWidget();
        }
        return null;
    }

    public Object getParentOfType(Class clazz) {
        if (this.adf == null) {
            return null;
        }
        if (clazz.isAssignableFrom(this.adf.getClass())) {
            return this.adf;
        }
        return this.adf.getParentOfType(clazz);
    }

    public boolean l(na_1 na_12) {
        if (this.adf == null) {
            return false;
        }
        if (this.adf == na_12) {
            return true;
        }
        return this.adf.l(na_12);
    }

    public Object getElementValue() {
        return this;
    }

    public void setElementMap(aji_1 aji_12) {
        this.blb = aji_12;
    }

    public aji_1 getElementMap() {
        if (this.blb == null && this.adf != null) {
            return this.adf.getElementMap();
        }
        return this.blb;
    }

    public boolean isElementMapRoot() {
        return this.byN;
    }

    public void setElementMapRoot(boolean bl2) {
        this.byN = bl2;
    }

    public qa_1 getRenderableParent() {
        return this.byQ;
    }

    public void setRenderableParent(qa_1 qa_12) {
        if (this.byQ != qa_12) {
            this.byQ = qa_12;
        }
    }

    public void setChildrenAdded(boolean bl2) {
        this.byP = bl2;
    }

    public boolean aae() {
        return this.byP;
    }

    public KU getUserDefinedManager() {
        return this.byR;
    }

    public void setUserDefinedManager(KU kU) {
        this.byR = kU;
    }

    public void a(qe_1 qe_12, boolean bl2) {
        if (bl2 && this.byH != null) {
            this.byH.remove((Object)qe_12);
        } else if (!bl2) {
            if (this.byH == null) {
                this.byH = EnumSet.noneOf(qe_1.class);
            }
            this.byH.add(qe_12);
        }
    }

    protected void a(ke ke2, boolean bl2) {
    }

    public void setCanBeCloned(boolean bl2) {
        this.byU = bl2;
    }

    public boolean aO() {
        return this.byU;
    }

    public void a(ajw ajw2) {
    }

    public void Xq() {
        if (this.byR != null) {
            this.byR.Xq();
        }
    }

    public void Xr() {
        if (this.byR != null) {
            this.byR.Xr();
        }
    }

    public boolean isInTree() {
        if (this.adf == null) {
            return false;
        }
        return this.adf.isInTree();
    }

    public void aaf() {
        for (na_1 na_12 : this.uA) {
            na_12.aaf();
        }
    }

    public void aag() {
        if (this.byR != null) {
            this.byR.Xr();
            this.byR.Bq();
            add_1.aOG().aOO().fd(this.blb.getId()).b(this);
        }
        int n2 = this.uA.size();
        for (int j = 0; j < n2; ++j) {
            ((na_1)this.uA.get(j)).aag();
        }
    }

    public void a(air_1 air_12) {
        int n2;
        int n3;
        Object object;
        int n4;
        int n5;
        na_1 na_12 = (na_1)air_12;
        super.a(air_12);
        na_12.rE = this.rE;
        na_12.byP = this.byP;
        na_12.byM = this.byM;
        na_12.blb = this.blb;
        if (this.byF != null) {
            n5 = this.byF.size();
            for (n4 = 0; n4 < n5; ++n4) {
                object = (ArrayList)this.byF.jx(n4);
                n3 = ((ArrayList)object).size();
                for (n2 = 0; n2 < n3; ++n2) {
                    na_12.a(qe_1.hi(this.byF.hL(n4)), (ov_1)((ArrayList)object).get(n2), true);
                }
            }
        }
        if (this.byG != null) {
            n5 = this.byG.size();
            for (n4 = 0; n4 < n5; ++n4) {
                object = (ArrayList)this.byG.jx(n4);
                n3 = ((ArrayList)object).size();
                for (n2 = 0; n2 < n3; ++n2) {
                    na_12.a(qe_1.hi(this.byG.hL(n4)), (ov_1)((ArrayList)object).get(n2), false);
                }
            }
        }
        if (this.uA != null) {
            n5 = this.uA.size();
            for (n4 = 0; n4 < n5; ++n4) {
                object = (na_1)this.uA.get(n4);
                if (!((na_1)object).aO()) continue;
                na_12.a(((na_1)object).aah());
            }
        }
    }

    public na_1 aah() {
        try {
            na_1 na_12;
            try {
                na_12 = (na_1)ye_2.amJ().w(this.getClass()).newInstance();
            }
            catch (NullPointerException nullPointerException) {
                a.error((Object)("pas de factory trouv\u00e9e pour " + this.getClass().getSimpleName()), (Throwable)nullPointerException);
                return null;
            }
            this.a((air_1)na_12);
            return na_12;
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
            return null;
        }
    }

    public int m(na_1 na_12) {
        return na_12.getTreeDepth() - this.byK;
    }

    public boolean b(ke ke2, boolean bl2) {
        this.a(ke2, bl2);
        ke2.d(this);
        boolean bl3 = false;
        if (this.byH == null || !this.byH.contains((Object)ke2.aV())) {
            bl3 = bl2 ? this.b(ke2) : this.c(ke2);
        }
        if (bl3) {
            ke2.release();
            return true;
        }
        if (ke2.oF() == this && !ke2.oD()) {
            ke2.W(true);
            bl2 = false;
        } else if (ke2.oF() == this && ke2.oD()) {
            ke2.W(false);
        }
        if (bl2) {
            na_1 na_12 = ke2.oB();
            if (na_12 != null) {
                return na_12.b(ke2, true);
            }
        } else {
            if (ke2.oD()) {
                return ke2.oF().b(ke2, false);
            }
            if (this.adf != null) {
                return this.adf.b(ke2, false);
            }
            ke2.release();
        }
        return false;
    }

    public void e(ke ke2) {
        if (this.byG != null) {
            boolean bl2 = false;
            ArrayList arrayList = (ArrayList)this.byG.get(ke2.aV().ordinal());
            if (arrayList != null) {
                for (int j = 0; j < arrayList.size(); ++j) {
                    if (!(bl2 |= ((ov_1)arrayList.get(j)).a(ke2))) continue;
                    ke2.release();
                    break;
                }
            }
        }
    }

    public boolean f(ke ke2) {
        if (ke2 == null) {
            a.error((Object)"L'event est null, impossible de le traiter");
            return false;
        }
        if (ke2.oF() == null) {
            a.error((Object)("[" + (Object)((Object)ke2.aV()) + "] L'event n'a pas de target, impossible de le traiter"));
            return false;
        }
        ke2.e(this);
        boolean bl2 = true;
        na_1 na_12 = this instanceof ago_2 ? this : this.adf;
        ke2.c(this);
        while (na_12 != null && !(na_12 instanceof ago_2)) {
            ke2.c(na_12);
            na_12 = na_12.adf;
        }
        if (na_12 != null) {
            return na_12.b(ke2, bl2);
        }
        return false;
    }

    public static void n(na_1 na_12) {
        if (!na_12.isUnloading()) {
            na_12.release();
        }
    }

    public void validate() {
        this.byO = true;
    }

    public void invalidate() {
        this.byO = false;
        this.setNeedsToPostProcess();
    }

    public void Aj() {
        super.Aj();
        this.byP = true;
    }

    public void Mj() {
        this.byJ = true;
        this.setNeedsToMiddleProcess();
    }

    public boolean cc(int n2) {
        int n3;
        int n4;
        boolean bl2 = super.cc(n2);
        if (this.byI != null && !this.byI.isEmpty()) {
            n4 = this.byI.size();
            for (n3 = 0; n3 < n4; ++n3) {
                this.h((adg_2)this.byI.get(n3));
            }
            this.byI.clear();
        }
        if (this.byT != null && (n4 = this.byT.size()) > 0) {
            for (n3 = 0; n3 < n4; ++n3) {
                ((aPk)this.byT.get(n3)).ly();
            }
            if (this.byS != null) {
                this.byS.removeAll(this.byT);
            }
            this.byT.clear();
        }
        if (this.byS != null && (n4 = this.byS.size()) > 0) {
            for (n3 = 0; n3 < n4; ++n3) {
                ((aPk)this.byS.get(n3)).aS(n2);
            }
        }
        if (this.byS != null && this.byS.size() > 0) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean gU(int n2) {
        boolean bl2 = super.gU(n2);
        if (!this.byJ) {
            this.Mj();
        }
        return bl2;
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (!this.byO) {
            this.validate();
        }
        return bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(ke ke2) {
        if (this.byF == null) {
            return false;
        }
        ArrayList arrayList = this.getListeners(ke2.aV(), true);
        if (arrayList != null) {
            ArrayList arrayList2 = arrayList;
            synchronized (arrayList2) {
                for (int j = 0; j < arrayList.size(); ++j) {
                    if (!((ov_1)arrayList.get(j)).a(ke2)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean c(ke ke2) {
        if (this.byG == null) {
            return false;
        }
        ArrayList arrayList = this.getListeners(ke2.aV(), false);
        if (arrayList != null) {
            ArrayList arrayList2 = arrayList;
            synchronized (arrayList2) {
                for (int j = 0; j < arrayList.size(); ++j) {
                    if (!((ov_1)arrayList.get(j)).a(ke2)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public void j() {
        super.j();
        if (this.uA != null) {
            for (int j = this.uA.size() - 1; j >= 0; --j) {
                this.k((na_1)this.uA.get(j));
            }
            this.uA.clear();
        }
        if (this.byI != null) {
            this.byI.clear();
            this.byI = null;
        }
        this.DG = null;
        this.adf = null;
        this.aad();
        this.byF = null;
        this.byG = null;
        this.byH = null;
        if (this.blb != null) {
            if (this.rE != null) {
                this.blb.removeElement(this.rE);
            }
            if (this.byN) {
                add_1.aOG().kQ(this.blb.getId());
                this.blb.azj().lg(this.blb.getId());
            }
            this.blb = null;
        }
        this.rE = null;
        this.byQ = null;
        if (this.byS != null) {
            this.byS.clear();
            this.byS = null;
        }
        if (this.byT != null) {
            this.byT.clear();
            this.byT = null;
        }
        this.byR = null;
    }

    public void b() {
        super.b();
        this.byJ = false;
        this.byM = (short)-1;
        this.byK = 0;
        this.byN = false;
        this.byO = false;
        this.byP = false;
        this.czn = false;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (byV != n2) {
            return false;
        }
        this.setId(if_12.eM(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (byV != n2) {
            return false;
        }
        this.setId((String)object);
        return true;
    }

    public void a(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
        aji_1 aji_12;
        aji_1 aji_13 = aji_12 = stack != null ? (aji_1)stack.peek() : null;
        if (aji_12 == null && na_12 != null) {
            aji_12 = na_12.getElementMap();
        }
        this.setElementMap(aji_12);
        super.a(k_02, na_12, stack, afq_12);
    }
}

