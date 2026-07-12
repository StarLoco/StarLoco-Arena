/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.BetCoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.Coach;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UICoachEquipmentMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ public class UIFightResultFrame
/*     */   implements MessageFrame
/*     */ {
/*  33 */   private static UIFightResultFrame m_instance = new UIFightResultFrame();
/*     */   
/*  35 */   private final ArrayList<Coach> m_winnerCoachs = new ArrayList();
/*  36 */   private final ArrayList<Coach> m_looserCoachs = new ArrayList();
/*     */   private ArrayList<BetCoachCard> m_lostCards;
/*     */   private ArrayList<BetCoachCard> m_wonCards;
/*     */   private ArrayList<BetCoachCard> m_bonusCards;
/*  40 */   private int m_fightDuration = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIFightResultFrame getInstance()
/*     */   {
/*  46 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addLooserCoach(Coach looserCoach)
/*     */   {
/*  53 */     this.m_looserCoachs.add(looserCoach);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addWinnerCoach(Coach winnerCoach)
/*     */   {
/*  60 */     this.m_winnerCoachs.add(winnerCoach);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLostCards(ArrayList<BetCoachCard> lostCards)
/*     */   {
/*  67 */     this.m_lostCards = lostCards;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setWonCards(ArrayList<BetCoachCard> wonCards)
/*     */   {
/*  74 */     this.m_wonCards = wonCards;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBonusCards(ArrayList<BetCoachCard> bonusCards)
/*     */   {
/*  81 */     this.m_bonusCards = bonusCards;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFightDuration(int fightDuration)
/*     */   {
/*  88 */     this.m_fightDuration = fightDuration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  97 */     switch (message.getId())
/*     */     {
/*     */ 
/*     */ 
/*     */     case 20005: 
/* 102 */       DofusArenaGameEntity.getInstance().removeFrame(this);
/*     */       
/* 104 */       return false;
/*     */     
/*     */ 
/*     */     case 16700: 
/* 108 */       UICoachEquipmentMessage msg = (UICoachEquipmentMessage)message;
/*     */       
/* 110 */       CoachCard equipment = msg.getEquipment();
/* 111 */       if (equipment != null)
/*     */       {
/* 113 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", equipment);
/*     */       }
/* 115 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16701: 
/* 121 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", null);
/*     */       
/* 123 */       return false;
/*     */     }
/*     */     
/*     */     
/* 127 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 136 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setId(long id) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameAdd(FrameHandler frameHandler, boolean isAboutToBeAdded)
/*     */   {
/* 154 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 157 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.winnerCoachs", this.m_winnerCoachs.toArray());
/* 158 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.loserCoachs", this.m_looserCoachs.toArray());
/* 159 */       if (this.m_lostCards != null) {
/* 160 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.lostCards", this.m_lostCards.toArray());
/*     */       }
/* 162 */       if (this.m_wonCards != null) {
/* 163 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.wonCards", this.m_wonCards.toArray());
/*     */       }
/* 165 */       if (this.m_bonusCards != null) {
/* 166 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.bonusCards", this.m_bonusCards.toArray());
/*     */       }
/* 168 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.duration", DofusArenaTranslator.getInstance().getString("fight.durationValue", new Object[] { Integer.valueOf(this.m_fightDuration) }));
/*     */       
/* 170 */       boolean localWinner = this.m_winnerCoachs.contains(DofusArenaGameEntity.getInstance().getLocalCoach().getFightingCoach());
/* 171 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.localWinner", Boolean.valueOf(localWinner));
/*     */       
/*     */ 
/* 174 */       Xulor.getInstance().load("fightResultDialog", Dialogs.getDialogPath("fightResultDialog"), 65L, (short)10001);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onFrameRemove(FrameHandler frameHandler, boolean isAboutToBeRemoved)
/*     */   {
/* 186 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 189 */       Xulor.getInstance().removeActionClass("dofusarena.fight");
/*     */       
/*     */ 
/* 192 */       Xulor.getInstance().unload("fightResultDialog");
/*     */       
/*     */ 
/* 195 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.winnerCoachs");
/* 196 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.loserCoachs");
/* 197 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.lostCards");
/* 198 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.wonCards");
/* 199 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.duration");
/* 200 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("fight.localWinner");
/*     */       
/* 202 */       this.m_looserCoachs.clear();
/* 203 */       this.m_winnerCoachs.clear();
/* 204 */       if (this.m_lostCards != null) {
/* 205 */         this.m_lostCards.clear();
/*     */       }
/* 207 */       if (this.m_wonCards != null) {
/* 208 */         this.m_wonCards.clear();
/*     */       }
/* 210 */       if (this.m_bonusCards != null) {
/* 211 */         this.m_bonusCards.clear();
/*     */       }
/* 213 */       this.m_fightDuration = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIFightResultFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */