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
                script.log("Unable to local walk. Trying to WebWalk!");
                Walker.webWalkToRuins();
            }
        } else if (script.getObjects().closest("Altar") != null && script.getObjects().closest("Altar").getConfig() != savedData.selectedRune().getAltar().getConfig()) {
            new EnterRuins();
        } else if (script.getObjects().closest("Altar") != null && script.getObjects().closest("Altar").getConfig() == savedData.selectedRune().getAltar().getConfig()) {
            new UseAltar();
        }
    }

    private boolean ruinsLocationContainsPlayer() {
        if (script.getObjects().closest("Altar") != null) {
            return savedData.selectedRune().getRuinsLocation().contains(script.myPlayer()) && savedData.selectedRune().getAltar().getConfig() != script.getObjects().closest("Altar").getConfig();
        }
        return savedData.selectedRune().getRuinsLocation().contains(script.myPlayer());
    }

}
