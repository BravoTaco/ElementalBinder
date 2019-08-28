package helpers;

import data.GlobalVariables;
import org.osbot.rs07.script.Script;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Paint {
    private Font stringFont = new Font("SansSerif", Font.BOLD, 12);
    private Font stringLargeFont = new Font("SansSerif", Font.BOLD, 20);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Script script;

    private RoundRectangle2D backGroundBox;

    private String formatTime(final long time) {
        final int sec = (int) (time / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
    }

    private String formatValue(final long l) {
        return (l > 1_000_000) ? String.format("%.2fm", ((double) l / 1_000_000))
                : (l > 1000) ? String.format("%.1fk", ((double) l / 1000))
                : l + "";
    }

    private void drawBox(Graphics2D g, int x, int y, int xSize, int ySize, Color c) {
        RoundRectangle2D rd = new RoundRectangle2D.Float(x, y, xSize, ySize, 10, 10);

        g.setColor(c);

        g.draw(rd);
    }

    public Paint(Script script){
        this.script = script;
    }

    public void drawData(Graphics2D g, int x, int y, long runTime, long timeToLevel, long xpPerHour, long amountOfFishCaught, int gainedXp, long xpTillLevel) {
        float alphaAmount = 0.95f;
        int width = 510;
        int height = 137;

        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaAmount);

        backGroundBox = new RoundRectangle2D.Float(x, y, width, height, 5, 5);

        g.setColor(Color.GRAY);
        g.draw(backGroundBox);
        g.setComposite(alpha);
        g.setColor(Color.DARK_GRAY);
        g.fill(backGroundBox);


        //Current Status
        g.setFont(stringFont);
        drawBox(g, x + (width / 6), y + 50, 330, 25, Color.BLACK);
        g.setColor(Color.RED);
        g.drawString("Current", x + (width / 6) + 8, y + 62);
        g.drawString("Status:", x + (width / 6) + 10, y + 72);
        g.setFont(stringLargeFont);
        g.setColor(Color.ORANGE);
        g.drawString(GlobalVariables.status, x + (width / 6) + 55, y + 69);

        //Run Time / Time left
        drawBox(g, x + (width / 10), y + 85, 110, 20, Color.BLACK);
        g.setFont(stringFont);
        g.setColor(Color.RED);
        g.drawString("RunTime:", x + (width / 10) + 3, y + 100);
        g.setColor(Color.ORANGE);
        g.drawString(formatTime(runTime), x + (width / 10) + 60, y + 100);
        g.setColor(Color.YELLOW);

        //Time Till Level
        drawBox(g, x + (width / 10), y + 115, 110, 20, Color.BLACK);
        g.setColor(Color.RED);
        g.drawString("Time Till", x + (width / 10) + 5, y + 125);
        g.drawString("  Level:", x + (width / 10) + 5, y + 135);
        g.setColor(Color.ORANGE);
        g.drawString(formatTime(timeToLevel), x + (width / 10) + 60, y + 130);

        //Xp/Hr
        drawBox(g, x + (width / 3) + 30, y + 85, 110, 20, Color.BLACK);
        g.setColor(Color.RED);
        g.drawString("Xp/Hr:", x + (width / 3) + 33, y + 100);
        g.setColor(Color.ORANGE);
        g.drawString(formatValue(xpPerHour), x + (width / 3) + 75, y + 100);

        //Fires Made
        drawBox(g, x + (width / 2) + 90, y + 85, 110, 20, Color.BLACK);
        g.setColor(Color.RED);
        g.drawString("Runes Made:", x + (width / 2) + 95, y + 100);
        g.setColor(Color.ORANGE);
        g.drawString(formatValue(amountOfFishCaught), x + (width / 2) + 173, y + 100);

        //Gained Xp
        drawBox(g, x + (width / 2) + 90, y + 115, 110, 20, Color.BLACK);
        g.setColor(Color.RED);
        g.drawString("Gained Xp:", x + (width / 2) + 95, y + 130);
        g.setColor(Color.ORANGE);
        g.drawString(formatValue(gainedXp), x + (width / 2) + 160, y + 130);

        //Xp Till Level
        drawBox(g, x + (width / 3) + 20, y + 115, 130, 20, Color.BLACK);
        g.setColor(Color.RED);
        g.drawString("Xp Till Level:", x + (width / 3) + 25, y + 130);
        g.setColor(Color.ORANGE);
        g.drawString(formatValue(xpTillLevel), x + (width / 3) + 98, y + 130);

        //Title
        g.setFont(titleFont);
        g.drawString("Elemental Binder [V" + script.getVersion() + "]", x + (width / 4) - 10, y + 30);

    }

}
