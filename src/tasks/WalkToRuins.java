package tasks;

import helpers.Walker;

import static data.GlobalVariables.*;

public class WalkToRuins {
    public WalkToRuins() throws InterruptedException {
        status = "Walking To Ruins!";
        if (!ruinsLocationContainsPlayer()) {
            if (Walker.ruinsWalkEvent()) {
                script.log("Walked To Ruins!");
            }
        } else if (script.getObjects().closest("Altar").getConfig() != rune.getAltar().getConfig()) {
            new EnterRuins();
        } else if (script.getObjects().closest("Altar").getConfig() == rune.getAltar().getConfig()) {
            new UseAltar();
        }
    }

    private boolean ruinsLocationContainsPlayer() {
        return rune.getRuinsLocation().contains(script.myPlayer()) && rune.getAltar().getConfig() != script.getObjects().closest("Altar").getConfig();
    }

}
