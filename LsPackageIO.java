package test;

import com.stenway.sml.SmlAttribute;
import com.stenway.sml.SmlDocument;
import com.stenway.sml.SmlElement;
import com.stenway.sml.SmlEmptyNode;
import com.stenway.sml.SmlNamedNode;
import com.stenway.sml.SmlNode;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.io.IOException;

class LimaScriptPackageException extends RuntimeException {
	public LimaScriptPackageException(String message) {
		super(message);
	}
}

class SchemaUtils {
	public static void assureOptionalElement(SmlElement element, String elementName) {
		int count = element.elements(elementName).length;
		if (count == 0 || count == 1) {
			return;
		}
		throw new LimaScriptPackageException("Element \""+elementName+"\" is optional but was found "+count+" times in element \""+element.getName()+"\"");
	}
	
	public static void assureRequiredElement(SmlElement element, String elementName) {
		int count = element.elements(elementName).length;
		if (count == 1) {
			return;
		}
		throw new LimaScriptPackageException("Element \""+elementName+"\" is required but was found "+count+" times in element \""+element.getName()+"\"");
	}
	
	public static void assureRepeatedPlusElement(SmlElement element, String elementName) {
		int count = element.elements(elementName).length;
		if (count >= 1) {
			return;
		}
		throw new LimaScriptPackageException("Element \""+elementName+"\" is required but was found "+count+" times in element \""+element.getName()+"\"");
	}
	
	public static void assureOptionalAttribute(SmlElement element, String attributeName) {
		int count = element.attributes(attributeName).length;
		if (count == 0 || count == 1) {
			return;
		}
		throw new LimaScriptPackageException("Attribute \""+attributeName+"\" is optional but was found "+count+" times in element \""+element.getName()+"\"");
	}
	
	public static void assureRequiredAttribute(SmlElement element, String attributeName) {
		int count = element.attributes(attributeName).length;
		if (count == 1) {
			return;
		}
		throw new LimaScriptPackageException("Attribute \""+attributeName+"\" is required but was found "+count+" times in element \""+element.getName()+"\"");
	}
	
	public static void assureRepeatedPlusAttribute(SmlElement element, String attributeName) {
		int count = element.attributes(attributeName).length;
		if (count >= 1) {
			return;
		}
		throw new LimaScriptPackageException("Attribute \""+attributeName+"\" is required but was found "+count+" times in element \""+element.getName()+"\"");
	}
	
}

class ValueTypeUtils {
	public static ControlType parseControlType(String value) {
		if (value.equalsIgnoreCase("Break")) { return ControlType.BREAK; }
		else if (value.equalsIgnoreCase("Continue")) { return ControlType.CONTINUE; }
		throw new LimaScriptPackageException("Value \""+value+"\" is not a valid ControlType");
	}
	
	public static String getControlTypeString(ControlType value) {
		if (value == ControlType.BREAK) { return "Break"; }
		else if (value == ControlType.CONTINUE) { return "Continue"; }
		throw new LimaScriptPackageException("Invalid ControlType argument");
	}
	
	public static ClassType parseClassType(String value) {
		if (value.equalsIgnoreCase("Abstract")) { return ClassType.ABSTRACT; }
		else if (value.equalsIgnoreCase("Static")) { return ClassType.STATIC; }
		throw new LimaScriptPackageException("Value \""+value+"\" is not a valid ClassType");
	}
	
	public static String getClassTypeString(ClassType value) {
		if (value == ClassType.ABSTRACT) { return "Abstract"; }
		else if (value == ClassType.STATIC) { return "Static"; }
		throw new LimaScriptPackageException("Invalid ClassType argument");
	}
	
	public static ImplementationType parseImplementationType(String value) {
		if (value.equalsIgnoreCase("Abstract")) { return ImplementationType.ABSTRACT; }
		else if (value.equalsIgnoreCase("Native")) { return ImplementationType.NATIVE; }
		throw new LimaScriptPackageException("Value \""+value+"\" is not a valid ImplementationType");
	}
	
	public static String getImplementationTypeString(ImplementationType value) {
		if (value == ImplementationType.ABSTRACT) { return "Abstract"; }
		else if (value == ImplementationType.NATIVE) { return "Native"; }
		throw new LimaScriptPackageException("Invalid ImplementationType argument");
	}
	
	public static OverrideType parseOverrideType(String value) {
		if (value.equalsIgnoreCase("Abstract")) { return OverrideType.ABSTRACT; }
		else if (value.equalsIgnoreCase("Overridable")) { return OverrideType.OVERRIDABLE; }
		else if (value.equalsIgnoreCase("Override")) { return OverrideType.OVERRIDE; }
		else if (value.equalsIgnoreCase("ForceOverride")) { return OverrideType.FORCEOVERRIDE; }
		else if (value.equalsIgnoreCase("Static")) { return OverrideType.STATIC; }
		throw new LimaScriptPackageException("Value \""+value+"\" is not a valid OverrideType");
	}
	
	public static String getOverrideTypeString(OverrideType value) {
		if (value == OverrideType.ABSTRACT) { return "Abstract"; }
		else if (value == OverrideType.OVERRIDABLE) { return "Overridable"; }
		else if (value == OverrideType.OVERRIDE) { return "Override"; }
		else if (value == OverrideType.FORCEOVERRIDE) { return "ForceOverride"; }
		else if (value == OverrideType.STATIC) { return "Static"; }
		throw new LimaScriptPackageException("Invalid OverrideType argument");
	}
	
}

class LimaScriptPackage {
	public Dependencies Dependencies;
	
	public boolean hasDependencies() {
		return this.Dependencies != null;
	}
	
	public ArrayList<Namespace> NamespaceList = new ArrayList<>();
	
	public static LimaScriptPackage parse(SmlElement element) {
		if (!element.hasName("LimaScriptPackage")) {
			throw new LimaScriptPackageException("Element with name \"LimaScriptPackage\" was expected, but found \""+element.getName()+"\"");
		}
		LimaScriptPackage result = new LimaScriptPackage();
		SchemaUtils.assureOptionalElement(element, "Dependencies");
		if (element.hasElement("Dependencies")) {
			result.Dependencies = test.Dependencies.parse(element.element("Dependencies"));
		}
		for (SmlElement childElement: element.elements("Namespace")) {
			result.NamespaceList.add(Namespace.parse(childElement));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("LimaScriptPackage");
		if (this.hasDependencies()) {
			result.add(this.Dependencies.toElement());
		}
		for (Namespace listValue : this.NamespaceList) {
			result.add(listValue.toElement());
		}
		return result;
	}
	
	public static LimaScriptPackage load(String filePath) throws IOException {
		SmlDocument document = SmlDocument.load(filePath);
		return parse(document.getRoot());
	}
	
	public void save(String filePath) throws IOException {
		SmlElement rootElement = this.toElement();
		SmlDocument document = new SmlDocument(rootElement);
		document.save(filePath);
	}
}

class Dependencies {
	public ArrayList<String> DependencyList = new ArrayList<>();
	
