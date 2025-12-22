document.addEventListener("DOMContentLoaded", () => {

    /* ===============================
       썸네일 → 프리뷰
    =============================== */
    const preview = document.getElementById("previewImg");
    const thumbs = document.getElementById("thumbs");

    if (thumbs && preview) {
        thumbs.addEventListener("click", (e) => {
            const t = e.target;
            if (!t.classList.contains("thumb")) return;

            document.querySelectorAll(".thumb").forEach(el => el.classList.remove("is-active"));
            t.classList.add("is-active");
            preview.src = t.src.replace("/200/150", "/800/600");
        });
    }

    /* ===============================
       옵션 select들
    =============================== */
    const optionSelects = document.querySelectorAll(".options select");

    /* ===============================
       ⭐ 총 금액 계산 함수
    =============================== */
    function updateTotalPrice() {
        const qty = parseInt(document.getElementById("quantity").value, 10) || 1;

        const priceText = document.querySelector(".price span")?.textContent || "0";
        const basePrice = parseInt(priceText.replace(/[^0-9]/g, ""), 10) || 0;

        const total = basePrice * qty;

        document.querySelector(".total-price strong").textContent =
            total.toLocaleString("ko-KR") + "원";
    }

    /* ===============================
       ⭐ 모든 옵션이 선택됐는지 체크
    =============================== */
    function isAllOptionsSelected() {
        return Array.from(optionSelects).every(select => select.value !== "");
    }

    /* ===============================
       ⭐ 옵션 변경 시
    =============================== */
    optionSelects.forEach(select => {
        select.addEventListener("change", () => {
            if (isAllOptionsSelected()) {
                updateTotalPrice();   // 🔥 전부 선택됐을 때만
            } else {
                document.querySelector(".total-price strong").textContent = "0원";
            }
        });
    });

    /* ===============================
       수량 증가 / 감소
    =============================== */
    window.changeQty = function (delta) {
        const qtyInput = document.getElementById("quantity");
        let qty = parseInt(qtyInput.value, 10) || 1;

        qty += delta;
        if (qty < 1) qty = 1;

        qtyInput.value = qty;

        // ⭐ 옵션이 모두 선택된 경우에만 계산
        if (isAllOptionsSelected()) {
            updateTotalPrice();
        }
    };

    /* ===============================
       찜하기 버튼
    =============================== */
    document.addEventListener("click", (e) => {
        const likeBtn = e.target.closest(".like-btn");
        if (!likeBtn) return;

        const icon = likeBtn.querySelector(".heart-icon");
        likeBtn.classList.toggle("active");

        icon.src = likeBtn.classList.contains("active")
            ? "/img/icons/heart-fill.png"
            : "/img/icons/heart-outline.png";
    });

    /* ===============================
       상품정보 더보기
    =============================== */
    const productDetail = document.querySelector(".product-detail");
    const toggleBtn = document.getElementById("toggle-btn");

    if (productDetail && toggleBtn) {
        productDetail.classList.add("collapsed");

        toggleBtn.addEventListener("click", () => {
            productDetail.classList.toggle("collapsed");
            toggleBtn.textContent = productDetail.classList.contains("collapsed")
                ? "상품정보 더보기 ˅"
                : "상품정보 접기 ˄";
        });
    }

    /* ===============================
       탭 전환
    =============================== */
    document.querySelectorAll(".tab-item").forEach(tab => {
        tab.addEventListener("click", function () {
            document.querySelectorAll(".tab-item").forEach(t => t.classList.remove("active"));
            this.classList.add("active");

            document.querySelectorAll(".tab-content-section")
                .forEach(section => section.classList.remove("active"));

            const target = document.getElementById(this.dataset.tab);
            if (target) target.classList.add("active");
        });
    });

});

/* ===============================
   장바구니
=============================== */
function addToCart(productId) {
    const qty = document.getElementById("quantity").value;

    fetch(`/cart/add?productId=${productId}&quantity=${qty}`, {
        method: "POST"
    }).then(() => {
        alert("장바구니에 추가되었습니다!");
    });
}
