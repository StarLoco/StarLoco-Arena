/*    */ package com.ankamagames.baseImpl.graphicalClient.script;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.sound.PlayMusic;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.sound.PlaySound;
/*    */ import com.ankamagames.baseImpl.graphicalClient.script.function.sound.StopMusic;
/*    */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*    */ import com.ankamagames.framework.sounds.SoundManager;
/*    */ import com.ankamagames.framework.sounds.group.AudioSourceGroup;
/*    */ import com.ankamagames.framework.sounds.group.DefaultSourceGroup;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundFunctionsLibrary
/*    */   extends JavaFunctionsLibrary
/*    */ {
/*    */   public static final String SOUND_GROUP_NAME = "SoundScriptGroup";
/* 23 */   private static SoundFunctionsLibrary m_instance = new SoundFunctionsLibrary();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private SoundFunctionsLibrary() {
/* 29 */     super("Sound");
/* 30 */     registerFunctionClass(PlaySound.class);
/* 31 */     registerFunctionClass(PlayMusic.class);
/* 32 */     registerFunctionClass(StopMusic.class);
/*    */ 
/*    */     
/* 35 */     SoundManager.getInstance().addGroup((AudioSourceGroup)new DefaultSourceGroup("SoundScriptGroup"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SoundFunctionsLibrary getInstance() {
/* 42 */     return m_instance;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\SoundFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */