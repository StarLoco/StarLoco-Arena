/*     */ package com.ankamagames.dofusarena.client.ui.protocol.frame;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.event.Event;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.fight.GiveUpFightRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.Dialogs;
/*     */ import com.ankamagames.dofusarena.client.ui.actions.FightActions;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.fight.UIFightEventCardMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSpellMessage;
/*     */ import com.ankamagames.framework.kernel.FrameHandler;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.events.MessageFrame;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.core.messagebox.IMessageBoxEventListener;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import com.ankamagames.xulor.property.Property;
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
/*     */ public class UIFightFrame
/*     */   implements MessageFrame
/*     */ {
/*  40 */   private static UIFightFrame m_instance = new UIFightFrame();
/*     */   
/*  42 */   private MessageBoxControler m_giveUpMessageBoxControler = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public static UIFightFrame getInstance()
/*     */   {
/*  48 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean onMessage(Message message)
/*     */   {
/*  57 */     switch (message.getId())
/*     */     {
/*     */ 
/*     */ 
/*     */     case 18000: 
/*  62 */       this.m_giveUpMessageBoxControler = Xulor.getInstance().msgBox(DofusArenaTranslator.getInstance().getString("question.giveUpFight", new Object[0]), 152);
/*  63 */       this.m_giveUpMessageBoxControler.addEventListener(new IMessageBoxEventListener() {
/*     */         public void messageBoxClosed(int type) {
/*  65 */           if (type == 8)
/*     */           {
/*     */ 
/*  68 */             GiveUpFightRequestMessage netMessage = new GiveUpFightRequestMessage();
/*  69 */             DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage(netMessage);
/*     */           }
/*     */           
/*     */         }
/*     */         
/*  74 */       });
/*  75 */       return false;
/*     */     
/*     */ 
/*     */     case 18012: 
/*  79 */       UIFighterMessage msg = (UIFighterMessage)message;
/*     */       
/*  81 */       Fighter fighter = msg.getFighter();
/*  82 */       if (fighter != null)
/*     */       {
/*     */ 
/*  85 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().removeProperty("singleCardData");
/*     */         
/*  87 */         Property property = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("fight.timeline.selectedFighter");
/*  88 */         if ((property != null) && (property.getValue().equals(fighter)) && (Xulor.getInstance().isLoaded("fighterInformationsDialog")))
/*     */         {
/*  90 */           Xulor.getInstance().unload("fighterInformationsDialog");
/*     */         }
/*     */         else {
/*  93 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("fight.timeline.selectedFighter", fighter);
/*     */           
/*     */ 
/*  96 */           Xulor.getInstance().load("fighterInformationsDialog", Dialogs.getDialogPath("fighterInformationsDialog"), 4L, (short)10000);
/*     */         }
/*     */       }
/*     */       
/* 100 */       return false;
/*     */     
/*     */ 
/*     */     case 18015: 
/* 104 */       UIFightEventCardMessage msg = (UIFightEventCardMessage)message;
/*     */       
/* 106 */       Event eventCard = msg.getEvent();
/* 107 */       if (eventCard != null)
/*     */       {
/* 109 */         Property property = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("singleCardData");
/* 110 */         if ((property != null) && (property.getValue() != null) && (property.getValue().equals(eventCard)) && (Xulor.getInstance().isLoaded("singleCardDialog")))
/*     */         {
/* 112 */           Xulor.getInstance().unload("singleCardDialog");
/*     */         }
/*     */         else {
/* 115 */           Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", eventCard);
/*     */           
/*     */ 
/* 118 */           Xulor.getInstance().load("singleCardDialog", Dialogs.getDialogPath("singleCardDialog"), 4L, (short)10100);
/*     */         }
/*     */       }
/*     */       
/* 122 */       return false;
/*     */     
/*     */ 
/*     */     case 16624: 
/* 126 */       UIFighterSpellMessage msg = (UIFighterSpellMessage)message;
/*     */       
/* 128 */       Spell spell = msg.getSpell();
/* 129 */       if (spell != null)
/*     */       {
/* 131 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", spell);
/*     */       }
/*     */       
/* 134 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16625: 
/* 140 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", null);
/*     */       
/* 142 */       return false;
/*     */     
/*     */ 
/*     */     case 16622: 
/* 146 */       UIFighterEquipmentMessage msg = (UIFighterEquipmentMessage)message;
/*     */       
/* 148 */       FighterCard equipment = msg.getEquipment();
/* 149 */       if (equipment != null)
/*     */       {
/* 151 */         Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", equipment);
/*     */       }
/*     */       
/* 154 */       return false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     case 16623: 
/* 160 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("singleCardData", null);
/*     */       
/* 162 */       return false;
/*     */     
/*     */ 
/*     */     case 18016: 
/* 166 */       UIFighterMessage msg = (UIFighterMessage)message;
/* 167 */       Fighter fighter = msg.getFighter();
/* 168 */       if (fighter != null) {
/* 169 */         fighter.getActor().highlight();
/*     */       }
/* 171 */       return false;
/*     */     
/*     */ 
/*     */     case 18017: 
/* 175 */       UIFighterMessage msg = (UIFighterMessage)message;
/* 176 */       Fighter fighter = msg.getFighter();
/* 177 */       if (fighter != null) {
/* 178 */         fighter.getActor().unhighlight();
/*     */       }
/* 180 */       return false;
/*     */     }
/*     */     
/* 183 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getId()
/*     */   {
/* 192 */     return 0L;
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
/* 210 */     if (!isAboutToBeAdded)
/*     */     {
/*     */ 
/* 213 */       Xulor.getInstance().putActionClass("dofusarena.fight", FightActions.class);
/*     */       
/*     */ 
/* 216 */       DofusArenaGameEntity.getInstance().pushFrame(UIFightMenuBarFrame.getInstance());
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
/* 228 */     if (!isAboutToBeRemoved)
/*     */     {
/*     */ 
/* 231 */       Xulor.getInstance().hideTooltip();
/*     */       
/*     */ 
/* 234 */       DofusArenaGameEntity.getInstance().pushFrame(UIFightMenuBarFrame.getInstance());
/*     */       
/*     */ 
/* 237 */       Xulor.getInstance().unload("fightCountdownDialog");
/* 238 */       Xulor.getInstance().unload("fightEventCardsDialog");
/* 239 */       Xulor.getInstance().unload("timelineDialog");
/* 240 */       Xulor.getInstance().unload("fighterInformationsDialog");
/* 241 */       Xulor.getInstance().unload("singleCardDialog");
/*     */       
/*     */ 
/* 244 */       Xulor.getInstance().hideTooltip();
/*     */       
/* 246 */       if (this.m_giveUpMessageBoxControler != null) {
/* 247 */         Xulor.getInstance().unload(this.m_giveUpMessageBoxControler.getMessageBoxId());
/* 248 */         this.m_giveUpMessageBoxControler = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\protocol\frame\UIFightFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */