package org.fenggui.console;

import java.io.PrintStream;

public interface ICommand {
  String getCommand();
  
  void execute(PrintStream paramPrintStream, Console paramConsole, String[] paramArrayOfString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\console\ICommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */