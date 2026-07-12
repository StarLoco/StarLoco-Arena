/*    */ package com.ankamagames.dofusarena.common.game.spell;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.ContentAlreadyPresentException;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.InventoryCapacityReachedException;
/*    */ import java.util.Iterator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoachSpellInventory<S extends AbstractSpell>
/*    */   implements Iterable<S>
/*    */ {
/* 17 */   protected static final Logger m_logger = Logger.getLogger(CoachSpellInventory.class);
/*    */   protected StackInventory<S> m_spellInventory;
/*    */   
/*    */   public CoachSpellInventory(AbstractSpellManager<S> spellManager, short size) {
/* 21 */     this.m_spellInventory = new StackInventory(size, spellManager, null, true, false, false);
/*    */   }
/*    */   
/*    */   public void unserialize(byte[] serializedCoachSpellInventory) {
/* 25 */     this.m_spellInventory.unserialize(serializedCoachSpellInventory);
/*    */   }
/*    */   
/*    */   public byte[] serialize() {
/* 29 */     return this.m_spellInventory.serialize();
/*    */   }
/*    */   
/*    */   public boolean addCoachSpell(S spell) {
/*    */     try {
/* 34 */       this.m_spellInventory.add(spell);
/* 35 */       return true;
/*    */     } catch (InventoryCapacityReachedException e) {
/* 37 */       m_logger.error("impossible d'ajouter ce sort : inventaire plein");
/*    */     } catch (ContentAlreadyPresentException e) {
/* 39 */       m_logger.error("impossible d'ajouter ce sort : on l'a déjà");
/*    */     }
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public void removeCoachSpell(long spellId) {
/* 45 */     this.m_spellInventory.removeWithUniqueId(spellId);
/*    */   }
/*    */   
/*    */   public StackInventory<S> getSpellInventory() {
/* 49 */     return this.m_spellInventory;
/*    */   }
/*    */   
/*    */   public Iterator<S> iterator() {
/* 53 */     return this.m_spellInventory.iterator();
/*    */   }
/*    */   
/*    */   public int size()
/*    */   {
/* 58 */     return this.m_spellInventory.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\spell\CoachSpellInventory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */