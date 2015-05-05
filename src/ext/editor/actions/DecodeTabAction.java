
package ext.editor.actions;

/**
 * タブをスペースに変換する
 * 
 * @author tadashi
 */
public class DecodeTabAction extends AbstractConvertAction
{
	protected void convert(String src, StringBuffer dst, int tabWidth)
	{
		int col = 0;
		for (int i = 0; i < src.length(); i++)
		{
			char ch = src.charAt(i);
			switch (ch)
			{
				case '\n' :
					col = 0;
					dst.append(ch);
					break;
				case '\t' :
					//  タブをスペースに展開する
					int spc = tabWidth - (col % tabWidth);
					for (int j = 0; j < spc; j++)
						dst.append(' ');
					col += spc;
					break;
				default :
					dst.append(ch);
					if (ch >= 0x100)
						col += 2; // 全角文字
					else
						col++;
					break;
			}
		}
	}

}
