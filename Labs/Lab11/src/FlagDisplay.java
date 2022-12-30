
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class FlagDisplay {
    private static JLabel capitalLabel; // make this static so it's in scope from the button
    public static void main(String[] args) {
        // Frame and its settings

        int width = 1000;
        int height = 600;

        JFrame frame = new JFrame("France");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(width, height));

        JPanel flagPanel = new JPanel();
        flagPanel.setPreferredSize(new Dimension(width, height));
        flagPanel.setLayout(new BoxLayout(flagPanel, BoxLayout.PAGE_AXIS)); //leave this line alone, sets the panel's layout
        flagPanel.add(new Flag(width, height));
        frame.add(flagPanel, BorderLayout.CENTER); // leave this alone, it adds the panel to the frame and tells it where

        // Label and button panel
        JPanel itemsPanel = new JPanel();
        itemsPanel.setPreferredSize(new Dimension(width, 50));
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.PAGE_AXIS)); //leave alone, it sets panel's layout

        JLabel capitalLabel = new JLabel("Paris");
        capitalLabel.setVisible(false);
        //capitalLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton capitalButton = new JButton("Toggle Capital");
        capitalButton.addActionListener(e -> capitalLabel.setVisible(!capitalLabel.isVisible()));
        //capitalButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        itemsPanel.add(capitalButton);
        itemsPanel.add(capitalLabel);

        frame.add(itemsPanel, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);
    }

    static class Flag extends JComponent{

        private final int width, height;

        public Flag(int width, int height){
            setPreferredSize(new Dimension(width, height));

            this.width = width;
            this.height = height;
        }

        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.blue);
            g.fillRect(0, 0, width/3, height);
            g.setColor(Color.white);
            g.fillRect(width/3, 0 ,width/3, height);
            g.setColor(Color.red);
            g.fillRect(2*width/3, 0, width/3, height);
        }
    }
}
