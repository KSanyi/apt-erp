package apt.erp.common;

public abstract class SimpleValueObject {

	public final String value;
	
	public SimpleValueObject(String value) {
		if(value == null) throw new NullPointerException();
		this.value = value;
	}
	
	public boolean matches(String filter) {
		return value.contains(filter);
	}
	
	public boolean isEmpty() {
		return value.isEmpty();
	}
	
	@Override
	public String toString(){
		return value;
	}
	
	@Override
	public boolean equals(Object other){
		if(this == other)
			return true;
		
		if(other == null)
			return false;
		
		if(this.getClass() != other.getClass())
			return false;

		return this.value.equals(((SimpleValueObject)other).value);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
}
