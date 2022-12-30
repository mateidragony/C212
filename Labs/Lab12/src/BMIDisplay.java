import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BMIDisplay {
    public static void main(String[] args) {
        JFrame frame = getFrame();
        frame.setVisible(true);
    }

    private static JFrame getFrame() {
        JFrame frame = new JFrame("BMI Calculator");
        frame.setPreferredSize(new Dimension(350,300));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();

        // define certain elements that to be referenced elsewhere
        JTextField ageInput = new JTextField(10); // text input, 10 columns
        ButtonGroup sexInputGroup = new ButtonGroup(); // this is a ButtonGroup, a logical group for buttons, which lets us ensure only one radiobutton is selected
        JTextField heightFeetInput = new JTextField(10); // text input, 10 columns
        JTextField heightInchesInput = new JTextField(10);; // text input, 10 columns
        JTextField weightInput = new JTextField(20);; // text input, 20 columns
        JLabel bmiOutput = new JLabel(); // simply text on screen, defaults to no text
        JLabel healthOutput = new JLabel(); // text on screen, no default text


        JPanel ageRowPanel = getAgeRowPanel(ageInput); // this one is done
        JPanel sexRowPanel = getSexRowPanel(sexInputGroup);
        JPanel heightRowPanel = getHeightRowPanel(heightFeetInput, heightInchesInput);
        JPanel weightRowPanel = getWeightRowPanel(weightInput);
        JPanel calculateRowPanel = getCalculateRowPanel(ageInput, sexInputGroup, heightFeetInput,
                heightInchesInput, weightInput, bmiOutput, healthOutput);
        JPanel resultsPanel = getResultsRowPanel(bmiOutput, healthOutput);

        // sets layout to align elements vertically in rows
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(ageRowPanel);
        mainPanel.add(sexRowPanel);
        mainPanel.add(heightRowPanel);
        mainPanel.add(weightRowPanel);
        mainPanel.add(calculateRowPanel);
        mainPanel.add(resultsPanel);

        frame.add(mainPanel);
        frame.pack();

        return frame;
    }

    private static JPanel getBasicRowPanel() {
        // Helper method to reduce code duplication, simply returns a JPanel formatted with X-Axis BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); // you can experiment with this layout setting if you want
        return panel;
    }

    private static JPanel getAgeRowPanel(JTextField ageInput) {
        JPanel ageRowPanel = getBasicRowPanel(); // use this each time instead of new JPanel()

        ageRowPanel.add(new JLabel("Age "));
        ageRowPanel.add(ageInput);
        ageRowPanel.add(new JLabel(" ages: 2-120"));

        return ageRowPanel;
    }

    private static JPanel getSexRowPanel(ButtonGroup sexInputGroup) {
        JPanel sexRowPanel = getBasicRowPanel();
        sexRowPanel.add(new JLabel("Sex "));

        JRadioButton maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setActionCommand("Male"); // this lets us identify the button later on
        maleRadioButton.setSelected(true);

        JRadioButton femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setActionCommand("Female");


        sexInputGroup.add(maleRadioButton);
        sexInputGroup.add(femaleRadioButton);

        sexRowPanel.add(maleRadioButton);
        sexRowPanel.add(femaleRadioButton);

        return sexRowPanel;
    }

    private static JPanel getHeightRowPanel(JTextField heightFeetInput, JTextField heightInchesInput) {
        JPanel heightRowPanel = new JPanel();

        heightRowPanel.add(new JLabel("Height "));
        heightRowPanel.add(heightFeetInput);
        heightRowPanel.add(new JLabel(" ft "));
        heightRowPanel.add(heightInchesInput);
        heightRowPanel.add(new JLabel(" in"));

        return heightRowPanel;
    }

    private static JPanel getWeightRowPanel(JTextField weightInput) {
        JPanel weightRowPanel = new JPanel();

        weightRowPanel.add(new JLabel("Weight "));
        weightRowPanel.add(weightInput);

        return weightRowPanel;
    }

    private static JPanel getCalculateRowPanel(JTextField ageInput, ButtonGroup sexInputGroup,
                                               JTextField heightFeetInput, JTextField heightInchesInput,
                                               JTextField weightInput, JLabel bmiOutput, JLabel healthOutput) {
        JPanel calculateRowPanel = getBasicRowPanel();

        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear");

        calculateButton.addActionListener(e -> {
            try {
                double bmi = BMIHelperMethods.getBMI(Integer.parseInt(weightInput.getText()),
                        Integer.parseInt(heightFeetInput.getText()) * 12 + Double.parseDouble(heightInchesInput.getText()));
                bmiOutput.setText(""+bmi);
                healthOutput.setText(BMIHelperMethods.getHealth(bmi, Integer.parseInt(ageInput.getText()),
                        sexInputGroup.getSelection().getActionCommand().equals("Male")));
            } catch(Exception ex){
                ex.printStackTrace();
            }
        });

        clearButton.addActionListener(e ->{
            ageInput.setText("");
            healthOutput.setText("");
            heightFeetInput.setText("");
            heightInchesInput.setText("");
            weightInput.setText("");
            bmiOutput.setText("");
        });


        calculateRowPanel.add(calculateButton);
        calculateRowPanel.add(clearButton);

        return calculateRowPanel;
    }

    private static JPanel getResultsRowPanel(JLabel bmiOutput, JLabel healthOutput) {
        // We'll give this one to you
        JPanel resultsRowPanel = getBasicRowPanel();
        resultsRowPanel.add(new JLabel("Results: "));
        resultsRowPanel.add(bmiOutput);
        resultsRowPanel.add(healthOutput);

        return resultsRowPanel;
    }
}
