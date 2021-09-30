package docgen;

import java.util.ArrayList;

class CharIterator {
	protected final int[] chars;
	protected int index;
	protected int lastSubLength;
	protected int lastTokenLength;
	
	public CharIterator(String text) {
		chars = text.codePoints().toArray();
	}
	
	public boolean isEndOfText() {
		return isEndOfText(0);
	}
	
	protected boolean isEndOfText(int offset) {
		return index + offset >= chars.length;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int[] getChars() {
		return chars;
	}
	
	@Override
	public String toString() {
		if (isEndOfText()) { return "END OF TEXT"; }
		int endIndex = Math.min(index+20, chars.length);
		return new String(chars, index, endIndex-index);
	}
	
	public int[] getLineInfo() {
		int lineIndex = 0;
		int linePosition = 0;
		for (int i=0; i<index; i++) {
			if (chars[i] == '\n') {
				lineIndex++;
				linePosition = 0;
			} else {
				linePosition++;
			}
		}
		return new int[] {lineIndex, linePosition};
	}
	
	public void resetIndex(int index) {
		this.index = index;
	}
	
	protected boolean isString(int offset, int[] strChars) {
		if (index + offset + strChars.length > chars.length) {
			return false;
		}
		for (int i=0; i<strChars.length; i++) {
			if (chars[index+offset+i] != strChars[i]) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean isCiString(int offset, int[] strChars) {
		if (index + offset + strChars.length > chars.length) {
			return false;
		}
		for (int i=0; i<strChars.length; i++) {
			if (Character.toLowerCase(chars[index+offset+i]) != Character.toLowerCase(strChars[i])) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean isChar_WsChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x09) || (c == 0x0B) || (c == 0x0C) || (c == 0x0D) || (c == 0x20) || (c == 0x85) || (c == 0xA0) || (c == 0x1680) || (c == 0x2000) || (c == 0x2001) || (c == 0x2002) || (c == 0x2003) || (c == 0x2004) || (c == 0x2005) || (c == 0x2006) || (c == 0x2007) || (c == 0x2008) || (c == 0x2009) || (c == 0x200A) || (c == 0x2028) || (c == 0x2029) || (c == 0x202F) || (c == 0x205F) || (c == 0x3000));
	}
	
	protected boolean isChar_LineBreakChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x0A));
	}
	
	protected boolean isChar_HashChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x23));
	}
	
	protected boolean isChar_CommentChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return (((c >= 0 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0x10FFFF))) && !(((((c == 0x0A)))));
	}
	
	protected boolean isChar_UnderscoreChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x5F));
	}
	
	protected boolean isChar_LetterNumberChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((Character.getType(c) == Character.LETTER_NUMBER));
	}
	
	protected boolean isChar_LetterChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((Character.isLetter(c)) || (((Character.getType(c) == Character.LETTER_NUMBER))));
	}
	
	protected boolean isChar_IdentifierStartChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((((c == 0x5F))) || (((Character.isLetter(c)) || (((Character.getType(c) == Character.LETTER_NUMBER))))));
	}
	
	protected boolean isChar_ConnectorChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((Character.getType(c) == Character.CONNECTOR_PUNCTUATION));
	}
	
	protected boolean isChar_MarkChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((Character.getType(c) == Character.COMBINING_SPACING_MARK) || (Character.getType(c) == Character.NON_SPACING_MARK));
	}
	
	protected boolean isChar_IdentifierRestChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((((Character.isLetter(c)) || (((Character.getType(c) == Character.LETTER_NUMBER))))) || (Character.isDigit(c)) || (((Character.getType(c) == Character.CONNECTOR_PUNCTUATION))) || (((Character.getType(c) == Character.COMBINING_SPACING_MARK) || (Character.getType(c) == Character.NON_SPACING_MARK))));
	}
	
	protected boolean isChar_BackslashChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x5C));
	}
	
	protected boolean isChar_DotChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x2E));
	}
	
	protected boolean isChar_VersionDigit(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c >= 0x30 && c <= 0x39));
	}
	
	protected boolean isChar_IntChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c >= 0x30 && c <= 0x39));
	}
	
	protected boolean isChar_ZeroChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x30));
	}
	
	protected boolean isChar_BinaryChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x30) || (c == 0x31));
	}
	
	protected boolean isChar_BChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x62) || (c == 0x42));
	}
	
	protected boolean isChar_XChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x78) || (c == 0x58));
	}
	
	protected boolean isChar_HexChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c >= 0x30 && c <= 0x39) || (c >= 0x61 && c <= 0x66) || (c >= 0x41 && c <= 0x46));
	}
	
	protected boolean isChar_ExponentialChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x65) || (c == 0x45));
	}
	
	protected boolean isChar_MinusChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x2D));
	}
	
	protected boolean isChar_SingleQuoteChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x27));
	}
	
	protected boolean isChar_SimpleCharChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return (((c >= 0 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0x10FFFF))) && !(((((c == 0x27))) || (((c == 0x0A))) || (((c == 0x5C)))));
	}
	
	protected boolean isChar_EscapedCharChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x30) || (((c == 0x5C))) || (((c == 0x27))) || (c == 0x6E) || (c == 0x4E) || (c == 0x72) || (c == 0x52) || (c == 0x74) || (c == 0x54));
	}
	
	protected boolean isChar_EscapedXChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x78) || (c == 0x58));
	}
	
	protected boolean isChar_EscapedUChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x75) || (c == 0x55));
	}
	
	protected boolean isChar_DoubleQuoteChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x22));
	}
	
	protected boolean isChar_SimpleStringChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return (((c >= 0 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0x10FFFF))) && !(((((c == 0x22))) || (((c == 0x0A))) || (((c == 0x5C)))));
	}
	
	protected boolean isChar_EscapedStringChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x30) || (((c == 0x5C))) || (((c == 0x22))) || (c == 0x6E) || (c == 0x4E) || (c == 0x72) || (c == 0x52) || (c == 0x74) || (c == 0x54));
	}
	
	protected boolean isChar_VerbatimStringChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return (((c >= 0 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0x10FFFF))) && !(((((c == 0x22))) || (((c == 0x0A)))));
	}
	
	protected boolean isChar_StringBlockChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return (((c >= 0 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0x10FFFF))) && !(((((c == 0x22)))));
	}
	
	protected boolean isChar_DollarChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x24));
	}
	
	protected boolean isChar_SimpleIStringChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return (((c >= 0 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0x10FFFF))) && !(((((c == 0x22))) || (((c == 0x0A))) || (((c == 0x5C))) || (((c == 0x24)))));
	}
	
	protected boolean isChar_VerbatimIStringChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return (((c >= 0 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0x10FFFF))) && !(((((c == 0x22))) || (((c == 0x0A))) || (((c == 0x24)))));
	}
	
	protected boolean isChar_IStringBlockChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return (((c >= 0 && c <= 0xD7FF) || (c >= 0xE000 && c <= 0x10FFFF))) && !(((((c == 0x22))) || (((c == 0x24)))));
	}
	
	protected boolean isChar_OpeningBracketChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x5B));
	}
	
	protected boolean isChar_ClosingBracketChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x5D));
	}
	
	protected boolean isChar_EqualSignChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x3D));
	}
	
	protected boolean isChar_CommaChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x2C));
	}
	
	protected boolean isChar_OpeningParenthesisChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x28));
	}
	
	protected boolean isChar_ClosingParenthesisChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x29));
	}
	
	protected boolean isChar_ColonChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x3A));
	}
	
	protected boolean isChar_PlusChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x2B));
	}
	
	protected boolean isChar_MultiplyChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x2A));
	}
	
	protected boolean isChar_DivideChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x2F));
	}
	
	protected boolean isChar_ModuloChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x25));
	}
	
	protected boolean isChar_AndChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x26));
	}
	
	protected boolean isChar_OrChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x7C));
	}
	
	protected boolean isChar_XorChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x5E));
	}
	
	protected boolean isChar_ComplementChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x7E));
	}
	
	protected boolean isChar_ExclamationMarkChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x21));
	}
	
	protected boolean isChar_QuestionMarkChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x3F));
	}
	
	protected boolean isChar_GreaterChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x3E));
	}
	
	protected boolean isChar_SmallerChar(int offset) {
		if (isEndOfText(offset)) return false;
		int c = chars[index+offset];
		return ((c == 0x3C));
	}
	
	public boolean isToken_LineBreak() {
		int offset = 0;
		if (!isChar_LineBreakChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_Ws(int offset) {
		int startOffset = offset;
		if (!isChar_WsChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_Ws() {
		int offset = 0;
		if (!(isTokenSub1_Ws(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_Ws(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_CommentText(int offset) {
		int startOffset = offset;
		if (!isChar_CommentChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_CommentText() {
		int offset = 0;
		if (!(isTokenSub1_CommentText(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_CommentText(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_CommentStart() {
		int offset = 0;
		if (!isChar_HashChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_Identifier(int offset) {
		int startOffset = offset;
		if (!isChar_IdentifierRestChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_Identifier() {
		int offset = 0;
		if (!isChar_IdentifierStartChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_Identifier(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_IdentifierDot() {
		int offset = 0;
		if (!isChar_DotChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Backslash() {
		int offset = 0;
		if (!isChar_BackslashChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_VersionPart(int offset) {
		int startOffset = offset;
		if (!isChar_VersionDigit(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_VersionPart() {
		int offset = 0;
		if (!(isTokenSub1_VersionPart(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_VersionPart(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_VersionDot() {
		int offset = 0;
		if (!isChar_DotChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_TypeName(int offset) {
		int startOffset = offset;
		if (!isChar_IdentifierRestChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_TypeName() {
		int offset = 0;
		if (!isChar_IdentifierStartChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_TypeName(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_NtIdentifier(int offset) {
		int startOffset = offset;
		if (!isChar_IdentifierRestChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_NtIdentifier() {
		int offset = 0;
		if (!isChar_IdentifierStartChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_NtIdentifier(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_NtIdentifierDot() {
		int offset = 0;
		if (!isChar_DotChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_PkgIdentifier(int offset) {
		int startOffset = offset;
		if (!isChar_IdentifierRestChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_PkgIdentifier() {
		int offset = 0;
		if (!isChar_IdentifierStartChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_PkgIdentifier(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_PkgIdentifierDot() {
		int offset = 0;
		if (!isChar_DotChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_PkgBackslash() {
		int offset = 0;
		if (!isChar_BackslashChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_BinaryIntLiteral(int offset) {
		int startOffset = offset;
		while (true) {
			if (!(isTokenSub2_BinaryIntLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_BinaryChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub2_BinaryIntLiteral(int offset) {
		int startOffset = offset;
		if (!isChar_UnderscoreChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_BinaryIntLiteral() {
		int offset = 0;
		if (!isChar_ZeroChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_BChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_BinaryChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_BinaryIntLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_HexIntLiteral(int offset) {
		int startOffset = offset;
		while (true) {
			if (!(isTokenSub2_HexIntLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub2_HexIntLiteral(int offset) {
		int startOffset = offset;
		if (!isChar_UnderscoreChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_HexIntLiteral() {
		int offset = 0;
		if (!isChar_ZeroChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_XChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_HexIntLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_DecimalIntLiteral(int offset) {
		int startOffset = offset;
		while (true) {
			if (!(isTokenSub2_DecimalIntLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_IntChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub2_DecimalIntLiteral(int offset) {
		int startOffset = offset;
		if (!isChar_UnderscoreChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_DecimalIntLiteral() {
		int offset = 0;
		if (!isChar_IntChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_DecimalIntLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_FloatLiteral(int offset) {
		int startOffset = offset;
		while (true) {
			if (!(isTokenSub2_FloatLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_IntChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub2_FloatLiteral(int offset) {
		int startOffset = offset;
		if (!isChar_UnderscoreChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub3_FloatLiteral(int offset) {
		int startOffset = offset;
		while (true) {
			if (!(isTokenSub4_FloatLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_IntChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub4_FloatLiteral(int offset) {
		int startOffset = offset;
		if (!isChar_UnderscoreChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub5_FloatLiteral(int offset) {
		int startOffset = offset;
		if (!isChar_ExponentialChar(offset)) {
			return false;
		}
		offset++;
		
		if (isTokenSub6_FloatLiteral(offset)) {
			offset += lastSubLength;
		}
		
		if (!isChar_IntChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub7_FloatLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub6_FloatLiteral(int offset) {
		int startOffset = offset;
		if (!isChar_MinusChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub7_FloatLiteral(int offset) {
		int startOffset = offset;
		while (true) {
			if (!(isTokenSub8_FloatLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_IntChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub8_FloatLiteral(int offset) {
		int startOffset = offset;
		if (!isChar_UnderscoreChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_FloatLiteral() {
		int offset = 0;
		if (!isChar_IntChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_FloatLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_DotChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_IntChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub3_FloatLiteral(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (isTokenSub5_FloatLiteral(offset)) {
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_TrueKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x74, 0x72, 0x75, 0x65}))) {
			return false;
		}
		offset += 4;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_FalseKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x66, 0x61, 0x6C, 0x73, 0x65}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_CharStart() {
		int offset = 0;
		if (!isChar_SingleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_CharEnd() {
		int offset = 0;
		if (!isChar_SingleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SimpleChar() {
		int offset = 0;
		if (!isChar_SimpleCharChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_EscapedChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedCharChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub2_EscapedChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedXChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub3_EscapedChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedUChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_EscapedChar() {
		int offset = 0;
		if (!isChar_BackslashChar(offset)) {
			return false;
		}
		offset++;
		
		if (!(isTokenSub1_EscapedChar(offset) || isTokenSub2_EscapedChar(offset) || isTokenSub3_EscapedChar(offset))) {
			return false;
		}
		offset += lastSubLength;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_EscapedSimpleStringChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedStringChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub2_EscapedSimpleStringChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedXChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub3_EscapedSimpleStringChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedUChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_EscapedSimpleStringChar() {
		int offset = 0;
		if (!isChar_BackslashChar(offset)) {
			return false;
		}
		offset++;
		
		if (!(isTokenSub1_EscapedSimpleStringChar(offset) || isTokenSub2_EscapedSimpleStringChar(offset) || isTokenSub3_EscapedSimpleStringChar(offset))) {
			return false;
		}
		offset += lastSubLength;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SimpleStringStart() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SimpleStringEnd() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_SimpleStringText(int offset) {
		int startOffset = offset;
		if (!isChar_SimpleStringChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_SimpleStringText() {
		int offset = 0;
		if (!(isTokenSub1_SimpleStringText(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_SimpleStringText(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_VerbatimStringStart() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_VerbatimStringEnd() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_TripleDoubleQuote() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_VerbatimStringText(int offset) {
		int startOffset = offset;
		if (!isChar_VerbatimStringChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_VerbatimStringText() {
		int offset = 0;
		if (!(isTokenSub1_VerbatimStringText(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_VerbatimStringText(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_EscapedTripleDoubleQuote() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_TwoDoubleQuotes() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_OneDoubleQuote() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_StringBlockStart() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_StringBlockEnd() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_StringBlockText(int offset) {
		int startOffset = offset;
		if (!isChar_StringBlockChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_StringBlockText() {
		int offset = 0;
		if (!(isTokenSub1_StringBlockText(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_StringBlockText(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_StringBlockIgnoredWhitespace(int offset) {
		int startOffset = offset;
		if (!isChar_WsChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_StringBlockIgnoredWhitespace() {
		int offset = 0;
		while (true) {
			if (!(isTokenSub1_StringBlockIgnoredWhitespace(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_LineBreakChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_EscapedSimpleIStringChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedStringChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub2_EscapedSimpleIStringChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedXChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	protected boolean isTokenSub3_EscapedSimpleIStringChar(int offset) {
		int startOffset = offset;
		if (!isChar_EscapedUChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_HexChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_EscapedSimpleIStringChar() {
		int offset = 0;
		if (!isChar_BackslashChar(offset)) {
			return false;
		}
		offset++;
		
		if (!(isTokenSub1_EscapedSimpleIStringChar(offset) || isTokenSub2_EscapedSimpleIStringChar(offset) || isTokenSub3_EscapedSimpleIStringChar(offset))) {
			return false;
		}
		offset += lastSubLength;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_EscapedDollar() {
		int offset = 0;
		if (!isChar_DollarChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DollarChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SimpleIStringStart() {
		int offset = 0;
		if (!isChar_DollarChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SimpleIStringEnd() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_SimpleIStringText(int offset) {
		int startOffset = offset;
		if (!isChar_SimpleIStringChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_SimpleIStringText() {
		int offset = 0;
		if (!(isTokenSub1_SimpleIStringText(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_SimpleIStringText(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_VerbatimIStringStart() {
		int offset = 0;
		if (!isChar_DollarChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_VerbatimIStringEnd() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_VerbatimIStringText(int offset) {
		int startOffset = offset;
		if (!isChar_VerbatimIStringChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_VerbatimIStringText() {
		int offset = 0;
		if (!(isTokenSub1_VerbatimIStringText(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_VerbatimIStringText(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_IStringBlockStart() {
		int offset = 0;
		if (!isChar_DollarChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_IStringBlockEnd() {
		int offset = 0;
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DoubleQuoteChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_IStringBlockText(int offset) {
		int startOffset = offset;
		if (!isChar_IStringBlockChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_IStringBlockText() {
		int offset = 0;
		if (!(isTokenSub1_IStringBlockText(offset))) {
			return false;
		}
		offset += lastSubLength;
		while (true) {
			if (!(isTokenSub1_IStringBlockText(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_IStringBlockIgnoredWhitespace(int offset) {
		int startOffset = offset;
		if (!isChar_WsChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_IStringBlockIgnoredWhitespace() {
		int offset = 0;
		while (true) {
			if (!(isTokenSub1_IStringBlockIgnoredWhitespace(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		if (!isChar_LineBreakChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_InterpolatedArgDollar() {
		int offset = 0;
		if (!isChar_DollarChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_OpeningBracket() {
		int offset = 0;
		if (!isChar_OpeningBracketChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ClosingBracket() {
		int offset = 0;
		if (!isChar_ClosingBracketChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_NullKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6E, 0x75, 0x6C, 0x6C}))) {
			return false;
		}
		offset += 4;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_EndKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x65, 0x6E, 0x64}))) {
			return false;
		}
		offset += 3;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_OpeningParenthesis() {
		int offset = 0;
		if (!isChar_OpeningParenthesisChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ClosingParenthesis() {
		int offset = 0;
		if (!isChar_ClosingParenthesisChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Comma() {
		int offset = 0;
		if (!isChar_CommaChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Colon() {
		int offset = 0;
		if (!isChar_ColonChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_UsingKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x75, 0x73, 0x69, 0x6E, 0x67}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_NamespaceKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6E, 0x61, 0x6D, 0x65, 0x73, 0x70, 0x61, 0x63, 0x65}))) {
			return false;
		}
		offset += 9;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_InternalKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x69, 0x6E, 0x74, 0x65, 0x72, 0x6E, 0x61, 0x6C}))) {
			return false;
		}
		offset += 8;
		
		lastTokenLength = offset;
		return true;
	}
	
	protected boolean isTokenSub1_ParamName(int offset) {
		int startOffset = offset;
		if (!isChar_IdentifierRestChar(offset)) {
			return false;
		}
		offset++;
		
		lastSubLength = offset-startOffset;
		return true;
	}
	
	public boolean isToken_ParamName() {
		int offset = 0;
		if (!isChar_IdentifierStartChar(offset)) {
			return false;
		}
		offset++;
		
		while (true) {
			if (!(isTokenSub1_ParamName(offset))) {
				break;
			}
			offset += lastSubLength;
		}
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ParamsKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x70, 0x61, 0x72, 0x61, 0x6D, 0x73}))) {
			return false;
		}
		offset += 6;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ConstantValueAssign() {
		int offset = 0;
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ClassKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x63, 0x6C, 0x61, 0x73, 0x73}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_AbstractKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x61, 0x62, 0x73, 0x74, 0x72, 0x61, 0x63, 0x74}))) {
			return false;
		}
		offset += 8;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_StaticKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x73, 0x74, 0x61, 0x74, 0x69, 0x63}))) {
			return false;
		}
		offset += 6;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_NativeKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6E, 0x61, 0x74, 0x69, 0x76, 0x65}))) {
			return false;
		}
		offset += 6;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ExtendsKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x65, 0x78, 0x74, 0x65, 0x6E, 0x64, 0x73}))) {
			return false;
		}
		offset += 7;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ImplementsKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x69, 0x6D, 0x70, 0x6C, 0x65, 0x6D, 0x65, 0x6E, 0x74, 0x73}))) {
			return false;
		}
		offset += 10;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_FieldKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x66, 0x69, 0x65, 0x6C, 0x64}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_MethodKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6D, 0x65, 0x74, 0x68, 0x6F, 0x64}))) {
			return false;
		}
		offset += 6;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ConstructorKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x63, 0x6F, 0x6E, 0x73, 0x74, 0x72, 0x75, 0x63, 0x74, 0x6F, 0x72}))) {
			return false;
		}
		offset += 11;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ReadOnlyKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x72, 0x65, 0x61, 0x64, 0x6F, 0x6E, 0x6C, 0x79}))) {
			return false;
		}
		offset += 8;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ForceKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x66, 0x6F, 0x72, 0x63, 0x65}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_OverrideKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6F, 0x76, 0x65, 0x72, 0x72, 0x69, 0x64, 0x65}))) {
			return false;
		}
		offset += 8;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_OverridableKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6F, 0x76, 0x65, 0x72, 0x72, 0x69, 0x64, 0x61, 0x62, 0x6C, 0x65}))) {
			return false;
		}
		offset += 11;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_PropertyKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x70, 0x72, 0x6F, 0x70, 0x65, 0x72, 0x74, 0x79}))) {
			return false;
		}
		offset += 8;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_GetKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x67, 0x65, 0x74}))) {
			return false;
		}
		offset += 3;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SetKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x73, 0x65, 0x74}))) {
			return false;
		}
		offset += 3;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_VarKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x76, 0x61, 0x72}))) {
			return false;
		}
		offset += 3;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ReturnKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x72, 0x65, 0x74, 0x75, 0x72, 0x6E}))) {
			return false;
		}
		offset += 6;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ThrowKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x74, 0x68, 0x72, 0x6F, 0x77}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_BreakKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x62, 0x72, 0x65, 0x61, 0x6B}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ContinueKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x63, 0x6F, 0x6E, 0x74, 0x69, 0x6E, 0x75, 0x65}))) {
			return false;
		}
		offset += 8;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Assignment() {
		int offset = 0;
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_PlusAssign() {
		int offset = 0;
		if (!isChar_PlusChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_MinusAssign() {
		int offset = 0;
		if (!isChar_MinusChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_MultiplyAssign() {
		int offset = 0;
		if (!isChar_MultiplyChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_DivideAssign() {
		int offset = 0;
		if (!isChar_DivideChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ModuloAssign() {
		int offset = 0;
		if (!isChar_ModuloChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_AndAssign() {
		int offset = 0;
		if (!isChar_AndChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_OrAssign() {
		int offset = 0;
		if (!isChar_OrChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_XorAssign() {
		int offset = 0;
		if (!isChar_XorChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_LeftShiftAssign() {
		int offset = 0;
		if (!isChar_SmallerChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_SmallerChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_RightShiftAssign() {
		int offset = 0;
		if (!isChar_GreaterChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_GreaterChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_NullDefaultAssign() {
		int offset = 0;
		if (!isChar_QuestionMarkChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_QuestionMarkChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Increment() {
		int offset = 0;
		if (!isChar_PlusChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_PlusChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Decrement() {
		int offset = 0;
		if (!isChar_MinusChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_MinusChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ForeachKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x66, 0x6F, 0x72, 0x65, 0x61, 0x63, 0x68}))) {
			return false;
		}
		offset += 7;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_InKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x69, 0x6E}))) {
			return false;
		}
		offset += 2;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ForKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x66, 0x6F, 0x72}))) {
			return false;
		}
		offset += 3;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ToKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x74, 0x6F}))) {
			return false;
		}
		offset += 2;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ByKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x62, 0x79}))) {
			return false;
		}
		offset += 2;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_WhileKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x77, 0x68, 0x69, 0x6C, 0x65}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_UntilKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x75, 0x6E, 0x74, 0x69, 0x6C}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_DoKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x64, 0x6F}))) {
			return false;
		}
		offset += 2;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_LoopKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6C, 0x6F, 0x6F, 0x70}))) {
			return false;
		}
		offset += 4;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_IfKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x69, 0x66}))) {
			return false;
		}
		offset += 2;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ElseKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x65, 0x6C, 0x73, 0x65}))) {
			return false;
		}
		offset += 4;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SwitchKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x73, 0x77, 0x69, 0x74, 0x63, 0x68}))) {
			return false;
		}
		offset += 6;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_CaseKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x63, 0x61, 0x73, 0x65}))) {
			return false;
		}
		offset += 4;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_DefaultKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x64, 0x65, 0x66, 0x61, 0x75, 0x6C, 0x74}))) {
			return false;
		}
		offset += 7;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_TryKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x74, 0x72, 0x79}))) {
			return false;
		}
		offset += 3;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_CatchKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x63, 0x61, 0x74, 0x63, 0x68}))) {
			return false;
		}
		offset += 5;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_FinallyKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x66, 0x69, 0x6E, 0x61, 0x6C, 0x6C, 0x79}))) {
			return false;
		}
		offset += 7;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Plus() {
		int offset = 0;
		if (!isChar_PlusChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Minus() {
		int offset = 0;
		if (!isChar_MinusChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Multiply() {
		int offset = 0;
		if (!isChar_MultiplyChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Divide() {
		int offset = 0;
		if (!isChar_DivideChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Modulo() {
		int offset = 0;
		if (!isChar_ModuloChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_AndSign() {
		int offset = 0;
		if (!isChar_AndChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_OrSign() {
		int offset = 0;
		if (!isChar_OrChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Xor() {
		int offset = 0;
		if (!isChar_XorChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Complement() {
		int offset = 0;
		if (!isChar_ComplementChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_LeftShift() {
		int offset = 0;
		if (!isChar_GreaterChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_GreaterChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_RightShift() {
		int offset = 0;
		if (!isChar_SmallerChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_SmallerChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Equal() {
		int offset = 0;
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_NotEqual() {
		int offset = 0;
		if (!isChar_ExclamationMarkChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_GreaterEqual() {
		int offset = 0;
		if (!isChar_GreaterChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SmallerEqual() {
		int offset = 0;
		if (!isChar_SmallerChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_PointerEqual() {
		int offset = 0;
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_PointerNotEqual() {
		int offset = 0;
		if (!isChar_ExclamationMarkChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_EqualSignChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Greater() {
		int offset = 0;
		if (!isChar_GreaterChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_Smaller() {
		int offset = 0;
		if (!isChar_SmallerChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_AccessDot() {
		int offset = 0;
		if (!isChar_DotChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SafeAccessDot() {
		int offset = 0;
		if (!isChar_QuestionMarkChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_DotChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_SafeOpeningBracket() {
		int offset = 0;
		if (!isChar_QuestionMarkChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_OpeningBracketChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_AndKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x61, 0x6E, 0x64}))) {
			return false;
		}
		offset += 3;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_OrKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6F, 0x72}))) {
			return false;
		}
		offset += 2;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_NotKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x6E, 0x6F, 0x74}))) {
			return false;
		}
		offset += 3;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_IsKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x69, 0x73}))) {
			return false;
		}
		offset += 2;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_NullDefault() {
		int offset = 0;
		if (!isChar_QuestionMarkChar(offset)) {
			return false;
		}
		offset++;
		
		if (!isChar_QuestionMarkChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_ExclamationMark() {
		int offset = 0;
		if (!isChar_ExclamationMarkChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_QuestionMark() {
		int offset = 0;
		if (!isChar_QuestionMarkChar(offset)) {
			return false;
		}
		offset++;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_InterfaceKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x69, 0x6E, 0x74, 0x65, 0x72, 0x66, 0x61, 0x63, 0x65}))) {
			return false;
		}
		offset += 9;
		
		lastTokenLength = offset;
		return true;
	}
	
	public boolean isToken_EnumKeyword() {
		int offset = 0;
		if (!(isCiString(offset, new int[] {0x65, 0x6E, 0x75, 0x6D}))) {
			return false;
		}
		offset += 4;
		
		lastTokenLength = offset;
		return true;
	}
	
	public int readToken() {
		index += lastTokenLength;
		return lastTokenLength;
	}
}

enum TokenType {
	LINEBREAK,
	WS,
	COMMENTTEXT,
	COMMENTSTART,
	IDENTIFIER,
	IDENTIFIERDOT,
	BACKSLASH,
	VERSIONPART,
	VERSIONDOT,
	TYPENAME,
	NTIDENTIFIER,
	NTIDENTIFIERDOT,
	PKGIDENTIFIER,
	PKGIDENTIFIERDOT,
	PKGBACKSLASH,
	BINARYINTLITERAL,
	HEXINTLITERAL,
	DECIMALINTLITERAL,
	FLOATLITERAL,
	TRUEKEYWORD,
	FALSEKEYWORD,
	CHARSTART,
	CHAREND,
	SIMPLECHAR,
	ESCAPEDCHAR,
	ESCAPEDSIMPLESTRINGCHAR,
	SIMPLESTRINGSTART,
	SIMPLESTRINGEND,
	SIMPLESTRINGTEXT,
	VERBATIMSTRINGSTART,
	VERBATIMSTRINGEND,
	TRIPLEDOUBLEQUOTE,
	VERBATIMSTRINGTEXT,
	ESCAPEDTRIPLEDOUBLEQUOTE,
	TWODOUBLEQUOTES,
	ONEDOUBLEQUOTE,
	STRINGBLOCKSTART,
	STRINGBLOCKEND,
	STRINGBLOCKTEXT,
	STRINGBLOCKIGNOREDWHITESPACE,
	ESCAPEDSIMPLEISTRINGCHAR,
	ESCAPEDDOLLAR,
	SIMPLEISTRINGSTART,
	SIMPLEISTRINGEND,
	SIMPLEISTRINGTEXT,
	VERBATIMISTRINGSTART,
	VERBATIMISTRINGEND,
	VERBATIMISTRINGTEXT,
	ISTRINGBLOCKSTART,
	ISTRINGBLOCKEND,
	ISTRINGBLOCKTEXT,
	ISTRINGBLOCKIGNOREDWHITESPACE,
	INTERPOLATEDARGDOLLAR,
	OPENINGBRACKET,
	CLOSINGBRACKET,
	NULLKEYWORD,
	ENDKEYWORD,
	OPENINGPARENTHESIS,
	CLOSINGPARENTHESIS,
	COMMA,
	COLON,
	USINGKEYWORD,
	NAMESPACEKEYWORD,
	INTERNALKEYWORD,
	PARAMNAME,
	PARAMSKEYWORD,
	CONSTANTVALUEASSIGN,
	CLASSKEYWORD,
	ABSTRACTKEYWORD,
	STATICKEYWORD,
	NATIVEKEYWORD,
	EXTENDSKEYWORD,
	IMPLEMENTSKEYWORD,
	FIELDKEYWORD,
	METHODKEYWORD,
	CONSTRUCTORKEYWORD,
	READONLYKEYWORD,
	FORCEKEYWORD,
	OVERRIDEKEYWORD,
	OVERRIDABLEKEYWORD,
	PROPERTYKEYWORD,
	GETKEYWORD,
	SETKEYWORD,
	VARKEYWORD,
	RETURNKEYWORD,
	THROWKEYWORD,
	BREAKKEYWORD,
	CONTINUEKEYWORD,
	ASSIGNMENT,
	PLUSASSIGN,
	MINUSASSIGN,
	MULTIPLYASSIGN,
	DIVIDEASSIGN,
	MODULOASSIGN,
	ANDASSIGN,
	ORASSIGN,
	XORASSIGN,
	LEFTSHIFTASSIGN,
	RIGHTSHIFTASSIGN,
	NULLDEFAULTASSIGN,
	INCREMENT,
	DECREMENT,
	FOREACHKEYWORD,
	INKEYWORD,
	FORKEYWORD,
	TOKEYWORD,
	BYKEYWORD,
	WHILEKEYWORD,
	UNTILKEYWORD,
	DOKEYWORD,
	LOOPKEYWORD,
	IFKEYWORD,
	ELSEKEYWORD,
	SWITCHKEYWORD,
	CASEKEYWORD,
	DEFAULTKEYWORD,
	TRYKEYWORD,
	CATCHKEYWORD,
	FINALLYKEYWORD,
	PLUS,
	MINUS,
	MULTIPLY,
	DIVIDE,
	MODULO,
	ANDSIGN,
	ORSIGN,
	XOR,
	COMPLEMENT,
	LEFTSHIFT,
	RIGHTSHIFT,
	EQUAL,
	NOTEQUAL,
	GREATEREQUAL,
	SMALLEREQUAL,
	POINTEREQUAL,
	POINTERNOTEQUAL,
	GREATER,
	SMALLER,
	ACCESSDOT,
	SAFEACCESSDOT,
	SAFEOPENINGBRACKET,
	ANDKEYWORD,
	ORKEYWORD,
	NOTKEYWORD,
	ISKEYWORD,
	NULLDEFAULT,
	EXCLAMATIONMARK,
	QUESTIONMARK,
	INTERFACEKEYWORD,
	ENUMKEYWORD
}

enum RuleType {
	COMMENT,
	FOLLOWINGVERSIONPART,
	PRERELEASEVERSION,
	RELEASEVERSION,
	IDENTIFIERCHAIN,
	NTIDENTIFIERCHAIN,
	NAMESPACEID,
	PKGIDENTIFIERCHAIN,
	PACKAGEID,
	USINGNAMESPACEID,
	CODEBLOCKNAMESPACEORENTITYID,
	INTLITERAL,
	BOOLLITERAL,
	CHARLITERAL,
	SIMPLESTRINGLITERAL,
	VERBATIMSTRINGCONTENT,
	VERBATIMSTRINGLITERAL,
	STRINGBLOCKCONTENT,
	STRINGBLOCKLITERAL,
	STRINGLITERAL,
	INTERPOLATEDARG,
	INTERPOLATEDGROUP,
	SIMPLEISTRINGLITERAL,
	VERBATIMISTRINGCONTENT,
	VERBATIMISTRINGLITERAL,
	ISTRINGBLOCKCONTENT,
	ISTRINGBLOCKLITERAL,
	ISTRINGLITERAL,
	ARRAYLITERAL,
	CONSTANTLITERAL,
	LINEEND,
	EMPTYLINECONTENT,
	EMPTYLINE,
	ENDLINE,
	LINECONTINUATION,
	COLONNOTATION,
	USING,
	USINGLINE,
	NAMESPACEHEADLINE,
	NAMESPACEEND,
	NAMESPACE,
	VARARGPARAMETER,
	NORMALPARAMETER,
	NOPARAMETERS,
	ONLYVARARGPARAMETER,
	NORMALPARAMETERS,
	NORMALPLUSVARARGPARAMETERS,
	PARAMETERS,
	CONSTANTASSIGNMENT,
	CLASSTYPE,
	CLASSHEADLINE,
	EXTENDS,
	IMPLEMENTS,
	FIELDLINE,
	METHODHEAD,
	METHODLINES,
	CONSTRUCTORHEADLINE,
	CONSTRUCTORLINES,
	OVERRIDETYPE,
	NATIVECONSTRUCTORLINE,
	NATIVEMETHODLINE,
	ABSTRACTMETHODLINE,
	GETHEADLINE,
	GETLINES,
	SINGLEGETLINE,
	SETHEADLINE,
	SETLINES,
	SINGLESETLINE,
	COLONNOTATIONSTATEMENT,
	PROPERTYHEADLINE,
	PROPERTYLINES,
	ABSTRACTPROPERTYLINE,
	COLONNOTATIONORCODEBLOCK,
	READONLYPROPERTYLINES,
	CLASSLINES,
	CODEBLOCK,
	VARDEFSTATEMENT,
	RETURNSTATEMENT,
	THROWSTATEMENT,
	BREAKSTATEMENT,
	CONTINUESTATEMENT,
	INCREMENTDECREMENTSTATEMENT,
	SINGLEFOREACHLINE,
	SINGLEFORLINE,
	SINGLEWHILELINE,
	SINGLEUNTILLINE,
	SINGLEIFLINE,
	SINGLETRYLINE,
	EXPRESSIONSTATEMENT,
	ASSIGNMENTOPERATOR,
	ASSIGNABLE,
	ASSIGNMENTSTATEMENT,
	STATEMENT,
	FOREACHHEAD,
	FOREACHSTATEMENTBLOCK,
	FORHEAD,
	FORSTATEMENTBLOCK,
	WHILEHEAD,
	WHILESTATEMENTBLOCK,
	UNTILHEAD,
	UNTILSTATEMENTBLOCK,
	LOOPLINE,
	DOSTATEMENTBLOCK,
	IFHEAD,
	ELSEIFHEAD,
	ELSEHEAD,
	IFSTATEMENTBLOCK,
	FOLLOWINGELSE,
	ELSEIFBLOCK,
	ELSEBLOCK,
	TRYHEAD,
	CATCHHEAD,
	FINALLYHEAD,
	TRYSTATEMENTBLOCK,
	FOLLOWINGCATCHFINALLY,
	CATCHBLOCK,
	FINALLYBLOCK,
	SWITCHSTATEMENTBLOCK,
	SWITCHBLOCKCONTENT,
	SWITCHCASE,
	SWITCHDEFAULT,
	STATEMENTBLOCK,
	CALLOPERATOR,
	ACCESSOPERATOR,
	ARRAYACCESSOPERATOR,
	SEQUENTIALOPERATOR,
	SIGNBINARYOPERATOR,
	WSBINARYOPERATOR,
	TERNARYOPERATOR,
	PREFIXOPERATOR,
	GROUP,
	SINGLEEXPRESSION,
	EXPRESSION,
	INTERFACEHEADLINE,
	INTERFACEMETHODLINE,
	INTERFACEPROPERTYLINE,
	INTERFACELINES,
	ENUMHEADLINE,
	ENUMVALUELINE,
	ENUMLINES,
	LSDOCUMENTCONTENTITEM,
	LSDOCUMENTCONTENT,
	EMPTYLSDOCUMENT,
	NONEMPTYLSDOCUMENT,
	LSDOCUMENT
}

enum TokenCategory {
	KEYWORD,
	OPERATOR,
	NUMBER,
	STRING
}

class ParserException extends RuntimeException {
	public ParserException(String message) {
		super(message);
	}
	public ParserException(String message, int[] lineInfo) {
		super(message + " (Line " + (lineInfo[0]+1) + ", Char " + (lineInfo[1]+1) + ")");
	}
}

class ParserTreeNode {
}

class RuleNode extends ParserTreeNode {
	public final RuleType Type;
	public final ArrayList<ParserTreeNode> Children = new ArrayList<>();
	
	public RuleNode(RuleType type) {
		Type = type;
	}
	
	public void addToken(TokenType type, int length) {
		Children.add(new Token(type, length));
	}
	
	public void add(ParserTreeNode node) {
		Children.add(node);
	}
	
	public void addTemp(RuleNode node) {
		for (ParserTreeNode child : node.Children) {
			Children.add(child);
		}
	}
	
	@Override
	public String toString() {
		return "Rule "+Type.name();
	}
}

class Token extends ParserTreeNode {
	public final TokenType Type;
	public final int Length;
	
	public Token(TokenType type, int length) {
		Type = type;
		Length = length;
	}
	
	@Override
	public String toString() {
		return "Token "+Type.name() + " (" + Length + ")";
	}
}

class TokenUtils {
	public static TokenCategory getTokenCategory(Token token) {
		if (token.Type == TokenType.NAMESPACEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.INTERNALKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.USINGKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.CLASSKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.ENUMKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.ENDKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.STATICKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.NATIVEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.ABSTRACTKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.EXTENDSKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.IMPLEMENTSKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.INTERFACEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.FIELDKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.CONSTRUCTORKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.METHODKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.READONLYKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.FORCEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.OVERRIDEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.OVERRIDABLEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.PARAMSKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.PROPERTYKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.GETKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.SETKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.VARKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.TRUEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.FALSEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.NULLKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.RETURNKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.FOREACHKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.INKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.FORKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.TOKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.BYKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.WHILEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.UNTILKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.DOKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.LOOPKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.IFKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.ELSEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.SWITCHKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.CASEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.DEFAULTKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.ANDKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.ORKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.NOTKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.ISKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.THROWKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.BREAKKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.CONTINUEKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.TRYKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.CATCHKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.FINALLYKEYWORD) {
			return TokenCategory.KEYWORD;
		} else if (token.Type == TokenType.CONSTANTVALUEASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.COMMA) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.COLON) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.ASSIGNMENT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.INTERPOLATEDARGDOLLAR) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.OPENINGBRACKET) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.CLOSINGBRACKET) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.ACCESSDOT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.PLUS) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.MINUS) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.MULTIPLY) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.DIVIDE) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.EQUAL) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.GREATEREQUAL) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.SMALLEREQUAL) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.GREATER) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.SMALLER) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.MODULO) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.ANDSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.ORSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.XOR) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.COMPLEMENT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.LEFTSHIFT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.RIGHTSHIFT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.NOTEQUAL) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.POINTEREQUAL) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.POINTERNOTEQUAL) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.NULLDEFAULT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.QUESTIONMARK) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.EXCLAMATIONMARK) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.SAFEACCESSDOT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.SAFEOPENINGBRACKET) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.PLUSASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.MINUSASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.MULTIPLYASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.DIVIDEASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.MODULOASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.ANDASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.ORASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.XORASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.LEFTSHIFTASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.RIGHTSHIFTASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.NULLDEFAULTASSIGN) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.INCREMENT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.DECREMENT) {
			return TokenCategory.OPERATOR;
		} else if (token.Type == TokenType.DECIMALINTLITERAL) {
			return TokenCategory.NUMBER;
		} else if (token.Type == TokenType.BINARYINTLITERAL) {
			return TokenCategory.NUMBER;
		} else if (token.Type == TokenType.HEXINTLITERAL) {
			return TokenCategory.NUMBER;
		} else if (token.Type == TokenType.FLOATLITERAL) {
			return TokenCategory.NUMBER;
		} else if (token.Type == TokenType.SIMPLESTRINGSTART) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.SIMPLESTRINGEND) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.SIMPLESTRINGTEXT) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.CHARSTART) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.CHAREND) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.SIMPLECHAR) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ESCAPEDCHAR) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ESCAPEDSIMPLESTRINGCHAR) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.VERBATIMSTRINGSTART) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.VERBATIMSTRINGEND) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.VERBATIMSTRINGTEXT) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ESCAPEDTRIPLEDOUBLEQUOTE) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.TWODOUBLEQUOTES) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ONEDOUBLEQUOTE) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.STRINGBLOCKSTART) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.STRINGBLOCKEND) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.STRINGBLOCKTEXT) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.STRINGBLOCKIGNOREDWHITESPACE) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ESCAPEDSIMPLEISTRINGCHAR) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ESCAPEDDOLLAR) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.SIMPLEISTRINGSTART) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.SIMPLEISTRINGEND) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.SIMPLEISTRINGTEXT) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.VERBATIMISTRINGSTART) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.VERBATIMISTRINGEND) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.VERBATIMISTRINGTEXT) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ISTRINGBLOCKSTART) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ISTRINGBLOCKEND) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ISTRINGBLOCKTEXT) {
			return TokenCategory.STRING;
		} else if (token.Type == TokenType.ISTRINGBLOCKIGNOREDWHITESPACE) {
			return TokenCategory.STRING;
		}
		return null;
	}
}

public class Parser {
	CharIterator iterator;
	ParserTreeNode lastNode;
	public RuleNode RootNode;
	
	public Parser(String content) {
		iterator = new CharIterator(content);
	}
	
	protected ParserException getException(String message) {
		int[] lineInfo = iterator.getLineInfo();
		return new ParserException(message, lineInfo);
	}
	
	protected boolean returnFalseOrThrowException(boolean optional, int startIndex, String message) {
		if (!optional) {
			throw getException(message);
		}
		iterator.resetIndex(startIndex);
		return false;
	}
	
	protected boolean parseRuleSub1_Comment(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COMMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_CommentText()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMMENTTEXT' expected in rule 'COMMENT'");
		}
		ruleNode.addToken(TokenType.COMMENTTEXT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Comment(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COMMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_CommentStart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMMENTSTART' expected in rule 'COMMENT'");
		}
		ruleNode.addToken(TokenType.COMMENTSTART, iterator.readToken());
		
		parseRuleSub1_Comment(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_FollowingVersionPart(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGVERSIONPART);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FOLLOWINGVERSIONPART'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_FollowingVersionPart(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGVERSIONPART);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FOLLOWINGVERSIONPART'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_FollowingVersionPart(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGVERSIONPART);
		int startIndex = iterator.getIndex();
		parseRuleSub1_FollowingVersionPart(ruleNode, true);
		
		if (!iterator.isToken_VersionDot()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERSIONDOT' expected in rule 'FOLLOWINGVERSIONPART'");
		}
		ruleNode.addToken(TokenType.VERSIONDOT, iterator.readToken());
		
		parseRuleSub2_FollowingVersionPart(ruleNode, true);
		
		if (!iterator.isToken_VersionPart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERSIONPART' expected in rule 'FOLLOWINGVERSIONPART'");
		}
		ruleNode.addToken(TokenType.VERSIONPART, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_PreReleaseVersion(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PRERELEASEVERSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_VersionPart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERSIONPART' expected in rule 'PRERELEASEVERSION'");
		}
		ruleNode.addToken(TokenType.VERSIONPART, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_FollowingVersionPart(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_ReleaseVersion(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.RELEASEVERSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_VersionPart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERSIONPART' expected in rule 'RELEASEVERSION'");
		}
		ruleNode.addToken(TokenType.VERSIONPART, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_FollowingVersionPart(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_FollowingVersionPart(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_FollowingVersionPart(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_IdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		parseRuleSub2_IdentifierChain(ruleNode, true);
		
		if (!iterator.isToken_IdentifierDot()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIERDOT' expected in rule 'IDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.IDENTIFIERDOT, iterator.readToken());
		
		parseRuleSub3_IdentifierChain(ruleNode, true);
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'IDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_IdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'IDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_IdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'IDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_IdentifierChain(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'IDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		while (true) {
			if (!(parseRuleSub1_IdentifierChain(ruleNode, true))) {
				break;
			}
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NtIdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NTIDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		parseRuleSub2_NtIdentifierChain(ruleNode, true);
		
		if (!iterator.isToken_NtIdentifierDot()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NTIDENTIFIERDOT' expected in rule 'NTIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.NTIDENTIFIERDOT, iterator.readToken());
		
		parseRuleSub3_NtIdentifierChain(ruleNode, true);
		
		if (!iterator.isToken_NtIdentifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NTIDENTIFIER' expected in rule 'NTIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.NTIDENTIFIER, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_NtIdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NTIDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NTIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_NtIdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NTIDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NTIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NtIdentifierChain(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NTIDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NtIdentifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NTIDENTIFIER' expected in rule 'NTIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.NTIDENTIFIER, iterator.readToken());
		
		while (true) {
			if (!(parseRuleSub1_NtIdentifierChain(ruleNode, true))) {
				break;
			}
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NamespaceID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEID);
		int startIndex = iterator.getIndex();
		parseRuleSub2_NamespaceID(ruleNode, true);
		
		if (!iterator.isToken_Backslash()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'BACKSLASH' expected in rule 'NAMESPACEID'");
		}
		ruleNode.addToken(TokenType.BACKSLASH, iterator.readToken());
		
		parseRuleSub3_NamespaceID(ruleNode, true);
		
		if (!parseRule_NtIdentifierChain(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_NamespaceID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NAMESPACEID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_NamespaceID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NAMESPACEID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NamespaceID(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEID);
		int startIndex = iterator.getIndex();
		if (!parseRule_PkgIdentifierChain(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub1_NamespaceID(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_PkgIdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PKGIDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		parseRuleSub2_PkgIdentifierChain(ruleNode, true);
		
		if (!iterator.isToken_PkgIdentifierDot()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PKGIDENTIFIERDOT' expected in rule 'PKGIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.PKGIDENTIFIERDOT, iterator.readToken());
		
		parseRuleSub3_PkgIdentifierChain(ruleNode, true);
		
		if (!iterator.isToken_PkgIdentifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PKGIDENTIFIER' expected in rule 'PKGIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.PKGIDENTIFIER, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_PkgIdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PKGIDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PKGIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_PkgIdentifierChain(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PKGIDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PKGIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_PkgIdentifierChain(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PKGIDENTIFIERCHAIN);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_PkgIdentifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PKGIDENTIFIER' expected in rule 'PKGIDENTIFIERCHAIN'");
		}
		ruleNode.addToken(TokenType.PKGIDENTIFIER, iterator.readToken());
		
		while (true) {
			if (!(parseRuleSub1_PkgIdentifierChain(ruleNode, true))) {
				break;
			}
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_PackageID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PACKAGEID);
		int startIndex = iterator.getIndex();
		parseRuleSub2_PackageID(ruleNode, true);
		
		if (!iterator.isToken_PkgBackslash()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PKGBACKSLASH' expected in rule 'PACKAGEID'");
		}
		ruleNode.addToken(TokenType.PKGBACKSLASH, iterator.readToken());
		
		parseRuleSub3_PackageID(ruleNode, true);
		
		if (!parseRule_ReleaseVersion(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub4_PackageID(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_PackageID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PACKAGEID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PACKAGEID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_PackageID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PACKAGEID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PACKAGEID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_PackageID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PACKAGEID);
		int startIndex = iterator.getIndex();
		parseRuleSub5_PackageID(ruleNode, true);
		
		if (!iterator.isToken_PkgBackslash()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PKGBACKSLASH' expected in rule 'PACKAGEID'");
		}
		ruleNode.addToken(TokenType.PKGBACKSLASH, iterator.readToken());
		
		parseRuleSub6_PackageID(ruleNode, true);
		
		if (!parseRule_PreReleaseVersion(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_PackageID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PACKAGEID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PACKAGEID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_PackageID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PACKAGEID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PACKAGEID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_PackageID(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PACKAGEID);
		int startIndex = iterator.getIndex();
		if (!parseRule_PkgIdentifierChain(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub1_PackageID(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_UsingNamespaceID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.USINGNAMESPACEID);
		int startIndex = iterator.getIndex();
		parseRuleSub2_UsingNamespaceID(ruleNode, true);
		
		if (!iterator.isToken_Backslash()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'BACKSLASH' expected in rule 'USINGNAMESPACEID'");
		}
		ruleNode.addToken(TokenType.BACKSLASH, iterator.readToken());
		
		parseRuleSub3_UsingNamespaceID(ruleNode, true);
		
		if (!parseRule_NtIdentifierChain(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_UsingNamespaceID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.USINGNAMESPACEID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'USINGNAMESPACEID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_UsingNamespaceID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.USINGNAMESPACEID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'USINGNAMESPACEID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_UsingNamespaceID(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.USINGNAMESPACEID);
		int startIndex = iterator.getIndex();
		if (!parseRule_PackageID(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub1_UsingNamespaceID(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_CodeBlockNamespaceOrEntityID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CODEBLOCKNAMESPACEORENTITYID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CODEBLOCKNAMESPACEORENTITYID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_CodeBlockNamespaceOrEntityID(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CODEBLOCKNAMESPACEORENTITYID);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CODEBLOCKNAMESPACEORENTITYID'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_CodeBlockNamespaceOrEntityID(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CODEBLOCKNAMESPACEORENTITYID);
		int startIndex = iterator.getIndex();
		if (!parseRule_PackageID(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub1_CodeBlockNamespaceOrEntityID(ruleNode, true);
		
		if (!iterator.isToken_Backslash()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'BACKSLASH' expected in rule 'CODEBLOCKNAMESPACEORENTITYID'");
		}
		ruleNode.addToken(TokenType.BACKSLASH, iterator.readToken());
		
		parseRuleSub2_CodeBlockNamespaceOrEntityID(ruleNode, true);
		
		if (!iterator.isToken_NtIdentifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NTIDENTIFIER' expected in rule 'CODEBLOCKNAMESPACEORENTITYID'");
		}
		ruleNode.addToken(TokenType.NTIDENTIFIER, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_IntLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_BinaryIntLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'BINARYINTLITERAL' expected in rule 'INTLITERAL'");
		}
		ruleNode.addToken(TokenType.BINARYINTLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_IntLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_HexIntLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'HEXINTLITERAL' expected in rule 'INTLITERAL'");
		}
		ruleNode.addToken(TokenType.HEXINTLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_IntLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_DecimalIntLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'DECIMALINTLITERAL' expected in rule 'INTLITERAL'");
		}
		ruleNode.addToken(TokenType.DECIMALINTLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_IntLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_IntLiteral(ruleNode, true) || parseRuleSub2_IntLiteral(ruleNode, true) || parseRuleSub3_IntLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_BoolLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.BOOLLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TrueKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TRUEKEYWORD' expected in rule 'BOOLLITERAL'");
		}
		ruleNode.addToken(TokenType.TRUEKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_BoolLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.BOOLLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_FalseKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FALSEKEYWORD' expected in rule 'BOOLLITERAL'");
		}
		ruleNode.addToken(TokenType.FALSEKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_BoolLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.BOOLLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_BoolLiteral(ruleNode, true) || parseRuleSub2_BoolLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_CharLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CHARLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_SimpleChar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SIMPLECHAR' expected in rule 'CHARLITERAL'");
		}
		ruleNode.addToken(TokenType.SIMPLECHAR, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_CharLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CHARLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedChar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDCHAR' expected in rule 'CHARLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDCHAR, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_CharLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CHARLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_CharStart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CHARSTART' expected in rule 'CHARLITERAL'");
		}
		ruleNode.addToken(TokenType.CHARSTART, iterator.readToken());
		
		optional = false;
		
		if (!(parseRuleSub1_CharLiteral(ruleNode, true) || parseRuleSub2_CharLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		if (!iterator.isToken_CharEnd()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CHAREND' expected in rule 'CHARLITERAL'");
		}
		ruleNode.addToken(TokenType.CHAREND, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SimpleStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLESTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_SimpleStringLiteral(ruleNode, true) || parseRuleSub3_SimpleStringLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_SimpleStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLESTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_SimpleStringText()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SIMPLESTRINGTEXT' expected in rule 'SIMPLESTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.SIMPLESTRINGTEXT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_SimpleStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLESTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedSimpleStringChar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDSIMPLESTRINGCHAR' expected in rule 'SIMPLESTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDSIMPLESTRINGCHAR, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SimpleStringLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLESTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_SimpleStringStart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SIMPLESTRINGSTART' expected in rule 'SIMPLESTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.SIMPLESTRINGSTART, iterator.readToken());
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_SimpleStringLiteral(ruleNode, true))) {
				break;
			}
		}
		
		if (!iterator.isToken_SimpleStringEnd()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SIMPLESTRINGEND' expected in rule 'SIMPLESTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.SIMPLESTRINGEND, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_VerbatimStringContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TripleDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TRIPLEDOUBLEQUOTE' expected in rule 'VERBATIMSTRINGCONTENT'");
		}
		ruleNode.addToken(TokenType.TRIPLEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_VerbatimStringContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_VerbatimStringText()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERBATIMSTRINGTEXT' expected in rule 'VERBATIMSTRINGCONTENT'");
		}
		ruleNode.addToken(TokenType.VERBATIMSTRINGTEXT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_VerbatimStringContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TwoDoubleQuotes()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TWODOUBLEQUOTES' expected in rule 'VERBATIMSTRINGCONTENT'");
		}
		ruleNode.addToken(TokenType.TWODOUBLEQUOTES, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_VerbatimStringContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OneDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ONEDOUBLEQUOTE' expected in rule 'VERBATIMSTRINGCONTENT'");
		}
		ruleNode.addToken(TokenType.ONEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_VerbatimStringContent(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (parseRuleSub1_VerbatimStringContent(ruleNode, true)) {
			return false;
		}
		
		if (!(parseRuleSub2_VerbatimStringContent(ruleNode, true) || parseRuleSub3_VerbatimStringContent(ruleNode, true) || parseRuleSub4_VerbatimStringContent(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_VerbatimStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_VerbatimStringLiteral(ruleNode, true) || parseRuleSub3_VerbatimStringLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_VerbatimStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedTripleDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDTRIPLEDOUBLEQUOTE' expected in rule 'VERBATIMSTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDTRIPLEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_VerbatimStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_VerbatimStringContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_VerbatimStringLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMSTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_VerbatimStringStart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERBATIMSTRINGSTART' expected in rule 'VERBATIMSTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.VERBATIMSTRINGSTART, iterator.readToken());
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_VerbatimStringLiteral(ruleNode, true))) {
				break;
			}
		}
		
		if (!iterator.isToken_VerbatimStringEnd()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERBATIMSTRINGEND' expected in rule 'VERBATIMSTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.VERBATIMSTRINGEND, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_StringBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TripleDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TRIPLEDOUBLEQUOTE' expected in rule 'STRINGBLOCKCONTENT'");
		}
		ruleNode.addToken(TokenType.TRIPLEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_StringBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_StringBlockText()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STRINGBLOCKTEXT' expected in rule 'STRINGBLOCKCONTENT'");
		}
		ruleNode.addToken(TokenType.STRINGBLOCKTEXT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_StringBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TwoDoubleQuotes()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TWODOUBLEQUOTES' expected in rule 'STRINGBLOCKCONTENT'");
		}
		ruleNode.addToken(TokenType.TWODOUBLEQUOTES, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_StringBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OneDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ONEDOUBLEQUOTE' expected in rule 'STRINGBLOCKCONTENT'");
		}
		ruleNode.addToken(TokenType.ONEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_StringBlockContent(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (parseRuleSub1_StringBlockContent(ruleNode, true)) {
			return false;
		}
		
		if (!(parseRuleSub2_StringBlockContent(ruleNode, true) || parseRuleSub3_StringBlockContent(ruleNode, true) || parseRuleSub4_StringBlockContent(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_StringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_StringBlockLiteral(ruleNode, true) || parseRuleSub3_StringBlockLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_StringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedTripleDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDTRIPLEDOUBLEQUOTE' expected in rule 'STRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDTRIPLEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_StringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_StringBlockContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_StringBlockLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_StringBlockStart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STRINGBLOCKSTART' expected in rule 'STRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.STRINGBLOCKSTART, iterator.readToken());
		
		if (!iterator.isToken_StringBlockIgnoredWhitespace()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STRINGBLOCKIGNOREDWHITESPACE' expected in rule 'STRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.STRINGBLOCKIGNOREDWHITESPACE, iterator.readToken());
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_StringBlockLiteral(ruleNode, true))) {
				break;
			}
		}
		
		if (!iterator.isToken_StringBlockEnd()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STRINGBLOCKEND' expected in rule 'STRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.STRINGBLOCKEND, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_StringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_StringBlockLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_StringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_VerbatimStringLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_StringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_SimpleStringLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_StringLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_StringLiteral(ruleNode, true) || parseRuleSub2_StringLiteral(ruleNode, true) || parseRuleSub3_StringLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_InterpolatedArg(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERPOLATEDARG);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_InterpolatedArgDollar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'INTERPOLATEDARGDOLLAR' expected in rule 'INTERPOLATEDARG'");
		}
		ruleNode.addToken(TokenType.INTERPOLATEDARGDOLLAR, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'INTERPOLATEDARG'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_InterpolatedGroup(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERPOLATEDGROUP);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_InterpolatedGroup(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERPOLATEDGROUP);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_InterpolatedGroup(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERPOLATEDGROUP);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_InterpolatedArgDollar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'INTERPOLATEDARGDOLLAR' expected in rule 'INTERPOLATEDGROUP'");
		}
		ruleNode.addToken(TokenType.INTERPOLATEDARGDOLLAR, iterator.readToken());
		
		if (!iterator.isToken_OpeningParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGPARENTHESIS' expected in rule 'INTERPOLATEDGROUP'");
		}
		ruleNode.addToken(TokenType.OPENINGPARENTHESIS, iterator.readToken());
		
		optional = false;
		
		parseRuleSub1_InterpolatedGroup(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_InterpolatedGroup(ruleNode, true);
		
		if (!iterator.isToken_ClosingParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGPARENTHESIS' expected in rule 'INTERPOLATEDGROUP'");
		}
		ruleNode.addToken(TokenType.CLOSINGPARENTHESIS, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SimpleIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLEISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_SimpleIStringLiteral(ruleNode, true) || parseRuleSub3_SimpleIStringLiteral(ruleNode, true) || parseRuleSub4_SimpleIStringLiteral(ruleNode, true) || parseRuleSub5_SimpleIStringLiteral(ruleNode, true) || parseRuleSub6_SimpleIStringLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_SimpleIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLEISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedDollar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDDOLLAR' expected in rule 'SIMPLEISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDDOLLAR, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_SimpleIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLEISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterpolatedArg(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_SimpleIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLEISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterpolatedGroup(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_SimpleIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLEISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_SimpleIStringText()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SIMPLEISTRINGTEXT' expected in rule 'SIMPLEISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.SIMPLEISTRINGTEXT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_SimpleIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLEISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedSimpleIStringChar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDSIMPLEISTRINGCHAR' expected in rule 'SIMPLEISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDSIMPLEISTRINGCHAR, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SimpleIStringLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIMPLEISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_SimpleIStringStart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SIMPLEISTRINGSTART' expected in rule 'SIMPLEISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.SIMPLEISTRINGSTART, iterator.readToken());
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_SimpleIStringLiteral(ruleNode, true))) {
				break;
			}
		}
		
		if (!iterator.isToken_SimpleIStringEnd()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SIMPLEISTRINGEND' expected in rule 'SIMPLEISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.SIMPLEISTRINGEND, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_VerbatimIStringContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TripleDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TRIPLEDOUBLEQUOTE' expected in rule 'VERBATIMISTRINGCONTENT'");
		}
		ruleNode.addToken(TokenType.TRIPLEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_VerbatimIStringContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_VerbatimIStringText()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERBATIMISTRINGTEXT' expected in rule 'VERBATIMISTRINGCONTENT'");
		}
		ruleNode.addToken(TokenType.VERBATIMISTRINGTEXT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_VerbatimIStringContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TwoDoubleQuotes()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TWODOUBLEQUOTES' expected in rule 'VERBATIMISTRINGCONTENT'");
		}
		ruleNode.addToken(TokenType.TWODOUBLEQUOTES, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_VerbatimIStringContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OneDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ONEDOUBLEQUOTE' expected in rule 'VERBATIMISTRINGCONTENT'");
		}
		ruleNode.addToken(TokenType.ONEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_VerbatimIStringContent(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGCONTENT);
		int startIndex = iterator.getIndex();
		if (parseRuleSub1_VerbatimIStringContent(ruleNode, true)) {
			return false;
		}
		
		if (!(parseRuleSub2_VerbatimIStringContent(ruleNode, true) || parseRuleSub3_VerbatimIStringContent(ruleNode, true) || parseRuleSub4_VerbatimIStringContent(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_VerbatimIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_VerbatimIStringLiteral(ruleNode, true) || parseRuleSub3_VerbatimIStringLiteral(ruleNode, true) || parseRuleSub4_VerbatimIStringLiteral(ruleNode, true) || parseRuleSub5_VerbatimIStringLiteral(ruleNode, true) || parseRuleSub6_VerbatimIStringLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_VerbatimIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedDollar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDDOLLAR' expected in rule 'VERBATIMISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDDOLLAR, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_VerbatimIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterpolatedArg(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_VerbatimIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterpolatedGroup(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_VerbatimIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedTripleDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDTRIPLEDOUBLEQUOTE' expected in rule 'VERBATIMISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDTRIPLEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_VerbatimIStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_VerbatimIStringContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_VerbatimIStringLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VERBATIMISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_VerbatimIStringStart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERBATIMISTRINGSTART' expected in rule 'VERBATIMISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.VERBATIMISTRINGSTART, iterator.readToken());
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_VerbatimIStringLiteral(ruleNode, true))) {
				break;
			}
		}
		
		if (!iterator.isToken_VerbatimIStringEnd()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VERBATIMISTRINGEND' expected in rule 'VERBATIMISTRINGLITERAL'");
		}
		ruleNode.addToken(TokenType.VERBATIMISTRINGEND, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_IStringBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TripleDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TRIPLEDOUBLEQUOTE' expected in rule 'ISTRINGBLOCKCONTENT'");
		}
		ruleNode.addToken(TokenType.TRIPLEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_IStringBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_IStringBlockText()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ISTRINGBLOCKTEXT' expected in rule 'ISTRINGBLOCKCONTENT'");
		}
		ruleNode.addToken(TokenType.ISTRINGBLOCKTEXT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_IStringBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_TwoDoubleQuotes()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TWODOUBLEQUOTES' expected in rule 'ISTRINGBLOCKCONTENT'");
		}
		ruleNode.addToken(TokenType.TWODOUBLEQUOTES, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_IStringBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OneDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ONEDOUBLEQUOTE' expected in rule 'ISTRINGBLOCKCONTENT'");
		}
		ruleNode.addToken(TokenType.ONEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_IStringBlockContent(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (parseRuleSub1_IStringBlockContent(ruleNode, true)) {
			return false;
		}
		
		if (!(parseRuleSub2_IStringBlockContent(ruleNode, true) || parseRuleSub3_IStringBlockContent(ruleNode, true) || parseRuleSub4_IStringBlockContent(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_IStringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_IStringBlockLiteral(ruleNode, true) || parseRuleSub3_IStringBlockLiteral(ruleNode, true) || parseRuleSub4_IStringBlockLiteral(ruleNode, true) || parseRuleSub5_IStringBlockLiteral(ruleNode, true) || parseRuleSub6_IStringBlockLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_IStringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedDollar()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDDOLLAR' expected in rule 'ISTRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDDOLLAR, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_IStringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterpolatedArg(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_IStringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterpolatedGroup(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_IStringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EscapedTripleDoubleQuote()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ESCAPEDTRIPLEDOUBLEQUOTE' expected in rule 'ISTRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.ESCAPEDTRIPLEDOUBLEQUOTE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_IStringBlockLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_IStringBlockContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_IStringBlockLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGBLOCKLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_IStringBlockStart()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ISTRINGBLOCKSTART' expected in rule 'ISTRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.ISTRINGBLOCKSTART, iterator.readToken());
		
		if (!iterator.isToken_IStringBlockIgnoredWhitespace()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ISTRINGBLOCKIGNOREDWHITESPACE' expected in rule 'ISTRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.ISTRINGBLOCKIGNOREDWHITESPACE, iterator.readToken());
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_IStringBlockLiteral(ruleNode, true))) {
				break;
			}
		}
		
		if (!iterator.isToken_IStringBlockEnd()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ISTRINGBLOCKEND' expected in rule 'ISTRINGBLOCKLITERAL'");
		}
		ruleNode.addToken(TokenType.ISTRINGBLOCKEND, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_IStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_IStringBlockLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_IStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_VerbatimIStringLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_IStringLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_SimpleIStringLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_IStringLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ISTRINGLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_IStringLiteral(ruleNode, true) || parseRuleSub2_IStringLiteral(ruleNode, true) || parseRuleSub3_IStringLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ArrayLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ArrayLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		while (true) {
			if (!(parseRuleSub3_ArrayLiteral(ruleNode, true))) {
				break;
			}
		}
		
		parseRuleSub6_ArrayLiteral(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ArrayLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYLITERAL);
		int startIndex = iterator.getIndex();
		parseRuleSub4_ArrayLiteral(ruleNode, true);
		
		if (!iterator.isToken_Comma()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMMA' expected in rule 'ARRAYLITERAL'");
		}
		ruleNode.addToken(TokenType.COMMA, iterator.readToken());
		
		parseRuleSub5_ArrayLiteral(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_ArrayLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_ArrayLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_ArrayLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ArrayLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OpeningBracket()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGBRACKET' expected in rule 'ARRAYLITERAL'");
		}
		ruleNode.addToken(TokenType.OPENINGBRACKET, iterator.readToken());
		
		optional = false;
		
		parseRuleSub1_ArrayLiteral(ruleNode, true);
		
		parseRuleSub2_ArrayLiteral(ruleNode, true);
		
		if (!iterator.isToken_ClosingBracket()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGBRACKET' expected in rule 'ARRAYLITERAL'");
		}
		ruleNode.addToken(TokenType.CLOSINGBRACKET, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ConstantLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_FloatLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FLOATLITERAL' expected in rule 'CONSTANTLITERAL'");
		}
		ruleNode.addToken(TokenType.FLOATLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ConstantLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_IntLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ConstantLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_BoolLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_ConstantLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_CharLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_ConstantLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTLITERAL);
		int startIndex = iterator.getIndex();
		if (!parseRule_StringLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_ConstantLiteral(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTLITERAL);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NullKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NULLKEYWORD' expected in rule 'CONSTANTLITERAL'");
		}
		ruleNode.addToken(TokenType.NULLKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ConstantLiteral(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTLITERAL);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_ConstantLiteral(ruleNode, true) || parseRuleSub2_ConstantLiteral(ruleNode, true) || parseRuleSub3_ConstantLiteral(ruleNode, true) || parseRuleSub4_ConstantLiteral(ruleNode, true) || parseRuleSub5_ConstantLiteral(ruleNode, true) || parseRuleSub6_ConstantLiteral(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_LineEnd(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LINEEND);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'LINEEND'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_LineEnd(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LINEEND);
		int startIndex = iterator.getIndex();
		if (!parseRule_Comment(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_LineEnd(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LINEEND);
		int startIndex = iterator.getIndex();
		parseRuleSub1_LineEnd(ruleNode, true);
		
		parseRuleSub2_LineEnd(ruleNode, true);
		
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'LINEEND'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_EmptyLineContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EMPTYLINECONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'EMPTYLINECONTENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_EmptyLineContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EMPTYLINECONTENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_Comment(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_EmptyLineContent(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EMPTYLINECONTENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_EmptyLineContent(ruleNode, true);
		
		parseRuleSub2_EmptyLineContent(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_EmptyLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EMPTYLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLineContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'EMPTYLINE'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_EndLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENDLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ENDLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_EndLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENDLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_EndLine(ruleNode, true);
		
		if (!iterator.isToken_EndKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ENDKEYWORD' expected in rule 'ENDLINE'");
		}
		ruleNode.addToken(TokenType.ENDKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_LineContinuation(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LINECONTINUATION);
		int startIndex = iterator.getIndex();
		parseRuleSub2_LineContinuation(ruleNode, true);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub3_LineContinuation(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_LineContinuation(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LINECONTINUATION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'LINECONTINUATION'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_LineContinuation(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LINECONTINUATION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'LINECONTINUATION'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_LineContinuation(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LINECONTINUATION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'LINECONTINUATION'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_LineContinuation(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LINECONTINUATION);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_LineContinuation(ruleNode, true) || parseRuleSub4_LineContinuation(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ColonNotation(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'COLONNOTATION'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ColonNotation(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATION);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ColonNotation(ruleNode, true);
		
		if (!iterator.isToken_Colon()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COLON' expected in rule 'COLONNOTATION'");
		}
		ruleNode.addToken(TokenType.COLON, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_Using(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.USING);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'USING'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_Using(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.USING);
		int startIndex = iterator.getIndex();
		if (!parseRule_Comment(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Using(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.USING);
		int startIndex = iterator.getIndex();
		parseRuleSub1_Using(ruleNode, true);
		
		if (!iterator.isToken_UsingKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'USINGKEYWORD' expected in rule 'USING'");
		}
		ruleNode.addToken(TokenType.USINGKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'USING'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_UsingNamespaceID(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_Using(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_UsingLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.USINGLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_Using(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'USINGLINE'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NamespaceHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NAMESPACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NamespaceHeadLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEHEADLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_NamespaceHeadLine(ruleNode, true);
		
		if (!iterator.isToken_NamespaceKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NAMESPACEKEYWORD' expected in rule 'NAMESPACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.NAMESPACEKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NAMESPACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_NamespaceID(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NamespaceEnd(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEEND);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NAMESPACEEND'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_NamespaceEnd(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEEND);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NAMESPACEEND'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_NamespaceEnd(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEEND);
		int startIndex = iterator.getIndex();
		if (!parseRule_Comment(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NamespaceEnd(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACEEND);
		int startIndex = iterator.getIndex();
		parseRuleSub1_NamespaceEnd(ruleNode, true);
		
		if (!iterator.isToken_EndKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ENDKEYWORD' expected in rule 'NAMESPACEEND'");
		}
		ruleNode.addToken(TokenType.ENDKEYWORD, iterator.readToken());
		
		parseRuleSub2_NamespaceEnd(ruleNode, true);
		
		parseRuleSub3_NamespaceEnd(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_Namespace(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACE);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_Namespace(ruleNode, true) || parseRuleSub3_Namespace(ruleNode, true) || parseRuleSub4_Namespace(ruleNode, true) || parseRuleSub5_Namespace(ruleNode, true) || parseRuleSub6_Namespace(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_Namespace(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACE);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_Namespace(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACE);
		int startIndex = iterator.getIndex();
		if (!parseRule_UsingLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_Namespace(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACE);
		int startIndex = iterator.getIndex();
		if (!parseRule_ClassLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_Namespace(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACE);
		int startIndex = iterator.getIndex();
		if (!parseRule_EnumLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_Namespace(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACE);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterfaceLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Namespace(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NAMESPACE);
		int startIndex = iterator.getIndex();
		if (!parseRule_NamespaceHeadLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_Namespace(ruleNode, true))) {
				break;
			}
		}
		
		if (!parseRule_NamespaceEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_VarArgParameter(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VARARGPARAMETER);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_ParamsKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PARAMSKEYWORD' expected in rule 'VARARGPARAMETER'");
		}
		ruleNode.addToken(TokenType.PARAMSKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'VARARGPARAMETER'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_ParamName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PARAMNAME' expected in rule 'VARARGPARAMETER'");
		}
		ruleNode.addToken(TokenType.PARAMNAME, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NormalParameter(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPARAMETER);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_ParamsKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PARAMSKEYWORD' expected in rule 'NORMALPARAMETER'");
		}
		ruleNode.addToken(TokenType.PARAMSKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NormalParameter(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPARAMETER);
		int startIndex = iterator.getIndex();
		if (parseRuleSub1_NormalParameter(ruleNode, true)) {
			return false;
		}
		
		if (!iterator.isToken_ParamName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PARAMNAME' expected in rule 'NORMALPARAMETER'");
		}
		ruleNode.addToken(TokenType.PARAMNAME, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NoParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NOPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NOPARAMETERS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NoParameters(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NOPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OpeningParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGPARENTHESIS' expected in rule 'NOPARAMETERS'");
		}
		ruleNode.addToken(TokenType.OPENINGPARENTHESIS, iterator.readToken());
		
		parseRuleSub1_NoParameters(ruleNode, true);
		
		if (!iterator.isToken_ClosingParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGPARENTHESIS' expected in rule 'NOPARAMETERS'");
		}
		ruleNode.addToken(TokenType.CLOSINGPARENTHESIS, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_OnlyVarArgParameter(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ONLYVARARGPARAMETER);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ONLYVARARGPARAMETER'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_OnlyVarArgParameter(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ONLYVARARGPARAMETER);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ONLYVARARGPARAMETER'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_OnlyVarArgParameter(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ONLYVARARGPARAMETER);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OpeningParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGPARENTHESIS' expected in rule 'ONLYVARARGPARAMETER'");
		}
		ruleNode.addToken(TokenType.OPENINGPARENTHESIS, iterator.readToken());
		
		parseRuleSub1_OnlyVarArgParameter(ruleNode, true);
		
		if (!parseRule_VarArgParameter(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_OnlyVarArgParameter(ruleNode, true);
		
		if (!iterator.isToken_ClosingParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGPARENTHESIS' expected in rule 'ONLYVARARGPARAMETER'");
		}
		ruleNode.addToken(TokenType.CLOSINGPARENTHESIS, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NormalParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NORMALPARAMETERS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_NormalParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPARAMETERS);
		int startIndex = iterator.getIndex();
		parseRuleSub3_NormalParameters(ruleNode, true);
		
		if (!iterator.isToken_Comma()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMMA' expected in rule 'NORMALPARAMETERS'");
		}
		ruleNode.addToken(TokenType.COMMA, iterator.readToken());
		
		parseRuleSub4_NormalParameters(ruleNode, true);
		
		if (!parseRule_NormalParameter(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_NormalParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NORMALPARAMETERS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_NormalParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_NormalParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NORMALPARAMETERS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NormalParameters(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OpeningParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGPARENTHESIS' expected in rule 'NORMALPARAMETERS'");
		}
		ruleNode.addToken(TokenType.OPENINGPARENTHESIS, iterator.readToken());
		
		parseRuleSub1_NormalParameters(ruleNode, true);
		
		if (!iterator.isToken_ParamName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PARAMNAME' expected in rule 'NORMALPARAMETERS'");
		}
		ruleNode.addToken(TokenType.PARAMNAME, iterator.readToken());
		
		while (true) {
			if (!(parseRuleSub2_NormalParameters(ruleNode, true))) {
				break;
			}
		}
		
		parseRuleSub5_NormalParameters(ruleNode, true);
		
		if (!iterator.isToken_ClosingParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGPARENTHESIS' expected in rule 'NORMALPARAMETERS'");
		}
		ruleNode.addToken(TokenType.CLOSINGPARENTHESIS, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NormalPlusVarArgParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_NormalPlusVarArgParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		parseRuleSub3_NormalPlusVarArgParameters(ruleNode, true);
		
		if (!iterator.isToken_Comma()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMMA' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.COMMA, iterator.readToken());
		
		parseRuleSub4_NormalPlusVarArgParameters(ruleNode, true);
		
		if (!parseRule_NormalParameter(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_NormalPlusVarArgParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_NormalPlusVarArgParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_NormalPlusVarArgParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		parseRuleSub6_NormalPlusVarArgParameters(ruleNode, true);
		
		if (!iterator.isToken_Comma()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMMA' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.COMMA, iterator.readToken());
		
		parseRuleSub7_NormalPlusVarArgParameters(ruleNode, true);
		
		if (!parseRule_VarArgParameter(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_NormalPlusVarArgParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_NormalPlusVarArgParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_NormalPlusVarArgParameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NormalPlusVarArgParameters(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NORMALPLUSVARARGPARAMETERS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OpeningParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGPARENTHESIS' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.OPENINGPARENTHESIS, iterator.readToken());
		
		parseRuleSub1_NormalPlusVarArgParameters(ruleNode, true);
		
		if (!iterator.isToken_ParamName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PARAMNAME' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.PARAMNAME, iterator.readToken());
		
		while (true) {
			if (!(parseRuleSub2_NormalPlusVarArgParameters(ruleNode, true))) {
				break;
			}
		}
		
		parseRuleSub5_NormalPlusVarArgParameters(ruleNode, true);
		
		parseRuleSub8_NormalPlusVarArgParameters(ruleNode, true);
		
		if (!iterator.isToken_ClosingParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGPARENTHESIS' expected in rule 'NORMALPLUSVARARGPARAMETERS'");
		}
		ruleNode.addToken(TokenType.CLOSINGPARENTHESIS, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_Parameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PARAMETERS);
		int startIndex = iterator.getIndex();
		if (!parseRule_NoParameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_Parameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PARAMETERS);
		int startIndex = iterator.getIndex();
		if (!parseRule_OnlyVarArgParameter(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_Parameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PARAMETERS);
		int startIndex = iterator.getIndex();
		if (!parseRule_NormalParameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_Parameters(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PARAMETERS);
		int startIndex = iterator.getIndex();
		if (!parseRule_NormalPlusVarArgParameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Parameters(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PARAMETERS);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_Parameters(ruleNode, true) || parseRuleSub2_Parameters(ruleNode, true) || parseRuleSub3_Parameters(ruleNode, true) || parseRuleSub4_Parameters(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ConstantAssignment(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTASSIGNMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CONSTANTASSIGNMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ConstantAssignment(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTASSIGNMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CONSTANTASSIGNMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ConstantAssignment(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTANTASSIGNMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ConstantAssignment(ruleNode, true);
		
		if (!iterator.isToken_ConstantValueAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CONSTANTVALUEASSIGN' expected in rule 'CONSTANTASSIGNMENT'");
		}
		ruleNode.addToken(TokenType.CONSTANTVALUEASSIGN, iterator.readToken());
		
		optional = false;
		
		parseRuleSub2_ConstantAssignment(ruleNode, true);
		
		if (!parseRule_ConstantLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ClassType(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSTYPE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_AbstractKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ABSTRACTKEYWORD' expected in rule 'CLASSTYPE'");
		}
		ruleNode.addToken(TokenType.ABSTRACTKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ClassType(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSTYPE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_StaticKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STATICKEYWORD' expected in rule 'CLASSTYPE'");
		}
		ruleNode.addToken(TokenType.STATICKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ClassType(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSTYPE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NativeKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NATIVEKEYWORD' expected in rule 'CLASSTYPE'");
		}
		ruleNode.addToken(TokenType.NATIVEKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ClassType(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSTYPE);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_ClassType(ruleNode, true) || parseRuleSub2_ClassType(ruleNode, true) || parseRuleSub3_ClassType(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ClassHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ClassHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_InternalKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'INTERNALKEYWORD' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.INTERNALKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ClassHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NativeKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NATIVEKEYWORD' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.NATIVEKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_ClassHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSHEADLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_ClassType(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_ClassHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSHEADLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_Extends(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_ClassHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSHEADLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_Implements(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ClassHeadLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSHEADLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ClassHeadLine(ruleNode, true);
		
		parseRuleSub2_ClassHeadLine(ruleNode, true);
		
		parseRuleSub3_ClassHeadLine(ruleNode, true);
		
		parseRuleSub4_ClassHeadLine(ruleNode, true);
		
		if (!iterator.isToken_ClassKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLASSKEYWORD' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.CLASSKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_TypeName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TYPENAME' expected in rule 'CLASSHEADLINE'");
		}
		ruleNode.addToken(TokenType.TYPENAME, iterator.readToken());
		
		parseRuleSub5_ClassHeadLine(ruleNode, true);
		
		parseRuleSub6_ClassHeadLine(ruleNode, true);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_Extends(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXTENDS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'EXTENDS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_ExtendsKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'EXTENDSKEYWORD' expected in rule 'EXTENDS'");
		}
		ruleNode.addToken(TokenType.EXTENDSKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'EXTENDS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_TypeName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TYPENAME' expected in rule 'EXTENDS'");
		}
		ruleNode.addToken(TokenType.TYPENAME, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_Implements(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IMPLEMENTS);
		int startIndex = iterator.getIndex();
		parseRuleSub2_Implements(ruleNode, true);
		
		if (!iterator.isToken_Comma()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMMA' expected in rule 'IMPLEMENTS'");
		}
		ruleNode.addToken(TokenType.COMMA, iterator.readToken());
		
		optional = false;
		
		parseRuleSub3_Implements(ruleNode, true);
		
		if (!iterator.isToken_TypeName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TYPENAME' expected in rule 'IMPLEMENTS'");
		}
		ruleNode.addToken(TokenType.TYPENAME, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_Implements(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IMPLEMENTS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'IMPLEMENTS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_Implements(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IMPLEMENTS);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Implements(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IMPLEMENTS);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'IMPLEMENTS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_ImplementsKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IMPLEMENTSKEYWORD' expected in rule 'IMPLEMENTS'");
		}
		ruleNode.addToken(TokenType.IMPLEMENTSKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'IMPLEMENTS'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_TypeName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TYPENAME' expected in rule 'IMPLEMENTS'");
		}
		ruleNode.addToken(TokenType.TYPENAME, iterator.readToken());
		
		while (true) {
			if (!(parseRuleSub1_Implements(ruleNode, true))) {
				break;
			}
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_FieldLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FIELDLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FIELDLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_FieldLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FIELDLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_StaticKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STATICKEYWORD' expected in rule 'FIELDLINE'");
		}
		ruleNode.addToken(TokenType.STATICKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FIELDLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_FieldLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FIELDLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_ReadOnlyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'READONLYKEYWORD' expected in rule 'FIELDLINE'");
		}
		ruleNode.addToken(TokenType.READONLYKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FIELDLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_FieldLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FIELDLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_ConstantAssignment(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_FieldLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FIELDLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_FieldLine(ruleNode, true);
		
		parseRuleSub2_FieldLine(ruleNode, true);
		
		parseRuleSub3_FieldLine(ruleNode, true);
		
		if (!iterator.isToken_FieldKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FIELDKEYWORD' expected in rule 'FIELDLINE'");
		}
		ruleNode.addToken(TokenType.FIELDKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FIELDLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'FIELDLINE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parseRuleSub4_FieldLine(ruleNode, true);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_MethodHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.METHODHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'METHODHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_MethodHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.METHODHEAD);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub3_MethodHead(ruleNode, true) || parseRuleSub4_MethodHead(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'METHODHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_MethodHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.METHODHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_StaticKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STATICKEYWORD' expected in rule 'METHODHEAD'");
		}
		ruleNode.addToken(TokenType.STATICKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_MethodHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.METHODHEAD);
		int startIndex = iterator.getIndex();
		if (!parseRule_OverrideType(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_MethodHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.METHODHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'METHODHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_MethodHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.METHODHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_MethodHead(ruleNode, true);
		
		parseRuleSub2_MethodHead(ruleNode, true);
		
		if (!iterator.isToken_MethodKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'METHODKEYWORD' expected in rule 'METHODHEAD'");
		}
		ruleNode.addToken(TokenType.METHODKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'METHODHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'METHODHEAD'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parseRuleSub5_MethodHead(ruleNode, true);
		
		if (!parseRule_Parameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_MethodLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.METHODLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_MethodHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_ColonNotationOrCodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ConstructorHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTRUCTORHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CONSTRUCTORHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ConstructorHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTRUCTORHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CONSTRUCTORHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ConstructorHeadLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTRUCTORHEADLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ConstructorHeadLine(ruleNode, true);
		
		if (!iterator.isToken_ConstructorKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CONSTRUCTORKEYWORD' expected in rule 'CONSTRUCTORHEADLINE'");
		}
		ruleNode.addToken(TokenType.CONSTRUCTORKEYWORD, iterator.readToken());
		
		optional = false;
		
		parseRuleSub2_ConstructorHeadLine(ruleNode, true);
		
		if (!parseRule_Parameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ConstructorLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTRUCTORLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ConstructorLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONSTRUCTORLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_ConstructorHeadLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		parseRuleSub1_ConstructorLines(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_OverrideType(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.OVERRIDETYPE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_ForceKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FORCEKEYWORD' expected in rule 'OVERRIDETYPE'");
		}
		ruleNode.addToken(TokenType.FORCEKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'OVERRIDETYPE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_OverrideKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OVERRIDEKEYWORD' expected in rule 'OVERRIDETYPE'");
		}
		ruleNode.addToken(TokenType.OVERRIDEKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_OverrideType(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.OVERRIDETYPE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OverrideKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OVERRIDEKEYWORD' expected in rule 'OVERRIDETYPE'");
		}
		ruleNode.addToken(TokenType.OVERRIDEKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_OverrideType(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.OVERRIDETYPE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OverridableKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OVERRIDABLEKEYWORD' expected in rule 'OVERRIDETYPE'");
		}
		ruleNode.addToken(TokenType.OVERRIDABLEKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_OverrideType(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.OVERRIDETYPE);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_OverrideType(ruleNode, true) || parseRuleSub2_OverrideType(ruleNode, true) || parseRuleSub3_OverrideType(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NativeConstructorLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NATIVECONSTRUCTORLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVECONSTRUCTORLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_NativeConstructorLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NATIVECONSTRUCTORLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVECONSTRUCTORLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NativeConstructorLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NATIVECONSTRUCTORLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_NativeConstructorLine(ruleNode, true);
		
		if (!iterator.isToken_NativeKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NATIVEKEYWORD' expected in rule 'NATIVECONSTRUCTORLINE'");
		}
		ruleNode.addToken(TokenType.NATIVEKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVECONSTRUCTORLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_ConstructorKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CONSTRUCTORKEYWORD' expected in rule 'NATIVECONSTRUCTORLINE'");
		}
		ruleNode.addToken(TokenType.CONSTRUCTORKEYWORD, iterator.readToken());
		
		optional = false;
		
		parseRuleSub2_NativeConstructorLine(ruleNode, true);
		
		if (!parseRule_Parameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NativeMethodLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NATIVEMETHODLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_NativeMethodLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NATIVEMETHODLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_StaticKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STATICKEYWORD' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.STATICKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_NativeMethodLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NATIVEMETHODLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_OverrideType(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_NativeMethodLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NATIVEMETHODLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NativeMethodLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NATIVEMETHODLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_NativeMethodLine(ruleNode, true);
		
		if (!iterator.isToken_NativeKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NATIVEKEYWORD' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.NATIVEKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parseRuleSub2_NativeMethodLine(ruleNode, true);
		
		parseRuleSub3_NativeMethodLine(ruleNode, true);
		
		if (!iterator.isToken_MethodKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'METHODKEYWORD' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.METHODKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'NATIVEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parseRuleSub4_NativeMethodLine(ruleNode, true);
		
		if (!parseRule_Parameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_AbstractMethodLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ABSTRACTMETHODLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ABSTRACTMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_AbstractMethodLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ABSTRACTMETHODLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ABSTRACTMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_AbstractMethodLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ABSTRACTMETHODLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_AbstractMethodLine(ruleNode, true);
		
		if (!iterator.isToken_AbstractKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ABSTRACTKEYWORD' expected in rule 'ABSTRACTMETHODLINE'");
		}
		ruleNode.addToken(TokenType.ABSTRACTKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ABSTRACTMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_MethodKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'METHODKEYWORD' expected in rule 'ABSTRACTMETHODLINE'");
		}
		ruleNode.addToken(TokenType.METHODKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ABSTRACTMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'ABSTRACTMETHODLINE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parseRuleSub2_AbstractMethodLine(ruleNode, true);
		
		if (!parseRule_Parameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_GetHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.GETHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'GETHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_GetHeadLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.GETHEADLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_GetHeadLine(ruleNode, true);
		
		if (!iterator.isToken_GetKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'GETKEYWORD' expected in rule 'GETHEADLINE'");
		}
		ruleNode.addToken(TokenType.GETKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_GetLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.GETLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_GetLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.GETLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_GetHeadLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		parseRuleSub1_GetLines(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SingleGetLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEGETLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SINGLEGETLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SingleGetLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEGETLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_SingleGetLine(ruleNode, true);
		
		if (!iterator.isToken_GetKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'GETKEYWORD' expected in rule 'SINGLEGETLINE'");
		}
		ruleNode.addToken(TokenType.GETKEYWORD, iterator.readToken());
		
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_ColonNotationStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SetHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SETHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SETHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SetHeadLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SETHEADLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_SetHeadLine(ruleNode, true);
		
		if (!iterator.isToken_SetKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SETKEYWORD' expected in rule 'SETHEADLINE'");
		}
		ruleNode.addToken(TokenType.SETKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SetLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SETLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SetLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SETLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_SetHeadLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		parseRuleSub1_SetLines(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SingleSetLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLESETLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SINGLESETLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SingleSetLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLESETLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_SingleSetLine(ruleNode, true);
		
		if (!iterator.isToken_SetKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SETKEYWORD' expected in rule 'SINGLESETLINE'");
		}
		ruleNode.addToken(TokenType.SETKEYWORD, iterator.readToken());
		
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_ColonNotationStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ColonNotationStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATIONSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ColonNotationStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATIONSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ColonNotationStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATIONSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_ColonNotationStatement(ruleNode, true) || parseRuleSub2_ColonNotationStatement(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_PropertyHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PROPERTYHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_PropertyHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYHEADLINE);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub3_PropertyHeadLine(ruleNode, true) || parseRuleSub4_PropertyHeadLine(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PROPERTYHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_PropertyHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_StaticKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STATICKEYWORD' expected in rule 'PROPERTYHEADLINE'");
		}
		ruleNode.addToken(TokenType.STATICKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_PropertyHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYHEADLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_OverrideType(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_PropertyHeadLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYHEADLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_PropertyHeadLine(ruleNode, true);
		
		parseRuleSub2_PropertyHeadLine(ruleNode, true);
		
		if (!iterator.isToken_PropertyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PROPERTYKEYWORD' expected in rule 'PROPERTYHEADLINE'");
		}
		ruleNode.addToken(TokenType.PROPERTYKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PROPERTYHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'PROPERTYHEADLINE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_PropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_PropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_SingleGetLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_PropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_GetLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_PropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_PropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_SingleSetLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_PropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_SetLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_PropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_PropertyLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_PropertyHeadLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_PropertyLines(ruleNode, true))) {
				break;
			}
		}
		
		if (!(parseRuleSub2_PropertyLines(ruleNode, true) || parseRuleSub3_PropertyLines(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		while (true) {
			if (!(parseRuleSub4_PropertyLines(ruleNode, true))) {
				break;
			}
		}
		
		if (!(parseRuleSub5_PropertyLines(ruleNode, true) || parseRuleSub6_PropertyLines(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		while (true) {
			if (!(parseRuleSub7_PropertyLines(ruleNode, true))) {
				break;
			}
		}
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_AbstractPropertyLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ABSTRACTPROPERTYLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ABSTRACTPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_AbstractPropertyLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ABSTRACTPROPERTYLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_ReadOnlyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'READONLYKEYWORD' expected in rule 'ABSTRACTPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.READONLYKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ABSTRACTPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_AbstractPropertyLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ABSTRACTPROPERTYLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_AbstractPropertyLine(ruleNode, true);
		
		if (!iterator.isToken_AbstractKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ABSTRACTKEYWORD' expected in rule 'ABSTRACTPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.ABSTRACTKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ABSTRACTPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parseRuleSub2_AbstractPropertyLine(ruleNode, true);
		
		if (!iterator.isToken_PropertyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PROPERTYKEYWORD' expected in rule 'ABSTRACTPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.PROPERTYKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ABSTRACTPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'ABSTRACTPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ColonNotationOrCodeBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATIONORCODEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ColonNotationOrCodeBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATIONORCODEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub3_ColonNotationOrCodeBlock(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ColonNotationOrCodeBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATIONORCODEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ColonNotationOrCodeBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.COLONNOTATIONORCODEBLOCK);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_ColonNotationOrCodeBlock(ruleNode, true) || parseRuleSub2_ColonNotationOrCodeBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ReadOnlyPropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.READONLYPROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'READONLYPROPERTYLINES'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ReadOnlyPropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.READONLYPROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub3_ReadOnlyPropertyLines(ruleNode, true) || parseRuleSub4_ReadOnlyPropertyLines(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'READONLYPROPERTYLINES'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ReadOnlyPropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.READONLYPROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_StaticKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'STATICKEYWORD' expected in rule 'READONLYPROPERTYLINES'");
		}
		ruleNode.addToken(TokenType.STATICKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_ReadOnlyPropertyLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.READONLYPROPERTYLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_OverrideType(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ReadOnlyPropertyLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.READONLYPROPERTYLINES);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ReadOnlyPropertyLines(ruleNode, true);
		
		parseRuleSub2_ReadOnlyPropertyLines(ruleNode, true);
		
		if (!iterator.isToken_ReadOnlyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'READONLYKEYWORD' expected in rule 'READONLYPROPERTYLINES'");
		}
		ruleNode.addToken(TokenType.READONLYKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'READONLYPROPERTYLINES'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_PropertyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PROPERTYKEYWORD' expected in rule 'READONLYPROPERTYLINES'");
		}
		ruleNode.addToken(TokenType.PROPERTYKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'READONLYPROPERTYLINES'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'READONLYPROPERTYLINES'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		if (!parseRule_ColonNotationOrCodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_ClassLines(ruleNode, true) || parseRuleSub3_ClassLines(ruleNode, true) || parseRuleSub4_ClassLines(ruleNode, true) || parseRuleSub5_ClassLines(ruleNode, true) || parseRuleSub6_ClassLines(ruleNode, true) || parseRuleSub7_ClassLines(ruleNode, true) || parseRuleSub8_ClassLines(ruleNode, true) || parseRuleSub9_ClassLines(ruleNode, true) || parseRuleSub10_ClassLines(ruleNode, true) || parseRuleSub11_ClassLines(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_FieldLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_MethodLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_ConstructorLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_AbstractMethodLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_NativeMethodLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_NativeConstructorLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub9_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_PropertyLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub10_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_ReadOnlyPropertyLines(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub11_ClassLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_AbstractPropertyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ClassLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CLASSLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_ClassHeadLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_ClassLines(ruleNode, true))) {
				break;
			}
		}
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_CodeBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CODEBLOCK);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_CodeBlock(ruleNode, true) || parseRuleSub3_CodeBlock(ruleNode, true) || parseRuleSub4_CodeBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_CodeBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CODEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_CodeBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CODEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_StatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_CodeBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CODEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_CodeBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CODEBLOCK);
		int startIndex = iterator.getIndex();
		while (true) {
			if (!(parseRuleSub1_CodeBlock(ruleNode, true))) {
				break;
			}
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_VarDefStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VARDEFSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'VARDEFSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_VarDefStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VARDEFSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_VarDefStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VARDEFSTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub4_VarDefStatement(ruleNode, true);
		
		if (!iterator.isToken_Assignment()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ASSIGNMENT' expected in rule 'VARDEFSTATEMENT'");
		}
		ruleNode.addToken(TokenType.ASSIGNMENT, iterator.readToken());
		
		parseRuleSub5_VarDefStatement(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_VarDefStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VARDEFSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'VARDEFSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_VarDefStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VARDEFSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'VARDEFSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_VarDefStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.VARDEFSTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_VarDefStatement(ruleNode, true);
		
		if (!iterator.isToken_VarKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VARKEYWORD' expected in rule 'VARDEFSTATEMENT'");
		}
		ruleNode.addToken(TokenType.VARKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'VARDEFSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'VARDEFSTATEMENT'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		optional = false;
		
		if (!(parseRuleSub2_VarDefStatement(ruleNode, true) || parseRuleSub3_VarDefStatement(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ReturnStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.RETURNSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'RETURNSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ReturnStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.RETURNSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'RETURNSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ReturnStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.RETURNSTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ReturnStatement(ruleNode, true);
		
		if (!iterator.isToken_ReturnKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'RETURNKEYWORD' expected in rule 'RETURNSTATEMENT'");
		}
		ruleNode.addToken(TokenType.RETURNKEYWORD, iterator.readToken());
		
		parseRuleSub2_ReturnStatement(ruleNode, true);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ThrowStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.THROWSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'THROWSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ThrowStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.THROWSTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ThrowStatement(ruleNode, true);
		
		if (!iterator.isToken_ThrowKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'THROWKEYWORD' expected in rule 'THROWSTATEMENT'");
		}
		ruleNode.addToken(TokenType.THROWKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'THROWSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_BreakStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.BREAKSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'BREAKSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_BreakStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.BREAKSTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_BreakStatement(ruleNode, true);
		
		if (!iterator.isToken_BreakKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'BREAKKEYWORD' expected in rule 'BREAKSTATEMENT'");
		}
		ruleNode.addToken(TokenType.BREAKKEYWORD, iterator.readToken());
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ContinueStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONTINUESTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CONTINUESTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ContinueStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CONTINUESTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ContinueStatement(ruleNode, true);
		
		if (!iterator.isToken_ContinueKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CONTINUEKEYWORD' expected in rule 'CONTINUESTATEMENT'");
		}
		ruleNode.addToken(TokenType.CONTINUEKEYWORD, iterator.readToken());
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_IncrementDecrementStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INCREMENTDECREMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INCREMENTDECREMENTSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_IncrementDecrementStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INCREMENTDECREMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INCREMENTDECREMENTSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_IncrementDecrementStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INCREMENTDECREMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Increment()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'INCREMENT' expected in rule 'INCREMENTDECREMENTSTATEMENT'");
		}
		ruleNode.addToken(TokenType.INCREMENT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_IncrementDecrementStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INCREMENTDECREMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Decrement()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'DECREMENT' expected in rule 'INCREMENTDECREMENTSTATEMENT'");
		}
		ruleNode.addToken(TokenType.DECREMENT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_IncrementDecrementStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INCREMENTDECREMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_IncrementDecrementStatement(ruleNode, true);
		
		if (!parseRule_Assignable(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_IncrementDecrementStatement(ruleNode, true);
		
		if (!(parseRuleSub3_IncrementDecrementStatement(ruleNode, true) || parseRuleSub4_IncrementDecrementStatement(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_SingleForeachLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEFOREACHLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_ForeachHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_SingleForLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEFORLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_ForHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_SingleWhileLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEWHILELINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_WhileHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_SingleUntilLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEUNTILLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_UntilHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_SingleIfLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEIFLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_IfHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_SingleTryLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLETRYLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_TryHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ExpressionStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSIONSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'EXPRESSIONSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ExpressionStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSIONSTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ExpressionStatement(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Assignment()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ASSIGNMENT' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.ASSIGNMENT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_PlusAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PLUSASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.PLUSASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_MinusAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'MINUSASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.MINUSASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_MultiplyAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'MULTIPLYASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.MULTIPLYASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_DivideAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'DIVIDEASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.DIVIDEASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_ModuloAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'MODULOASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.MODULOASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_AndAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ANDASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.ANDASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OrAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ORASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.ORASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub9_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_XorAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'XORASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.XORASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub10_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_LeftShiftAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LEFTSHIFTASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.LEFTSHIFTASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub11_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_RightShiftAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'RIGHTSHIFTASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.RIGHTSHIFTASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub12_AssignmentOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NullDefaultAssign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NULLDEFAULTASSIGN' expected in rule 'ASSIGNMENTOPERATOR'");
		}
		ruleNode.addToken(TokenType.NULLDEFAULTASSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_AssignmentOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTOPERATOR);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_AssignmentOperator(ruleNode, true) || parseRuleSub2_AssignmentOperator(ruleNode, true) || parseRuleSub3_AssignmentOperator(ruleNode, true) || parseRuleSub4_AssignmentOperator(ruleNode, true) || parseRuleSub5_AssignmentOperator(ruleNode, true) || parseRuleSub6_AssignmentOperator(ruleNode, true) || parseRuleSub7_AssignmentOperator(ruleNode, true) || parseRuleSub8_AssignmentOperator(ruleNode, true) || parseRuleSub9_AssignmentOperator(ruleNode, true) || parseRuleSub10_AssignmentOperator(ruleNode, true) || parseRuleSub11_AssignmentOperator(ruleNode, true) || parseRuleSub12_AssignmentOperator(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_Assignable(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNABLE);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlockNamespaceOrEntityID(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_Assignable(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNABLE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'ASSIGNABLE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_Assignable(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNABLE);
		int startIndex = iterator.getIndex();
		parseRuleSub4_Assignable(ruleNode, true);
		
		if (!parseRule_SequentialOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_Assignable(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNABLE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ASSIGNABLE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Assignable(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNABLE);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_Assignable(ruleNode, true) || parseRuleSub2_Assignable(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parseRuleSub3_Assignable(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_AssignmentStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ASSIGNMENTSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_AssignmentStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ASSIGNMENTSTATEMENT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_AssignmentStatement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_AssignmentStatement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ASSIGNMENTSTATEMENT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_AssignmentStatement(ruleNode, true);
		
		if (!parseRule_Assignable(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_AssignmentStatement(ruleNode, true);
		
		if (!parseRule_AssignmentOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub3_AssignmentStatement(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_IncrementDecrementStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_BreakStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_ContinueStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_AssignmentStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_VarDefStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_ReturnStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_ThrowStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_SingleTryLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub9_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_SingleForeachLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub10_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_SingleForLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub11_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_SingleWhileLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub12_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_SingleUntilLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub13_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_SingleIfLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub14_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (parseRuleSub15_Statement(ruleNode, true)) {
			return false;
		}
		
		if (parseRuleSub16_Statement(ruleNode, true)) {
			return false;
		}
		
		if (parseRuleSub17_Statement(ruleNode, true)) {
			return false;
		}
		
		if (!parseRule_ExpressionStatement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub15_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub16_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_LoopLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub17_Statement(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_ElseKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ELSEKEYWORD' expected in rule 'STATEMENT'");
		}
		ruleNode.addToken(TokenType.ELSEKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Statement(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENT);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_Statement(ruleNode, true) || parseRuleSub2_Statement(ruleNode, true) || parseRuleSub3_Statement(ruleNode, true) || parseRuleSub4_Statement(ruleNode, true) || parseRuleSub5_Statement(ruleNode, true) || parseRuleSub6_Statement(ruleNode, true) || parseRuleSub7_Statement(ruleNode, true) || parseRuleSub8_Statement(ruleNode, true) || parseRuleSub9_Statement(ruleNode, true) || parseRuleSub10_Statement(ruleNode, true) || parseRuleSub11_Statement(ruleNode, true) || parseRuleSub12_Statement(ruleNode, true) || parseRuleSub13_Statement(ruleNode, true) || parseRuleSub14_Statement(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ForeachHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOREACHHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ForeachHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOREACHHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_VarKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VARKEYWORD' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.VARKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ForeachHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOREACHHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ForeachHead(ruleNode, true);
		
		if (!iterator.isToken_ForeachKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FOREACHKEYWORD' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.FOREACHKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		optional = false;
		
		parseRuleSub2_ForeachHead(ruleNode, true);
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_InKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'INKEYWORD' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.INKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FOREACHHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ForeachStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOREACHSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ForeachStatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOREACHSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ForeachHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub1_ForeachStatementBlock(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ForHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ForHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_VarKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'VARKEYWORD' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.VARKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ForHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_ForHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_ForHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_ByKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'BYKEYWORD' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.BYKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parseRuleSub6_ForHead(ruleNode, true);
		
		if (!(parseRuleSub7_ForHead(ruleNode, true) || parseRuleSub8_ForHead(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_ForHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Minus()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'MINUS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.MINUS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_ForHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_FloatLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FLOATLITERAL' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.FLOATLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_ForHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		if (!parseRule_IntLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ForHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ForHead(ruleNode, true);
		
		if (!iterator.isToken_ForKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FORKEYWORD' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.FORKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		optional = false;
		
		parseRuleSub2_ForHead(ruleNode, true);
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parseRuleSub3_ForHead(ruleNode, true);
		
		if (!iterator.isToken_Assignment()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ASSIGNMENT' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.ASSIGNMENT, iterator.readToken());
		
		parseRuleSub4_ForHead(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_ToKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TOKEYWORD' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.TOKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FORHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub5_ForHead(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ForStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ForStatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FORSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ForHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub1_ForStatementBlock(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_WhileHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WHILEHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'WHILEHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_WhileHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WHILEHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_WhileHead(ruleNode, true);
		
		if (!iterator.isToken_WhileKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WHILEKEYWORD' expected in rule 'WHILEHEAD'");
		}
		ruleNode.addToken(TokenType.WHILEKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'WHILEHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_WhileStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WHILESTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_WhileStatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WHILESTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_WhileHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub1_WhileStatementBlock(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_UntilHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.UNTILHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'UNTILHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_UntilHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.UNTILHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_UntilHead(ruleNode, true);
		
		if (!iterator.isToken_UntilKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'UNTILKEYWORD' expected in rule 'UNTILHEAD'");
		}
		ruleNode.addToken(TokenType.UNTILKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'UNTILHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_UntilStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.UNTILSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_UntilStatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.UNTILSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_UntilHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub1_UntilStatementBlock(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_LoopLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LOOPLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'LOOPLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_LoopLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LOOPLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'LOOPLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!(parseRuleSub3_LoopLine(ruleNode, true) || parseRuleSub4_LoopLine(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'LOOPLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_LoopLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LOOPLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_UntilKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'UNTILKEYWORD' expected in rule 'LOOPLINE'");
		}
		ruleNode.addToken(TokenType.UNTILKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_LoopLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LOOPLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_WhileKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WHILEKEYWORD' expected in rule 'LOOPLINE'");
		}
		ruleNode.addToken(TokenType.WHILEKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_LoopLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LOOPLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_LoopLine(ruleNode, true);
		
		if (!iterator.isToken_LoopKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LOOPKEYWORD' expected in rule 'LOOPLINE'");
		}
		ruleNode.addToken(TokenType.LOOPKEYWORD, iterator.readToken());
		
		parseRuleSub2_LoopLine(ruleNode, true);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_DoStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.DOSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'DOSTATEMENTBLOCK'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_DoStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.DOSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_DoStatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.DOSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		parseRuleSub1_DoStatementBlock(ruleNode, true);
		
		if (!iterator.isToken_DoKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'DOKEYWORD' expected in rule 'DOSTATEMENTBLOCK'");
		}
		ruleNode.addToken(TokenType.DOKEYWORD, iterator.readToken());
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_DoStatementBlock(ruleNode, true);
		
		if (!parseRule_LoopLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_IfHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'IFHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_IfHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_IfHead(ruleNode, true);
		
		if (!iterator.isToken_IfKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IFKEYWORD' expected in rule 'IFHEAD'");
		}
		ruleNode.addToken(TokenType.IFKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'IFHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ElseIfHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ELSEIFHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ElseIfHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ElseIfHead(ruleNode, true);
		
		if (!iterator.isToken_ElseKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ELSEKEYWORD' expected in rule 'ELSEIFHEAD'");
		}
		ruleNode.addToken(TokenType.ELSEKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ELSEIFHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_IfKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IFKEYWORD' expected in rule 'ELSEIFHEAD'");
		}
		ruleNode.addToken(TokenType.IFKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ELSEIFHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ElseHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ELSEHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ElseHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_ElseHead(ruleNode, true);
		
		if (!iterator.isToken_ElseKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ELSEKEYWORD' expected in rule 'ELSEHEAD'");
		}
		ruleNode.addToken(TokenType.ELSEKEYWORD, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_IfStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_IfStatementBlock(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_IfStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FollowingElse(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_IfStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub4_IfStatementBlock(ruleNode, true);
		
		if (!(parseRuleSub5_IfStatementBlock(ruleNode, true) || parseRuleSub6_IfStatementBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_IfStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_IfStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_IfStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FollowingElse(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_IfStatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.IFSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_IfHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!(parseRuleSub1_IfStatementBlock(ruleNode, true) || parseRuleSub3_IfStatementBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_FollowingElse(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGELSE);
		int startIndex = iterator.getIndex();
		if (!parseRule_ElseIfBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_FollowingElse(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGELSE);
		int startIndex = iterator.getIndex();
		if (!parseRule_ElseBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_FollowingElse(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGELSE);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_FollowingElse(ruleNode, true) || parseRuleSub2_FollowingElse(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ElseIfBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_ElseIfBlock(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ElseIfBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FollowingElse(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ElseIfBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub4_ElseIfBlock(ruleNode, true);
		
		if (!(parseRuleSub5_ElseIfBlock(ruleNode, true) || parseRuleSub6_ElseIfBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_ElseIfBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_ElseIfBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_ElseIfBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FollowingElse(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ElseIfBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEIFBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ElseIfHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!(parseRuleSub1_ElseIfBlock(ruleNode, true) || parseRuleSub3_ElseIfBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ElseBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ElseBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub3_ElseBlock(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ElseBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ElseBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ELSEBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ElseHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!(parseRuleSub1_ElseBlock(ruleNode, true) || parseRuleSub2_ElseBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_TryHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'TRYHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_TryHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_TryHead(ruleNode, true);
		
		if (!iterator.isToken_TryKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TRYKEYWORD' expected in rule 'TRYHEAD'");
		}
		ruleNode.addToken(TokenType.TRYKEYWORD, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_CatchHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CATCHHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_CatchHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_CatchHead(ruleNode, true);
		
		if (!iterator.isToken_CatchKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CATCHKEYWORD' expected in rule 'CATCHHEAD'");
		}
		ruleNode.addToken(TokenType.CATCHKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'CATCHHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'CATCHHEAD'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_FinallyHead(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FINALLYHEAD);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'FINALLYHEAD'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_FinallyHead(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FINALLYHEAD);
		int startIndex = iterator.getIndex();
		parseRuleSub1_FinallyHead(ruleNode, true);
		
		if (!iterator.isToken_FinallyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FINALLYKEYWORD' expected in rule 'FINALLYHEAD'");
		}
		ruleNode.addToken(TokenType.FINALLYKEYWORD, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_TryStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_TryStatementBlock(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_TryStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FollowingCatchFinally(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_TryStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub4_TryStatementBlock(ruleNode, true);
		
		if (!(parseRuleSub5_TryStatementBlock(ruleNode, true) || parseRuleSub6_TryStatementBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_TryStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_TryStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_TryStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FollowingCatchFinally(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_TryStatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TRYSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_TryHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!(parseRuleSub1_TryStatementBlock(ruleNode, true) || parseRuleSub3_TryStatementBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_FollowingCatchFinally(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGCATCHFINALLY);
		int startIndex = iterator.getIndex();
		if (!parseRule_CatchBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_FollowingCatchFinally(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGCATCHFINALLY);
		int startIndex = iterator.getIndex();
		if (!parseRule_FinallyBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_FollowingCatchFinally(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FOLLOWINGCATCHFINALLY);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_FollowingCatchFinally(ruleNode, true) || parseRuleSub2_FollowingCatchFinally(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_CatchBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_CatchBlock(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_CatchBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FinallyBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_CatchBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub4_CatchBlock(ruleNode, true);
		
		if (!(parseRuleSub5_CatchBlock(ruleNode, true) || parseRuleSub6_CatchBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_CatchBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_CatchBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_CatchBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FinallyBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_CatchBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CATCHBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CatchHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!(parseRuleSub1_CatchBlock(ruleNode, true) || parseRuleSub3_CatchBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_FinallyBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FINALLYBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ColonNotation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Statement(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_FinallyBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FINALLYBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub3_FinallyBlock(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_FinallyBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FINALLYBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_FinallyBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.FINALLYBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_FinallyHead(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!(parseRuleSub1_FinallyBlock(ruleNode, true) || parseRuleSub2_FinallyBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SwitchStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SWITCHSTATEMENTBLOCK'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_SwitchStatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_SwitchBlockContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SwitchStatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHSTATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		parseRuleSub1_SwitchStatementBlock(ruleNode, true);
		
		if (!iterator.isToken_SwitchKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SWITCHKEYWORD' expected in rule 'SWITCHSTATEMENTBLOCK'");
		}
		ruleNode.addToken(TokenType.SWITCHKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SWITCHSTATEMENTBLOCK'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_SwitchStatementBlock(ruleNode, true);
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SwitchBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_SwitchBlockContent(ruleNode, true) || parseRuleSub3_SwitchBlockContent(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_SwitchBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_SwitchBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_SwitchCase(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_SwitchBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_SwitchDefault(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		while (true) {
			if (!(parseRuleSub5_SwitchBlockContent(ruleNode, true))) {
				break;
			}
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_SwitchBlockContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SwitchBlockContent(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHBLOCKCONTENT);
		int startIndex = iterator.getIndex();
		while (true) {
			if (!(parseRuleSub1_SwitchBlockContent(ruleNode, true))) {
				break;
			}
		}
		
		parseRuleSub4_SwitchBlockContent(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SwitchCase(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHCASE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SWITCHCASE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SwitchCase(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHCASE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_SwitchCase(ruleNode, true);
		
		if (!iterator.isToken_CaseKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CASEKEYWORD' expected in rule 'SWITCHCASE'");
		}
		ruleNode.addToken(TokenType.CASEKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SWITCHCASE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_ColonNotationOrCodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SwitchDefault(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHDEFAULT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SWITCHDEFAULT'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SwitchDefault(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SWITCHDEFAULT);
		int startIndex = iterator.getIndex();
		parseRuleSub1_SwitchDefault(ruleNode, true);
		
		if (!iterator.isToken_DefaultKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'DEFAULTKEYWORD' expected in rule 'SWITCHDEFAULT'");
		}
		ruleNode.addToken(TokenType.DEFAULTKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!parseRule_ColonNotationOrCodeBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_StatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ForeachStatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_StatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_ForStatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_StatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_WhileStatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_StatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_UntilStatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_StatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_DoStatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_StatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_TryStatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_StatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_IfStatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_StatementBlock(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!parseRule_SwitchStatementBlock(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_StatementBlock(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.STATEMENTBLOCK);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_StatementBlock(ruleNode, true) || parseRuleSub2_StatementBlock(ruleNode, true) || parseRuleSub3_StatementBlock(ruleNode, true) || parseRuleSub4_StatementBlock(ruleNode, true) || parseRuleSub5_StatementBlock(ruleNode, true) || parseRuleSub6_StatementBlock(ruleNode, true) || parseRuleSub7_StatementBlock(ruleNode, true) || parseRuleSub8_StatementBlock(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_CallOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CALLOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_CallOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CALLOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		while (true) {
			if (!(parseRuleSub3_CallOperator(ruleNode, true))) {
				break;
			}
		}
		
		parseRuleSub6_CallOperator(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_CallOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CALLOPERATOR);
		int startIndex = iterator.getIndex();
		parseRuleSub4_CallOperator(ruleNode, true);
		
		if (!iterator.isToken_Comma()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMMA' expected in rule 'CALLOPERATOR'");
		}
		ruleNode.addToken(TokenType.COMMA, iterator.readToken());
		
		parseRuleSub5_CallOperator(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_CallOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CALLOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_CallOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CALLOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_CallOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CALLOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_CallOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.CALLOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OpeningParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGPARENTHESIS' expected in rule 'CALLOPERATOR'");
		}
		ruleNode.addToken(TokenType.OPENINGPARENTHESIS, iterator.readToken());
		
		optional = false;
		
		parseRuleSub1_CallOperator(ruleNode, true);
		
		parseRuleSub2_CallOperator(ruleNode, true);
		
		if (!iterator.isToken_ClosingParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGPARENTHESIS' expected in rule 'CALLOPERATOR'");
		}
		ruleNode.addToken(TokenType.CLOSINGPARENTHESIS, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_AccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_SafeAccessDot()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SAFEACCESSDOT' expected in rule 'ACCESSOPERATOR'");
		}
		ruleNode.addToken(TokenType.SAFEACCESSDOT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_AccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_AccessDot()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ACCESSDOT' expected in rule 'ACCESSOPERATOR'");
		}
		ruleNode.addToken(TokenType.ACCESSDOT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_AccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_AccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		parseRuleSub5_AccessOperator(ruleNode, true);
		
		if (!parseRule_SequentialOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_AccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ACCESSOPERATOR'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_AccessOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_AccessOperator(ruleNode, true) || parseRuleSub2_AccessOperator(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parseRuleSub3_AccessOperator(ruleNode, true);
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'ACCESSOPERATOR'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parseRuleSub4_AccessOperator(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_ArrayAccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_SafeOpeningBracket()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SAFEOPENINGBRACKET' expected in rule 'ARRAYACCESSOPERATOR'");
		}
		ruleNode.addToken(TokenType.SAFEOPENINGBRACKET, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_ArrayAccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OpeningBracket()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGBRACKET' expected in rule 'ARRAYACCESSOPERATOR'");
		}
		ruleNode.addToken(TokenType.OPENINGBRACKET, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_ArrayAccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_ArrayAccessOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_ArrayAccessOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ARRAYACCESSOPERATOR);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_ArrayAccessOperator(ruleNode, true) || parseRuleSub2_ArrayAccessOperator(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		optional = false;
		
		parseRuleSub3_ArrayAccessOperator(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub4_ArrayAccessOperator(ruleNode, true);
		
		if (!iterator.isToken_ClosingBracket()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGBRACKET' expected in rule 'ARRAYACCESSOPERATOR'");
		}
		ruleNode.addToken(TokenType.CLOSINGBRACKET, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SequentialOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SEQUENTIALOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_CallOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_SequentialOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SEQUENTIALOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_AccessOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_SequentialOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SEQUENTIALOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_ArrayAccessOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_SequentialOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SEQUENTIALOPERATOR);
		int startIndex = iterator.getIndex();
		parseRuleSub5_SequentialOperator(ruleNode, true);
		
		if (!parseRule_SequentialOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_SequentialOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SEQUENTIALOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'SEQUENTIALOPERATOR'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SequentialOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SEQUENTIALOPERATOR);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_SequentialOperator(ruleNode, true) || parseRuleSub2_SequentialOperator(ruleNode, true) || parseRuleSub3_SequentialOperator(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parseRuleSub4_SequentialOperator(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_PointerEqual()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'POINTEREQUAL' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.POINTEREQUAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_PointerNotEqual()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'POINTERNOTEQUAL' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.POINTERNOTEQUAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NullDefault()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NULLDEFAULT' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.NULLDEFAULT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NotEqual()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NOTEQUAL' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.NOTEQUAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Equal()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'EQUAL' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.EQUAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_GreaterEqual()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'GREATEREQUAL' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.GREATEREQUAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_SmallerEqual()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SMALLEREQUAL' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.SMALLEREQUAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_LeftShift()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LEFTSHIFT' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.LEFTSHIFT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub9_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_RightShift()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'RIGHTSHIFT' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.RIGHTSHIFT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub10_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Greater()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'GREATER' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.GREATER, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub11_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Smaller()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'SMALLER' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.SMALLER, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub12_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Plus()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PLUS' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.PLUS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub13_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Minus()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'MINUS' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.MINUS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub14_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Multiply()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'MULTIPLY' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.MULTIPLY, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub15_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Divide()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'DIVIDE' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.DIVIDE, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub16_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Modulo()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'MODULO' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.MODULO, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub17_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_AndSign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ANDSIGN' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.ANDSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub18_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OrSign()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ORSIGN' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.ORSIGN, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub19_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Xor()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'XOR' expected in rule 'SIGNBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.XOR, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub20_SignBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SignBinaryOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SIGNBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_SignBinaryOperator(ruleNode, true) || parseRuleSub2_SignBinaryOperator(ruleNode, true) || parseRuleSub3_SignBinaryOperator(ruleNode, true) || parseRuleSub4_SignBinaryOperator(ruleNode, true) || parseRuleSub5_SignBinaryOperator(ruleNode, true) || parseRuleSub6_SignBinaryOperator(ruleNode, true) || parseRuleSub7_SignBinaryOperator(ruleNode, true) || parseRuleSub8_SignBinaryOperator(ruleNode, true) || parseRuleSub9_SignBinaryOperator(ruleNode, true) || parseRuleSub10_SignBinaryOperator(ruleNode, true) || parseRuleSub11_SignBinaryOperator(ruleNode, true) || parseRuleSub12_SignBinaryOperator(ruleNode, true) || parseRuleSub13_SignBinaryOperator(ruleNode, true) || parseRuleSub14_SignBinaryOperator(ruleNode, true) || parseRuleSub15_SignBinaryOperator(ruleNode, true) || parseRuleSub16_SignBinaryOperator(ruleNode, true) || parseRuleSub17_SignBinaryOperator(ruleNode, true) || parseRuleSub18_SignBinaryOperator(ruleNode, true) || parseRuleSub19_SignBinaryOperator(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		optional = false;
		
		parseRuleSub20_SignBinaryOperator(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_WsBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WSBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_AndKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ANDKEYWORD' expected in rule 'WSBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.ANDKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_WsBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WSBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OrKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ORKEYWORD' expected in rule 'WSBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.ORKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_WsBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WSBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_IsKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ISKEYWORD' expected in rule 'WSBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.ISKEYWORD, iterator.readToken());
		
		parseRuleSub4_WsBinaryOperator(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_WsBinaryOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WSBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'WSBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_NotKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NOTKEYWORD' expected in rule 'WSBINARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.NOTKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_WsBinaryOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.WSBINARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_WsBinaryOperator(ruleNode, true) || parseRuleSub2_WsBinaryOperator(ruleNode, true) || parseRuleSub3_WsBinaryOperator(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		optional = false;
		
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	public boolean parseRule_TernaryOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.TERNARYOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_QuestionMark()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'QUESTIONMARK' expected in rule 'TERNARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.QUESTIONMARK, iterator.readToken());
		
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'TERNARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_ExclamationMark()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'EXCLAMATIONMARK' expected in rule 'TERNARYOPERATOR'");
		}
		ruleNode.addToken(TokenType.EXCLAMATIONMARK, iterator.readToken());
		
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_PrefixOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PREFIXOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NotKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NOTKEYWORD' expected in rule 'PREFIXOPERATOR'");
		}
		ruleNode.addToken(TokenType.NOTKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PREFIXOPERATOR'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_PrefixOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PREFIXOPERATOR);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub3_PrefixOperator(ruleNode, true) || parseRuleSub4_PrefixOperator(ruleNode, true) || parseRuleSub5_PrefixOperator(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parseRuleSub6_PrefixOperator(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_PrefixOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PREFIXOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Complement()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'COMPLEMENT' expected in rule 'PREFIXOPERATOR'");
		}
		ruleNode.addToken(TokenType.COMPLEMENT, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_PrefixOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PREFIXOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Plus()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PLUS' expected in rule 'PREFIXOPERATOR'");
		}
		ruleNode.addToken(TokenType.PLUS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_PrefixOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PREFIXOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Minus()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'MINUS' expected in rule 'PREFIXOPERATOR'");
		}
		ruleNode.addToken(TokenType.MINUS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_PrefixOperator(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PREFIXOPERATOR);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'PREFIXOPERATOR'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_PrefixOperator(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.PREFIXOPERATOR);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_PrefixOperator(ruleNode, true) || parseRuleSub2_PrefixOperator(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_Group(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.GROUP);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_Group(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.GROUP);
		int startIndex = iterator.getIndex();
		if (!parseRule_LineContinuation(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Group(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.GROUP);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_OpeningParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'OPENINGPARENTHESIS' expected in rule 'GROUP'");
		}
		ruleNode.addToken(TokenType.OPENINGPARENTHESIS, iterator.readToken());
		
		optional = false;
		
		parseRuleSub1_Group(ruleNode, true);
		
		if (!parseRule_Expression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_Group(ruleNode, true);
		
		if (!iterator.isToken_ClosingParenthesis()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'CLOSINGPARENTHESIS' expected in rule 'GROUP'");
		}
		ruleNode.addToken(TokenType.CLOSINGPARENTHESIS, iterator.readToken());
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_FloatLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'FLOATLITERAL' expected in rule 'SINGLEEXPRESSION'");
		}
		ruleNode.addToken(TokenType.FLOATLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_BinaryIntLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'BINARYINTLITERAL' expected in rule 'SINGLEEXPRESSION'");
		}
		ruleNode.addToken(TokenType.BINARYINTLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_HexIntLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'HEXINTLITERAL' expected in rule 'SINGLEEXPRESSION'");
		}
		ruleNode.addToken(TokenType.HEXINTLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_DecimalIntLiteral()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'DECIMALINTLITERAL' expected in rule 'SINGLEEXPRESSION'");
		}
		ruleNode.addToken(TokenType.DECIMALINTLITERAL, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!parseRule_BoolLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_NullKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'NULLKEYWORD' expected in rule 'SINGLEEXPRESSION'");
		}
		ruleNode.addToken(TokenType.NULLKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!parseRule_CharLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!parseRule_StringLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub9_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!parseRule_IStringLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub10_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!parseRule_ArrayLiteral(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub11_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!parseRule_CodeBlockNamespaceOrEntityID(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub12_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'SINGLEEXPRESSION'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub13_SingleExpression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!parseRule_Group(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_SingleExpression(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.SINGLEEXPRESSION);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_SingleExpression(ruleNode, true) || parseRuleSub2_SingleExpression(ruleNode, true) || parseRuleSub3_SingleExpression(ruleNode, true) || parseRuleSub4_SingleExpression(ruleNode, true) || parseRuleSub5_SingleExpression(ruleNode, true) || parseRuleSub6_SingleExpression(ruleNode, true) || parseRuleSub7_SingleExpression(ruleNode, true) || parseRuleSub8_SingleExpression(ruleNode, true) || parseRuleSub9_SingleExpression(ruleNode, true) || parseRuleSub10_SingleExpression(ruleNode, true) || parseRuleSub11_SingleExpression(ruleNode, true) || parseRuleSub12_SingleExpression(ruleNode, true) || parseRuleSub13_SingleExpression(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		if (!parseRule_PrefixOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		parseRuleSub3_Expression(ruleNode, true);
		
		if (!parseRule_SequentialOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'EXPRESSION'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub5_Expression(ruleNode, true) || parseRuleSub7_Expression(ruleNode, true) || parseRuleSub8_Expression(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		parseRuleSub6_Expression(ruleNode, true);
		
		if (!parseRule_SignBinaryOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub6_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'EXPRESSION'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub7_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'EXPRESSION'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!parseRule_WsBinaryOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub8_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		parseRuleSub9_Expression(ruleNode, true);
		
		if (!parseRule_TernaryOperator(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub9_Expression(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'EXPRESSION'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_Expression(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EXPRESSION);
		int startIndex = iterator.getIndex();
		parseRuleSub1_Expression(ruleNode, true);
		
		if (!parseRule_SingleExpression(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_Expression(ruleNode, true);
		
		parseRuleSub4_Expression(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_InterfaceHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_InterfaceHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_InternalKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'INTERNALKEYWORD' expected in rule 'INTERFACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.INTERNALKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_InterfaceHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEHEADLINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_Extends(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_InterfaceHeadLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEHEADLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_InterfaceHeadLine(ruleNode, true);
		
		parseRuleSub2_InterfaceHeadLine(ruleNode, true);
		
		if (!iterator.isToken_InterfaceKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'INTERFACEKEYWORD' expected in rule 'INTERFACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.INTERFACEKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_TypeName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TYPENAME' expected in rule 'INTERFACEHEADLINE'");
		}
		ruleNode.addToken(TokenType.TYPENAME, iterator.readToken());
		
		parseRuleSub3_InterfaceHeadLine(ruleNode, true);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_InterfaceMethodLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEMETHODLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_InterfaceMethodLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEMETHODLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_InterfaceMethodLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEMETHODLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_InterfaceMethodLine(ruleNode, true);
		
		if (!iterator.isToken_MethodKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'METHODKEYWORD' expected in rule 'INTERFACEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.METHODKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'INTERFACEMETHODLINE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		parseRuleSub2_InterfaceMethodLine(ruleNode, true);
		
		if (!parseRule_Parameters(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_InterfacePropertyLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEPROPERTYLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_InterfacePropertyLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEPROPERTYLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_ReadOnlyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'READONLYKEYWORD' expected in rule 'INTERFACEPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.READONLYKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_InterfacePropertyLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACEPROPERTYLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_InterfacePropertyLine(ruleNode, true);
		
		parseRuleSub2_InterfacePropertyLine(ruleNode, true);
		
		if (!iterator.isToken_PropertyKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'PROPERTYKEYWORD' expected in rule 'INTERFACEPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.PROPERTYKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'INTERFACEPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'INTERFACEPROPERTYLINE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_InterfaceLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACELINES);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_InterfaceLines(ruleNode, true) || parseRuleSub3_InterfaceLines(ruleNode, true) || parseRuleSub4_InterfaceLines(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_InterfaceLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACELINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_InterfaceLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACELINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterfaceMethodLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_InterfaceLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACELINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterfacePropertyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_InterfaceLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.INTERFACELINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_InterfaceHeadLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_InterfaceLines(ruleNode, true))) {
				break;
			}
		}
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_EnumHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ENUMHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_EnumHeadLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMHEADLINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_InternalKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'INTERNALKEYWORD' expected in rule 'ENUMHEADLINE'");
		}
		ruleNode.addToken(TokenType.INTERNALKEYWORD, iterator.readToken());
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ENUMHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_EnumHeadLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMHEADLINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_EnumHeadLine(ruleNode, true);
		
		parseRuleSub2_EnumHeadLine(ruleNode, true);
		
		if (!iterator.isToken_EnumKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ENUMKEYWORD' expected in rule 'ENUMHEADLINE'");
		}
		ruleNode.addToken(TokenType.ENUMKEYWORD, iterator.readToken());
		
		optional = false;
		
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ENUMHEADLINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		if (!iterator.isToken_TypeName()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'TYPENAME' expected in rule 'ENUMHEADLINE'");
		}
		ruleNode.addToken(TokenType.TYPENAME, iterator.readToken());
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_EnumValueLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMVALUELINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_Ws()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'WS' expected in rule 'ENUMVALUELINE'");
		}
		ruleNode.addToken(TokenType.WS, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_EnumValueLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMVALUELINE);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_EndKeyword()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'ENDKEYWORD' expected in rule 'ENUMVALUELINE'");
		}
		ruleNode.addToken(TokenType.ENDKEYWORD, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_EnumValueLine(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMVALUELINE);
		int startIndex = iterator.getIndex();
		if (!parseRule_ConstantAssignment(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_EnumValueLine(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMVALUELINE);
		int startIndex = iterator.getIndex();
		parseRuleSub1_EnumValueLine(ruleNode, true);
		
		if (parseRuleSub2_EnumValueLine(ruleNode, true)) {
			return false;
		}
		
		if (!iterator.isToken_Identifier()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'IDENTIFIER' expected in rule 'ENUMVALUELINE'");
		}
		ruleNode.addToken(TokenType.IDENTIFIER, iterator.readToken());
		
		optional = false;
		
		parseRuleSub3_EnumValueLine(ruleNode, true);
		
		if (!parseRule_LineEnd(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_EnumLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMLINES);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub2_EnumLines(ruleNode, true) || parseRuleSub3_EnumLines(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_EnumLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_EnumLines(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_EnumValueLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_EnumLines(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.ENUMLINES);
		int startIndex = iterator.getIndex();
		if (!parseRule_EnumHeadLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		optional = false;
		
		while (true) {
			if (!(parseRuleSub1_EnumLines(ruleNode, true))) {
				break;
			}
		}
		
		if (!parseRule_EndLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_LsDocumentContentItem(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENTCONTENTITEM);
		int startIndex = iterator.getIndex();
		if (!parseRule_Namespace(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_LsDocumentContentItem(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENTCONTENTITEM);
		int startIndex = iterator.getIndex();
		if (!parseRule_Using(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_LsDocumentContentItem(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENTCONTENTITEM);
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_LsDocumentContentItem(ruleNode, true) || parseRuleSub2_LsDocumentContentItem(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_LsDocumentContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENTCONTENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'LSDOCUMENTCONTENT'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		while (true) {
			if (!(parseRuleSub2_LsDocumentContent(ruleNode, true))) {
				break;
			}
		}
		
		if (!parseRule_LsDocumentContentItem(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_LsDocumentContent(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENTCONTENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLine(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_LsDocumentContent(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENTCONTENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_LsDocumentContentItem(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		while (true) {
			if (!(parseRuleSub1_LsDocumentContent(ruleNode, true))) {
				break;
			}
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_EmptyLsDocument(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EMPTYLSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'EMPTYLSDOCUMENT'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		if (!parseRule_EmptyLineContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_EmptyLsDocument(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.EMPTYLSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLineContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		while (true) {
			if (!(parseRuleSub1_EmptyLsDocument(ruleNode, true))) {
				break;
			}
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_NonEmptyLsDocument(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NONEMPTYLSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLineContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'NONEMPTYLSDOCUMENT'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_NonEmptyLsDocument(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NONEMPTYLSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'NONEMPTYLSDOCUMENT'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		parseRuleSub3_NonEmptyLsDocument(ruleNode, true);
		
		parseRuleSub5_NonEmptyLsDocument(ruleNode, true);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub3_NonEmptyLsDocument(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NONEMPTYLSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLineContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		while (true) {
			if (!(parseRuleSub4_NonEmptyLsDocument(ruleNode, true))) {
				break;
			}
		}
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub4_NonEmptyLsDocument(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NONEMPTYLSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'NONEMPTYLSDOCUMENT'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		if (!parseRule_EmptyLineContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub5_NonEmptyLsDocument(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NONEMPTYLSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!iterator.isToken_LineBreak()) {
			return returnFalseOrThrowException(optional, startIndex, "Token of type 'LINEBREAK' expected in rule 'NONEMPTYLSDOCUMENT'");
		}
		ruleNode.addToken(TokenType.LINEBREAK, iterator.readToken());
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_NonEmptyLsDocument(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.NONEMPTYLSDOCUMENT);
		int startIndex = iterator.getIndex();
		while (true) {
			if (!(parseRuleSub1_NonEmptyLsDocument(ruleNode, true))) {
				break;
			}
		}
		
		if (!parseRule_LsDocumentContent(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parseRuleSub2_NonEmptyLsDocument(ruleNode, true);
		
		lastNode = ruleNode;
		return true;
	}
	
	protected boolean parseRuleSub1_LsDocument(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_NonEmptyLsDocument(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	protected boolean parseRuleSub2_LsDocument(RuleNode parentRuleNode, boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENT);
		int startIndex = iterator.getIndex();
		if (!parseRule_EmptyLsDocument(optional || false)) {
			iterator.resetIndex(startIndex);
			return false;
		}
		ruleNode.add(lastNode);
		
		parentRuleNode.addTemp(ruleNode);
		return true;
	}
	
	public boolean parseRule_LsDocument(boolean optional) {
		RuleNode ruleNode = new RuleNode(RuleType.LSDOCUMENT);
		RootNode = ruleNode;
		int startIndex = iterator.getIndex();
		if (!(parseRuleSub1_LsDocument(ruleNode, true) || parseRuleSub2_LsDocument(ruleNode, true))) {
			return returnFalseOrThrowException(optional, startIndex, "Non optional rule alternative could not be matched");
		}
		
		lastNode = ruleNode;
		return true;
	}
	
	public RuleNode parse() {
		boolean result = parseRule_LsDocument(false);
		if (!result) {
			throw getException("Could not match root rule");
		}
		if (!iterator.isEndOfText()) {
			throw getException("Text not completely parsed");
		}
		return (RuleNode)lastNode;
	}
	
	public static RuleNode parse(String content) {
		return new Parser(content).parse();
	}
}