import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Simulator extends JFrame {


    private final String[] infoLabels = new String[]{"Elapsed Time (s):", "Frames per Second (FPS):", "Thread Count:", "Particle Count:"};
    private final String[] method1Labels = new String[]{"X-coordinate", "Y-coordinate", "Angle", "Velocity"};
    private HashMap<String, JTextField> method1Quantities;
    private JPanel canvasPanel;
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

        // Create Toolbar Panel
        toolbarPanel = new JPanel();
        toolbarPanel.setPreferredSize(new Dimension(600, 720 )); // Adjust as needed
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        toolbarPanel.setLayout(new GridLayout(2, 2, 5, 5));
        toolbarPanel.setBackground(new Color(241,255,248));

        addInformationDisplay();
        addInformationDisplay();
        addPerParticleMethodDisplay();
        addInformationDisplay();



//        JPanel measurementsPanel = new JPanel();
//        measurementsPanel.setBackground(Color.white);
//        toolbarPanel.add(measurementsPanel);
//
//        JPanel secondPanel = new JPanel();
//        secondPanel.setBackground(Color.black);
//        toolbarPanel.add(secondPanel);
//
//        JPanel third = new JPanel();
//        third.setBackground(Color.blue);
//        toolbarPanel.add(third);

        add(toolbarPanel, BorderLayout.EAST);
    }

    private void addInformationDisplay() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 0));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 10, 5, true),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);
        infoPanel.add(panel);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(4, 0));
        for (String strLabel : infoLabels){
            addInfo(container, strLabel);
        }
        infoPanel.add(container);

        toolbarPanel.add(infoPanel);
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

        quantity.setPreferredSize(new Dimension(50, 20));
        quantity.setFont(new Font("Arial", Font.BOLD, 14));
        quantity.setHorizontalAlignment(SwingConstants.CENTER);

        JButton increment = new JButton("+");
        increment.setFont(new Font("Arial", Font.BOLD, 16));
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
        decrement.setFont(new Font("Arial", Font.BOLD, 16));
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
        canvasPanel = new JPanel();
        canvasPanel.setLayout(new BorderLayout());
        canvasPanel.setBackground(new Color(13, 21, 23));
        canvasPanel.setPreferredSize(new Dimension(1280, 720));
        add(canvasPanel, BorderLayout.CENTER);

    }


}
