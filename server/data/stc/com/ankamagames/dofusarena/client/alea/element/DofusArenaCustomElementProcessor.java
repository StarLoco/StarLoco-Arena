/*    */ package com.ankamagames.dofusarena.client.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.CustomElementProcessor;
/*    */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.GraphicalWorldElement;
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DofusArenaCustomElementProcessor
/*    */   implements CustomElementProcessor
/*    */ {
/* 19 */   private static DofusArenaCustomElementProcessor m_instance = new DofusArenaCustomElementProcessor();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static DofusArenaCustomElementProcessor getInstance()
/*    */   {
/* 31 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onReadCell(WorldCell cell, WorldElement worldElement, ArrayList<WorldElement>[] cellData)
/*    */   {
/* 42 */     switch (worldElement.getElement().getType())
/*    */     {
/*    */     case 1000: 
/*    */       break;
/*    */     
/*    */ 
/*    */     case 1001: 
/*    */       break;
/*    */     
/*    */ 
/*    */     case 1002: 
/* 53 */       cell.addVisualElement((GraphicalWorldElement)worldElement);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\alea\element\DofusArenaCustomElementProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */