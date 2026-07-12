/*     */ package com.ankamagames.dofusarena.client.core.game.fighter;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FighterManager
/*     */ {
/*  21 */   private static FighterManager m_instance = new FighterManager();
/*     */   
/*     */   private HashMap<Long, Fighter> m_fighters;
/*     */   
/*  25 */   private EditableFighter m_editableFighter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FighterManager() {
/*  31 */     this.m_fighters = new HashMap<Long, Fighter>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FighterManager getInstance() {
/*  38 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  47 */     for (Fighter fighter : this.m_fighters.values()) {
/*  48 */       fighter.release();
/*     */     }
/*  50 */     this.m_fighters.clear();
/*     */ 
/*     */     
/*  53 */     if (this.m_editableFighter != null) {
/*  54 */       this.m_editableFighter.release();
/*  55 */       this.m_editableFighter = null;
/*     */     } 
/*     */ 
/*     */     
/*  59 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("teamManagement.editableFighter");
/*  60 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("teamManagement.fighterList");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  67 */     return this.m_fighters.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFighter(Fighter fighter) {
/*  76 */     addFighter(fighter, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFighter(Fighter fighter, boolean updateProperty) {
/*  85 */     this.m_fighters.put(Long.valueOf(fighter.getId()), fighter);
/*  86 */     if (updateProperty) {
/*  87 */       updateFighterListProperty();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFighter(long id) {
/*  97 */     this.m_fighters.remove(Long.valueOf(id));
/*  98 */     updateFighterListProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFighter(Fighter fighter) {
/* 107 */     removeFighter(fighter.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Fighter getFighter(long id) {
/* 117 */     return this.m_fighters.get(Long.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditableFighter getEditableFighter() {
/* 124 */     return this.m_editableFighter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEditableFighter(EditableFighter editableFighter) {
/* 131 */     this.m_editableFighter = editableFighter;
/* 132 */     updateEditableFighterProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditableFighter createEmptyEditableFighter() {
/* 139 */     return new EditableFighter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateEditableFighterProperty() {
/* 146 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.editableFighter", this.m_editableFighter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFighterListProperty() {
/* 153 */     List<Fighter> fighters = new ArrayList<Fighter>(this.m_fighters.values());
/* 154 */     Collections.sort(fighters, new Comparator<Fighter>() {
/*     */           public int compare(Fighter fighter1, Fighter fighter2) {
/* 156 */             return Long.valueOf(fighter1.getId()).compareTo(Long.valueOf(fighter2.getId()));
/*     */           }
/*     */         });
/*     */     
/* 160 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("teamManagement.fighterList", fighters.toArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseEditableFighter() {
/* 167 */     if (this.m_editableFighter != null) {
/* 168 */       this.m_editableFighter.release();
/* 169 */       this.m_editableFighter = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\fighter\FighterManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */