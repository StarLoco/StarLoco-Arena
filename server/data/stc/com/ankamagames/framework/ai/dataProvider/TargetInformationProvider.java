package com.ankamagames.framework.ai.dataProvider;

import com.ankamagames.framework.ai.targetfinder.Target;
import java.util.Iterator;

public abstract interface TargetInformationProvider<T extends Target>
{
  public abstract Iterator<T> getPossibleTargets();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\dataProvider\TargetInformationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */