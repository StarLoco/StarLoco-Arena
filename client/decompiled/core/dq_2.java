/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mysql.jdbc.jdbc2.optional.MysqlDataSource
 */
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;

/*
 * Renamed from Dq
 */
public class dq_2
extends oc_1 {
    public dq_2(String string, String string2, String string3, String string4, int n2, int n3) {
        super(string, string2, string3, string4, n2, n3);
    }

    protected DataSource a(String string, String string2, String string3, String string4, int n2) {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setDatabaseName(string);
        mysqlDataSource.setServerName(string2);
        mysqlDataSource.setUser(string3);
        mysqlDataSource.setPassword(string4);
        mysqlDataSource.setPort(n2);
        mysqlDataSource.setEncoding("utf8");
        mysqlDataSource.setCharacterEncoding("utf8");
        return mysqlDataSource;
    }
}

