
public class LU {

	static int[] o;

	public static double[] lu(double[] Aug, int option) {

		switch (option) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
		return null;
	}

	// LU doolittle with pivoting
	/**
	 * 
	 * @param Aug    augmented matrix passed
	 * @param n      A dimensions
	 * @param tol
	 * @param option 0 for decomposition only 1 for decomp and sub
	 */

	public static void myLuDoolitte(double[][] Aug, int n, double tol, int option) {
		double[] s = new double[n];
		o = new int[n];
		double[] x = new double[n];

		int er = 0;
		Object[] AB = convertAugToAB(Aug, n);
		double[][] A = (double[][]) AB[0];
		double[] B = (double[]) AB[1];

		er = myDecompose(A, o, s, tol, n, er);

		// substitute
		if (option == 1) {
			// return A;
			// return convertToLUDoolitte(A, n);
			if (er != -1)
				mySubstitute(A, o, n, B, x);
			else {
				// ill conditioned
			}
		}

	}

	public static Object[] convertToLUDoolitte(double[][] A, int n) {
		double[][] upper = new double[n][n];
		double[][] lower = new double[n][n];
		Object[] arr = new Object[2];

		for (int i = 0; i < n; i++)
			for (int j = 0; j <= i; j++) {
				if (i == j)
					lower[i][j] = 1;
				else
					lower[i][j] = A[o[i]][j];
			}

		for (int i = 0; i < n; i++)
			for (int j = i; j < n; j++) {
				upper[i][j] = A[o[i]][j];
			}

		arr[0] = lower;
		arr[1] = upper;
		return arr;
	}

	public static double[] adjustB(double[] B, int n) {
		double[] adjustedB = new double[n];
		for (int i = 0; i < n; i++)
			adjustedB[i] = B[o[i]];

		return B;
	}

	public static double[][] adjustA(double[][] A, int n) {
		double[][] adjustedA = new double[n][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				adjustedA[i][j] = A[o[i]][j];

		return adjustedA;
	}

	public static void printA(double[][] A, int n) {
		printMatrix(adjustA(A, n), n);
	}

	public static double[] mySubstitute(double A[][], int[] o, int n, double[] B, double[] x) {
		// forward sub
		double[] y = new double[n];
		y[o[0]] = B[o[0]];

		Object[] LU = convertToLUDoolitte(A, n);
		firstPrintLU("Ax = B");
		firstPrintLU("A = LU");
		firstPrintLU("LUx = B");
		firstPrintLU("Ux = y");
		firstPrintLU("Ly = B");
		firstPrintLU("solving Ly = B");

		printAug((double[][]) LU[0], B, n);

		firstPrintLU("updating y");
		printAnswer(y, n);

		// printLU((double[][])LU[0], (double[][])LU[1], n);

		// printAnswer(adjustB(y, n), n);

		for (int i = 1; i < n; i++) {
			double sum = B[o[i]];

			for (int j = 0; j < i; j++)
				sum = sum - A[o[i]][j] * y[o[j]];

			y[o[i]] = sum;
			/*
			 * firstPrintLU("solving Ly = B");
			 * 
			 * printAug((double[][])LU[0], B, n);
			 */

			firstPrintLU("updating y");
			printAnswer(y, n);
		}

		// backward sub
		// n - 1 in x bec the arrangement of variables hasn't changed (columns hasn't
		// changed)
		// while y [o[n - 1]] as the arrangement of equations had changed
		firstPrintLU("solving Ux = y");
		printAug((double[][]) LU[1], y, n);

		x[n - 1] = y[o[n - 1]] / A[o[n - 1]][n - 1];

		firstPrintLU("updating x");
		printAnswer(x, n);

		for (int i = n - 2; i >= 0; i--) {
			double sum = 0;

			for (int j = i + 1; j < n; j++)
				sum = sum + A[o[i]][j] * x[j];

			x[i] = (y[o[i]] - sum) / A[o[i]][i];

			firstPrintLU("updating x");
			printAnswer(x, n);
		}

		return x;
	}

	public static int myDecompose(double[][] A, int[] o, double[] s, double tol, int n, int er) {
		// makes o, s ready
		for (int i = 0; i < n; i++) {
			o[i] = i;
			s[i] = Math.abs(A[i][0]);

			for (int j = 1; j < n; j++)
				if (Math.abs(A[i][j]) > s[i])
					s[i] = Math.abs(A[i][j]);
		}

		for (int i = 0; i < n - 1; i++) {
			// main diagonal loop
			myPivot(A, s, o, n, i);

			Object[] LU = convertToLUDoolitte(A, n);
			// firstPrintLU("after pivoting");
			System.out.println("after pivoting\n");
			System.out.println("LU compressed in matrix A");

			printA(A, n);
			firstPrintLU("LU matrices");
			printLU((double[][]) LU[0], (double[][]) LU[1], n);

			if (Math.abs(A[o[i]][i] / s[o[i]]) < tol) {
				er = -1;
				return -1;
			}

			for (int j = i + 1; j < n; j++) {
				// column loop
				double factor = A[o[j]][i] / A[o[i]][i];
				A[o[j]][i] = factor;

				// adjust the other numbers of the row
				for (int k = i + 1; k < n; k++)
					A[o[j]][k] = A[o[j]][k] - (A[o[i]][k] * factor);

				LU = convertToLUDoolitte(A, n);
				System.out.println("updating A\n");
				System.out.println("A matrix");

				printA(A, n);
				firstPrintLU("LU matrices");
				printLU((double[][]) LU[0], (double[][]) LU[1], n);
			}
		}

		if (Math.abs(A[o[n - 1]][n - 1] / s[o[n - 1]]) < tol) {
			er = -1;
			return -1;
		}
		return 0;
	}

	public static void myPivot(double[][] A, double[] s, int[] o, int n, int i) {
		int p = i;
		double big = Math.abs(A[o[i]][i] / s[o[i]]);
		double dummy = 0;

		// determine largest pivot in the column
		for (int k = i + 1; k < n; k++) {
			dummy = Math.abs(A[o[k]][i] / s[o[k]]);

			if (dummy > big) {
				big = dummy;
				p = k;
			}
		}

		// switch the indices
		int tempIndex = o[p];
		o[p] = o[i];
		o[i] = tempIndex;
	}

	// LU crout
	public static void myLUCrout(double[][] Aug, int n, int option) {
		// decompose LU in A
		// croutDecompose();
		Object[] AB = convertAugToAB(Aug, n);
		double[][] A = (double[][]) AB[0];
		double[] B = (double[]) AB[1];

		Object[] LU = myCroutDecompose(A, n);

		if (option == 1)
			myCroutSubstitute((double[][]) LU[0], (double[][]) LU[1], n, B);

		// gives answer in x
		// substitute();

	}

	public static Object[] myCroutDecompose(double[][] A, int n) {

		double[][] l = new double[n][n];
		double[][] u = new double[n][n];

		// first column will not be modified
		for (int i = 0; i < n; i++)
			l[i][0] = A[i][0];

		firstPrintLU("first column in L is the same in A");
		printLU(l, u, n);

		for (int i = 1; i < n; i++)
			u[0][i] = A[0][i] / l[0][0];

		firstPrintLU("first row in U");
		printLU(l, u, n);

		for (int i = 0; i < n; i++)
			u[i][i] = 1;

		firstPrintLU("main diagonal equals 1 in U");
		printLU(l, u, n);

		for (int j = 1; j < n - 1; j++) {

			// updates L value
			for (int i = j; i < n; i++) {
				double sum = 0;

				for (int k = 0; k <= j - 1; k++)
					sum = sum + l[i][k] * u[k][j];

				l[i][j] = A[i][j] - sum;
				firstPrintLU("update L");
				printLU(l, u, n);
			}

			// updates U value
			for (int k = j + 1; k < n; k++) {
				double sum = 0;

				for (int i = 0; i <= j - 1; i++)
					sum = sum + l[j][i] * u[i][k];

				u[j][k] = (1 / l[j][j]) * (A[j][k] - sum);
				firstPrintLU("update U");
				printLU(l, u, n);
			}

		}

		// last pivot
		double sum = 0;

		for (int k = 0; k <= n - 1; k++)
			sum = sum + l[n - 1][k] * u[k][n - 1];

		l[n - 1][n - 1] = A[n - 1][n - 1] - sum;
		firstPrintLU("update last element in main diagonal in L");
		printLU(l, u, n);

		Object[] lu = new Object[2];

		lu[0] = l;
		lu[1] = u;

		return lu;
	}

	public static double[] myCroutSubstitute(double l[][], double u[][], int n, double[] B) {
		double[] x = new double[n];
		// forward sub
		firstPrintLU("Ax = B");
		firstPrintLU("A = LU");
		firstPrintLU("LUx = B");
		firstPrintLU("Ux = y");
		firstPrintLU("Ly = B");
		firstPrintLU("solving Ly = B");
		double[] y = new double[n];
		y[0] = B[0] / l[0][0];

		printAug(l, B, n);

		firstPrintLU("updating y");
		printAnswer(y, n);

		for (int i = 1; i < n; i++) {
			double sum = 0;

			for (int j = 0; j < i; j++)
				sum = sum + l[i][j] * y[j];

			y[i] = (B[i] - sum) / l[i][i];

			firstPrintLU("updating y");
			printAnswer(y, n);
		}

		firstPrintLU("solving Ux = y");
		printAug(u, y, n);

		// backward sub

		x[n - 1] = y[n - 1];
		firstPrintLU("updating y");
		printAnswer(x, n);

		for (int i = n - 2; i >= 0; i--) {
			double sum = 0;

			for (int j = i + 1; j < n; j++)
				sum = sum + u[i][j] * x[j];

			x[i] = (y[i] - sum);
			firstPrintLU("updating y");
			printAnswer(x, n);
		}

		return x;
	}

	// LU Cholesky diagonal
	public static void myLUSymCholesky(double[][] Aug, int n, int option) {

		Object[] AB = convertAugToAB(Aug, n);
		double[][] A = (double[][]) AB[0];
		double[] B = (double[]) AB[1];

		if (!isSymmetric(A, n))
			System.out.println("not symmetric matrix so cannot be solved with this method");
		else {

			Object[] LU = myLuSymCholeskyDec(A, n);
			if (option == 1)
				LuSymCholeskySub((double[][]) LU[0], (double[][]) LU[1], B, n);
		}

	}

	public static double[] luCholesky(double[][] Aug, int n, int option) {
		/*
		 * if (!isSymmetric(mat, n)) return null; return null;
		 */
		// logic

		Object[] AB = convertAugToAB(Aug, n);
		double[][] A = (double[][]) AB[0];
		double[] B = (double[]) AB[1];

		Object[] LDU = myCholeskyDecompose(A, n);

		if (option == 1)
			myCholeskySubstitute((double[][]) LDU[0], (double[][]) LDU[1], (double[][]) LDU[2], n, B);

		return null;

	}

	// returns LDU
	public static Object[] myCholeskyDecompose(double[][] A, int n) {
		System.out.println("U in this formula is the same U in crout LU decomposition");
		firstPrintLU("so we start with crout decomposition to get U");

		Object[] LU = myCroutDecompose(A, n);
		firstPrintLU("crout decomposition ended and now U is ready");

		double[][] lDash = (double[][]) LU[0];
		double[][] u = (double[][]) LU[1];
		double[][] d = new double[n][n];
		double[][] l = new double[n][n];

		printLDU(l, d, u, n);

		firstPrintLU("LD in this formula is the same L in crout LU decomposition");

		for (int i = 0; i < n; i++) {
			d[i][i] = lDash[i][i];
			firstPrintLU("updating D");
			printLDU(l, d, u, n);
		}
		// d is ready

		for (int i = 0; i < n; i++)
			l[i][i] = 1;

		firstPrintLU("the main diagonal in L is 1");
		printLDU(l, d, u, n);

		for (int j = 0; j < n - 1; j++) {
			for (int i = j + 1; i < n; i++)
				l[i][j] = lDash[i][j] / d[j][j];

			firstPrintLU("updating L");
			printLDU(l, d, u, n);
		}

		firstPrintLU("now LDU is ready");

		Object[] LDU = new Object[3];

		LDU[0] = l;
		LDU[1] = d;
		LDU[2] = u;

		return LDU;
	}

	public static double[] myCholeskySubstitute(double l[][], double d[][], double u[][], int n, double[] B) {
		// forward
		// forward sub
		firstPrintLU("Ax = B");
		firstPrintLU("A = LDU");
		firstPrintLU("LDUx = B");
		firstPrintLU("Ux = y");
		firstPrintLU("LDy = B");
		firstPrintLU("Dy = z");
		firstPrintLU("Lz = B");

		firstPrintLU("solving Lz = B");
		printAug(l, B, n);

		double[] z = new double[n];
		z[0] = B[0];

		firstPrintLU("updating z");
		printAnswer(z, n);

		for (int i = 1; i < n; i++) {
			double sum = B[i];

			for (int j = 0; j < i; j++)
				sum = sum - l[i][j] * z[j];

			z[i] = sum;

			firstPrintLU("updating z");
			printAnswer(z, n);
		}

		firstPrintLU("solving Dy = z");
		printAug(d, z, n);
		// diagonal
		double[] y = new double[n];

		for (int i = 0; i < n; i++) {
			y[i] = z[i] / d[i][i];

			firstPrintLU("updating y");
			printAnswer(y, n);
		}

		// backward
		firstPrintLU("solving Ux = y");
		printAug(u, y, n);

		double[] x = new double[n];
		x[n - 1] = y[n - 1];
		firstPrintLU("updating x");
		printAnswer(x, n);

		for (int i = n - 2; i >= 0; i--) {
			double sum = 0;

			for (int j = i + 1; j < n; j++)
				sum = sum + u[i][j] * x[j];

			x[i] = (y[i] - sum);

			firstPrintLU("updating x");
			printAnswer(x, n);
		}

		return x;
	}

	// LU choleskey transposed

	public static Object[] myLuSymCholeskyDec(double[][] A, int n) {

		double[][] l = new double[n][n];
		firstPrintLU("A = LU");
		firstPrintLU("A is symmetric");
		firstPrintLU("U = L^T");
		firstPrintLU("A = L(L^T)");

		l[0][0] = Math.sqrt(A[0][0]);
		firstPrintLU("update L and U");
		printLU(l, transposeSquare(l, n), n);

		for (int i = 1; i < n; i++) {
			l[i][0] = A[i][0] / l[0][0];
			firstPrintLU("update L and U");
			printLU(l, transposeSquare(l, n), n);
		}

		for (int k = 1; k < n; k++) {

			// non main diagonal
			for (int i = 1; i < k; i++) {
				double sum0 = 0;

				for (int j = 0; j < i; j++)
					sum0 += l[i][j] * l[k][j];

				l[k][i] = (A[k][i] - sum0) / l[i][i];

				firstPrintLU("update L and U");
				printLU(l, transposeSquare(l, n), n);
			}

			// main diagonal
			double sum = 0;
			for (int i = 0; i < k; i++)
				sum += Math.pow(l[k][i], 2);

			l[k][k] = Math.sqrt(A[k][k] - sum);

			firstPrintLU("update L and U");
			printLU(l, transposeSquare(l, n), n);
		}

		double[][] u = new double[n][n];

		u = transposeSquare(l, n);
		Object[] LU = new Object[n];
		LU[0] = l;
		LU[1] = u;
		return LU;

	}

	public static double[] LuSymCholeskySub(double[][] l, double[][] u, double[] B, int n) {
		// solving with lu
		firstPrintLU("Ax = B");
		firstPrintLU("A = L(L^T)");
		firstPrintLU("LUx = B");
		firstPrintLU("Ux = y");
		firstPrintLU("Ly = B");

		double[] x = new double[n];

		firstPrintLU("solving Ly = B");
		printAug(l, B, n);

		// forward sub
		double[] y = new double[n];
		y[0] = B[0] / l[0][0];

		firstPrintLU("updating y");
		printAnswer(y, n);

		for (int i = 1; i < n; i++) {
			double sum = 0;

			for (int j = 0; j < i; j++)
				sum = sum + l[i][j] * y[j];

			y[i] = (B[i] - sum) / l[i][i];

			firstPrintLU("updating y");
			printAnswer(y, n);
		}

		// back sub
		firstPrintLU("solving Ux = y");
		printAug(u, y, n);

		x[n - 1] = y[n - 1] / u[n - 1][n - 1];

		firstPrintLU("updating x");
		printAnswer(x, n);

		for (int i = n - 2; i >= 0; i--) {
			double sum = 0;

			for (int j = i + 1; j < n; j++)
				sum = sum + u[i][j] * x[j];

			x[i] = (y[i] - sum) / u[i][i];

			firstPrintLU("updating x");
			printAnswer(x, n);
		}

		return x;
	}

	public static double[][] transposeSquare(double[][] mat, int n) {
		double[][] ans = new double[n][n];

		for (int i = 0; i < mat.length; i++)
			for (int j = 0; j < mat[0].length; j++)
				ans[j][i] = mat[i][j];

		return ans;
	}

	public static boolean isSymmetric(double[][] mat, int n) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (mat[i][j] != mat[j][i])
					return false;

		return true;
	}

	public static Object[] convertAugToSimple(double[][] Aug, int n) {
		// converts Augmented matrix to A and B form
		double[][] A = new double[n][n];
		double[] B = new double[n];

		Object[] AB = new Object[2];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				A[i][j] = Aug[i][j];

		for (int i = 0; i < n; i++)
			B[i] = Aug[i][n];

		AB[0] = A;
		AB[1] = B;

		return AB;
	}

	public static void printLU(double[][] L, double[][] U, int n) {
		// LU printing

		for (int i = 0; i < n; i++) {
			// prints L
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			// print nums
			for (int j = 0; j < n; j++)
				System.out.printf("%-9.2f", L[i][j]);

			if (i == 0)
				System.out.print(" \\");
			else if (i == n - 1)
				System.out.print(" /");
			else
				System.out.print(" |");

			System.out.print(" ");

			// prints U
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			// print nums
			for (int j = 0; j < n; j++)
				System.out.printf("%-9.2f", U[i][j]);

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

	public static void printLDU(double[][] L, double[][] D, double[][] U, int n) {
    	//LU printing

    	
    	for (int i = 0; i < n; i++) {
    		//prints L
    		if (i == 0)
    			System.out.print("/ ");
    		else if (i == n - 1) 
    			System.out.print("\\ ");
    		else
    			System.out.print("| ");

    		//print nums
    		for (int j = 0; j < n; j++)
    			System.out.printf("%-9.2f", L[i][j]);

    		if (i == 0)
    			System.out.print(" \\");
    		else if (i == n - 1) 
    			System.out.print(" /");
    		else
    			System.out.print(" |");
    		
    		System.out.print(" ");
    		
    		
    		
    		
    		//prints D
    		if (i == 0)
    			System.out.print("/ ");
    		else if (i == n - 1) 
    			System.out.print("\\ ");
    		else
    			System.out.print("| ");

    		//print nums
    		for (int j = 0; j < n; j++)
    			System.out.printf("%-9.2f", D[i][j]);
    		
    		if (i == 0)
    			System.out.print(" \\");
    		else if (i == n - 1) 
    			System.out.print(" /");
    		else
    			System.out.print(" |");
    		
    		System.out.print(" ");
    		
    		System.out.println();
			
    	}
    	
    	System.out.println();
    	
    	for (int i = 0; i < n; i++) {
    		//prints U
    		if (i == 0)
    			System.out.print("/ ");
    		else if (i == n - 1) 
    			System.out.print("\\ ");
    		else
    			System.out.print("| ");	

    		//print nums
    		for (int j = 0; j < n; j++)
    			System.out.printf("%-9.2f", U[i][j]);
    		
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
	
	
	
	/*
	public static void printLDU(double[][] L, double[][] D, double[][] U, int n) {
		// LU printing

		for (int i = 0; i < n; i++) {
			// prints L
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			// print nums
			for (int j = 0; j < n; j++)
				System.out.printf("%-9.2f", L[i][j]);

			if (i == 0)
				System.out.print(" \\");
			else if (i == n - 1)
				System.out.print(" /");
			else
				System.out.print(" |");

			System.out.print(" ");

			// prints D
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			// print nums
			for (int j = 0; j < n; j++)
				System.out.printf("%-9.2f", D[i][j]);

			if (i == 0)
				System.out.print(" \\");
			else if (i == n - 1)
				System.out.print(" /");
			else
				System.out.print(" |");

			System.out.print(" ");

			// prints U
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			// print nums
			for (int j = 0; j < n; j++)
				System.out.printf("%-9.2f", U[i][j]);

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
*/
	public static void firstPrintLU(String formula) {
		// may be Ax = B, LUx = B, LDUx = B L.(L^T).x = B, A = LU, A = LDU
		System.out.println(formula + "\n");
	}

	public static void printMatrix(double[][] A, int n) {

		System.out.println();
		for (int i = 0; i < n; i++) {
			// prints L
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			// print nums
			for (int j = 0; j < n; j++)
				System.out.printf("%-9.2f", A[i][j]);

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

	public static void printAug(double A[][], double[] B, int n) {

		double[][] aug = aug(A, B, n);

		System.out.println();
		for (int i = 0; i < n; i++) {
			// prints L
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			// print nums
			for (int j = 0; j < n; j++)
				System.out.printf("%-9.2f", aug[i][j]);

			System.out.printf("|%-9.2f", aug[i][n]);

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

	public static double[][] aug(double A[][], double[] B, int n) {
		double[][] Aug = new double[n][n + 1];

		for (int i = 0; i < n; i++) {

			for (int j = 0; j < n; j++)
				Aug[i][j] = A[i][j];

			Aug[i][n] = B[i];
		}

		return Aug;
	}

	public static void printAnswer(double[] x, int n) {

		System.out.println();
		for (int i = 0; i < n; i++) {
			// prints L
			if (i == 0)
				System.out.print("/ ");
			else if (i == n - 1)
				System.out.print("\\ ");
			else
				System.out.print("| ");

			// print nums
			System.out.printf("%7.2f", x[i]);

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

	public static Object[] convertAugToAB(double[][] aug, int n) {
		// n is the 2d dimensions
		double[][] A = new double[n][n];
		double[] B = new double[n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				A[i][j] = aug[i][j];

		for (int i = 0; i < n; i++)
			B[i] = aug[i][n];

		Object[] AB = new Object[2];
		AB[0] = A;
		AB[1] = B;

		return AB;
	}
}
