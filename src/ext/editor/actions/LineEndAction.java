package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * �s�̍Ō�ɃL�����b�g���ڂ�
 * <p>
 * @author tadashi
 */
public class LineEndAction extends AbstractAction {

	public LineEndAction() {
	}

	/**
	 * ���s���[�`��
	 */
	public void execute(IAction action) {
		int offset = getCaretPos();
		IDocument doc = getDocument();
		try {
			int line = doc.getLineOfOffset(offset);
			offset = doc.getLineOffset(line);
			offset += doc.getLineLength(line);
			offset -= doc.getLineDelimiter(line).length();
			setCaretPos(offset);
		} catch (BadLocationException e) {
			// Exception�̏ꍇ�̓L�����b�g�͈ړ����Ȃ�
		}
	}
}
