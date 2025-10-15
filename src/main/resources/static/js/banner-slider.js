(function () {
    // ===== 설정값 =====
    const AUTOPLAY_MS = 4000;   // 자동 전환 간격 (ms)
    const TRANSITION_MS = 400;  // 슬라이드 전환 애니메이션 시간 (CSS와 동일하게 맞추기)

    // ===== 엘리먼트 선택 =====
    const slider = document.querySelector('.banner-slider');
    if (!slider) return;

    const track  = slider.querySelector('.slider-track');
    const slides = Array.from(track.children);
    const dots   = Array.from(slider.querySelectorAll('.dot'));
    const N      = slides.length;

    if (N === 0) return;

    let index = 0;
    let timerId = null;

    // ===== 초기 설정 =====
    track.style.willChange = 'transform';
    track.style.transition = `transform ${TRANSITION_MS}ms ease`;
    update();

    // ===== 도트 클릭 이벤트 =====
    dots.forEach((btn) => {
        btn.addEventListener('click', () => {
            const i = Number(btn.dataset.index);
            if (Number.isInteger(i)) {
                goTo(i);
                restartAutoplay();
            }
        });
    });

    // ===== 자동 재생 시작 =====
    startAutoplay();

    // ===== 마우스/포커스 시 일시정지 =====
    ['mouseenter', 'focusin', 'touchstart'].forEach((ev) => {
        slider.addEventListener(ev, stopAutoplay, { passive: true });
    });
    ['mouseleave', 'focusout', 'touchend'].forEach((ev) => {
        slider.addEventListener(ev, startAutoplay, { passive: true });
    });

    // ===== 함수들 =====

    // 특정 인덱스로 이동
    function goTo(i) {
        index = (i % N + N) % N; // 안전한 모듈러 연산
        const offset = -index * 100;
        track.style.transform = `translateX(${offset}%)`;
        update();
    }

    // 다음 슬라이드
    function next() {
        goTo(index + 1);
    }

    // 도트/접근성 갱신
    function update() {
        dots.forEach((d, di) => {
            d.classList.toggle('active', di === index);
            d.setAttribute('aria-pressed', di === index ? 'true' : 'false');
        });
        slides.forEach((s, si) => {
            s.setAttribute('aria-hidden', si === index ? 'false' : 'true');
            s.tabIndex = si === index ? 0 : -1;
        });
    }

    // 자동재생 제어
    function startAutoplay() {
        if (timerId != null) return;
        timerId = setInterval(next, AUTOPLAY_MS);
    }

    function stopAutoplay() {
        if (timerId == null) return;
        clearInterval(timerId);
        timerId = null;
    }

    function restartAutoplay() {
        stopAutoplay();
        startAutoplay();
    }
})();
