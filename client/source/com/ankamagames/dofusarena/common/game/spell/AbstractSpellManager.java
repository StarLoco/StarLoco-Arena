/*    */ package com.ankamagames.dofusarena.common.game.spell;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContent;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.InventoryContentProvider;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.Breed;
/*    */ import gnu.trove.TLongObjectHashMap;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public abstract class AbstractSpellManager<S extends AbstractSpell>
/*    */   implements InventoryContentProvider<S>
/*    */ {
/* 23 */   protected static final Logger m_logger = Logger.getLogger(AbstractSpellManager.class);
/* 24 */   private final TLongObjectHashMap<S> m_spells = new TLongObjectHashMap();
/* 25 */   private final ArrayList<S> m_coachSpells = new ArrayList<S>();
/* 26 */   private final ArrayList<S> m_godSpells = new ArrayList<S>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSpell(S spell) {
/* 32 */     this.m_spells.put(spell.getId(), spell);
/*    */   }
/*    */   
/*    */   public void addCoachSpell(S spell) {
/* 36 */     if (spell.getBreedId() == Breed.COACH.getId()) {
/* 37 */       this.m_coachSpells.add(spell);
/*    */     }
/*    */   }
/*    */   
/*    */   public void addGodSpell(S spell) {
/* 42 */     if (spell.getBreedId() == Breed.GOD.getId()) {
/* 43 */       this.m_godSpells.add(spell);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<S> getCoachSpells() {
/* 49 */     return this.m_coachSpells;
/*    */   }
/*    */   
/*    */   public ArrayList<S> getGodSpells() {
/* 53 */     return this.m_godSpells;
/*    */   }
/*    */   
/*    */   public TLongObjectHashMap<S> getSpells() {
/* 57 */     return this.m_spells;
/*    */   }
/*    */   
/*    */   public S getSpell(long spellId) {
/* 61 */     return (S)this.m_spells.get(spellId);
/*    */   }
/*    */   
/*    */   public S unserializeContent(ByteBuffer buf) {
/* 65 */     return (S)this.m_spells.get(buf.getInt());
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\spell\AbstractSpellManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */