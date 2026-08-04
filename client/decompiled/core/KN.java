/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;

public class KN {
    private UI hL;
    private File K;
    private Vector bpl = new Vector();
    private File aGH;
    private String bpm;
    private Locator awt;
    private id_2 aGI = new id_2();
    private id_2 bpn = null;
    private Vector bpo = new Vector();
    private boolean bpp = false;
    private Map bpq = new HashMap();
    private Map bpr = null;

    public KN(UI uI) {
        this.hL = uI;
        this.aGI.l(uI);
        this.aGI.setName("");
        this.bpl.addElement(this.aGI);
    }

    public void w(File file) {
        this.K = file;
        this.aGH = new File(file.getParent());
        this.aGI.a(new axc_0(file.getAbsolutePath()));
    }

    public File Xa() {
        return this.K;
    }

    public File Xb() {
        return this.aGH;
    }

    public UI TP() {
        return this.hL;
    }

    public String Xc() {
        return this.bpm;
    }

    public void fb(String string) {
        this.bpm = string;
    }

    public fy_2 Xd() {
        if (this.bpo.size() < 1) {
            return null;
        }
        return (fy_2)this.bpo.elementAt(this.bpo.size() - 1);
    }

    public fy_2 Xe() {
        if (this.bpo.size() < 2) {
            return null;
        }
        return (fy_2)this.bpo.elementAt(this.bpo.size() - 2);
    }

    public void e(fy_2 fy_22) {
        this.bpo.addElement(fy_22);
    }

    public void Xf() {
        if (this.bpo.size() > 0) {
            this.bpo.removeElementAt(this.bpo.size() - 1);
        }
    }

    public Vector Xg() {
        return this.bpo;
    }

    public void b(id_2 id_22) {
        this.bpl.addElement(id_22);
        this.bpn = id_22;
    }

    public id_2 Xh() {
        return this.bpn;
    }

    public id_2 Xi() {
        return this.aGI;
    }

    public void c(id_2 id_22) {
        this.bpn = id_22;
    }

    public void d(id_2 id_22) {
        this.aGI = id_22;
    }

    public Vector Xj() {
        return this.bpl;
    }

    public void a(Object object, Attributes attributes) {
        String string = attributes.getValue("id");
        if (string != null) {
            this.hL.n(string, object);
        }
    }

    public Locator getLocator() {
        return this.awt;
    }

    public void setLocator(Locator locator) {
        this.awt = locator;
    }

    public boolean Xk() {
        return this.bpp;
    }

    public void bN(boolean bl2) {
        this.bpp = bl2;
    }

    public void startPrefixMapping(String string, String string2) {
        ArrayList<String> arrayList = (ArrayList<String>)this.bpq.get(string);
        if (arrayList == null) {
            arrayList = new ArrayList<String>();
            this.bpq.put(string, arrayList);
        }
        arrayList.add(string2);
    }

    public void endPrefixMapping(String string) {
        List list = (List)this.bpq.get(string);
        if (list == null || list.size() == 0) {
            return;
        }
        list.remove(list.size() - 1);
    }

    public String fc(String string) {
        List list = (List)this.bpq.get(string);
        if (list == null || list.size() == 0) {
            return null;
        }
        return (String)list.get(list.size() - 1);
    }

    public Map Xl() {
        return this.bpr;
    }

    public void h(Map map) {
        this.bpr = map;
    }
}

