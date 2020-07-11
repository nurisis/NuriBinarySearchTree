
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
            // 데이터(키)의 중복을 허용하지 않음.
            if(data == currentNode.getData()) return

            parentNode = currentNode

            currentNode = if(currentNode.getData() > data)
                currentNode.getLeftNode()
            else
                currentNode.getRightNode()
        }

        // 추가할 새로운 노드 생성
        newNode = BTreeNode(data)

        // parentNode의 자식 노드로 newNode 추가
        if(parentNode != null){
            if(data < parentNode.getData())
                makeLeftSubTree(parentNode, newNode)
            else
                makeRightSubTree(parentNode, newNode)
        }
        // 새로운 노드가 루트노드일 경우
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
        var pVRoot = BTreeNode(target) // 가상노드이므로 임으로 data값을 target으로 설정
        var pNode = pVRoot
        var cNode = rootNode
        var dNode: BTreeNode<T>

        // 루트 노드를 pVRoot가 가리키는 노드의 오른쪽 자식 노드가 되게 한다
        pVRoot.setRightNode(rootNode)

        // 삭제 대상 노드 탐색
        while (cNode != null && cNode.getData() != target) {
            pNode = cNode

            cNode = if(target < cNode.getData())
                cNode.getLeftNode()
            else
                cNode.getRightNode()
        }

        // 삭제 대상이 존재하지 않는다면
        if(cNode == null) return false

        dNode = cNode

        // CASE 1 : 삭제 대상이 단말 노드인 경우
        if(dNode.getLeftNode() == null && dNode.getRightNode() == null) {
            if(pNode.getLeftNode() == dNode)
                removeLeftSubTree(pNode)
            else
                removeRightSubTree(pNode)
        }

        // CASE 2 : 삭제 대상이 하나의 자식 노드를 갖는 경우
        else if(dNode.getLeftNode() == null || dNode.getRightNode() == null){
            // 삭제 대상의 자식 노드
            var dcNode = if(dNode.getLeftNode() != null)
                dNode.getLeftNode()
            else
                dNode.getRightNode()

            if(pNode.getLeftNode() == dNode)
                changeLeftSubTree(pNode, dcNode!!)
            else
                changeRightSubTree(pNode, dcNode!!)
        }

        // CASE 3 : 두 개의 자식 노드를 모두 갖는 경우
        else {
            // 대체노드
            var mNode = dNode.getRightNode()!!
            // 대체노드의 부모노드
            var mpNode = dNode

            // 삭제 대상의 대체노드 찾음
            while (mNode.getLeftNode() != null) {
                mpNode = mNode
                mNode = mNode.getLeftNode()!!
            }

            // 대체 노드에 저장된 값을 삭제할 노드에 대입
            val delData = dNode.getData()
            dNode.setData(mNode.getData())

            // 대체 노드의 부모 노드와 자식 노드를 연결
            if(mpNode.getLeftNode() == mNode)
                changeLeftSubTree(mpNode, mNode.getRightNode()!!)
            else
                changeRightSubTree(mpNode, mNode.getRightNode()!!)
        }

        // 삭제된 노드가 루트 노드인 경우에 대한 추가 처리
        if(pVRoot.getRightNode() != rootNode)
            rootNode = pVRoot.getRightNode()

        return true
    }

    private fun makeLeftSubTree(parentNode:BTreeNode<T>, newNode: BTreeNode<T>) {
        // 메모리 해제
        if(parentNode.getLeftNode() != null)
            parentNode.setLeftNode(null)
        parentNode.setLeftNode(newNode)
    }

    private fun makeRightSubTree(parentNode:BTreeNode<T>, newNode: BTreeNode<T>) {
        // 메모리 해제
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
    // 메모리 소멸을 수반하지 않고 main의 왼쪽 자식 노드를 변경한다
    private fun changeLeftSubTree(main:BTreeNode<T>, sub:BTreeNode<T>) {
        main.setLeftNode(sub)
    }
    // 메모리 소멸을 수반하지 않고 main의 오른쪽 자식 노드를 변경한다
    private fun changeRightSubTree(main:BTreeNode<T>, sub:BTreeNode<T>) {
        main.setRightNode(sub)
    }

    fun printOut() {
        println("******** ROOT : ${rootNode?.getData()} *******")
        println("******** LEFT : ${rootNode?.getLeftNode()} *******")
        println("******** RIGHT : ${rootNode?.getRightNode()} *******")
    }
}
