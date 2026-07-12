package com.ankamagames.xulor.binding;

import com.ankamagames.xulor.core.ConverterLibrary;
import com.ankamagames.xulor.core.EnvironmentWidgetCleaner;
import com.ankamagames.xulor.core.TagLibrary;
import com.ankamagames.xulor.template.IElement;
import com.ankamagames.xulor.util.Cursor;
import java.util.Collection;
import java.util.HashMap;

public interface Binding {
  ConverterLibrary getConverterLibrary();
  
  TagLibrary getTagLibrary();
  
  EnvironmentWidgetCleaner getEnvironmentWidgetCleaner(HashMap<Object, IElement> paramHashMap);
  
  void loadCursors(Collection<Cursor> paramCollection);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\Binding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */