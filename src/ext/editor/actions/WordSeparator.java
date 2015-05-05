/**
 * 	���[�h�������[�`��
 *
 *	$Id: WordSeparator.java,v 1.1 2001/04/30 20:40:39(JST) tadashi Exp $
 */

package ext.editor.actions;

public class WordSeparator
{
	//  ������
	public static final int	SPACE	 	= 0 ;	//  �󔒕���
	public static final int CR			= 1 ;
	public static final int	LF		 	= 2 ;
	public static final int	IDENT	 	= 3 ;	//  ���ʎq�̕���
	public static final int	SPECIAL	 	= 4 ;	//  ���ꕶ��
	public static final int	HIRAGANA 	= 5 ;	//  �Ђ炪��
	public static final int	KATAKANA	= 6 ;	//  �J�^�J�i
	public static final int	KANJI	 	= 7 ;	//  ����
	public static final int	ZEN_ELSE	= 8 ;	//  ���̑��̑S�p����

	/**	 ���[�h�����Ɋ܂߂���ꕶ�� */
	public String wordChars = "_" ;

	/**  �啶�������[�h��؂�ɂ��邩�ǂ���  */
	public boolean isSeparateUpperCase = true ;

	/**  ����������߂�  */
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
			//  2�o�C�g����
			if ( 0x3041 <= c && c <= 0x309f )	//  Unicode�̂Ђ炪��
				return HIRAGANA ;
			if ( c == '�E' )					//  �J�^�J�i�̊Ԃɂ���
				return ZEN_ELSE ;
			if ( 0x30a1 <= c && c <= 0x30ff )	//  Unicode�̃J�^�J�i
				return KATAKANA ;
			if ( 0x4e00 <= c && c <= 0x9faf )	//  CJK��������
				return KANJI ;
			return ZEN_ELSE ;
		}
		if ( isWordChar( c ) )
			return IDENT ;
		return SPECIAL ;	//  ���̑�
	}
	
	/**  ���[�h�����̃`�F�b�N  */
	public boolean isWordChar( char ch )
	{
		if ( Character.isLetterOrDigit( ch ) )
			return true ;
		if ( wordChars.indexOf( ch ) >= 0 )
			return true ;
		return false ;
	}
	
	/**	 1���[�h�E������  */
	public int getNextWord( String text, int pos )
	{
		int len = text.length();

		//  �Ō�̏ꍇ�̗�O����
		if ( pos >= len )
			return pos ;

		char c = text.charAt( pos );
		int ctype = typeOfChar( c );

		//  �s�̍Ō�̏ꍇ�͂ЂƂi��
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

		//  ���[�h�̏I���ʒu�𓾂�
		pos = getWordEnd( text, pos );
		
		//  �X�y�[�X�������X�L�b�v
		while( pos < len && typeOfChar( text.charAt( pos ) ) == SPACE )
		{
			pos++ ;
		}
		return pos ;
	}
	
	/**	 1���[�h��������  */
	public int getPrevWord( String text, int pos )
	{
		//  �擪�̗�O����
		if ( pos == 0 )
			return 0 ;

		int ctype = typeOfChar( text.charAt( --pos ) );

		//  pos���s�̐擪�̏ꍇ
		if ( ctype == LF )
		{
			if ( pos > 1 && typeOfChar( text.charAt(pos-1) ) == CR )
				return pos -1 ;
			else
				return pos ;
		}

		//  �X�y�[�X�������X�L�b�v
		while( ctype == SPACE )
		{
			if ( pos == 0 )
				return 0 ;
			ctype = typeOfChar( text.charAt( --pos ) );
		}

		//  �s�̐擪�ɂ��ǂ蒅�����ꍇ�͏I��
		if ( ctype == LF )
			return pos + 1 ;

		//  ���[�h�J�n�ʒu�𓾂�
		return getWordStart( text, pos );
	}
	
	/**  ���[�h�̐擪�ʒu�𓾂�  */
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
	
	/**  ���[�h�̖����ʒu�𓾂�  */
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














