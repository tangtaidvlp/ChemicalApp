package phamf.com.chemicalapp.Model;

public class Chemical_Element {

    int id;

    String name = "";

    String symbol = "";

    int electron_count;

    int proton_count;

    int notron_count;

    float mass;

    String type;

    int backgroundColor;

    public Chemical_Element() {

    }

    public Chemical_Element(int id, String name, String symbol, int electron_count, int proton_count, int notron_count, float mass, String type, int backgroundColor) {

        this.name = name;
        this.symbol = symbol;
        this.electron_count = electron_count;
        this.proton_count = proton_count;
        this.notron_count = notron_count;
        this.mass = mass;
        this.type = type;
        this.id = id;
        this.backgroundColor = backgroundColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getElectron_count() {
        return electron_count;
    }

    public void setElectron_count(int electron_count) {
        this.electron_count = electron_count;
    }

    public int getProton_count() {
        return proton_count;
    }

    public void setProton_count(int proton_count) {
        this.proton_count = proton_count;
    }

    public int getNotron_count() {
        return notron_count;
    }

    public void setNotron_count(int notron_count) {
        this.notron_count = notron_count;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
