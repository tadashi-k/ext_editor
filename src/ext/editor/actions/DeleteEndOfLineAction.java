package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * 行末まで削除する。
 * <p>
 * 削除した文字列はDeleteLineActionと同じstaticで保持する。
 * YankLineで復活させることができる。
 * 
 * @author tadashi
 */
public class DeleteEndOfLineAction extends AbstractDeleteAction
{
	/**
	 * 実行ルーチン（再定義している）
	 */
	public void execute(IAction action)
	{
		int offset = getCaretPos();
		IDocument doc = getDocument();
		try
		{
			int line = doc.getLineOfOffset(offset);
			int top = doc.getLineOffset(line);
			int end = top + doc.getLineLength(line) - doc.getLineDelimiter(line).length(); 

			setDeletedString(false, doc.get(offset, end - offset));
			doc.replace(offset, end - offset, "");
			setCaretPos(offset);

			/*
			StyledText text = getTextWidget();
			setDeletedString(false, text.getText(offset, end - 1));
			text.replaceTextRange(offset, end-offset, "");
			*/
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}

	}

}
