import java.util.Arrays;

public class Operations {
	private static double[][] tempDD;
	public static void printAugmented(double[][] A) {
		int n = A.length;
		System.out.println();
		for (int i = 0; i < n; i++) {
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			for (int j = 0; j <= n; j++) {
				if (j == n)
					System.out.print(" | ");
				System.out.printf("%-9.2f", A[i][j]);
			}
			if (i == 0)
				System.out.print(" \\");
			else if (i == n - 1)
				System.out.print(" /");
			else
				System.out.print(" |");

			System.out.println();

		}
		System.out.println();

	}

	public static void GaussElimination(double[][] augmented) {
		double[] x = new double[augmented.length];
		double factor = 1;
		int counter = 1;
		/// the forward elimination
		System.out.println("Forward Elimination:");
		for (int k = 0; k < augmented.length - 1; k++) {
			for (int i = k + 1; i < augmented.length; i++) {
				factor = augmented[i][k] / augmented[k][k];
				augmented[i][k] = 0;
				for (int j = k + 1; j < augmented[0].length; j++) {
					augmented[i][j] = augmented[i][j] - factor * augmented[k][j];
				}
				System.out.println("step" + counter + ":");
				counter++;
				printAugmented(augmented);
			}
		}
		/// the backward substitution
		System.out.println("After backward Substitution:");
		x[augmented.length - 1] = augmented[augmented.length - 1][augmented.length]
				/ augmented[augmented.length - 1][augmented.length - 1];
		for (int i = augmented.length - 2; i >= 0; i--) {
			double sum = 0;
			for (int j = i + 1; j < augmented.length; j++) {
				sum += augmented[i][j] * x[j];
			}
			x[i] = (augmented[i][augmented.length] - sum) / augmented[i][i];
		}
		System.out.println("The Solution");
		for (int i = 0; i < x.length; i++) {
			System.out.printf("x" + (i + 1) + " = %-9.2f \n", x[i]);
		}
	}

	public static void scaledGaussElimination(double[][] augmented) {
		GaussElimination(scaling(augmented));
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	 public static void GaussianEliminationWithPartialPivoting(double[][] augmentedMatrix){
	        // x: Coef. of vector x (to store the solution)
	        // tol: Tolerance; smallest possible scaled pivot allowed.
	        // er: Pass back -1 if matrix is singular. (Reference var.)
	        int n = augmentedMatrix.length;
	        double [][] matrix = new double[n][n];
	        int a = matrix.length;
	        double [] result = new double[n];
	        int b = result.length;
	        double[] x = new double[b];
	        double tol = .00001;
	        double er = 0;
	        double s[] = new double[b];
	        for (int i = 0; i < b; i++) {
	            result[i] = augmentedMatrix[i][n];
	        }
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                matrix[i][j] = augmentedMatrix[i][j];
	            }
	        }
	        for (int i = 0; i < b ; i++) {
	            s[i] = Math.abs(matrix[i][1]);
	            for(int j = 1 ; j < b  ; j++){
	                if (Math.abs(matrix[i][j]) > s[i]) {
	                    s[i] = Math.abs(matrix[i][j]);
	                }
	            }
	        }
	        System.out.println("GaussianElimination: ");
	        ForwardElimination(matrix, result, b-1, s, tol, er); // Forward Elimination
	        if (er != -1) { // If not singular
	            BackSubstitution(matrix, result, x, b-1); // Back Substitution
	        }
	        System.out.println("The Solution");
	        for (int i = 0; i < x.length; i++) {
	            System.out.printf("x" + (i + 1) + " = %f \n", x[i]);
	        }
	    }


	    public static void Pivoting(double[][] matrix , double[] result , double[] s , int n , int k){
	        //row k is the pivot row for now
	        int pivot = k;
	        //find the largest scaled coefficient in column k
	        double LargestScaledCoefficient = Math.abs((matrix[k][k]) / s[k]);
	        double temp;
	        for(int i = k+1;i<n+1;i++){
	            temp = Math.abs((matrix[i][k]) / s[i]);
	            if(temp > LargestScaledCoefficient){
	                LargestScaledCoefficient = temp;
	                //new pivot
	                pivot = i;
	            }
	        }
	        //swap row pivot and row k
	        if(pivot != k){
	            for(int i = k ; i<n+1 ; i++){
	                temp = matrix[pivot][i];
	                matrix[pivot][i] = matrix[k][i];
	                matrix[k][i] = temp;
	            }
	            //swap b of pivot and b of k
	            temp = result[pivot];
	            result[pivot] = result[k];
	            result[k] = temp;
	            //swap s of pivot and s of k
	            temp = s[pivot];
	            s[pivot] = result[k];
	            s[k]=temp;
	        }
	    }
	    public static void ForwardElimination(double[][] matrix, double[] result, int n, double[] s, double tol, double er){
	    	int countStep=1;
	        for (int k = 0; k <= n - 1; k++) {
	            Pivoting(matrix, result, s, n, k); // Partial Pivoting
	            if (Math.abs(matrix[k][k] / s[k]) < tol) { // Check for singularity
	                er = -1;
	                return;
	            }
	            for (int i = k + 1; i <= n; i++) {
	                double factor = matrix[i][k] / matrix[k][k];
	                matrix[i][k] = 0;
	                for (int j = k + 1; j <= n; j++)
	                    matrix[i][j] = matrix[i][j] - factor * matrix[k][j];
	                result[i] = result[i] - factor * result[k];
	                System.out.println( "step" +" : "+(countStep++) );
	                augmentedMatrixMakerPrinter(matrix, result, matrix.length);
	            }
	        }

	        System.out.println();

	        if (Math.abs(matrix[n][n] / s[n]) < tol) { // Check for singularity
	            er = -1;
	        }
	    }
	    public static void BackSubstitution(double[][] matrix, double[] result , double[] x , int n){
	        x[n] = result[n] / matrix[n][n];
	        for(int i = n-1;i>=0;i--){
	            double sum = 0;
	            for(int j=i+1 ;j<n+1;j++ ){
	                sum = sum + matrix[i][j]*x[j];
	            }
	            x[i] = (result[i]-sum) / matrix[i][i];
	        }
	    }
	    public static void augmentedMatrixMakerPrinter(double[][] matrix, double[] result, int n) {
	        double[][] augmentedMatrix = new double[n][n + 1];
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++){
	                augmentedMatrix[i][j] = matrix[i][j];
	            }
	            augmentedMatrix[i][n] = result[i];
	        }
	        System.out.println();
	        for (int i = 0; i < n; i++) {
	            if (i == 0)
	                System.out.print("/ ");
	            else if (i == n - 1)
	                System.out.print("\\ ");
	            else
	                System.out.print("| ");
	            for (int j = 0; j < n; j++)
	                System.out.printf("%-9.2f", augmentedMatrix[i][j]);
	            System.out.printf("|%-9.2f", augmentedMatrix[i][n]);
	            if (i == 0)
	                System.out.print(" \\");
	            else if (i == n - 1)
	                System.out.print(" /");
	            else
	                System.out.print(" |");

	            System.out.println();
	        }
	        System.out.println();
	    }
	
	public static void scaledGaussEliminationUsingPivoting(double[][] augmented) {
		GaussianEliminationWithPartialPivoting(scaling(augmented));
	}

//	public static void main(String[] args) {
//		double[][] augmented = new double[3][4];
//		augmented[0][0] = 5;
//		augmented[0][1] = -1;
//		augmented[0][2] = 1;
//		augmented[0][3] = 10;
//		augmented[1][0] = 2;
//		augmented[1][1] = 8;
//		augmented[1][2] = -1;
//		augmented[1][3] = 11;
//		augmented[2][0] = -1;
//		augmented[2][1] = 1;
//		augmented[2][2] = 4;
//		augmented[2][3] = 3;
//		GaussianEliminationWithPartialPivoting(augmented);
//
//	}
//


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void GaussJordan(double[][] augmented) {
		double[] x = new double[augmented.length];
		double factor = 1;
		/// the forward elimination
		int counter = 1;
		System.out.println("Forward Elimination:");
		for (int k = 0; k < augmented.length - 1; k++) {
			for (int i = k + 1; i < augmented.length; i++) {
				factor = augmented[i][k] / augmented[k][k];
				augmented[i][k] = 0;
				for (int j = k + 1; j < augmented[0].length; j++) {
					augmented[i][j] = augmented[i][j] - factor * augmented[k][j];
				}
				System.out.println("step" + counter + ":");
				counter++;
				printAugmented(augmented);
			}
		}
		/// the backward elimination
		System.out.println("backward Elimination:");
		for (int k = augmented.length - 1; k > 0; k--) {
			for (int i = k - 1; i >= 0; i--) {
				factor = augmented[i][k] / augmented[k][k];
				augmented[i][k] = 0;
				for (int j = k - 1; j >= 0; j--) {
					augmented[i][j] = augmented[i][j] - factor * augmented[k][j];
				}
				augmented[i][augmented[0].length - 1] = augmented[i][augmented[0].length - 1]
						- (factor * augmented[k][augmented[0].length - 1]);
				System.out.println("step" + counter + ":");
				counter++;
				printAugmented(augmented);
			}
		}
		/// the forward substitution
		System.out.println("After Forward Substitution:");
		for (int i = 0; i < augmented.length; i++) {
			x[i] = augmented[i][augmented[0].length - 1] / augmented[i][i];
		}
		System.out.println("The Solution");
		for (int i = 0; i < x.length; i++) {
			System.out.printf("x" + (i + 1) + " = %-9.2f \n", x[i]);
		}
	}

	public static void scaledGaussJordan(double[][] augmented) {
		GaussJordan(scaling(augmented));
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	public static double[][] LUDecomposition(double[][] a, int option) {
//		if(option == 1) {
//			
//		}else if(option == 2) {
//			
//		}else {
//			
//		}
//		return a;
//		
//	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void GaussSeidalSolver(double[][] augmentedMatrix, double[] intialGuess, int iterations,
			double epsilon) {
		copy2DmatrixToTempDD( augmentedMatrix);
		if (!makeDominant()) {
		System.out.println("The system isn't diagonally dominant: \n"+ "So ,The method cannot guarantee convergence.");
	    System.out.println("The original Matrix :");
		printAugmented(augmentedMatrix);
		}else {
			System.out.println("The Diagonally Dominant Matrix :");
			augmentedMatrix = copy2DmatrixFromTempDD(augmentedMatrix);
			printAugmented(augmentedMatrix);
		}
		
		int matrixSize = augmentedMatrix.length;
		int currIterations = 1;
		double[] xNew = new double[matrixSize];
		double[] xOld = new double[matrixSize];
		xNew = intialGuess;
		int counter = 0;
		while (counter < iterations) {
			for (int i = 0; i < matrixSize; i++) {
				double sum = augmentedMatrix[i][matrixSize];
				for (int j = 0; j < matrixSize; j++) {
					if (j != i) {
						sum = sum - augmentedMatrix[i][j] * xNew[j];
					}
				}
				xNew[i] = 1 / augmentedMatrix[i][i] * sum;
			}
			System.out.println(currIterations + " - Approximation");
			for (int i = 0; i < matrixSize; i++) {
				System.out.println("x" + (i + 1) + " = " + xNew[i]);
			}
			System.out.println("");
			currIterations++;
			counter++;
			boolean stop = true;
			for (int i = 0; i < matrixSize && stop; i++)
				if (Math.abs(xNew[i] - xOld[i]) > epsilon)
					stop = false;
			if (stop || counter == 100)
				break;
			xOld = xNew.clone();
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static double[][] copy2DmatrixFromTempDD(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				matrix[i][j] = tempDD[i][j];
		return matrix;
	}
	
	private static void copy2DmatrixToTempDD(double[][] matrix) {
		tempDD=new double [ matrix.length][ matrix[0].length];
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				tempDD[i][j] = matrix[i][j];
	}

	public static boolean transformToDominant(int r, boolean[] V, int[] R) {
		int n = tempDD.length;
		if (r == tempDD.length) {
			double[][] T = new double[n][n + 1];
			for (int i = 0; i < R.length; i++) {
				for (int j = 0; j < n + 1; j++)
					T[i][j] = tempDD[R[i]][j];
			}
			tempDD = T;
			return true;
		}
		for (int i = 0; i < n; i++) {
			if (V[i])
				continue;
			double sum = 0;
			for (int j = 0; j < n; j++)
				sum += Math.abs(tempDD[i][j]);
			if (2 * Math.abs(tempDD[i][r]) > sum) {
				// diagonally dominant?
				V[i] = true;
				R[r] = i;
				if (transformToDominant( r + 1, V, R))
					return true;
				V[i] = false;
			}
		}
		return false;
	}

	// method to check whether matrix is
	// diagonally dominant or not
	public static boolean makeDominant() {
		boolean[] visited = new boolean[tempDD.length];
		int[] rows = new int[tempDD.length];
		Arrays.fill(visited, false);
		return transformToDominant(0, visited, rows);
	}

	public static void jacobi(double[][] matrix, double[] InitialGuess, int numOfIterations, double epsilon) {
		copy2DmatrixToTempDD( matrix);
		if (!makeDominant()) {
		System.out.println("The system isn't diagonally dominant: \n"+ "So ,The method cannot guarantee convergence.");
	    System.out.println("The original Matrix :");
		printAugmented(matrix);
		}else {
			System.out.println("The Diagonally Dominant Matrix :");
		    matrix = copy2DmatrixFromTempDD(matrix);
			printAugmented(matrix);
		}
		
		int iterationsCount = 0;
		int size = matrix.length;
		double[][] results = new double[size][numOfIterations];
		while (iterationsCount < numOfIterations) {
			for (int i = 0; i < size; i++) {
				double sum = matrix[i][size];
				for (int j = 0; j < size; j++) {
					if (j != i) {
						sum -= matrix[i][j] * InitialGuess[j];
					}
				}
				sum = 1 / matrix[i][i] * sum;
				// To store results in an array.
				results[i][iterationsCount] = sum;
			}

			// To update initialGuess array with the new one.
			for (int i = 0; i < size; i++) {
				InitialGuess[i] = results[i][iterationsCount];
			}

			// just for printing each result
			System.out.println((iterationsCount + 1) + " - Approximation");
			for (int i = 0; i < size; i++) {
				System.out.println("x" + (i + 1) + " = " + InitialGuess[i]);
			}
			System.out.println("");
			// end printing

			// Test stop
			if (iterationsCount > 0) {
				boolean stop = true;
				for (int i = 0; i < size && stop; i++) {
					if (Math.abs(InitialGuess[i] - results[i][iterationsCount - 1]) > epsilon) {
						stop = false;
					}
				}
				if (stop || iterationsCount + 1 == 100)
					break;
			}

			iterationsCount++;
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static double[][] rowSwapping(double[][] a, double[] b, int m, int n) {
		double[][] augmented = new double[a.length][a[0].length + 1];
		for (int x = 0; x < a.length; x++) {
			for (int y = 0; y < a[0].length; y++) {
				augmented[x][y] = a[x][y];
			}
		}
		for (int x = 0; x < b.length; x++) {
			augmented[x][a.length] = b[x];
		}
		for (int i = 0; i < a[0].length; i++) {
			double temp = a[m][i];
			a[m][i] = a[n][i];
			a[n][i] = temp;
		}
		return augmented;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static double[][] rowAndColumnSwapping(double[][] a, double[] b, int k, int l, int m, int n) {
		double[][] augmented = new double[a.length][a[0].length + 1];
		for (int x = 0; x < a.length; x++) {
			for (int y = 0; y < a[0].length; y++) {
				augmented[x][y] = a[x][y];
			}
		}
		for (int x = 0; x < b.length; x++) {
			augmented[x][a.length] = b[x];
		}
		augmented = rowSwapping(a, b, k, m);
		for (int i = 0; i < a[0].length; i++) {
			double temp = a[i][l];
			a[i][l] = a[i][n];
			a[i][n] = temp;
		}
		return augmented;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double[][] partitioningWithScaling(double[][] a, double[] b, int i, int j, String type) {
		double[][] augmented = new double[a.length][a[0].length + 1];
		for (int x = 0; x < a.length; x++) {
			for (int y = 0; y < a[0].length; y++) {
				augmented[x][y] = a[x][y];
			}
		}
		for (int x = 0; x < b.length; x++) {
			augmented[x][a.length] = b[x];
		}
		if (type == "complete pivoting") {
			for (int x = i; x < a.length; x++) {
				double maxInRow = augmented[i][j];
				for (int y = j; y < a[0].length; y++) {
					if (augmented[x][y] > maxInRow) {
						maxInRow = augmented[x][y];
					}
				}
				for (int y = j; y < a[0].length; y++) {
					augmented[x][y] /= maxInRow;
				}
			}
			// now i have the rectangle scalde.
			double maxInRectangle = augmented[i][j];
			int maxInRectangleRow = i;
			int maxInRectangleColumn = j;
			for (int x = i; x < a.length; x++) {
				for (int y = j; y < a[0].length; y++) {
					if (augmented[x][y] > maxInRectangle) {
						maxInRectangle = augmented[x][y];
						maxInRectangleRow = x;
						maxInRectangleColumn = y;
					}
				}
			}
			// now we need to swap them.
			return rowAndColumnSwapping(getFirstNColumn(augmented, a.length),
					getColumn(augmented, augmented.length - 1), i, j, maxInRectangleRow, maxInRectangleColumn);
		} else {// (type=="partial pivoting")
			for (int x = i; x < a.length; x++) {
				double maxInRow = augmented[i][j];
				for (int y = j; y < a[0].length; y++) {
					if (augmented[x][y] > maxInRow) {
						maxInRow = augmented[x][y];
					}
				}
				for (int y = j; y < a[0].length; y++) {
					augmented[x][y] /= maxInRow;
				}
			}
			// now i have the rectangle scalde.
			double maxInColumn = augmented[i][j];
			int maxInColumnlRow = i;
			for (int x = i; x < a.length; x++) {
				if (augmented[x][j] > maxInColumn) {
					maxInColumn = augmented[x][j];
					maxInColumnlRow = x;
				}
			}
			// now we need to swap them.
			return rowSwapping(getFirstNColumn(augmented, a.length), getColumn(augmented, augmented.length - 1), i,
					maxInColumnlRow);
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double[] getColumn(double[][] array, int index) {
		double[] column = new double[array.length]; // Here I assume a rectangular 2D array!
		for (int i = 0; i < column.length; i++) {
			column[i] = array[i][index];
		}
		return column;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double[][] getFirstNColumn(double[][] array, int n) {
		double[][] columns = new double[array.length][n]; // Here I assume a rectangular 2D array!
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < n; j++) {
				columns[i][j] = array[i][j];
			}
		}
		return columns;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static double[][] scaling(double[][] augmented) {
		for (int x = 0; x < augmented.length; x++) {
			double maxInRow = augmented[x][0];
			for (int y = 0; y < augmented[0].length; y++) {
				if (augmented[x][y] > maxInRow) {
					maxInRow = augmented[x][y];
				}
			}
			for (int y = 0; y < augmented[0].length; y++) {
				augmented[x][y] /= maxInRow;
			}
		}
		System.out.println("after Scaling: ");
		printAugmented(augmented);
		return augmented;
	}
}
