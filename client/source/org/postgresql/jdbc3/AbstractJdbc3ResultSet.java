/*     */ package org.postgresql.jdbc3;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.sql.Array;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Clob;
/*     */ import java.sql.Ref;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Vector;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.core.BaseStatement;
/*     */ import org.postgresql.core.Field;
/*     */ import org.postgresql.core.Query;
/*     */ import org.postgresql.core.ResultCursor;
/*     */ import org.postgresql.jdbc2.AbstractJdbc2ResultSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractJdbc3ResultSet
/*     */   extends AbstractJdbc2ResultSet
/*     */ {
/*     */   public AbstractJdbc3ResultSet(Query originalQuery, BaseStatement statement, Field[] fields, Vector tuples, ResultCursor cursor, int maxRows, int maxFieldSize, int rsType, int rsConcurrency, int rsHoldability) throws SQLException {
/*  29 */     super(originalQuery, statement, fields, tuples, cursor, maxRows, maxFieldSize, rsType, rsConcurrency);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object internalGetObject(int columnIndex, Field field) throws SQLException {
/*  35 */     switch (getSQLType(columnIndex)) {
/*     */       case 16:
/*  37 */         return new Boolean(getBoolean(columnIndex));
/*     */     } 
/*  39 */     return super.internalGetObject(columnIndex, field);
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
/*     */   public URL getURL(int columnIndex) throws SQLException {
/*  58 */     throw Driver.notImplemented(getClass(), "getURL(int)");
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
/*     */   public URL getURL(String columnName) throws SQLException {
/*  76 */     throw Driver.notImplemented(getClass(), "getURL(String)");
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
/*     */   public void updateRef(int columnIndex, Ref x) throws SQLException {
/*  93 */     throw Driver.notImplemented(getClass(), "updateRef(int,Ref)");
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
/*     */   public void updateRef(String columnName, Ref x) throws SQLException {
/* 110 */     throw Driver.notImplemented(getClass(), "updateRef(String,Ref)");
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
/*     */   public void updateBlob(int columnIndex, Blob x) throws SQLException {
/* 127 */     throw Driver.notImplemented(getClass(), "updateBlob(int,Blob)");
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
/*     */   public void updateBlob(String columnName, Blob x) throws SQLException {
/* 144 */     throw Driver.notImplemented(getClass(), "updateBlob(String,Blob)");
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
/*     */   public void updateClob(int columnIndex, Clob x) throws SQLException {
/* 161 */     throw Driver.notImplemented(getClass(), "updateClob(int,Clob)");
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
/*     */   public void updateClob(String columnName, Clob x) throws SQLException {
/* 178 */     throw Driver.notImplemented(getClass(), "updateClob(String,Clob)");
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
/*     */   public void updateArray(int columnIndex, Array x) throws SQLException {
/* 195 */     throw Driver.notImplemented(getClass(), "updateArray(int,Array)");
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
/*     */   public void updateArray(String columnName, Array x) throws SQLException {
/* 212 */     throw Driver.notImplemented(getClass(), "updateArray(String,Array)");
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\jdbc3\AbstractJdbc3ResultSet.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */