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
/*    */ public class BrightnessElement
/*    */   extends BasicElement
/*    */ {
/*    */   public BrightnessElement(int id)
/*    */   {
/* 18 */     super(id);
/* 19 */     setType(10);
/*    */   }
/*    */   
/*    */   public static double getBrightnessModification(WorldElement element, double lightContrast) {
/* 23 */     return element.getParams()[1] * lightContrast;
/*    */   }
/*    */   
/*    */   public static double getBrightness(WorldElement element) {
/* 27 */     return element.getParams()[1];
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\BrightnessElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */