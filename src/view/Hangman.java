package view;

import utility.LinkedListStack;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

public class Hangman extends JPanel {

    private LinkedListStack<Runnable> steps;
    private GeneralPath path;

    public Hangman() {
        this.setPreferredSize(new Dimension(300, 300));
        this.path = new GeneralPath();
        this.steps = new LinkedListStack<>();
        initStack();
    }

    private void initStack() {
        Ellipse2D head = new Ellipse2D.Double(235, 75, 30, 30); //kopf
        Arc2D ground = new Arc2D.Double(0, 275, 200, 50, 0, 180, Arc2D.OPEN); //hügel
        Ellipse2D body = new Ellipse2D.Double(230, 110, 40, 100); // bauch

        //rechtes bein
        steps.push(() -> {
            path.moveTo(258, 207);
            path.lineTo(264, 232);
            path.lineTo(264, 257);
        });

        //linkes bein
        steps.push(() -> {
            path.moveTo(242, 207);
            path.lineTo(238, 232);
            path.lineTo(238, 257);
        });

        //rechter arm
        steps.push(() -> {
            path.moveTo(267, 130);
            path.lineTo(280, 140);
            path.lineTo(280, 170);
        });

        //linker arm
        steps.push(() -> {
            path.moveTo(233, 130);
            path.lineTo(220, 140);
            path.lineTo(220, 170);
        });

        //hals + bauch
        steps.push(() -> {
            path.moveTo(250, 105);
            path.lineTo(250, 110);
            path.append(body, false);
        });

        //schlinge + kopf
        steps.push(() -> {
            path.moveTo(250, 50);
            path.lineTo(250, 75);
            path.append(head, false);
        });

        //stützbalken
        steps.push(() -> {
            path.moveTo(150, 50);
            path.lineTo(100, 100);
        });

        //querbalken
        steps.push(() -> path.lineTo(250, 50));

        //mast
        steps.push(() -> {
            path.moveTo(100, 275);
            path.lineTo(100, 50);
        });

        //hügel
        steps.push(() -> path.append(ground, false));
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D graphics = (Graphics2D) gr;
        graphics.setColor(Color.RED);
        graphics.drawRect(0, 0, 300, 300);
        graphics.setColor(Color.BLACK);

        graphics.draw(path);
    }

    public void drawNext() {
        steps.pop().run();
        repaint();
    }

    public void reset() {
        path.reset();
        initStack();
    }

    /*
        0 = hügel
        1 = mast
        2 = querbalken
        3 = stützbalken
        4 = schlinge + kopf
        5 = bauch
        6 = linker arm
        7 = rechter arm
        8 = linkes bein
        9 = rechtes bein
         */
}
