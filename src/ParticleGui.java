import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParticleGui extends JFrame {
    private JPanel canvasPanel;
    private JPanel toolbarPanel;
    private JTextField startTextField;
    private JTextField endTextField;
    private JTextField numberOfParticlesField;
    private JButton addParticlesButton;

//    private JTextField startTextField;
//    private JTextField endTextField;
//    private JTextField numberOfParticlesField;

    public ParticleGui() {
        setTitle("Particle Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Canvas Panel
        canvasPanel = new JPanel();
        canvasPanel.setBackground(Color.BLACK);
        canvasPanel.setPreferredSize(new Dimension(1280, 720));
        add(canvasPanel, BorderLayout.CENTER);

        // Create Toolbar Panel
        toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new BorderLayout());
        toolbarPanel.setPreferredSize(new Dimension((1920 - canvasPanel.getWidth()), 720 )); // Adjust as needed
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        add(toolbarPanel, BorderLayout.EAST);

        addParticlesSection();
        addWallSection();

        // Pack and display the frame
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
        setPreferredSize(new Dimension(1920, 1920));
    }

    private void addParticlesSection(){
        // Add particle parameters section
        JPanel particleParametersPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout
        particleParametersPanel.setBorder(BorderFactory.createTitledBorder("Particle Parameters")); // Add border
        GridBagConstraints gbc = new GridBagConstraints();

        // Number of Particles
        JLabel numberOfParticlesLabel = new JLabel("Number of Particles:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        particleParametersPanel.add(numberOfParticlesLabel, gbc);

        // Start Point
        JLabel startLabel = new JLabel("Start Point:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        particleParametersPanel.add(startLabel, gbc);

        startTextField = new JTextField();
        startTextField.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 0;
        particleParametersPanel.add(startTextField, gbc);

        // End Point
        JLabel endLabel = new JLabel("End Point:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        particleParametersPanel.add(endLabel, gbc);

        endTextField = new JTextField();
        endTextField.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 1;
        particleParametersPanel.add(endTextField, gbc);

        numberOfParticlesField = new JTextField();
        numberOfParticlesField.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 2;
        particleParametersPanel.add(numberOfParticlesField, gbc);

        // Add "Add Particles" button
        addParticlesButton = new JButton("Add Particles");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        particleParametersPanel.add(addParticlesButton, gbc);

        // Add particleParametersPanel to toolbarPanel
        toolbarPanel.add(particleParametersPanel);
    }

    private void addWallSection(){
        // Add particle parameters section
        JPanel wallParametersPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout
        wallParametersPanel.setBorder(BorderFactory.createTitledBorder("Wall Parameters")); // Add border
        GridBagConstraints gbc = new GridBagConstraints();



        // Add "Add Wall" button
        addParticlesButton = new JButton("Add Wall");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        wallParametersPanel.add(addParticlesButton, gbc);

        // Add particleParametersPanel to toolbarPanel
        toolbarPanel.add(wallParametersPanel);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParticleGui::new);
    }
}
