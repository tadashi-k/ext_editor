package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;

/**
 * 次のワードの先頭にキャレットを移す。
 * <p>
 * @author tadashi
 */
public class NextWordAction extends AbstractAction
{
	private WordSeparator separator ;

	public NextWordAction()
	{
		separator = new WordSeparator();
	}

	/**
	 * 実行ルーチン
	 */
	public void execute(IAction action)
	{
		int offset = getCaretPos();
		IDocument doc = getDocument();

		offset = separator.getNextWord(doc.get(), offset);
		setCaretPos(offset);
	}
}
