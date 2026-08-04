/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.postgresql.jdbc3.Jdbc3SimpleDataSource
 */
import javax.sql.DataSource;
import org.postgresql.jdbc3.Jdbc3SimpleDataSource;

/*
 * Renamed from oE
 */
public class oe_2
extends oc_1 {
    public oe_2(String string, String string2, String string3, String string4, int n2, int n3) {
        super(string, string2, string3, string4, n2, n3);
    }

    protected DataSource a(String string, String string2, String string3, String string4, int n2) {
        Jdbc3SimpleDataSource jdbc3SimpleDataSource = new Jdbc3SimpleDataSource();
        jdbc3SimpleDataSource.setDatabaseName(string);
        jdbc3SimpleDataSource.setServerName(string2);
        jdbc3SimpleDataSource.setUser(string3);
        jdbc3SimpleDataSource.setPassword(string4);
        jdbc3SimpleDataSource.setPortNumber(n2);
        return jdbc3SimpleDataSource;
    }
}

