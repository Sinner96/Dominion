import gdi.game.dominion.*;

import java.awt.*;

public class Dominion implements DominionInterface {
    private Figure player;
    Figure npc;
    Figure winner = null;
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
        if(checkWinner()){
            baseGame.setWinner(winner);
        }
            if (!npc.isWalking()){
                npc.setDepartureTime(npc.getPauseDuration());   //set new target
                chooseTarget(npc);
            }

    }

    private boolean checkWinner() {
        boolean playerWon = true;
        boolean npcWon = true;

        for (int col = 0; col < tileManager.getNumColumns(); col++) {
            for (int raw = 0; raw < tileManager.getNumRows(); raw++) {
                if (tileManager.getTile(col, raw).isPropertyOf(npc)) {
                    playerWon = false;
                } else if (tileManager.getTile(col, raw).isPropertyOf(player)) {
                    npcWon = false;
                }
            }
        }
        if (playerWon) {
            winner = getPlayer();

        } else if (npcWon){
            winner = getNpc();
        }

        return false;
    }

    @Override
    public void chooseTarget(Figure figure) {
       int currRaw = figure.getTile().getRow();
       int currCol = figure.getTile().getColumn();
        DominionTile next = null;

       for (int col = 0; col < tileManager.getNumColumns(); col++){
           for (int raw = 0; raw < tileManager.getNumRows(); raw++){

               if (raw == currRaw && col == currCol) {
                   continue;
               }

               DominionTile curr = tileManager.getTile(col, raw);

               if (curr.isPropertyOf(figure)){
                   continue;
               }
               next = curr;

           }
        }
       figure.moveTo(next);
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
