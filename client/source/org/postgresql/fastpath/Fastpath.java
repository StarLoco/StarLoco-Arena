/*     */ package org.postgresql.fastpath;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Hashtable;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.core.BaseConnection;
/*     */ import org.postgresql.core.ParameterList;
/*     */ import org.postgresql.core.QueryExecutor;
/*     */ import org.postgresql.util.GT;
/*     */ import org.postgresql.util.PSQLException;
/*     */ import org.postgresql.util.PSQLState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Fastpath
/*     */ {
/*  36 */   private final Hashtable func = new Hashtable();
/*     */ 
/*     */   
/*     */   private final QueryExecutor executor;
/*     */ 
/*     */   
/*     */   private final BaseConnection connection;
/*     */ 
/*     */ 
/*     */   
/*     */   public Fastpath(BaseConnection conn) {
/*  47 */     this.connection = conn;
/*  48 */     this.executor = conn.getQueryExecutor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object fastpath(int fnId, boolean resultType, FastpathArg[] args) throws SQLException {
/*  63 */     ParameterList params = this.executor.createFastpathParameters(args.length);
/*  64 */     for (int i = 0; i < args.length; i++)
/*     */     {
/*  66 */       args[i].populateParameter(params, i + 1);
/*     */     }
/*     */ 
/*     */     
/*  70 */     byte[] returnValue = this.executor.fastpathCall(fnId, params, this.connection.getAutoCommit());
/*     */ 
/*     */     
/*  73 */     if (!resultType || returnValue == null) {
/*  74 */       return returnValue;
/*     */     }
/*  76 */     if (returnValue.length != 4) {
/*  77 */       throw new PSQLException(GT.tr("Fastpath call {0} - No result was returned and we expected an integer.", new Integer(fnId)), PSQLState.NO_DATA);
/*     */     }
/*     */     
/*  80 */     return new Integer(returnValue[3] & 0xFF | (returnValue[2] & 0xFF) << 8 | (returnValue[1] & 0xFF) << 16 | (returnValue[0] & 0xFF) << 24);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object fastpath(String name, boolean resulttype, FastpathArg[] args) throws SQLException {
/* 108 */     if (Driver.logDebug)
/* 109 */       Driver.debug("Fastpath: calling " + name); 
/* 110 */     return fastpath(getID(name), resulttype, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInteger(String name, FastpathArg[] args) throws SQLException {
/* 122 */     Integer i = (Integer)fastpath(name, true, args);
/* 123 */     if (i == null) {
/* 124 */       throw new PSQLException(GT.tr("Fastpath call {0} - No result was returned and we expected an integer.", name), PSQLState.NO_DATA);
/*     */     }
/* 126 */     return i.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getData(String name, FastpathArg[] args) throws SQLException {
/* 138 */     return (byte[])fastpath(name, false, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFunction(String name, int fnid) {
/* 154 */     this.func.put(name, new Integer(fnid));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFunctions(ResultSet rs) throws SQLException {
/* 191 */     while (rs.next())
/*     */     {
/* 193 */       this.func.put(rs.getString(1), new Integer(rs.getInt(2)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getID(String name) throws SQLException {
/* 209 */     Integer id = (Integer)this.func.get(name);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     if (id == null) {
/* 219 */       throw new PSQLException(GT.tr("The fastpath function {0} is unknown.", name), PSQLState.UNEXPECTED_ERROR);
/*     */     }
/* 221 */     return id.intValue();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\fastpath\Fastpath.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */