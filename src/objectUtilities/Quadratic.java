package objectUtilities;


//******************************************************************************
//
// File:    Quadratic.java
// Package: edu.rit.numeric
// Unit:    Class edu.rit.numeric.Quadratic
//
// This Java source file is copyright (C) 2008 by Alan Kaminsky. All rights
// reserved. For further information, contact the author, Alan Kaminsky, at
// ark@cs.rit.edu.
//
// This Java source file is part of the Parallel Java Library ("PJ"). PJ is free
// software; you can redistribute it and/or modify it under the terms of the GNU
// General Public License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// PJ is distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
// A PARTICULAR PURPOSE. See the GNU General Public License for more details.
//
// Linking this library statically or dynamically with other modules is making a
// combined work based on this library. Thus, the terms and conditions of the
// GNU General Public License cover the whole combination.
//
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent modules, and
// to copy and distribute the resulting executable under terms of your choice,
// provided that you also meet, for each linked independent module, the terms
// and conditions of the license of that module. An independent module is a
// module which is not derived from or based on this library. If you modify this
// library, you may extend this exception to your version of the library, but
// you are not obligated to do so. If you do not wish to do so, delete this
// exception statement from your version.
//
// A copy of the GNU General Public License is provided in the file gpl.txt. You
// may also obtain a copy of the GNU General Public License on the World Wide
// Web at http://www.gnu.org/licenses/gpl.html.
//
//******************************************************************************


public class Quadratic
	{

// Hidden constants.

	private static final double TWO_PI = 2.0 * Math.PI;
	private static final double FOUR_PI = 4.0 * Math.PI;

// Exported fields.

	/**
	 * The number of real roots.
	 */
	public int nRoots;

	/**
	 * The first real root.
	 */
	public double x1;

	/**
	 * The second real root.
	 */
	public double x2;

// Exported constructors.

	/**
	 * Construct a new Quadratic object.
	 */
	public Quadratic()
		{
		}

// Exported operations.

	/**
	 * Solve the quadratic equation with the given coefficients. The results are
	 * stored in this Quadratic object's fields.
	 *
	 * @param  a  Coefficient of <I>x</I><SUP>2</SUP>.
	 * @param  b  Coefficient of <I>x</I>.
	 * @param  c  Constant coefficient.
	 *
	 * @exception  DomainException
	 *     (unchecked exception) Thrown if <TT>a</TT> is 0; in other words, the
	 *     coefficients do not represent a quadratic equation.
	 */
	public void solve
		(double a,
		 double b,
		 double c)
		{
		// Verify preconditions.	

		// Compute discriminant.
		double d = b*b - 4.0*a*c;

		if (d >= 0.0)
			{
			// Two real roots.
			nRoots = 2;
			double q = -0.5 * (b + sgn(b)*Math.sqrt(d));
			x1 = q / a;
			x2 = c / q;
			sortRoots();
			}

		else
			{
			// No real roots.
			nRoots = 0;
			x1 = Double.NaN;
			x2 = Double.NaN;
			}
		}

// Hidden operations.

	/**
	 * Returns the signum of x.
	 */
	private static double sgn
		(double x)
		{
		return x < 0.0 ? -1.0 : 1.0;
		}

	/**
	 * Sort the roots into descending order.
	 */
	private void sortRoots()
		{
		if (x1 < x2)
			{
			double tmp = x1; x1 = x2; x2 = tmp;
			}
		}

// Unit test main program.

	/**
	 * Unit test main program.
	 * <P>
	 * Usage: java edu.rit.numeric.Quadratic <I>a</I> <I>b</I> <I>c</I>
	 */
	
	/**
	 * Print a usage message and exit.
	 */
	

	}
