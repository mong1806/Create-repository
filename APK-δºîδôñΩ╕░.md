# 개발 도구 없이 APK 만들기 (GitHub 이용, 무료)

1. https://github.com 에 가입 후 로그인합니다.
2. 오른쪽 위 "+" → "New repository" → 이름 입력(예: ARMeasure) → "Create repository".
3. 만들어진 저장소 화면에서 "uploading an existing file" 링크를 클릭합니다.
4. 이 압축을 푼 **ARMeasure 폴더 안의 내용 전부**(app, .github, build.gradle.kts 등)를
   브라우저 창으로 드래그해서 올리고 "Commit changes"를 누릅니다.
   - 숨김 폴더 `.github`가 꼭 포함되어야 합니다. (Windows: 보기 → 숨긴 항목 표시)
5. 저장소 상단 "Actions" 탭을 누르면 "Build APK"가 자동으로 실행됩니다. 3~6분 걸립니다.
6. 초록색 체크가 뜨면 그 실행을 클릭 → 아래 "Artifacts" 항목의 "ARMeasure-apk"를 다운로드합니다.
7. 압축 안의 app-debug.apk 를 폰으로 옮겨 설치합니다.
   - 설치 시 "출처를 알 수 없는 앱" 허용이 필요합니다.
   - 폰이 ARCore 지원 기종이어야 합니다: https://developers.google.com/ar/devices

빌드가 빨간색 X 로 실패하면, Actions 화면의 오류 메시지를 복사해서 알려주시면 고쳐드립니다.
