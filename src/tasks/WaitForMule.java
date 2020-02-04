package tasks;

import Utils.StringUtilities;
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
        return muleIsNearby();
    }

    private boolean muleIsNearby() {
        for (Player player : script.getPlayers().getAll()) {
            for (String s : savedData.muleNames()) {
                if (StringUtilities.stringMatchesBasedOnChars(s, player.getName())) {
                    script.log("Name Matches!");
                    mule = player;
                    return true;
                }
            }
        }
        return false;
    }
}
