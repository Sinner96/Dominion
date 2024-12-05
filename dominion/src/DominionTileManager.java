import gdi.game.dominion.BaseGame;
import gdi.game.dominion.DominionTile;
import gdi.game.dominion.DominionTileManagerInterface;
import gdi.game.map.MapWorld;

import java.util.Arrays;

public class DominionTileManager implements DominionTileManagerInterface {
     private DominionTile[][] board;
     private int col;
     private int raw;

    DominionTileManager(int x,int y){
        this.raw = y;
        this.col = x;
        board = new DominionTile[raw][col];

    }


    @Override
    public DominionTile getTile(int i, int i1) {

        if(isValid(i,i1)) {
            return board[i1][i]; //if valid return Tile
        }else
            return null;
    }

    @Override
    public void setupMapTiles(MapWorld mapWorld) {
        for (int raw = 0; raw < getNumRows(); raw++){
            for (int col = 0; col < getNumColumns(); col++){
                board[raw][col]= new DominionTile(mapWorld,col,raw);
            }
        }
    }

    @Override
    public int getNumRows() {
        return raw;
    }

    @Override
    public int getNumColumns() {
        return col;
    }

    @Override
    public DominionTile[] getColumn(int i) {
        DominionTile[] colmnArr = new DominionTile[getNumRows()];
        for (int row = 0; row < getNumRows() ; row++) {
            colmnArr[row] = board[row][i]; // Retrieve the value from each row at column i
        }
        return colmnArr;
    }

    @Override
    public boolean isValid(int i, int i1) {
        if (i < 0 || i1 < 0) {
            return false;  //check if negativ
        }else if (i > getNumColumns() - 1 || i1 > getNumRows() - 1){
            return false; //check if exceeds array size
        }else{
            return true;
        }
    }
}
