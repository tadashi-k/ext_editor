/**
 * 	�����񌟍����[�`��
 *
 *	$Id: StringMatcher.java,v 1.4 2001/05/13 01:28:29(JST) tadashi Exp $
 */

package ext.editor.actions;

public	class StringMatcher extends WordSeparator
{
	/**  �啶���^����������ʂ��邩�ǂ���  */
	public boolean ignoreCase = true ;

	/**  ���[�h�������[�h  */
	public boolean isWordSearch ;

	private String	searchWord ;	//  �������[�h
	
	public StringMatcher()
	{
	}
	public StringMatcher( String word )
	{
		setSearchWord( word );
	}
	public StringMatcher( char c )
	{
		setSearchWord( c );
	}
	
	/**  �����������ݒ肷��  */
	public void setSearchWord( String word )
	{
		searchWord = word ;
	}
	public void setSearchWord( char c )
	{
		searchWord = String.valueOf( c );
	}
	public String getSearchWord()
	{
		return searchWord;
	}
	
	/**  �O������  */
	public int searchForward( String str, int from )
	{
		if ( searchWord == null )
			return -1 ;

		int idx = from - 1 ;
		do
		{
			//  �ŏ��̕�����T��	
			idx = searchCharForward( str, searchWord.charAt( 0 ), idx+1 );

			//  �S�̂̔�r
			if ( idx >= 0 && match( str, idx ) )
				return idx ;
		}
		while( idx >= 0 );
		return -1 ;
	}
	
	/**  �������  */
	public int searchBackward( String str, int from )
	{
		if ( searchWord == null )
			return -1 ;

		int idx = from - searchWord.length() + 1 ;
		do
		{
			//  �ŏ��̕�����T��
			idx = searchCharBackward( str, searchWord.charAt( 0 ), idx-1 );

			//  �S�̂̔�r
			if ( idx >= 0 && match( str, idx ) )
				return idx ;
		}
		while( idx >= 0 );
		return -1 ;
	}

	/**	��v������������Ԃ�  */
	public int length()
	{
		return searchWord.length();
	}

	/**  �Ή���������T��  */
	public int searchCloseBracket( String text, int pos )
	{
		String open = "({[" ;
		String close = ")}]" ;

		int mode = open.indexOf( searchWord.charAt( 0 ) );
		if ( mode >= 0 )
		{
			int level = 1 ;
			int len = text.length();
			pos++ ;
			while( pos < len )
			{
				if ( open.charAt( mode ) == text.charAt( pos ) )
					level++ ;
				if ( close.charAt( mode ) == text.charAt( pos ) )
					level-- ;
				if ( level == 0 )
					return pos ;
				pos++ ;
			}
			return -1 ;
		}
		return -1 ;
	}
	
	/**  �Ή���������T��  */
	public int searchOpenBracket( String text, int pos )
	{
		String open = "({[" ;
		String close = ")}]" ;
		
		int mode = close.indexOf( searchWord.charAt( 0 ) );
		if ( mode >= 0 )
		{
			int level = 1 ;
			pos -- ;
			while( pos >= 0 )
			{
				if ( open.charAt( mode ) == text.charAt( pos ) )
					level-- ;
				if ( close.charAt( mode ) == text.charAt( pos ) )
					level++ ;
				if ( level == 0 )
					return pos ;
				pos-- ;
			}
			return -1 ;
		}
		return -1 ;
	}


	//
	//  �w���p�[���\�b�h
	//
	
	/**  �啶���^�������ϊ� */
	private char changeCase( char ch )
	{
		if ( Character.isUpperCase( ch ) )
			return Character.toLowerCase( ch );
		else
			return Character.toUpperCase( ch );
	}
	
	/**  �����̑O������  */
	private int searchCharForward( String text, char ch, int from )
	{
		int index ;
		index = text.indexOf( ch, from );
		if ( ignoreCase )
		{
			int i = text.indexOf( changeCase( ch ), from );
			if ( i >= 0 && ( index < 0 || i < index ) )
				return i ;
		}
		return index ;
	}
	
	/**  �����̌������  */
	private int searchCharBackward( String text, char ch, int from )
	{
		int index ;
		index = text.lastIndexOf( ch, from );
		if ( ignoreCase )
		{
			int i = text.lastIndexOf( changeCase( ch ), from );
			if ( i > index )
				return i ;
		}
		return index ;
	}
	
	/**  1�����ڂ���v����������̃`�F�b�N  */
	private boolean match( String text, int idx )
	{
		if ( isWordSearch && idx > 0 )
		{
			if ( isWordChar( text.charAt( idx-1 ) ) )
				return false ;
		}
		
		int len = searchWord.length();
		if ( idx+len <= text.length() )
		{
			String	comp = text.substring( idx, idx+len );
			if ( ignoreCase )
				return comp.equalsIgnoreCase( searchWord );
			else
				return comp.equals( searchWord );
		}
		else
			return false ;
	}

}










