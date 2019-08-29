package tasks;

import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.*;

public class WaitForMule {
    Player mule;

    public WaitForMule() throws InterruptedException {
        if (!muleIsNearby()) {
            if (waitForMule()) {
                new TradeWithMule();
            }
        } else {
            new TradeWithMule();
        }
    }

    private boolean waitForMule() {
        status = "Waiting for mule.";
        new ConditionalSleep(60000, 4000) {
            @Override
            public boolean condition() throws InterruptedException {
                return muleIsNearby();
            }
        }.sleep();
        if (muleIsNearby()) {
            return true;
        }
        return false;
    }

    private boolean muleIsNearby() {
        for (Player player : script.getPlayers().getAll()) {
            if (player != null) {
                if (muleNames.contains(player.getName()) && rune.getRuinsLocation().contains(player)) {
                    mule = player;
                    return true;
                }
            }
        }
        return false;
    }
}
