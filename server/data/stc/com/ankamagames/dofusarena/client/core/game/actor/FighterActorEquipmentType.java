/*    */ package com.ankamagames.dofusarena.client.core.game.actor;
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
/*    */ public enum FighterActorEquipmentType
/*    */ {
/* 14 */   FIGHTER_WEAPON(0, new String[] { "Arme" }), 
/* 15 */   FIGHTER_CLOAK(2, new String[] { "Cape" }), 
/* 16 */   FIGHTER_HAT(3, new String[] { "Chapeau" });
/*    */   
/*    */ 
/*    */ 
/*    */   private int m_index;
/*    */   
/*    */   private String[] m_linkageNames;
/*    */   
/*    */ 
/*    */   private FighterActorEquipmentType(int index, String[] linkageNames)
/*    */   {
/* 27 */     this.m_index = index;
/* 28 */     this.m_linkageNames = linkageNames;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int getIndex()
/*    */   {
/* 35 */     return this.m_index;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String[] getLinkageNames()
/*    */   {
/* 42 */     return this.m_linkageNames;
/*    */   }
/*    */   
/*    */ 
/*    */   public static FighterActorEquipmentType getActorEquipmentTypeFromIndex(int index)
/*    */   {
/*    */     FighterActorEquipmentType[] arrayOfFighterActorEquipmentType;
/*    */     
/* 50 */     int j = (arrayOfFighterActorEquipmentType = values()).length; for (int i = 0; i < j; i++) { FighterActorEquipmentType actorEquipmentType = arrayOfFighterActorEquipmentType[i];
/* 51 */       if (actorEquipmentType.getIndex() == index) {
/* 52 */         return actorEquipmentType;
/*    */       }
/*    */     }
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\actor\FighterActorEquipmentType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */