document.addEventListener('DOMContentLoaded', () => {
    loadCart();
    initEventListeners();
});

let cartData = null;

function initEventListeners() {
    document.getElementById('selectAll').addEventListener('change', handleSelectAll);
    document.getElementById('deleteSelectedBtn').addEventListener('click', deleteSelectedItems);
    document.getElementById('checkoutBtn').addEventListener('click', handleCheckout);
}

async function loadCart() {
    const container = document.getElementById('cartItemsContainer');
    container.innerHTML = '<div class="loading">장바구니를 불러오는 중</div>';

    try {
        const response = await fetch('/cart/info');
        if (!response.ok) throw new Error('Failed to load cart');

        cartData = await response.json();
        renderCart();
    } catch (error) {
        console.error('Error loading cart:', error);
        container.innerHTML = '<div class="empty-cart"><p class="empty-text">장바구니를 불러오는 데 실패했습니다.</p></div>';
    }
}

function renderCart() {
    const container = document.getElementById('cartItemsContainer');
    const emptyCart = document.getElementById('emptyCart');
    const summary = document.querySelector('.cart-summary');

    if (!cartData || !cartData.items || cartData.items.length === 0) {
        container.style.display = 'none';
        document.querySelector('.cart-header').style.display = 'none';
        emptyCart.style.display = 'block';
        summary.style.display = 'none';
        return;
    }

    container.style.display = 'flex';
    document.querySelector('.cart-header').style.display = 'flex';
    emptyCart.style.display = 'none';
    summary.style.display = 'block';

    container.innerHTML = cartData.items.map(item => `
        <div class="cart-item" data-cart-detail-id="${item.cartDetailId}">
            <input type="checkbox" class="item-checkbox" checked>
            <div class="item-image">
                <a href="/product/detail?id=${item.productId}">
                    <img src="${item.thumbnail || 'https://via.placeholder.com/120'}" alt="${item.productName}">
                </a>
            </div>
            <div class="item-info">
                <a href="/product/detail?id=${item.productId}" class="item-name">${item.productName}</a>
                <span class="item-option">${item.optionText || ''}</span>
                <span class="item-unit-price">${formatPrice(item.unitPrice)}원</span>
            </div>
            <div class="item-quantity">
                <button class="qty-btn" onclick="updateQuantity(${item.cartDetailId}, ${item.qty - 1})" ${item.qty <= 1 ? 'disabled' : ''}>−</button>
                <span class="qty-value">${item.qty}</span>
                <button class="qty-btn" onclick="updateQuantity(${item.cartDetailId}, ${item.qty + 1})">+</button>
            </div>
            <div class="item-price">
                <span class="price-value">${formatPrice(item.totalPrice)}원</span>
                <button class="item-delete-btn" onclick="deleteItem(${item.cartDetailId})">삭제</button>
            </div>
        </div>
    `).join('');

    updateSummary();
    updateSelectAllState();
}

function updateSummary() {
    const selectedItems = getSelectedItems();

    let subtotal = 0;
    let totalCount = 0;

    selectedItems.forEach(item => {
        subtotal += item.totalPrice;
        totalCount += item.qty;
    });

    const discount = 0;
    const shippingFee = subtotal >= 50000 || subtotal === 0 ? 0 : 3000;
    const total = subtotal - discount + shippingFee;

    document.getElementById('subtotalAmount').textContent = formatPrice(subtotal) + '원';
    document.getElementById('discountAmount').textContent = '-' + formatPrice(discount) + '원';
    document.getElementById('shippingFee').textContent = shippingFee === 0 ? '무료' : formatPrice(shippingFee) + '원';
    document.getElementById('totalAmount').textContent = formatPrice(total) + '원';

    document.getElementById('checkoutBtn').disabled = selectedItems.length === 0;
}

function getSelectedItems() {
    if (!cartData || !cartData.items) return [];

    const checkboxes = document.querySelectorAll('.item-checkbox');
    const selectedItems = [];

    checkboxes.forEach((checkbox, index) => {
        if (checkbox.checked && cartData.items[index]) {
            selectedItems.push(cartData.items[index]);
        }
    });

    return selectedItems;
}

function handleSelectAll(e) {
    const checkboxes = document.querySelectorAll('.item-checkbox');
    checkboxes.forEach(checkbox => {
        checkbox.checked = e.target.checked;
    });
    updateSummary();
}

function updateSelectAllState() {
    const checkboxes = document.querySelectorAll('.item-checkbox');
    const selectAll = document.getElementById('selectAll');

    if (checkboxes.length === 0) {
        selectAll.checked = false;
        return;
    }

    const allChecked = Array.from(checkboxes).every(cb => cb.checked);
    selectAll.checked = allChecked;

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            updateSelectAllState();
            updateSummary();
        });
    });
}

async function updateQuantity(cartDetailId, newQty) {
    if (newQty < 1) return;

    try {
        const response = await fetch(`/cart/update`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ cartDetailId, qty: newQty })
        });

        if (!response.ok) throw new Error('Failed to update quantity');

        await loadCart();
    } catch (error) {
        console.error('Error updating quantity:', error);
        alert('수량 변경에 실패했습니다.');
    }
}

async function deleteItem(cartDetailId) {
    if (!confirm('이 상품을 장바구니에서 삭제하시겠습니까?')) return;

    try {
        const response = await fetch(`/cart/remove/${cartDetailId}`, {
            method: 'DELETE'
        });

        if (!response.ok) throw new Error('Failed to delete item');

        await loadCart();
    } catch (error) {
        console.error('Error deleting item:', error);
        alert('상품 삭제에 실패했습니다.');
    }
}

async function deleteSelectedItems() {
    const selectedItems = getSelectedItems();
    if (selectedItems.length === 0) {
        alert('삭제할 상품을 선택해주세요.');
        return;
    }

    if (!confirm(`선택한 ${selectedItems.length}개 상품을 삭제하시겠습니까?`)) return;

    try {
        const cartDetailIds = selectedItems.map(item => item.cartDetailId);

        const response = await fetch('/cart/remove', {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ cartDetailIds })
        });

        if (!response.ok) throw new Error('Failed to delete items');

        await loadCart();
    } catch (error) {
        console.error('Error deleting items:', error);
        alert('상품 삭제에 실패했습니다.');
    }
}

function handleCheckout() {
    const selectedItems = getSelectedItems();
    if (selectedItems.length === 0) {
        alert('주문할 상품을 선택해주세요.');
        return;
    }

    alert('주문 기능은 준비 중입니다.');
}

function formatPrice(price) {
    return price.toLocaleString('ko-KR');
}
