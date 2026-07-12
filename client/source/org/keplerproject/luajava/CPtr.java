/*    */ package org.keplerproject.luajava;
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
/*    */ public class CPtr
/*    */ {
/*    */   private long peer;
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 46 */     if (paramObject == null)
/* 47 */       return false; 
/* 48 */     if (paramObject == this)
/* 49 */       return true; 
/* 50 */     if (CPtr.class != paramObject.getClass())
/* 51 */       return false; 
/* 52 */     return (this.peer == ((CPtr)paramObject).peer);
/*    */   }
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
/*    */   protected long getPeer() {
/* 65 */     return this.peer;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\keplerproject\luajava\CPtr.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */