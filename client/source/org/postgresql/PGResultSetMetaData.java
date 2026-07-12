package org.postgresql;

import java.sql.SQLException;

public interface PGResultSetMetaData {
  String getBaseColumnName(int paramInt) throws SQLException;
  
  String getBaseTableName(int paramInt) throws SQLException;
  
  String getBaseSchemaName(int paramInt) throws SQLException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\PGResultSetMetaData.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */