public class TrafficLight {
    private TrafficState trafficState;

    public TrafficLight(){
        // Default TrafficState = Green
        this.trafficState = new GreenLight();
    }

    public void setState(TrafficState trafficState){
        this.trafficState = trafficState;
    }

    public void speak(){
        this.trafficState.speak();
    }

    public void change(){
        this.trafficState.change(this);
    }
}
