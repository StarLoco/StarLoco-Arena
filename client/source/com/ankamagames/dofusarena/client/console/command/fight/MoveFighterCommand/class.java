/*     */ package com.ankamagames.dofusarena.client.console.command.fight.MoveFighterCommand;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldCell;
/*     */ import com.ankamagames.baseImpl.graphics.alea.WorldManager;
/*     */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*     */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.game.actor.FighterActor;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.network.protocol.message.game.clientToServer.FighterActorMovementRequestMessage;
/*     */ import com.ankamagames.framework.ai.dataProvider.CellInformationProvider;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindMover;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindParameters;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFindResult;
/*     */ import com.ankamagames.framework.ai.pathfinder.PathFinder;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.maths.Direction8;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MoveFighterCommand
/*     */   implements Command
/*     */ {
/*  35 */   private static Logger m_logger = Logger.getLogger(com.ankamagames.dofusarena.client.console.command.fight.MoveFighterCommand.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/*  45 */     if (args.size() < 3 || args.get(2) == null) {
/*     */       return;
/*     */     }
/*  48 */     Direction8 direction = Direction8.getDirectionFromIndex(Integer.valueOf(args.get(2)).intValue());
/*     */     
/*  50 */     Iterable<Fighter> fighters = DofusArenaGameEntity.getInstance().getLocalCoach().getFightingCoach().getFighters();
/*  51 */     Fighter currentFighter = (Fighter)DofusArenaGameEntity.getInstance().getFight().getTimeline().getCurrentFighter();
/*     */     
/*  53 */     boolean containsFighter = false;
/*  54 */     for (Fighter fighter : fighters) {
/*  55 */       if (fighter == currentFighter) {
/*  56 */         containsFighter = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*  60 */     if (!containsFighter) {
/*  61 */       m_logger.warn("Le fighter courant n'a pas l'air dans ton équipe");
/*     */       
/*     */       return;
/*     */     } 
/*  65 */     FighterActor fighterActor = currentFighter.getActor();
/*     */     
/*  67 */     Point3 from = new Point3(fighterActor.getDestinationWorldX(), fighterActor.getDestinationWorldY(), (short)(int)fighterActor.getAltitude());
/*     */     
/*  69 */     PathFindParameters defaultParameters = new PathFindParameters();
/*  70 */     defaultParameters.m_searchLimit = 3;
/*  71 */     defaultParameters.m_includeStartCell = false;
/*     */ 
/*     */     
/*  74 */     AleaWorldScene scene = DofusArenaClientInstance.getInstance().getWorldScene();
/*  75 */     WorldCell worldCell = (WorldCell)scene.getWorldCell(from.getX() + direction.getVector()[0], from.getY() + direction.getVector()[1]);
/*  76 */     short altitude = worldCell.getArrivalAltitude((PathFindMover)fighterActor, from.getZ(), direction, defaultParameters);
/*     */     
/*  78 */     Point3 to = new Point3(worldCell.getX(), worldCell.getY(), altitude);
/*     */ 
/*     */     
/*  81 */     PathFinder pathFinder = PathFinder.checkOut();
/*  82 */     PathFindResult currentPathResult = pathFinder.compute((PathFindMover)fighterActor, (CellInformationProvider)WorldManager.getInstance(), from, to, defaultParameters);
/*     */     
/*  84 */     if (currentPathResult != null && currentPathResult.isPathFound() && currentPathResult.getPathLength() > 0) {
/*     */ 
/*     */       
/*  87 */       FighterActorMovementRequestMessage netMessage = new FighterActorMovementRequestMessage();
/*  88 */       netMessage.setFighterId(currentFighter.getId());
/*  89 */       netMessage.setPathResult(currentPathResult);
/*  90 */       DofusArenaGameEntity.getInstance().getNetworkEntity().sendMessage((Message)netMessage);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     pathFinder.release();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPassThrough() {
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\fight\MoveFighterCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */