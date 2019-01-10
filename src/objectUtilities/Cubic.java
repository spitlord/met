package objectUtilities;

//******************************************************************************
//
// File:    Cubic.java
// Package: edu.rit.numeric
// Unit:    Class edu.rit.numeric.Cubic
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

public class Cubic
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

    /**
     * The third real root.
     */
    public double x3;

// Exported constructors.

    /**
     * Construct a new Cubic object.
     */
    public Cubic(){
    }

// Exported operations.

    /**
     * Solve the cubic equation with the given coefficients. The results are
     * stored in this Cubic object's fields.
     *
     * @param  a  Coefficient of <I>x</I><SUP>3</SUP>.
     * @param  b  Coefficient of <I>x</I><SUP>2</SUP>.
     * @param  c  Coefficient of <I>x</I>.
     * @param  d  Constant coefficient.
     *
     * @exception  DomainException
     *     (unchecked exception) Thrown if <TT>a</TT> is 0; in other words, the
     *     coefficients do not represent a cubic equation.
     */
    public void solve
        (double a,
         double b,
         double c,
         double d)
        {
        // Verify preconditions.

        // Normalize coefficients.
        double denom = a;
        a = b/denom;
        b = c/denom;
        c = d/denom;

        // Commence solution.
        double a_over_3 = a / 3.0;
        double Q = (3*b - a*a) / 9.0;
        double Q_CUBE = Q*Q*Q;
        double R = (9*a*b - 27*c - 2*a*a*a) / 54.0;
        double R_SQR = R*R;
        double D = Q_CUBE + R_SQR;

        if (D < 0.0)
            {
            // Three unequal real roots.
            nRoots = 3;
            double theta = Math.acos (R / Math.sqrt (-Q_CUBE));
            double SQRT_Q = Math.sqrt (-Q);
            x1 = 2.0 * SQRT_Q * Math.cos (theta/3.0) - a_over_3;
            x2 = 2.0 * SQRT_Q * Math.cos ((theta+TWO_PI)/3.0) - a_over_3;
            x3 = 2.0 * SQRT_Q * Math.cos ((theta+FOUR_PI)/3.0) - a_over_3;
            sortRoots();
            }
        else if (D > 0.0)
            {
            // One real root.
            nRoots = 1;
            double SQRT_D = Math.sqrt (D);
            double S = Math.cbrt (R + SQRT_D);
            double T = Math.cbrt (R - SQRT_D);
            x1 = (S + T) - a_over_3;
            x2 = Double.NaN;
            x3 = Double.NaN;
            }
        else
            {
            // Three real roots, at least two equal.
            nRoots = 3;
            double CBRT_R = Math.cbrt (R);
            x1 = 2*CBRT_R - a_over_3;
            x2 = x3 = CBRT_R - a_over_3;
            sortRoots();
            }
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
        if (x2 < x3)
            {
            double tmp = x2; x2 = x3; x3 = tmp;
            }
        if (x1 < x2)
            {
            double tmp = x1; x1 = x2; x2 = tmp;
            }
        }
    
    public String toString () {
        System.out.print("NUM: " + nRoots );
        if(nRoots == 1) {
            System.out.println("  root: " + this.x1);
        }
        else System.out.println("  roots: " + this.x1 +
                ", " + this.x2 + ", " + this.x3  );
        return null;
    }
}