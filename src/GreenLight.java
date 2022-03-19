public class GreenLight implements TrafficState{
    @Override
    public void speak() {
        System.out.println("green light");
    }

    @Override
    public void change(TrafficLight trafficLight) {
        System.out.println("light changed!!");

        trafficLight.setState(new RedLight());
    }
}
