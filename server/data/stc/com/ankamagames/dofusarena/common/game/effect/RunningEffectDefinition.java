/*    */ package com.ankamagames.dofusarena.common.game.effect;
/*    */ 
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.RunningEffectStatus;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.effect.runningEffect.StaticRunningEffect;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.ConstantDefinition;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.utils.Constants;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RunningEffectDefinition
/*    */   extends ConstantDefinition<StaticRunningEffect>
/*    */ {
/* 15 */   public static int NO_SCRIPT_ID = -1;
/* 16 */   public static int HP_LOSS_SCRIPT_ID = 1001;
/* 17 */   public static int HP_GAIN_SCRIPT_ID = 1002;
/* 18 */   public static int HP_LEECH_SCRIPT_ID = 1003;
/* 19 */   public static int MP_LOSS_SCRIPT_ID = 1004;
/* 20 */   public static int MP_GAIN_SCRIPT_ID = 1005;
/* 21 */   public static int MP_LEECH_SCRIPT_ID = 1006;
/* 22 */   public static int AP_LOSS_SCRIPT_ID = 1007;
/* 23 */   public static int AP_GAIN_SCRIPT_ID = 1008;
/* 24 */   public static int AP_LEECH_SCRIPT_ID = 1009;
/* 25 */   public static int SLIDE_MOBILE_SCRIPT_ID = 1010;
/* 26 */   public static int RG_GAIN_SCRIPT_ID = 1011;
/* 27 */   public static int RG_LOSS_SCRIPT_ID = 1012;
/* 28 */   public static int PETRIFIED_SCRIPT_ID = -1;
/* 29 */   public static int STABILIZED_SCRIPT_ID = -1;
/* 30 */   public static int ROOTED_SCRIPT_ID = -1;
/*    */   
/*    */   private String m_adminDescription;
/*    */   private int[] m_paramsCount;
/*    */   private int m_scriptId;
/*    */   
/*    */   public RunningEffectDefinition(int id, StaticRunningEffect object, Constants<StaticRunningEffect> constants, int scriptId, String decription, RunningEffectStatus status, int... paramsCount)
/*    */   {
/* 38 */     super(id, object, constants);
/* 39 */     object.setId(id);
/* 40 */     object.setRunningEffectStatus(status);
/* 41 */     this.m_paramsCount = paramsCount;
/* 42 */     this.m_adminDescription = decription;
/* 43 */     this.m_scriptId = scriptId;
/*    */   }
/*    */   
/*    */   public String getAdminDescription() {
/* 47 */     return this.m_adminDescription;
/*    */   }
/*    */   
/*    */   public int getScriptId() {
/* 51 */     return this.m_scriptId;
/*    */   }
/*    */   
/*    */   public int[] getParamsCount() {
/* 55 */     return this.m_paramsCount;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean checkParamsCount(int count)
/*    */   {
/* 64 */     if (this.m_paramsCount == null)
/* 65 */       return count == 0;
/* 66 */     int[] arrayOfInt; int j = (arrayOfInt = this.m_paramsCount).length; for (int i = 0; i < j; i++) { int counts = arrayOfInt[i];
/* 67 */       if (counts == count) return true;
/*    */     }
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\effect\RunningEffectDefinition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */