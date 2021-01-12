import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Output extends JFrame {
	JFrame f = new JFrame();
	JPanel p = new JPanel();
	private double epsilon;
	private int numberOfInterations = 0;
	String nameOfMethod;
	JLabel l;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	public static JTextArea textArea = new JTextArea();

	private class GuiOutputStream extends OutputStream {
		JTextArea textArea;

		public GuiOutputStream(JTextArea textArea) {
			this.textArea = textArea;
		}

		@Override
		public void write(int data) throws IOException {
			textArea.append(new String(new byte[] { (byte) data }));
		}
	}
	private void printTime(long startTime, long endTime) {
		long duration = (endTime - startTime);
		System.out.println(duration + " NANOSECONDS");
		long convert = TimeUnit.MICROSECONDS.convert(duration, TimeUnit.NANOSECONDS);
//		System.out.println(convert + " MICROSECONDS");
	}

	public Output(int methodOption, double[][] augmented, String epsilon, String numberOfIterations) {
		f.setTitle("Numerical Methods");
		f.setSize(900, 900);
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocation(400, 10);
		p.setSize(900, 900);
		p.setBackground(Color.decode("#222831"));
		p.setLayout(null);
		f.getContentPane().add(p);

		JLabel lblNewLabel = new JLabel("Results");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 37));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(370, 27, 137, 42);
		p.add(lblNewLabel);
		scrollPane.setBounds(44, 90, 797, 730);

		p.add(scrollPane);
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
		textArea.setLineWrap(true);

		scrollPane.setViewportView(textArea);
		scrollPane_1.setBounds(742, 90, 2, 644);

		p.add(scrollPane_1);

//		PrintStream stdout = System.out;
		GuiOutputStream rawout = new GuiOutputStream(textArea);
		System.setOut(new PrintStream(rawout, true));
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int size = augmented.length;
		if (methodOption == 11 || methodOption == 12) {
			this.numberOfInterations = Integer.parseInt(numberOfIterations);
			this.epsilon = Double.parseDouble(epsilon);
		}
		if (methodOption == 1) {
			nameOfMethod = "Gauss Elimination";
			System.out.println("Steps using " + nameOfMethod + " :");
			
//			long startTime = System.nanoTime();
		
			Operations.GaussElimination(augmented);
			
//			long endTime = System.nanoTime();
//			printTime(startTime, endTime);
			
		} else if (methodOption == 2) {
			nameOfMethod = "Gauss Elimination using pivoting";
			System.out.println("Steps using " + nameOfMethod + " :");
			
			
//			long startTime = System.nanoTime();
			
			Operations.GaussianEliminationWithPartialPivoting(augmented);
			
//			long endTime = System.nanoTime();
//			printTime(startTime, endTime);
			
		} else if (methodOption == 3) {
			nameOfMethod = "Scaled Gauss Elimination";
			System.out.println("Steps using " + nameOfMethod + " :");
			
			
//			long startTime = System.nanoTime();
			
			Operations.scaledGaussElimination(augmented);
			
//			long endTime = System.nanoTime();
//			printTime(startTime, endTime);
			
		} else if (methodOption == 4) {
			nameOfMethod = "Scaled Gauss Elimination using pivoting";
			System.out.println("Steps using " + nameOfMethod + " :");
			
			
//	        long startTime = System.nanoTime();
			
	        Operations.scaledGaussEliminationUsingPivoting(augmented);
			
//			long endTime = System.nanoTime();
//			printTime(startTime, endTime);
			
			
		} else if (methodOption == 5) {
			nameOfMethod = "Gauss Jordan";
			System.out.println("Steps using " + nameOfMethod + " :");
			
			
//            long startTime = System.nanoTime();
			
            Operations.GaussJordan(augmented);
			
//			long endTime = System.nanoTime();
//			printTime(startTime, endTime);
			
			
		} else if (methodOption == 6) {
			nameOfMethod = "scaled Gauss Jordan";
			System.out.println("Steps using " + nameOfMethod + " :");
			
//            long startTime = System.nanoTime();
			
            Operations.scaledGaussJordan(augmented);
			
//			long endTime = System.nanoTime();
//			printTime(startTime, endTime);
			
			
		} else if (methodOption == 7) {
			nameOfMethod = "LU Decomposition using Doolittle";
			System.out.println("Steps using " + nameOfMethod + " :");
//			LU.myLuDoolitte(augmented, augmented.length, 0.0001, 1); // <<<<<<<<<<<<<< tol = 0.0001
			
//            long startTime = System.nanoTime();
			
            LU.myLuDoolitte(augmented, augmented.length, 0.0001, 1); 
			
//			long endTime = System.nanoTime();
//			printTime(startTime, endTime);
			
			
		} else if (methodOption == 8) {
			nameOfMethod = "LU Decomposition using Crout ";
			System.out.println("Steps using " + nameOfMethod + " :");
			
//			 long startTime = System.nanoTime();
				
			 LU.myLUCrout(augmented, augmented.length, 1);
				
//		     long endTime = System.nanoTime();
//			 printTime(startTime, endTime);
				
		} else if (methodOption == 9) {
			nameOfMethod = "LU Decomposition using Cholesky";
			System.out.println("Steps using " + nameOfMethod + " :");
			
//			long startTime = System.nanoTime();
			
			LU.luCholesky(augmented, augmented.length, 1);
			
//		     long endTime = System.nanoTime();
//			 printTime(startTime, endTime);
			 
		} else if (methodOption == 10) {
			nameOfMethod = "LU Decomposition using Cholesky for symmetric positive definite matrix";
			System.out.println("Steps using " + nameOfMethod + " :");
			
//            long startTime = System.nanoTime();
			
            LU.myLUSymCholesky(augmented, augmented.length, 1);
				
//		     long endTime = System.nanoTime();
//			 printTime(startTime, endTime);
			 
		}else if (methodOption == 11) {
			nameOfMethod = "Gauss Seidel";
			System.out.println("Steps using " + nameOfMethod + " :");
			
//            long startTime = System.nanoTime();
			
            Operations.GaussSeidalSolver(augmented, new double[] { 0, 0, 0 }, this.numberOfInterations, this.epsilon);
				
//		     long endTime = System.nanoTime();
			 //printTime(startTime, endTime);
			 
		} else if (methodOption == 12) {
			nameOfMethod = "Jacobi Iterative";
			System.out.println("Steps using " + nameOfMethod + " :");
			
//			 long startTime = System.nanoTime();
				
			 Operations.jacobi(augmented, new double[] { 0, 0, 0 }, this.numberOfInterations, this.epsilon);
					
//			 long endTime = System.nanoTime();
//		     printTime(startTime, endTime);
			
		}
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				f.setVisible(false);
				f.dispose();
				dispose();
				InputMatrix main = new InputMatrix(methodOption, Integer.toString(size), epsilon, numberOfIterations);
				main.setVisible(true);

			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(44, 27, 124, 42);
		p.add(btnNewButton);

	}

}
