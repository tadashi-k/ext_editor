/**
 * 	ワード分割ルーチン
 *
 *	$Id: WordSeparator.java,v 1.1 2001/04/30 20:40:39(JST) tadashi Exp $
 */

package ext.editor.actions;

public class WordSeparator
{
	//  文字種
	public static final int	SPACE	 	= 0 ;	//  空白文字
	public static final int CR			= 1 ;
	public static final int	LF		 	= 2 ;
	public static final int	IDENT	 	= 3 ;	//  識別子の文字
	public static final int	SPECIAL	 	= 4 ;	//  特殊文字
	public static final int	HIRAGANA 	= 5 ;	//  ひらがな
	public static final int	KATAKANA	= 6 ;	//  カタカナ
	public static final int	KANJI	 	= 7 ;	//  漢字
	public static final int	ZEN_ELSE	= 8 ;	//  その他の全角文字

	/**	 ワード文字に含める特殊文字 */
	public String wordChars = "_" ;

	/**  大文字をワード区切りにするかどうか  */
	public boolean isSeparateUpperCase = true ;

	/**  文字種を求める  */
	public int typeOfChar( char c )
	{
		if ( c == '\n' )
			return LF ;
		if ( c == '\r' )
			return CR ;
		if ( Character.isWhitespace( c ) )
			return SPACE ;
		if ( c >= 0x100 )
		{
			//  2バイト文字
			if ( 0x3041 <= c && c <= 0x309f )	//  Unicodeのひらがな
				return HIRAGANA ;
			if ( c == '・' )					//  カタカナの間にある
				return ZEN_ELSE ;
			if ( 0x30a1 <= c && c <= 0x30ff )	//  Unicodeのカタカナ
				return KATAKANA ;
			if ( 0x4e00 <= c && c <= 0x9faf )	//  CJK統合漢字
				return KANJI ;
			return ZEN_ELSE ;
		}
		if ( isWordChar( c ) )
			return IDENT ;
		return SPECIAL ;	//  その他
	}
	
	/**  ワード文字のチェック  */
	public boolean isWordChar( char ch )
	{
		if ( Character.isLetterOrDigit( ch ) )
			return true ;
		if ( wordChars.indexOf( ch ) >= 0 )
			return true ;
		return false ;
	}
	
	/**	 1ワード右を検索  */
	public int getNextWord( String text, int pos )
	{
		int len = text.length();

		//  最後の場合の例外処理
		if ( pos >= len )
			return pos ;

		char c = text.charAt( pos );
		int ctype = typeOfChar( c );

		//  行の最後の場合はひとつ進む
		if ( ctype == LF )
		{
			return pos + 1 ;
		}
		if ( ctype == CR )
		{
			if ( typeOfChar( text.charAt(pos+1) ) == LF )
				return pos + 2 ;
			else
				return pos + 1 ;
		}

		//  ワードの終了位置を得る
		pos = getWordEnd( text, pos );
		
		//  スペース文字をスキップ
		while( pos < len && typeOfChar( text.charAt( pos ) ) == SPACE )
		{
			pos++ ;
		}
		return pos ;
	}
	
	/**	 1ワード左を検索  */
	public int getPrevWord( String text, int pos )
	{
		//  先頭の例外処理
		if ( pos == 0 )
			return 0 ;

		int ctype = typeOfChar( text.charAt( --pos ) );

		//  posが行の先頭の場合
		if ( ctype == LF )
		{
			if ( pos > 1 && typeOfChar( text.charAt(pos-1) ) == CR )
				return pos -1 ;
			else
				return pos ;
		}

		//  スペース文字をスキップ
		while( ctype == SPACE )
		{
			if ( pos == 0 )
				return 0 ;
			ctype = typeOfChar( text.charAt( --pos ) );
		}

		//  行の先頭にたどり着いた場合は終了
		if ( ctype == LF )
			return pos + 1 ;

		//  ワード開始位置を得る
		return getWordStart( text, pos );
	}
	
	/**  ワードの先頭位置を得る  */
	public int getWordStart( String text, int pos )
	{
		char c = text.charAt( pos );
		int ctype = typeOfChar( c );
		
		boolean nlcase = ! Character.isUpperCase( c );
		boolean islcase ;
		int nctype ;
		do
		{
			boolean plcase = nlcase ;
			if ( pos == 0 )
				return 0 ;
			c = text.charAt( --pos );
			nctype = typeOfChar( c );
			nlcase = ! Character.isUpperCase( c );
			islcase = isSeparateUpperCase && ! plcase && nlcase ;
		}
		while( nctype == ctype && ! islcase );
		return pos + 1 ;
	}
	
	/**  ワードの末尾位置を得る  */
	public int getWordEnd( String text, int pos )
	{
		int len = text.length();
		char c = text.charAt( pos );
		int ctype = typeOfChar( c );

		boolean nucase = Character.isUpperCase( c );
		boolean isucase ;
		int nctype ;
		do
		{
			boolean pucase = nucase ;
			pos++ ;
			if ( pos >= len )
				return len ;
			c = text.charAt( pos );
			nctype = typeOfChar( c );
			nucase = Character.isUpperCase( c );
			isucase = isSeparateUpperCase && ! pucase && nucase ;
		}
		while( nctype == ctype && ! isucase );
		return pos ;
	}
}














