package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * 行の最初にキャレットを移す
 * <p>
 * @author tadashi
 */
public class LineStartAction extends AbstractAction {

	public LineStartAction() {
	}

	/**
	 * 実行ルーチン
	 */
	public void execute(IAction action) {
		int offset = getCaretPos();
		IDocument doc = getDocument();

		try {
			int line = doc.getLineOfOffset(offset);
			offset = doc.getLineOffset(line);
			setCaretPos(offset);
		} catch (BadLocationException e) {
			// Exceptionの場合はキャレットは移動しない
		}
	}
}
