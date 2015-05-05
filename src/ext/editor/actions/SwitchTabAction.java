package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.custom.StyledText;

public class SwitchTabAction extends AbstractAction
{
	public void execute(IAction action)
	{
		StyledText widget = getTextWidget();
		int width = widget.getTabs();
		if (width == 4)
			width = 8;
		else
			width = 4;
		widget.setTabs(width);
	}
}

