package tictactoe;

public class BoardCell {
    private Boolean isValidCell;
    private String cellValue;

    public BoardCell(Boolean isValid){
        isValidCell = isValid;
        cellValue = null;
    }

    public Boolean isValid(){
        return isValidCell;
    }

    public Boolean isEmpty(){
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
