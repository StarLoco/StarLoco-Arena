/*    */ package com.ankamagames.dofusarena.client.core.preferences;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*    */ import com.ankamagames.framework.preferences.PreferenceStore;
/*    */ import com.ankamagames.xulor.util.XulorUtil;
/*    */ import java.io.IOException;
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
/*    */ public class FightPreferenceStore
/*    */   extends PreferenceStore
/*    */ {
/*    */   private static final String FIGHT_USER_PREFERENCES_FILE = "fightUserPreferences.properties";
/* 25 */   private static final FightPreferenceStore m_instance = new FightPreferenceStore();
/*    */   
/*    */ 
/*    */ 
/*    */   private FightPreferenceStore()
/*    */   {
/* 31 */     super("fightUserPreferences.properties");
/* 32 */     setAutoSave(true);
/*    */     try {
/* 34 */       load();
/*    */     }
/*    */     catch (IOException localIOException) {}
/*    */     
/*    */ 
/* 39 */     int x = DofusArenaClientInstance.getInstance().getGamePreferences().getScreenWidth() - 400;
/*    */     
/* 41 */     setDefault(XulorUtil.generatePreferenceKey("chatDialog", "chatDialog", "x"), x);
/* 42 */     setDefault(XulorUtil.generatePreferenceKey("chatDialog", "chatDialog", "y"), 0);
/* 43 */     setDefault(XulorUtil.generatePreferenceKey("chatDialog", "chatDialog", "width"), 400);
/* 44 */     setDefault(XulorUtil.generatePreferenceKey("chatDialog", "chatDialog", "height"), 200);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static FightPreferenceStore getInstance()
/*    */   {
/* 51 */     return m_instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\preferences\FightPreferenceStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */