package custom;

import javax.swing.*;
import java.awt.*;

public class TitleLabel extends JLabel {
    public TitleLabel(String text) {
        super(text);

        setFont(new Font("Tahoma", Font.BOLD, 32));
    }
}
