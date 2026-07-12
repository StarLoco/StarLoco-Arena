/*     */ package com.ankamagames.framework.script;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class LuaScript
/*     */ {
/*  22 */   private static Logger m_logger = Logger.getLogger(LuaScript.class);
/*     */   
/*  24 */   private int INFINITE_LOOP = -1;
/*     */   
/*     */   private int m_id;
/*     */   
/*     */   private State m_state;
/*     */ 
/*     */   
/*     */   private class Task
/*     */   {
/*     */     private int m_tickTime;
/*     */     
/*     */     private int m_waitTime;
/*     */     
/*     */     private int m_loopCount;
/*     */     
/*     */     private String m_funcName;
/*     */     
/*     */     private Object[] m_args;
/*     */     
/*     */     public Task(int time, int loopCount, String funcName, Object... args) {
/*  44 */       this.m_tickTime = time;
/*  45 */       this.m_waitTime = LuaScript.this.m_time + time;
/*  46 */       this.m_loopCount = loopCount;
/*     */       
/*  48 */       this.m_funcName = funcName;
/*  49 */       this.m_args = args;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void execute() {
/*  56 */       LuaScript.this.m_luaState.pushString(this.m_funcName);
/*  57 */       LuaScript.this.m_luaState.getTable(LuaState.LUA_GLOBALSINDEX.intValue());
/*     */ 
/*     */       
/*     */       try {
/*  61 */         for (int i = 0; i < this.m_args.length; i++) {
/*  62 */           LuaScript.this.m_luaState.pushObjectValue(this.m_args[i]);
/*     */         }
/*  64 */       } catch (LuaException e) {
/*  65 */         LuaScript.m_logger.error(e);
/*     */       } 
/*     */ 
/*     */       
/*  69 */       if (LuaScript.this.m_luaState.resume(this.m_args.length) != 0) {
/*  70 */         LuaScript.this.onError(LuaScript.this.m_luaState, LuaScriptErrorType.RUNTIME_ERROR);
/*     */       } else {
/*  72 */         LuaScript.this.tryToFinish();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void update() {
/*  81 */       if (LuaScript.this.m_time >= this.m_waitTime) {
/*  82 */         execute();
/*     */         
/*  84 */         if (this.m_loopCount == LuaScript.this.INFINITE_LOOP) {
/*  85 */           this.m_waitTime += this.m_tickTime;
/*     */         } else {
/*  87 */           this.m_loopCount--;
/*  88 */           if (this.m_loopCount > 0) {
/*  89 */             this.m_waitTime += this.m_tickTime;
/*     */           } else {
/*  91 */             LuaScript.this.m_tasksToRemove.add(this);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public enum State
/*     */   {
/* 100 */     NOT_LOADED, LOADED, RUNNING, DONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 105 */   private LuaManager m_manager = null;
/*     */   
/*     */   private int m_time;
/*     */   private LuaState m_luaState;
/*     */   private boolean m_needToInterrupt = false;
/* 110 */   private List<Task> m_tasks = new ArrayList<Task>();
/* 111 */   private List<Task> m_tasksToRemove = new ArrayList<Task>();
/*     */ 
/*     */   
/*     */   private boolean m_silentError = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public LuaScript(int id, LuaState luastate, LuaManager manager, JavaFunctionsLibrary[] libraries) {
/* 119 */     this.m_id = id;
/* 120 */     this.m_luaState = luastate;
/* 121 */     this.m_manager = manager;
/* 122 */     this.m_state = State.NOT_LOADED;
/*     */ 
/*     */     
/* 125 */     if (libraries != null) {
/* 126 */       byte b; int i; JavaFunctionsLibrary[] arrayOfJavaFunctionsLibrary; for (i = (arrayOfJavaFunctionsLibrary = libraries).length, b = 0; b < i; ) { JavaFunctionsLibrary library = arrayOfJavaFunctionsLibrary[b];
/*     */         try {
/* 128 */           library.importLibs(this.m_luaState);
/* 129 */         } catch (Exception e) {
/* 130 */           m_logger.error(e);
/*     */         } 
/*     */         
/*     */         b++; }
/*     */     
/*     */     } 
/* 136 */     this.m_luaState.openBase();
/* 137 */     this.m_luaState.openMath();
/* 138 */     this.m_luaState.openTable();
/* 139 */     this.m_luaState.openOs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 146 */     return this.m_id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState() {
/* 155 */     return this.m_state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTime() {
/* 162 */     return this.m_time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSilentError() {
/* 169 */     return this.m_silentError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setSilentError(boolean silentError) {
/* 176 */     this.m_silentError = silentError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTask(int time, int loopCount, String funcName, Object... args) {
/* 188 */     Task task = new Task(time, loopCount, funcName, args);
/* 189 */     this.m_tasks.add(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateTasks() {
/* 196 */     for (Task t : this.m_tasksToRemove) {
/* 197 */       this.m_tasks.remove(t);
/*     */     }
/* 199 */     this.m_tasksToRemove.clear();
/* 200 */     for (int i = 0; i < this.m_tasks.size(); i++) {
/* 201 */       ((Task)this.m_tasks.get(i)).update();
/*     */     }
/* 203 */     tryToFinish();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(int deltaTime) {
/* 212 */     if (this.m_needToInterrupt) {
/* 213 */       finish();
/*     */     } else {
/* 215 */       this.m_time += deltaTime;
/* 216 */       switch (this.m_state) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case RUNNING:
/* 223 */           updateTasks();
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadFile(String fileName) throws Exception {
/* 237 */     InputStream stream = null;
/*     */     
/*     */     try {
/* 240 */       URL jarUrl = new URL(fileName);
/* 241 */       stream = jarUrl.openStream();
/* 242 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 245 */       File file = new File(fileName);
/* 246 */       if (file.exists()) {
/* 247 */         stream = new FileInputStream(file);
/*     */       }
/*     */     } 
/*     */     
/* 251 */     if (stream != null) {
/* 252 */       byte[] buffer = new byte[stream.available()];
/* 253 */       if (stream.read(buffer) > 0) {
/* 254 */         loadCommand(new String(buffer));
/*     */       } else {
/* 256 */         m_logger.error("Erreur lors du chargement du script : " + fileName + ", pas de données.");
/* 257 */       }  stream.close();
/*     */     } else {
/* 259 */       m_logger.error("Erreur lors du chargement du script : " + fileName + ", pas de stream ouvert.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadCommand(String command) {
/* 269 */     assert this.m_luaState != null;
/* 270 */     if (this.m_luaState.LloadString(command) == 0) {
/* 271 */       this.m_state = State.LOADED;
/*     */     } else {
/* 273 */       onError(this.m_luaState, LuaScriptErrorType.SYNTAX_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 281 */     if (this.m_state == State.LOADED) {
/*     */       
/* 283 */       int status = this.m_luaState.resume(0);
/* 284 */       this.m_state = State.RUNNING;
/* 285 */       if (status != 0) {
/* 286 */         onError(this.m_luaState, LuaScriptErrorType.RUNTIME_ERROR);
/*     */       } else {
/* 288 */         tryToFinish();
/*     */       } 
/* 290 */     } else if (!this.m_needToInterrupt) {
/* 291 */       this.m_luaState.pushString("No file loaded");
/* 292 */       onError(this.m_luaState, LuaScriptErrorType.NOT_LOADED_ERROR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interrupt() {
/* 300 */     this.m_needToInterrupt = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tryToFinish() {
/* 307 */     if (this.m_tasks.isEmpty()) {
/* 308 */       finish();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finish() {
/* 316 */     if (!this.m_luaState.isClosed()) {
/* 317 */       this.m_luaState.close();
/*     */     }
/* 319 */     this.m_state = State.DONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onError(LuaState luaState, LuaScriptErrorType errorType) {
/* 330 */     String msg = null;
/* 331 */     if (!luaState.isClosed() && luaState.getTop() >= 1 && luaState.isString(-1)) {
/* 332 */       msg = luaState.toString(-1);
/* 333 */       luaState.pop(1);
/*     */     } 
/* 335 */     if (msg == null) {
/* 336 */       msg = "";
/*     */     }
/*     */ 
/*     */     
/* 340 */     if (!this.m_silentError) {
/* 341 */       this.m_manager.onScriptError(this, errorType, msg);
/*     */     }
/*     */ 
/*     */     
/* 345 */     interrupt();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\LuaScript.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */