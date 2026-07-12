package com.ankamagames.baseImpl.graphicalClient.ui.progress;

public interface ProgressMonitor {
  void beginTask(String paramString, int paramInt);
  
  void done();
  
  void setTaskName(String paramString);
  
  void subTask(String paramString);
  
  void worked(int paramInt);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClien\\ui\progress\ProgressMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */