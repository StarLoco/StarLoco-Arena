package com.ankamagames.baseImpl.graphicalClient.ui.progress;

public abstract interface ProgressMonitor
{
  public abstract void beginTask(String paramString, int paramInt);
  
  public abstract void done();
  
  public abstract void setTaskName(String paramString);
  
  public abstract void subTask(String paramString);
  
  public abstract void worked(int paramInt);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\ui\progress\ProgressMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */