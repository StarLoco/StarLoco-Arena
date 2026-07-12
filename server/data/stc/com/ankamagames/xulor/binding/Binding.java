package com.ankamagames.xulor.binding;

import com.ankamagames.xulor.core.ConverterLibrary;
import com.ankamagames.xulor.core.EnvironmentWidgetCleaner;
import com.ankamagames.xulor.core.TagLibrary;
import com.ankamagames.xulor.template.IElement;
import com.ankamagames.xulor.util.Cursor;
import java.util.Collection;
import java.util.HashMap;

public abstract interface Binding
{
  public abstract ConverterLibrary getConverterLibrary();
  
  public abstract TagLibrary getTagLibrary();
  
  public abstract EnvironmentWidgetCleaner getEnvironmentWidgetCleaner(HashMap<Object, IElement> paramHashMap);
  
  public abstract void loadCursors(Collection<Cursor> paramCollection);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\Binding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */