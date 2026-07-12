/*     */ package org.fenggui.console;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.ObservableWidget;
/*     */ import org.fenggui.event.IKeyPressedListener;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.KeyPressedEvent;
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
/*     */ 
/*     */ 
/*     */ public class Console
/*     */   extends ObservableWidget
/*     */ {
/*  41 */   private ConsoleAppearance appearance = null;
/*  42 */   private ArrayList<ICommand> commands = new ArrayList<ICommand>();
/*  43 */   private PrintStream out = null;
/*     */   public static final String PROMPT = ">>";
/*  45 */   private File currentDir = new File("");
/*  46 */   private int carretIndex = -1;
/*  47 */   private ArrayList<String> history = new ArrayList<String>();
/*  48 */   private int historyIndex = 0;
/*     */ 
/*     */   
/*     */   public Console() {
/*  52 */     this.appearance = new ConsoleAppearance(this);
/*  53 */     this.out = new PrintStream(new ConsoleOutputStream(this.appearance.getTextRenderer()));
/*  54 */     buildKeyBehavior();
/*  55 */     setupTheme(Console.class);
/*     */     
/*  57 */     getAppearance().getTextRenderer().setText("Welcome to the FengGUI console, warrior!\n");
/*  58 */     getAppearance().getPromtRenderer().setText(">>");
/*  59 */     setCarretIndex(">>".length());
/*  60 */     setTraversable(true);
/*     */     
/*  62 */     add(new ListCommand());
/*  63 */     add(new PwdCommand());
/*  64 */     add(new ChangeDirectoryCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildKeyBehavior() {
/*  69 */     addKeyPressedListener(new IKeyPressedListener()
/*     */         {
/*     */           public void keyPressed(KeyPressedEvent kpe) {
/*  72 */             Console.this.handleKeyPressed(kpe);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public String getPrompt() {
/*  78 */     return getAppearance().getPromtRenderer().getText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrompt(String s) {
/*  83 */     getAppearance().getPromtRenderer().setText(s);
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleKeyPressed(KeyPressedEvent kpe) {
/*     */     String s;
/*  89 */     switch (kpe.getKeyClass()) {
/*     */       
/*     */       case ENTER:
/*  92 */         run(getPrompt().substring(">>".length(), getPrompt().length()));
/*  93 */         this.history.add(getPrompt());
/*  94 */         this.historyIndex++;
/*  95 */         setPrompt(">>");
/*  96 */         this.carretIndex = ">>".length();
/*  97 */         this.historyIndex = this.history.size() - 1;
/*     */         break;
/*     */       case BACKSPACE:
/* 100 */         s = getPrompt();
/* 101 */         if (this.carretIndex > ">>".length()) {
/*     */           
/* 103 */           s = s.substring(0, s.length() - 1);
/* 104 */           setPrompt(s);
/* 105 */           this.carretIndex--;
/*     */         } 
/*     */         break;
/*     */       case LEFT:
/* 109 */         s = getPrompt();
/* 110 */         if (this.carretIndex > ">>".length())
/*     */         {
/* 112 */           this.carretIndex--;
/*     */         }
/*     */         break;
/*     */       case RIGHT:
/* 116 */         s = getPrompt();
/* 117 */         if (this.carretIndex <= s.length())
/*     */         {
/* 119 */           this.carretIndex++;
/*     */         }
/*     */         break;
/*     */       case UP:
/* 123 */         this.historyIndex--;
/* 124 */         if (this.historyIndex < 0) this.historyIndex = 0; 
/* 125 */         setPrompt(this.history.get(this.historyIndex));
/* 126 */         this.carretIndex = getPrompt().length();
/*     */         break;
/*     */       case DOWN:
/* 129 */         this.historyIndex++;
/* 130 */         if (this.historyIndex >= this.history.size()) {
/*     */           
/* 132 */           setPrompt(">>");
/* 133 */           this.historyIndex--;
/*     */         } else {
/*     */           
/* 136 */           setPrompt(this.history.get(this.historyIndex));
/* 137 */         }  this.carretIndex = getPrompt().length();
/*     */         break;
/*     */       case LETTER:
/*     */       case DIGIT:
/* 141 */         s = getPrompt();
/* 142 */         setPrompt(String.valueOf(s.substring(0, this.carretIndex)) + 
/* 143 */             kpe.getKey() + 
/* 144 */             s.substring(this.carretIndex, s.length()));
/* 145 */         this.carretIndex++;
/*     */         break;
/*     */     } 
/* 148 */     getAppearance().getCarretTimer().reset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(ICommand command) {
/* 154 */     this.commands.add(command);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(ICommand command) {
/* 159 */     this.commands.remove(command);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 164 */     this.commands.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ConsoleAppearance getAppearance() {
/* 170 */     return this.appearance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(String commandLine) {
/* 175 */     getOut().println(">>" + commandLine);
/* 176 */     String[] split = commandLine.split(" ");
/*     */     
/* 178 */     ICommand command = getCommand(split[0]);
/*     */     
/* 180 */     if (command == null) {
/*     */       
/* 182 */       getOut().println("Command \"" + split[0] + "\" not recognized!");
/*     */     }
/*     */     else {
/*     */       
/* 186 */       command.execute(getOut(), this, split);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ICommand getCommand(String command) {
/* 192 */     for (ICommand c : this.commands) {
/*     */       
/* 194 */       if (c.getCommand().equals(command)) {
/* 195 */         return c;
/*     */       }
/*     */     } 
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrintStream getOut() {
/* 203 */     return this.out;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getCurrentDir() {
/* 208 */     return this.currentDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentDir(File currentDir) {
/* 213 */     this.currentDir = currentDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCarretIndex() {
/* 218 */     return this.carretIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCarretIndex(int carretIndex) {
/* 223 */     this.carretIndex = carretIndex;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\console\Console.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */