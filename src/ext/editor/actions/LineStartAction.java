package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * �s�̍ŏ��ɃL�����b�g���ڂ�
 * <p>
 * @author tadashi
 */
public class LineStartAction extends AbstractAction {

	public LineStartAction() {
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
			setCaretPos(offset);
		} catch (BadLocationException e) {
			// Exception�̏ꍇ�̓L�����b�g�͈ړ����Ȃ�
		}
	}
}
