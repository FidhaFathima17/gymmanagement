import com.gym.view.PublicHomeFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            PublicHomeFrame home =
                    new PublicHomeFrame();

            home.setVisible(true);
        });
    }
}