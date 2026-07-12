/*    */ package com.ankamagames.dofusarena.client.ui.actions;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.game.team.EditableTeamPreset;
/*    */ import com.ankamagames.dofusarena.client.core.game.team.TeamPresetManager;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFightMessage;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIReadyForFightRequestMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.property.PropertiesProvider;
/*    */ import com.ankamagames.xulor.property.Property;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FightCreationActions
/*    */ {
/*    */   public static final String PACKAGE = "dofusarena.fightCreation";
/*    */   
/*    */   public static void setReadyForFight(Event event)
/*    */   {
/* 34 */     long fightId = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("fight.id").getLong();
/*    */     
/* 36 */     EditableTeamPreset teamPreset = TeamPresetManager.getInstance().getEditableTeamPreset();
/*    */     
/*    */ 
/* 39 */     UIReadyForFightRequestMessage message = new UIReadyForFightRequestMessage();
/* 40 */     message.setFightId(fightId);
/* 41 */     message.setTeamPreset(teamPreset);
/* 42 */     Worker.getInstance().pushMessage(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void cancelFightCreation(Event event)
/*    */   {
/* 54 */     long fightId = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("fight.id").getLong();
/*    */     
/*    */ 
/* 57 */     UIFightMessage message = new UIFightMessage();
/* 58 */     message.setFightId(fightId);
/* 59 */     message.setId(16601);
/* 60 */     Worker.getInstance().pushMessage(message);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\actions\FightCreationActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */