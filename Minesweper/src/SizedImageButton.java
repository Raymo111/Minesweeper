import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class SizedImageButton extends JButton {

	private static final long serialVersionUID = 6158260528669052590L;

	private ImageIcon image;

	public SizedImageButton(String imageName) {
		image = new ImageIcon(this.getClass().getClassLoader().getResource(imageName));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		setPreferredSize(new Dimension(60, 60));
		this.setIcon(image);
	}

	protected void paintComponent(Graphics g) {
		int imageWidth = image.getIconWidth();
		int imageHeight = image.getIconHeight();

		if (imageWidth == 0 || imageHeight == 0)
			return;
		double widthScale = (double) getWidth() / (double) imageWidth;
		double heightScale = (double) getHeight() / (double) imageHeight;
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(image.getImage(), AffineTransform.getScaleInstance(widthScale, heightScale), this);
		g2d.dispose();
	}

}