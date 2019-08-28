package tasks;

import helpers.Walker;

import static data.GlobalVariables.*;

public class WalkToRuins {
    public WalkToRuins() throws InterruptedException {
        status = "Walking To Ruins!";
        if (!ruinsLocationContainsPlayer()) {
            if (Walker.ruinsWalkEvent()) {
                script.log("Walked To Ruins!");
                new EnterRuins();
            }
        }
    }

    private boolean ruinsLocationContainsPlayer() {
        return rune.getRuinsLocation().contains(script.myPlayer());
    }

}
