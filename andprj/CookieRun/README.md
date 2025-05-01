# CookieRun Copy

* Skeleton
  * `buildFeatures` (`viewBinding`, `buildConfig`)
  * `a2dg` framework Module import
  * Activity 를 `.app` package 로 옮기고 추가 Activity 생성(no layout xml), `startActivity()` 하는 구조 만듦.
  * 빈 `Scene` 생성후 push
* `Player`
  * `SheetSprite`
    * state 에 따라 다른 이미지/동작 이 되도록 한다.
    * 여러 장의 프레임이 포함된 한 장의 이미지를 주고 state 에 따라 여러 Rect[] 들 중 하나가 선택되어 애니메이션 되게 한다.
    * 중력/점프/뛰어내리기 를 적용한다. 더블 점프와 물리법칙.
    * 낙하중 플랫폼을 만나면 달리기 상태로 전환한다. 발 밑에 가장 가까운 착지대상을 찾는다.
  * 여러 종류의 Cookie sheet 를 선택할 수 있게 구현
  * Magnification: Scale 적용하여 특정 아이템 먹었을 때 커지거나 작아지도록
* `MapObject`
  * `Platform`, `JellyItem`, `Obstacle` 은 화면 우측에서 생성되어 왼쪽으로 이동, 왼쪽에서 사라지면 없앤다. 재활용 대상이다.
  * Text File 을 통해 map 을 읽어들인다. 특정 글자이면 해당 객체를 생성하도록 한다.
  * `Obstacle` 은 `ObstacleFactory` 를 사용해 생성하며 상속받은 class 의 객체가 생성된다.
    * `Obstacle` - 정적 이미지
    * `AnimObstacle` - 특정 시간 후 출연하며 흐른 시간에 따라 이미지를 바꾸어 준다
    * `FallingObstacle` - 이미지 한 장으로 움직이는 애니메이션을 한다
      * `ValueAnimator` 와 `BounceInterpolator` 를 사용하여 다양한 움직임 효과를 낸다
      * `IRecyclable.onRecycle()` 에서 animation 을 중지시켜 주어야 한다
  * `MapLoader` 구현
    * JSON 으로 시도
    * text file 로 구현
    * controller 이지만 `Gauge` 를 사용하여 map 진행상황을 표시
* Music/Sound
  * `Sound` class 를 만든다. `raw` resource 로 `.mp3` 나 `.wav` 파일을 지정할 수 있게 한다. 
  * Music 은 무한루프를 돌리고 Sound Effect 는 짧게 플레이한다.
  * Music 은 Activity/Scene 의 pause 시에 멈춰야 한다.
* `Button`
  * 이미지, 위치, 콜백 설정하여 터치 이벤트시 코드가 실행되게 한다.
  * Jump, Slide, Pause 버튼을 생성하며 Slide 의 경우 pressed, released 이벤트를 모두 처리한다.
* `PausedScene`
  * Pause 버튼을 누르면 생성하여 Push 한다. 다시 게임을 재개하거나 게임을 종료할 수 있도록 한다.
* Activity extras
  * Title Activity 에서 Game Activity 로 인자를 전달한다 (stage number)