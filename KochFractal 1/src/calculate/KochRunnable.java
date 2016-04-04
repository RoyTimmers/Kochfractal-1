/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import com.sun.deploy.security.ruleset.RunRule;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 *
 * @author Roy
 */
public class KochRunnable implements Runnable, Observer {

    KochFractal koch;
    private List<Edge> edges;
    public int count = 0;
    SIDE side;

    public enum SIDE {
        BOTT, LEFT, RIGHT;
    }

    public KochRunnable(int level, SIDE s, List<Edge> e)
    {
        koch = new KochFractal();
        koch.setLevel(level);
        koch.addObserver(this);
        edges = new ArrayList<>();
        koch.addObserver(this);
        side = s;
        edges = e;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        synchronized (edges) {
            edges.add((Edge) arg);
        }
    }

    public List<Edge> getEdges()
    {
        return edges;
    }

    @Override
    public void run()
    {
        switch (side) {
            case BOTT:
                koch.generateBottomEdge();
            case LEFT:
                koch.generateLeftEdge();
            case RIGHT:
                koch.generateRightEdge();
        }
    }

}
