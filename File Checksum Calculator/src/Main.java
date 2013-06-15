import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class Main
{
	private static JFrame setUpJFrame()
	{
		JFrame frame = new JFrame("File Checksum Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI gui = new GUI();
		frame.setContentPane(gui);
		frame.pack();
		return frame;
	}
	private static void createAndStartGUI()
	{
		JFrame frame = setUpJFrame();
		frame.setVisible(true);
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {public void run() {createAndStartGUI(); }});
	}
}
