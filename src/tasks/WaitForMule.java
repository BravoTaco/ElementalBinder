package tasks;

import Utils.PlayerUtilities;
import org.osbot.rs07.utility.ConditionalSleep;

import static data.GlobalVariables.savedData;
import static data.GlobalVariables.status;

public class WaitForMule {

    public WaitForMule() throws InterruptedException {
        if (PlayerUtilities.getPlayerFromNames(savedData.muleNames()) != null) {
            if (waitForMule()) {
                new TradeWithMule();
            }
        } else {
            new TradeWithMule();
        }
    }

    private boolean waitForMule() {
        status = "Waiting for mule.";
        new ConditionalSleep(10000, 1500) {
            @Override
            public boolean condition() throws InterruptedException {
                return PlayerUtilities.getPlayerFromNames(savedData.muleNames()) != null;
            }
        }.sleep();
        return PlayerUtilities.getPlayerFromNames(savedData.muleNames()) != null;
    }

}
