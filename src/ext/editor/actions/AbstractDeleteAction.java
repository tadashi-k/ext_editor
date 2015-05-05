package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * �폜�֘A�R�}���h�̃x�[�X�N���X.
 * <p>
 * �폜�����������static�ŕێ�����B���������ăV�X�e�����j�[�N�ł���B
 * YankLine�ŕ��������邱�Ƃ��ł���B
 * 
 * @author tadashi
 */
public abstract class AbstractDeleteAction extends AbstractAction
{
	private static String deletedString = null;
	private static boolean isAppend = false;
	private static boolean isLined = true;

	/**
	 * �폜����������𓾂�
	 */
	public static String getDeletedString()
	{
		return deletedString;
	}

	/**
	 * �폜�����̂��s�S�̂��ǂ����������t���O
	 */
	public static boolean isLined()
	{
		return isLined;
	}
	
	/**
	 * ��A�̍폜���I������Ƃ��ɌĂ΂��
	 */
	public static void done()
	{
		isAppend = false ;
	}
	
	/**
	 * �폜�������ǉ�����<p>
	 * ���Ƃ̍폜������ɒǉ����邩�ǂ����͕����Ɉˑ�����
	 */
	protected static void addDeletedString(boolean isLined, String string)
	{
		if ( isAppend )
			deletedString += string;
		else
			deletedString = string;
		isAppend = true ;
		AbstractDeleteAction.isLined = isLined ;
	}
	
	/**
	 * �폜�������ݒ肷��
	 */
	protected static void setDeletedString(boolean isLined, String string)
	{
		deletedString = string ;
		isAppend = false ;
		AbstractDeleteAction.isLined = isLined ;
	}
	
	/**
	 *  �폜�����������append���邩�ǂ������߂邽�߂̃��X�i
	 */
	private static Listener listener = new Listener();

	/**
	 * �폜�����s���o����
	 */
	private int deletedLine ;

	public AbstractDeleteAction()
	{
		deletedLine = -1 ;
	}

	/**
	 * ���s���[�`��
	 */
	public void execute(IAction action)
	{
		try
		{
			IDocument doc = getDocument();
			int line = doc.getLineOfOffset(getCaretPos());
			if (line != deletedLine)
			{
				done();
				deletedLine = line;
			}
		}
		catch (BadLocationException e)
		{}
		getTextWidget().addKeyListener(listener);
		// getSourceViewer().addTextListener(listener);
	}

	/**
	 * �폜�����������append���邩�ǂ��������߂郊�X�i.<p>
	 * ���ꂪ�Ă΂ꂽ�ꍇ�͑��̏������������ƌ��Ȃ���append���Ȃ�
	 */
	private static class Listener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			done();
		}
		public void keyReleased(KeyEvent e)
		{}
	}
}









