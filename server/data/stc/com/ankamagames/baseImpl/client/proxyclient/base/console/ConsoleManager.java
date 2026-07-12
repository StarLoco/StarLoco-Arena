/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.console;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.HelpCommand;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.NavigateToParentCommandSetCommand;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandDescriptor;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandDescriptorSet;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ public final class ConsoleManager
/*     */   extends AbstractInputHistoryManager
/*     */ {
/*     */   private static final String COMMANDS_SEPARATOR = ";";
/*     */   private static final String PATH_SEPARATOR = "/";
/*     */   private static final String PATH_UNCHANGE_MARKER = "!";
/*     */   private static final String PROMPT_END = ">";
/*  33 */   private static final Pattern PATH_PATTERN = Pattern.compile("^((/(\\w+))+/){1}|^(((\\w+)/)+){1}|^(/\\w+){1}");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  38 */   private static final CommandDescriptor GLOBAL_PARENT_COMMAND = new CommandDescriptor("[.]{2}", null, NavigateToParentCommandSetCommand.class);
/*  39 */   private static final CommandDescriptor GLOBAL_HELP_COMMAND = new CommandDescriptor("\\?|help", null, HelpCommand.class);
/*     */   
/*  41 */   private static ConsoleManager m_instance = new ConsoleManager();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private CommandDescriptorSet m_nativeCommandDescriptorSet;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private CommandDescriptorSet m_commandDescriptorSet;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  56 */   private Command m_garbageCommand = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private List<ConsoleView> m_views;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  67 */   private boolean m_useMultiCommands = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  72 */   private boolean m_usePath = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  78 */   private byte m_userLevel = Byte.MAX_VALUE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ConsoleManager()
/*     */   {
/*  86 */     this.m_nativeCommandDescriptorSet = new CommandDescriptorSet();
/*  87 */     this.m_nativeCommandDescriptorSet.addChild(GLOBAL_PARENT_COMMAND);
/*  88 */     this.m_nativeCommandDescriptorSet.addChild(GLOBAL_HELP_COMMAND);
/*     */     
/*     */ 
/*     */ 
/*  92 */     this.m_commandDescriptorSet = new CommandDescriptorSet();
/*     */     
/*     */ 
/*  95 */     this.m_views = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ConsoleManager getInstance()
/*     */   {
/* 105 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setGarbageCommand(Command garbageCommand)
/*     */   {
/* 112 */     this.m_garbageCommand = garbageCommand;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isUseMultiCommands()
/*     */   {
/* 119 */     return this.m_useMultiCommands;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUseMultiCommands(boolean useMultiCommands)
/*     */   {
/* 126 */     this.m_useMultiCommands = useMultiCommands;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isUsePath()
/*     */   {
/* 133 */     return this.m_usePath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUsePath(boolean usePath)
/*     */   {
/* 140 */     this.m_usePath = usePath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte getUserLevel()
/*     */   {
/* 147 */     return this.m_userLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUserLevel(byte userLevel)
/*     */   {
/* 154 */     this.m_userLevel = userLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addView(ConsoleView view)
/*     */   {
/* 164 */     this.m_views.add(view);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean removeView(ConsoleView view)
/*     */   {
/* 174 */     if (this.m_views.contains(view)) {
/* 175 */       this.m_views.remove(view);
/* 176 */       return true;
/*     */     }
/* 178 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CommandDescriptorSet getNativeCommandDescriptorSet()
/*     */   {
/* 185 */     return this.m_nativeCommandDescriptorSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void navigateToParentCommandDescriptorSet()
/*     */   {
/* 193 */     if ((this.m_commandDescriptorSet != null) && (this.m_commandDescriptorSet.getParent() != null)) {
/* 194 */       setCommandDescriptorSet(this.m_commandDescriptorSet.getParent());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCommandDescriptorSet(CommandDescriptorSet commandDescriptorSet)
/*     */   {
/* 204 */     if ((commandDescriptorSet != null) && (commandDescriptorSet != this.m_commandDescriptorSet)) {
/* 205 */       this.m_commandDescriptorSet = commandDescriptorSet;
/* 206 */       String prompt = getPrompt();
/* 207 */       for (ConsoleView view : this.m_views) {
/* 208 */         view.setPrompt(prompt);
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
/*     */   public boolean addCommandListFromXmlFile(URL url)
/*     */   {
/* 221 */     if (this.m_commandDescriptorSet != null) {
/* 222 */       CommandDescriptorSet rootCommandDescriptorSet = this.m_commandDescriptorSet.getRoot();
/* 223 */       if (rootCommandDescriptorSet != null) {
/* 224 */         return rootCommandDescriptorSet.addCommandListFromXmlFile(url);
/*     */       }
/*     */     }
/* 227 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CommandDescriptorSet getCommandDescriptorSet()
/*     */   {
/* 234 */     return this.m_commandDescriptorSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPrompt()
/*     */   {
/* 241 */     if (this.m_commandDescriptorSet != null) {
/* 242 */       return this.m_commandDescriptorSet.getPath() + ">";
/*     */     }
/* 244 */     return "";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void parseInput(String input)
/*     */   {
/* 273 */     pushToHistory(input);
/*     */     
/*     */ 
/* 276 */     String[] commandLines = (String[])null;
/* 277 */     if (isUseMultiCommands()) {
/* 278 */       commandLines = input.split(";");
/*     */     } else {
/* 280 */       commandLines = new String[] { input };
/*     */     }
/*     */     
/*     */     String[] arrayOfString1;
/* 284 */     int j = (arrayOfString1 = commandLines).length; for (int i = 0; i < j; i++) { String commandLine = arrayOfString1[i];
/*     */       
/*     */ 
/* 287 */       commandLine = commandLine.trim();
/*     */       
/*     */ 
/* 290 */       CommandDescriptorSet savedCurrentCommandDescriptorSet = null;
/* 291 */       if ((isUsePath()) && (commandLine.startsWith("!"))) {
/* 292 */         commandLine = commandLine.substring(1);
/* 293 */         savedCurrentCommandDescriptorSet = this.m_commandDescriptorSet;
/*     */       }
/*     */       
/*     */       boolean isAbsolutePath;
/* 297 */       if (isUsePath()) {
/* 298 */         Matcher matcher = PATH_PATTERN.matcher(commandLine);
/* 299 */         if (matcher.find())
/*     */         {
/*     */ 
/* 302 */           String pathGroup = matcher.group();
/*     */           
/*     */ 
/* 305 */           isAbsolutePath = pathGroup.startsWith("/");
/*     */           
/*     */           CommandDescriptorSet targetCommandSet;
/*     */           
/*     */           String[] path;
/*     */           
/*     */           CommandDescriptorSet targetCommandSet;
/* 312 */           if (isAbsolutePath) {
/* 313 */             String[] path = pathGroup.substring(1).split("/");
/* 314 */             targetCommandSet = this.m_commandDescriptorSet.getRoot();
/*     */           } else {
/* 316 */             path = pathGroup.split("/");
/* 317 */             targetCommandSet = this.m_commandDescriptorSet;
/*     */           }
/*     */           
/*     */ 
/* 321 */           if ((path.length == 1) && (!pathGroup.endsWith("/"))) {
/* 322 */             commandLine = commandLine.substring(1);
/*     */           } else {
/*     */             String[] arrayOfString2;
/* 325 */             int m = (arrayOfString2 = path).length; for (int k = 0; k < m; k++) { String commandSet = arrayOfString2[k];
/* 326 */               ArrayList<CommandPattern> pathMatches = targetCommandSet.getMatchesCommandPatterns(commandSet, this.m_userLevel);
/* 327 */               if (pathMatches.isEmpty()) {
/* 328 */                 err("Chemin " + commandSet + " invalide");
/* 329 */                 break; }
/* 330 */               if (pathMatches.size() == 1) {
/* 331 */                 CommandPattern commandPattern = (CommandPattern)pathMatches.get(0);
/* 332 */                 if ((commandPattern instanceof CommandDescriptorSet)) {
/* 333 */                   targetCommandSet = (CommandDescriptorSet)pathMatches.get(0);
/*     */                 } else {
/* 335 */                   err("Chemin " + commandSet + " invalide");
/* 336 */                   break;
/*     */                 }
/*     */               } else {
/* 339 */                 err("Trop de possibilités");
/* 340 */                 break;
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 345 */             commandLine = commandLine.substring(pathGroup.length());
/*     */           }
/*     */           
/*     */ 
/* 349 */           setCommandDescriptorSet(targetCommandSet);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 354 */       ArrayList<CommandPattern> matchesDescriptors = new ArrayList();
/* 355 */       matchesDescriptors.addAll(this.m_commandDescriptorSet.getMatchesCommandPatterns(commandLine, this.m_userLevel));
/* 356 */       matchesDescriptors.addAll(this.m_nativeCommandDescriptorSet.getMatchesCommandPatterns(commandLine, this.m_userLevel));
/*     */       
/* 358 */       if (matchesDescriptors.isEmpty()) {
/* 359 */         if (this.m_garbageCommand != null) {
/* 360 */           ArrayList<String> args = new ArrayList();
/* 361 */           args.add(commandLine);
/* 362 */           this.m_garbageCommand.execute(this, null, args);
/*     */         }
/*     */         else {
/* 365 */           err("Commande '" + commandLine + "' invalide");
/*     */         }
/*     */       }
/*     */       else {
/* 369 */         for (CommandPattern commandPattern : matchesDescriptors)
/*     */         {
/*     */ 
/* 372 */           Command command = commandPattern.createInstance();
/*     */           
/*     */ 
/* 375 */           Pattern argsPattern = commandPattern.getArgsPattern();
/* 376 */           Matcher argsMatcher = argsPattern.matcher(commandLine);
/* 377 */           if (argsMatcher.matches())
/*     */           {
/* 379 */             argsMatcher.reset();
/* 380 */             Object args = new ArrayList();
/* 381 */             int i; for (; argsMatcher.find(); 
/* 382 */                 i <= argsMatcher.groupCount()) { i = 0; continue;
/* 383 */               ((ArrayList)args).add(argsMatcher.group(i));i++;
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 388 */             command.execute(this, commandPattern, (ArrayList)args);
/*     */           }
/* 390 */           else if (argsPattern.pattern().length() != 0) {
/* 391 */             err("Les paramètres de commande ne correspondent pas !");
/*     */           }
/*     */           
/* 394 */           if (!command.isPassThrough()) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 403 */       if (savedCurrentCommandDescriptorSet != null) {
/* 404 */         setCommandDescriptorSet(savedCurrentCommandDescriptorSet);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void log(String text)
/*     */   {
/* 416 */     for (ConsoleView view : this.m_views) {
/* 417 */       view.log(text);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void trace(String text)
/*     */   {
/* 428 */     for (ConsoleView view : this.m_views) {
/* 429 */       view.trace(text);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void err(String text)
/*     */   {
/* 440 */     for (ConsoleView view : this.m_views) {
/* 441 */       view.err(text);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\ConsoleManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */