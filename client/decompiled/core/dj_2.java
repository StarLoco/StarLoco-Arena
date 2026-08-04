/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

/*
 * Renamed from dj
 */
public abstract class dj_2
extends atd_0 {
    private static final String lj = "/antlib.xml";
    private static xo_2 lk = new xo_2(null);
    private String name;
    private String classname;
    private File ll;
    private String lm;
    private int format = 0;
    private boolean ln = false;
    private int lo = 0;
    private String lp;
    private String lq;
    private Class lr;
    private Class ls;

    public void a(akV akV2) {
        this.lo = akV2.getIndex();
    }

    public void a(XD xD) {
        this.format = xD.getIndex();
    }

    public String getName() {
        return this.name;
    }

    public File getFile() {
        return this.ll;
    }

    public String fH() {
        return this.lm;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute() {
        Object object;
        ClassLoader classLoader = this.aFX();
        if (!this.ln) {
            if (this.getURI() == null) {
                throw new eq_2("name, file or resource attribute of " + this.LF() + " is undefined", this.hW());
            }
            if (this.getURI().startsWith("antlib:")) {
                object = this.getURI();
                this.K(dj_2.J((String)object));
            } else {
                throw new eq_2("Only antlib URIs can be located from the URI alone,not the URI " + this.getURI());
            }
        }
        if (this.name != null) {
            if (this.classname == null) {
                throw new eq_2("classname attribute of " + this.LF() + " element " + "is undefined", this.hW());
            }
            this.a(classLoader, this.name, this.classname);
        } else {
            URL uRL;
            if (this.classname != null) {
                object = "You must not specify classname together with file or resource.";
                throw new eq_2((String)object, this.hW());
            }
            object = null;
            if (this.ll != null) {
                uRL = this.fI();
                if (uRL == null) {
                    return;
                }
                object = new si_1(this, uRL);
            } else {
                object = this.b(classLoader);
            }
            while (object.hasMoreElements()) {
                uRL = (URL)object.nextElement();
                int n2 = this.format;
                if (uRL.toString().toLowerCase(Locale.US).endsWith(".xml")) {
                    n2 = 1;
                }
                if (n2 == 0) {
                    this.a(classLoader, uRL);
                    break;
                }
                if (lk.DI().get(uRL) != null) {
                    this.l("Warning: Recursive loading of " + uRL + " ignored" + " at " + this.hW() + " originally loaded at " + lk.DI().get(uRL), 1);
                    continue;
                }
                try {
                    lk.DI().put(uRL, this.hW());
                    this.b(classLoader, uRL);
                }
                finally {
                    lk.DI().remove(uRL);
                }
            }
        }
    }

    public static String J(String string) {
        String string2;
        String string3 = string.substring("antlib:".length());
        if (string3.startsWith("//")) {
            string2 = string3.substring("//".length());
            if (!string2.endsWith(".xml")) {
                string2 = string2 + lj;
            }
        } else {
            string2 = string3.replace('.', '/') + lj;
        }
        return string2;
    }

    private URL fI() {
        String string = null;
        if (!this.ll.exists()) {
            string = "File " + this.ll + " does not exist";
        }
        if (string == null && !this.ll.isFile()) {
            string = "File " + this.ll + " is not a file";
        }
        try {
            if (string == null) {
                return this.ll.toURL();
            }
        }
        catch (Exception exception) {
            string = "File " + this.ll + " cannot use as URL: " + exception.toString();
        }
        switch (this.lo) {
            case 3: {
                throw new eq_2(string);
            }
            case 0: 
            case 1: {
                this.l(string, 1);
                break;
            }
            case 2: {
                this.l(string, 3);
                break;
            }
        }
        return null;
    }

    private Enumeration b(ClassLoader classLoader) {
        Enumeration<URL> enumeration;
        try {
            enumeration = classLoader.getResources(this.lm);
        }
        catch (IOException iOException) {
            throw new eq_2("Could not fetch resources named " + this.lm, iOException, this.hW());
        }
        if (!enumeration.hasMoreElements()) {
            String string = "Could not load definitions from resource " + this.lm + ". It could not be found.";
            switch (this.lo) {
                case 3: {
                    throw new eq_2(string);
                }
                case 0: 
                case 1: {
                    this.l(string, 1);
                    break;
                }
                case 2: {
                    this.l(string, 3);
                    break;
                }
            }
        }
        return enumeration;
    }

    protected void a(ClassLoader classLoader, URL uRL) {
        InputStream inputStream = null;
        try {
            inputStream = uRL.openStream();
            if (inputStream == null) {
                this.l("Could not load definitions from " + uRL, 1);
                return;
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            Enumeration enumeration = properties.keys();
            while (enumeration.hasMoreElements()) {
                this.name = (String)enumeration.nextElement();
                this.classname = properties.getProperty(this.name);
                this.a(classLoader, this.name, this.classname);
            }
        }
        catch (IOException iOException) {
            throw new eq_2(iOException, this.hW());
        }
        finally {
            ga_2.h(inputStream);
        }
    }

    private void b(ClassLoader classLoader, URL uRL) {
        try {
            abh_2 abh_22 = abh_2.a(this.TP(), uRL, this.getURI());
            abh_22.setClassLoader(classLoader);
            abh_22.setURI(this.getURI());
            abh_22.execute();
        }
        catch (eq_2 eq_22) {
            throw es_2.a(eq_22, this.hW());
        }
    }

    public void e(File file) {
        if (this.ln) {
            this.fJ();
        }
        this.ln = true;
        this.ll = file;
    }

    public void K(String string) {
        if (this.ln) {
            this.fJ();
        }
        this.ln = true;
        this.lm = string;
    }

    public void L(String string) {
        if (this.ln) {
            this.fJ();
        }
        if (!string.startsWith("antlib:")) {
            throw new eq_2("Invalid antlib attribute - it must start with antlib:");
        }
        this.setURI(string);
        this.lm = string.substring("antlib:".length()).replace('.', '/') + lj;
        this.ln = true;
    }

    public void setName(String string) {
        if (this.ln) {
            this.fJ();
        }
        this.ln = true;
        this.name = string;
    }

    public String getClassname() {
        return this.classname;
    }

    public void setClassname(String string) {
        this.classname = string;
    }

    public void M(String string) {
        this.lp = string;
    }

    protected void c(Class clazz) {
        this.lr = clazz;
    }

    public void N(String string) {
        this.lq = string;
    }

    protected void d(Class clazz) {
        this.ls = clazz;
    }

    protected void a(ClassLoader classLoader, String string, String string2) {
        Class<?> clazz = null;
        try {
            try {
                string = es_2.s(this.getURI(), string);
                if (this.lo != 2) {
                    clazz = Class.forName(string2, true, classLoader);
                }
                if (this.lp != null) {
                    this.lr = Class.forName(this.lp, true, classLoader);
                }
                if (this.lq != null) {
                    this.ls = Class.forName(this.lq, true, classLoader);
                }
                alv_2 alv_22 = new alv_2();
                alv_22.setName(string);
                alv_22.setClassName(string2);
                alv_22.b(clazz);
                alv_22.c(this.lr);
                alv_22.d(this.ls);
                alv_22.setClassLoader(classLoader);
                if (clazz != null) {
                    alv_22.h(this.TP());
                }
                abm_1.D(this.TP()).a(alv_22);
            }
            catch (ClassNotFoundException classNotFoundException) {
                String string3 = this.LF() + " class " + string2 + " cannot be found";
                throw new eq_2(string3, classNotFoundException, this.hW());
            }
            catch (NoClassDefFoundError noClassDefFoundError) {
                String string4 = this.LF() + " A class needed by class " + string2 + " cannot be found: " + noClassDefFoundError.getMessage();
                throw new eq_2(string4, noClassDefFoundError, this.hW());
            }
        }
        catch (eq_2 eq_22) {
            switch (this.lo) {
                case 0: 
                case 3: {
                    throw eq_22;
                }
                case 1: {
                    this.l(eq_22.hW() + "Warning: " + eq_22.getMessage(), 1);
                    break;
                }
                default: {
                    this.l(eq_22.hW() + eq_22.getMessage(), 4);
                }
            }
        }
    }

    private void fJ() {
        throw new eq_2("Only one of the attributes name, file and resource can be set", this.hW());
    }
}

