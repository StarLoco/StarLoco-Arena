/*    */ package com.ankamagames.baseImpl.graphics.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleElement
/*    */   extends BasicElement
/*    */ {
/*    */   public ParticleElement(int id)
/*    */   {
/* 13 */     super(id);
/* 14 */     setType(9);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static int getParticleFileId(WorldElement element)
/*    */   {
/* 23 */     return element.getParams()[1];
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\ParticleElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */