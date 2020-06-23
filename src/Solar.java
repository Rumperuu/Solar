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
 **   @author  Ben Goldsworthy (rumps) <me+modelsolar@bengoldsworthy.net>
 **   @version 1.0
 **/
public class Solar {	 
   private enum Planets {MERCURY, VENUS, TERRA, MARS, JUPITER,
                         SATURN, URANUS, NEPTUNE}
   private enum DwarfPlanets {CERES, PLUTO, HAUMEA, MAKEMAKE, ERIS}
   private enum Moons {LUNA, PHOBOS, DEIMOS, IO, EUROPA, GANYMEDE, CALLISTO, 
                       AMALTHEA, HIMALIA, ELARA, PASIPHAE, SINOPE, LYSITHEA, 
                       CARME, ANANKE, LEDA, THEBE, ADRASTEA, METIS, CALLIRRHOE, 
                       THEMISTO, MEGACLITE, TAYGETE, CHALDENE, HARPALYKE, 
                       KALYKE, IOCASTE, ERINOME, ISONOE, PRAXIDIKE, AUTONOE, 
                       THYONE, HERMIPPE, AITNE, EURYDOME, EUANTHE, EUPORIE, 
                       ORTHOSIE, SPONDE, KALE, PASITHEE, HEGEMONE, MNEME, AOEDE, 
                       THELXINOE, ARCHE, KALLICHORE, HELIKE, CARPO, EUKELADE, 
                       CYLLENE, KORE, HERSE, S2010J1, S2010J2, DIA, S2003J2, 
                       S2003J3, S2003J4, S2003J5, S2003J9, S2003J10, S2003J12, 
                       S2003J15, S2003J16, S2003J18, S2003J19, S2003J23, MIMAS, 
                       ENCELADUS, TETHYS, DIONE, RHEA, TITAN, HYPERION, IAPETUS, 
                       PHOEBE, JANUS, EPIMETHEUS, HELENE, TELESTO, CALYPSO, 
                       ATLAS, PROMETHEUS, PANDORA, PAN, YMIR, PAALIAQ, TARVOS, 
                       IJIRAQ, SUTTUNGR, KIVIUQ, MUNDILFARI, ALBIORIX, SKATHI, 
                       ERRIAPUS, SIARNAQ, THRYMR, NARVI, METHONE, PALLENE, 
                       POLYDEUCES, DAPHNIS, AEGIR, BEBHIONN, BERGELMIR, BESTLA, 
                       FARBAUTI, FENRIR, FORNJOT, HATI, HYRROKKIN, KARI, LOGE, 
                       SKOLL, SURTUR, ANTHE, JARNSAXA, GREIP, TARQEQ, AEGAEON, 
                       S2004S7, S2004S12, S2004S13, S2006S1, S2006S3, S2007S2, 
                       S2007S3, S2009S1,  ARIEL, UMBRIEL, TITANIA, OBERON, 
                       MIRANDA, CORDELIA, OPHELIA, BIANCA, CRESSIDA, DESDEMONA, 
                       JULIET, PORTIA, ROSALIND, BELINDA, PUCK, CALIBAN, 
                       SYCORAX, PROSPERO, SETEBOS, STEPHANO, TRINCULO, 
                       FRANCISCO, MARGARET, FERDINAND, PERDITA, MAB, CUPID, 
                       TRITON, NEREID, NAIAD, THALASSA, DESPINA, GALATEA, 
                       LARISSA, PROTEUS, HALIMEDE, PSAMATHE, SAO, LAOMEDEIA, 
                       NESO, S2004N1}

