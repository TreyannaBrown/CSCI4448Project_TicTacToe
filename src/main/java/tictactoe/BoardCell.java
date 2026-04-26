package tictactoe;

public class BoardCell {
    private final boolean isValidCell;
    private String cellValue;

    public BoardCell(boolean isValid){
        isValidCell = isValid;
        cellValue = null;
    }

    public boolean isValid(){
        return isValidCell;
    }

    public boolean isEmpty(){
        return cellValue == null;
    }

    public String getCellValue(){
        return cellValue;
    }

    public void setCellValue(String value){
        if(!isValidCell){
            throw new RuntimeException("cannot place into a invalid cell");
        }
        if(cellValue == null){
            cellValue = value;
        }
        else{
            throw new RuntimeException("Cannot reassign a cell");
        }
    }
}
