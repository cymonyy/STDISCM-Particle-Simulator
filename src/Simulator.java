import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Simulator extends JFrame {


    private final String[] infoLabels = new String[]{"Elapsed Time (s):", "Frames per Second (FPS):", "Thread Count:", "Particle Count:"};
    private final String[] method1Labels = new String[]{"X-coordinate", "Y-coordinate", "Angle", "Velocity"};
    private final String[] wallLabels = new String[]{"X1-coordinate", "Y1-coordinate", "X2-coordinate", "Y2-coordinate"};
    private final String[] linearParticleLabels = new String[]{"N", "X1-coordinate", "Y1-coordinate", "X2-coordinate", "Y2-coordinate",  "Angle",  "Velocity"};
    private final String[] angularParticleLabels = new String[]{"N", "X-coordinate", "Y-coordinate", "Start Angle", "End Angle",  "Velocity"};
    private final String[] velocityParticleLabels = new String[]{"N", "X-coordinate", "Y-coordinate", "Start Velocity", "End Velocity",  "Angle"};


    private HashMap<String, JTextField> methodQuantities;
    private Canvas canvasPanel;
    private JPanel toolbarPanel;

    //for information of simulator
    private JLabel fps;
    private JLabel elapsedTime;
    private JLabel particles;
    private JLabel threads;

    public Simulator(){
        setTitle("Particle Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        addCanvas();

        addToolbar();

        setVisible(true);
        pack();


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

   
    // Create and return a JLabel for displaying information
    private JLabel createInfoLabel(String labelText) {
        JLabel infoLabel = new JLabel("001"); // Initial value
        infoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        infoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for sleek appearance
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return infoLabel;
    }

    private void addCombinedPanel() {
        JPanel combinedPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column
        combinedPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 10, 5, false),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    
        // Information display panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 2 columns
        for (String strLabel : infoLabels) {
            JLabel label = new JLabel(strLabel);
            label.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font size
            infoPanel.add(label);
    
            JLabel info = createInfoLabel(strLabel);
            info.setFont(new Font("Arial", Font.PLAIN, 14)); // Adjust font size
            infoPanel.add(info);
        }
        combinedPanel.add(infoPanel);
    
        // Add-a-particle panel
        JPanel addParticlePanel = createAddJPanel("ADD A PARTICLE", method1Labels);
        combinedPanel.add(addParticlePanel);
    
        // Add-a-wall panel
        JPanel addWallPanel = createAddJPanel("ADD A WALL", wallLabels);
    
        combinedPanel.add(addWallPanel);
        toolbarPanel.add(combinedPanel);
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
                        int angle = Integer.parseInt(methodQuantities.get(title+"Angle").getText());
                        int velocity = Integer.parseInt(methodQuantities.get(title+"Velocity").getText());

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
                        break;
                    }

                    case "Particle Batch Angular": {

                        int n = Integer.parseInt(methodQuantities.get(title+"N").getText());
                        int x = Integer.parseInt(methodQuantities.get(title+"X-coordinate").getText());
                        int y = Integer.parseInt(methodQuantities.get(title+"Y-coordinate").getText());
                        int startAngle = Integer.parseInt(methodQuantities.get(title+"Start Angle").getText());
                        int endAngle = Integer.parseInt(methodQuantities.get(title+"End Angle").getText());
                        int velocity = Integer.parseInt(methodQuantities.get(title+"Velocity").getText());

                        int range = (int) (endAngle - startAngle)/ (n-1);
                        for (int i = 1; i <= n; i++) {
                            int angle = (int) Math.round(startAngle + (i * range));
                            particles.add(new Particle(
                                    x,
                                    y,
                                    angle,
                                    velocity
                            ));
                            System.out.println(angle);
                        }

                        break;
                    }

                    case "Particle Batch Velocity": {

                        int n = Integer.parseInt(methodQuantities.get(title+"N").getText());
                        int x = Integer.parseInt(methodQuantities.get(title+"X-coordinate").getText());
                        int y = Integer.parseInt(methodQuantities.get(title+"Y-coordinate").getText());
                        int startVelocity = Integer.parseInt(methodQuantities.get(title+"Start Velocity").getText());
                        int endVelocity = Integer.parseInt(methodQuantities.get(title+"End Velocity").getText());
                        int angle = Integer.parseInt(methodQuantities.get(title+"Angle").getText());

                        int range = (int) (endVelocity - startVelocity)/ (n-1);
                        for (int i = 1; i <= n; i++) {
                            int velocity = (int) Math.round(startVelocity + (i * range));
                            particles.add(new Particle(
                                    x,
                                    y,
                                    angle,
                                    velocity
                            ));
                            System.out.println(velocity);
                        }

                        break;
                    }

                    case "ADD A WALL": {
                        int x1 = Integer.parseInt(methodQuantities.get(title+"X1-coordinate").getText());
                        int y1 = Integer.parseInt(methodQuantities.get(title+"Y1-coordinate").getText());
                        int x2 = Integer.parseInt(methodQuantities.get(title+"X2-coordinate").getText());
                        int y2 = Integer.parseInt(methodQuantities.get(title+"Y2-coordinate").getText());




                        break;
                    }
                    default: {
                        int x = Integer.parseInt(methodQuantities.get(title+"X-coordinate").getText());
                        int y = Integer.parseInt(methodQuantities.get(title+"Y-coordinate").getText());
                        int angle = Integer.parseInt(methodQuantities.get(title+"Angle").getText());
                        int velocity = Integer.parseInt(methodQuantities.get(title+"Velocity").getText());
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
        methodQuantities.put(title+label, quantity);

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


    private void addCanvas(){
        // Canvas Panel
        canvasPanel = new Canvas();
        canvasPanel.setLayout(null);
        canvasPanel.setBackground(new Color(13, 21, 23));
        canvasPanel.setPreferredSize(new Dimension(1280, 720));
        add(canvasPanel, BorderLayout.CENTER);

//        addOneParticle();
    }
//
//    private void addOneParticle(){
//        List<Particle> particles = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            particles.add(new Particle(
//                    20+(7*i),
//                    20,
//                    30,
//                    10
//            ));
//        }
//
//        canvasPanel.addParticles(particles);
//    }


//    @Override
//    public void windowOpened(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowClosing(WindowEvent e) {
//        for (Thread thread : canvasPanel.getThreads()){
//            thread.interrupt();
//            try {
//                thread.join();
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        }
//        System.out.println("closing");
//    }
//
//    @Override
//    public void windowClosed(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowIconified(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowDeiconified(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowActivated(WindowEvent e) {
//
//    }
//
//    @Override
//    public void windowDeactivated(WindowEvent e) {
//
//    }
}
