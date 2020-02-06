package core;

import Utils.StringUtilities;
import data.GlobalVariables;
import enums.State;
import enums.Talisman;
import enums.Tiara;
import github.VersionChecker;
import gui.GUI;
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

@ScriptManifest(name = "Elemental Binder", author = "BravoTaco", version = 1.89, info = "Runecrafts F2P Runes.", logo = "https://i.imgur.com/svwoFav.png")
public class ElementalBinder extends Script {

    private final Filter<Item> essenceFilter = item -> item.getName().equals("Rune essence") || item.getName().equals("Pure essence");
    private final long startTime = System.currentTimeMillis();
    private Paint paint;
    private State state;
    private Filter<Item> tiaraFilter;
    private Filter<Item> talismanFilter;
    private int startXp;
    private boolean started = false;

    private GUI gui;

    @Override
    public void onStart() {
        initializeVariables();
        if (runChecks()) {
            log("Checks Complete!");
        } else {
            stop(false);
            return;
        }
        if (!hasTiaraOrTalisman() && !hasEssence()) {
            if (Walker.webWalkToStartupArea()) {
                log("Walked To Startup Area!");
            }
        }
    }


    @Override
    public void onExit() {
        log("Script exited. Leave feedback at: https://osbot.org/forum/topic/156306-elemental-binder-open-source-runecrafter");
    }

    @Override
    public int onLoop() throws InterruptedException {
        state = setState();
        if (state != null && !savedData.isMule()) {
            log("Not Mule!");
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
                default:
                    status = "Default";
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
                default:
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
            paint.drawData(g, 5, 340, runTime, timeTillLevel, xpPerHour, amountOfRunesMade, amountOfEssencesTraded, gainedXp, xpTillLevel);
        }

    }

    private void initializeVariables() {
        script = this;
        rcLevel = getSkills().getStatic(Skill.RUNECRAFTING);
        getExperienceTracker().start(Skill.RUNECRAFTING);
        startXp = getSkills().getExperience(Skill.RUNECRAFTING);
        paint = new Paint(this);
        amountOfEssencesTraded = 0;
        amountOfRunesMade = 0;
        started = true;
    }

    private boolean runChecks() {
        if (VersionChecker.needsUpdated("BravoTaco", "ElementalBinder", getVersion(), bot.getCanvas())) {
            log("Newer Version Available on GitHub. Link: https://github.com/BravoTaco/Elemental-Binder/releases");
            JOptionPane.showMessageDialog(getBot().getCanvas(), "Newer version is available on GitHub! \n https://github.com/BravoTaco/Elemental-Binder/releases");
        } else {
            log("Script Up-To-Date!");
        }

        gui = new GUI();
        gui.start();
        if (gui.wasExited()) {
            return false;
        } else {
            savedData.setTiara(getTiaraBasedOnRune());
            savedData.setTalisman(getTalismanBasedOnRune());
            tiaraFilter = item -> item.getName().equals(savedData.tiara().getName());
            talismanFilter = item -> item.getName().equals(savedData.talisman().getName());
            if (savedData.selectedRune().getLevelRequirement() > getSkills().getStatic(Skill.RUNECRAFTING) && !savedData.isMule()) {
                log("Level Requirement not met! Stopping script!");
                JOptionPane.showMessageDialog(getBot().getCanvas(), "Runecrafting level does meet \n specified requirement of: " + savedData.selectedRune().getLevelRequirement());
                stop(false);
            }
        }
        return true;
    }

    private State setState() {
        if (!savedData.isMule()) {
            if (!hasTiaraOrTalisman()) {
                return State.GETTOOL;
            } else if (!hasEssence() && hasTiaraOrTalisman() && !portalExists() && !altarExists() && !savedData.isMuling()) {
                return State.GETESSENCE;
            } else if (hasEssence() && !portalExists() && !altarExists() && !mysteriousRuinsExists()) {
                return State.WALKTORUINS;
            } else if (mysteriousRuinsExists() && hasEssence() && !portalExists() && !altarExists()) {
                return State.ENTERRUINS;
            } else if (altarExists() && hasEssence()) {
                return State.USEALTAR;
            } else if (portalExists() && !hasEssence()) {
                return State.USEPORTAL;
            } else if (!hasEssence() && hasTiaraOrTalisman() && !portalExists() && !altarExists() && savedData.isMuling() && mysteriousRuinsExists() && !muleIsNearby()) {
                return State.WAITFORMULE;
            } else if (muleIsNearby() && !hasEssence() && hasTiaraOrTalisman() && savedData.isMuling()) {
                return State.TRADEWITHMULE;
            }
        } else {
            if (!hasEssence()) {
                return State.GETESSENCE;
            } else if (hasEssence() && !savedData.selectedRune().getRuinsLocation().contains(myPlayer())) {
                return State.WALKTORUINS;
            } else if (hasEssence() && savedData.selectedRune().getRuinsLocation().contains(myPlayer()) && runecrafterNearby()) {
                return State.TRADEWITHCRAFTER;
            } else if (hasEssence() && savedData.selectedRune().getRuinsLocation().contains(myPlayer()) && !runecrafterNearby()) {
                status = "Waiting for crafter!";
            }
        }
        return null;
    }

    private boolean runecrafterNearby() {
        for (Player player : getPlayers().getAll()) {
            if (player.getName().equals(savedData.runecrafterName())) {
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
        return ((portal = getObjects().closest("Portal")) != null && portal.getConfig() == savedData.selectedRune().getPortal().getConfig());
    }

    private boolean altarExists() {
        RS2Object altar;
        return ((altar = getObjects().closest("Altar")) != null && altar.getConfig() == savedData.selectedRune().getAltar().getConfig());
    }

    private boolean hasTiaraOrTalisman() {
        return getEquipment().getItem(tiaraFilter) != null || getInventory().contains(talismanFilter);
    }

    private boolean hasEssence() {
        return getInventory().contains(essenceFilter);
    }

    private boolean muleIsNearby() {
        for (Player player : script.getPlayers().getAll()) {
            for (String s : savedData.muleNames()) {
                if (StringUtilities.stringMatchesWithRemovedWhiteSpace(s, player.getName()))
                    return true;
            }
        }
        return false;
    }

    private Talisman getTalismanBasedOnRune() {
        switch (GlobalVariables.savedData.selectedRune()) {
            case AIR:
                return Talisman.AIR;
            case MIND:
                return Talisman.MIND;
            case WATER:
                return Talisman.WATER;
            case EARTH:
                return Talisman.EARTH;
            case FIRE:
                return Talisman.FIRE;
            case BODY:
                return Talisman.BODY;
            default:
                return null;
        }
    }

    private Tiara getTiaraBasedOnRune() {
        switch (GlobalVariables.savedData.selectedRune()) {
            case AIR:
                return Tiara.AIR;
            case MIND:
                return Tiara.MIND;
            case WATER:
                return Tiara.WATER;
            case EARTH:
                return Tiara.EARTH;
            case FIRE:
                return Tiara.FIRE;
            case BODY:
                return Tiara.BODY;
            default:
                return null;
        }
    }

}