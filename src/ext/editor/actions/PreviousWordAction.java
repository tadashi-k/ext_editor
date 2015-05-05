package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;

/**
 * �O�̃��[�h�̐擪�ɃL�����b�g���ڂ��B
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
	 * ���s���[�`��
	 */
	public void execute(IAction action)
	{
		int offset = getCaretPos();
		IDocument doc = getDocument();

		offset = separator.getPrevWord(doc.get(), offset);
		setCaretPos(offset);
	}
}
