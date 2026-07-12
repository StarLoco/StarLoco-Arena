/*     */ package org.postgresql.largeobject;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.core.BaseConnection;
/*     */ import org.postgresql.fastpath.Fastpath;
/*     */ import org.postgresql.fastpath.FastpathArg;
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
/*     */ public class LargeObjectManager
/*     */ {
/*     */   private Fastpath fp;
/*     */   private BaseConnection conn;
/*     */   public static final int WRITE = 131072;
/*     */   public static final int READ = 262144;
/*     */   public static final int READWRITE = 393216;
/*     */   
/*     */   private LargeObjectManager() {}
/*     */   
/*     */   public LargeObjectManager(BaseConnection conn) throws SQLException {
/* 104 */     this.conn = conn;
/*     */     
/* 106 */     this.fp = conn.getFastpathAPI();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (conn.getMetaData().supportsSchemasInTableDefinitions()) {
/*     */       
/* 115 */       str = "SELECT p.proname,p.oid  FROM pg_catalog.pg_proc p, pg_catalog.pg_namespace n  WHERE p.pronamespace=n.oid AND n.nspname='pg_catalog' AND (";
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 121 */       str = "SELECT proname,oid FROM pg_proc WHERE ";
/*     */     } 
/* 123 */     String str = str + " proname = 'lo_open' or proname = 'lo_close' or proname = 'lo_creat' or proname = 'lo_unlink' or proname = 'lo_lseek' or proname = 'lo_tell' or proname = 'loread' or proname = 'lowrite'";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     if (conn.getMetaData().supportsSchemasInTableDefinitions())
/*     */     {
/* 134 */       str = str + ")";
/*     */     }
/*     */     
/* 137 */     ResultSet res = conn.createStatement().executeQuery(str);
/*     */     
/* 139 */     if (res == null) {
/* 140 */       throw new PSQLException(GT.tr("Failed to initialize LargeObject API"), PSQLState.SYSTEM_ERROR);
/*     */     }
/* 142 */     this.fp.addFunctions(res);
/* 143 */     res.close();
/* 144 */     if (Driver.logDebug) {
/* 145 */       Driver.debug("Large Object initialised");
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
/*     */   public LargeObject open(int oid) throws SQLException {
/* 158 */     return open(oid, 393216);
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
/*     */   public LargeObject open(int oid, int mode) throws SQLException {
/* 171 */     if (this.conn.getAutoCommit()) {
/* 172 */       throw new PSQLException(GT.tr("Large Objects may not be used in auto-commit mode."), PSQLState.NO_ACTIVE_SQL_TRANSACTION);
/*     */     }
/* 174 */     return new LargeObject(this.fp, oid, mode);
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
/*     */   public int create() throws SQLException {
/* 187 */     return create(393216);
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
/*     */   public int create(int mode) throws SQLException {
/* 199 */     if (this.conn.getAutoCommit()) {
/* 200 */       throw new PSQLException(GT.tr("Large Objects may not be used in auto-commit mode."), PSQLState.NO_ACTIVE_SQL_TRANSACTION);
/*     */     }
/* 202 */     FastpathArg[] args = new FastpathArg[1];
/* 203 */     args[0] = new FastpathArg(mode);
/* 204 */     return this.fp.getInteger("lo_creat", args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(int oid) throws SQLException {
/* 215 */     FastpathArg[] args = new FastpathArg[1];
/* 216 */     args[0] = new FastpathArg(oid);
/* 217 */     this.fp.fastpath("lo_unlink", false, args);
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
/*     */   public void unlink(int oid) throws SQLException {
/* 231 */     delete(oid);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\largeobject\LargeObjectManager.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */