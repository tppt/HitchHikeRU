package model;

/** The Landmark class contains all data for a landmark.
 *  This class is used by the Route object as the "to" and "from" location
 *  
 *	@authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public class Landmark implements LandmarkInterface {

		private String name = "";
		private String streetAddress = "";
		private String city = "";
		private String state = "";
		private String zipCode = "";
		private String description = "";
		private double longitude = 0.0;
		private double latitude = 0.0;
		private String region = "";
		private String owner = null;
		private int LMID = 0;
	
		//Constructors
		
		protected Landmark( String owner, String name, String streetAddress, String city, String state, String zipCode ){
			
			if(name != "")
				this.name = name;
			else
				throw new IllegalArgumentException("No name given to Route constructor");
			
			if(streetAddress != "")
				this.streetAddress = streetAddress;
			else
				throw new IllegalArgumentException("No streetAddress given to Route constructor");
			
			if(city != "")
				this.city = city;
			else
				throw new IllegalArgumentException("No city given to Route constructor");
			
			if(state != "")
				this.state = state;
			else
				throw new IllegalArgumentException("No state given to Route constructor");
			
			if(zipCode != "")
				this.zipCode = zipCode;
			else
				throw new IllegalArgumentException("No zipCode given to Route constructor");
			
			latitude = -1;
			longitude = -1;
		}
		
		protected Landmark( String owner, String name, double latitude, double longitude ){
			
			if(name != "")
				this.name = name;
			else
				throw new IllegalArgumentException("No name given to Route constructor");
			
			if(latitude != 0)
				this.latitude = latitude;
			else
				throw new IllegalArgumentException("No streetAddress given to Route constructor");
			
			if(longitude != 0)
				this.longitude = longitude;
			else
				throw new IllegalArgumentException("No city given to Route constructor");
			
		}

		//Getter methods
		
		/**
		 * @return The name of the Landmark.
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Return the string value representing this landmark object's city value.
		 */
		public String getCity() {
			
			return city;
		}

		/**
		 * Return the string, human-readable description associated with this landmark.
		 */
		public String getDescription() {
		
			return description;
		}

		/**
		 * Return this Landmark's latitude.  Note that latitude ranges from -90 degrees 
		 * to +90 degrees, where '-' denotes "South" and '+' denotes "North".
		 * Specifically, these are the degrees north/south from the equator.
		 */
		public double getLatitude() {
			
			return latitude;
		}

		/**
		 * Return this Landmark's longitude.  Note that longitude
		 * ranges from -180 degress to +180 degrees, where '-' denotes "West" and '+' denotes 
		 * "East" with respect to the Prime (Greenwich) Meridian.
		 */
		public double getLongitude() {
			
			return longitude;
		}

		/**
		 * Return the region associated with this Landmark.  In general, a region is a subsection
		 * of a city (i.e. Busch Campus is a subsection of Piscataway ).
		 */
		public String getRegion() {
			
			return region;
		}

		/**
		 * Return the State (as in, geographical subsection of U.S.) wherein this Landmark resides.
		 */
		public String getState() {
			
			return state;
		}

		/**
		 * Return the street address of this Landmark; this value excludes city, state, and zip code.
		 */
		public String getStreetAddress() {
			
			return streetAddress;
		}

		/**
		 * Return the ZIP code of the area wherein this Landmark resides.
		 */
		public String getZipCode() {
			
			return zipCode;
		}
		
		/**
		 * @return Returns the unique identifier for the user object that is the owner of this landmark.
		 */
		public String getOwner() {
			return owner;
		}
		
		public int getLMID() {
			return LMID;
		}

		//Setter methods
		
		/**
		 * Set the city for this landmark to the given parameter.
		 * @return true if successfully updated; false otherwise.
		 */
		public boolean setCity(String newCity) {
			
			if( newCity == null || newCity.isEmpty() )
				return false;
			
			if( newCity.equalsIgnoreCase(city) )
				return true;
			
			city = newCity;
			return true;
		}

		/**
		 * Set the human-readable description of this landmark to the given parameter.
		 * @return true if successfully updated; false otherwise.
		 */
		public boolean setDescription(String newDescr) {
			
			if( newDescr == null || newDescr.isEmpty() )
				return false;
			
			if( newDescr.equalsIgnoreCase(description) )
				return true;
			
			description = newDescr;
			return true;
		}

		/**
		 * Set the latitude of this landmark to the given parameter.  Note that latitude
		 * ranges from -90 degrees to +90 degrees, where '-' denotes "South" and '+' denotes 
		 * "North".  Specifically, these are the degrees north/south from the equator.
		 * 
		 * @return true if successfully updated; false otherwise.
		 */
		public boolean setLatitude(double newLatitude) {
			
			if( newLatitude < -90.0 || newLatitude > 90.0 )
				return false;
			
			if( newLatitude == latitude )
				return true;
			
			latitude = newLatitude;
			return true;
		}

		/**
		 * Set the longitude of this landmark to the given parameter.  Note that longitude
		 * ranges from -180 degress to +180 degrees, where '-' denotes "West" and '+' denotes 
		 * "East" with respect to the Prime (Greenwich) Meridian.
		 * 
		 * @return true if successfully updated; false otherwise.
		 */
		public boolean setLongitude(double newLongitude) {
			
			if( newLongitude < -180.0 || newLongitude > 180.0 )
				return false;
			
			if( newLongitude == longitude )
				return true;
			
			longitude = newLongitude;
			return true;
		}

		/**
		 * Set this landmark's region to the given parameter.
		 * @return true if updated successfully; false otherwise.
		 */
		public boolean setRegion(String newRegion) {
			
			if( newRegion == null || newRegion.isEmpty() )
				return false;
			
			if( newRegion.equalsIgnoreCase(region) )
				return true;
			
			region = newRegion;
			return true;
		}

		/**
		 * Set the U.S. state wherein this landmark resides to the given parameter.
		 * @return true if successfully updated; false otherwise.
		 */
		public boolean setState(String newState) {
			
			if( newState == null || newState.isEmpty() )
				return false;
			
			if( newState.equalsIgnoreCase(state) )
				return true;
			
			state = newState;
			return true;
		}

		/**
		 * Set the street address for this landmark to the given parameter.
		 * @return true if successfully updated; false otherwise.
		 */
		public boolean setStreetAddress(String newAddress) {
			
			if( newAddress == null || newAddress.isEmpty() )
				return false;
			
			if( newAddress.equalsIgnoreCase(streetAddress) )
				return true;
			
			streetAddress = newAddress;
			return true;
		}

		/**
		 * Set the ZIP code for the area wherein this landmark resides to the given parameter.
		 * This method assumes the use of 5-digit ZIP codes only, without the 4 digit
		 * ZIP extension codes.
		 * 
		 * @return true if successfully updated; false otherwise.
		 */
		public boolean setZipCode(String newZipCode) {

			if( newZipCode == zipCode )
				return true;
			
			if( newZipCode == "" )
				return false;
			
			zipCode = newZipCode;
			return true;
		}
		
		public boolean setLMID(int LMID) {
			
			if(LMID < 0)
				return false;
			
			this.LMID = LMID;
			return true;
		}
}
