package org.example.dev.이벤트소싱

class Cart(private var cartId: String) {
    private val items = mutableListOf<CartItem>()

    fun addItem(productNo: String, quantity: Int) {
        items.add(CartItem(productNo, quantity))
    }

    fun removeItem(productNo: String) {
        items.removeIf { it.productNo == productNo }
    }

    fun changeQuantity(productNo: String, quantity: Int) {
        items.removeIf { it.productNo == productNo }
        items.add(CartItem(productNo, quantity))
    }

    fun clear() {
        items.clear()
    }

    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    fun getItems(): List<CartItem> {
        return items.toList()
    }

}

data class CartItem(val productNo: String, val quantity: Int)
data class Product(val productNo: String, val name: String, val price: Int)