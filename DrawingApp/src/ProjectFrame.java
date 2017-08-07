import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * The Frame is our runnable class. It is the container of the GUI
 * 
 * @author Christos Liontos
 * 
 */
public class ProjectFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	// Hosts any other panel
	private JPanel contentPanel;
	// Located on the hookingPanel it is the one used to draw the grid and paint
	// the squares. BorderLayout
	private DrawingPanel drawingPanel;
	// Located to the East hosts the buttons
	private JPanel buttonsPanel;
	private JPanel buttons1panel;
	private JPanel buttons2panel;
	private JPanel buttons3panel;
	// Hosts the Lego Welcome label
	private JPanel labelPanel;
	// Located to the Center, it is the panel that hosts the drawingPanel
	private JPanel hookingPanel;
	// Located to the West hosts the yAxis labels
	private JPanel yAxis;
	// Located to the South hosts the xAxxis labels
	private JPanel xAxis;
	// Button Group and buttons for transfer selection
	private ButtonGroup group;
	// Declare radio buttons
	private JRadioButton BTButton;
	private JRadioButton USBButton;
	// Declare a String to save the selected method (BT or USB)
	private String method = "";

	public static JLabel emptyLabelNum;

	public ProjectFrame() {
		// The GUI will not be resizable to avoid inconsistencies when drawing
		// on the Grid. Resizing will move the label panels away from the Grid
		// squares
		setResizable(false);
		this.setTitle("MSc Project: Liontos Christos-1314383");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// The bounds have been set to host exactly the width and height of the
		// 10X10 Grid.
		setBounds(100, 100, 642, 585);

		drawingPanel = new DrawingPanel();
		// The content panel is used as a container
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPanel);

		// The label panel host the LEGO Welcome label
		labelPanel = new JPanel();
		labelPanel.setBackground(Color.RED);
		labelPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		contentPanel.add(labelPanel, BorderLayout.NORTH);
		JLabel lblWelcomeToLego = new JLabel("LEGO 4-");
		lblWelcomeToLego.setFont(new Font("Arial Rounded MT Bold", Font.BOLD,
				12));
		lblWelcomeToLego.setForeground(Color.white);
		labelPanel.add(lblWelcomeToLego);

		// The buttons panel hosts buttons and radio buttons
		buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.setPreferredSize(new Dimension(110, 0));
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons1panel = new JPanel(new GridLayout(4, 1, 0, 0));
		buttons1panel.setPreferredSize(new Dimension(0, 120));
		buttons1panel.setBorder(BorderFactory.createRaisedBevelBorder());
		buttons2panel = new JPanel(new GridLayout(3, 2, 0, 10));
		buttons2panel.setBorder(BorderFactory.createRaisedBevelBorder());
		buttons3panel = new JPanel(new BorderLayout());
		buttons3panel.setPreferredSize(new Dimension(0, 200));

		JLabel empty3 = new JLabel(new ImageIcon(
				"C:\\Users\\Christos\\workspace\\LegoDrawingApp\\2.jpg"));
		JLabel numberOfSelectedSquaresLbl = new JLabel("Selected Squares:");
		numberOfSelectedSquaresLbl.setPreferredSize(new Dimension(30, 30));
		numberOfSelectedSquaresLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		numberOfSelectedSquaresLbl.setBackground(Color.LIGHT_GRAY);
		numberOfSelectedSquaresLbl.setOpaque(true);
		emptyLabelNum = new JLabel("");
		emptyLabelNum.setFont(new Font("Tahoma", Font.BOLD, 20));
		emptyLabelNum.setHorizontalAlignment(SwingConstants.CENTER);
		emptyLabelNum.setOpaque(true);
		emptyLabelNum.setBackground(Color.white);

		buttons3panel.add(empty3, BorderLayout.SOUTH);
		buttons3panel.add(numberOfSelectedSquaresLbl, BorderLayout.NORTH);
		buttons3panel.add(emptyLabelNum, BorderLayout.CENTER);

		buttonsPanel.add(buttons1panel, BorderLayout.NORTH);
		buttonsPanel.add(buttons2panel, BorderLayout.CENTER);
		buttonsPanel.add(buttons3panel, BorderLayout.SOUTH);
		contentPanel.add(buttonsPanel, BorderLayout.EAST);
		// Hitting this button will invoke the clear method to clear the Grid
		JButton ClearBtn = new JButton("Clear");
		ClearBtn.setBorder(BorderFactory.createRaisedBevelBorder());
		ClearBtn.addActionListener(new ActionListener() {
			// Action performed when clear button is clicked
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.clear();
			}
		});

		JLabel lblClearDrawing = new JLabel("Clear Drawing");
		lblClearDrawing.setHorizontalAlignment(SwingConstants.CENTER);
		lblClearDrawing.setBackground(Color.LIGHT_GRAY);
		lblClearDrawing.setOpaque(true);
		lblClearDrawing.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttons1panel.add(lblClearDrawing);
		buttons1panel.add(ClearBtn);

		// Hitting this button will invoke the getCoordinates method
		JButton uploadBtn = new JButton("Upload");
		uploadBtn.setBorder(BorderFactory.createRaisedBevelBorder());
		uploadBtn.addActionListener(new ActionListener() {
			// Action performed when upload button is clicked
			public void actionPerformed(ActionEvent e) {
				if (!BTButton.isSelected() && !USBButton.isSelected()) {
					JOptionPane.showMessageDialog(null,
							"Select Transfer Method");
				}
				// Set the String method variable in the DrawingPanel class
				// according to the selected method from the radio buttons
				drawingPanel.setMethod(method);
				// Send the coordinates of the selected square on the grid
				drawingPanel.sendCoordinates();
			}
		});

		JLabel lblUploadToBrick = new JLabel("Upload to Brick");
		lblUploadToBrick.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUploadToBrick.setHorizontalAlignment(SwingConstants.CENTER);
		lblUploadToBrick.setBackground(Color.LIGHT_GRAY);
		lblUploadToBrick.setOpaque(true);
		buttons1panel.add(lblUploadToBrick);
		buttons1panel.add(uploadBtn);

		// RadioButtons and label
		JLabel BTlabel = new JLabel(new ImageIcon(
				"C:\\Users\\Christos\\workspace\\LegoDrawingApp\\BT2.jpg"));
		JLabel USBlabel = new JLabel(new ImageIcon(
				"C:\\Users\\Christos\\workspace\\LegoDrawingApp\\USB.jpg"));
		BTButton = new JRadioButton("BT");

		USBButton = new JRadioButton("USB");
		group = new ButtonGroup();
		group.add(BTButton);
		group.add(USBButton);
		// Add action listener to BT radio button
		BTButton.addActionListener(new ActionListener() {
			// Action performed when BT radio button is selected.Imports BT
			// string in method variable
			public void actionPerformed(ActionEvent e) {
				method = "BT";
			}
		});
		// Add action listener to USB radio button
		USBButton.addActionListener(new ActionListener() {
			// Action performed when USB radio button is selected.Imports USB
			// string in method variable
			public void actionPerformed(ActionEvent e) {
				method = "USB";
			}
		});

		JLabel lbltransfer = new JLabel("Transfer");
		JLabel methodLbl = new JLabel("Method");
		lbltransfer.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbltransfer.setHorizontalAlignment(SwingConstants.CENTER);
		lbltransfer.setBackground(Color.LIGHT_GRAY);
		lbltransfer.setOpaque(true);
		methodLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		methodLbl.setHorizontalAlignment(SwingConstants.CENTER);
		methodLbl.setBackground(Color.LIGHT_GRAY);
		methodLbl.setOpaque(true);
		buttons2panel.add(lbltransfer);
		buttons2panel.add(methodLbl);
		buttons2panel.add(BTButton);
		buttons2panel.add(BTlabel);
		buttons2panel.add(USBButton);
		buttons2panel.add(USBlabel);

		// The HookingPanel will host an instance of the DrawingPanel on which
		// we draw. The DrawingPanel is our canvas
		hookingPanel = new JPanel();
		contentPanel.add(hookingPanel, BorderLayout.CENTER);
		hookingPanel.setLayout(new BorderLayout(0, 0));
		hookingPanel.add(drawingPanel);

		// yAxis panel
		yAxis = new JPanel();
		yAxis.setBackground(Color.white);
		yAxis.setBorder(BorderFactory.createLineBorder(Color.black));

		contentPanel.add(yAxis, BorderLayout.WEST);
		yAxis.setLayout(new GridLayout(10, 1, 0, 0));

		JLabel lblNewLabel = new JLabel("10");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("9");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("8");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("7");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("6");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("5");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("4");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel_6);

		JLabel lblNewLabel_8 = new JLabel("3");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("2");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(lblNewLabel_9);

		JLabel label = new JLabel("1");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		yAxis.add(label);

		// xAxis panel
		xAxis = new JPanel();
		xAxis.setBackground(Color.white);
		xAxis.setBorder(BorderFactory.createLineBorder(Color.black));

		contentPanel.add(xAxis, BorderLayout.SOUTH);
		xAxis.setLayout(new GridLayout(0, 12, 0, 0));

		JLabel label_1 = new JLabel("      1");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_1);

		JLabel label_2 = new JLabel("      2");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_2);

		JLabel label_3 = new JLabel("     3");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_3);

		JLabel label_4 = new JLabel("     4");
		label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_4);

		JLabel label_5 = new JLabel("     5");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_5);

		JLabel lblNewLabel_7 = new JLabel("    6");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(lblNewLabel_7);

		JLabel label_6 = new JLabel("  7");
		label_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_6);

		JLabel label_7 = new JLabel("  8");
		label_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_7);

		JLabel label_8 = new JLabel("  9");
		label_8.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_8);

		JLabel label_9 = new JLabel("10");
		label_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		xAxis.add(label_9);

	}

	/**
	 * Main method
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjectFrame frame = new ProjectFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
