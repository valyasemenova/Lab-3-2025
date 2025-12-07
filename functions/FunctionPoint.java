package functions;

public class FunctionPoint{
    private double x;
    private double y;

    public FunctionPoint(double x, double y){
        this.x=x;
        this.y=y;
    }

    public FunctionPoint(FunctionPoint point){
        this.x=point.x;
        this.y=point.y;
    }

    FunctionPoint(){
        this(0.0, 0.0);
    }

    public double get_x(){
        return x;
    }

    public void set_x(double x) {
        this.x = x;
    }

    public double get_y(){
        return y;
    }

    public void set_y(double y) {
            this.y = y;
        }
}