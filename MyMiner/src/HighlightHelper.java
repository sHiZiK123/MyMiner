
public class HighlightHelper {
	private CellInfo currentCell = null;
	private CellInfo oldCell = null;
	
	public void HighlightCell(CellInfo info) {
		if(info == null && oldCell != null && oldCell.getCellState() != CellState.Opened) {
			oldCell.setCellState(CellState.Hidden);
			oldCell = null;
			return;
		}
		if(info == null || info.getCellState() == CellState.Opened)
			return;
		
		
		  if(oldCell == null) { oldCell = info; info.setCellState(CellState.Hovered); }
		  else { if(oldCell.getCellState() != CellState.Opened)
		  oldCell.setCellState(CellState.Hidden); info.setCellState(CellState.Hovered);
		  oldCell = info; }
		 
	}
}
