/*    */ package com.ankamagames.dofusarena.client.core.game.team;
/*    */ 
/*    */ import com.ankamagames.dofusarena.common.game.fight.NamedFightingTeam;
/*    */ import com.ankamagames.dofusarena.common.game.fighter.AbstractFighter;
/*    */ import com.ankamagames.xulor.property.FieldProvider;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ public class DofusArenaNamedFightingTeam<F extends AbstractFighter>
/*    */   extends NamedFightingTeam
/*    */   implements FieldProvider
/*    */ {
/*    */   public static final String COACH_FIELD = "coach";
/*    */   public static final String FIGHTERS_FIELD = "fighters";
/* 24 */   public static final String[] FIELDS = new String[] {
/* 25 */       "coach", 
/* 26 */       "fighters"
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void appendFieldValue(String fieldName, Object value) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getFieldValue(String fieldName) {
/* 45 */     if (fieldName.equals("coach")) {
/* 46 */       Iterator iterator = getTeamMates().iterator(); if (iterator.hasNext()) { Object teamMate = iterator.next();
/* 47 */         return teamMate; }
/*    */     
/*    */     } 
/* 50 */     if (fieldName.equals("fighters")) {
/* 51 */       Iterator<F> fighterIterator = getFighterIterator();
/* 52 */       ArrayList<F> fighters = new ArrayList<F>();
/* 53 */       while (fighterIterator.hasNext()) {
/* 54 */         fighters.add(fighterIterator.next());
/*    */       }
/* 56 */       return fighters.toArray();
/*    */     } 
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getFields() {
/* 67 */     return FIELDS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFieldSynchronisable(String fieldName) {
/* 76 */     return false;
/*    */   }
/*    */   
/*    */   public void prependFieldValue(String fieldName, Object value) {}
/*    */   
/*    */   public void setFieldValue(String fieldName, Object value) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\team\DofusArenaNamedFightingTeam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */