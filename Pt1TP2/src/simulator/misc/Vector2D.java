package simulator.misc;

import org.json.JSONArray;

public class Vector2D {

	double _x;
	double _y;

	// create the zero vector
	public Vector2D() {
		_x = _y = 0.0;
	}

	// copy constructor
	public Vector2D(Vector2D v) {
		_x = v._x;
		_y = v._y;
	}

	// create a vector from an array
	public Vector2D(double x, double y) {
		_x = x;
		_y = y;
	}

	// return the inner product of this Vector a and b
	public double dot(Vector2D that) {
		return _x * that._x + _y * that._y;
	}

	// return the length of the vector
	public double magnitude() {
		return Math.sqrt(dot(this));
	}

	// return the distance between this and that
	public double distanceTo(Vector2D that) {
		return minus(that).magnitude();
	}

	// create and return a new object whose value is (this + that)
	public Vector2D plus(Vector2D that) {
		return new Vector2D(_x + that._x, _y + that._y);
	}

	// create and return a new object whose value is (this - that)
	public Vector2D minus(Vector2D that) {
		return new Vector2D(_x - that._x, _y - that._y);
	}

	// return the corresponding coordinate
	public double getX() {
		return _x;
	}

	public double getY() {
		return _y;
	}

	// create and return a new object whose value is (this * factor)
	public Vector2D scale(double factor) {
		return new Vector2D(_x * factor, _y * factor);
	}

	// return the corresponding unit vector
	public Vector2D direction() {
		if (magnitude() > 0.0)
			return scale(1.0 / magnitude());
		else
			return new Vector2D(this);
	}

	// rotate a vector by 'alpha' degrees
	public Vector2D rotate(double alpha) {

		assert(alpha >= -180.0 && alpha <= 180.0);

		double angle = alpha * Math.PI / 180.0;
		double sine = Math.sin(angle);
		double cosine = Math.cos(angle);

		double x = _x;
		double y = _y;
		Vector2D r = new Vector2D();

		r._x = cosine * x + (-sine) * y;
		r._y = sine * x + cosine * y;

		return r;
	}
	
	// compute the angle 'alpha' between 'this' and 'v', 
	// it's such that this.rotate(alpha) equals 'v'
	public double angle(Vector2D v)  {
		double a2 = Math.atan2(v.getX(), v.getY());
		double a1 = Math.atan2(_x, _y);
		double angle = a1 - a2;
		double K = a1 > a2 ? -2.0 * Math.PI : 2.0 * Math.PI;
		angle = (Math.abs(K + angle) < Math.abs(angle)) ? K + angle : angle;
		return angle * 180.0 / Math.PI;
	}
	
	public JSONArray asJSONArray() {
		JSONArray a = new JSONArray();
		a.put(_x);
		a.put(_y);
		return a;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(_x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
		if (Math.abs(_x - other._x)>0.00000000001)
			return false;
		if (Math.abs(_y - other._y)>0.00000000001)
			return false;
		return true;
	}

	// return a string representation of the vector
	public String toString() {
		return "[" + _x + "," + _y + "]";
	}

}
