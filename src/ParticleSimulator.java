import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ParticleSimulator extends JFrame {

    private final String[] infoLabels = new String[]{"Elapsed Time (s):", "Frames per Second (FPS):", "Thread Count:", "Particle Count:"};
    private final String[] method1Labels = new String[]{"X-coordinate", "Y-coordinate", "Angle", "Velocity"};
    private final String[] wallLabels = new String[]{"X1-coordinate", "Y1-coordinate", "X2-coordinate", "Y2-coordinate"};
    private final String[] linearParticleLabels = new String[]{"N", "X1-coordinate", "Y1-coordinate", "X2-coordinate", "Y2-coordinate",  "Angle",  "Velocity"};
    private final String[] angularParticleLabels = new String[]{"N", "X-coordinate", "Y-coordinate", "Start Angle", "End Angle",  "Velocity"};
    private final String[] velocityParticleLabels = new String[]{"N", "X-coordinate", "Y-coordinate", "Start Velocity", "End Velocity",  "Angle"};


    private HashMap<String, JTextField> methodQuantities;


    private JPanel toolbarPanel;
    private Canvas canvasPanel;


    public ParticleSimulator(){
        setTitle("Particle Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        addCanvas();
        addToolbar();

        pack();
        setVisible(true);

        // Start the FPS counter thread
        ///startFPSTracker();

    }

    private void addCanvas() {
        canvasPanel = new Canvas();
        canvasPanel.setBackground(Color.white);
        canvasPanel.setPreferredSize(new Dimension(1280, 720));
        add(canvasPanel, BorderLayout.CENTER);
    }

    private void addToolbar() {

        methodQuantities = new HashMap<>();

        // Create Toolbar Panel
        toolbarPanel = new JPanel();
        toolbarPanel.setPreferredSize(new Dimension(600, 720 )); // Adjust as needed
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        toolbarPanel.setLayout(new GridLayout(2, 2, 5, 5));
        toolbarPanel.setBackground(new Color(241,255,248));

        addCombinedPanel();
        JPanel addBatch1 = createAddJPanel("Particle Batch Linear", linearParticleLabels);
        toolbarPanel.add(addBatch1);

        JPanel addBatch2 = createAddJPanel("Particle Batch Angular", angularParticleLabels);
        toolbarPanel.add(addBatch2);

        JPanel addBatch3 = createAddJPanel("Particle Batch Velocity", velocityParticleLabels);
        toolbarPanel.add(addBatch3);

        add(toolbarPanel, BorderLayout.EAST);
    }

    //        // Information display panel
//        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 2 columns
//        for (String strLabel : infoLabels) {
//            JLabel label = new JLabel(strLabel);
//            label.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font size
//            infoPanel.add(label);
//
//            JLabel info = createInfoLabel(strLabel);
//            info.setFont(new Font("Arial", Font.PLAIN, 14)); // Adjust font size
//            infoPanel.add(info);
//        }
//        combinedPanel.add(infoPanel);

    private void addCombinedPanel() {
        JPanel combinedPanel = new JPanel(new GridLayout(2, 1, 10, 0)); // 3 rows, 1 column
        combinedPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 10, 5, false),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Add-a-particle panel
        JPanel addParticlePanel = createAddJPanel("ADD A PARTICLE", method1Labels);
        combinedPanel.add(addParticlePanel);

        // Add-a-wall panel
        JPanel addWallPanel = createAddJPanel("ADD A WALL", wallLabels);

        combinedPanel.add(addWallPanel);
        toolbarPanel.add(combinedPanel);
    }





    private JLabel createInfoLabel(String labelText) {
        JLabel infoLabel = new JLabel("001"); // Initial value
        infoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        infoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for sleek appearance
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return infoLabel;
    }

    private JPanel createAddJPanel(String title, String[] labels) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 10, 5, false),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JLabel header = new JLabel(title);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(header, BorderLayout.NORTH);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(labels.length, 1, 5, 5)); // Adjust gaps

        for (String label : labels) {
            addQuantityPanel(title, label, container);
            System.out.println(label);
        }
        panel.add(new JScrollPane(container), BorderLayout.CENTER); // Use JScrollPane to handle overflow

        JButton addButton = new JButton("Add now");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Particle> particles = new ArrayList<>();

                switch (title){
                    case "Particle Batch Linear": {

                        int n = Integer.parseInt(methodQuantities.get(title+"N").getText());
                        int x1 = Integer.parseInt(methodQuantities.get(title+"X1-coordinate").getText());
                        int y1 = Integer.parseInt(methodQuantities.get(title+"Y1-coordinate").getText());
                        int x2 = Integer.parseInt(methodQuantities.get(title+"X2-coordinate").getText());
                        int y2 = Integer.parseInt(methodQuantities.get(title+"Y2-coordinate").getText());
                        double angle = Double.parseDouble(methodQuantities.get(title+"Angle").getText());
                        double velocity = Double.parseDouble(methodQuantities.get(title+"Velocity").getText());

                        // Calculate the total distance between the two points
                        double totalDistance = Math.hypot(x2-x1, y2-y1);

                        // Calculate the increment for x and y coordinates
                        double deltaX = (x2-x1) / (double) (n - 1);
                        double deltaY = (y2-y1) / (double) (n - 1);

                        // Generate points along the line
                        for (int i = 0; i < n; i++) {
                            int x = (int) Math.round(x1 + deltaX * i);
                            int y = (int) Math.round(y1 + deltaY * i);
                            particles.add(new Particle(
                                    x,
                                    y,
                                    angle,
                                    velocity
                            ));
                            System.out.println("("+x+","+y+")");
                        }


                        canvasPanel.addParticles(particles);


                        break;
                    }

                    case "Particle Batch Angular": {

                        int n = Integer.parseInt(methodQuantities.get(title+"N").getText());
                        int x = Integer.parseInt(methodQuantities.get(title+"X-coordinate").getText());
                        int y = Integer.parseInt(methodQuantities.get(title+"Y-coordinate").getText());
                        double startAngle = Double.parseDouble(methodQuantities.get(title+"Start Angle").getText());
                        double endAngle = Double.parseDouble(methodQuantities.get(title+"End Angle").getText());
                        double velocity = Double.parseDouble(methodQuantities.get(title+"Velocity").getText());

                        double range = (endAngle - startAngle)/ (n-1);
                        for (int i = 0; i < n; i++) {
                            double angle = startAngle + i * range;
                            particles.add(new Particle(
                                    x,
                                    y,
                                    angle,
                                    velocity
                            ));
                            System.out.println(angle);
                        }

                        canvasPanel.addParticles(particles);

                        break;
                    }

                    case "Particle Batch Velocity": {

                        int n = Integer.parseInt(methodQuantities.get(title+"N").getText());
                        int x = Integer.parseInt(methodQuantities.get(title+"X-coordinate").getText());
                        int y = Integer.parseInt(methodQuantities.get(title+"Y-coordinate").getText());
                        double startVelocity = Double.parseDouble(methodQuantities.get(title+"Start Velocity").getText());
                        double endVelocity = Double.parseDouble(methodQuantities.get(title+"End Velocity").getText());
                        double angle = Double.parseDouble(methodQuantities.get(title+"Angle").getText());

                        double range = (endVelocity - startVelocity)/ (n-1);
                        for (int i = 0; i < n; i++) {
                            double velocity =  startVelocity + i * range;
                            particles.add(new Particle(
                                    x,
                                    y,
                                    angle,
                                    velocity
                            ));
                            System.out.println(velocity);
                        }

                        canvasPanel.addParticles(particles);

                        break;
                    }

                    case "ADD A WALL": {
                        int x1 = Integer.parseInt(methodQuantities.get(title+"X1-coordinate").getText());
                        int y1 = Integer.parseInt(methodQuantities.get(title+"Y1-coordinate").getText());
                        int x2 = Integer.parseInt(methodQuantities.get(title+"X2-coordinate").getText());
                        int y2 = Integer.parseInt(methodQuantities.get(title+"Y2-coordinate").getText());

                        canvasPanel.addWall(new Wall(
                                x1,
                                y1,
                                x2,
                                y2
                        ));

                        break;
                    }
                    default: {
                        int x = Integer.parseInt(methodQuantities.get(title+"X-coordinate").getText());
                        int y = Integer.parseInt(methodQuantities.get(title+"Y-coordinate").getText());
                        double angle = Double.parseDouble(methodQuantities.get(title+"Angle").getText());
                        double velocity = Double.parseDouble(methodQuantities.get(title+"Velocity").getText());

                        particles.add(new Particle(
                                x,
                                y,
                                angle,
                                velocity
                        ));

                        canvasPanel.addParticles(particles);
                        break;
                    }
                }

                for(JTextField field : methodQuantities.values()){
                    field.setText("0");
                }


                System.out.println(title);
            }
        });

        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }


    private void addQuantityPanel(String title, String label, JPanel panel) {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));

        JTextField quantity = new JTextField("0");
        methodQuantities.put(title + label, quantity);

        quantity.setPreferredSize(new Dimension(50, 25));
        quantity.setFont(new Font("Arial", Font.BOLD, 12));
        quantity.setHorizontalAlignment(SwingConstants.CENTER);

        JButton increment = new JButton("+");
        increment.setFont(new Font("Arial", Font.PLAIN, 12));
        increment.setPreferredSize(new Dimension(45, 20));
        increment.setForeground(Color.black);
        increment.setBackground(Color.white);
        increment.setFocusPainted(false);
        increment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quantity.getText().isEmpty())
                    quantity.setText("0");

                int value = Integer.parseInt(quantity.getText());
                value++;
                quantity.setText(String.valueOf(value));
                System.out.println(value);
            }
        });


        JButton decrement = new JButton("-");
        decrement.setFont(new Font("Arial", Font.PLAIN, 12));
        decrement.setPreferredSize(new Dimension(45, 20));
        decrement.setForeground(Color.black);
        decrement.setBackground(Color.white);
        decrement.setFocusPainted(false);
        decrement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quantity.getText().isEmpty())
                    quantity.setText("0");

                int value = Integer.parseInt(quantity.getText());
                if (value > 0)
                    value--;

                quantity.setText(String.valueOf(value));
                System.out.println(value);
            }
        });

        JPanel outerContainer = new JPanel();
        outerContainer.setLayout(new FlowLayout(FlowLayout.LEFT));

        container.add(decrement);
        container.add(quantity);
        container.add(increment);
        outerContainer.add(container);


        JLabel quanLabel = new JLabel(label);
        quanLabel.setFont(new Font("Arial", Font.BOLD, 12));
        quanLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        quanLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        outerContainer.add(quanLabel);

        panel.add(outerContainer);

    }








}
