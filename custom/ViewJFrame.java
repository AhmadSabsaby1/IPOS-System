package custom;

import javax.swing.*;
import java.awt.*;

/**
 * This class extends <code>JFrame</code>, adding useful functionality
 * and pre-setting many parameters to our needs.
 * The class creates its own <code>CardLayout</code> to change from
 * one view to another using <code>changeCardView</code>.
 */
public class ViewJFrame extends JFrame {
    protected CardLayout cardLayout;

    /**
     * The constructor for the class.
     * @param title the title for the window
     */
    public ViewJFrame(String title) {
        super(title);

        //creates the CardLayout
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // frame width & height
        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;

        // size of our application frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Adds a new layout to the <code>CardLayout</code>.
     * @param view the <code>JPanel</code> view to be added
     * @param id the name for this specific view
     */
    public void addCardLayout(JPanel view, String id) {
        add(view, id);
    }

    /**
     * Shows one of the cards in the <code>CardLayout</code>,
     * identified by its <code>id</code>.
     * @param id the id of the card to be shown
     */
    public void changeCardView(String id) {
        cardLayout.show(getContentPane(), id);
    }
}
