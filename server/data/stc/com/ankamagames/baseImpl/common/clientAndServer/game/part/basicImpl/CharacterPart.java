/*    */ package com.ankamagames.baseImpl.common.clientAndServer.game.part.basicImpl;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.part.Part;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharacterPart
/*    */   implements Part
/*    */ {
/*    */   public static final int FRONT = 0;
/*    */   public static final int RIGHT_SIDE = 1;
/*    */   public static final int BACK = 2;
/*    */   public static final int LEFT_SIDE = 3;
/*    */   private int m_id;
/*    */   
/*    */   CharacterPart(int id)
/*    */   {
/* 20 */     this.m_id = id;
/*    */   }
/*    */   
/*    */   public int getPartId() {
/* 24 */     return this.m_id;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\part\basicImpl\CharacterPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */