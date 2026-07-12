/*    */ package com.ankamagames.dofusarena.client.core.game.spell;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpell;
/*    */ import com.ankamagames.dofusarena.common.game.spell.AbstractSpellManager;
/*    */ import gnu.trove.TLongObjectIterator;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpellManager
/*    */   extends AbstractSpellManager<Spell>
/*    */ {
/* 21 */   private static final SpellManager m_instance = new SpellManager();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SpellManager getInstance() {
/* 27 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayList<Spell> getSpellsFromBreedId(int breedId) {
/* 38 */     ArrayList<Spell> spells = new ArrayList<Spell>();
/* 39 */     for (TLongObjectIterator<Spell> it = getSpells().iterator(); it.hasNext(); ) {
/* 40 */       it.advance();
/* 41 */       if (((Spell)it.value()).getBreedId() == breedId) {
/* 42 */         spells.add((Spell)it.value());
/*    */       }
/*    */     } 
/* 45 */     Collections.sort(spells);
/* 46 */     return spells;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Spell unserializeContent(ByteBuffer buf) {
/* 56 */     Spell refSpell = (Spell)super.unserializeContent(buf);
/* 57 */     Spell spell = null;
/* 58 */     if (refSpell != null) {
/* 59 */       spell = new UsableSpell(refSpell);
/*    */     }
/* 61 */     return spell;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\spell\SpellManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */