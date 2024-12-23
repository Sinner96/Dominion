import gdi.game.dominion.BaseGame;
import gdi.game.dominion.DominionInterface;

public class Main {
    public static void main(String[] args) {
        DominionTileManager board = new DominionTileManager(5,7);
        Dominion dom = new Dominion(board);

        BaseGame game = new BaseGame(args, dom);
        game.run();
    }
}