   /**
	 **   Populates the solar system and animates the orbiting.
	 **
	 **   @param args arguments passed in from the command line.
	 **/
   public static void main(String[] args) {
      SolarSystem system = new SolarSystem();
      
      // Defaults the focus to Sol.
      int previousFocus = 1;
		CentrePoint centre = new CentrePoint(54.5, "Sol", "YELLOW");
		
      // Initialises all the bodies.
      Satellite[] planets = new Satellite[8];
      importPlanets(planets, centre);      
      Satellite[] dwarfPlanets = new Satellite[5];
      importDwarfPlanets(dwarfPlanets, centre);      
      Satellite[] moons = new Satellite[173];     
      importMoons(moons, planets);
        	
		while(true){
         int focus = system.getFocus();
         // If the focus has changed, replaces the centre point with the
         // currently-focused one.
         if (focus != previousFocus) {
            switch(focus) {
               case 1:
                  centre.setDiameter(109);
                  centre.setName("Sol");
                  centre.setColour("YELLOW");
                  
                  system.setZoom(2.0);
                  break;
               case 2:case 3:case 4:case 5:case 6:case 7:case 8:case 9:
                  for (Planets p : Planets.values())
                     planets[p.ordinal()].setFocus(false);
                  
                  centre.setDiameter(planets[focus - 2].getDiameter());
                  centre.setName(planets[focus - 2].getName());
                  centre.setColour(planets[focus - 2].getColour());
                  planets[focus - 2].setFocus(true);
                  
                  system.setZoom(35.0);
                  break;
               default:
            } 
            previousFocus = focus;            
         }
         
         // Adds the centre point to the display.
		   system.drawSolarObject(0,0, (centre.getDiameter() * system.getZoom()), 
                                      centre.getColour());
         
         // If the focus is on Sol, populates the display with the planets, 
         // dwarf planets and (certain) moons - see next comment.
         if (focus == 1) {
            for (Planets p : Planets.values()) {
               Satellite currPlanet = planets[p.ordinal()];
               double currSize = currPlanet.getDiameter() * system.getZoom();
               // If any body is so small at the current zoom level that it
               // is invisible, the program doesn't bother rendering it.
               if (currSize > 0.1) {
                  double currDist = currPlanet.getDistance() + 
                                    (centre.getDiameter() * system.getZoom());
                  system.drawSolarObject(currDist, currPlanet.getAngle(), 
                                         currSize, currPlanet.getColour());
               }
               currPlanet.move();
            }
            for (DwarfPlanets dp : DwarfPlanets.values()) {
               Satellite currDwarfPlanet = dwarfPlanets[dp.ordinal()];
               double currSize = currDwarfPlanet.getDiameter()*system.getZoom();
               if (currSize > 0.1) {
                  double currDist = currDwarfPlanet.getDistance() + 
                                    (centre.getDiameter() * system.getZoom());
                  
                  system.drawSolarObject(currDist, currDwarfPlanet.getAngle(), 
                                         currSize, currDwarfPlanet.getColour());
               }
               currDwarfPlanet.move();
            }    
         }       
         // For the moons, if Sol is the current focus, they're all populated.
         // If any of the planets is the focus, only that planet's moons are.
         // The size-visibility rule still applies (hence why only 4-5 of 
         // Jupiter or Saturn's 60+ moons show up, despite all being present).
         for (Moons m : Moons.values()) {
            Satellite currMoon = moons[m.ordinal()];
            if (focus > 1) {
               if (currMoon.getPrimary().getFocus()) {
                  double currMoonSize = currMoon.getDiameter() * system.getZoom();
                  if (currMoonSize > 0.1) {
                     double currMoonDist = currMoon.getDistance() + 
                                           (centre.getDiameter() * system.getZoom());
                     
                     system.drawSolarObject(currMoonDist, currMoon.getAngle(),
                                            currMoonSize, currMoon.getColour());
                  }
               }
            } else if (focus == 1) {
               double currMoonSize = currMoon.getDiameter() * system.getZoom();
               if (currMoonSize > 0.1) {
                  Satellite currPrimary = currMoon.getPrimary();
                  double currMoonDist = currMoon.getDistance() + 
                                        (currPrimary.getDiameter() * system.getZoom());
                  double primaryDist = currPrimary.getDistance() +
                                       (centre.getDiameter() * system.getZoom());
                  double primaryAngle = currPrimary.getAngle();
                  
                  system.drawSolarObjectAbout(currMoonDist, currMoon.getAngle(), 
                                              currMoonSize, currMoon.getColour(), 
                                              primaryDist, primaryAngle);
                  currMoon.move();
               }
            }
            currMoon.move();
         }
         
         // The drawing is pushed to the screen.
		   system.finishedDrawing();
	   }
	}
   
	/**
	 **   Imports the details of the planets (Mercury, Venus, Terra, Mars, 
    **   Jupiter, Saturn, Uranus & Neptune).
	 **
	 **   @param planets The array of Satellite objects representing the planets.
	 **/
	private static void importPlanets(Satellite[] planets, CentrePoint centre) {
      planets[Planets.MERCURY.ordinal()] = new Satellite(0.38709893, startOrbit(), 0.3825, 0.2408467, "Mercury","CYAN");
	   planets[Planets.VENUS.ordinal()] = new Satellite(0.72333199, startOrbit(), 0.9488, 0.61519726, "Venus","CYAN");
	   planets[Planets.TERRA.ordinal()] = new Satellite(1.00000011, startOrbit(), 1, 1.0000174, "Terra","CYAN");
	   planets[Planets.MARS.ordinal()] = new Satellite(1.52366231, startOrbit(), 0.53226, 1.8808476, "Mars","RED");
	   planets[Planets.JUPITER.ordinal()] = new Satellite(5.20336301, startOrbit(), 11.209, 11.862615, "Jupiter","ORANGE");
	   planets[Planets.SATURN.ordinal()] = new Satellite(9.53707032, startOrbit(), 9.449, 29.447498, "Saturn","ORANGE");
	   planets[Planets.URANUS.ordinal()] = new Satellite(19.19126393, startOrbit(), 4.007, 84.016846, "Uranus","CYAN");
	   planets[Planets.NEPTUNE.ordinal()] = new Satellite(30.06896348, startOrbit(), 3.883, 164.79132, "Neptune","BLUE");
	}
   
	/**
	 **   Imports the details of the dwarf planets (Ceres, Pluto, Haumea, 
    **   Makemake & Eris).
	 **
	 **   @param dwarfPlanets The array of Satellite objects representing the
    **   dwarf planets.
	 **/
	private static void importDwarfPlanets(Satellite[] dwarfPlanets, CentrePoint centre) {
      dwarfPlanets[DwarfPlanets.CERES.ordinal()] = new Satellite(2.766, startOrbit(), 0.0741, 4.599, "Ceres", "RED");
	   dwarfPlanets[DwarfPlanets.PLUTO.ordinal()] = new Satellite(39.482, startOrbit(), 0.180, 247.92065, "Pluto", "RED");
	   dwarfPlanets[DwarfPlanets.HAUMEA.ordinal()] = new Satellite(43.335, startOrbit(), 0.10, 285.4, "Haumea", "RED");
	   dwarfPlanets[DwarfPlanets.MAKEMAKE.ordinal()] = new Satellite(45.792, startOrbit(), 0.11, 309.9, "Makemake", "RED");
	   dwarfPlanets[DwarfPlanets.ERIS.ordinal()] = new Satellite(67.668, startOrbit(), 0.19, 557, "Eris", "RED");
	}
   
	/**
	 **   Imports the details of the moons (there's loads).
	 **
	 **   @param moons The array of Satellite objects representing the moons.
	 **   @param planets The array of Satellite objects representing their 
    **   primaries.
	 **/
   private static void importMoons(Satellite[] moons, Satellite[] planets) {   
      /* Moons of Terra */
      moons[Moons.LUNA.ordinal()] = new Satellite(0.00256954860549408, startOrbit(), 0.272353835781816, 27.321582, "Luna", "WHITE", planets[Planets.TERRA.ordinal()]);
      
      /* Moons of Mars  */
      moons[Moons.PHOBOS.ordinal()] = new Satellite(0.0000627, startOrbit(), 0.00174033019237704, 0.319, "Phobos", "WHITE", planets[Planets.MARS.ordinal()]);
      moons[Moons.DEIMOS.ordinal()] = new Satellite(0.00015682041390558, startOrbit(), 0.000972076323670058, 1.262, "Deimos", "WHITE", planets[Planets.MARS.ordinal()]);
      
      /* Moons of Jupiter */
      moons[Moons.IO.ordinal()] = new Satellite(0.0028195588484814, startOrbit(), 0.285053542591054, 1.769, "Io", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.EUROPA.ordinal()] = new Satellite(0.0044860264182453, startOrbit(), 0.244696696508365, 3.551, "Europa", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.GANYMEDE.ordinal()] = new Satellite(0.0071551820564592, startOrbit(), 0.412991329706339, 7.155, "Ganymede", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.CALLISTO.ordinal()] = new Satellite(0.0125850721764721, startOrbit(), 0.377604615794672, 16.69, "Callisto", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.AMALTHEA.ordinal()] = new Satellite(0.0012125841041122, startOrbit(), 0.0130838337435914, 0.498, "Amalthea", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.HIMALIA.ordinal()] = new Satellite(0.076612053016703, startOrbit(), 0.0105046957557893, 250.56, "Himalia", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.ELARA.ordinal()] = new Satellite(0.078483737411143, startOrbit(), 0.00674181966416331, 259.64, "Elara", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.PASIPHAE.ordinal()] = new Satellite(0.157916686193752, startOrbit(), 0.00470359511453254, -743.6, "Pasiphae", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.SINOPE.ordinal()] = new Satellite(0.160022331137497, startOrbit(), 0.00297894357253728, -758.9, "Sinope", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.LYSITHEA.ordinal()] = new Satellite(0.078323307320191, startOrbit(), 0.00282215706871952, 259.2, "Lysithea", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.CARME.ordinal()] = new Satellite(0.156446077026692, startOrbit(), 0.00360608958780828, -734.1, "Carme", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.ANANKE.ordinal()] = new Satellite(0.142221275628948, startOrbit(), 0.00219501105344852, -629.7, "Ananke", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.LEDA.ordinal()] = new Satellite(0.074633415228295, startOrbit(), 0.00156786503817751, 240.92, "Leda", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.THEBE.ordinal()] = new Satellite(0.0014833098825937, startOrbit(), 0.00772957463821514, 0.675, "Thebe", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.ADRASTEA.ordinal()] = new Satellite(0.000862311738867, startOrbit(), 0.00128564933130556, 0.298, "Adrastea", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.METIS.ordinal()] = new Satellite(0.000855627151744, startOrbit(), 0.00337090983208165, 0.295, "Metis", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.CALLIRRHOE.ordinal()] = new Satellite(0.161118603425669, startOrbit(), 0.000674181966416331, -758.7, "Callirrhoe", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.THEMISTO.ordinal()] = new Satellite(0.048690532603932, startOrbit(), 0.000627146015271005, 130.02, "Themisto", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.MEGACLITE.ordinal()] = new Satellite(0.157041005280639, startOrbit(), 0.000423323560307929, -752.8, "Megaclite", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.TAYGETE.ordinal()] = new Satellite(0.15561718822344, startOrbit(), 0.000391966259544378, -732.4, "Taygete", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.CHALDENE.ordinal()] = new Satellite(0.1544139625413, startOrbit(), 0.000297894357253728, -723.7, "Chaldene", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.HARPALYKE.ordinal()] = new Satellite(0.139427118211534, startOrbit(), 0.000344930308399053, -623.3, "Harpalyke", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.KALYKE.ordinal()] = new Satellite(0.156974159409409, startOrbit(), 0.000407644909926154, -742, "Kalyke", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.IOCASTE.ordinal()] = new Satellite(0.14077740481038, startOrbit(), 0.000407644909926154, -631.6, "Iocaste", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.ERINOME.ordinal()] = new Satellite(0.155055682905108, startOrbit(), 0.000250858406108402, -728.4, "Erinome", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.ISONOE.ordinal()] = new Satellite(0.154781614833065, startOrbit(), 0.000297894357253728, -726.2, "Isonoe", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.PRAXIDIKE.ordinal()] = new Satellite(0.139761347567684, startOrbit(), 0.000533074112980355, -625.3, "Praxidike", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.AUTONOE.ordinal()] = new Satellite(0.160737581959658, startOrbit(), 0.000313573007635503, -760.9, "Autonoe", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.THYONE.ordinal()] = new Satellite(0.139968569768497, startOrbit(), 0.000313573007635503, -627.2, "Thyone", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.HERMIPPE.ordinal()] = new Satellite(0.141252010496113, startOrbit(), 0.000313573007635503, -633, "Hermippe", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.AITNE.ordinal()] = new Satellite(0.155276274280167, startOrbit(), 0.000235179755726627, -730.1, "Aitne", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.EURYDOME.ordinal()] = new Satellite(0.152843084567395, startOrbit(), 0.000235179755726627, -717.3, "Eurydome", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.EUANTHE.ordinal()] = new Satellite(0.139019358397031, startOrbit(), 0.000235179755726627, -620.4, "Euanthe", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.EUPORIE.ordinal()] = new Satellite(0.129039269822392, startOrbit(), 0.000156786503817751, -550.7, "Euporie", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.ORTHOSIE.ordinal()] = new Satellite(0.13850464518856, startOrbit(), 0.000156786503817751, -622.5, "Orthosie", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.SPONDE.ordinal()] = new Satellite(0.157000897757901, startOrbit(), 0.000156786503817751, -748.3, "Sponde", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.KALE.ordinal()] = new Satellite(0.155196059234691, startOrbit(), 0.000156786503817751, -729.4, "Kale", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.PASITHEE.ordinal()] = new Satellite(0.153772242177492, startOrbit(), 0.000156786503817751, -719.4, "Pasithee", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.HEGEMONE.ordinal()] = new Satellite(0.157602510598971, startOrbit(), 0.000235179755726627, -739.8, "Hegemone", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.MNEME.ordinal()] = new Satellite(0.140610290132305, startOrbit(), 0.000156786503817751, -620, "Mneme", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.AOEDE.ordinal()] = new Satellite(0.16029639920954, startOrbit(), 0.000313573007635503, -761.5, "Aoede", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.THELXINOE.ordinal()] = new Satellite(0.141472601871172, startOrbit(), 0.000156786503817751, -628, "Thelxinoe", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.ARCHE.ordinal()] = new Satellite(0.156118532257665, startOrbit(), 0.000235179755726627, -731.9, "Arche", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.KALLICHORE.ordinal()] = new Satellite(0.155670664920424, startOrbit(), 0.000156786503817751, -728.7, "Kallichore", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.HELIKE.ordinal()] = new Satellite(0.140837566094487, startOrbit(), 0.000313573007635503, -626.3, "Helike", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.CARPO.ordinal()] = new Satellite(0.114025687144134, startOrbit(), 0.000235179755726627, 456.3, "Carpo", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.EUKELADE.ordinal()] = new Satellite(0.155938048405344, startOrbit(), 0.000313573007635503, -730.4, "Eukelade", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.CYLLENE.ordinal()] = new Satellite(0.159153334811507, startOrbit(), 0.000156786503817751, -75, "Cyllene", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.KORE.ordinal()] = new Satellite(0.164059821759789, startOrbit(), 0.000156786503817751, -779.1, "Kore", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.HERSE.ordinal()] = new Satellite(0.153631865847909, startOrbit(), 0.000156786503817751, -714.5, "Herse", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2010J1.ordinal()] = new Satellite(0.155846703522308, startOrbit(), 0.000156786503817751, -723, "S/2010 J 1", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2010J2.ordinal()] = new Satellite(0.135744913394829, startOrbit(), 0.0000783932519088757, -588, "S/2010 J 2", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.DIA.ordinal()] = new Satellite(0.08402526013611, startOrbit(), 0.000313573007635503, 287.93, "Dia", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J2.ordinal()] = new Satellite(0.190209926584965, startOrbit(), 0.000156786503817751, -981.5, "S/2003 J 2", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J3.ordinal()] = new Satellite(0.135189089975552, startOrbit(), 0.000156786503817751, -583.8, "S/2003 J 3", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J4.ordinal()] = new Satellite(0.159982223614759, startOrbit(), 0.000156786503817751, -755.2, "S/2003 J 4", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J5.ordinal()] = new Satellite(0.157074428216254, startOrbit(), 0.000313573007635503, -738.7, "S/2003 J 5", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J9.ordinal()] = new Satellite(0.156339123632724, startOrbit(), 0.0000783932519088757, -733.3, "S/2003 J 9", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J10.ordinal()] = new Satellite(0.154039625662412, startOrbit(), 0.000156786503817751, -716.2, "S/2003 J 10", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J12.ordinal()] = new Satellite(0.119206242164459, startOrbit(), 0.0000783932519088757, -489.7, "S/2003 J 12", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J15.ordinal()] = new Satellite(0.15127220659349, startOrbit(), 0.000156786503817751, -689.7, "S/2003 J 15", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J16.ordinal()] = new Satellite(0.140082207749588, startOrbit(), 0.000156786503817751, -616.3, "S/2003 J 16", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J18.ordinal()] = new Satellite(0.136539376574398, startOrbit(), 0.000156786503817751, -596.5, "S/2003 J 18", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J19.ordinal()] = new Satellite(0.157321757939805, startOrbit(), 0.000156786503817751, -740.4, "S/2003 J 19", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J23.ordinal()] = new Satellite(0.157528980140618, startOrbit(), 0.000156786503817751, -732.4, "S/2003 J 23", "WHITE", planets[Planets.JUPITER.ordinal()]);
      moons[Moons.S2003J2.ordinal()] = new Satellite(0.155949479049324, startOrbit(), 0.0000783932519088757, -726, "S/2011 J 2", "WHITE", planets[Planets.JUPITER.ordinal()]);

      /* Moons of Saturn */ 
      
      moons[Moons.MIMAS.ordinal()] = new Satellite(0.00124025829480142, startOrbit(), 0.0310750850566783, 0.942, "Mimas", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.ENCELADUS.ordinal()] = new Satellite(0.00159119911875892, startOrbit(), 0.0395572349132187, 1.37, "Enceladus", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.TETHYS.ordinal()] = new Satellite(0.00196974728753441, startOrbit(), 0.08408460199746, 1.888, "Tethys", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.DIONE.ordinal()] = new Satellite(0.00252289687196266, startOrbit(), 0.0881924083974851, 2.737, "Dione", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.RHEA.ordinal()] = new Satellite(0.00352324533491961, startOrbit(), 0.119863282168671, 4.518, "Rhea", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.TITAN.ordinal()] = new Satellite(0.00816769646798001, startOrbit(), 0.403803640582619, 15.95, "Titan", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.HYPERION.ordinal()] = new Satellite(0.0100327631211682, startOrbit(), 0.0208526050077609, 21.28, "Hyperion", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.IAPETUS.ordinal()] = new Satellite(0.0238027452110633, startOrbit(), 0.115159687054138, 79.33, "Iapetus", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.PHOEBE.ordinal()] = new Satellite(0.0865505634594369, startOrbit(), 0.0167134413069723, -550.3, "Phoebe", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.JANUS.ordinal()] = new Satellite(0.00101244756564958, startOrbit(), 0.0141734999451247, 0.695, "Janus", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.EPIMETHEUS.ordinal()] = new Satellite(0.00101211333629343, startOrbit(), 0.0091406531725749, 0.694, "Epimetheus", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.HELENE.ordinal()] = new Satellite(0.00252289687196266, startOrbit(), 0.00250858406108402, 2.737, "Helene", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.TELESTO.ordinal()] = new Satellite(0.00197001467101933, startOrbit(), 0.00188143804581302, 1.888, "Telesto", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.CALYPSO.ordinal()] = new Satellite(0.00197001467101933, startOrbit(), 0.00148947178626864, 1.888, "Calypso", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.ATLAS.ordinal()] = new Satellite(0.00092026710922341, startOrbit(), 0.0023988335084116, 0.602, "Atlas", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.PROMETHEUS.ordinal()] = new Satellite(0.00093169775320374, startOrbit(), 0.00733760837867076, 0.613, "Prometheus", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.PANDORA.ordinal()] = new Satellite(0.00094733968707156, startOrbit(), 0.00636553205500071, 0.629, "Pandora", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.PAN.ordinal()] = new Satellite(0.00089292714789034, startOrbit(), 0.00200686724886722, 0.575, "Pan", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.YMIR.ordinal()] = new Satellite(0.154684019861069, startOrbit(), 0.00141107853435976, -1315.5, "Ymir", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.PAALIAQ.ordinal()] = new Satellite(0.1016057242696, startOrbit(), 0.00172465154199526, 686.95, "Paaliaq", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.TARVOS.ordinal()] = new Satellite(0.120208930232909, startOrbit(), 0.00117589877863314, 926.23, "Tarvos", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.IJIRAQ.ordinal()] = new Satellite(0.074359347156252, startOrbit(), 0.000940719022906508, 451.42, "Ijiraq", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.SUTTUNGR.ordinal()] = new Satellite(0.130075380826457, startOrbit(), 0.00054875276336213, -1016.6, "Suttungr", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.KIVIUQ.ordinal()] = new Satellite(0.07426576293653, startOrbit(), 0.00125429203054201, 449.22, "Kiviuq", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.MUNDILFARI.ordinal()] = new Satellite(0.124520488927244, startOrbit(), 0.00054875276336213, -952.7, "Mundilfari", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.ALBIORIX.ordinal()] = new Satellite(0.108169988824386, startOrbit(), 0.00250858406108402, 783.45, "Albiorix", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.SKATHI.ordinal()] = new Satellite(0.10387848389142, startOrbit(), 0.000627146015271005, -728.2, "Skathi", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.ERRIAPUS.ordinal()] = new Satellite(0.115930794474189, startOrbit(), 0.000783932519088757, 871.19, "Erriapus", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.SIARNAQ.ordinal()] = new Satellite(0.120425510855694, startOrbit(), 0.00313573007635503, 896.44, "Siarnaq", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.THRYMR.ordinal()] = new Satellite(0.135790702816622, startOrbit(), 0.00054875276336213, -1094.1, "Thrymr", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.NARVI.ordinal()] = new Satellite(0.127053947446861, startOrbit(), 0.00054875276336213, -1003.8, "Narvi", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.METHONE.ordinal()] = new Satellite(0.00129975112019612, startOrbit(), 0.000250858406108402, 1.01, "Methone", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.PALLENE.ordinal()] = new Satellite(0.00141900415447044, startOrbit(), 0.000313573007635503, 1.154, "Pallene", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.POLYDEUCES.ordinal()] = new Satellite(0.0025214262627956, startOrbit(), 0.000195983129772189, 2.737, "Polydeuces", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.DAPHNIS.ordinal()] = new Satellite(0.0009124461422895, startOrbit(), 0.00054875276336213, 0.594, "Daphnis", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.AEGIR.ordinal()] = new Satellite(0.138711867389373, startOrbit(), 0.000470359511453254, -1117.5, "Aegir", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.BEBHIONN.ordinal()] = new Satellite(0.114433446958637, startOrbit(), 0.000470359511453254, 834.84, "Bebhionn", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.BERGELMIR.ordinal()] = new Satellite(0.129253176610328, startOrbit(), 0.000470359511453254, -1005.7, "Bergelmir", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.BESTLA.ordinal()] = new Satellite(0.134975183187616, startOrbit(), 0.00054875276336213, -1088.7, "Bestla", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.FARBAUTI.ordinal()] = new Satellite(0.136211831805371, startOrbit(), 0.000391966259544378, -1085.5, "Farbauti", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.FENRIR.ordinal()] = new Satellite(0.150095719259842, startOrbit(), 0.000313573007635503, -1260.3, "Fenrir", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.FORNJOT.ordinal()] = new Satellite(0.168090627794958, startOrbit(), 0.000470359511453254, -1494, "Fornjot", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.HATI.ordinal()] = new Satellite(0.132662316043058, startOrbit(), 0.000470359511453254, -1038.6, "Hati", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.HYRROKKIN.ordinal()] = new Satellite(0.123243732786751, startOrbit(), 0.000627146015271005, -931.8, "Hyrrokkin", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.KARI.ordinal()] = new Satellite(0.147655844959947, startOrbit(), 0.00054875276336213, -1230.9, "Kari", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.LOGE.ordinal()] = new Satellite(0.154133209882134, startOrbit(), 0.000470359511453254, -1311.3, "Loge", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.SKOLL.ordinal()] = new Satellite(0.118083231527795, startOrbit(), 0.000470359511453254, -878.2, "Skoll", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.SURTUR.ordinal()] = new Satellite(0.151766866040592, startOrbit(), 0.000470359511453254, -1297.3, "Surtur", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.ANTHE.ordinal()] = new Satellite(0.0013215428742171, startOrbit(), 0.000156786503817751, 1.0365, "Anthe", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.JARNSAXA.ordinal()] = new Satellite(0.125743768370753, startOrbit(), 0.000470359511453254, -964.74, "Jarnsaxa", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.GREIP.ordinal()] = new Satellite(0.121699593161338, startOrbit(), 0.000470359511453254, -921.19, "Greip", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.TARQEQ.ordinal()] = new Satellite(0.120382729498107, startOrbit(), 0.00054875276336213, 887.48, "Tarqeq", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.AEGAEON.ordinal()] = new Satellite(0.0011196683431025, startOrbit(), 0.0000391966259544378, 0.808, "Aegaeon", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.S2004S7.ordinal()] = new Satellite(0.140369644995877, startOrbit(), 0.000470359511453254, -1140.24, "S/2004 S 7", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.S2004S12.ordinal()] = new Satellite(0.132876222830994, startOrbit(), 0.000391966259544378, -1046.19, "S/2004 S 12", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.S2004S13.ordinal()] = new Satellite(0.123023141411692, startOrbit(), 0.000470359511453254, -933.48, "S/2004 S 13", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.S2006S1.ordinal()] = new Satellite(0.12560339204117, startOrbit(), 0.000470359511453254, -963.37, "S/2006 S 1", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.S2006S3.ordinal()] = new Satellite(0.147702637069808, startOrbit(), 0.000470359511453254, -1227.21, "S/2006 S 3", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.S2007S2.ordinal()] = new Satellite(0.111799719632175, startOrbit(), 0.000470359511453254, -808.08, "S/2007 S 2", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.S2007S3.ordinal()] = new Satellite(0.126840040658925, startOrbit(), 0.000470359511453254, -977.8, "S/2007 S 3", "WHITE", planets[Planets.SATURN.ordinal()]);
      moons[Moons.S2009S1.ordinal()] = new Satellite(0.000782096693391, startOrbit(), 0.0000235179755726627, 0.471, "S/2009 S 1", "WHITE", planets[Planets.SATURN.ordinal()]);
      
      /* Moons of Uranus  */
      
      moons[Moons.ARIEL.ordinal()] = new Satellite(0.0012760876817807, startOrbit(), 0.0907637070600963, 2.52, "Ariel", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.UMBRIEL.ordinal()] = new Satellite(0.001778100174718, startOrbit(), 0.0916730687822392, 4.144, "Umbriel", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.TITANIA.ordinal()] = new Satellite(0.0029164853617649, startOrbit(), 0.123688872861824, 8.706, "Titania", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.OBERON.ordinal()] = new Satellite(0.0039004565862705, startOrbit(), 0.119377244006836, 13.46, "Oberon", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.MIRANDA.ordinal()] = new Satellite(0.0008683278672777, startOrbit(), 0.0369702576002258, 1.413, "Miranda", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.CORDELIA.ordinal()] = new Satellite(0.0003328924387254, startOrbit(), 0.0031514087267368, 0.335, "Cordelia", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.OPHELIA.ordinal()] = new Satellite(0.0003596307872174, startOrbit(), 0.00335523118169988, 0.376, "Ophelia", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.BIANCA.ordinal()] = new Satellite(0.0003957275576816, startOrbit(), 0.00402941314811621, 0.435, "Bianca", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.CRESSIDA.ordinal()] = new Satellite(0.0004131074842014, startOrbit(), 0.0062401028519465, 0.464, "Cressida", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.DESDEMONA.ordinal()] = new Satellite(0.0004191236126121, startOrbit(), 0.00501716812216804, 0.474, "Desdemona", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.JULIET.ordinal()] = new Satellite(0.0004304874107212, startOrbit(), 0.00733760837867076, 0.493, "Juliet", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.PORTIA.ordinal()] = new Satellite(0.0004418512088303, startOrbit(), 0.01059876765808, 0.513, "Portia", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.ROSALIND.ordinal()] = new Satellite(0.0004672526398977, startOrbit(), 0.00564431413743905, 0.558, "Rosalind", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.BELINDA.ordinal()] = new Satellite(0.0005033494103619, startOrbit(), 0.00631849610385538, 0.624, "Belinda", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.PUCK.ordinal()] = new Satellite(0.000574874492578, startOrbit(), 0.0126997068092379, 0.762, "Puck", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.CALIBAN.ordinal()] = new Satellite(0.0483369179451253, startOrbit(), 0.00768253868706982, -579.73, "Caliban", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.SYCORAX.ordinal()] = new Satellite(0.0814142604058662, startOrbit(), 0.0117589877863314, -1288.38, "Sycorax", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.PROSPERO.ordinal()] = new Satellite(0.108664648271488, startOrbit(), 0.00235179755726627, -1978.29, "Prospero", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.SETEBOS.ordinal()] = new Satellite(0.116432138508414, startOrbit(), 0.00235179755726627, -2225.21, "Setebos", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.STEPHANO.ordinal()] = new Satellite(0.053503435332492, startOrbit(), 0.00156786503817751, -677.36, "Stephano", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.TRINCULO.ordinal()] = new Satellite(0.056845728893992, startOrbit(), 0.000783932519088757, -749.24, "Trinculo", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.FRANCISCO.ordinal()] = new Satellite(0.028583294537948, startOrbit(), 0.000940719022906508, -266.56, "Francisco", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.MARGARET.ordinal()] = new Satellite(0.095890402279435, startOrbit(), 0.000862325770997632, 1687.01, "Margaret", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.FERDINAND.ordinal()] = new Satellite(0.139714555457823, startOrbit(), 0.000940719022906508, -2887.21, "Ferdinand", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.PERDITA.ordinal()] = new Satellite(0.000510816094178291, startOrbit(), 0.00156786503817751, 0.638, "Perdita", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.MAB.ordinal()] = new Satellite(0.000653324807053528, startOrbit(), 0.000783932519088757, 0.923, "Mab", "WHITE", planets[Planets.URANUS.ordinal()]);
      moons[Moons.CUPID.ordinal()] = new Satellite(0.000497279805254216, startOrbit(), 0.000783932519088757, 0.613, "Cupid", "WHITE", planets[Planets.URANUS.ordinal()]);

      /* Moons of Neptune   */
      
      moons[Moons.TRITON.ordinal()] = new Satellite(0.0023716915112404, startOrbit(), 0.212194854266945, -5.877, "Triton", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.NEREID.ordinal()] = new Satellite(0.0368576101705399, startOrbit(), 0.0266537056490177, 360.14, "Nereid", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.NAIAD.ordinal()] = new Satellite(0.000322377583180921, startOrbit(), 0.0051739546259858, 0.294, "Naiad", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.THALASSA.ordinal()] = new Satellite(0.000334730700184225, startOrbit(), 0.00642824665652781, 0.311, "Thalassa", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.DESPINA.ordinal()] = new Satellite(0.000351114623222698, startOrbit(), 0.0117589877863314, 0.335, "Despina", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.GALATEA.ordinal()] = new Satellite(0.000414130226031219, startOrbit(), 0.0137972123359621, 0.429, "Galatea", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.LARISSA.ordinal()] = new Satellite(0.000491638013722404, startOrbit(), 0.0152082908703219, 0.555, "Larissa", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.PROTEUS.ordinal()] = new Satellite(0.000786421621259581, startOrbit(), 0.0329251658017278, 1.122, "Proteus", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.HALIMEDE.ordinal()] = new Satellite(0.105135186270544, startOrbit(), 0.00486038161835029, -1879.71, "Halimede", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.PSAMATHE.ordinal()] = new Satellite(0.312136795708485, startOrbit(), 0.00313573007635503, -9115.91, "Psamathe", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.SAO.ordinal()] = new Satellite(0.149881812471906, startOrbit(), 0.00344930308399053, 2914.07, "Sao", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.LAOMEDEIA.ordinal()] = new Satellite(0.157562403076233, startOrbit(), 0.00329251658017278, 3167.85, "Laomedeia", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.NESO.ordinal()] = new Satellite(0.323447117120601, startOrbit(), 0.00470359511453254, -9373.99, "Neso", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
      moons[Moons.S2004N1.ordinal()] = new Satellite(0.000703773386070809, startOrbit(), 0.00148947178626864, 0.9362, "S/2004 N 1", "WHITE", planets[Planets.NEPTUNE.ordinal()]);
   }
   
	/**
	 **   Returns a random starting position in the body in question's orbit.
	 **
	 **   @return the angle in the orbit.
	 **/
   private static int startOrbit() {
      return (0 + (int)(Math.random() * ((360 - 0) + 1)));
   }
}
