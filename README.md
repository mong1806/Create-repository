# AR 줄자 (Android)

화면을 두 번 탭하면 두 점 사이의 실제 거리를 표시하는 ARCore 앱입니다.

## 빌드 방법
1. Android Studio(Koala 이상)에서 이 폴더를 엽니다.
2. Gradle 동기화가 끝나면 ARCore 지원 폰을 USB로 연결하고 Run ▶ 을 누릅니다.
   - 지원 기기 목록: https://developers.google.com/ar/devices
   - 폰에 "Google Play services for AR" 앱이 설치되어 있어야 합니다(보통 자동 설치).
3. 에뮬레이터에서는 동작하지 않습니다. 실제 기기가 필요합니다.

## 사용법
- 바닥이나 벽을 천천히 비추면 면이 인식됩니다(점 무늬 표시).
- 첫 지점, 두 번째 지점을 순서대로 탭 → 거리 표시.
- "다시 측정" 버튼으로 초기화.

## 정확도
- 일반 폰: 0.3~5 m 범위에서 약 1~3 % 오차.
- ToF/깊이 센서 탑재 폰: 더 정확.
- 조명이 어둡거나 무늬 없는 매끈한 면은 인식이 잘 안 됩니다.

## 참고
- SceneView 라이브러리 버전(2.2.1)에 따라 일부 API 이름이 다를 수 있습니다.
  빌드 오류가 나면 https://github.com/SceneView/sceneview-android 의 최신 예제를 확인하세요.
