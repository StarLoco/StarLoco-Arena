/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.NavigateToCommandSetCommand;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Stack;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
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
/*     */ public class CommandDescriptorSet
/*     */   extends CommandPattern
/*     */ {
/*  30 */   protected static final Logger m_logger = Logger.getLogger(CommandDescriptorSet.class);
/*     */   
/*     */   private CommandDescriptorSet m_parent;
/*     */   
/*     */   private ArrayList<CommandPattern> m_children;
/*     */   
/*     */ 
/*     */   private static class DescriptorHandler
/*     */     extends DefaultHandler
/*     */   {
/*     */     private static final String COMMAND_LIST = "commandList";
/*     */     
/*     */     private static final String DESCRIPTOR_SET = "commandSet";
/*     */     
/*     */     private static final String DESCRIPTOR = "command";
/*     */     
/*     */     private static final String NAME_ATTRIBUTE = "name";
/*     */     
/*     */     private static final String CMD_PATTERN_ATTRIBUTE = "cmdPattern";
/*     */     private static final String ARGS_PATTERN_ATTRIBUTE = "argsPattern";
/*     */     private static final String CLASS_ATTRIBUTE = "class";
/*     */     private static final String LEVEL_ATTRIBUTE = "level";
/*     */     private Stack<CommandDescriptorSet> m_commandDescriptorSets;
/*     */     
/*     */     public DescriptorHandler(CommandDescriptorSet rootCommandDescriptorSet)
/*     */     {
/*  56 */       this.m_commandDescriptorSets = new Stack();
/*  57 */       this.m_commandDescriptorSets.add(rootCommandDescriptorSet);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void startElement(String uri, String localName, String qName, Attributes attributes)
/*     */       throws SAXException
/*     */     {
/*  68 */       if (!qName.equals("commandList"))
/*     */       {
/*  70 */         String name = attributes.getValue("name");
/*  71 */         String cmdRegex = attributes.getValue("cmdPattern");
/*  72 */         String argsRegex = attributes.getValue("argsPattern");
/*  73 */         String level = attributes.getValue("level");
/*     */         
/*     */ 
/*  76 */         if ((cmdRegex == null) || (cmdRegex.length() == 0)) {
/*  77 */           CommandDescriptorSet.m_logger.error("cmdPattern est invalide pour " + name + "!");
/*     */         }
/*     */         
/*  80 */         if (qName.equals("commandSet"))
/*     */         {
/*  82 */           CommandDescriptorSet descriptorSet = new CommandDescriptorSet(cmdRegex, argsRegex);
/*  83 */           if (name != null) {
/*  84 */             descriptorSet.setName(name);
/*     */           }
/*  86 */           if (level != null) {
/*  87 */             descriptorSet.setLevel(Byte.valueOf(level).byteValue());
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*  92 */           if (!this.m_commandDescriptorSets.isEmpty()) {
/*  93 */             CommandDescriptorSet parent = (CommandDescriptorSet)this.m_commandDescriptorSets.lastElement();
/*  94 */             descriptorSet.setParent(parent);
/*  95 */             parent.addChild(descriptorSet);
/*     */           }
/*     */           
/*     */ 
/*  99 */           this.m_commandDescriptorSets.add(descriptorSet);
/*     */         }
/* 101 */         else if (qName.equals("command"))
/*     */         {
/* 103 */           String classAttribute = attributes.getValue("class");
/*     */           try {
/* 105 */             Class instanceClass = getClass().getClassLoader().loadClass(classAttribute);
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 110 */             if (!this.m_commandDescriptorSets.isEmpty()) {
/* 111 */               CommandDescriptor descriptor = new CommandDescriptor(cmdRegex, argsRegex, instanceClass);
/* 112 */               if (name != null) {
/* 113 */                 descriptor.setName(name);
/*     */               }
/* 115 */               if (level != null) {
/* 116 */                 descriptor.setLevel(Byte.valueOf(level).byteValue());
/*     */               }
/* 118 */               ((CommandDescriptorSet)this.m_commandDescriptorSets.lastElement()).addChild(descriptor);
/*     */             }
/*     */           } catch (ClassNotFoundException e) {
/* 121 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void endElement(String uri, String localName, String qName)
/*     */       throws SAXException
/*     */     {
/* 137 */       if (qName.equals("commandSet"))
/*     */       {
/* 139 */         this.m_commandDescriptorSets.pop();
/*     */       }
/*     */     }
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
/*     */     public void endDocument()
/*     */       throws SAXException
/*     */     {}
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
/*     */   public CommandDescriptorSet()
/*     */   {
/* 168 */     this("", "");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CommandDescriptorSet(String cmdRegex, String argsRegex)
/*     */   {
/* 178 */     super(cmdRegex, argsRegex);
/* 179 */     this.m_children = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean addCommandListFromXmlFile(URL url)
/*     */   {
/* 191 */     SAXParserFactory factory = SAXParserFactory.newInstance();
/*     */     try {
/* 193 */       SAXParser saxParser = factory.newSAXParser();
/* 194 */       DescriptorHandler handler = new DescriptorHandler(this);
/* 195 */       saxParser.parse(url.openStream(), handler);
/* 196 */       return true;
/*     */     } catch (Exception e) {
/* 198 */       e.printStackTrace();
/*     */     }
/* 200 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<CommandPattern> getChildren()
/*     */   {
/* 207 */     return this.m_children;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addChild(CommandPattern commandPattern)
/*     */   {
/* 214 */     this.m_children.add(commandPattern);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void setParent(CommandDescriptorSet parent)
/*     */   {
/* 221 */     this.m_parent = parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CommandDescriptorSet getParent()
/*     */   {
/* 228 */     return this.m_parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRoot()
/*     */   {
/* 236 */     return this.m_parent == null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public CommandDescriptorSet getRoot()
/*     */   {
/* 243 */     if (isRoot()) {
/* 244 */       return this;
/*     */     }
/* 246 */     return getParent().getRoot();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 253 */     StringBuilder builder = new StringBuilder();
/* 254 */     if (this.m_parent != null) {
/* 255 */       builder.append(getParent().getPath());
/*     */     }
/* 257 */     return getName() + "/";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<CommandPattern> getMatchesCommandPatterns(String input, byte level)
/*     */   {
/* 266 */     ArrayList<CommandPattern> matchesDescriptors = new ArrayList();
/* 267 */     for (CommandPattern descriptor : this.m_children) {
/* 268 */       if (descriptor.getLevel() <= level) {
/* 269 */         Matcher matcher = descriptor.getCmdPattern().matcher(input);
/* 270 */         if (matcher.matches()) {
/* 271 */           matchesDescriptors.add(descriptor);
/*     */         }
/*     */       }
/*     */     }
/* 275 */     return matchesDescriptors;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArrayList<String> getChildrenNamesList()
/*     */   {
/* 283 */     ArrayList<String> names = new ArrayList();
/* 284 */     for (CommandPattern commandPattern : this.m_children) {
/* 285 */       names.add(commandPattern.getName());
/*     */     }
/* 287 */     return names;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Command createInstance()
/*     */   {
/* 297 */     return new NavigateToCommandSetCommand(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\command\descriptors\CommandDescriptorSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */