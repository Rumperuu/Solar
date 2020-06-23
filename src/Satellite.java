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
 ** This class represents a satellite in space, which orbits a primary, 
 ** utilised in the Solar program.
 **/

/**
 **   @author  Ben Goldsworthy (rumps) <me+modelsolar@bengoldsworthy.net>
 **   @version 1.0
 **/
public class Satellite extends Point {
   private double distance;
   private double angle;
   private double speed;
   private Satellite primary;
   
   /**
    **   Constructor method. Used for moon-type satellites.
    **
    **   @param distance The distance of the body from its primary.
    **   @param radius The radius of the body (the table I used for values had 
    **   radii rather than diameters).
    **   @param angle The starting position of the body along its orbit.
    **   @param speed The speed of the body.
    **   @param name The name of the body.
    **   @param colour The colour of the body.
    **   @param primary The primary, or orbital target, of the satellite.
    **/
	public Satellite(double distance, double angle, double radius, double speed, 
                    String name, String colour, Satellite primary) {
		super(radius, name, colour, false);
		this.distance = distance;
		this.angle = angle;
      // Determines the speed as the result of 1 - (the sidereal orbital period 
      // of the body in years/1000), which gives a speed relative to that of
      // Terra
		this.speed = (1 - (speed/1000));
      this.primary = primary;
	}
   
   /**
    **   Constructor function. Used for non-moon-type satellites.
    **
    **   @param distance The distance of the body from its primary.
    **   @param radius The radius of the body (the table I used for values had 
    **   radii rather than diameters).
    **   @param angle The starting position of the body along its orbit.
    **   @param speed The speed of the body.
    **   @param name The name of the body.
    **   @param colour The colour of the body.
    **/
	public Satellite(double distance, double angle, double radius, double speed, 
                    String name, String colour) {
		super(radius, name, colour, false);
		this.distance = distance;
		this.angle = angle;
		this.speed = (1 - (speed/1000));
      // Planets orbit the centre point, or are the centre point, so don't need
      // primaries
      this.primary = null;
	}
	
	/**
	 **   Moves the body along its orbit according to its current speed.
	 **/
	public void move() {
	   this.angle += this.speed;
	}  
   
	/**
	 *	Accessor function.
    *
	 * @return The distance between this body and its primary.
	 */
	public double getDistance() {
	   return this.distance * 5;
	}
	
	/**
	 *	Accessor function.
    *
	 * @return The angle along this body's orbit it is currently at.
	 */
	public double getAngle() {
	   return this.angle;
	}
	
	/**
	 *	Accessor function.
    *
	 * @return The speed of this body.
	 */
	public double getSpeed() {
	   return this.speed;
	}
   
	/**
	 *	Accessor function.
    *
	 * @return The primary of this body (where applicable).
	 */
   public Satellite getPrimary() {
      return primary;
   } 
	
	/**
	 *	Mutator function.
    *
	 * @param speed Sets the speed of this body.
	 */
	public void setSpeed(double speed) {
	   this.speed = speed;
   } 
}

