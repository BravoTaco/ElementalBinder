package tasks;

import helpers.Walker;

import static data.GlobalVariables.*;

public class WalkToRuins {
    public WalkToRuins() throws InterruptedException {
        if (!ruinsLocationContainsPlayer()) {
            status = "Walking To Ruins!";
            if (Walker.walkToRuins()) {
                script.log("Walked To Ruins!");
            } else {
                Walker.webWalkToRuins();
            }
        } else if (script.getObjects().closest("Altar") != null && script.getObjects().closest("Altar").getConfig() != rune.getAltar().getConfig()) {
            new EnterRuins();
        } else if (script.getObjects().closest("Altar") != null && script.getObjects().closest("Altar").getConfig() == rune.getAltar().getConfig()) {
            new UseAltar();
        }
    }

    private boolean ruinsLocationContainsPlayer() {
        if (script.getObjects().closest("Altar") != null) {
            return rune.getRuinsLocation().contains(script.myPlayer()) && rune.getAltar().getConfig() != script.getObjects().closest("Altar").getConfig();
        }
        return rune.getRuinsLocation().contains(script.myPlayer());
    }

}
