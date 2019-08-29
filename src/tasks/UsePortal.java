package tasks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class UsePortal {
    RS2Object portal;

    public UsePortal() throws InterruptedException {
        status = "Using Portal!";
        if (portalExists()) {
            if (usePortal()) {
                script.log("Used Portal!");
                if (!mulingEnabled) {
                    new GetEssence();
                }
            }
        } else if (portalIsNotVisible()){
            if(walkToPortal()){
                new UsePortal();
            }
        }
    }

    private boolean walkToPortal(){
        return script.getWalking().walk(portal);
    }

    private boolean portalExists() {
        return ((portal = script.getObjects().closest("Portal")) != null) && portal.getPosition().isOnMiniMap(script.getBot());
    }

    private boolean portalIsNotVisible(){
        return portal != null && !portal.isVisible();
    }

    private boolean usePortal() {
        if (portal.interact("Use")) {
            new ConditionalSleep(15000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return !portal.exists();
                }
            }.sleep();
            return true;
        }
        return false;
    }
}
