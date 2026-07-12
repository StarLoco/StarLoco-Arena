package org.postgresql;

import java.sql.SQLException;
import org.postgresql.core.Encoding;
import org.postgresql.fastpath.Fastpath;
import org.postgresql.largeobject.LargeObjectManager;

public interface PGConnection {
  PGNotification[] getNotifications() throws SQLException;
  
  LargeObjectManager getLargeObjectAPI() throws SQLException;
  
  Fastpath getFastpathAPI() throws SQLException;
  
  void addDataType(String paramString1, String paramString2);
  
  void addDataType(String paramString, Class paramClass) throws SQLException;
  
  void setPrepareThreshold(int paramInt);
  
  int getPrepareThreshold();
  
  Encoding getEncoding() throws SQLException;
  
  int getSQLType(String paramString) throws SQLException;
  
  int getSQLType(int paramInt) throws SQLException;
  
  String getPGType(int paramInt) throws SQLException;
  
  int getPGType(String paramString) throws SQLException;
  
  Object getObject(String paramString1, String paramString2) throws SQLException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\PGConnection.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */