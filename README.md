# NuriBinarySearchTree
A project that implements Binary Search Tree using Kotlin.  
You can insert, remove, search using NuriBinarySearchTree.

## How to use
```
fun main() {
    val tree = NuriBinarySearchTree<Int> ()

    tree.insert(20)
    tree.insert(8)
    tree.insert(4)
    tree.insert(2)
    tree.insert(7)
    tree.insert(12)
    tree.insert(10)
    tree.insert(9)
    tree.insert(11)
    println("Tree : ${tree.printOut()}")

    tree.remove(20)
    tree.remove(8)
    println("After REMOVE : ${tree.printOut()}")
}
```
