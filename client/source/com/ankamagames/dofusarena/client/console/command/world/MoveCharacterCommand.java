/*    */ package com.ankamagames.dofusarena.client.console.command.world;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*    */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*    */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.CoachActorMovementRequestMessage;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*    */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*    */ import com.ankamagames.framework.kernel.core.maths.Point3;
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
/*    */ public class MoveCharacterCommand
/*    */   implements Command
/*    */ {
/* 31 */   private static long m_lastMoveStart = 0L;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 41 */     if (args.size() < 3 || args.get(2) == null) {
/*    */       return;
/*    */     }
/* 44 */     if (System.nanoTime() - m_lastMoveStart < 300000000L) {
/*    */       return;
/*    */     }
/*    */     
/* 48 */     Direction8 direction = Direction8.getDirectionFromIndex(Integer.valueOf(args.get(2)).intValue());
/*    */     
/* 50 */     LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/* 51 */     Point3 from = new Point3(localCoach.getDestinationWorldX(), localCoach.getDestinationWorldY(), (short)(int)localCoach.getAltitude());
/*    */     
/* 53 */     PathFindParameters defaultParameters = new PathFindParameters();
/* 54 */     defaultParameters.m_searchLimit = 1;
/* 55 */     defaultParameters.m_includeStartCell = false;
/*    */ 
/*    */     
/* 58 */     AleaWorldScene scene = DofusArenaClientInstance.getInstance().getWorldScene();
/* 59 */     WorldCell worldCell = (WorldCell)scene.getWorldCell(from.getX() + direction.getVector()[0], from.getY() + direction.getVector()[1]);
/*    */     
/* 61 */     short altitude = worldCell.getArrivalAltitude((PathFindMover)localCoach, from.getZ(), direction, defaultParameters);
/* 62 */     if (altitude == Integer.MIN_VALUE) {
/*    */       return;
/*    */     }
/* 65 */     boolean valid = worldCell.getMovementAcrossValidity((PathFindMover)localCoach, (short)(int)localCoach.getAltitude(), localCoach.getDirection(), altitude, direction, defaultParameters);
/* 66 */     if (valid) {
/* 67 */       PathFindResult result = new PathFindResult(1);
/* 68 */       result.setStep(0, worldCell.getX(), worldCell.getY(), altitude);
/* 69 */       CoachActorMovementRequestMessage netMessage = new CoachActorMovementRequestMessage();
/* 70 */       netMessage.setPathResult(result);
/* 71 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/* 72 */       m_lastMoveStart = System.nanoTime();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\world\MoveCharacterCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */