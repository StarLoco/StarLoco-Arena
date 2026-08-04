/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

public class Zq
extends avg
implements Cloneable {
    public static final String ccO = "@";
    public static final String ccP = "@";
    private String ccQ = "@";
    private String ccR = "@";
    private Vector ccS;
    private boolean ccT = false;
    private boolean ccU = true;
    private Hashtable ccV = null;
    private Vector ccW = new Vector();
    private if_2 ccX = if_2.yB;
    private boolean ccY = false;
    private int ccZ = 0;
    private Vector ano = new Vector();
    static Class cda;

    public Zq() {
    }

    protected Zq(Zq zq) {
        this.ano = (Vector)zq.ano().clone();
    }

    protected synchronized Vector ano() {
        if (this.aId()) {
            return this.anp().ano();
        }
        if (!this.ccY) {
            this.ccY = true;
            int n2 = this.ccW.size();
            for (int j = 0; j < n2; ++j) {
                this.G((File)this.ccW.get(j));
            }
            this.ccW.clear();
            this.ccY = false;
        }
        return this.ano;
    }

    protected Zq anp() {
        return (Zq)this.k(cda == null ? (cda = Zq.a("Zq")) : cda, "filterset");
    }

    public synchronized Hashtable anq() {
        if (this.ccV == null) {
            this.ccV = new Hashtable(this.ano().size());
            Enumeration enumeration = this.ano().elements();
            while (enumeration.hasMoreElements()) {
                fj fj2 = (fj)enumeration.nextElement();
                this.ccV.put(fj2.io(), fj2.getValue());
            }
        }
        return this.ccV;
    }

    public void F(File file) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.ccW.add(file);
    }

    public void gR(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (string == null || "".equals(string)) {
            throw new eq_2("beginToken must not be empty");
        }
        this.ccQ = string;
    }

    public String anr() {
        if (this.aId()) {
            return this.anp().anr();
        }
        return this.ccQ;
    }

    public void gS(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (string == null || "".equals(string)) {
            throw new eq_2("endToken must not be empty");
        }
        this.ccR = string;
    }

    public String ans() {
        if (this.aId()) {
            return this.anp().ans();
        }
        return this.ccR;
    }

    public void cH(boolean bl2) {
        this.ccU = bl2;
    }

    public boolean ant() {
        return this.ccU;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public synchronized void G(File file) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (!file.exists()) {
            this.gV("Could not read filters from file " + file + " as it doesn't exist.");
        }
        if (file.isFile()) {
            this.l("Reading filters from " + file, 3);
            FileInputStream fileInputStream = null;
            try {
                try {
                    Properties properties = new Properties();
                    fileInputStream = new FileInputStream(file);
                    properties.load(fileInputStream);
                    Enumeration<?> enumeration = properties.propertyNames();
                    Vector vector = this.ano();
                    while (enumeration.hasMoreElements()) {
                        String string = (String)enumeration.nextElement();
                        String string2 = properties.getProperty(string);
                        vector.addElement(new fj(string, string2));
                    }
                    Object var9_9 = null;
                }
                catch (Exception exception) {
                    throw new eq_2("Could not read filters from file: " + file, exception);
                }
            }
            catch (Throwable throwable) {
                Object var9_10 = null;
                ga_2.h(fileInputStream);
                throw throwable;
            }
            ga_2.h(fileInputStream);
        } else {
            this.gV("Must specify a file rather than a directory in the filtersfile attribute:" + file);
        }
        this.ccV = null;
    }

    public synchronized String gT(String string) {
        return this.gU(string);
    }

    public synchronized void a(fj fj2) {
        if (this.aId()) {
            throw this.aIi();
        }
        this.ano.addElement(fj2);
        this.ccV = null;
    }

    public dZ anu() {
        if (this.aId()) {
            throw this.aIi();
        }
        return new dZ(this);
    }

    public synchronized void H(String string, String string2) {
        if (this.aId()) {
            throw this.aIi();
        }
        this.a(new fj(string, string2));
    }

    public synchronized void a(Zq zq) {
        if (this.aId()) {
            throw this.aIi();
        }
        Enumeration enumeration = zq.ano().elements();
        while (enumeration.hasMoreElements()) {
            this.a((fj)enumeration.nextElement());
        }
    }

    public synchronized boolean anv() {
        return this.ano().size() > 0;
    }

    public synchronized Object clone() {
        if (this.aId()) {
            return this.anp().clone();
        }
        try {
            Zq zq = (Zq)super.clone();
            zq.ano = (Vector)this.ano().clone();
            zq.l(this.TP());
            return zq;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new eq_2(cloneNotSupportedException);
        }
    }

    public void a(if_2 if_22) {
        this.ccX = if_22;
    }

    public if_2 anw() {
        return this.ccX;
    }

    private synchronized String gU(String string) {
        String string2 = this.anr();
        String string3 = this.ans();
        int n2 = string.indexOf(string2);
        if (n2 > -1) {
            Hashtable hashtable = this.anq();
            try {
                int n3;
                StringBuffer stringBuffer = new StringBuffer();
                int n4 = 0;
                String string4 = null;
                String string5 = null;
                while (n2 > -1 && (n3 = string.indexOf(string3, n2 + string2.length() + 1)) != -1) {
                    string4 = string.substring(n2 + string2.length(), n3);
                    stringBuffer.append(string.substring(n4, n2));
                    if (hashtable.containsKey(string4)) {
                        string5 = (String)hashtable.get(string4);
                        if (this.ccU && !string5.equals(string4)) {
                            string5 = this.M(string5, string4);
                        }
                        this.l("Replacing: " + string2 + string4 + string3 + " -> " + string5, 3);
                        stringBuffer.append(string5);
                        n4 = n2 + string2.length() + string4.length() + string3.length();
                    } else {
                        stringBuffer.append(string2);
                        n4 = n2 + string2.length();
                    }
                    n2 = string.indexOf(string2, n4);
                }
                stringBuffer.append(string.substring(n4));
                return stringBuffer.toString();
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                return string;
            }
        }
        return string;
    }

    private synchronized String M(String string, String string2) {
        String string3 = this.anr();
        String string4 = this.ans();
        if (this.ccZ == 0) {
            this.ccS = new Vector();
        }
        ++this.ccZ;
        if (this.ccS.contains(string2) && !this.ccT) {
            this.ccT = true;
            System.out.println("Infinite loop in tokens. Currently known tokens : " + this.ccS.toString() + "\nProblem token : " + string3 + string2 + string4 + " called from " + string3 + this.ccS.lastElement().toString() + string4);
            --this.ccZ;
            return string2;
        }
        this.ccS.addElement(string2);
        String string5 = this.gU(string);
        if (string5.indexOf(string3) == -1 && !this.ccT && this.ccZ == 1) {
            this.ccS = null;
        } else if (this.ccT && this.ccS.size() > 0) {
            string5 = (String)this.ccS.remove(this.ccS.size() - 1);
            if (this.ccS.size() == 0) {
                string5 = string3 + string5 + string4;
                this.ccT = false;
            }
        }
        --this.ccZ;
        return string5;
    }

    private void gV(String string) {
        switch (this.ccX.getIndex()) {
            case 2: {
                return;
            }
            case 0: {
                throw new eq_2(string);
            }
            case 1: {
                this.l(string, 1);
                return;
            }
        }
        throw new eq_2("Invalid value for onMissingFiltersFile");
    }

    static Vector b(Zq zq) {
        return zq.ccW;
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

