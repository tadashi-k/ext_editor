package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;

/**
 * ���̃��[�h�̐擪�ɃL�����b�g���ڂ��B
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
	 * ���s���[�`��
	 */
	public void execute(IAction action)
	{
		int offset = getCaretPos();
		IDocument doc = getDocument();

		offset = separator.getNextWord(doc.get(), offset);
		setCaretPos(offset);
	}
}
