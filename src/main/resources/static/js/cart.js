function addToCart(i) {
	// Lập danh sách các thẻ thông tin sản phẩm
    let allBoxProduct = document.querySelectorAll(".ltn__product-item")[i].children;
    console.log(allBoxProduct[1]);
}
let allAddButton = document.querySelectorAll(".add-to-cart");
for (let i = 0; i < allAddButton.length; i++) {
    allAddButton[i].addEventListener("click", function () {
        addToCart(i);
    });
}