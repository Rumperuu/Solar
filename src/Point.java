/******************************************************************************
 *                             Solar 1.0                                      *
 *                  Copyright © 2015 Ben Goldsworthy (rumps)                  *
 *                                                                            *
 * A program to simulate the motion of the Sol system                         *
 *                                                                            *
 * This file is part of Solar.                                                *
 *                                                                            *
 * Solar is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * Solar is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with Solar.  If not, see <http://www.gnu.org/licenses/>.             *
 ******************************************************************************/

/**
 ** This class represents a point in space, utilised in the Solar program.
 **/

/**
 **   @author  Ben Goldsworthy (rumps) <me+modelsolar@bengoldsworthy.net>
 **   @version 1.0
 **/
public class Point {
	private double diameter;
	private String name;
	private String colour;
   private boolean focus;
 
   /**
    **   Constructor method.
    **
    **   @param radius The radius of the body (the table I used for values had 
    **   radii rather than diameters).
    **   @param name The name of the body.
    **   @param colour The colour of the body.
    **   @param focus Whether the body is the current focus or not.
    **/
	public Point(double radius, String name, String colour, boolean focus) {
		this.diameter = radius * 2;
		this.name = name;
		this.colour = colour;
	}
	
	/**
	 *	Accessor function.
    *
	 * @return Whether the body is the current focus or not.
	 */
	public boolean getFocus() {
	   return focus;
	}
   
	/**
	 *	Accessor function.
	 * @return The body's diameter.
	 */
	public double getDiameter() {
	   return this.diameter;
	}
	
	/**
	 *	Accessor function.
	 * @return The body's radius.
	 */
	public double getRadius() {
	   return this.diameter/2;
	}
	
	/**
	 *	Accessor function.
	 * @return The body's name.
	 */
	public String getName() {
	   return this.name;
	}
	
	/**
	 *	Accessor function.
	 * @return The body's colour.
	 */
	public String getColour() {
	   return this.colour;
	}
   
	/**
	 *	Mutator function.
	 * @param focus Whether the body is in focus or not.
	 */
	public void setFocus(boolean focus) {
	   this.focus = focus;
	}
   
	/**
	 *	Mutator function.
	 * @param diameter The new diameter.
	 */
	public void setDiameter(double diameter) {
	   this.diameter = diameter;
	}
	
	/**
	 *	Mutator function.
	 * @param name The new name.
	 */
	public void setName(String name) {
	   this.name = name;
	}
	
	/**
	 *	Mutator function.
	 * @param colour The new colour.
	 */
	public void setColour(String colour) {
	   this.colour = colour;
	}
}
