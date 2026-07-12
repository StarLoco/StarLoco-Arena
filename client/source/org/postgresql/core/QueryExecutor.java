package org.postgresql.core;

import java.sql.SQLException;

public interface QueryExecutor {
  public static final int QUERY_ONESHOT = 1;
  
  public static final int QUERY_NO_METADATA = 2;
  
  public static final int QUERY_NO_RESULTS = 4;
  
  public static final int QUERY_FORWARD_CURSOR = 8;
  
  public static final int QUERY_SUPPRESS_BEGIN = 16;
  
  public static final int QUERY_DESCRIBE_ONLY = 32;
  
  void execute(Query paramQuery, ParameterList paramParameterList, ResultHandler paramResultHandler, int paramInt1, int paramInt2, int paramInt3) throws SQLException;
  
  void execute(Query[] paramArrayOfQuery, ParameterList[] paramArrayOfParameterList, ResultHandler paramResultHandler, int paramInt1, int paramInt2, int paramInt3) throws SQLException;
  
  void fetch(ResultCursor paramResultCursor, ResultHandler paramResultHandler, int paramInt) throws SQLException;
  
  Query createSimpleQuery(String paramString);
  
  Query createParameterizedQuery(String paramString);
  
  ParameterList createFastpathParameters(int paramInt);
  
  byte[] fastpathCall(int paramInt, ParameterList paramParameterList, boolean paramBoolean) throws SQLException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\QueryExecutor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */