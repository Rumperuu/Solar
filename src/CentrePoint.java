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
 ** This class represents a point in space, around which satellites orbit.
 **/

/**
 **   @author  Ben Goldsworthy (rumps) <me+modelsolar@bengoldsworthy.net>
 **   @version 1.0
 **/
 public class CentrePoint extends Point {
   /**
    **   Constructor method. Used for central points in the simulation.
    **
    **   @param radius The radius of the body (the table I used for values had 
    **   radii rather than diameters).
    **   @param name The name of the body.
    **   @param colour The colour of the body.
    **/
	public CentrePoint(double radius, String name, String colour) {
		super(radius, name, colour, true);
	}
}

