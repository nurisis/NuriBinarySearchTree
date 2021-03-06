
private data class BTreeNode<T : Int> (
        private var data:T,
        private var leftNode:BTreeNode<T>? = null,
        private var rightNode:BTreeNode<T>? = null
) {
    fun setData(d:T) {data = d}
    fun getData() : T = data
    fun getLeftNode() :BTreeNode<T>? = leftNode
    fun getRightNode() :BTreeNode<T>? = rightNode
    fun setLeftNode(newNode:BTreeNode<T>?) {leftNode = newNode}
    fun setRightNode(newNode:BTreeNode<T>?) {rightNode = newNode}
}

class NuriBinarySearchTree<T : Int> {
    private var rootNode:BTreeNode<T>? = null

    fun insert(data:T) {
        var parentNode:BTreeNode<T>? = null
        var currentNode = rootNode
        var newNode:BTreeNode<T>? = null

        while (currentNode != null) {
            // Do not allow duplicate data (key)
            if(data == currentNode.getData()) return

            parentNode = currentNode

            currentNode = if(currentNode.getData() > data)
                currentNode.getLeftNode()
            else
                currentNode.getRightNode()
        }

        // Create new nodes to add
        newNode = BTreeNode(data)

        // Add newNode as a child node of parentNode
        if(parentNode != null){
            if(data < parentNode.getData())
                makeLeftSubTree(parentNode, newNode)
            else
                makeRightSubTree(parentNode, newNode)
        }
        // When the new node is the root node
        else
            rootNode = newNode
    }

    fun search(target: T) : T? {
        var currentNode = rootNode
        var currentData : T

        while(currentNode != null) {
            currentData = currentNode.getData()

            if(target == currentData)
                return currentData
            else if(target < currentData)
                currentNode = currentNode.getLeftNode()
            else
                currentNode = currentNode.getRightNode()
        }

        return null
    }

    fun remove(target: T): Boolean {
        var pVRoot = BTreeNode(target) // Since it is a virtual node, the data value can be arbitrarily set as a target.
        var pNode = pVRoot
        var cNode = rootNode
        var dNode: BTreeNode<T>

        // Make the root node the right child node of the node pointed to by pVRoot
        pVRoot.setRightNode(rootNode)

        // Navigate to the node to be deleted
        while (cNode != null && cNode.getData() != target) {
            pNode = cNode

            cNode = if(target < cNode.getData())
                cNode.getLeftNode()
            else
                cNode.getRightNode()
        }

        // If the object to be deleted does not exist
        if(cNode == null) return false

        dNode = cNode

        // CASE 1 : When the deletion target is a terminal node
        if(dNode.getLeftNode() == null && dNode.getRightNode() == null) {
            if(pNode.getLeftNode() == dNode)
                removeLeftSubTree(pNode)
            else
                removeRightSubTree(pNode)
        }

        // CASE 2 : When the deletion target has one child node
        else if(dNode.getLeftNode() == null || dNode.getRightNode() == null){
            // Child node to be deleted
            var dcNode = if(dNode.getLeftNode() != null)
                dNode.getLeftNode()
            else
                dNode.getRightNode()

            if(pNode.getLeftNode() == dNode)
                changeLeftSubTree(pNode, dcNode!!)
            else
                changeRightSubTree(pNode, dcNode!!)
        }

        // CASE 3 : If the deletion target has both child nodes
        else {
            // Alternative node
            var mNode = dNode.getRightNode()!!
            // Parent node of the alternative node
            var mpNode = dNode

            // Finding the alternative node => The process of finding the smallest value in the tree on the right
            while (mNode.getLeftNode() != null) {
                mpNode = mNode
                mNode = mNode.getLeftNode()!!
            }

            // Assign the value stored in the alternate node to the node to be deleted
            val delData = dNode.getData()
            dNode.setData(mNode.getData())

            // Connect the parent and child nodes of the alternate node
            if(mpNode.getLeftNode() == mNode)
                changeLeftSubTree(mpNode, mNode.getRightNode())
            else
                changeRightSubTree(mpNode, mNode.getRightNode())
        }

        // Additional processing for when the deleted node is the root node
        if(pVRoot.getRightNode() != rootNode)
            rootNode = pVRoot.getRightNode()

        return true
    }

    private fun makeLeftSubTree(parentNode:BTreeNode<T>, newNode: BTreeNode<T>) {
        // Free memory
        if(parentNode.getLeftNode() != null)
            parentNode.setLeftNode(null)
        parentNode.setLeftNode(newNode)
    }

    private fun makeRightSubTree(parentNode:BTreeNode<T>, newNode: BTreeNode<T>) {
        // Free memory
        if(parentNode.getRightNode() != null)
            parentNode.setRightNode(null)
        parentNode.setRightNode(newNode)
    }

    private fun removeLeftSubTree(node:BTreeNode<T>?) : BTreeNode<T>? {
        var deletedNode : BTreeNode<T>? = null

        node?.apply {
            deletedNode = node.getLeftNode()
            setLeftNode(null)
        }
        return deletedNode
    }
    private fun removeRightSubTree(node:BTreeNode<T>?) : BTreeNode<T>? {
        var deletedNode : BTreeNode<T>? = null

        node?.apply {
            deletedNode = node.getRightNode()
            setRightNode(null)
        }
        return deletedNode
    }
    // Change the left child node of main without causing memory destruction
    private fun changeLeftSubTree(main:BTreeNode<T>, sub:BTreeNode<T>?) {
        main.setLeftNode(sub)
    }
    // Changes the right child node of main without causing memory destruction
    private fun changeRightSubTree(main:BTreeNode<T>, sub:BTreeNode<T>?) {
        main.setRightNode(sub)
    }

    fun printOut() {
        println("** ROOT : ${rootNode?.getData()} **")
        println("** LEFT : ${rootNode?.getLeftNode()} **")
        println("** RIGHT : ${rootNode?.getRightNode()} **")
    }
}
