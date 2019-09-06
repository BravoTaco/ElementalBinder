package helpers;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.event.WebWalkEvent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import static data.GlobalVariables.*;

public class Walker {
    private static Area closestBank;

    public static Area closestBankToRuins() {
        closestBank = banks[0];
        for (int i = 1; i < banks.length; i++) {
            if (banks[i].getCentralPosition().distance(rune.getRuinsLocation().getCentralPosition()) < closestBank.getCentralPosition().distance(rune.getRuinsLocation().getCentralPosition())) {
                closestBank = banks[i];
            }
        }
        return closestBank;
    }

    public static boolean closestBankContainsPlayer() {
        closestBank = banks[0];
        for (int i = 1; i < banks.length; i++) {
            if (banks[i].getCentralPosition().distance(script.myPosition()) < closestBank.getCentralPosition().distance(script.myPosition())) {
                closestBank = banks[i];
            }
        }
        return closestBank.contains(script.myPlayer());
    }

    public static boolean webWalkToStartupArea() {
        status = "Walking to Startup Area!";
        WebWalkEvent webWalkEvent = new WebWalkEvent(closestBankToRuins());
        webWalkEvent.setEnergyThreshold(10);
        script.execute(webWalkEvent);
        return webWalkEvent.getStatus().equals(Event.EventStatus.FINISHED);
    }

    public static boolean webWalkToBank() {
        status = "Walking to Startup Area!";
        WebWalkEvent webWalkEvent = new WebWalkEvent(closestBankToRuins());
        webWalkEvent.setEnergyThreshold(10);
        script.execute(webWalkEvent);
        return webWalkEvent.getStatus().equals(Event.EventStatus.FINISHED);
    }

    public static boolean webWalkToRuins() {
        status = "Walking to Ruins!";
        WebWalkEvent webWalkEvent = new WebWalkEvent(rune.getRuinsLocation());
        webWalkEvent.setEnergyThreshold(10);
        script.execute(webWalkEvent);
        return webWalkEvent.getStatus().equals(Event.EventStatus.FINISHED);
    }

    public static boolean walkToBank() {
        status = "Walking Path to Bank!";
        Random rnd = new Random(System.currentTimeMillis());
        int rndNumber = rnd.nextInt(3);
        LinkedList<Position> posList = new LinkedList<>();
        Position[] walkPath = rune.getWalkPaths()[rndNumber];
        for (Position pos : walkPath) {
            posList.add(pos);
        }
        Collections.reverse(posList);
        WalkingEvent walkingEvent = new WalkingEvent();
        walkingEvent.setPath(posList);
        walkingEvent.setEnergyThreshold(10);
        script.execute(walkingEvent);
        if (closestBankToRuins().contains(script.myPlayer())) {
            return true;
        }
        return false;
    }

    public static boolean walkToRuins() {
        status = "Walking Path to Ruins!";
        Random rnd = new Random(System.currentTimeMillis());
        int rndNumber = rnd.nextInt(3);
        LinkedList<Position> posList = new LinkedList<>();
        Position[] walkPath = rune.getWalkPaths()[rndNumber];
        for (Position pos : walkPath) {
            posList.add(pos);
        }
        WalkingEvent walkingEvent = new WalkingEvent();
        walkingEvent.setPath(posList);
        walkingEvent.setEnergyThreshold(10);
        script.execute(walkingEvent);
        if (rune.getRuinsLocation().contains(script.myPlayer())) {
            return true;
        }
        return false;
    }
}
