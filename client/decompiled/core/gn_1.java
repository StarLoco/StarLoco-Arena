/*
 * Decompiled with CFR 0.152.
 */
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Renamed from gN
 */
class gn_1
extends arr {
    private static final acl_0 uG = new ym_0(new nt_2());
    private static final byte[] uH = null;
    private static final byte[] uI = new byte[]{0, 0};
    private String uJ;
    private boolean uK;
    private boolean uL;
    private short uM;
    private long uN;
    private byte[] uO = uI;

    gn_1() {
    }

    static gn_1 km() {
        gn_1 gn_12;
        try {
            gn_12 = (gn_1)uG.adr();
            gn_12.a(uG);
        }
        catch (Exception exception) {
            gn_12 = new gn_1();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type StatisticsReportSaveRequest : " + exception.getMessage()));
        }
        return gn_12;
    }

    void I(boolean bl2) {
        this.uK = bl2;
    }

    public void J(boolean bl2) {
        this.uL = bl2;
    }

    void y(short s) {
        this.uM = s;
    }

    void R(long l2) {
        this.uN = l2;
    }

    void f(byte[] byArray) {
        this.uO = byArray == uH ? uI : byArray;
    }

    public pr_0 a(jn_0 jn_02) {
        ajv_1 ajv_12 = ajv_1.azz();
        ajv_12.y(this.uM);
        ajv_12.R(this.uN);
        try {
            PreparedStatement preparedStatement;
            boolean bl2 = false;
            if (this.uK) {
                this.uJ = "INSERT INTO tbl_dynamic_statistics(statistics_model_id,statistics_report_id,statistics_report) VALUES(?,?,?);";
                preparedStatement = jn_02.getConnection().prepareStatement(this.uJ);
                bl2 = true;
                preparedStatement.setShort(1, this.uM);
                preparedStatement.setLong(2, this.uN);
                preparedStatement.setBytes(3, this.uO);
            } else if (this.uL) {
                this.uJ = "delete from tbl_dynamic_statistics where statistics_report_id = ?;";
                preparedStatement = jn_02.getConnection().prepareStatement(this.uJ);
                bl2 = true;
                preparedStatement.setLong(1, this.uN);
            } else {
                this.uJ = "UPDATE tbl_dynamic_statistics SET statistics_report=? WHERE statistics_model_id=? AND statistics_report_id=?;";
                preparedStatement = jn_02.gv(this.getId());
                if (preparedStatement == null) {
                    preparedStatement = jn_02.getConnection().prepareStatement(this.uJ);
                    jn_02.a(this.getId(), preparedStatement);
                }
                preparedStatement.setBytes(1, this.uO);
                preparedStatement.setShort(2, this.uM);
                preparedStatement.setLong(3, this.uN);
            }
            boolean bl3 = preparedStatement.execute();
            if (bl3) {
                ajv_12.aX((byte)4);
                ajv_12.jE("Update result is not an update count");
            } else {
                int n2 = preparedStatement.getUpdateCount();
                if (n2 != 1) {
                    ajv_12.aX((byte)4);
                    ajv_12.jE("Erreur lors de la sauvegarde : updateCount attendu=1, retourn\u00e9=" + n2);
                } else {
                    ajv_12.aX((byte)2);
                }
            }
            if (bl2) {
                preparedStatement.close();
            }
        }
        catch (SQLException sQLException) {
            ajv_12.aX((byte)4);
            ajv_12.jE("Exception : " + sQLException.toString());
        }
        return ajv_12;
    }

    public int kn() {
        return -1;
    }

    public int getId() {
        return this.hashCode();
    }

    public void b() {
        this.uK = false;
    }

    public void j() {
        this.uK = false;
    }
}

