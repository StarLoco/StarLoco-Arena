/*    */ package com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
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
/*    */ public class UIFighterSpellMessage
/*    */   extends UIFighterMessage
/*    */ {
/*    */   private Spell m_spell;
/*    */   
/*    */   public Spell getSpell() {
/* 22 */     return this.m_spell;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpell(Spell spell) {
/* 29 */     this.m_spell = spell;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\protocol\message\teamManagement\UIFighterSpellMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */