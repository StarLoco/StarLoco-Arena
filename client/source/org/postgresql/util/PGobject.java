/*     */ package org.postgresql.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
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
/*     */ public class PGobject
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   protected String type;
/*     */   protected String value;
/*     */   
/*     */   public final void setType(String type) {
/*  41 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(String value) throws SQLException {
/*  52 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getType() {
/*  61 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  71 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  81 */     if (obj instanceof PGobject)
/*  82 */       return ((PGobject)obj).getValue().equals(getValue()); 
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*  91 */     PGobject obj = new PGobject();
/*  92 */     obj.type = this.type;
/*  93 */     obj.value = this.value;
/*  94 */     return obj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return getValue();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresq\\util\PGobject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */