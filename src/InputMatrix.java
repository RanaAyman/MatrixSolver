import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.regex.*;

public class InputMatrix extends JFrame{
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	private JLabel labelEnter;
	private JTextField text;
	private JLabel label;
	private JButton submit;
	private JScrollPane scroll;
	private JButton btnBack_1;
	private JTextField textField;
	private JLabel checkFile;
	private JLabel checkInputMatrix;
	submitAction action = new submitAction();
	private int size = 0;
    private int counter = 1;
	private int RowCounter = 0;
	private int ColumnCounter = 0;
	private int flag = 0;
	private int methodOption = 0;
	private int NIterations = 0;
	private double epsilon;
	private String epsilonS;
	private String NIterationsS;
	private double[][] augmented;
	private JTextField[][] numbers;
	
	public InputMatrix (int methodOption, String n, String epsilon, String numberOfIterations) {
		this.epsilonS = epsilon;
		this.methodOption = methodOption;
		this.size = Integer.parseInt(n);
		this.NIterationsS = numberOfIterations;
		if (this.methodOption == 11 || this.methodOption == 12) {
			this.NIterations = Integer.parseInt(numberOfIterations);
			this.epsilon = Double.parseDouble(epsilon);
		}
		numbers = new JTextField[size][size + 1];
		augmented = new double[size][size + 1];
		this.setTitle("Numerical Methods");
		this.setSize(900, 900);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(400, 10);
		panel.setSize(900, 900);
		panel.setBackground(Color.decode("#222831"));
		panel.setLayout(null);

		labelEnter = new JLabel("Enter the equations");

		submit = new JButton("Submit");
		submit.setBounds(670, 62, 133, 47);
		submit.setBackground(Color.white);
		submit.setFont(new Font("atilic", Font.BOLD, 25));

		JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		labelEnter.setBounds(250, 50, 373, 60);
		labelEnter.setForeground(Color.white);
		labelEnter.setFont(new Font("atilic", Font.BOLD, 40));
		this.add(panel);
		panel.add(labelEnter);
		int row = 0, col = 0;
		for (int i = 130; i < (panel.getHeight() - 80); i += 80) {
			if (RowCounter < size) {
				RowCounter++;
				ColumnCounter = 0;
				counter = 1;
				col = 0;
				for (int j = 100; j < (panel.getWidth() - 80); j += 80) {
					if (ColumnCounter <= size) {
						if (counter != size + 1) {
							label = new JLabel("x" + counter);
							label.setBounds(j - 50, i + 60, 40, 40);//
							label.setForeground(Color.white);
							label.setFont(new Font("atilic", Font.BOLD, 20));
							counter++;
							panel.add(label);
						} else {
							label = new JLabel("=");
							label.setBounds(j - 50, i + 60, 40, 40);//
							label.setForeground(Color.white);
							label.setFont(new Font("atilic", Font.BOLD, 20));
							counter++;
							panel.add(label);
						}
						ColumnCounter++;
						text = new JTextField("");
						text.setBounds(j - 20, i + 60, 40, 40);//
						text.setForeground(Color.black);
						text.setFont(new Font("atilic", Font.BOLD, 20));
						numbers[row][col] =text;
						col++;
						panel.add(text);

					} else {
						break;
					}
				}
				row++;
			} else {
				break;
			}
		}
		panel.add(submit);

		panel.add(scroll);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnBack_1 = new JButton("Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				dispose();
				StartWindow main = new StartWindow();
				main.setVisible(true);
			}
		});
		btnBack_1.setFont(new Font("Dialog", Font.BOLD, 25));
		btnBack_1.setBackground(Color.WHITE);
		btnBack_1.setForeground(Color.BLACK);
		btnBack_1.setBounds(60, 62, 133, 47);
		panel.add(btnBack_1);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(150, 135, 430, 31);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("File Path\r\n : ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(50, 135, 96, 31);
		panel.add(lblNewLabel);

		checkFile = new JLabel("");
		checkFile.setForeground(Color.WHITE);
		checkFile.setBounds(590, 140, 153, 31);
		panel.add(checkFile);
		
		checkInputMatrix = new JLabel("");
		checkInputMatrix.setForeground(Color.WHITE);
		checkInputMatrix.setBounds(670, 20, 133, 47);
		panel.add(checkInputMatrix);

		submit.addActionListener(action);

	}

	private class submitAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submit) {
				
				/// check here for valid input
				if (!textField.getText().equals("")) {
					File file = new File(textField.getText());
					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(file));
					} catch (FileNotFoundException fileNotFoundException) {
						checkFile.setText("put valid url of file");
						textField.setText(null);
						fileNotFoundException.printStackTrace();
						return;
					}
					String st = "";
					int n = 0;
					int size = 0;
					while (true) {
						try {
							if (!((st = br.readLine()) != null))
								break;
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
						size++;
					}
					augmented = new double[size][size + 1];
					try {
						br = new BufferedReader(new FileReader(file));
					} catch (FileNotFoundException fileNotFoundException) {
						checkFile.setText("Enter valid url of file");
						textField.setText(null);
						fileNotFoundException.printStackTrace();
						return;
					}
					while (true) {
						try {
							if (!((st = br.readLine()) != null))
								break;
						} catch (IOException ioException) {
							ioException.printStackTrace();
						}
						String regex = "-?[1-9]\\d*|0"; 
						Pattern p = Pattern.compile(regex); 
						String[] splited = st.split("\\s+");
						for (int i = 0; i < splited.length; i++) {
							Matcher m = p.matcher(splited[i]);
							if (!m.matches()) {
								checkInputMatrix.setText("Enter Integers Only");
								System.out.println("Invalid input in matrix... You must enter only integers ");
								return ;
//								throw new RuntimeException("Invalid input in matrix... You must enter only integers ");
							}
							augmented[n][i] = Double.parseDouble(splited[i]);
						}
						n++;
					}
//					for(int i=0;i<augmented.length;i++) {
//						for(int j=0;j<augmented[0].length;j++) {
//							System.out.println(augmented[i][j]+"	");
//						}
//					}
					Output output = new Output(methodOption, augmented, epsilonS, NIterationsS);
					frame.setVisible(false);
					frame.dispose();
					dispose();
				} else {
					String regex = "-?[1-9]\\d*|0"; 
					Pattern p = Pattern.compile(regex); 
					for (int i = 0; i < numbers.length; i++) {
						for (int j = 0; j < numbers[0].length; j++) {
							Matcher m = p.matcher(numbers[i][j].getText().toString());
							if (!m.matches()) {
								checkInputMatrix.setText("Enter Integers Only");
								System.out.println("Invalid input in matrix... You must enter only integers ");
								return ;
//								throw new RuntimeException("Invalid input in matrix... You must enter only integers ");
							}
							augmented[i][j] = Double.parseDouble(numbers[i][j].getText());
						}
					}
					Output output = new Output(methodOption, augmented, epsilonS, NIterationsS); // sending the
																									// result
					frame.setVisible(false);
					frame.dispose();
					dispose();
				}

			}
		}
	}
}
