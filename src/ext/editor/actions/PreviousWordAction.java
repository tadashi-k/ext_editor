package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;

/**
 * 前のワードの先頭にキャレットを移す。
 * <p>
 * @author tadashi
 */
public class PreviousWordAction extends AbstractAction
{
	private WordSeparator separator ;

	public PreviousWordAction()
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

		offset = separator.getPrevWord(doc.get(), offset);
		setCaretPos(offset);
	}
}
