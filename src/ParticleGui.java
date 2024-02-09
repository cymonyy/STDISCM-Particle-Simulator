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

        setResizable(false);

        // Canvas Panel
        canvasPanel = new JPanel();
        canvasPanel.setLayout(new BorderLayout());
        canvasPanel.setBackground(Color.YELLOW);
        canvasPanel.setPreferredSize(new Dimension(1280, 720));
        add(canvasPanel, BorderLayout.CENTER);

        // Create Toolbar Panel
        toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new BoxLayout(toolbarPanel, BoxLayout.Y_AXIS));
        toolbarPanel.setPreferredSize(new Dimension(300, 720 )); // Adjust as needed
        //toolbarPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Add padding

        add(toolbarPanel, BorderLayout.EAST);

        addParticlesSection();
        addWallSection();

        // Pack and display the frame
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
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

    private void addWallSection() {
        // Add wall parameters section
        JPanel wallParametersPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout
        wallParametersPanel.setBorder(BorderFactory.createTitledBorder("Wall Parameters")); // Add border
        GridBagConstraints gbc = new GridBagConstraints();

        // Add x1 label and text field
        JLabel x1Label = new JLabel("x1:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        wallParametersPanel.add(x1Label, gbc);

        JTextField x1Field = new JTextField();
        x1Field.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        gbc.gridx = 1;
        wallParametersPanel.add(x1Field, gbc);

        // Add y1 label and text field
        JLabel y1Label = new JLabel("y1:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        wallParametersPanel.add(y1Label, gbc);

        JTextField y1Field = new JTextField();
        y1Field.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 1;
        wallParametersPanel.add(y1Field, gbc);

        // Add x2 label and text field
        JLabel x2Label = new JLabel("x2:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        wallParametersPanel.add(x2Label, gbc);

        JTextField x2Field = new JTextField();
        x2Field.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 2;
        wallParametersPanel.add(x2Field, gbc);

        // Add y2 label and text field
        JLabel y2Label = new JLabel("y2:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        wallParametersPanel.add(y2Label, gbc);

        JTextField y2Field = new JTextField();
        y2Field.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 3;
        wallParametersPanel.add(y2Field, gbc);

        // Add "Add Wall" button
        JButton addWallButton = new JButton("Add Wall");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        wallParametersPanel.add(addWallButton, gbc);

        // Add wallParametersPanel to toolbarPanel
        toolbarPanel.add(wallParametersPanel);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParticleGui::new);
    }
}