	public static Dependencies parse(SmlElement element) {
		if (!element.hasName("Dependencies")) {
			throw new LimaScriptPackageException("Element with name \"Dependencies\" was expected, but found \""+element.getName()+"\"");
		}
		Dependencies result = new Dependencies();
		for (SmlAttribute attribute : element.attributes("Dependency")) {
			result.DependencyList.add(attribute.getString());
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Dependencies");
		for (String listValue : this.DependencyList) {
			result.add(new SmlAttribute("Dependency", listValue));
		}
		return result;
	}
	
}

class Namespace {
	public String Name;
	
	public boolean hasName() {
		return this.Name != null;
	}
	
	public ArrayList<Object> Entities = new ArrayList<>();
	
	public static Namespace parse(SmlElement element) {
		if (!element.hasName("Namespace")) {
			throw new LimaScriptPackageException("Element with name \"Namespace\" was expected, but found \""+element.getName()+"\"");
		}
		Namespace result = new Namespace();
		SchemaUtils.assureOptionalAttribute(element, "Name");
		if (element.hasAttribute("Name")) {
			SmlAttribute attribute = element.attribute("Name");
			result.Name = attribute.getString();
		}
		for (SmlNode childNode : element.Nodes) {
			if (childNode instanceof SmlElement ) {
				SmlElement childElement = (SmlElement)childNode;
				if (childElement.hasName("Class")) { result.Entities.add(Class.parse(childElement)); }
				else if (childElement.hasName("Enum")) { result.Entities.add(Enum.parse(childElement)); }
				else if (childElement.hasName("Interface")) { result.Entities.add(Interface.parse(childElement)); }
			} else if (childNode instanceof SmlAttribute) {
				SmlAttribute attribute = (SmlAttribute)childNode;
			}
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Namespace");
		if (this.hasName()) {
			result.add(new SmlAttribute("Name", (String)this.Name));
		}
		for (Object listValue : this.Entities) {
			if (listValue instanceof AbstractMap.SimpleEntry) {
				AbstractMap.SimpleEntry entry = (AbstractMap.SimpleEntry)listValue;
				String attributeName = (String)entry.getKey();
				Object attributeObj = entry.getValue();
			} else {
				if (listValue instanceof Class) { result.add(((Class)listValue).toElement()); }
				else if (listValue instanceof Enum) { result.add(((Enum)listValue).toElement()); }
				else if (listValue instanceof Interface) { result.add(((Interface)listValue).toElement()); }
				else { throw new LimaScriptPackageException("Unknown child list item type"); }
			}
		}
		return result;
	}
	
}

class Params {
	public ArrayList<String> ParamList = new ArrayList<>();
	
	public String VarArgParam;
	
	public boolean hasVarArgParam() {
		return this.VarArgParam != null;
	}
	
	public static Params parse(SmlElement element) {
		if (!element.hasName("Params")) {
			throw new LimaScriptPackageException("Element with name \"Params\" was expected, but found \""+element.getName()+"\"");
		}
		Params result = new Params();
		for (SmlAttribute attribute : element.attributes("Param")) {
			result.ParamList.add(attribute.getString());
		}
		SchemaUtils.assureOptionalAttribute(element, "VarArgParam");
		if (element.hasAttribute("VarArgParam")) {
			SmlAttribute attribute = element.attribute("VarArgParam");
			result.VarArgParam = attribute.getString();
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Params");
		for (String listValue : this.ParamList) {
			result.add(new SmlAttribute("Param", listValue));
		}
		if (this.hasVarArgParam()) {
			result.add(new SmlAttribute("VarArgParam", (String)this.VarArgParam));
		}
		return result;
	}
	
}

class Begin {
	public ArrayList<Object> Statements = new ArrayList<>();
	
	public static Begin parse(SmlElement element) {
		if (!element.hasName("Begin")) {
			throw new LimaScriptPackageException("Element with name \"Begin\" was expected, but found \""+element.getName()+"\"");
		}
		Begin result = new Begin();
		for (SmlNode childNode : element.Nodes) {
			if (childNode instanceof SmlElement ) {
				SmlElement childElement = (SmlElement)childNode;
				if (childElement.hasName("If")) { result.Statements.add(If.parse(childElement)); }
				else if (childElement.hasName("For")) { result.Statements.add(For.parse(childElement)); }
				else if (childElement.hasName("Foreach")) { result.Statements.add(Foreach.parse(childElement)); }
				else if (childElement.hasName("Loop")) { result.Statements.add(Loop.parse(childElement)); }
				else if (childElement.hasName("Switch")) { result.Statements.add(Switch.parse(childElement)); }
				else if (childElement.hasName("Try")) { result.Statements.add(Try.parse(childElement)); }
			} else if (childNode instanceof SmlAttribute) {
				SmlAttribute attribute = (SmlAttribute)childNode;
				if (attribute.hasName("Expression")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Expression", attribute.getValues())); }
				else if (attribute.hasName("DefV")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("DefV", attribute.getValues())); }
				else if (attribute.hasName("SetF")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetF", attribute.getValues())); }
				else if (attribute.hasName("SetP")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetP", attribute.getValues())); }
				else if (attribute.hasName("SetV")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetV", attribute.getValues())); }
				else if (attribute.hasName("SetParam")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetParam", attribute.getValues())); }
				else if (attribute.hasName("Control")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Control", ValueTypeUtils.parseControlType(attribute.getString()))); }
				else if (attribute.hasName("Return")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Return", attribute.getValues())); }
				else if (attribute.hasName("Throw")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Throw", attribute.getValues())); }
			}
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Begin");
		for (Object listValue : this.Statements) {
			if (listValue instanceof AbstractMap.SimpleEntry) {
				AbstractMap.SimpleEntry entry = (AbstractMap.SimpleEntry)listValue;
				String attributeName = (String)entry.getKey();
				Object attributeObj = entry.getValue();
				if (attributeName.equalsIgnoreCase("Expression")) { result.add(new SmlAttribute("Expression", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("DefV")) { result.add(new SmlAttribute("DefV", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetF")) { result.add(new SmlAttribute("SetF", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetP")) { result.add(new SmlAttribute("SetP", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetV")) { result.add(new SmlAttribute("SetV", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetParam")) { result.add(new SmlAttribute("SetParam", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("Control")) { result.add(new SmlAttribute("Control", ValueTypeUtils.getControlTypeString((ControlType)attributeObj))); }
				else if (attributeName.equalsIgnoreCase("Return")) { result.add(new SmlAttribute("Return", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("Throw")) { result.add(new SmlAttribute("Throw", (String[])attributeObj)); }
			} else {
				if (listValue instanceof If) { result.add(((If)listValue).toElement()); }
				else if (listValue instanceof For) { result.add(((For)listValue).toElement()); }
				else if (listValue instanceof Foreach) { result.add(((Foreach)listValue).toElement()); }
				else if (listValue instanceof Loop) { result.add(((Loop)listValue).toElement()); }
				else if (listValue instanceof Switch) { result.add(((Switch)listValue).toElement()); }
				else if (listValue instanceof Try) { result.add(((Try)listValue).toElement()); }
				else { throw new LimaScriptPackageException("Unknown child list item type"); }
			}
		}
		return result;
	}
	
}

class If {
	public ArrayList<String[]> CondList = new ArrayList<>();
	
	public ArrayList<Begin> BeginList = new ArrayList<>();
	
	public static If parse(SmlElement element) {
		if (!element.hasName("If")) {
			throw new LimaScriptPackageException("Element with name \"If\" was expected, but found \""+element.getName()+"\"");
		}
		If result = new If();
		SchemaUtils.assureRepeatedPlusAttribute(element, "Cond");
		for (SmlAttribute attribute : element.attributes("Cond")) {
			result.CondList.add(attribute.getValues());
		}
		SchemaUtils.assureRepeatedPlusElement(element, "Begin");
		for (SmlElement childElement: element.elements("Begin")) {
			result.BeginList.add(Begin.parse(childElement));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("If");
		if (this.CondList.size() == 0) { throw new LimaScriptPackageException("'CondList' must have at least one value"); }
		for (String[] listValue : this.CondList) {
			result.add(new SmlAttribute("Cond", listValue));
		}
		if (this.BeginList.size() == 0) { throw new LimaScriptPackageException("'BeginList' must have at least one value"); }
		for (Begin listValue : this.BeginList) {
			result.add(listValue.toElement());
		}
		return result;
	}
	
}

class For {
	public String[] DefV;
	
	public String[] While;
	
	public String[] SetV;
	
	public boolean hasSetV() {
		return this.SetV != null;
	}
	
	public String[] SetF;
	
	public boolean hasSetF() {
		return this.SetF != null;
	}
	
	public String[] SetP;
	
	public boolean hasSetP() {
		return this.SetP != null;
	}
	
	public String[] SetParam;
	
	public boolean hasSetParam() {
		return this.SetParam != null;
	}
	
	public Begin Begin = new Begin();
	
	public static For parse(SmlElement element) {
		if (!element.hasName("For")) {
			throw new LimaScriptPackageException("Element with name \"For\" was expected, but found \""+element.getName()+"\"");
		}
		For result = new For();
		{
			SchemaUtils.assureRequiredAttribute(element, "DefV");
			SmlAttribute attribute = element.attribute("DefV");
			result.DefV = attribute.getValues();
		}
		{
			SchemaUtils.assureRequiredAttribute(element, "While");
			SmlAttribute attribute = element.attribute("While");
			result.While = attribute.getValues();
		}
		SchemaUtils.assureOptionalAttribute(element, "SetV");
		if (element.hasAttribute("SetV")) {
			SmlAttribute attribute = element.attribute("SetV");
			result.SetV = attribute.getValues();
		}
		SchemaUtils.assureOptionalAttribute(element, "SetF");
		if (element.hasAttribute("SetF")) {
			SmlAttribute attribute = element.attribute("SetF");
			result.SetF = attribute.getValues();
		}
		SchemaUtils.assureOptionalAttribute(element, "SetP");
		if (element.hasAttribute("SetP")) {
			SmlAttribute attribute = element.attribute("SetP");
			result.SetP = attribute.getValues();
		}
		SchemaUtils.assureOptionalAttribute(element, "SetParam");
		if (element.hasAttribute("SetParam")) {
			SmlAttribute attribute = element.attribute("SetParam");
			result.SetParam = attribute.getValues();
		}
		SchemaUtils.assureRequiredElement(element, "Begin");
		{
			result.Begin = test.Begin.parse(element.element("Begin"));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("For");
		if (this.DefV == null) {
			throw new LimaScriptPackageException("Field 'DefV' in class 'For' is null");
		}
		result.add(new SmlAttribute("DefV", (String[])this.DefV));
		if (this.While == null) {
			throw new LimaScriptPackageException("Field 'While' in class 'For' is null");
		}
		result.add(new SmlAttribute("While", (String[])this.While));
		if (this.hasSetV()) {
			result.add(new SmlAttribute("SetV", (String[])this.SetV));
		}
		if (this.hasSetF()) {
			result.add(new SmlAttribute("SetF", (String[])this.SetF));
		}
		if (this.hasSetP()) {
			result.add(new SmlAttribute("SetP", (String[])this.SetP));
		}
		if (this.hasSetParam()) {
			result.add(new SmlAttribute("SetParam", (String[])this.SetParam));
		}
		if (this.Begin == null) {
			throw new LimaScriptPackageException("Field 'Begin' in class 'For' is null");
		}
		result.add(this.Begin.toElement());
		return result;
	}
	
}

class Foreach {
	public String Var;
	
	public String[] In;
	
	public Begin Begin = new Begin();
	
	public static Foreach parse(SmlElement element) {
		if (!element.hasName("Foreach")) {
			throw new LimaScriptPackageException("Element with name \"Foreach\" was expected, but found \""+element.getName()+"\"");
		}
		Foreach result = new Foreach();
		{
			SchemaUtils.assureRequiredAttribute(element, "Var");
			SmlAttribute attribute = element.attribute("Var");
			result.Var = attribute.getString();
		}
		{
			SchemaUtils.assureRequiredAttribute(element, "In");
			SmlAttribute attribute = element.attribute("In");
			result.In = attribute.getValues();
		}
		SchemaUtils.assureRequiredElement(element, "Begin");
		{
			result.Begin = test.Begin.parse(element.element("Begin"));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Foreach");
		if (this.Var == null) {
			throw new LimaScriptPackageException("Field 'Var' in class 'Foreach' is null");
		}
		result.add(new SmlAttribute("Var", (String)this.Var));
		if (this.In == null) {
			throw new LimaScriptPackageException("Field 'In' in class 'Foreach' is null");
		}
		result.add(new SmlAttribute("In", (String[])this.In));
		if (this.Begin == null) {
			throw new LimaScriptPackageException("Field 'Begin' in class 'Foreach' is null");
		}
		result.add(this.Begin.toElement());
		return result;
	}
	
}

class Loop {
	public String[] Cond;
	
	public boolean hasCond() {
		return this.Cond != null;
	}
	
	public String[] Until;
	
	public boolean hasUntil() {
		return this.Until != null;
	}
	
	public String[] DoWhile;
	
	public boolean hasDoWhile() {
		return this.DoWhile != null;
	}
	
	public String DoUntil;
	
	public boolean hasDoUntil() {
		return this.DoUntil != null;
	}
	
	public Begin Begin = new Begin();
	
	public static Loop parse(SmlElement element) {
		if (!element.hasName("Loop")) {
			throw new LimaScriptPackageException("Element with name \"Loop\" was expected, but found \""+element.getName()+"\"");
		}
		Loop result = new Loop();
		SchemaUtils.assureOptionalAttribute(element, "Cond");
		if (element.hasAttribute("Cond")) {
			SmlAttribute attribute = element.attribute("Cond");
			result.Cond = attribute.getValues();
		}
		SchemaUtils.assureOptionalAttribute(element, "Until");
		if (element.hasAttribute("Until")) {
			SmlAttribute attribute = element.attribute("Until");
			result.Until = attribute.getValues();
		}
		SchemaUtils.assureOptionalAttribute(element, "DoWhile");
		if (element.hasAttribute("DoWhile")) {
			SmlAttribute attribute = element.attribute("DoWhile");
			result.DoWhile = attribute.getValues();
		}
		SchemaUtils.assureOptionalAttribute(element, "DoUntil");
		if (element.hasAttribute("DoUntil")) {
			SmlAttribute attribute = element.attribute("DoUntil");
			result.DoUntil = attribute.getString();
		}
		SchemaUtils.assureRequiredElement(element, "Begin");
		{
			result.Begin = test.Begin.parse(element.element("Begin"));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Loop");
		if (this.hasCond()) {
			result.add(new SmlAttribute("Cond", (String[])this.Cond));
		}
		if (this.hasUntil()) {
			result.add(new SmlAttribute("Until", (String[])this.Until));
		}
		if (this.hasDoWhile()) {
			result.add(new SmlAttribute("DoWhile", (String[])this.DoWhile));
		}
		if (this.hasDoUntil()) {
			result.add(new SmlAttribute("DoUntil", (String)this.DoUntil));
		}
		if (this.Begin == null) {
			throw new LimaScriptPackageException("Field 'Begin' in class 'Loop' is null");
		}
		result.add(this.Begin.toElement());
		return result;
	}
	
}

class Switch {
	public String[] Expression;
	
	public ArrayList<String[]> CaseList = new ArrayList<>();
	
	public ArrayList<Begin> BeginList = new ArrayList<>();
	
	public static Switch parse(SmlElement element) {
		if (!element.hasName("Switch")) {
			throw new LimaScriptPackageException("Element with name \"Switch\" was expected, but found \""+element.getName()+"\"");
		}
		Switch result = new Switch();
		{
			SchemaUtils.assureRequiredAttribute(element, "Expression");
			SmlAttribute attribute = element.attribute("Expression");
			result.Expression = attribute.getValues();
		}
		SchemaUtils.assureRepeatedPlusAttribute(element, "Case");
		for (SmlAttribute attribute : element.attributes("Case")) {
			result.CaseList.add(attribute.getValues());
		}
		SchemaUtils.assureRepeatedPlusElement(element, "Begin");
		for (SmlElement childElement: element.elements("Begin")) {
			result.BeginList.add(Begin.parse(childElement));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Switch");
		if (this.Expression == null) {
			throw new LimaScriptPackageException("Field 'Expression' in class 'Switch' is null");
		}
		result.add(new SmlAttribute("Expression", (String[])this.Expression));
		if (this.CaseList.size() == 0) { throw new LimaScriptPackageException("'CaseList' must have at least one value"); }
		for (String[] listValue : this.CaseList) {
			result.add(new SmlAttribute("Case", listValue));
		}
		if (this.BeginList.size() == 0) { throw new LimaScriptPackageException("'BeginList' must have at least one value"); }
		for (Begin listValue : this.BeginList) {
			result.add(listValue.toElement());
		}
		return result;
	}
	
}

class Try {
	public String[] Catch;
	
	public boolean hasCatch() {
		return this.Catch != null;
	}
	
	public ArrayList<Begin> BeginList = new ArrayList<>();
	
	public static Try parse(SmlElement element) {
		if (!element.hasName("Try")) {
			throw new LimaScriptPackageException("Element with name \"Try\" was expected, but found \""+element.getName()+"\"");
		}
		Try result = new Try();
		SchemaUtils.assureOptionalAttribute(element, "Catch");
		if (element.hasAttribute("Catch")) {
			SmlAttribute attribute = element.attribute("Catch");
			result.Catch = attribute.getValues();
		}
		SchemaUtils.assureRepeatedPlusElement(element, "Begin");
		for (SmlElement childElement: element.elements("Begin")) {
			result.BeginList.add(Begin.parse(childElement));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Try");
		if (this.hasCatch()) {
			result.add(new SmlAttribute("Catch", (String[])this.Catch));
		}
		if (this.BeginList.size() == 0) { throw new LimaScriptPackageException("'BeginList' must have at least one value"); }
		for (Begin listValue : this.BeginList) {
			result.add(listValue.toElement());
		}
		return result;
	}
	
}

class Class {
	public String Name;
	
	public Boolean Internal;
	
	public boolean hasInternal() {
		return this.Internal != null;
	}
	
	public Boolean Native;
	
	public boolean hasNative() {
		return this.Native != null;
	}
	
	public ClassType Type;
	
	public boolean hasType() {
		return this.Type != null;
	}
	
	public String Extends;
	
	public boolean hasExtends() {
		return this.Extends != null;
	}
	
	public ArrayList<String> ImplementsList = new ArrayList<>();
	
	public Constructor Constructor;
	
	public boolean hasConstructor() {
		return this.Constructor != null;
	}
	
	public ArrayList<Field> FieldList = new ArrayList<>();
	
	public ArrayList<Method> MethodList = new ArrayList<>();
	
	public ArrayList<Property> PropertyList = new ArrayList<>();
	
	public static Class parse(SmlElement element) {
		if (!element.hasName("Class")) {
			throw new LimaScriptPackageException("Element with name \"Class\" was expected, but found \""+element.getName()+"\"");
		}
		Class result = new Class();
		{
			SchemaUtils.assureRequiredAttribute(element, "Name");
			SmlAttribute attribute = element.attribute("Name");
			result.Name = attribute.getString();
		}
		SchemaUtils.assureOptionalAttribute(element, "Internal");
		if (element.hasAttribute("Internal")) {
			SmlAttribute attribute = element.attribute("Internal");
			result.Internal = attribute.getBoolean();
		}
		SchemaUtils.assureOptionalAttribute(element, "Native");
		if (element.hasAttribute("Native")) {
			SmlAttribute attribute = element.attribute("Native");
			result.Native = attribute.getBoolean();
		}
		SchemaUtils.assureOptionalAttribute(element, "Type");
		if (element.hasAttribute("Type")) {
			SmlAttribute attribute = element.attribute("Type");
			result.Type = ValueTypeUtils.parseClassType(attribute.getString());
		}
		SchemaUtils.assureOptionalAttribute(element, "Extends");
		if (element.hasAttribute("Extends")) {
			SmlAttribute attribute = element.attribute("Extends");
			result.Extends = attribute.getString();
		}
		for (SmlAttribute attribute : element.attributes("Implements")) {
			result.ImplementsList.add(attribute.getString());
		}
		SchemaUtils.assureOptionalElement(element, "Constructor");
		if (element.hasElement("Constructor")) {
			result.Constructor = test.Constructor.parse(element.element("Constructor"));
		}
		for (SmlElement childElement: element.elements("Field")) {
			result.FieldList.add(Field.parse(childElement));
		}
		for (SmlElement childElement: element.elements("Method")) {
			result.MethodList.add(Method.parse(childElement));
		}
		for (SmlElement childElement: element.elements("Property")) {
			result.PropertyList.add(Property.parse(childElement));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Class");
		if (this.Name == null) {
			throw new LimaScriptPackageException("Field 'Name' in class 'Class' is null");
		}
		result.add(new SmlAttribute("Name", (String)this.Name));
		if (this.hasInternal()) {
			result.add(new SmlAttribute("Internal", (Boolean)this.Internal));
		}
		if (this.hasNative()) {
			result.add(new SmlAttribute("Native", (Boolean)this.Native));
		}
		if (this.hasType()) {
			result.add(new SmlAttribute("Type", ValueTypeUtils.getClassTypeString((ClassType)this.Type)));
		}
		if (this.hasExtends()) {
			result.add(new SmlAttribute("Extends", (String)this.Extends));
		}
		for (String listValue : this.ImplementsList) {
			result.add(new SmlAttribute("Implements", listValue));
		}
		if (this.hasConstructor()) {
			result.add(this.Constructor.toElement());
		}
		for (Field listValue : this.FieldList) {
			result.add(listValue.toElement());
		}
		for (Method listValue : this.MethodList) {
			result.add(listValue.toElement());
		}
		for (Property listValue : this.PropertyList) {
			result.add(listValue.toElement());
		}
		return result;
	}
	
}

class Constructor {
	public String Name;
	
	public Boolean Native;
	
	public boolean hasNative() {
		return this.Native != null;
	}
	
	public Params Params = new Params();
	
	public Begin Begin;
	
	public boolean hasBegin() {
		return this.Begin != null;
	}
	
	public static Constructor parse(SmlElement element) {
		if (!element.hasName("Constructor")) {
			throw new LimaScriptPackageException("Element with name \"Constructor\" was expected, but found \""+element.getName()+"\"");
		}
		Constructor result = new Constructor();
		{
			SchemaUtils.assureRequiredAttribute(element, "Name");
			SmlAttribute attribute = element.attribute("Name");
			result.Name = attribute.getString();
		}
		SchemaUtils.assureOptionalAttribute(element, "Native");
		if (element.hasAttribute("Native")) {
			SmlAttribute attribute = element.attribute("Native");
			result.Native = attribute.getBoolean();
		}
		SchemaUtils.assureRequiredElement(element, "Params");
		{
			result.Params = test.Params.parse(element.element("Params"));
		}
		SchemaUtils.assureOptionalElement(element, "Begin");
		if (element.hasElement("Begin")) {
			result.Begin = test.Begin.parse(element.element("Begin"));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Constructor");
		if (this.Name == null) {
			throw new LimaScriptPackageException("Field 'Name' in class 'Constructor' is null");
		}
		result.add(new SmlAttribute("Name", (String)this.Name));
		if (this.hasNative()) {
			result.add(new SmlAttribute("Native", (Boolean)this.Native));
		}
		if (this.Params == null) {
			throw new LimaScriptPackageException("Field 'Params' in class 'Constructor' is null");
		}
		result.add(this.Params.toElement());
		if (this.hasBegin()) {
			result.add(this.Begin.toElement());
		}
		return result;
	}
	
}

class Field {
	public String Name;
	
	public Boolean Static;
	
	public boolean hasStatic() {
		return this.Static != null;
	}
	
	public Boolean ReadOnly;
	
	public boolean hasReadOnly() {
		return this.ReadOnly != null;
	}
	
	public String[] ConstValue;
	
	public boolean hasConstValue() {
		return this.ConstValue != null;
	}
	
	public static Field parse(SmlElement element) {
		if (!element.hasName("Field")) {
			throw new LimaScriptPackageException("Element with name \"Field\" was expected, but found \""+element.getName()+"\"");
		}
		Field result = new Field();
		{
			SchemaUtils.assureRequiredAttribute(element, "Name");
			SmlAttribute attribute = element.attribute("Name");
			result.Name = attribute.getString();
		}
		SchemaUtils.assureOptionalAttribute(element, "Static");
		if (element.hasAttribute("Static")) {
			SmlAttribute attribute = element.attribute("Static");
			result.Static = attribute.getBoolean();
		}
		SchemaUtils.assureOptionalAttribute(element, "ReadOnly");
		if (element.hasAttribute("ReadOnly")) {
			SmlAttribute attribute = element.attribute("ReadOnly");
			result.ReadOnly = attribute.getBoolean();
		}
		SchemaUtils.assureOptionalAttribute(element, "ConstValue");
		if (element.hasAttribute("ConstValue")) {
			SmlAttribute attribute = element.attribute("ConstValue");
			result.ConstValue = attribute.getValues();
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Field");
		if (this.Name == null) {
			throw new LimaScriptPackageException("Field 'Name' in class 'Field' is null");
		}
		result.add(new SmlAttribute("Name", (String)this.Name));
		if (this.hasStatic()) {
			result.add(new SmlAttribute("Static", (Boolean)this.Static));
		}
		if (this.hasReadOnly()) {
			result.add(new SmlAttribute("ReadOnly", (Boolean)this.ReadOnly));
		}
		if (this.hasConstValue()) {
			result.add(new SmlAttribute("ConstValue", (String[])this.ConstValue));
		}
		return result;
	}
	
}

class Method {
	public String Name;
	
	public Boolean Native;
	
	public boolean hasNative() {
		return this.Native != null;
	}
	
	public OverrideType Type;
	
	public boolean hasType() {
		return this.Type != null;
	}
	
	public Params Params = new Params();
	
	public Begin Begin = new Begin();
	
	public static Method parse(SmlElement element) {
		if (!element.hasName("Method")) {
			throw new LimaScriptPackageException("Element with name \"Method\" was expected, but found \""+element.getName()+"\"");
		}
		Method result = new Method();
		{
			SchemaUtils.assureRequiredAttribute(element, "Name");
			SmlAttribute attribute = element.attribute("Name");
			result.Name = attribute.getString();
		}
		SchemaUtils.assureOptionalAttribute(element, "Native");
		if (element.hasAttribute("Native")) {
			SmlAttribute attribute = element.attribute("Native");
			result.Native = attribute.getBoolean();
		}
		SchemaUtils.assureOptionalAttribute(element, "Type");
		if (element.hasAttribute("Type")) {
			SmlAttribute attribute = element.attribute("Type");
			result.Type = ValueTypeUtils.parseOverrideType(attribute.getString());
		}
		SchemaUtils.assureRequiredElement(element, "Params");
		{
			result.Params = test.Params.parse(element.element("Params"));
		}
		SchemaUtils.assureRequiredElement(element, "Begin");
		{
			result.Begin = test.Begin.parse(element.element("Begin"));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Method");
		if (this.Name == null) {
			throw new LimaScriptPackageException("Field 'Name' in class 'Method' is null");
		}
		result.add(new SmlAttribute("Name", (String)this.Name));
		if (this.hasNative()) {
			result.add(new SmlAttribute("Native", (Boolean)this.Native));
		}
		if (this.hasType()) {
			result.add(new SmlAttribute("Type", ValueTypeUtils.getOverrideTypeString((OverrideType)this.Type)));
		}
		if (this.Params == null) {
			throw new LimaScriptPackageException("Field 'Params' in class 'Method' is null");
		}
		result.add(this.Params.toElement());
		if (this.Begin == null) {
			throw new LimaScriptPackageException("Field 'Begin' in class 'Method' is null");
		}
		result.add(this.Begin.toElement());
		return result;
	}
	
}

class Property {
	public String Name;
	
	public Boolean Native;
	
	public boolean hasNative() {
		return this.Native != null;
	}
	
	public OverrideType Type;
	
	public boolean hasType() {
		return this.Type != null;
	}
	
	public Get Get = new Get();
	
	public Set Set;
	
	public boolean hasSet() {
		return this.Set != null;
	}
	
	public static Property parse(SmlElement element) {
		if (!element.hasName("Property")) {
			throw new LimaScriptPackageException("Element with name \"Property\" was expected, but found \""+element.getName()+"\"");
		}
		Property result = new Property();
		{
			SchemaUtils.assureRequiredAttribute(element, "Name");
			SmlAttribute attribute = element.attribute("Name");
			result.Name = attribute.getString();
		}
		SchemaUtils.assureOptionalAttribute(element, "Native");
		if (element.hasAttribute("Native")) {
			SmlAttribute attribute = element.attribute("Native");
			result.Native = attribute.getBoolean();
		}
		SchemaUtils.assureOptionalAttribute(element, "Type");
		if (element.hasAttribute("Type")) {
			SmlAttribute attribute = element.attribute("Type");
			result.Type = ValueTypeUtils.parseOverrideType(attribute.getString());
		}
		SchemaUtils.assureRequiredElement(element, "Get");
		{
			result.Get = test.Get.parse(element.element("Get"));
		}
		SchemaUtils.assureOptionalElement(element, "Set");
		if (element.hasElement("Set")) {
			result.Set = test.Set.parse(element.element("Set"));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Property");
		if (this.Name == null) {
			throw new LimaScriptPackageException("Field 'Name' in class 'Property' is null");
		}
		result.add(new SmlAttribute("Name", (String)this.Name));
		if (this.hasNative()) {
			result.add(new SmlAttribute("Native", (Boolean)this.Native));
		}
		if (this.hasType()) {
			result.add(new SmlAttribute("Type", ValueTypeUtils.getOverrideTypeString((OverrideType)this.Type)));
		}
		if (this.Get == null) {
			throw new LimaScriptPackageException("Field 'Get' in class 'Property' is null");
		}
		result.add(this.Get.toElement());
		if (this.hasSet()) {
			result.add(this.Set.toElement());
		}
		return result;
	}
	
}

class Set extends Begin{
	public ArrayList<Object> Statements = new ArrayList<>();
	
	public static Set parse(SmlElement element) {
		if (!element.hasName("Set")) {
			throw new LimaScriptPackageException("Element with name \"Set\" was expected, but found \""+element.getName()+"\"");
		}
		Set result = new Set();
		for (SmlNode childNode : element.Nodes) {
			if (childNode instanceof SmlElement ) {
				SmlElement childElement = (SmlElement)childNode;
				if (childElement.hasName("If")) { result.Statements.add(If.parse(childElement)); }
				else if (childElement.hasName("For")) { result.Statements.add(For.parse(childElement)); }
				else if (childElement.hasName("Foreach")) { result.Statements.add(Foreach.parse(childElement)); }
				else if (childElement.hasName("Loop")) { result.Statements.add(Loop.parse(childElement)); }
				else if (childElement.hasName("Switch")) { result.Statements.add(Switch.parse(childElement)); }
				else if (childElement.hasName("Try")) { result.Statements.add(Try.parse(childElement)); }
			} else if (childNode instanceof SmlAttribute) {
				SmlAttribute attribute = (SmlAttribute)childNode;
				if (attribute.hasName("Expression")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Expression", attribute.getValues())); }
				else if (attribute.hasName("DefV")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("DefV", attribute.getValues())); }
				else if (attribute.hasName("SetF")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetF", attribute.getValues())); }
				else if (attribute.hasName("SetP")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetP", attribute.getValues())); }
				else if (attribute.hasName("SetV")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetV", attribute.getValues())); }
				else if (attribute.hasName("SetParam")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetParam", attribute.getValues())); }
				else if (attribute.hasName("Control")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Control", ValueTypeUtils.parseControlType(attribute.getString()))); }
				else if (attribute.hasName("Return")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Return", attribute.getValues())); }
				else if (attribute.hasName("Throw")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Throw", attribute.getValues())); }
			}
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Set");
		for (Object listValue : this.Statements) {
			if (listValue instanceof AbstractMap.SimpleEntry) {
				AbstractMap.SimpleEntry entry = (AbstractMap.SimpleEntry)listValue;
				String attributeName = (String)entry.getKey();
				Object attributeObj = entry.getValue();
				if (attributeName.equalsIgnoreCase("Expression")) { result.add(new SmlAttribute("Expression", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("DefV")) { result.add(new SmlAttribute("DefV", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetF")) { result.add(new SmlAttribute("SetF", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetP")) { result.add(new SmlAttribute("SetP", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetV")) { result.add(new SmlAttribute("SetV", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetParam")) { result.add(new SmlAttribute("SetParam", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("Control")) { result.add(new SmlAttribute("Control", ValueTypeUtils.getControlTypeString((ControlType)attributeObj))); }
				else if (attributeName.equalsIgnoreCase("Return")) { result.add(new SmlAttribute("Return", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("Throw")) { result.add(new SmlAttribute("Throw", (String[])attributeObj)); }
			} else {
				if (listValue instanceof If) { result.add(((If)listValue).toElement()); }
				else if (listValue instanceof For) { result.add(((For)listValue).toElement()); }
				else if (listValue instanceof Foreach) { result.add(((Foreach)listValue).toElement()); }
				else if (listValue instanceof Loop) { result.add(((Loop)listValue).toElement()); }
				else if (listValue instanceof Switch) { result.add(((Switch)listValue).toElement()); }
				else if (listValue instanceof Try) { result.add(((Try)listValue).toElement()); }
				else { throw new LimaScriptPackageException("Unknown child list item type"); }
			}
		}
		return result;
	}
	
}

class Get extends Begin{
	public ArrayList<Object> Statements = new ArrayList<>();
	
	public static Get parse(SmlElement element) {
		if (!element.hasName("Get")) {
			throw new LimaScriptPackageException("Element with name \"Get\" was expected, but found \""+element.getName()+"\"");
		}
		Get result = new Get();
		for (SmlNode childNode : element.Nodes) {
			if (childNode instanceof SmlElement ) {
				SmlElement childElement = (SmlElement)childNode;
				if (childElement.hasName("If")) { result.Statements.add(If.parse(childElement)); }
				else if (childElement.hasName("For")) { result.Statements.add(For.parse(childElement)); }
				else if (childElement.hasName("Foreach")) { result.Statements.add(Foreach.parse(childElement)); }
				else if (childElement.hasName("Loop")) { result.Statements.add(Loop.parse(childElement)); }
				else if (childElement.hasName("Switch")) { result.Statements.add(Switch.parse(childElement)); }
				else if (childElement.hasName("Try")) { result.Statements.add(Try.parse(childElement)); }
			} else if (childNode instanceof SmlAttribute) {
				SmlAttribute attribute = (SmlAttribute)childNode;
				if (attribute.hasName("Expression")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Expression", attribute.getValues())); }
				else if (attribute.hasName("DefV")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("DefV", attribute.getValues())); }
				else if (attribute.hasName("SetF")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetF", attribute.getValues())); }
				else if (attribute.hasName("SetP")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetP", attribute.getValues())); }
				else if (attribute.hasName("SetV")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetV", attribute.getValues())); }
				else if (attribute.hasName("SetParam")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("SetParam", attribute.getValues())); }
				else if (attribute.hasName("Control")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Control", ValueTypeUtils.parseControlType(attribute.getString()))); }
				else if (attribute.hasName("Return")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Return", attribute.getValues())); }
				else if (attribute.hasName("Throw")) { result.Statements.add(new AbstractMap.SimpleEntry<String,Object>("Throw", attribute.getValues())); }
			}
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Get");
		for (Object listValue : this.Statements) {
			if (listValue instanceof AbstractMap.SimpleEntry) {
				AbstractMap.SimpleEntry entry = (AbstractMap.SimpleEntry)listValue;
				String attributeName = (String)entry.getKey();
				Object attributeObj = entry.getValue();
				if (attributeName.equalsIgnoreCase("Expression")) { result.add(new SmlAttribute("Expression", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("DefV")) { result.add(new SmlAttribute("DefV", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetF")) { result.add(new SmlAttribute("SetF", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetP")) { result.add(new SmlAttribute("SetP", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetV")) { result.add(new SmlAttribute("SetV", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("SetParam")) { result.add(new SmlAttribute("SetParam", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("Control")) { result.add(new SmlAttribute("Control", ValueTypeUtils.getControlTypeString((ControlType)attributeObj))); }
				else if (attributeName.equalsIgnoreCase("Return")) { result.add(new SmlAttribute("Return", (String[])attributeObj)); }
				else if (attributeName.equalsIgnoreCase("Throw")) { result.add(new SmlAttribute("Throw", (String[])attributeObj)); }
			} else {
				if (listValue instanceof If) { result.add(((If)listValue).toElement()); }
				else if (listValue instanceof For) { result.add(((For)listValue).toElement()); }
				else if (listValue instanceof Foreach) { result.add(((Foreach)listValue).toElement()); }
				else if (listValue instanceof Loop) { result.add(((Loop)listValue).toElement()); }
				else if (listValue instanceof Switch) { result.add(((Switch)listValue).toElement()); }
				else if (listValue instanceof Try) { result.add(((Try)listValue).toElement()); }
				else { throw new LimaScriptPackageException("Unknown child list item type"); }
			}
		}
		return result;
	}
	
}

class Interface {
	public String Name;
	
	public Boolean Internal;
	
	public boolean hasInternal() {
		return this.Internal != null;
	}
	
	public String Extends;
	
	public boolean hasExtends() {
		return this.Extends != null;
	}
	
	public ArrayList<String> ImplementsList = new ArrayList<>();
	
	public ArrayList<InterfaceProperty> InterfacePropertyList = new ArrayList<>();
	
	public ArrayList<InterfaceMethod> InterfaceMethodList = new ArrayList<>();
	
	public static Interface parse(SmlElement element) {
		if (!element.hasName("Interface")) {
			throw new LimaScriptPackageException("Element with name \"Interface\" was expected, but found \""+element.getName()+"\"");
		}
		Interface result = new Interface();
		{
			SchemaUtils.assureRequiredAttribute(element, "Name");
			SmlAttribute attribute = element.attribute("Name");
			result.Name = attribute.getString();
		}
		SchemaUtils.assureOptionalAttribute(element, "Internal");
		if (element.hasAttribute("Internal")) {
			SmlAttribute attribute = element.attribute("Internal");
			result.Internal = attribute.getBoolean();
		}
		SchemaUtils.assureOptionalAttribute(element, "Extends");
		if (element.hasAttribute("Extends")) {
			SmlAttribute attribute = element.attribute("Extends");
			result.Extends = attribute.getString();
		}
		for (SmlAttribute attribute : element.attributes("Implements")) {
			result.ImplementsList.add(attribute.getString());
		}
		for (SmlElement childElement: element.elements("InterfaceProperty")) {
			result.InterfacePropertyList.add(InterfaceProperty.parse(childElement));
		}
		for (SmlElement childElement: element.elements("InterfaceMethod")) {
			result.InterfaceMethodList.add(InterfaceMethod.parse(childElement));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Interface");
		if (this.Name == null) {
			throw new LimaScriptPackageException("Field 'Name' in class 'Interface' is null");
		}
		result.add(new SmlAttribute("Name", (String)this.Name));
		if (this.hasInternal()) {
			result.add(new SmlAttribute("Internal", (Boolean)this.Internal));
		}
		if (this.hasExtends()) {
			result.add(new SmlAttribute("Extends", (String)this.Extends));
		}
		for (String listValue : this.ImplementsList) {
			result.add(new SmlAttribute("Implements", listValue));
		}
		for (InterfaceProperty listValue : this.InterfacePropertyList) {
			result.add(listValue.toElement());
		}
		for (InterfaceMethod listValue : this.InterfaceMethodList) {
			result.add(listValue.toElement());
		}
		return result;
	}
	
}

class InterfaceProperty {
	public Boolean ReadOnly;
	
	public boolean hasReadOnly() {
		return this.ReadOnly != null;
	}
	
	public static InterfaceProperty parse(SmlElement element) {
		if (!element.hasName("Property")) {
			throw new LimaScriptPackageException("Element with name \"Property\" was expected, but found \""+element.getName()+"\"");
		}
		InterfaceProperty result = new InterfaceProperty();
		SchemaUtils.assureOptionalAttribute(element, "ReadOnly");
		if (element.hasAttribute("ReadOnly")) {
			SmlAttribute attribute = element.attribute("ReadOnly");
			result.ReadOnly = attribute.getBoolean();
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Property");
		if (this.hasReadOnly()) {
			result.add(new SmlAttribute("ReadOnly", (Boolean)this.ReadOnly));
		}
		return result;
	}
	
}

class InterfaceMethod {
	public Params Params = new Params();
	
	public static InterfaceMethod parse(SmlElement element) {
		if (!element.hasName("Method")) {
			throw new LimaScriptPackageException("Element with name \"Method\" was expected, but found \""+element.getName()+"\"");
		}
		InterfaceMethod result = new InterfaceMethod();
		SchemaUtils.assureRequiredElement(element, "Params");
		{
			result.Params = test.Params.parse(element.element("Params"));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Method");
		if (this.Params == null) {
			throw new LimaScriptPackageException("Field 'Params' in class 'InterfaceMethod' is null");
		}
		result.add(this.Params.toElement());
		return result;
	}
	
}

class Enum {
	public String Name;
	
	public Boolean Internal;
	
	public boolean hasInternal() {
		return this.Internal != null;
	}
	
	public Values Values;
	
	public boolean hasValues() {
		return this.Values != null;
	}
	
	public static Enum parse(SmlElement element) {
		if (!element.hasName("Enum")) {
			throw new LimaScriptPackageException("Element with name \"Enum\" was expected, but found \""+element.getName()+"\"");
		}
		Enum result = new Enum();
		{
			SchemaUtils.assureRequiredAttribute(element, "Name");
			SmlAttribute attribute = element.attribute("Name");
			result.Name = attribute.getString();
		}
		SchemaUtils.assureOptionalAttribute(element, "Internal");
		if (element.hasAttribute("Internal")) {
			SmlAttribute attribute = element.attribute("Internal");
			result.Internal = attribute.getBoolean();
		}
		SchemaUtils.assureOptionalElement(element, "Values");
		if (element.hasElement("Values")) {
			result.Values = test.Values.parse(element.element("Values"));
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Enum");
		if (this.Name == null) {
			throw new LimaScriptPackageException("Field 'Name' in class 'Enum' is null");
		}
		result.add(new SmlAttribute("Name", (String)this.Name));
		if (this.hasInternal()) {
			result.add(new SmlAttribute("Internal", (Boolean)this.Internal));
		}
		if (this.hasValues()) {
			result.add(this.Values.toElement());
		}
		return result;
	}
	
}

class Values {
	public ArrayList<String[]> ValueList = new ArrayList<>();
	
	public static Values parse(SmlElement element) {
		if (!element.hasName("Values")) {
			throw new LimaScriptPackageException("Element with name \"Values\" was expected, but found \""+element.getName()+"\"");
		}
		Values result = new Values();
		for (SmlAttribute attribute : element.attributes("Value")) {
			result.ValueList.add(attribute.getValues());
		}
		return result;
	}
	
	public SmlElement toElement() {
		SmlElement result = new SmlElement("Values");
		for (String[] listValue : this.ValueList) {
			result.add(new SmlAttribute("Value", listValue));
		}
		return result;
	}
	
}

enum ControlType {
	BREAK,
	CONTINUE
}

enum ClassType {
	ABSTRACT,
	STATIC
}

enum ImplementationType {
	ABSTRACT,
	NATIVE
}

enum OverrideType {
	ABSTRACT,
	OVERRIDABLE,
	OVERRIDE,
	FORCEOVERRIDE,
	STATIC
}
