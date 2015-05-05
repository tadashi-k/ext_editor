
package ext.editor.actions;

/**
 * スペースをタブに変換する
 * 
 * @author tadashi
 */
public class EncodeTabAction extends AbstractConvertAction
{
	protected void convert(String src, StringBuffer dst, int tabWidth)
	{
		int col = 0;
		int spccnt = 0;
		int len = src.length();
		for (int n = 0; n < len; n++)
		{
			int ch = src.charAt(n);
			if (ch == ' ')
				spccnt++;
			if ((spccnt + col) % tabWidth == 0)
			{
				if (spccnt >= 2)
					dst.append('\t');
				else if (spccnt == 1)
					dst.append(' ');
				col += spccnt;
				spccnt = 0;
			}

			if (ch != ' ')
			{
				if (spccnt > 0)
				{
					for (int i = 0; i < spccnt; i++)
						dst.append(' ');
					col += spccnt;
					spccnt = 0;
				}
				dst.append((char)ch);
				if (ch == '\n')
					col = 0;
				else if (ch == '\t')
					col = (col + tabWidth) / tabWidth * tabWidth;
				else if (ch >= 0x100)
					col += 2;
				else
					col++;
			}
		}
	}
}
