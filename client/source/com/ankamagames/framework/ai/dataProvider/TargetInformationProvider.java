package com.ankamagames.framework.ai.dataProvider;

import java.util.Iterator;

public interface TargetInformationProvider<T extends com.ankamagames.framework.ai.targetfinder.Target> {
  Iterator<T> getPossibleTargets();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\ai\dataProvider\TargetInformationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */