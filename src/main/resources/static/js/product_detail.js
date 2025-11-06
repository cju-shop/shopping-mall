// 썸네일 클릭 → 프리뷰 교체
const preview = document.getElementById('previewImg');
document.getElementById('thumbs').addEventListener('click', (e) => {
    const t = e.target;
    if (!t.classList.contains('thumb')) return;
    document.querySelectorAll('.thumb').forEach(el => el.classList.remove('is-active'));
    t.classList.add('is-active');
    preview.src = t.src.replace('/200/150','/800/600'); // 샘플 사이즈 교체
});

// 수량 증가/감소
document.querySelectorAll('.qty-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        const delta = Number(btn.dataset.delta);
        const input = document.getElementById('qty');
        const next = Math.max(1, (Number(input.value)||1) + delta);
        input.value = next;
    });
});

document.addEventListener('DOMContentLoaded', () => {
    document.addEventListener('click', (e) => {
        const likeBtn = e.target.closest('.like-btn');
        if (!likeBtn) return;

        const icon = likeBtn.querySelector('.heart-icon');
        likeBtn.classList.toggle('active');
        icon.src = likeBtn.classList.contains('active')
            ? '/img/icons/heart-fill.png'
            : '/img/icons/heart-outline.png';
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const productDetail = document.querySelector(".product-detail");
    const btn = document.getElementById("toggle-btn");

    // 기본: 접힘 상태
    productDetail.classList.add("collapsed");

    btn.addEventListener("click", () => {
        productDetail.classList.toggle("collapsed");
        if (productDetail.classList.contains("collapsed")) {
            btn.textContent = "상품정보 더보기 ˅";
        } else {
            btn.textContent = "상품정보 접기 ˄";
        }
    });
});