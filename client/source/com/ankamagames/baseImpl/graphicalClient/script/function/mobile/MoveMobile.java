/*    */ package com.ankamagames.baseImpl.graphicalClient.script.function.mobile;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.Mobile;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.MobileManager;
/*    */ import com.ankamagames.baseImpl.graphics.alea.mobile.PathMobile;
/*    */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFinder;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*    */ import com.ankamagames.framework.script.JavaFunctionEx;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterDescriptor;
/*    */ import com.ankamagames.framework.script.LuaScriptParameterType;
/*    */ import org.keplerproject.luajava.LuaException;
/*    */ import org.keplerproject.luajava.LuaState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MoveMobile
/*    */   extends JavaFunctionEx
/*    */ {
/*    */   public MoveMobile(LuaState luaState) {
/* 29 */     super(luaState);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 38 */     return "moveMobile";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor[] getParameterDescriptors() {
/* 47 */     return new LuaScriptParameterDescriptor[] {
/* 48 */         new LuaScriptParameterDescriptor("mobileId", LuaScriptParameterType.INTEGER, false), 
/* 49 */         new LuaScriptParameterDescriptor("worldX", LuaScriptParameterType.INTEGER, false), 
/* 50 */         new LuaScriptParameterDescriptor("worldY", LuaScriptParameterType.INTEGER, false), 
/* 51 */         new LuaScriptParameterDescriptor("altitude", LuaScriptParameterType.INTEGER, false)
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(int paramCount) throws LuaException {
/* 61 */     int mobileId = getParamInt(0);
/* 62 */     int worldX = getParamInt(1);
/* 63 */     int worldY = getParamInt(2);
/* 64 */     int altitude = getParamInt(3);
/*    */     
/* 66 */     Mobile mobile = MobileManager.getInstance().getMobile(mobileId);
/* 67 */     if (mobile != null && 
/* 68 */       mobile instanceof PathMobile) {
/* 69 */       PathMobile pathMobile = (PathMobile)mobile;
/* 70 */       Point3 from = new Point3(mobile.getWorldCellX(), mobile.getWorldCellY(), (short)(int)mobile.getAltitude());
/* 71 */       Point3 to = new Point3(worldX, worldY, (short)altitude);
/* 72 */       PathFinder pathFinder = PathFinder.checkOut();
/*    */       
/* 74 */       PathFindParameters defaultParameters = new PathFindParameters();
/* 75 */       defaultParameters.m_searchLimit = 1000;
/* 76 */       PathFindResult result = pathFinder.compute((PathFindMover)pathMobile, (CellInformationProvider)WorldManager.getInstance(), from, to, defaultParameters);
/*    */       
/* 78 */       if (result.isPathFound()) {
/* 79 */         pathMobile.setPath(result, true);
/*    */       }
/* 81 */       pathFinder.release();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\script\function\mobile\MoveMobile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */