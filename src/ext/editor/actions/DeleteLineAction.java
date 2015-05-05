package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * 一行削除機能を実装する。
 * 
 * @author tadashi
 */
public class DeleteLineAction extends AbstractDeleteAction
{
	/**
	 * 実行ルーチン
	 */
	public void execute(IAction action)
	{
		super.execute(action);

		int offset = getCaretPos();
		IDocument doc = getDocument();
		try
		{
			int line = doc.getLineOfOffset(offset);
			int top = doc.getLineOffset(line);
			int end = top + doc.getLineLength(line);

			addDeletedString(true, doc.get(top, end - top));
			doc.replace(top, end - top, "");

			// 新しいラインが元のカーソル位置より短い場合は行末にする
			int len = doc.getLineLength(line) - doc.getLineDelimiter(line).length();
			if (offset > top + len)
				offset = top + len;

			setCaretPos(offset);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

}
