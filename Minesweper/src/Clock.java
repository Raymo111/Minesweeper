import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextPane;

public class Clock {

	private JTextPane clockDisplay;
	private Timer timer = new Timer();
	private boolean cancel = true;
	private TimerTask clock = new TimerTask() {

		public void run() {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					clockDisplay.setText(String.valueOf(Minesweeper.clockSeconds++));
				}
			});
		}
	};

	public Clock(JTextPane textField) {
		clockDisplay = textField;
		timer.schedule(clock, 0, 1000);
	}

	public void cancel() {
		if (cancel) {
			clock.cancel();
			timer.cancel();
			cancel = false;
		}
	}

}