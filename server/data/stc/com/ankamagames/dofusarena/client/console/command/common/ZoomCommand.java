/*    */ package com.ankamagames.dofusarena.client.console.command.common;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ public class ZoomCommand
/*    */   implements Command
/*    */ {
/*    */   public static final float ZOOM_OFFSET = 0.1F;
/*    */   
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 32 */     if ((args == null) || (args.size() < 3) || (args.get(2) == null)) {
/* 33 */       return;
/*    */     }
/* 35 */     AleaWorldScene scene = DofusArenaClientInstance.getInstance().getWorldScene();
/*    */     
/* 37 */     if (scene == null) {
/* 38 */       return;
/*    */     }
/* 40 */     double zoomFactor = scene.getDesiredZoomFactor();
/*    */     
/* 42 */     if (((String)args.get(2)).equals("+")) {
/* 43 */       zoomFactor += 0.10000000149011612D;
/* 44 */     } else if (((String)args.get(2)).equals("-")) {
/* 45 */       zoomFactor -= 0.10000000149011612D;
/*    */     } else {
/* 47 */       return;
/*    */     }
/* 49 */     scene.setDesiredZoomFactor(zoomFactor);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\common\ZoomCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */