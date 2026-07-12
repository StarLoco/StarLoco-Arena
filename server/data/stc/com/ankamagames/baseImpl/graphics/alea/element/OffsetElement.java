/*    */ package com.ankamagames.baseImpl.graphics.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OffsetElement
/*    */   extends BasicElement
/*    */ {
/*    */   public OffsetElement(int id)
/*    */   {
/* 18 */     super(id);
/* 19 */     setType(4);
/*    */   }
/*    */   
/*    */   public static byte getOffset(WorldElement element) {
/* 23 */     return element.getParams()[1];
/*    */   }
/*    */   
/*    */   public static boolean isAbsolute(WorldElement element) {
/* 27 */     if (element.getParamsCount() != 2) {
/* 28 */       return false;
/*    */     }
/* 30 */     return element.getParams()[3] == 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\OffsetElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */