/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;

public class afT {
    private static final Logger a = Logger.getLogger(afT.class);
    private BufferedReader csQ;

    public afT() {
        try {
            File file = new File("c:/replay0.rda");
            if (file.exists()) {
                this.csQ = new BufferedReader(new FileReader("c:/replay0.rda"));
            }
        }
        catch (IOException iOException) {
            a.error((Object)"Erreur lors de la cr\u00e9ation du replayReader ", (Throwable)iOException);
        }
    }

    public afT(String string) {
        try {
            this.csQ = new BufferedReader(new FileReader(string));
        }
        catch (IOException iOException) {
            a.error((Object)("Erreur lors de la cr\u00e9ation du replayReader " + string), (Throwable)iOException);
        }
    }

    public String readLine() {
        if (this.csQ == null) {
            return null;
        }
        try {
            return this.csQ.readLine();
        }
        catch (IOException iOException) {
            a.error((Object)"Erreur lors de la lecture d'une ligne", (Throwable)iOException);
            return null;
        }
    }

    public void close() {
        if (this.csQ == null) {
            return;
        }
        try {
            this.csQ.close();
        }
        catch (IOException iOException) {
            a.error((Object)"Erreur lors de la fermeture", (Throwable)iOException);
        }
    }
}

