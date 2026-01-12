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

    /* ===============================
       옵션 선택 → 카드 누적
    =============================== */
    document.querySelectorAll(".options select")
        .forEach(s => s.addEventListener("change", onOptionsChanged));
});

/* ===============================
   장바구니 (지금은 “현재 select” 기준)
   ✅ 나중에 “selectedItems 전체”로 한 번에 담기”로 바꾸는 걸 추천
=============================== */
function addToCart() {
    if (!selectedItems.length) {
        alert("옵션을 선택해주세요!");
        return;
    }

    const items = selectedItems.map(it => ({
        productVariantId: it.variantId,
        quantity: it.qty
    }));

    fetch("/cart/add-items", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ items })
    })
        .then(res => {
            if (!res.ok) throw new Error();
            alert("장바구니에 추가되었습니다!");
        })
        .catch(() => alert("장바구니 추가 실패"));
}

/* ===============================
   카드 누적 상태
=============================== */
const selectedItems = [];

function makeKey(productId, optionValueIds) {
    const fp = [...optionValueIds].sort((a, b) => a - b).join("-");
    return `${productId}:${fp}`;
}

function getSelectedOptionText() {
    return Array.from(document.querySelectorAll(".options select"))
        .map(s => s.selectedOptions[0]?.textContent || "")
        .join(" × ");
}

function resetOptionSelects() {
    document.querySelectorAll(".options select").forEach(s => s.selectedIndex = 0);
}

function updateGrandTotal() {
    const sum = selectedItems.reduce((acc, it) => acc + it.unitPrice * it.qty, 0);
    const el = document.getElementById("grandTotal");
    if (el) el.textContent = sum.toLocaleString("ko-KR") + "원";
}

function renderSelectedCards() {
    const area = document.getElementById("selectedOptionsArea");
    if (!area) return;

    area.innerHTML = selectedItems.map(it => `
    <div class="option-card">
      <div class="left">
        <div class="title"><strong>${it.optionText}</strong></div>
        <div class="price">${(it.unitPrice * it.qty).toLocaleString("ko-KR")}원</div>
      </div>
      <div class="right">
        <div class="qty-control">
          <button type="button" onclick="changeCardQty('${it.key}', -1)">−</button>
          <span>${it.qty}</span>
          <button type="button" onclick="changeCardQty('${it.key}', 1)">+</button>
        </div>
        <button type="button" onclick="removeCard('${it.key}')">×</button>
      </div>
    </div>
  `).join("");

    updateGrandTotal();
}

window.changeCardQty = function (key, delta) {
    const item = selectedItems.find(x => x.key === key);
    if (!item) return;

    item.qty = Math.max(1, item.qty + delta);
    renderSelectedCards();
};

window.removeCard = function (key) {
    const idx = selectedItems.findIndex(x => x.key === key);
    if (idx === -1) return;

    selectedItems.splice(idx, 1);
    renderSelectedCards();
};

async function onOptionsChanged() {
    const productId = parseInt(document.getElementById("productId").value, 10);

    const selects = Array.from(document.querySelectorAll(".options select"));
    const optionValueIds = selects.map(s => parseInt(s.value, 10));

    // ✅ 덜 선택되면 종료
    if (optionValueIds.some(v => !Number.isInteger(v) || v <= 0)) return;

    const optionText = getSelectedOptionText();
    const key = makeKey(productId, optionValueIds);

    // ✅ 이미 있으면 수량 +1
    const exist = selectedItems.find(x => x.key === key);
    if (exist) {
        exist.qty += 1;
        renderSelectedCards();
        resetOptionSelects();
        return;
    }

    const res = await fetch("/product/variant/price", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ productId, optionValueIds })
    });

    if (!res.ok) {
        const msg = await res.text().catch(() => "");
        console.log("NO_VARIANT", res.status, msg);
        alert("해당 옵션 조합이 없습니다!");
        return;
    }

    const data = await res.json();

    selectedItems.push({
        key,
        productId,
        optionText,
        optionValueIds,
        variantId: data.variantId,
        unitPrice: Number(data.price) || 0,
        qty: 1
    });

    renderSelectedCards();
    resetOptionSelects();
}
function paintStars(container, rating) {
    const r = Number(rating);
    const filledCount = Number.isFinite(r) ? Math.floor(r) : 0; // ✅ 소수점 버림

    const stars = container.querySelectorAll(".star");
    stars.forEach((star, idx) => {
        star.classList.toggle("filled", idx < filledCount);
    });
}

document.addEventListener("DOMContentLoaded", () => {
    const ratingBox = document.querySelector(".rating[data-rating]");
    if (ratingBox) {
        const rating = ratingBox.dataset.rating; // "4.8" or "null"
        paintStars(ratingBox, rating);
    }

    document.querySelectorAll(".review-stars[data-rating]").forEach(el => {
        paintStars(el, el.dataset.rating);
    });

});
