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

        for (int col = 0; col < tileManager.getNumColumns(); col++) {     //set random tile
            for (int raw = 0; raw < tileManager.getNumRows(); raw++) {
                if (Math.random() < 0.5){
                    tileManager.getTile(col, raw).setOwner(player);
                }else {
                    tileManager.getTile(col, raw).setOwner(npc);
                }
            }
        }

    }




    @Override
    public void update(BaseGame baseGame, double v) {
        checkWinner();
        if  (winner!=null){
            baseGame.setWinner(winner);                             //if att. winner was set by checkWinner() then winner will be set and game ends
        }

        if (!npc.isWalking() && v >= npc.getDepartureTime()){
                chooseTarget(npc);                                      //if game time reached the departure time which was set by reached target, then it choose next
        }

    }

    private void checkWinner() {
        boolean playerWon = true;
        boolean npcWon = true;

        for (int col = 0; col < tileManager.getNumColumns(); col++) {
            for (int raw = 0; raw < tileManager.getNumRows(); raw++) {
                if (tileManager.getTile(col, raw).isPropertyOf(npc)) {
                    playerWon = false;
                } else if (tileManager.getTile(col, raw).isPropertyOf(player)) {            //if a figure has all tile, should one of the boolean set to false
                    npcWon = false;
                }


            }
        }

        if (playerWon){
            winner=getPlayer();
        }else if(npcWon){                                                                   //if one of figures won, should his boolean still true and winner shall be set
            winner=getNpc();
        }
    }

    @Override
    public void chooseTarget(Figure figure) {

        DominionTile next = null;

       for (int col = 0; col < tileManager.getNumColumns(); col++){
           for (int raw = 0; raw < tileManager.getNumRows(); raw++){

               DominionTile curr = tileManager.getTile(col, raw);     //current choosen tile

               if (curr.isPropertyOf(figure)){
                   continue;                                        //skip if owned by npc
               }

               next = curr;

           }
        }
       figure.moveTo(next);                                         //move to next
    }


    @Override
    public void clickedTile(DominionTile dominionTile) {
            if(!getPlayer().isWalking()){
                getPlayer().moveTo(dominionTile);
                dominionTile.setOwner(getPlayer());
            }
    }

    @Override
    public void reachedTarget(Figure figure, double v) {
        figure.getTile().setOwner(figure);             //set the new owner of the tile
        figure.setPauseDuration(.5);                    //set pause half second for NPC to make it faster


        if(figure.isNpc()){
            figure.setDepartureTime(v + figure.getPauseDuration());    //set deptarture time = (current game time + pause duration)
        }
    }
}
