package esof322.a1;

/**
 * Team 03
 *
 * @author Megan Weller, Ashley Bertrand, Sheila Tupker
 */
public class Vector3D {
    private double x;
    private double y;
    private double z;

    //constructor Megan
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //Vector3D scale Megan
    public Vector3D scale(double f) {
        Vector3D v = new Vector3D(this.x * f, this.y * f, this.z * f);
        return v;

    }

    //Vector3D add Ashley
    //formula for add
    public Vector3D add(Vector3D v) {
        return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    //Vector3D subtract Sheila
    //formula for subtract
    public Vector3D subtract(Vector3D v) {
        return new Vector3D(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    //Vector3D negate Sheila
    //formula for negate
    public Vector3D negate() {
        return new Vector3D(-1 * x, -1 * y, -1 * z);
    }

    //double dot Ashley
    //formula for dot product
    public double dot(Vector3D v) {
        return ((this.x * v.x) + (this.y * v.y) + (this.z * v.z));
    }

    //double magnitude Sheila
    //formula for magnitutde
    public double magnitude() {
        return (Math.sqrt(x * x + y * y + z * z));
    }

    //toString Ashley
    //overrides an object's toString() method
    @Override
    public String toString() {
        return "{" + this.x + ", " + this.y + ", " + this.z + "}";
    }

    //implementation of equals Megan 
    public boolean equals(Vector3D v) {
        if (this == null || v == null) {
            return false;
        }
        if (this.x != v.x) {
            return false;
        }
        if (this.y != v.y) {
            return false;
        }
        if (this.z != v.z) {
            return false;
        }

        return true;
    }

}
