import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Simulator extends JFrame implements WindowListener {


    private final String[] infoLabels = new String[]{"Elapsed Time (s):", "Frames per Second (FPS):", "Thread Count:", "Particle Count:"};
    private final String[] method1Labels = new String[]{"X-coordinate", "Y-coordinate", "Angle", "Velocity"};
    private final String[] wallLabels = new String[]{"X1-coordinate", "Y1-coordinate", "X2-coordinate", "Y2-coordinate"};
    private final String[] linearParticleLabels = new String[]{"N", "X1-coordinate", "Y1-coordinate", "X2-coordinate", "Y2-coordinate",  "Angle",  "Velocity"};
    private final String[] angularParticleLabels = new String[]{"N", "X-coordinate", "Y-coordinate", "Start Angle", "End Angle",  "Velocity"};
    private final String[] velocityParticleLabels = new String[]{"N", "X-coordinate", "Y-coordinate", "Start Velocity", "End Velocity",  "Angle"};


    private HashMap<String, JTextField> method1Quantities;
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
        addWindowListener(this);

        addCanvas();
        addToolbar();

        setVisible(true);
        pack();


    }

    private void addToolbar() {

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
    
    private void addInfo(JPanel container, String strLabel){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel(strLabel);
        label.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel info = null;
        switch (strLabel){
            case "Elapsed Time (s):": {
                elapsedTime = new JLabel("001");
                info = elapsedTime;
                break;
            }
            case "Frames per Second (FPS):": {
                fps = new JLabel("001");
                info = fps;
                break;
            }
            case "Thread Count:": {
                threads = new JLabel("001");
                info = threads;
                break;
            }
            case "Particle Count:": {
                particles = new JLabel("001");
                info = particles;
                break;
            }
        }

        assert info != null;
        info.setFont(new Font("Arial", Font.BOLD, 15));

        panel.add(label, BorderLayout.WEST);
        panel.add(info, BorderLayout.EAST);

        container.add(panel);
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
        JLabel header = new JLabel(title);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(header, BorderLayout.NORTH);
    
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(labels.length, 1, 5, 5)); // Adjust gaps
    
        method1Quantities = new HashMap<>();
        for (String label : labels) {
            addQuantityPanel(label, container);
            System.out.println(label);
        }
        panel.add(new JScrollPane(container), BorderLayout.CENTER); // Use JScrollPane to handle overflow
    
        JButton addButton = new JButton("Add now");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(addButton, BorderLayout.SOUTH);
    
        return panel;
    }
    
    // private void addCombinedPanel() {
    //     JPanel combinedPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column
    //     combinedPanel.setBorder(BorderFactory.createCompoundBorder(
    //             BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 10, 5, false),
    //             BorderFactory.createEmptyBorder(10, 10, 10,10)
    //     ));
    
    //     // Information display panel
    //     JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 2 columns
    //     for (String strLabel : infoLabels) {
    //         JLabel label = new JLabel(strLabel);
    //         label.setFont(new Font("Arial", Font.BOLD, 10));
    //         infoPanel.add(label);
    
    //         JLabel info = createInfoLabel(strLabel);
    //         infoPanel.add(info);
    //     }
    //     combinedPanel.add(infoPanel);
    
    //     // Add-a-particle panel
    //     JPanel addParticlePanel = createAddJPanel("ADD A PARTICLE", method1Labels);
    //     combinedPanel.add(addParticlePanel);
    
    //     // Add-a-wall panel
    //     JPanel addWallPanel = createAddJPanel("ADD A WALL", wallLabels);

    //     combinedPanel.add(addWallPanel);
    //     toolbarPanel.add(combinedPanel);
    // }
    
    // private JPanel createAddJPanel(String title, String[] labels) {
    //     JPanel panel = new JPanel(new BorderLayout());
    //     JLabel header = new JLabel(title);
    //     header.setHorizontalAlignment(SwingConstants.CENTER);
    //     header.setFont(new Font("Arial", Font.BOLD, 16));
    //     panel.add(header, BorderLayout.NORTH);
    
    //     JPanel container = new JPanel();
    //     container.setLayout(new FlowLayout(FlowLayout.LEFT)); 
    //     container.setPreferredSize(new Dimension(200, labels.length * 30)); // Adjust the width and height as needed


    //     method1Quantities = new HashMap<>();
    //     for (String label : labels) {
    //         addQuantityPanel(label, container);
    //         System.out.println(label);
    //     }
    //     panel.add(container, BorderLayout.CENTER);
    
    //     JButton addButton = new JButton("Add now");
    //     panel.add(addButton, BorderLayout.SOUTH);
    
    //     return panel;
    // }
    
    
    
    private void addPerParticleMethodDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 10, 5, true),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));
        setBackground(Color.cyan);

        JLabel header = new JLabel("ADD A PARTICLE");
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(header, BorderLayout.NORTH);


        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        method1Quantities = new HashMap<>();
        for (String label : method1Labels){
            addQuantityPanel(label, container);
        }
        panel.add(container, BorderLayout.CENTER);

        JButton addParticleButton = new JButton("Add Now");
        panel.add(addParticleButton, BorderLayout.SOUTH);


        toolbarPanel.add(panel);
    }

    private void addQuantityPanel(String label, JPanel panel) {
        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));

        JTextField quantity = new JTextField("0");
        method1Quantities.put(label, quantity);

        quantity.setPreferredSize(new Dimension(50, 25));
        quantity.setFont(new Font("Arial", Font.BOLD, 12));
        quantity.setHorizontalAlignment(SwingConstants.CENTER);

        JButton increment = new JButton("+");
        increment.setFont(new Font("Arial", Font.PLAIN, 12));
        increment.setPreferredSize(new Dimension(45, 20));
        increment.setForeground(Color.white);
        increment.setBackground(new Color(13, 21, 23));
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
        decrement.setForeground(Color.white);
        decrement.setBackground(new Color(13, 21, 23));
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
        add(canvasPanel, BorderLayout.CENTER);

        addOneParticle();
    }

    private void addOneParticle(){
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            particles.add(new Particle(
                    20+(7*i),
                    20,
                    30,
                    10
            ));
        }

        canvasPanel.addParticles(particles);
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Thread thread : canvasPanel.getThreads()){
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("closing");
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
