/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;

/*
 * Renamed from Jh
 */
public class jh_1 {
    private static List EMPTY_LIST = new Vector(0);
    private final aom_2 bju;
    private final qq_0 bjv;
    private final ArrayList bjw;
    private final asi_0 bjx = new asi_0(this);
    zf_0 bjy;
    Locator awt;
    aax_2 bjz;
    Stack bjA;
    zf_0 bjB = null;

    public jh_1(vU vU2, aom_2 aom_22, zf_0 zf_02) {
        this.bjx.a(vU2);
        this.bju = aom_22;
        this.bjv = new qq_0(vU2, this);
        this.bjw = new ArrayList(3);
        this.bjy = zf_02;
        this.bjA = new Stack();
        this.bjz = new aax_2(this);
    }

    public void f(Map map) {
        this.bjv.c(map);
    }

    public qq_0 Vx() {
        return this.Vy();
    }

    public qq_0 Vy() {
        return this.bjv;
    }

    public void startDocument() {
    }

    public void a(auk_0 auk_02) {
        this.setDocumentLocator(auk_02.getLocator());
        this.startElement(auk_02.namespaceURI, auk_02.localName, auk_02.qName, auk_02.cWI);
    }

    private void startElement(String string, String string2, String string3, Attributes attributes) {
        String string4 = this.w(string2, string3);
        this.bjy.push(string4);
        if (this.bjB != null) {
            this.Vz();
            return;
        }
        List list = this.a(this.bjy, attributes);
        if (list != null) {
            this.bjA.add(list);
            this.a(list, string4, attributes);
        } else {
            this.Vz();
            String string5 = "no applicable action for [" + string4 + "], current pattern is [" + this.bjy + "]";
            this.bjx.eg(string5);
        }
    }

    private void Vz() {
        this.bjA.add(EMPTY_LIST);
    }

    public void a(AJ aJ) {
        this.setDocumentLocator(aJ.awt);
        String string = aJ.getText();
        List list = (List)this.bjA.peek();
        if (string != null) {
            string = string.trim();
        }
        if (string.length() > 0) {
            this.a(list, string);
        }
    }

    public void a(bi_0 bi_02) {
        this.setDocumentLocator(bi_02.awt);
        this.endElement(bi_02.namespaceURI, bi_02.localName, bi_02.qName);
    }

    private void endElement(String string, String string2, String string3) {
        List list = (List)this.bjA.pop();
        if (this.bjB != null) {
            if (this.bjB.equals(this.bjy)) {
                this.bjB = null;
            }
        } else if (list != EMPTY_LIST) {
            this.b(list, this.w(string2, string3));
        }
        this.bjy.pop();
    }

    public Locator getLocator() {
        return this.awt;
    }

    public void setDocumentLocator(Locator locator) {
        this.awt = locator;
    }

    String w(String string, String string2) {
        String string3 = string;
        if (string3 == null || string3.length() < 1) {
            string3 = string2;
        }
        return string3;
    }

    public void a(acz_1 acz_12) {
        this.bjw.add(acz_12);
    }

    List b(zf_0 zf_02, Attributes attributes, qq_0 qq_02) {
        int n2 = this.bjw.size();
        for (int j = 0; j < n2; ++j) {
            acz_1 acz_12 = (acz_1)this.bjw.get(j);
            if (!acz_12.a(zf_02, attributes, qq_02)) continue;
            ArrayList<acz_1> arrayList = new ArrayList<acz_1>(1);
            arrayList.add(acz_12);
            return arrayList;
        }
        return null;
    }

    List a(zf_0 zf_02, Attributes attributes) {
        List list = this.bju.c(zf_02);
        if (list == null) {
            list = this.b(zf_02, attributes, this.bjv);
        }
        return list;
    }

    void a(List list, String string, Attributes attributes) {
        if (list == null) {
            return;
        }
        for (ka_0 ka_02 : list) {
            try {
                ka_02.a(this.bjv, string, attributes);
            }
            catch (vf_1 vf_12) {
                this.bjB = (zf_0)this.bjy.clone();
                this.bjx.e("ActionException in Action for tag [" + string + "]", vf_12);
            }
            catch (RuntimeException runtimeException) {
                this.bjB = (zf_0)this.bjy.clone();
                this.bjx.e("RuntimeException in Action for tag [" + string + "]", runtimeException);
            }
        }
    }

    private void a(List list, String string) {
        if (list == null) {
            return;
        }
        for (ka_0 ka_02 : list) {
            try {
                ka_02.b(this.bjv, string);
            }
            catch (vf_1 vf_12) {
                this.bjx.e("Exception in end() methd for action [" + ka_02 + "]", vf_12);
            }
        }
    }

    private void b(List list, String string) {
        if (list == null) {
            return;
        }
        for (ka_0 ka_02 : list) {
            try {
                ka_02.a(this.bjv, string);
            }
            catch (vf_1 vf_12) {
                this.bjx.e("ActionException in Action for tag [" + string + "]", vf_12);
            }
            catch (RuntimeException runtimeException) {
                this.bjx.e("RuntimeException in Action for tag [" + string + "]", runtimeException);
            }
        }
    }

    public aom_2 VA() {
        return this.bju;
    }

    public void g(List list) {
        this.bjz.g(list);
    }

    public void h(List list) {
        if (this.bjz != null) {
            this.bjz.h(list);
        }
    }
}

