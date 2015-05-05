
package ext.editor.actions;

/**
 * �^�u���X�y�[�X�ɕϊ�����
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
					//  �^�u���X�y�[�X�ɓW�J����
					int spc = tabWidth - (col % tabWidth);
					for (int j = 0; j < spc; j++)
						dst.append(' ');
					col += spc;
					break;
				default :
					dst.append(ch);
					if (ch >= 0x100)
						col += 2; // �S�p����
					else
						col++;
					break;
			}
		}
	}

}
