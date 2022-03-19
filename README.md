# DesignPattern_Study_State

# Notion Link
https://five-cosmos-fb9.notion.site/State-fe6b9aa46371465386053b0ef30f1336

# 상태 (State)

### 의도

객체는 내부 상태에 따라 스스로 행동을 변경할 수 있게 허가하는 패턴

이렇게 하면 객체는 마치 자신의 클래스를 바꾸는 것 처럼 보인다.

<aside>
🎈 다른 이름 : 상태 표현 객체 : Object for State

</aside>

### 활용성

- 객체의 행동이 상태에 따라 달라질 수 있고, 객체의 상태에 따라서 런타임에 행동이 바뀌어야 한다.
- 어떤 연산에 그 객체의 상태에 따라 달라지는 다중 분기 조건 처리가 너무 많이 들어 있을 때.
    - 객체의 상태를 표현하기 위해 상태를 하나 이상 나열형 상수로 정의해야 한다.
    - 동일한 조건 문장들을 하나 이상의 연산에 중복 정의해야 할 때도 잦습니다.
    - 이 때 객체의 상태를 별도의 객체로 정의하면, 다른 객체들과 상관없이 그 객체의 상태를 다양화 시킬 수 있다.
    

### 구조

![image](https://user-images.githubusercontent.com/18654358/159111797-f59aff69-4625-45c2-b764-73cdcc0fd3ad.png)

### 참여자

**Context**

- 사용자가 관심있는 인터페이스를 정의한다.
- 객체의 현재 상태를 정의한 ConcreteState 서브클래스의 인스턴스를 유지/관리 한다.

**State**

- Context의 각 상태별로 필요한 행동을 캡슐화하여 인터페이스로 정의한다.

**ConcreteState 서브클래스들**

- 각 서브 클래스들은 COntext의 상태에 따라 처리되어야 할 실제 행동을 구현한다.

---

---

**상태를 가지는 예 : 새로운 기능 개발 요청 시 진행되는 개발**

![image](https://user-images.githubusercontent.com/18654358/159111803-c5c51179-06dc-4cd1-a29a-92de90df7d6b.png)

총 5가지 상태가 존재하며 상태에 따라 작업이 진행된다.

- 진행되는 과정에서 이전 상태로 돌아가거나 하는 상황이 발생될 수 있다.

이러한 총 **5가지 상태**가 **State Pattern**에서 얘기하는 **State**를 의미한다.

**신호등을 예로 들어보자.**

녹색, 빨간색 신호를 가지는 신호등이라고 가정한다면

녹색 ↔ 빨간색 신호가 변경된다.

**State Pattern 적용 전**

TrafficState.java

```java
public enum TrafficState {
    GREEN,
    RED
}
```

TrafficLight.java

```java
public class TrafficLight {
    private TrafficState trafficState;

    public TrafficLight(){
        // Default TrafficState = Green
        this.trafficState = TrafficState.GREEN;
    }

    public void setState(TrafficState trafficState){
        this.trafficState = trafficState;
    }

    public void speak(){
        if(this.trafficState == TrafficState.GREEN){
            System.out.println("green!!");
        }else{
            System.out.println("red!!");
        }
    }

    public void change(){
        System.out.println("process..! traffic state change");
        if(this.trafficState == TrafficState.GREEN){
            this.trafficState = TrafficState.RED;
        }else{
            this.trafficState = TrafficState.GREEN;
        }
    }
}
```

Main

```java
public class Main {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        trafficLight.speak();

        trafficLight.change();
        trafficLight.speak();
    }
}
```

```java
green!!
process..! traffic state change
red!!
```

**State Pattern 적용**

![image](https://user-images.githubusercontent.com/18654358/159111816-3432b477-d9bd-4ded-b6a2-11245bac00be.png)

TrafficState.java

```java
public interface TrafficState {
    void speak();
    void change(TrafficLight trafficLight);
}
```

GreenLight.java

```java
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
```

RedLight.java

```java
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
```

TrafficLight.java

```java
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
```

Main.java

```java
public class Main {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        trafficLight.speak();

        trafficLight.change();
        trafficLight.speak();
    }
}
```

```java
green light
light changed!!
red!
```

---

여기서 TrafficState 인터페이스를 구성하고 이를 구현한 Red, Green **ConcreteState Object**가 

**Strategy 패턴과 비슷**하다고 생각될 수 있다.

그러나 큰 차이점은

TrafficState의 Red와 Green은 change() 라는 메소드를 통해 

**`서로를 알고`** **`서로의 상태를 바꿔줄 수 있어야 한다는 점`**이다.

<aside>
🎈 그러나 Strategy 패턴에서의 ConcreteObject는 서로가 서로의 존재를 모른다는 점이다.

</aside>
