/*    */ package org.postgresql.util;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GT
/*    */ {
/* 26 */   private static GT _gt = new GT();
/*    */   
/*    */   public static final String tr(String message) {
/* 29 */     return _gt.translate(message, null);
/*    */   }
/*    */   private ResourceBundle _bundle;
/*    */   public static final String tr(String message, Object arg) {
/* 33 */     return _gt.translate(message, new Object[] { arg });
/*    */   }
/*    */   
/*    */   public static final String tr(String message, Object[] args) {
/* 37 */     return _gt.translate(message, args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private GT() {
/*    */     try {
/* 46 */       this._bundle = ResourceBundle.getBundle("org.postgresql.translation.messages");
/*    */     
/*    */     }
/*    */     catch (MissingResourceException mre) {
/*    */       
/* 51 */       this._bundle = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private final String translate(String message, Object[] args) {
/* 57 */     if (this._bundle != null && message != null) {
/*    */       
/*    */       try {
/*    */         
/* 61 */         message = this._bundle.getString(message);
/*    */       
/*    */       }
/* 64 */       catch (MissingResourceException mre) {}
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 72 */     if (args != null && message != null)
/*    */     {
/* 74 */       message = MessageFormat.format(message, args);
/*    */     }
/*    */     
/* 77 */     return message;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresq\\util\GT.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */