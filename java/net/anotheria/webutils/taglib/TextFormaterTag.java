package net.anotheria.webutils.taglib;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.jsp.JspException;

import net.anotheria.util.StringUtils;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class TextFormaterTag extends BaseBodyTagSupport{

	public static final String CONTROL_IDENTIFIER = "__";

	public static final String NO_HANDLING = "___";
	public static final String LINE_TAG = CONTROL_IDENTIFIER+"line";
	public static final String IMAGE_TAG = CONTROL_IDENTIFIER+"img";
	public static final String NBSP_TAG = CONTROL_IDENTIFIER+"space";
	
	public static final String PUNKTUATION[] = {
		".",
		",",
		"-",
		"}",
		"]",
		")",
		";",
		":"
	};
	
	private static HashMap mappings ;
	static{
		mappings = new HashMap();
		mappings.put(CONTROL_IDENTIFIER+"bs", "b");
		mappings.put(CONTROL_IDENTIFIER+"be", "/b");
		mappings.put(CONTROL_IDENTIFIER+"is", "i");
		mappings.put(CONTROL_IDENTIFIER+"ie", "/i");
		mappings.put(CONTROL_IDENTIFIER+"us", "u");
		mappings.put(CONTROL_IDENTIFIER+"ue", "/u");
		
		mappings.put(CONTROL_IDENTIFIER+"tables", "table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"");
		mappings.put(CONTROL_IDENTIFIER+"tablee", "/table");
		mappings.put(CONTROL_IDENTIFIER+"rows", "tr");
		mappings.put(CONTROL_IDENTIFIER+"rowe","/tr");
		mappings.put(CONTROL_IDENTIFIER+"cells", "td valign=\"top\"");
		mappings.put(CONTROL_IDENTIFIER+"celle","/td");
		mappings.put(CONTROL_IDENTIFIER+"trs", "tr");
		mappings.put(CONTROL_IDENTIFIER+"tre","/tr");
		mappings.put(CONTROL_IDENTIFIER+"tds", "td valign=\"top\"");
		mappings.put(CONTROL_IDENTIFIER+"tde","/td");
		
		mappings.put(CONTROL_IDENTIFIER+"centers", "center");
		mappings.put(CONTROL_IDENTIFIER+"centere","/center");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		String body = getBodyContent().getString();
		body = StringUtils.removeChar(body, '\r');
		String tokens[] = StringUtils.tokenize(body, '\n');
		for (int i=0; i<tokens.length; i++){
			proceedLine(tokens[i]);
			if (!lastTagBrBreaker)
				writeLn("<br>");
		}
		//write(body);
		return EVAL_BODY_TAG;
	}
	
	private void proceedLine(String s) throws JspException{
		lastTagBrBreaker = false;
		tagStarted = false;
		String words[] = StringUtils.tokenize(s, ' ');
		for (int i=0; i<words.length; i++){
			proceedWord(words[i]);
		}
	}
	
	private boolean tagStarted;
	private boolean lastTagBrBreaker;
	
	private void proceedWord(String word) throws JspException{
		if (word.startsWith(CONTROL_IDENTIFIER)){
			String endOfWord = word.substring(word.length()-1);
			boolean punctuated = false;
			for (int i=0; i<PUNKTUATION.length; i++){
				if (endOfWord.equals(PUNKTUATION[i])){
					punctuated = true;
					break;
				}
			}
			if (!punctuated)
				proceedControlWord(word);
			else{
				String subword = word.substring(0, word.length()-1);
				proceedControlWord(subword);
				write(endOfWord);
			}
		}else{
			proceedPlainWord(word);
		}
			
	}
	
	private void proceedPlainWord(String word) throws JspException{
		if (!tagStarted)
			write(" ");
		write(word);
		tagStarted = false;
		
	}
	
	
	private void proceedControlWord(String word) throws JspException{
		lastTagBrBreaker = false;
		String mapping = (String) mappings.get(word);
		if (mapping!=null){
			proceedMapping(word, mapping);
			return;
		}
		
		if (word.startsWith(NO_HANDLING)){
			proceedPlainWord(word.substring(1));
			return;
		}
		
		if (word.equals(LINE_TAG)){
			writeLn("");
			lastTagBrBreaker = true;
			writeLn("<hr size="+quote("1")+">");
			return;
		}
		
		if (word.equals(NBSP_TAG)){
			write("&nbsp;");
			return;
		}

		if (word.startsWith(IMAGE_TAG)){
			proceedImageTag(word);
			return;
		}
			
		
		write("Unknown: "+word);
	}
	
	public static final String EMPTY = "?";
	private int imageWindowCount = 1;
	private void proceedImageTag(String word) throws JspException{
		String tag = word.substring((IMAGE_TAG+"_").length());
		//System.out.println("tag: "+tag);
		
		String name, width, height;
		width = EMPTY;
		height = EMPTY;
		int index = tag.indexOf('_');
		if (index==-1){
			name = tag;
		}else{
			name = tag.substring(0, index);
			String size = tag.substring(index+1);
			int indexOfDelimiter = size.indexOf('x');
			if (indexOfDelimiter>0){
				width = size.substring(0, indexOfDelimiter);
				height = size.substring(indexOfDelimiter+1);
			}
		}
		
		//System.out.println("Name "+name+", width: "+width+", height: "+height);
		String ret = "";
		ret += "<a href=\"#\" onClick=\"openPictureWindowByName('"+URLEncoder.encode(name)+"', 'Full view', 'WIN"+(imageWindowCount++)+"');return false\">";		ret += "<img src="+quote("/tec/cms/getImageByName?name="+URLEncoder.encode(name));
		if (!EMPTY.equals(width)){
			ret += " width="+quote(width);
		}
		if (!EMPTY.equals(height)){
			ret += " height="+quote(height);
		}
		ret += " border="+quote("0")+">";
		ret += "</a>";
		write(ret);
	}
	
	private void proceedMapping(String word, String mapping) throws JspException{
		if (!tagStarted && word.endsWith("s"))
			write(" ");
		if (word.endsWith("s"))
			tagStarted = true;
		write("<"+mapping+">");
		//System.out.println("word: "+word+", mapping:"+mapping);
		if (word.equals("__cells") || word.equals("__celle") || 
			word.equals("__rows") || word.equals("__rowe") || 
			word.equals("__tablee") || word.equals("__tables") 
			)
			lastTagBrBreaker = true;
	}

}
