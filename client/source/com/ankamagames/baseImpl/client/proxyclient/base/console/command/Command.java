package com.ankamagames.baseImpl.client.proxyclient.base.console.command;

import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
import java.util.ArrayList;

public interface Command {
  void execute(ConsoleManager paramConsoleManager, CommandPattern paramCommandPattern, ArrayList<String> paramArrayList);
  
  boolean isPassThrough();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\command\Command.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */