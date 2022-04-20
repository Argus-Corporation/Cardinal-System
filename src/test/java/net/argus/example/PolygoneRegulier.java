package net.argus.example;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.function.Function;

public class PolygoneRegulier extends Polygone {
    private static final long serialVersionUID = 1L;

    private static final int[] compute(double coord0, double rayon, int nbCotes, double rotation, Function<Double, Double> trigo) {
        int[] coord = new int[nbCotes];
        for (int i = 0; i < nbCotes; i++)
            coord[i] = (int) (coord0 + rayon * trigo.apply(2 * i * Math.PI / nbCotes + rotation));
        return coord;
    }

    public PolygoneRegulier(Point2D centre, double rayon, int nbCotes, double rotation, Color color) {
        super(compute(centre.getX(), rayon, nbCotes, rotation, Math::cos),
              compute(centre.getY(), rayon, nbCotes, rotation, Math::sin),
              color);
    }
}