/*     */ package com.ankamagames.framework.script.action;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.core.common.message.scheduler.MessageScheduler;
/*     */ import com.ankamagames.framework.script.LuaManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public final class ActionGroup
/*     */   implements ActionEventListener
/*     */ {
/*  22 */   private static final Logger m_logger = Logger.getLogger(ActionGroup.class);
/*     */   
/*  24 */   private ArrayList<ActionGroupEventListener> m_listeners = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  29 */   private LinkedList<Action> m_actions = new LinkedList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LinkedList<Action> getActions()
/*     */   {
/*  38 */     return this.m_actions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addAction(Action action)
/*     */   {
/*  47 */     this.m_actions.add(action);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action removeAction(int uniqueId)
/*     */   {
/*  57 */     Action action = getActionByUniqueId(uniqueId);
/*  58 */     if (action != null) {
/*  59 */       this.m_actions.remove(action);
/*     */     }
/*  61 */     return action;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getActionByUniqueId(int uniqueId)
/*     */   {
/*  71 */     for (Action action : this.m_actions) {
/*  72 */       if (action.getUniqueId() == uniqueId) {
/*  73 */         return action;
/*     */       }
/*     */     }
/*  76 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getActionByTypeAndId(int actionType, int actionId)
/*     */   {
/*  86 */     for (Action action : this.m_actions) {
/*  87 */       if ((action.getActionId() == actionId) && (action.getActionType() == actionType)) {
/*  88 */         return action;
/*     */       }
/*     */     }
/*  91 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getActionByType(int actionType)
/*     */   {
/* 101 */     for (Action action : this.m_actions) {
/* 102 */       if (action.getActionType() == actionType) {
/* 103 */         return action;
/*     */       }
/*     */     }
/* 106 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getActionByTarget(int target)
/*     */   {
/* 116 */     for (Action action : this.m_actions) {
/* 117 */       if (action.getTargetId() == target) {
/* 118 */         return action;
/*     */       }
/*     */     }
/* 121 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterable<Long> getTargets()
/*     */   {
/* 129 */     ArrayList<Long> list = new ArrayList();
/* 130 */     for (Action action : this.m_actions) {
/* 131 */       long targetId = action.getTargetId();
/*     */       
/* 133 */       if ((targetId != Long.MIN_VALUE) && (!list.contains(Long.valueOf(targetId))))
/*     */       {
/* 135 */         list.add(Long.valueOf(targetId));
/*     */       }
/*     */     }
/*     */     
/* 139 */     return list;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getActionBySpecificActionOnTarget(int targetId, int actionType, int actionId)
/*     */   {
/* 151 */     for (Action action : this.m_actions) {
/* 152 */       if ((action.getTargetId() == targetId) && (action.getActionType() == actionType) && (action.getActionId() == actionId)) {
/* 153 */         return action;
/*     */       }
/*     */     }
/* 156 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addListener(ActionGroupEventListener listener)
/*     */   {
/* 164 */     this.m_listeners.add(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeListener(ActionGroupEventListener listener)
/*     */   {
/* 172 */     this.m_listeners.remove(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void runNextAction()
/*     */   {
/* 180 */     if ((this.m_actions == null) || (this.m_actions.size() <= 0))
/*     */     {
/* 182 */       fireActionGroupFinishedEvent();
/* 183 */       return;
/*     */     }
/*     */     
/* 186 */     Action action = (Action)this.m_actions.getFirst();
/*     */     
/* 188 */     runAction(action, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void runAction(Action action, boolean waitFinishEventToRemove)
/*     */   {
/* 199 */     if (action.getTriggerActionUniqueId() != -1)
/*     */     {
/* 201 */       Action triggeredAction = getActionByUniqueId(action.getTriggerActionUniqueId());
/*     */       
/* 203 */       if (triggeredAction != null)
/*     */       {
/* 205 */         runAction(triggeredAction, true);
/* 206 */         return;
/*     */       }
/*     */     }
/*     */     
/* 210 */     if (waitFinishEventToRemove) {
/* 211 */       action.addListener(this);
/*     */     } else {
/* 213 */       removeAction(action.getUniqueId());
/*     */     }
/* 215 */     action.run();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onActionFinished(Action action)
/*     */   {
/* 223 */     action.removeListener(this);
/* 224 */     removeAction(action.getUniqueId());
/*     */     
/* 226 */     runNextAction();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void kill()
/*     */   {
/* 234 */     m_logger.info("Kill des actions de la pile (" + getActions().size() + ")");
/*     */     
/* 236 */     ArrayList<Action> actionsToKill = new ArrayList();
/*     */     
/*     */ 
/* 239 */     for (Action action : this.m_actions) {
/* 240 */       action.removeListener(this);
/* 241 */       actionsToKill.add(action);
/*     */     }
/* 243 */     this.m_actions.clear();
/*     */     
/*     */ 
/* 246 */     for (Action action : actionsToKill)
/*     */     {
/* 248 */       if ((action instanceof ScriptedAction))
/*     */       {
/* 250 */         int waitingScriptId = ((ScriptedAction)action).getWaitingEndScript();
/*     */         
/* 252 */         if (waitingScriptId != -1)
/*     */         {
/* 254 */           LuaManager.getInstance().interruptScript(waitingScriptId);
/*     */         }
/*     */       }
/* 257 */       else if ((action instanceof TimedAction))
/*     */       {
/* 259 */         TimedAction timedAction = (TimedAction)action;
/* 260 */         MessageScheduler.getInstance().removeAllClocks(timedAction);
/*     */       }
/*     */     }
/*     */     
/* 264 */     fireActionGroupFinishedEvent();
/*     */   }
/*     */   
/*     */ 
/*     */   private void fireActionGroupFinishedEvent()
/*     */   {
/*     */     ActionGroupEventListener[] arrayOfActionGroupEventListener;
/*     */     
/* 272 */     int j = (arrayOfActionGroupEventListener = (ActionGroupEventListener[])this.m_listeners.toArray(new ActionGroupEventListener[0])).length; for (int i = 0; i < j; i++) { ActionGroupEventListener listener = arrayOfActionGroupEventListener[i];
/* 273 */       listener.onActionGroupFinished(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\action\ActionGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */