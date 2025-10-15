document.addEventListener('DOMContentLoaded', function() {
    const sliderTrack = document.querySelector('.slider-track');
    const slides = document.querySelectorAll('.ad-slide');
    const adIndex = document.getElementById('adIndex');

    let currentIndex = 0;
    const slideCount = slides.length;
    const slidePercent = 100 / slideCount;

    sliderTrack.style.width = (slideCount * 100) + '%';
    slides.forEach(slide => {
        slide.style.width = slidePercent + '%';
    });

    function updateSliderPosition() {
        const offset = -slidePercent * currentIndex;
        sliderTrack.style.transform = `translateX(${offset}%)`;
        updateActiveClasses();
        updateIndicator();
    }

    function updateActiveClasses() {
        slides.forEach((slide, i) => {
            slide.classList.toggle('active', i === currentIndex);
        });
    }

    function updateIndicator() {
        adIndex.textContent = currentIndex + 1;
    }

    function moveAdSlide(direction) {
        currentIndex += direction;
        if (currentIndex < 0) currentIndex = slideCount - 1;
        if (currentIndex >= slideCount) currentIndex = 0;
        updateSliderPosition();
    }

    const leftBtn = document.querySelector('.ad-arrow.left');
    const rightBtn = document.querySelector('.ad-arrow.right');

    leftBtn.addEventListener('click', () => moveAdSlide(-1));
    rightBtn.addEventListener('click', () => moveAdSlide(1));

    updateSliderPosition();
    // 여기서 자동 슬라이드 추가 (3초 간격)
    setInterval(() => {
        moveAdSlide(1);
    }, 3000);
});
