package core;

import enums.State;
import github.VersionChecker;
import gui.GUI;
import helpers.MessageBox;
import helpers.Paint;
import helpers.Walker;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import tasks.*;

import javax.swing.*;
import java.awt.*;

import static data.GlobalVariables.*;

@ScriptManifest(name = "Elemental Binder", author = "BravoTaco", version = 1.5, info = "Runecrafts F2P Runes.", logo = "https://i.imgur.com/svwoFav.png")
public class ElementalBinder extends Script {

    private final Filter<Item> essenceFilter = item -> item.getName().equals("Rune essence") || item.getName().equals("Pure essence");
    private final long startTime = System.currentTimeMillis();
    private GUI gui;
    private Paint paint;
    private State state;
    private Filter<Item> tiaraFilter;
    private Filter<Item> talismanFilter;
    private int startXp;
    private boolean started = false;

    @Override
    public void onStart() {
        initializeVariables();
        runChecks();
        log("Checks Complete!");
        if (!hasTiaraOrTalisman() && !hasEssence()) {
            if (Walker.webWalkToStartupArea()) {
                log("Walked To Startup Area!");
            }
        }
    }


    @Override
    public void onExit() {

    }

    @Override
    public int onLoop() throws InterruptedException {
        state = setState();
        if (state != null && !isMule) {
            switch (state) {
                case GETTOOL:
                    new GetTool();
                    break;
                case GETESSENCE:
                    new GetEssence();
                    break;
                case WALKTORUINS:
                    new WalkToRuins();
                    break;
                case ENTERRUINS:
                    new EnterRuins();
                    break;
                case USEALTAR:
                    new UseAltar();
                    break;
                case USEPORTAL:
                    new UsePortal();
                    break;
                case WAITFORMULE:
                    new WaitForMule();
                    break;
                case TRADEWITHMULE:
                    new TradeWithMule();
                    break;
            }
        } else if (state != null) {
            switch (state) {
                case GETESSENCE:
                    new GetEssence();
                    break;
                case WALKTORUINS:
                    new WalkToRuins();
                    break;
                case TRADEWITHCRAFTER:
                    new TradeWithCrafter();
                    break;
            }
        }

        return random(1300, 2300);

    }

    @Override
    public void onPaint(Graphics2D g) {

        int gainedXp = getSkills().getExperience(Skill.RUNECRAFTING) - startXp;
        long runTime = System.currentTimeMillis() - startTime;
        long xpPerHour = getExperienceTracker().getGainedXPPerHour(Skill.RUNECRAFTING);
        long timeTillLevel = getExperienceTracker().getTimeToLevel(Skill.RUNECRAFTING);
        long xpTillLevel = getSkills().getExperienceForLevel(getSkills().getStatic(Skill.RUNECRAFTING) + 1) - getSkills().getExperience(Skill.RUNECRAFTING);

        if (started) {
            paint.drawData(g, 5, 340, runTime, timeTillLevel, xpPerHour, amountOfRunesMade, gainedXp, xpTillLevel);
        }

    }

    private void initializeVariables(){
        script = this;
        gui = new GUI();
        rcLevel = getSkills().getStatic(Skill.RUNECRAFTING);
        getExperienceTracker().start(Skill.RUNECRAFTING);
        startXp = getSkills().getExperience(Skill.RUNECRAFTING);
        paint = new Paint(this);
        started = true;
    }

    private void runChecks(){
        if(VersionChecker.needsUpdated("BravoTaco", "Elemental-Binder", getVersion())){
            log("Newer Version Available on GitHub. Link: https://github.com/BravoTaco/Elemental-Binder/releases");
        } else {
            log("Script Up-To-Date!");
        }
        gui.show();
        if (gui.exited()) {
            stop(false);
        } else {
            tiaraFilter = item -> item.getName().equals(tiara.getName());
            talismanFilter = item -> item.getName().equals(talisman.getName());
            if (rune.getLevelRequirement() > getSkills().getStatic(Skill.RUNECRAFTING) && !isMule) {
                log("Level Requirement not met! Stopping script!");
                new MessageBox("Runecrafting level does meet specified requirement of: " + rune.getLevelRequirement()).showMessage();
                stop(false);
            }
        }
    }

    private State setState() {
        if (!isMule) {
            if (!hasTiaraOrTalisman()) {
                return State.GETTOOL;
            } else if (!hasEssence() && hasTiaraOrTalisman() && !portalExists() && !altarExists() && !mulingEnabled) {
                return State.GETESSENCE;
            } else if (hasEssence() && !portalExists() && !altarExists() && !mysteriousRuinsExists()) {
                return State.WALKTORUINS;
            } else if (mysteriousRuinsExists() && hasEssence() && !portalExists() && !altarExists()) {
                return State.ENTERRUINS;
            } else if (altarExists() && hasEssence()) {
                return State.USEALTAR;
            } else if (portalExists() && !hasEssence()) {
                return State.USEPORTAL;
            } else if (!hasEssence() && hasTiaraOrTalisman() && !portalExists() && !altarExists() && mulingEnabled && mysteriousRuinsExists() && !muleNearby()) {
                return State.WAITFORMULE;
            } else if (muleNearby() && !hasEssence() && hasTiaraOrTalisman() && mulingEnabled) {
                return State.TRADEWITHMULE;
            }
        } else {
            if (!hasEssence()) {
                return State.GETESSENCE;
            } else if (hasEssence() && !mysteriousRuinsExists()) {
                return State.WALKTORUINS;
            } else if (hasEssence() && mysteriousRuinsExists() && runecrafterNearby()) {
                return State.TRADEWITHCRAFTER;
            }
        }
        return null;
    }

    private boolean runecrafterNearby() {
        for (Player player : getPlayers().getAll()) {
            if (player.getName().equals(runecrafterName)) {
                return true;
            }
        }
        return false;
    }

    private boolean mysteriousRuinsExists() {
        RS2Object ruins;
        return ((ruins = getObjects().closest("Mysterious ruins")) != null) && ruins.getPosition().isOnMiniMap(this.bot);
    }

    private boolean portalExists() {
        RS2Object portal;
        return ((portal = getObjects().closest("Portal")) != null && portal.getConfig() == rune.getPortal().getConfig());
    }

    private boolean altarExists() {
        RS2Object altar;
        return ((altar = getObjects().closest("Altar")) != null && altar.getConfig() == rune.getAltar().getConfig());
    }

    private boolean hasTiaraOrTalisman() {
        return getEquipment().getItem(tiaraFilter) != null || getInventory().contains(talismanFilter);
    }

    private boolean hasEssence() {
        if (!isMule) {
            return getInventory().contains(essenceFilter);
        }
        return getInventory().contains(essenceFilter) && getInventory().getAmount(essenceFilter) >= 27;
    }

    private boolean muleNearby() {
        for (Player player : getPlayers().getAll()) {
            if (muleNames.contains(player.getName())) {
                return true;
            }
        }
        return false;
    }

}