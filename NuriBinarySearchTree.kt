
private data class BTreeNode<T : Int> (
    val data:T,
    private var leftNode:BTreeNode<T>? = null,
    private var rightNode:BTreeNode<T>? = null
) {
    fun getLeftNode() :BTreeNode<T>? = leftNode
    fun getRightNode() :BTreeNode<T>? = rightNode
    fun setLeftNode(newNode:BTreeNode<T>) {leftNode = newNode}
    fun setRightNode(newNode:BTreeNode<T>) {rightNode = newNode}
}

class NuriBinarySearchTree<T : Int> {
    private var rootNode:BTreeNode<T>? = null

    fun insert(data:T) {
        var parentNode:BTreeNode<T>? = null
        var currentNode = rootNode
        var newNode:BTreeNode<T>? = null

        while (currentNode != null) {
            // 데이터(키)의 중복을 허용하지 않음.
            if(data == currentNode.data) return

            parentNode = currentNode

            currentNode = if(currentNode.data > data)
                currentNode.getLeftNode()
            else
                currentNode.getRightNode()
        }

        // 추가할 새로운 노드 생성
        newNode = BTreeNode(data)

        // parentNode의 자식 노드로 newNode 추가
        if(parentNode != null){
            if(data < parentNode.data)
                makeLeftSubTree(parentNode, newNode)
            else
                makeRightSubTree(parentNode, newNode)
        }
        // 새로운 노드가 루트노드일 경우
        else
            rootNode = newNode
    }

//    fun search(node:BTreeNode<T>, target: T) : BTreeNode<T> {
//
//    }

    private fun makeLeftSubTree(parentNode:BTreeNode<T>, newNode: BTreeNode<T>) {
        parentNode.setLeftNode(newNode)
    }

    private fun makeRightSubTree(parentNode:BTreeNode<T>, newNode: BTreeNode<T>) {
        parentNode.setRightNode(newNode)
    }

    fun printOut() {
        println("******** ROOT : ${rootNode?.data} *******")
        println("******** LEFT : ${rootNode?.getLeftNode()} *******")
        println("******** RIGHT : ${rootNode?.getRightNode()} *******")
    }
}
