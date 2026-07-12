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
/* 24 */   public static final String[] FIELDS = {
/* 25 */     "coach", 
/* 26 */     "fighters" };
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
/*    */   public Object getFieldValue(String fieldName)
/*    */   {
/* 45 */     if (fieldName.equals("coach")) {
/* 46 */       Iterator localIterator = getTeamMates().iterator(); if (localIterator.hasNext()) { Object teamMate = localIterator.next();
/* 47 */         return teamMate;
/*    */       }
/*    */     }
/* 50 */     if (fieldName.equals("fighters")) {
/* 51 */       Iterator<F> fighterIterator = getFighterIterator();
/* 52 */       Object fighters = new ArrayList();
/* 53 */       while (fighterIterator.hasNext()) {
/* 54 */         ((ArrayList)fighters).add((AbstractFighter)fighterIterator.next());
/*    */       }
/* 56 */       return ((ArrayList)fighters).toArray();
/*    */     }
/* 58 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String[] getFields()
/*    */   {
/* 67 */     return FIELDS;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isFieldSynchronisable(String fieldName)
/*    */   {
/* 76 */     return false;
/*    */   }
/*    */   
/*    */   public void prependFieldValue(String fieldName, Object value) {}
/*    */   
/*    */   public void setFieldValue(String fieldName, Object value) {}
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\team\DofusArenaNamedFightingTeam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */