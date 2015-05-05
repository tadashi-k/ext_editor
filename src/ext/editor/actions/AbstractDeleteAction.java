package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * 削除関連コマンドのベースクラス.
 * <p>
 * 削除した文字列はstaticで保持する。したがってシステムユニークである。
 * YankLineで復活させることができる。
 * 
 * @author tadashi
 */
public abstract class AbstractDeleteAction extends AbstractAction
{
	private static String deletedString = null;
	private static boolean isAppend = false;
	private static boolean isLined = true;

	/**
	 * 削除した文字列を得る
	 */
	public static String getDeletedString()
	{
		return deletedString;
	}

	/**
	 * 削除したのが行全体かどうかを示すフラグ
	 */
	public static boolean isLined()
	{
		return isLined;
	}
	
	/**
	 * 一連の削除が終わったときに呼ばれる
	 */
	public static void done()
	{
		isAppend = false ;
	}
	
	/**
	 * 削除文字列を追加する<p>
	 * もとの削除文字列に追加するかどうかは文脈に依存する
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
	 * 削除文字列を設定する
	 */
	protected static void setDeletedString(boolean isLined, String string)
	{
		deletedString = string ;
		isAppend = false ;
		AbstractDeleteAction.isLined = isLined ;
	}
	
	/**
	 *  削除した文字列をappendするかどうか決めるためのリスナ
	 */
	private static Listener listener = new Listener();

	/**
	 * 削除した行を覚える
	 */
	private int deletedLine ;

	public AbstractDeleteAction()
	{
		deletedLine = -1 ;
	}

	/**
	 * 実行ルーチン
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
	 * 削除した文字列をappendするかどうかを決めるリスナ.<p>
	 * これが呼ばれた場合は他の処理が入ったと見なしてappendしない
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









