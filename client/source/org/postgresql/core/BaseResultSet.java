package org.postgresql.core;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseResultSet extends ResultSet {
  String getFixedString(int paramInt) throws SQLException;
  
  Array createArray(int paramInt) throws SQLException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\BaseResultSet.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */