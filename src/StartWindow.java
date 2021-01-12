import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartWindow extends JFrame {

	private int methodOption = 0;
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	submitAction action = new submitAction();
	JLabel ISize;
	JLabel Op;
	JTextField text;
	JLabel ep;
	JLabel NofIterations;
	JTextField epsilon;
	JTextField numberOfIterations;
	JButton submit;
	JComboBox methods;
	JLabel inputSize;

	private String[] options = { "", "Gauss Elimination", "Gauss Elimination using pivoting", "scaled Gauss Elimination",
			"scaled Gauss Elimination using pivoting", "Gauss Jordan", "scaled Gauss Jordan",
			"LU Decomposition using Doolittle", "LU Decomposition using Crout ", "LU Decomposition using Cholesky ","LU Cholesky for positive definite matrix",
			"Gauss Seidel", "Jacobi Iterative" };
	
	public StartWindow() {
		this.setTitle("Numerical Methods");
		this.setSize(773, 594);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(400, 10);
		panel.setSize(800, 800);
		panel.setBackground(Color.decode("#222831"));
		panel.setLayout(null);
		ISize = new JLabel("Size of square matrix :");
		Op = new JLabel("Choose the operation :");
		ep = new JLabel("Epsilon");
		NofIterations = new JLabel("Number of iterations");
		submit = new JButton("Submit");
		text = new JTextField("");
		epsilon = new JTextField();
		numberOfIterations = new JTextField();
		methods = new JComboBox();
		methods.setModel(new DefaultComboBoxModel(options));

		ISize.setBounds(124, 89, 320, 60);
		ISize.setForeground(Color.WHITE);
		ISize.setFont(new Font("Dialog", Font.BOLD, 30));

		text.setBounds(487, 103, 64, 40);
		text.setForeground(Color.BLACK);
		text.setFont(new Font("atilic", Font.BOLD, 20));

		Op.setBounds(124, 160, 332, 60);
		Op.setForeground(Color.WHITE);
		Op.setFont(new Font("Dialog", Font.BOLD, 30));

		methods.setBounds(124, 231, 520, 40);
		methods.setBackground(Color.white);
		methods.setForeground(Color.BLACK);
		methods.setFont(new Font("Dialog", Font.BOLD, 25));

		ep.setBounds(207, 267, 90, 60);
		ep.setForeground(Color.WHITE);
		ep.setFont(new Font("atilic", Font.BOLD, 20));

		epsilon.setBounds(140, 322, 200, 40);
		epsilon.setForeground(Color.BLACK);
		epsilon.setFont(new Font("atilic", Font.BOLD, 20));

		NofIterations.setBounds(425, 267, 200, 60);
		NofIterations.setForeground(Color.WHITE);
		NofIterations.setFont(new Font("atilic", Font.BOLD, 20));

		numberOfIterations.setBounds(425, 322, 200, 40);
		numberOfIterations.setForeground(Color.BLACK);
		numberOfIterations.setFont(new Font("atilic", Font.BOLD, 20));

		submit.setBounds(269, 373, 250, 60);
		submit.setBackground(Color.white);
		submit.setForeground(Color.BLACK);
		submit.setFont(new Font("atilic", Font.BOLD, 30));

		ep.setVisible(false);
		epsilon.setVisible(false);
		NofIterations.setVisible(false);
		numberOfIterations.setVisible(false);

		this.getContentPane().add(panel);
		panel.add(ISize);
		panel.add(text);
		panel.add(Op);
		panel.add(ep);
		panel.add(epsilon);
		panel.add(NofIterations);
		panel.add(numberOfIterations);
		panel.add(submit);
		panel.add(methods);

		inputSize = new JLabel("");
		inputSize.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inputSize.setForeground(Color.WHITE);
		inputSize.setBounds(290, 444, 214, 39);
		panel.add(inputSize);

		submit.addActionListener(action);
		methods.addActionListener(action);
	}
	
	private class submitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String item = (String) methods.getSelectedItem();

			methodOption = methods.getSelectedIndex();
			if (methodOption == 11 || methodOption == 12) {
				ep.setVisible(true);
				epsilon.setVisible(true);
				NofIterations.setVisible(true);
				numberOfIterations.setVisible(true);
			} else {
				ep.setVisible(false);
				epsilon.setVisible(false);
				NofIterations.setVisible(false);
				numberOfIterations.setVisible(false);
			}
			if (e.getSource() == submit) {
				inputSize.setText(null);
				JTextField[] array = { text, epsilon, numberOfIterations };
				int flag = 0;
				for (int i = 0; i < array.length; i++) {
					for (int j = 0; j < array[i].getText().length(); j++) {
						if (i != 1 && !Character.isDigit(array[i].getText().charAt(j))) {

							inputSize.setText("Enter only positive integer numbers");
							flag = 1;
						}
						if (i == 0 && Integer.valueOf(array[i].getText()) > 8) {  // add limint to size of matrix to fit the size of screen 
							inputSize.setText("    Maximum size allowed is  8 X 8");
							flag = 1;
						}
						if (i == 1) {
							if (!Character.isDigit(array[i].getText().charAt(j))
									&& array[i].getText().charAt(j) != '.') {

								inputSize.setText("Enter only positive numbers");
								flag = 1;
							}
						}
					}
				}

				if (flag != 1 && methodOption != 0 && text.getText().length() > 0) { 
					if (methodOption == 11 || methodOption == 12) {
						if (epsilon.getText().length() > 0 && numberOfIterations.getText().length() > 0) {
							InputMatrix input = new InputMatrix(methodOption, text.getText(), epsilon.getText(),
									numberOfIterations.getText());
							frame.setVisible(false);
							frame.dispose();
							dispose();
						}
					} else {
						InputMatrix input = new InputMatrix(methodOption, text.getText(), epsilon.getText(),
								numberOfIterations.getText());
						frame.setVisible(false);
						frame.dispose();
						dispose();
					}
				}

			}

		}

	}
}
