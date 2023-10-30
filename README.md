# 제휴하SSU
2023-2 앱프로그래밍및실습 제휴하SSU 앱 레포지토리

## 제휴하SSU?
**제휴하SSU**는 기존 인스타를 통해 일일이 확인해야했던 숭실대학교내 제휴사업을 한눈에 볼 수 있도록 제휴사업 정보를 제공하는 안드로이드 앱입니다.

## Git commit convention
| 커밋 타입 | 설명 |
| --- | --- |
| feat | 기능 추가 |
| style | 코드 포매팅 변경, 세미 콜론 누락, 스타일 수정 |
| fix | 버그 수정 |
| refactor | 코드 리팩토링, 새로운 기능이나 버그 수정없이 현재 구현을 개선한 경우 |
| docs | README.md 같은 문서 파일 수정 |
| chore | 빌드 관련 수정, 패키지 매니저 수정, 패키지 관리자 구성 등 업데이트, Production Code 변경 없음 |

## Git branch strategy
> [GitHub Flow](https://subicura.com/git/guide/github-flow.html#github-flow-%E1%84%87%E1%85%A1%E1%86%BC%E1%84%89%E1%85%B5%E1%86%A8)
*GitHub Flow* 방식을 사용합니다.
- `feature` 브랜치는 항상 `main` 브랜치에서 분기하고 `main` 브랜치로 머지됩니다.
- `feature` 브랜치에서 기능 구현이 완료되면 `main` 브랜치로 `Pull Request`를 생성합니다.

## Git branch naming
`feature/{issue-number}-{feature-name}`과 같은 형식으로 브랜치를 생성합니다. <br/>
- `{feature-name}`은 한글로 작성해도 무방합니다.
