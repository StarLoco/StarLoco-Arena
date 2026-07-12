/*     */ package com.ankamagames.dofusarena.client.ui.actions;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.proxy.ProxyGroup;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.connection.UIChangeLanguageRequestMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.connection.UILogonRequestMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.framework.kernel.core.translator.Language;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.form.Form;
/*     */ import com.ankamagames.xulor.event.Event;
/*     */ import com.ankamagames.xulor.event.Key;
/*     */ import com.ankamagames.xulor.event.KeyPressedEvent;
/*     */ import com.ankamagames.xulor.event.MapPointClickEvent;
/*     */ import com.ankamagames.xulor.event.SliderMovedEvent;
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
/*     */ 
/*     */ public class Actions
/*     */ {
/*     */   public static final String PACKAGE = "dofusarena";
/*     */   
/*     */   public static void disconnect(Event event) {
/*  36 */     UIMessage message = new UIMessage();
/*  37 */     message.setId(16386);
/*  38 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void quit(Event event) {
/*  48 */     UIMessage message = new UIMessage();
/*  49 */     message.setId(16387);
/*  50 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setLanguage(Event event, String languageCode) {
/*  61 */     UIChangeLanguageRequestMessage message = new UIChangeLanguageRequestMessage();
/*  62 */     message.setLanguage(Language.getLanguage(languageCode));
/*  63 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFullScreen(Event event) {
/*  72 */     boolean currentValue = DofusArenaClientInstance.getInstance().getGamePreferences().getFullScreen();
/*  73 */     DofusArenaClientInstance.getInstance().getGamePreferences().setFullScreen(!currentValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setMusicVolume(Event event) {
/*  82 */     if (event != null && event instanceof SliderMovedEvent) {
/*  83 */       DofusArenaClientInstance.getInstance().getGamePreferences().setMusicVolume((float)((SliderMovedEvent)event).getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setSoundsVolume(Event event) {
/*  93 */     if (event != null && event instanceof SliderMovedEvent) {
/*  94 */       DofusArenaClientInstance.getInstance().getGamePreferences().setSoundsVolume((float)((SliderMovedEvent)event).getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setMusicMute(Event event) {
/* 104 */     boolean currentValue = DofusArenaClientInstance.getInstance().getGamePreferences().getMusicMute();
/* 105 */     DofusArenaClientInstance.getInstance().getGamePreferences().setMusicMute(!currentValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setSoundsMute(Event event) {
/* 114 */     boolean currentValue = DofusArenaClientInstance.getInstance().getGamePreferences().getSoundsMute();
/* 115 */     DofusArenaClientInstance.getInstance().getGamePreferences().setSoundsMute(!currentValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean validateLoginForm(Form form) {
/* 125 */     form.synchronizeProperties();
/* 126 */     return (!form.getProperty("account.name").isEmpty() && !form.getProperty("account.password").isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void logon(Event event, Form form) {
/* 136 */     if (event instanceof KeyPressedEvent && !((KeyPressedEvent)event).getKeyClass().equals(Key.ENTER)) {
/*     */       return;
/*     */     }
/* 139 */     if (form.isValid()) {
/*     */       
/* 141 */       ProxyGroup proxyGroup = (ProxyGroup)form.getProperty("proxy.selected").getValue();
/* 142 */       String login = form.getProperty("account.name").getString();
/* 143 */       String password = form.getProperty("account.password").getString();
/* 144 */       boolean remember = form.getProperty("account.remember").getBoolean();
/*     */ 
/*     */       
/* 147 */       UILogonRequestMessage message = UILogonRequestMessage.checkOut();
/* 148 */       message.setProxyGroup(proxyGroup);
/* 149 */       message.setLogin(login);
/* 150 */       message.setPassword(password);
/* 151 */       message.setRemember(Boolean.valueOf(remember));
/* 152 */       Worker.getInstance().pushMessage((Message)message);
/*     */     } else {
/*     */       
/* 155 */       System.out.println("Formulaire invalide");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseCoachStatisticsDialog(Event event) {
/* 166 */     UIMessage message = new UIMessage();
/* 167 */     message.setId(20009);
/* 168 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseOptionsDialog(Event event) {
/* 179 */     UIMessage message = new UIMessage();
/* 180 */     message.setId(20011);
/* 181 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseCoachInventoryDialog(Event event) {
/* 192 */     UIMessage message = new UIMessage();
/* 193 */     message.setId(20006);
/* 194 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseChatDialog(Event event) {
/* 204 */     UIMessage message = new UIMessage();
/* 205 */     message.setId(20001);
/* 206 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseTeamManagementDialog(Event event) {
/* 216 */     UIMessage message = new UIMessage();
/* 217 */     message.setId(20004);
/* 218 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseMenuDialog(Event event) {
/* 228 */     UIMessage message = new UIMessage();
/* 229 */     message.setId(20007);
/* 230 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createRandomFightSearch(Event event) {
/* 240 */     UIMessage message = new UIMessage();
/* 241 */     message.setId(19500);
/* 242 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closeFightResultDialog(Event event) {
/* 252 */     UIMessage message = new UIMessage();
/* 253 */     message.setId(20005);
/* 254 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closePrivateMessageDialog(Event event) {
/* 264 */     UIMessage message = new UIMessage();
/* 265 */     message.setId(19004);
/* 266 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void zoomIn(Event event) {
/* 273 */     double zoom = Xulor.getInstance().getEnvironment().getPropertiesProvider().getDoubleProperty("miniMap.zoom");
/* 274 */     zoom = Math.min(1.0D, zoom + 0.1D);
/* 275 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("miniMap.zoom", Double.valueOf(zoom));
/* 276 */     if (event instanceof MapPointClickEvent) {
/* 277 */       System.out.println(((MapPointClickEvent)event).getTarget());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void zoomOut(Event event) {
/* 282 */     double zoom = Xulor.getInstance().getEnvironment().getPropertiesProvider().getDoubleProperty("miniMap.zoom");
/* 283 */     zoom = Math.max(0.0D, zoom - 0.1D);
/* 284 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("miniMap.zoom", Double.valueOf(zoom));
/*     */   }
/*     */   
/*     */   public static void setMapZoom(Event event) {
/* 288 */     if (event != null && event instanceof SliderMovedEvent)
/* 289 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("miniMap.zoom", Double.valueOf(((SliderMovedEvent)event).getValue())); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\actions\Actions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */