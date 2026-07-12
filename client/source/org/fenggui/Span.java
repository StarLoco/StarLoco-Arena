/*    */ package org.fenggui;
/*    */ 
/*    */ import org.fenggui.io.EncodingException;
/*    */ import org.fenggui.io.StorageFormat;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Span
/*    */ {
/* 28 */   MARGIN("margin"), PADDING("padding"), BORDER("border");
/*    */   
/*    */   private String code;
/*    */   public static final StorageFormat STORAGE_FORMAT;
/*    */   
/*    */   Span(String code) {
/* 34 */     this.code = code;
/*    */   }
/*    */ 
/*    */   
/*    */   public String code() {
/* 39 */     return this.code;
/*    */   }
/*    */   static {
/* 42 */     STORAGE_FORMAT = new StorageFormat<Span, String>()
/*    */       {
/*    */         public String encode(Span obj) throws EncodingException
/*    */         {
/* 46 */           return obj.code();
/*    */         }
/*    */ 
/*    */         
/*    */         public Span decode(String encodedObj) throws EncodingException {
/* 51 */           if (encodedObj.equals("margin"))
/* 52 */             return Span.MARGIN; 
/* 53 */           if (encodedObj.equals("padding"))
/* 54 */             return Span.PADDING; 
/* 55 */           return Span.BORDER;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Span.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */