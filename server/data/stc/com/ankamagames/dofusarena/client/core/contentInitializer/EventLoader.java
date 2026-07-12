/*     */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.Effect;
/*     */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.event.Event;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEvent;
/*     */ import com.ankamagames.dofusarena.common.game.event.AbstractEventManager;
/*     */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventLoader
/*     */   extends EffectContentDocumentLoader
/*     */ {
/*  23 */   private static final EventLoader m_instance = new EventLoader();
/*     */   
/*     */   public static EventLoader getInstance() {
/*  26 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private EventLoader()
/*     */   {
/*  33 */     setContentDocumentExtension(".dat");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  42 */     return DofusArenaTranslator.getInstance().getString("contentLoader.event", new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void init(AbstractGameClientInstance clientInstance)
/*     */     throws Exception
/*     */   {
/*  51 */     open(DofusArenaConfiguration.getInstance().getString("contentEventFile"));
/*  52 */     clientInstance.fireContentInitializerDone(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void read(DocumentContainer container)
/*     */   {
/*  62 */     if (container == null) {
/*  63 */       return;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  68 */       int eventCount = readInteger();
/*     */       
/*  70 */       for (int i = 0; i < eventCount; i++) {
/*  71 */         int eventId = readInteger();
/*  72 */         boolean eventUseAutomaticDescription = readBoolean();
/*     */         
/*  74 */         Event event = new Event(eventId, eventUseAutomaticDescription);
/*  75 */         AbstractEventManager.getInstance().addEvent(event);
/*     */       }
/*     */       
/*     */ 
/*  79 */       int effectCount = readInteger();
/*     */       
/*  81 */       for (int i = 0; i < effectCount; i++) {
/*  82 */         readAndLoadEffect();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  86 */       e.printStackTrace();
/*     */     }
/*     */     
/*  89 */     container.notifyOnLoadComplete();
/*     */   }
/*     */   
/*     */   public void onEffectLoaded(Effect effect, String parentType, int parentId)
/*     */   {
/*  94 */     AbstractEvent event = AbstractEventManager.getInstance().getAbstractEventFromId(parentId);
/*     */     
/*  96 */     if (event != null)
/*  97 */       event.addEffect(effect);
/*     */   }
/*     */   
/*     */   public void notifyOnLoadComplete() {
/* 101 */     m_logger.info("Events loaded successfully");
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\EventLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */