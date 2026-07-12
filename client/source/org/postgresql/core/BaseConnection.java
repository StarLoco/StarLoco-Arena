package org.postgresql.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.postgresql.PGConnection;
import org.postgresql.jdbc2.TimestampUtils;

public interface BaseConnection extends PGConnection, Connection {
  void cancelQuery() throws SQLException;
  
  ResultSet execSQLQuery(String paramString) throws SQLException;
  
  void execSQLUpdate(String paramString) throws SQLException;
  
  QueryExecutor getQueryExecutor();
  
  Object getObject(String paramString1, String paramString2) throws SQLException;
  
  String getJavaClass(int paramInt) throws SQLException;
  
  String getPGType(int paramInt) throws SQLException;
  
  int getPGType(String paramString) throws SQLException;
  
  int getSQLType(int paramInt) throws SQLException;
  
  int getSQLType(String paramString) throws SQLException;
  
  boolean haveMinimumCompatibleVersion(String paramString);
  
  boolean haveMinimumServerVersion(String paramString);
  
  byte[] encodeString(String paramString) throws SQLException;
  
  TimestampUtils getTimestampUtils();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\BaseConnection.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */