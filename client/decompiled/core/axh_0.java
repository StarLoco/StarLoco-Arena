/*
 * Decompiled with CFR 0.152.
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Renamed from axh
 */
class axh_0
extends arr {
    private static final acl_0 uG = new ym_0(new aNj());
    private String uJ;

    axh_0() {
    }

    static axh_0 aJP() {
        axh_0 axh_02;
        try {
            axh_02 = (axh_0)uG.adr();
            axh_02.a(uG);
        }
        catch (Exception exception) {
            axh_02 = new axh_0();
            a.error((Object)("Erreur lors d'un checkOut sur un message de type StatisticsReportLoadAllForLadderRequest : " + exception.getMessage()));
        }
        return axh_02;
    }

    public void b() {
        this.uJ = null;
    }

    public void j() {
        this.uJ = null;
    }

    public pr_0 a(jn_0 jn_02) {
        this.uJ = "SELECT * FROM tbl_dynamic_statistics, tbl_dynamic_stats_ladder where statistics_report_id = ladder_coach_id;";
        ajv_1 ajv_12 = ajv_1.azz();
        try {
            int n2 = this.uJ.hashCode();
            PreparedStatement preparedStatement = jn_02.gv(n2);
            if (preparedStatement == null) {
                preparedStatement = jn_02.getConnection().prepareStatement(this.uJ);
                jn_02.a(n2, preparedStatement);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            arq_0 arq_02 = arq_0.aEv();
            int n3 = 1;
            while (resultSet.next()) {
                byte[] byArray = resultSet.getBytes("statistics_report");
                if (byArray == null) {
                    a.error((Object)("Ladders datas empty found in resultSet number " + n3));
                } else {
                    rs_2 rs_22 = arq_02.aa(byArray);
                    arq_02.a(rs_22.wC(), rs_22.wD(), rs_22);
                }
                ++n3;
            }
            ajv_12.aX((byte)1);
        }
        catch (SQLException sQLException) {
            ajv_12.R(3L);
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
}

