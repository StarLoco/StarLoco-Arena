/*     */ package com.ankamagames.dofusarena.client.core.script;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.Adviser;
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.AdviserManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.adviser.text.flying.FlyingText;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*     */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*     */ import com.ankamagames.dofusarena.client.core.action.SpellEffectAction;
/*     */ import com.ankamagames.framework.script.JavaFunctionEx;
/*     */ import com.ankamagames.framework.script.JavaFunctionsLibrary;
/*     */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*     */ import com.ankamagames.framework.script.LuaScriptParameterType;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldTarget;
/*     */ import java.awt.Font;
/*     */ import org.keplerproject.luajava.LuaException;
/*     */ import org.keplerproject.luajava.LuaState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpellEffectActionFunctionsLibrary
/*     */   extends JavaFunctionsLibrary
/*     */ {
/*     */   private SpellEffectAction m_spellEffectAction;
/*     */   
/*     */   private class GetTarget
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public GetTarget(LuaState luaState) {
/*  35 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/*  39 */       return "getTarget";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  43 */       return null;
/*     */     }
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/*  47 */       addReturnValue(SpellEffectActionFunctionsLibrary.this.m_spellEffectAction.getTargetId());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class DisplayFlyingValue
/*     */     extends JavaFunctionEx
/*     */   {
/*  56 */     private int FLYING_EFFECT_DURATION = 4000;
/*     */     
/*     */     public DisplayFlyingValue(LuaState luaState) {
/*  59 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/*  63 */       return "displayFlyingValue";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/*  67 */       return new LuaScriptParameterDescriptor[] { new LuaScriptParameterDescriptor("R", LuaScriptParameterType.NUMBER, false), 
/*  68 */           new LuaScriptParameterDescriptor("G", LuaScriptParameterType.NUMBER, false), 
/*  69 */           new LuaScriptParameterDescriptor("B", LuaScriptParameterType.NUMBER, false), 
/*  70 */           new LuaScriptParameterDescriptor("negatesValue", LuaScriptParameterType.BOOLEAN, true) };
/*     */     }
/*     */ 
/*     */     
/*     */     public void run(int paramCount) throws LuaException {
/*  75 */       float r = (float)getParamDouble(0);
/*  76 */       float g = (float)getParamDouble(1);
/*  77 */       float b = (float)getParamDouble(2);
/*     */       
/*  79 */       int value = SpellEffectActionFunctionsLibrary.this.m_spellEffectAction.getEffectValue();
/*     */       
/*  81 */       if (value == 0) {
/*     */         return;
/*     */       }
/*  84 */       if (paramCount >= 4 && getParamBool(3)) {
/*  85 */         value *= -1;
/*     */       }
/*  87 */       Mobile mobile = MobileManager.getInstance().getMobile(SpellEffectActionFunctionsLibrary.this.m_spellEffectAction.getTargetId());
/*     */       
/*  89 */       if (mobile == null || !mobile.isVisible()) {
/*     */         return;
/*     */       }
/*  92 */       FlyingText flyingText = new FlyingText(new Font("Verdana", 1, 20), String.valueOf(value), this.FLYING_EFFECT_DURATION);
/*  93 */       flyingText.setColor(r, g, b, 1.0F);
/*  94 */       flyingText.setTarget((IsoWorldTarget)mobile);
/*  95 */       AdviserManager.getInstance().addAdviser((Adviser)flyingText);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class GetParams
/*     */     extends JavaFunctionEx
/*     */   {
/*     */     public GetParams(LuaState luaState) {
/* 105 */       super(luaState);
/*     */     }
/*     */     
/*     */     public String getName() {
/* 109 */       return "getParams";
/*     */     }
/*     */     
/*     */     public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 113 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run(int paramCount) throws LuaException {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SpellEffectActionFunctionsLibrary(SpellEffectAction action) {
/* 124 */     super("SpellEffect");
/* 125 */     this.m_spellEffectAction = action;
/*     */     
/* 127 */     registerFunctionClass(GetTarget.class);
/* 128 */     registerFunctionClass(GetParams.class);
/* 129 */     registerFunctionClass(DisplayFlyingValue.class);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\script\SpellEffectActionFunctionsLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */