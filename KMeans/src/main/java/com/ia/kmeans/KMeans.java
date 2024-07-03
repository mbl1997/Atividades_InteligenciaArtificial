package com.ia.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author marib
 */
public class KMeans {

    private int k; // número de clusters
    private List<Point> points; // lista de pontos
    private List<Cluster> clusters; // lista de clusters

    public KMeans(int k, List<Point> points) {
        this.k = k;
        this.points = points;
        this.clusters = new ArrayList<>();

        // Inicializa os clusters com centróides aleatórios
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            Cluster cluster = new Cluster(i);
            Point centroid = points.get(random.nextInt(points.size()));
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }
    }

    public void run() {
        boolean converged = false;
        int iteration = 0;

        while (!converged) {
            clearClusters();

            List<Point> lastCentroids = getCentroids();

            assignPointsToClusters();

            calculateCentroids();

            iteration++;

            List<Point> currentCentroids = getCentroids();

            double distance = 0;
            for (int i = 0; i < lastCentroids.size(); i++) {
                distance += Point.euclideanDistance(lastCentroids.get(i), currentCentroids.get(i));
            }

            if (distance == 0) {
                converged = true;
            }
        }

        System.out.println("Convergiu após " + iteration + " iterações.");

        for (Cluster cluster : clusters) {
            System.out.println("Cluster " + cluster.getId() + ": " + cluster.getPoints());
        }
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List<Point> getCentroids() {
        List<Point> centroids = new ArrayList<>(k);
        for (Cluster cluster : clusters) {
            Point centroid = cluster.getCentroid();
            centroids.add(centroid);
        }
        return centroids;
    }

    private void assignPointsToClusters() {
        for (Point point : points) {
            double minDistance = Double.MAX_VALUE;
            int clusterId = -1;

            for (Cluster cluster : clusters) {
                double distance = Point.euclideanDistance(point, cluster.getCentroid());
                if (distance < minDistance) {
                    minDistance = distance;
                    clusterId = cluster.getId();
                }
            }

            clusters.get(clusterId).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : clusters) {
            List<Point> clusterPoints = cluster.getPoints();
            double[] centroidCoordinates = new double[Point.DIMENSION];

            if (!clusterPoints.isEmpty()) {
                for (Point point : clusterPoints) {
                    for (int i = 0; i < Point.DIMENSION; i++) {
                        centroidCoordinates[i] += point.getCoordinates()[i];
                    }
                }

                for (int i = 0; i < Point.DIMENSION; i++) {
                    centroidCoordinates[i] /= clusterPoints.size();
                }

                Point centroid = new Point(centroidCoordinates);
                cluster.setCentroid(centroid);
            }
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso
        List<Point> points = new ArrayList<>();
        points.add(new Point(new double[]{1, 1}));
        points.add(new Point(new double[]{1, 2}));
        points.add(new Point(new double[]{2, 1}));
        points.add(new Point(new double[]{2, 2}));
        points.add(new Point(new double[]{5, 5}));
        points.add(new Point(new double[]{6, 6}));
        points.add(new Point(new double[]{5, 6}));
        points.add(new Point(new double[]{6, 5}));

        int k = 2; // número de clusters desejado

        KMeans kmeans = new KMeans(k, points);
        kmeans.run();
    }
}

class Point {
    public static final int DIMENSION = 2; // dimensão dos pontos

    private double[] coordinates;

    public Point(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public static double euclideanDistance(Point point1, Point point2) {
        double sum = 0.0;
        for (int i = 0; i < DIMENSION; i++) {
            sum += Math.pow(point1.coordinates[i] - point2.coordinates[i], 2);
        }
        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < DIMENSION - 1; i++) {
            sb.append(coordinates[i]).append(", ");
        }
        sb.append(coordinates[DIMENSION - 1]).append(")");
        return sb.toString();
    }
}

class Cluster {
    private int id;
    private List<Point> points;
    private Point centroid;

    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList<>();
        this.centroid = null;
    }

    public int getId() {
        return id;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public void clear() {
        points.clear();
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cluster ").append(id).append(": [");
        for (Point point : points) {
            sb.append(point).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length()); // remove a última vírgula e espaço
        sb.append("]");
        return sb.toString();
    }
}
