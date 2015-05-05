package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * 拡張Actionのベースクラス<p>
 * ユーティリティメソッドを定義する
 * @author tadashi
 */
public abstract class AbstractAction implements IWorkbenchWindowActionDelegate
{
	private IWorkbenchWindow window;
	private IEditorPart editor ;

	public AbstractAction()
	{
	}
	
	/**
	 * 実行する
	 */
	public void run(IAction action)
	{
		IEditorPart editorPart = window.getActivePage().getActiveEditor();

		if (editorPart instanceof ITextEditor)
		{
			editor = (ITextEditor)editorPart;
		}
		else if (editorPart instanceof MultiPageEditorPart)
		{
			editor = editorPart;
		}
		else
		{
			beep();
			return;
		}
		execute( action );
	}
	
	
	/**
	 * 実行する<p>
	 * サブクラスで定義するtemplate method
	 */
	abstract public void execute(IAction action);

	public void selectionChanged(IAction action, ISelection selection)
	{
	}

	public void dispose()
	{
	}

	public void init(IWorkbenchWindow window)
	{
		this.window = window;
		//window.getActivePage().addPartListener( new PartListener() );
	}

	protected void beep()
	{
		window.getShell().getDisplay().beep();
	}

	/** ISourceViewerを得るためのインチキ */
	protected ISourceViewer getSourceViewer()
	{
		return (ISourceViewer)( editor.getAdapter(ITextOperationTarget.class) );
	}
	
	/**  カーソル位置を得る */
	protected int getCaretPos()
	{
		ISourceViewer viewer = getSourceViewer();
		int pos = viewer.getTextWidget().getCaretOffset();

		try
		{
			ITextViewerExtension5 extention = (ITextViewerExtension5)viewer;
			pos = extention.widgetOffset2ModelOffset(pos);
		}
		catch (ClassCastException e)
		{
		}
		
		return pos;
	}

	protected StyledText getTextWidget()
	{
		ISourceViewer viewer = getSourceViewer();
		return viewer.getTextWidget();
	}

	protected IDocument getDocument()
	{
		return getSourceViewer().getDocument();
		//return editor.getDocumentProvider().getDocument(editor.getEditorInput());
	}
	
	protected void setCaretPos(int offset)
	{
		ISourceViewer viewer = getSourceViewer();

		try
		{
			ITextViewerExtension5 extention = (ITextViewerExtension5)viewer;
			offset = extention.modelOffset2WidgetOffset(offset);
		}
		catch (ClassCastException e)
		{
		}

		// foldingされている内部に移動しようとした場合は無視する
		if (offset != -1)
		{
			viewer.getTextWidget().setSelection(offset);
		}
	}
	
	public IAction getAction(String actionId)
	{
		if (editor instanceof ITextEditor)
			return ((ITextEditor)editor).getAction(actionId);
		else
			return null;
	}
	
	/**
	 * @author tadashi
	 *
	 * 使わない<p>
	 * IPartListenerのテストのみ
	 */
	/*
	private class PartListener implements IPartListener
	{
		public void partActivated(IWorkbenchPart part)
		{
			System.out.println( "partActivated : " + part );
		}

		public void partBroughtToTop(IWorkbenchPart part)
		{
			System.out.println( "partBroughtToTop : " + part );
		}

		public void partClosed(IWorkbenchPart part)
		{
			System.out.println( "partClosed : " + part );
			
		}

		public void partDeactivated(IWorkbenchPart part)
		{
			System.out.println( "partDeactivated : " + part );
		}

		public void partOpened(IWorkbenchPart part)
		{
			System.out.println( "partOpened : " + part );
		}
		
	}
	*/
	
}

