public class RedLight implements TrafficState{
    @Override
    public void speak() {
        System.out.println("red!");
    }

    @Override
    public void change(TrafficLight trafficLight) {
        System.out.println("light changed!!");

        trafficLight.setState(new GreenLight());
    }
}
