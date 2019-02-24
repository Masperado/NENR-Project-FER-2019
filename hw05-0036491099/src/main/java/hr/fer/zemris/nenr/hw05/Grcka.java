package hr.fer.zemris.nenr.hw05;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Grcka extends JFrame {

    private static int M = 5;
    private int gestNumber = 20;
    private List<List<Point>> uzorci = new ArrayList<>();
    private List<Point> current = new ArrayList<>();
    private static boolean train = false;

    private int counter = 0;
    private int letters = 0;

    private static Mreza grcka;


    public Grcka() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLocation(400, 400);
        setSize(600, 600);
        setTitle("Učenje");

        initGui();
    }


    private void initGui() {
        getContentPane().setLayout(new BorderLayout());

        JPanel panel = new JPanel();


        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                double meanX = current.stream().mapToDouble(Point::getX).average().orElse(Double.NaN);
                double meanY = current.stream().mapToDouble(Point::getY).average().orElse(Double.NaN);

                current = current.stream().map(p -> new Point(p.x - meanX, p.y)).collect(Collectors.toList());
                current = current.stream().map(p -> new Point(p.x, p.y - meanY)).collect(Collectors.toList());

                double max = current.stream().mapToDouble(Point::max).max().orElse(Double.NaN);

                current = current.stream().map(p -> new Point(p.x / max, p.y)).collect(Collectors.toList());
                current = current.stream().map(p -> new Point(p.x, p.y / max)).collect(Collectors.toList());


                double distance = 0;

                for (int i = 0; i < current.size() - 1; i++) {
                    distance += current.get(i).distanceTo(current.get(i + 1));
                }

                List<Point> choosen = new ArrayList<>();


                for (int k = 0; k < M; k++) {

                    double distanceToDot = k * distance / (M - 1);

                    int i = 0;

                    Point temp = current.get(i);

                    while (distanceToDot > 0) {
                        distanceToDot -= temp.distanceTo(current.get(i + 1));
                        temp = current.get(i + 1);
                        i++;

                        if (i == current.size() - 1) {
                            break;
                        }
                    }

                    choosen.add(temp);
                }

                if (train) {

                    uzorci.add(choosen);

                    counter++;
                    System.out.println(counter);

                    if (counter == gestNumber) {
                        letters++;
                        if (letters == 5) {
                            StringBuilder sb = new StringBuilder();

                            int letterCount = 0;
                            int gestCount = 0;

                            for (var uzorak : uzorci) {
                                for (var point : uzorak) {
                                    sb.append(point.x).append(" ").append(point.y).append(" ");
                                }

                                for (int i = 0; i < 5; i++) {
                                    if (i == letterCount) {
                                        sb.append("1.0 ");
                                    } else {
                                        sb.append("0.0 ");
                                    }
                                }

                                sb.setLength(sb.length() - 1);

                                gestCount++;

                                if (gestCount == gestNumber) {
                                    gestCount = 0;
                                    letterCount++;
                                }

                                sb.append("\n");

                            }

                            try {
                                Files.write(Paths.get("uzorak.txt"), sb.toString().getBytes(StandardCharsets.UTF_8));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            System.exit(0);
                        } else {
                            System.out.println("AJMO DALJE");
                            counter = 0;
                        }
                    }

                } else {
                    List<Double> input = new ArrayList<>();

                    for (var point : choosen) {
                        input.add(point.getX());
                        input.add(point.getY());
                    }

                    grcka.evaluate(input);

                    System.out.println(grcka.getLetter());


                }

                panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());


            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.getGraphics().drawLine(e.getX(), e.getY(), e.getX(), e.getY());
                current.add(new Point(e.getX(), e.getY()));
            }

        });


        getContentPane().add(panel);
    }


    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> new Grcka().setVisible(true));


        // 5 točaka, 10000 iteracija. Kod običnog backpropagation stopa učenja 0.2, kod stohastičko stopa učenja 4, kod batch stopa učenja 0.7. Radi ko podmazano.


        if (!train) {
            grcka = new Mreza("uzorak.txt", 10000, 3, 1, M * 2, 6, 6, 5);
            grcka.learn();
        }

        System.out.println("Ajmo");

    }

    private class Point {

        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }


        public double max() {
            if (Math.abs(x) > Math.abs(y)) {
                return Math.abs(x);
            } else {
                return Math.abs(y);
            }
        }

        public double distanceTo(Point other) {
            return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
        }
    }
}