package kr.ac.tukorea.ge.scgyong.spgp2025.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.scgyong.spgp2025.firstapp.databinding.ActivityMainBinding;

/*
** View와 Activity **는 각각 ** "화면 구성 요소" **와 ** "화면 그 자체" **


🎬 Activity란?
액티비티는 하나의 화면F 전체를 의미해.
사용자가 앱을 실행했을 때 눈으로 보게 되는 창!

* 1. 화면 하나 = 액티비티 하나
* 2. 보통 여기에 setContentView()를 써서 뷰를 붙임 => 예: 로그인 화면, 메인 화면, 설정 화면 등
📦 비유: 액티비티는 건물 하나
*
*
🎨 View란?
뷰는 화면에 그려지는 모든 UI 요소의 기본 단위
📦 비유: 뷰는 건물 안에 있는 책상, 의자, 전등 같은 내부 구성 요소
*

* 🧱 레이아웃이란?
화면에 보여질 버튼, 텍스트, 이미지 같은 View들이 어떤 구조로 배치될지를 정하는 것
*       View(버튼, 텍스트 등)를 어떻게 배치할 것인지에 대한 개념 또는 UI 설계 방식
*
*  ViewGroup 클래스 (예: LinearLayout, RelativeLayout 등)
*       그 레이아웃을 실제 구현하는 클래스. View들을 담고 배치하는 컨테이너 역할
*
* setOnClickListener는 View 클래스의 함수이기 때문에,
👉 View 또는 View를 상속받은 애들이면 다 가능해!
*
* ⚠️ 주의할 점
TextView, ImageView, Layout 같은 비버튼 뷰들은
기본적으로는 포커스/클릭이 막혀 있을 수 있으니:

android:clickable="true"
이 속성을 XML에 추가하거나 코드로 setClickable(true) 해줘야 해!
*
* 💡 자주 쓰는 리스너들
* setOnClickListener()	클릭했을 때 동작
setOnTouchListener()	터치(누름, 움직임, 뗌)를 감지
setOnLongClickListener()	꾹 눌렀을 때
GestureDetector	제스처 (플릭, 스와이프 등) 인식
setOnKeyListener()	키 입력 (물리 키보드 등)
*
* #2. 이 방법이 다른 Listener 여러개를 쓰기에 적당하다. 버튼은 4번째 방법이 좋겠다
*/

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 추가 선언 과정 설명.
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // ->activity_main.xml을 기준으로 뷰를 메모리에 올리고 바인딩 객체 생성
//        개념	비유
//        XML 레이아웃	쿠키 틀
//        LayoutInflater	반죽 눌러서 쿠키 만드는 도구
//        inflate()	쿠키 틀로 실제 쿠키 만드는 작업
//
//        "inflate(getLayoutInflater())는 XML → 실제 View 객체로 바꾸는 과정이다."
//        inflate() 꼭 해줘야 binding이 작동함
//        그걸 통해 XML의 뷰(id 붙은 애들)를 코드에서 바로 쓸 수 있음

        setContentView(binding.getRoot());
        // → 뷰 트리의 루트를 화면에 설정
        // 콘텐트 뷰는 스마트폰 화면 위에 올려진 투명한 종이(레이아웃) 같아.
        // 그 위에 TextView, Button 등을 올려서 유저에게 보여주는 거지!
        // 우리가 만든 레이아웃을 시스템이 만든 판 위에 "딱 붙이는" 구조

        binding.mainButton.setOnClickListener(m_mainButtonListener);
        binding.pushMeButton.setOnClickListener(m_pushMeButtonListener);
    }

    // 멤버에게 알려줘 -> 생성할 때 정의
    // ➤ 선언 + 익명 클래스 초기화 = 한 줄에 처리
    private View.OnClickListener m_mainButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.mainTextView.setText("Main Button Clicked");
            binding.subTextView.setText("Main is 4 characters long");
        }
    };
    private View.OnClickListener m_pushMeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.mainTextView.setText("PushMe Button Clicked");
            binding.subTextView.setText("PushMe is 6 characters long");
        }
    };
}