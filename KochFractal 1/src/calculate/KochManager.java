/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 * @author Sander Geraedts - Code Panda
 */
public class KochManager {

    private JSF31KochFractalFX application;
    private List<Edge> edges;
    int next;

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        edges = new ArrayList<>();
    }

    public void changeLevel(int nxt) {
        next = nxt;
        edges.clear();
        TimeStamp calc = new TimeStamp();
        calc.setBegin("Level " + (nxt - 1));
        //generate
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread t1 = new Thread(new KochRunnable(nxt, KochRunnable.SIDE.BOTT, edges));
                Thread t2 = new Thread(new KochRunnable(nxt, KochRunnable.SIDE.LEFT, edges));
                Thread t3 = new Thread(new KochRunnable(nxt, KochRunnable.SIDE.RIGHT, edges));
                t1.start();
                t2.start();
                t3.start();
                try {
                    t1.join();
                    t2.join();
                    t3.join();
                    application.requestDrawEdges();
                } catch (InterruptedException ex) {
                    Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t4.start();


        calc.setEnd("Level " + nxt);
        application.setTextCalc(calc.toString());
    }

    public void drawEdges() {
        application.clearKochPanel();
        TimeStamp time = new TimeStamp();
        time.setBegin("Level " + (next - 1));
        for (Edge e : edges) {
            application.drawEdge(e);
        }
        time.setEnd("Level " + next);
        application.setTextDraw(time.toString());
        application.setTextNrEdges("" + this.edges.size());
    }

}
