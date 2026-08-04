/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class TP
extends avg
implements Cloneable {
    private Vector bOJ = new Vector();
    private Vector bOK = new Vector();
    private Vector bOL = new Vector();
    private Vector bOM = new Vector();

    public void a(awq_0 awq_02) {
        if (!this.bOJ.isEmpty() || !this.bOK.isEmpty()) {
            throw this.aIh();
        }
        super.a(awq_02);
    }

    public void a(TP tP) {
        int n2;
        if (this.aId()) {
            throw this.aIi();
        }
        String[] stringArray = tP.s(this.TP());
        String[] stringArray2 = tP.t(this.TP());
        if (stringArray != null) {
            for (n2 = 0; n2 < stringArray.length; ++n2) {
                this.agj().setName(stringArray[n2]);
            }
        }
        if (stringArray2 != null) {
            for (n2 = 0; n2 < stringArray2.length; ++n2) {
                this.agl().setName(stringArray2[n2]);
            }
        }
    }

    public bM agj() {
        if (this.aId()) {
            throw this.aIi();
        }
        return this.d(this.bOJ);
    }

    public bM agk() {
        if (this.aId()) {
            throw this.aIi();
        }
        return this.d(this.bOL);
    }

    public bM agl() {
        if (this.aId()) {
            throw this.aIi();
        }
        return this.d(this.bOK);
    }

    public bM agm() {
        if (this.aId()) {
            throw this.aIi();
        }
        return this.d(this.bOM);
    }

    public void fT(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (string != null && string.length() > 0) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ", ", false);
            while (stringTokenizer.hasMoreTokens()) {
                this.agj().setName(stringTokenizer.nextToken());
            }
        }
    }

    public void fU(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (string != null && string.length() > 0) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ", ", false);
            while (stringTokenizer.hasMoreTokens()) {
                this.agl().setName(stringTokenizer.nextToken());
            }
        }
    }

    private bM d(Vector vector) {
        bM bM2 = new bM(this);
        vector.addElement(bM2);
        return bM2;
    }

    public void y(File file) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.agk().setName(file.getAbsolutePath());
    }

    public void z(File file) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.agm().setName(file.getAbsolutePath());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(File file, Vector vector, UI uI) {
        BufferedReader bufferedReader = null;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String string = bufferedReader.readLine();
                while (string != null) {
                    if (string.length() > 0) {
                        string = uI.fZ(string);
                        this.d(vector).setName(string);
                    }
                    string = bufferedReader.readLine();
                }
                Object var8_7 = null;
                if (null == bufferedReader) return;
            }
            catch (IOException iOException) {
                String string2 = "An error occurred while reading from pattern file: " + file;
                throw new eq_2(string2, iOException);
            }
        }
        catch (Throwable throwable) {
            Object var8_8 = null;
            if (null == bufferedReader) throw throwable;
            try {
                bufferedReader.close();
                throw throwable;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            throw throwable;
        }
        try {}
        catch (IOException iOException) {}
        bufferedReader.close();
        return;
    }

    public void a(TP tP, UI uI) {
        String[] stringArray;
        if (this.aId()) {
            throw new eq_2("Cannot append to a reference");
        }
        String[] stringArray2 = tP.s(uI);
        if (stringArray2 != null) {
            for (int j = 0; j < stringArray2.length; ++j) {
                this.agj().setName(stringArray2[j]);
            }
        }
        if ((stringArray = tP.t(uI)) != null) {
            for (int j = 0; j < stringArray.length; ++j) {
                this.agl().setName(stringArray[j]);
            }
        }
    }

    public String[] s(UI uI) {
        if (this.aId()) {
            return this.v(uI).s(uI);
        }
        this.w(uI);
        return this.a(this.bOJ, uI);
    }

    public String[] t(UI uI) {
        if (this.aId()) {
            return this.v(uI).t(uI);
        }
        this.w(uI);
        return this.a(this.bOK, uI);
    }

    public boolean u(UI uI) {
        if (this.aId()) {
            return this.v(uI).u(uI);
        }
        return this.bOL.size() > 0 || this.bOM.size() > 0 || this.bOJ.size() > 0 || this.bOK.size() > 0;
    }

    private TP v(UI uI) {
        return (TP)this.O(uI);
    }

    private String[] a(Vector vector, UI uI) {
        if (vector.size() == 0) {
            return null;
        }
        Vector<String> vector2 = new Vector<String>();
        Object[] objectArray = vector.elements();
        while (objectArray.hasMoreElements()) {
            bM bM2 = (bM)objectArray.nextElement();
            String string = bM2.d(uI);
            if (string == null || string.length() <= 0) continue;
            vector2.addElement(string);
        }
        objectArray = new String[vector2.size()];
        vector2.copyInto(objectArray);
        return objectArray;
    }

    private void w(UI uI) {
        File file;
        String string;
        bM bM2;
        Enumeration enumeration;
        if (this.bOL.size() > 0) {
            enumeration = this.bOL.elements();
            while (enumeration.hasMoreElements()) {
                bM2 = (bM)enumeration.nextElement();
                string = bM2.d(uI);
                if (string == null) continue;
                file = uI.gg(string);
                if (!file.exists()) {
                    throw new eq_2("Includesfile " + file.getAbsolutePath() + " not found.");
                }
                this.a(file, this.bOJ, uI);
            }
            this.bOL.removeAllElements();
        }
        if (this.bOM.size() > 0) {
            enumeration = this.bOM.elements();
            while (enumeration.hasMoreElements()) {
                bM2 = (bM)enumeration.nextElement();
                string = bM2.d(uI);
                if (string == null) continue;
                file = uI.gg(string);
                if (!file.exists()) {
                    throw new eq_2("Excludesfile " + file.getAbsolutePath() + " not found.");
                }
                this.a(file, this.bOK, uI);
            }
            this.bOM.removeAllElements();
        }
    }

    public String toString() {
        return "patternSet{ includes: " + this.bOJ + " excludes: " + this.bOK + " }";
    }

    public Object clone() {
        try {
            TP tP = (TP)super.clone();
            tP.bOJ = (Vector)this.bOJ.clone();
            tP.bOK = (Vector)this.bOK.clone();
            tP.bOL = (Vector)this.bOL.clone();
            tP.bOM = (Vector)this.bOM.clone();
            return tP;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new eq_2(cloneNotSupportedException);
        }
    }

    public void b(TP tP) {
        this.a(new ayl_0(tP, null));
    }
}

