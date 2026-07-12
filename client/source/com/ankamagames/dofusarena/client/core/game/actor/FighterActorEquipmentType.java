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
/*    */   private int m_index;
/*    */ 
/*    */   
/*    */   private String[] m_linkageNames;
/*    */ 
/*    */ 
/*    */   
/*    */   FighterActorEquipmentType(int index, String[] linkageNames) {
/* 27 */     this.m_index = index;
/* 28 */     this.m_linkageNames = linkageNames;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 35 */     return this.m_index;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getLinkageNames() {
/* 42 */     return this.m_linkageNames;
/*    */   }
/*    */ 
/*    */   
/*    */   public static FighterActorEquipmentType getActorEquipmentTypeFromIndex(int index) {
/*    */     byte b;
/*    */     int i;
/*    */     FighterActorEquipmentType[] arrayOfFighterActorEquipmentType;
/* 50 */     for (i = (arrayOfFighterActorEquipmentType = values()).length, b = 0; b < i; ) { FighterActorEquipmentType actorEquipmentType = arrayOfFighterActorEquipmentType[b];
/* 51 */       if (actorEquipmentType.getIndex() == index)
/* 52 */         return actorEquipmentType; 
/*    */       b++; }
/*    */     
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\actor\FighterActorEquipmentType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */