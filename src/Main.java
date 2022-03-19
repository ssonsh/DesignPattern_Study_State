public class Main {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        trafficLight.speak();

        trafficLight.change();
        trafficLight.speak();
    }
}
