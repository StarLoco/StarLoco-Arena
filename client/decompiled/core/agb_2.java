/*
 * Decompiled with CFR 0.152.
 */
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*
 * Renamed from agb
 */
public class agb_2 {
    public static Context avT() {
        return new InitialContext();
    }

    public static String a(Context context, String string) {
        if (context == null) {
            return null;
        }
        try {
            return (String)context.lookup(string);
        }
        catch (NamingException namingException) {
            return null;
        }
    }
}

