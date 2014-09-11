package model;


/**Object containing route information - its name, and "to" and "from" locations
 * Has two landmarks.
 *
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public class Route implements RouteInterface {

	private String name = "";
	private LandmarkInterface pointOfDeparture = null;
	private LandmarkInterface pointOfArrival = null;
	private String owner;
	
	protected Route( String owner, String name, LandmarkInterface pointOfDeparture, LandmarkInterface pointOfArrival ){
		
		if( owner == null )
			throw new NullPointerException("Param Owner is null.");
		if( owner.isEmpty() )
			throw new IllegalArgumentException("Param Owner is an empty string.");
		this.owner = owner;
		
		if(name != "")
			this.name = name;
		else
			throw new IllegalArgumentException("No name given to Route constructor");
		
		if(pointOfDeparture != null)
			this.pointOfDeparture = (Landmark) pointOfDeparture;
		else
			throw new IllegalArgumentException("Null value for pointOfDeparture given to Route constructor");
		
		if(pointOfArrival != null)
			this.pointOfArrival = (Landmark) pointOfArrival;
		else
			throw new IllegalArgumentException("Null value for pointOfArrival given to Route constructor");
	}
	
	/*protected Route( String owner, String name, String landmarkDepName, String streetAddressDep, String cityDep, String stateDep, String zipCodeDep,
			String landmarkArrName, String streetAddressArr, String cityArr, String stateArr, String zipCodeArr ){
		
		if(name != "")
			this.name = name;
		else
			throw new IllegalArgumentException("No name given for Route to Route constructor");
		
		if(landmarkDepName != "" && streetAddressDep != "" && cityDep != "" && stateDep != "" && zipCodeDep != "")
		{
			pointOfDeparture = new Landmark(landmarkDepName, streetAddressDep, cityDep, stateDep, zipCodeDep);
		}
		else
			throw new IllegalArgumentException("Invalid arguments to create Landmark given to Route constructor");
		
		if(landmarkArrName != "" && streetAddressArr != "" && cityArr != "" && stateArr != "" && zipCodeArr != "")
		{
			pointOfArrival = new Landmark(landmarkArrName, streetAddressArr, cityArr, stateArr, zipCodeArr);
		}
		else
			throw new IllegalArgumentException("Invalid arguments to creat Landmark given to Route constructor");
	}
	
	protected Route(String owner, String name, LandmarkInterface pointOfDeparture, String landmarkArrName, String streetAddressArr,
			String cityArr, String stateArr, String zipCodeArr){
		
		if(name != "")
			this.name = name;
		else
			throw new IllegalArgumentException("No name given for Route to Route constructor");
		
		if(pointOfDeparture != null)
			this.pointOfDeparture = (Landmark) pointOfDeparture;
		else
			throw new IllegalArgumentException("Null pointOfDeparture given to Route constructor");
		
		if(landmarkArrName != "" && streetAddressArr != "" && cityArr != "" && stateArr != "" && zipCodeArr != "")
		{
			pointOfArrival = new Landmark(landmarkArrName, streetAddressArr, cityArr, stateArr, zipCodeArr);
		}
		else
			throw new IllegalArgumentException("Invalid arguments to creat Landmark given to Route constructor");
	}
	
	protected Route(String owner, String name, String landmarkDepName, String streetAddressDep, String cityDep, 
			String stateDep, String zipCodeDep, LandmarkInterface pointOfArrival){
		
		if(name != "")
			this.name = name;
		else
			throw new IllegalArgumentException("No name given for Route to Route constructor");
		
		if(pointOfArrival != null)
			this.pointOfArrival = (Landmark) pointOfArrival;
		else
			throw new IllegalArgumentException("Null pointOfArrival given to Route constructor");
		
		if(landmarkDepName != "" && streetAddressDep != "" && cityDep != "" && stateDep != "" && zipCodeDep != "")
		{
			pointOfDeparture = new Landmark(landmarkDepName, streetAddressDep, cityDep, stateDep, zipCodeDep);
		}
		else
			throw new IllegalArgumentException("Invalid arguments to create Landmark given to Route constructor");
	}*/

	public String getName(){
		return name;
	}
	
	@Override
	public LandmarkInterface getPointOfArrival() {
	
		return pointOfArrival;
	}

	@Override
	public LandmarkInterface getPointOfDeparture() {
		
		return pointOfDeparture;
	}
	
	public boolean setName( String newName ){
		
		if( newName.isEmpty() || newName == null )
			throw new IllegalArgumentException("Null or empty value passed in.");
		
		if( name.equals(newName))
			return true;
		
		name = newName;
		return true;
	}

	@Override
	public boolean setPointOfArrival(LandmarkInterface newPointOfArrival) {
		
		if(newPointOfArrival != null)
		{
			pointOfArrival = (Landmark) newPointOfArrival;
			return true;
		}	
		else
			throw new IllegalArgumentException("Null value passed to setPointOfArrival method in Route class");
	}
	
	public boolean setPointOfDeparture(LandmarkInterface newPointOfDeparture) {
		
		if(newPointOfDeparture != null)
		{
			pointOfArrival = (Landmark) newPointOfDeparture;
			return true;
		}	
		else
			throw new IllegalArgumentException("Null value passed to setPointOfArrival method in Route class");
	}
	
	public String getOwner(){
		return owner;
	}

	//@Override
	/*public boolean setPointOfArrival(String pointOfArrName, String streetAddress,
			String city, String state, String zipCode) {
		
		try
		{
			Landmark newLandmark = new Landmark(pointOfArrName, streetAddress, city, state, zipCode);
			pointOfArrival = newLandmark;
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	@Override
	

	@Override
	public boolean setPointOfDeparture(String pointOfDepName, String streetAddress, String city, String state,
			String zipCode) {
		
		try
		{
			Landmark newLandmark = new Landmark(pointOfDepName, streetAddress, city, state, zipCode);
			pointOfArrival = newLandmark;
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}*/
}
