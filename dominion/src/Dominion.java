import gdi.game.dominion.*;

import java.awt.*;

public class Dominion implements DominionInterface {
    private Figure player;
    Figure npc;
    Figure winner;
    private DominionTileManagerInterface tileManager;
    public Dominion(DominionTileManagerInterface tileManager){
         this.tileManager = tileManager;
    }

    @Override
    public DominionTileManagerInterface getDominionTileManager() {
        return this.tileManager;
    }

    @Override
    public Figure getPlayer() {
        return player;
    }

    @Override
    public Figure getNpc() {
        return npc;
    }

    @Override
    public void setupWorld(BaseGame baseGame) {
        player = new Figure(baseGame, Color.blue,false);
        npc = new Figure(baseGame, Color.red, true);


    }


    @Override
    public void update(BaseGame baseGame, double v) {
            if (!npc.isWalking()){
                npc.setDepartureTime(npc.getPauseDuration());   //set new target
                chooseTarget(npc);
            }
            if(checkWinner()){
                baseGame.setWinner(winner);
            }
    }

    private boolean checkWinner() {
        int raw = tileManager.getNumRows();
        int col = tileManager.getNumColumns();
        return false;
    }

    @Override
    public void chooseTarget(Figure figure) {
        figure.getTile();
    }

    @Override
    public void clickedTile(DominionTile dominionTile) {
            if(!getPlayer().isWalking()){
                getPlayer().moveTo(dominionTile);
            }
    }

    @Override
    public void reachedTarget(Figure figure, double v) {
        figure.getTile().setOwner(figure);
        if(figure.isNpc()){
            figure.setDepartureTime(figure.getDepartureTime());
        }
    }
}
